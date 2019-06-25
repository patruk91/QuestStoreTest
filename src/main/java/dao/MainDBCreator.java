package dao;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {
            DBCreator dbCreator = new DBCreator();
            dbCreator.connectToDatabase();
            dbCreator.executeStatement();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
