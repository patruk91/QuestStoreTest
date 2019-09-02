package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DBCreator {


    private Connection connection = null;
    private PreparedStatement statement = null;

    public Connection connectToDatabase() throws SQLException {
        String database = "jdbc:postgresql://localhost:5432/queststoretests";
        String user = "quest";
        String password = "quest";
       // System.out.println("Opened database successfully");
        return connection = DriverManager.getConnection(database, user, password);

    }

    private String readStatement(String filename) {
        StringBuilder out = new StringBuilder();


        try {

            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                out.append(sc.nextLine());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }


    public void executeStatement() throws SQLException {
        statement = connection.prepareStatement(readStatement("allqueries.sql"));
        statement.execute();
        System.out.println("DB created");
    }
}


