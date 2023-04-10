package com.example.PiDev;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class DBHelper {

        // Replace below database url, username and password with your actual database credentials
        private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/docnet?useSSL=false";
        private static final String DATABASE_USERNAME = "root";
        private static final String DATABASE_PASSWORD = "roottoor";
        private static final String INSERT_QUERY = "INSERT INTO evenement  VALUES (?, ?, ?, ?, ?, ?,?,?,?)";

    private static final String UPDATE_QUERY = "update evenement set nom=?, capacite=? ,local = ? , date = ? , prix =? , description =?  where id =? ;";

    private static final String GET_QUERY = "SELECT * FROM  evenement";
        private static final String DELETE_QUERY = "DELETE FROM evenement WHERE id = ?";

        public void deleteEvent(Integer id) {
            System.out.println(DELETE_QUERY+id);
            try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                 PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {

           statement.executeUpdate("DELETE FROM evenement WHERE id ="+id+";");

            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }

            public void saveEvent(Event e)  {

            // Step 1: Establishing a Connection and
            // try-with-resource statement will auto close the connection.
            try (Connection connection = DriverManager
                    .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
                preparedStatement.setNull(1, NULL);
                preparedStatement.setInt(2, e.getCategorie()=="public"?1:2);
                preparedStatement.setString(3, e.getNom());
                preparedStatement.setInt(4, e.getCapacite());
                preparedStatement.setString(5, e.getLocal());
                preparedStatement.setString(6, e.getDate());
                preparedStatement.setDouble(7,e.getPrix());
                preparedStatement.setString(8,e.getDescription());
                preparedStatement.setNull(9,NULL);

                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                // print SQL exception information
                printSQLException(ex);
            }
        }

    public void updateEvent(Event e)  {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY) ;
             ) {
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
        } catch (SQLException ex) {
            // print SQL exception information
            printSQLException(ex);
        }
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
                      event.setCategorie(res.getInt("type_id")==1?"public":"prive");
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



}
