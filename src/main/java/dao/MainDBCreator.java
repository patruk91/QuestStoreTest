package dao;

import model.users.Mentor;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {
            DBCreator dbCreator = new DBCreator();
            dbCreator.connectToDatabase();
            //dbCreator.executeStatement();

            //AdminDAO adminDAO = new AdminDAO();

            //Mentor mentor = adminDAO.getMentor(5);

            UserDAO userDAO = new UserDAO();

            System.out.println(userDAO.seeArtifactsList());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
