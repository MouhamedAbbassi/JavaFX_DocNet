/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Service.FicheService;
import Service.ServiceMedicament;
import Service.OrdonnanceService;
import Service.ServiceReservation;
import Service.ServiceUser;
import Entity.Fiche;
import Entity.Ordonnance;
import Entity.OrdonnanceMedicament;
import Entity.Reservation;
import Entity.Medicament;
import Entity.User;
import Service.UserSession;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfiicherFicheController {

    @FXML
    private ImageView patientImage;
    @FXML
    private VBox detailFiche;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab tabReservation;
    @FXML
    private Tab tabOrdonnance;
    
     private ObjectProperty<Image> patientImages = new SimpleObjectProperty<>();
    @FXML
    private Label dNaissance;
    @FXML
    private Label tel;
    @FXML
    private Label genre;
    @FXML
    private Label eClinique;
    @FXML
    private Label tAssurance;
    @FXML
    private Button ret;
    @FXML
    private TableColumn<?,?> dateColumn;
    @FXML
    private TableView<Reservation> reservationTable;
    
    @FXML
    private TableView<Ordonnance> ordonnanceTable;
    @FXML
    private TableColumn<Ordonnance, String> dateColumns;
    @FXML
    private TableColumn<Ordonnance, String> commentaireColumn;
    @FXML
    private TableColumn<Ordonnance, String> medicamentsColumn;

    OrdonnanceService ordonnanceService = new OrdonnanceService();
    
    
    Fiche f = new Fiche();
    User userPatient = new User();
    User userDoctor = new User();
    
    public ObjectProperty<Image> patientImageProperty() {
        return patientImages;
    }

    public Image getPatientImage() {
        return patientImages.get();
    }

    public void setPatientImage(Image image) {
        patientImages.set(image);
    }
    
    int patientId;
    ServiceUser userService = new ServiceUser();
    
    
    FicheService ficheService = new FicheService();
    ServiceReservation reservationService = new ServiceReservation();
    
    ObservableList<Reservation> res;
    ObservableList<Ordonnance> ord;
    ServiceMedicament ServiceM = new ServiceMedicament();
    
    
    /**
     * Initializes the controller class.
     */
     
     public void initialize(int id) {
        UserSession userSession = new UserSession();
        patientId = id;
        userPatient = userService.getPatientInfo(patientId);
        userDoctor = userService.getDoctorInfo(userSession.getUser().getId());
        ObservableList<Reservation> reservations = reservationService.getReservationForPatient(patientId,userSession.getUser().getId());
        res = reservations;
        ObservableList<Ordonnance> ordonnances = ordonnanceService.getOrdonnanceForPatient(patientId, userSession.getUser().getId());
        ord = ordonnances;
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        reservationTable.setItems(reservations);
        dateColumns.setCellValueFactory(new PropertyValueFactory<>("date"));
        commentaireColumn.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        medicamentsColumn.setCellValueFactory(cellData -> {
        StringBuilder sb = new StringBuilder();
        for (OrdonnanceMedicament medicament : cellData.getValue().getMedicaments()) {
            ServiceMedicament medicamentService = new ServiceMedicament();
            Medicament med = new Medicament();
            
            med.setNom(medicamentService.getMedicamentNameById(medicament.getIdMedicament()));
            sb.append(med.getNom()).append(" (Dosage: ")
            .append(medicament.getDosage()).append(", Durée: ")
            .append(medicament.getDuration()).append(")\n");
        }
        return new SimpleStringProperty(sb.toString());
        });

        ordonnanceTable.setItems(ordonnances);
        patientImage.setFitWidth(200); // set the width to 200
        patientImage.setFitHeight(200); // set the height to 200
        patientImage.setPreserveRatio(true); // preserve the aspect ratio of the image
        patientImage.imageProperty().bind(patientImages);
        Fiche fiche = ficheService.getFiche(userSession.getUser().getId(), patientId);
        f=fiche;
        if(fiche.getDate() == null)
        {
            dNaissance.setText("Date de Naissance : --------");
        }else{
            dNaissance.setText("Date de Naissance : "+fiche.getDate());
        }
        if(fiche.getGenre() == null)
        {
            genre.setText("Genre : --------");
        }else{
            genre.setText("Genre : "+fiche.getGenre());
        }
        if(fiche.getTel() == 0)
        {
            tel.setText("Tel : --------");
        }else{
            tel.setText("Tel : "+String.valueOf(fiche.getTel()));
        }
        if(fiche.getEtatClinique() == null)
        {
            eClinique.setText("Etat Clinique : --------");
        }else{
            eClinique.setText("Etat Clinique : "+fiche.getEtatClinique());
        }
        if(fiche.getTypeAssurance() == null)
        {
            tAssurance.setText("Type d'assurance : --------");
        }else{
            tAssurance.setText("Type d'assurance : "+fiche.getTypeAssurance());
        }
    }   
    @FXML
    private void returnButton(ActionEvent event) {
    	try {
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherOrdonnance.fxml"));
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
    private void modifierButton(ActionEvent event) {
    	try { 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateFiche.fxml"));
            Parent root = loader.load();
            UpdateFicheController controller = loader.getController();
            controller.initialize(f);
            Scene newScene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(newScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void createPdf(ActionEvent event) {
        try {
            // create a new PDF document
            PDDocument document = new PDDocument();
            File imageFile = new File("src/resource/pdfBackground11.png");
            PDImageXObject backgroundImage = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
            // create a new page and add it to the document
            PDPage page = new PDPage(new PDRectangle(backgroundImage.getWidth(), backgroundImage.getHeight()));
            document.addPage(page);

            // create a content stream for the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.drawImage(backgroundImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

            File fontFile = new File("src/resource/LufgaSemiBold.ttf");
            PDType0Font font = PDType0Font.load(document, fontFile);
            contentStream.setFont(font, 16);
            contentStream.setNonStrokingColor(Color.WHITE);

            float x0 = 110; // left position of the first text box
            float y0 = 810; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(x0, y0);
            contentStream.showText("DOCNET");
            contentStream.endText();

            File fontFile1 = new File("src/resource/drname.ttf");
            PDType0Font font1 = PDType0Font.load(document, fontFile1);
            contentStream.setFont(font1, 14);
            contentStream.setNonStrokingColor(Color.WHITE);

            float x00 = 480; // left position of the first text box
            float y00 = 810; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(x00, y00);
            contentStream.showText("DR." + userDoctor.getNom() + " " + userDoctor.getPrenom());
            contentStream.endText();

            contentStream.setFont(font1, 20);
            contentStream.setNonStrokingColor(Color.BLACK);

            float x000 = 250; // left position of the first text box
            float y000 = 600; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(x000, y000);
            contentStream.showText("FICHE MEDICALE");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 17);
            contentStream.setNonStrokingColor(Color.BLACK);
            float xDate = 475; // left position of the first text box
            float yDate = 520; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(xDate, yDate);
            contentStream.showText(LocalDate.now().toString());
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 17);
            contentStream.setNonStrokingColor(Color.BLACK);
            float xNomPatient = 170; // left position of the first text box
            float yNomPatient = 485; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(xNomPatient, yNomPatient);
            contentStream.showText(userPatient.getNom() + " " + userPatient.getPrenom());
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 17);
            contentStream.setNonStrokingColor(Color.BLACK);
            float xDateNaissance = 150; // left position of the first text box
            float yDateNaissance = 450; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(xDateNaissance, yDateNaissance);
            if(f.getDate() != null){
                contentStream.showText(f.getDate());
            }
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 17);
            contentStream.setNonStrokingColor(Color.BLACK);
            float xGenre = 475; // left position of the first text box
            float yGenre = 450; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(xGenre, yGenre);
            if(f.getGenre()!= null)
            contentStream.showText(f.getGenre().toUpperCase());
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 17);
            contentStream.setNonStrokingColor(Color.BLACK);
            float xEtat = 150; // left position of the first text box
            float yEtat = 422; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(xEtat, yEtat);
            if(f.getEtatClinique()!= null)
            contentStream.showText(f.getEtatClinique());
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 17);
            contentStream.setNonStrokingColor(Color.BLACK);
            float xType = 375; // left position of the first text box
            float yType = 422; // top position of the first text box

            // write the first text inside the first text box
            contentStream.beginText();
            contentStream.newLineAtOffset(xType, yType);
            if(f.getTypeAssurance()!= null)
            contentStream.showText(f.getTypeAssurance());
            contentStream.endText();

            float xReservation = 260;
            float yReservation = 300;
            float xNumR = 30;
            float yNumR = 300;
            int numReservation = 1;
            if (yReservation < 300) {
                contentStream.close();
                File imageFile1 = new File("src/resource/pdfBackground.png");
                PDImageXObject backgroundImage1 = PDImageXObject.createFromFile(imageFile1.getAbsolutePath(), document);
                PDPage page1 = new PDPage(new PDRectangle(backgroundImage1.getWidth(), backgroundImage1.getHeight()));
                document.addPage(page1);
                contentStream = new PDPageContentStream(document, page1);
                contentStream.drawImage(backgroundImage1, 0, 0, page1.getMediaBox().getWidth(), page1.getMediaBox().getHeight());
                yReservation = 600;
                for (Reservation reservation : res) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 17);
                    contentStream.newLineAtOffset(xNumR, yNumR);
                    contentStream.showText("Date de Reservation num " + numReservation + " :");
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 17);
                    contentStream.newLineAtOffset(xReservation, yReservation);
                    contentStream.showText(reservation.getstart().toString());
                    contentStream.endText();
                    yReservation -= 30;
                    yNumR -= 30;
                    numReservation += 1;
                }
            } else {
                for (Reservation reservation : res) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 17);
                    contentStream.newLineAtOffset(xNumR, yNumR);
                    contentStream.showText("Date de Reservation num " + numReservation + " :");
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 17);
                    contentStream.newLineAtOffset(xReservation, yReservation);
                    contentStream.showText(reservation.getstart().toString());
                    contentStream.endText();
                    yReservation -= 30;
                    yNumR -= 30;
                    numReservation += 1;
                }
            }

            float xOrdonnance = 260;
            float yOrdonnance = yReservation - 30;
            float xNumO = 30;
            float yNumO = yNumR - 30;
            int numOrdonnance = 1;
            float newYOrdonnance = 700;
            for (int i = 0; i < ord.size(); i++) {
                Ordonnance ordonnance = ord.get(i);
                // your code for adding ordonnance and its associated medicaments goes here
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA ,17);
                contentStream.newLineAtOffset(xNumO, yNumO);
                contentStream.showText("Ordonnance num " + numOrdonnance + " :");
                contentStream.endText();
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 17);
                contentStream.newLineAtOffset(xOrdonnance, yOrdonnance);
               String text = ordonnance.getCommentaire().replace('\n', ' ');
                contentStream.showText(text);
                
                contentStream.endText();
                yOrdonnance -= 30;

                numOrdonnance += 1;
                for (OrdonnanceMedicament medicament : ordonnance.getMedicaments()) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 14);
                    contentStream.newLineAtOffset(xOrdonnance + 20, yOrdonnance);
                    contentStream.showText(ServiceM.getMedicamentNameById(medicament.getIdMedicament()) + "  Dosage: " + medicament.getDosage() + " Duration: " + medicament.getDuration());
                    contentStream.endText();
                    yOrdonnance -= 40;
                    yNumO = yOrdonnance;
                }
                // check if yOrdonnance is less than 300 after adding a new ordonnance and its medicaments
                if (yOrdonnance < 300) {
                    // close the current page
                    contentStream.close();
                    
                    // create a new page and add a background image
                    File imageFile1 = new File("src/resource/pdfBackground.png");
                    PDImageXObject backgroundImage1 = PDImageXObject.createFromFile(imageFile1.getAbsolutePath(), document);
                    PDPage page1 = new PDPage(new PDRectangle(backgroundImage1.getWidth(), backgroundImage1.getHeight()));
                    document.addPage(page1);
                    contentStream = new PDPageContentStream(document, page1);
                    contentStream.drawImage(backgroundImage1, 0, 0, page1.getMediaBox().getWidth(), page1.getMediaBox().getHeight());
                    contentStream.setFont(font, 16);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x0, y0);
                    contentStream.showText("DOCNET");
                    contentStream.endText();
                    contentStream.setFont(font1, 14);
                    contentStream.setNonStrokingColor(Color.WHITE);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(x00, y00);
                    contentStream.showText("DR." + userDoctor.getNom() + " " + userDoctor.getPrenom());
                    contentStream.endText();
                    yOrdonnance = 600;
                    yNumO = 600;
                    for ( i = numOrdonnance-1; i < ord.size(); i++) {
                    Ordonnance ordonnances = ord.get(i);
                    // reset yOrdonnance and add the current ordonnance and its associated medicaments to the new page
                    
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 17);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.newLineAtOffset(xNumO, yNumO);
                    contentStream.showText("Ordonnance num " + numOrdonnance + " :");
                    contentStream.endText();
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 17);
                    contentStream.newLineAtOffset(xOrdonnance, yOrdonnance);
                    contentStream.showText(ordonnances.getCommentaire());
                    contentStream.endText();
                    yOrdonnance -= 30;

                    for (OrdonnanceMedicament medicament : ordonnances.getMedicaments()) {
                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA, 14);
                        contentStream.newLineAtOffset(xOrdonnance + 20, yOrdonnance);
                        contentStream.showText(ServiceM.getMedicamentNameById(medicament.getIdMedicament()) + "  Dosage: " + medicament.getDosage() + " Duration: " + medicament.getDuration());
                        contentStream.endText();
                        yOrdonnance -= 40;
                        yNumO = yOrdonnance;
                    }
                  
                    numOrdonnance += 1;
                }
                }
            }

            contentStream.close();

            // save the document to a file
            File file = new File("fiche de " + userPatient.getNom() + " " + userPatient.getPrenom() + ".pdf");
            document.save(file);

            // open the document in the default PDF viewer
            Desktop.getDesktop().open(file);

            // close the document
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
