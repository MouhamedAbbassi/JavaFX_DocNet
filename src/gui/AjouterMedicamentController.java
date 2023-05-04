package gui;

import com.google.zxing.BinaryBitmap;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import Service.ServiceMedicament;
import Entity.Medicament;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class AjouterMedicamentController implements Initializable {

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField doseTextField;
    @FXML
    private TextField prixTextField;
    @FXML
    private TextField stockTextField;
    @FXML
    private Button ajouterProduitButton;
    @FXML
    private ComboBox<String> typeComboBox;

    private File imageFile;
    private FileInputStream fis;
    @FXML
    private Button uploadButton;
    String im;
    @FXML
    private Button gen;
    @FXML
    private TextField QR_path;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.getItems().addAll("Sachét", "Comprimé", "Campsule");
    }

    @FXML
    private void ajouterProduitButtonAction() {
        String nom = nomTextField.getText().trim();
        String dose = doseTextField.getText().trim();
        String prix = prixTextField.getText().trim();
        String stock = stockTextField.getText().trim();
        String type = typeComboBox.getSelectionModel().getSelectedItem();
        String image = im; // or a default image

        if (!nom.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le nom doit contenir uniquement des lettres et des espaces");
            alert.showAndWait();
            return;
        }
        int nb_dose = 0;
        try {
            nb_dose = Integer.parseInt(dose);
            if (nb_dose <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "La dose doit être un entier strictement positif");
            alert.showAndWait();
            return;
        }
        int prix_int = 0;
        try {
            prix_int = Integer.parseInt(prix);
            if (prix_int <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le prix doit être un entier strictement positif");
            alert.showAndWait();
            return;
        }
        int stock_int = 0;
        try {
            stock_int = Integer.parseInt(stock);
            if (stock_int <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le stock doit être un entier strictement positif");
            alert.showAndWait();
            return;
        }
        if (!type.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Le type doit contenir uniquement des lettres et des espaces");
            alert.showAndWait();
            return;
        }
        if (nom.isEmpty() || type == null || dose.isEmpty() || prix.isEmpty() || stock.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs");
            alert.showAndWait();
        } else {
            Medicament m = new Medicament(nom, type, nb_dose, prix_int, stock_int, image);
            ServiceMedicament servicemedicament = new ServiceMedicament();
            servicemedicament.ajouterm(m);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Medicament ajoutée avec succès");
            alert.showAndWait();

        }
    }

    ;

        @FXML
    private void backto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherMedicament.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void upimg(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        chooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            BufferedImage bufferedImg = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImg, null);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageFile = file;
            fis = new FileInputStream(file);
            im = file.getName();
        }
    }

    /*  @FXML
        private void gen(ActionEvent event) {
          /*try {

                ByteArrayOutputStream out = QRCode.from(nomTextField.getText()).to(ImageType.PNG).stream();

                String f_name = nomTextField.getText();
                String Path_name = "C:\\Users\\Dalio\\Desktop\\ttt\\" ;

                FileOutputStream fout = new FileOutputStream(new File(Path_name +(f_name + ".PNG")));
                fout.write(out.toByteArray());
                fout.flush();



            } catch (Exception e) {
                System.out.println(e);
            }

        try {
            // concatenate all attributes into a single string
            String qrText = nomTextField.getText() + " | " +
                            typeComboBox.getSelectionModel().getSelectedItem() + " | " +
                            doseTextField.getText() + " | " +
                            prixTextField.getText() + " | " +
                            stockTextField.getText() + " | " +
                            im;

            // generate the QR code image
            ByteArrayOutputStream out = QRCode.from(qrText)
                                                 .to(ImageType.PNG)
                                                 .stream();

            // create the file name and path
            String fileName = nomTextField.getText() + ".png";
            String filePath = "C:\\Users\\Dalio\\Desktop\\ttt\\" + fileName;

            // write the image data to a file
            FileOutputStream fout = new FileOutputStream(new File(filePath));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void read(ActionEvent event) {
     //(QR_path.getText());
        try {
            
            InputStream barcodeInputStream = new FileInputStream(QR_path.getText());
            BufferedImage barcBufferedImage = ImageIO.read(barcodeInputStream);
            
            LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Reader reader = new MultiFormatReader();
            Result reslut = reader.decode(bitmap);
            
            
            //nomTextField.setText(reslut.getText());
            nomTextField.setText(reslut.getText());
            typeComboBox.getSelectionModel().select(reslut.getText());
            doseTextField.setText(reslut.getText());
            prixTextField.setText(reslut.getText());
            stockTextField.setText(reslut.getText());
            im = (reslut.getText());
            
            
            
        } catch (Exception e) {
        }
    }  */
    @FXML
    private void gen(ActionEvent event) {
        try {
            // concatenate all attributes into a single string separated by a delimiter
            String qrText = nomTextField.getText() + " | "
                    + typeComboBox.getSelectionModel().getSelectedItem() + " | "
                    + doseTextField.getText() + " | "
                    + prixTextField.getText() + " | "
                    + stockTextField.getText() + " | "
                    + im;

            // generate the QR code image
            ByteArrayOutputStream out = QRCode.from(qrText)
                    .to(ImageType.PNG)
                    .stream();

            // create the file name and path
            String fileName = nomTextField.getText() + ".png";
            String filePath = "C:\\xampp\\htdocs\\uploads\\images\\" + fileName;

            // write the image data to a file
            FileOutputStream fout = new FileOutputStream(new File(filePath));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void read(ActionEvent event) {
        try {
            // read the QR code image file and create a binary bitmap
            InputStream barcodeInputStream = new FileInputStream(QR_path.getText());
            BufferedImage barcBufferedImage = ImageIO.read(barcodeInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // decode the QR code and split the resulting text into attributes
            Reader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);
            String qrText = result.getText();
            String[] attributes = qrText.split(" \\| ");

            // set the values of the GUI components based on the decoded attributes
            nomTextField.setText(attributes[0]);
            typeComboBox.getSelectionModel().select(attributes[1]);
            doseTextField.setText(attributes[2]);
            prixTextField.setText(attributes[3]);
            stockTextField.setText(attributes[4]);
            im = attributes[5]; // assuming "im" is a variable that stores the image path
        } catch (Exception e) {
            // handle exceptions
        }
    }

}
