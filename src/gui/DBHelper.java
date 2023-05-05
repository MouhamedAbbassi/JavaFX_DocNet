package gui;

import Config.MaConnexion;
import Entity.Role;
import Entity.User;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.Types.NULL;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import org.mindrot.jbcrypt.BCrypt;


public class DBHelper {

     // Replace below database url, username and password with your actual database credentials
        private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/docnet";
        private static final String DATABASE_USERNAME = "root";
        private static final String DATABASE_PASSWORD ="";
        private static final String INSERT_QUERY = "INSERT INTO evenement (nom,capacite,local,date,prix,description) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_QUERY = "update evenement set nom=?, capacite=? ,local = ? , date = ? , prix =? , description =?  where id =? ;";

    private static final String GET_QUERY = "SELECT * FROM  evenement";
    private static final String FIND_QUERY = "SELECT * FROM  evenement WHERE nom like ?%";
    private static final String DELETE_QUERY = "DELETE FROM evenement WHERE id = ?";


        public void deleteEvent(Integer id) {
            System.out.println(DELETE_QUERY+id);
            try (PreparedStatement statement = MaConnexion.getInstance().getCnx().prepareStatement(DELETE_QUERY); ) {

           statement.executeUpdate("DELETE FROM evenement WHERE id ="+id+";");

            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }

            public void saveEvent(Event e) {
            try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(INSERT_QUERY)) {           
            preparedStatement.setString(1, e.getNom());
            preparedStatement.setInt(2, e.getCapacite());
            preparedStatement.setString(3, e.getLocal());
            preparedStatement.setString(4, e.getDate());
            preparedStatement.setDouble(5,e.getPrix());
            preparedStatement.setString(6,e.getDescription());
                System.out.println(e.getNom());
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                // print SQL exception information
                printSQLException(ex);
            }
        }
public boolean register(Event e) 
     {
       try(PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(INSERT_QUERY))
           {        
               preparedStatement.setString(1, e.getNom());
            preparedStatement.setInt(2, e.getCapacite());
            preparedStatement.setString(3, e.getLocal());
            preparedStatement.setString(4, e.getDate());
            preparedStatement.setDouble(5,e.getPrix());
            preparedStatement.setString(6,e.getDescription());

            if (preparedStatement.executeUpdate() > 0) 
            {
                System.out.println("event add successfully.");
                return true;
            } 
            else
            {
                System.out.println("Something went wrong.");
                return false;
            }
           
            }
        catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
                return false;
            }
    }
    

    public void updateEvent(Event e)  {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (PreparedStatement preparedStatement = MaConnexion.getInstance().getCnx().prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, e.getNom());
            preparedStatement.setInt(2, e.getCapacite());
            preparedStatement.setString(3, e.getLocal());
            preparedStatement.setString(4, e.getDate());
            preparedStatement.setDouble(5,e.getPrix());
            preparedStatement.setString(6,e.getDescription());
            preparedStatement.setInt(7,e.getId());
            System.out.println("PrepareStatement" + preparedStatement.toString());

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
            System.out.println("exucuted");
           // this.sendMail("naski.semah@gmail.com");
        } catch (SQLException ex) {
            // print SQL exception information
            printSQLException(ex);
        }// catch (MessagingException ex) {
           // throw new RuntimeException(ex);
       // }
    }
    public List<EventModel> findBy(String search){
        String query="SELECT * FROM  evenement WHERE nom like '"+search+"%'";
        List<EventModel> events = new ArrayList<>();
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery(query);
            while (res.next()) {
                EventModel event = new EventModel();
                event.setId(res.getInt("id"));
                event.setNom(res.getString("nom") );
                event.setCapacite(res.getInt("capacite"));
                event.setLocal( res.getString("local"));
                event.setDate(res.getString("date"));
                event.setPrix(res.getDouble("prix"));
                event.setDescription(  res.getString("description"));
              //  event.setCategorie(res.getInt("type_id")==1?"public":"prive");
                System.out.println(event.toString());
                events.add(event);
            }

        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return events;
    }
        public List<EventModel> getAllEvents()  {
            List<EventModel> events = new ArrayList<>();
        try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                 Statement statement = connection.createStatement()) {
                    ResultSet res = statement.executeQuery(GET_QUERY);
                    while (res.next()) {
                        EventModel event = new EventModel();
                      event.setId(res.getInt("id"));
                                event.setNom(res.getString("nom") );
                                event.setCapacite(res.getInt("capacite"));
                                event.setLocal( res.getString("local"));
                                event.setDate(res.getString("date"));
                                event.setPrix(res.getDouble("prix"));
                        event.setDescription(  res.getString("description"));
                    //  event.setCategorie(res.getInt("type_id")==1?"public":"prive");
                      System.out.println(event.toString());
                        events.add(event);
                    }

            } catch (SQLException e) {
                // print SQL exception information
                printSQLException(e);
            }
    return events;
    }



        public static void printSQLException(SQLException ex) {
            for (Throwable e: ex) {
                if (e instanceof SQLException) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    void sendMail(String to) throws MessagingException {
        String subject = "DOCNET MESSENGER !";
        String message = "check your events there ara some updates <3 <3 ";

        // Set up the properties of the email server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a session with the email server
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("savewaterwed321@gmail.com", "zurjqlspgphycovk");
            }
        });

        // Compose the email message
        Message emailMessage = new MimeMessage(session);
        emailMessage.setFrom(new InternetAddress("savewaterwed321@gmail.com"));
        emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        // Send the email message
        Transport.send(emailMessage);

        // Show a message to the user that the email has been sent
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email sent");
        alert.setHeaderText(null);
        alert.setContentText("The email has been sent.");
        alert.showAndWait();
    }



}
