package dao;

import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {

            AdminDAO adminDAO = new AdminDAO();

            UserDAO userDAO = new UserDAO();

            User user = userDAO.seeProfile(20);

            System.out.println(user.getFirstName());


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
