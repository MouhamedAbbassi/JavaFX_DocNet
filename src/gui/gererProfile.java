package gui;

import Entity.User;
import Service.ServiceUser;
import Service.UserSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class gererProfile implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private Button update;
    
     File file;
    @FXML
    private Button image;
    
    @FXML
    private Circle imageView;
    @FXML
    private TextField numero;
    @FXML
    private Button save;
    
       public List<File> findAllFilesInFolder(File folder) {
        List<File> list = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                list.add(file);

            } else {
                findAllFilesInFolder(file);
            }
        }
        return list;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
         UserSession userSession = new UserSession();
          ServiceUser userService = new ServiceUser();

        File folder = new File("C:\\xampp\\htdocs\\uploads\\images");
        Circle cir2 = new Circle(250, 250, 100);
        for (int i = 0; i < findAllFilesInFolder(folder).size(); i++) {
            if (findAllFilesInFolder(folder).get(i).getName().equals(userSession.getUser().getImage())) {
                try {
                    Image imge = new Image(new FileInputStream("C:\\xampp\\htdocs\\uploads\\images\\" + userSession.getUser().getImage()));
                    imageView.setFill(new ImagePattern(imge));
                    cir2.setFill(new ImagePattern(imge));
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        nom.setText(userSession.getUser().getNom());
        prenom.setText(userSession.getUser().getPrenom());
         numero.setText(userSession.getUser().getNumero());

    }    

    @FXML
    private void update(MouseEvent event) throws FileNotFoundException, IOException 
    {
        ServiceUser user1 = new ServiceUser();
        UserSession session = new UserSession();
        String emailS = session.getUser().getEmail();

        String name = nom.getText();
        String lastName = prenom.getText();
         String num = numero.getText();

         User user = new User(name, lastName,num );
         user1.Update(user, emailS);
              Parent root = FXMLLoader.load(getClass().getResource("profileUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    
    }

    @FXML
    private void image(MouseEvent event)
    {
        Path to = null;
        String m = null;
        String path = "C:\\xampp\\htdocs\\uploads\\images";
        JFileChooser chooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & PNG Images", "jpg", "jpeg", "PNG");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            m = chooser.getSelectedFile().getAbsolutePath();

            file = chooser.getSelectedFile();
            String fileName = file.getName();

            if (chooser.getSelectedFile() != null) {

                try {
                    Path from = Paths.get(chooser.getSelectedFile().toURI());
                    to = Paths.get(path + "\\" + fileName);
                    
                    CopyOption[] options = new CopyOption[]
                    {
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.COPY_ATTRIBUTES
                    };
 
                    Files.copy(from, to, options);
                    System.out.println("added");
 
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }

        }
        
    }

    @FXML
    private void saveImage(MouseEvent event) throws FileNotFoundException, IOException 
    {
        ServiceUser u = new ServiceUser();
        UserSession session = new UserSession();
        String emailS = session.getUser().getEmail();

               FileInputStream fl = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                String fileName = file.getName();
                fl.read(data);
                fl.close();

         User user = new User(fileName );
         u.addImage(user, emailS);
    
              Parent root = FXMLLoader.load(getClass().getResource("profileUser.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
     Scene scene = new Scene(root);
     stage.setScene(scene);
     stage.show();
    
         
    }
    
}
