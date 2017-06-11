package de.hdm.wim.devlab.machinelearning;

import java.sql.Connection;
import java.sql.PreparedStatement;

import de.hdm.wim.devlab.machinelearning.server.db.DBConnection;



public class App {

    public static void main(String[] args) {

       
        Connection connection = null;

        try {
                connection = DBConnection.connection();
                String schema = connection.getSchema();
                System.out.println("Successful connection - Schema: " + schema);

                System.out.println("Insert data example:");
                System.out.println("=========================================");

                // Prepared statement to insert data
                String insertSql = "INSERT INTO [user] (id, vorname, nachname, " 
                    + " email) VALUES (?,?,?,?);";

              //  java.util.Date date = new java.util.Date();
              //  java.sql.Timestamp sqlTimeStamp = new java.sql.Timestamp(date.getTime());

                try (PreparedStatement prep = connection.prepareStatement(insertSql)) {
                        prep.setInt(1, 1);
                        prep.setString(2, "");
                        prep.setString(3, "");
                        prep.setString(4, "");
                       //prep.setTimestamp(5, sqlTimeStamp);

                        int count = prep.executeUpdate();
                        System.out.println("Inserted: " + count + " row(s)");
                }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }
}