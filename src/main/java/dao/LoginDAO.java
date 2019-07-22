package dao;

import model.users.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class LoginDAO implements ILoginDAO {

    //private User user;

    private DBCreator dbCreator = new DBCreator();

    public User getUserByLogin(String login, String password) {
        User user = new EmptyUser();
        try {
            Connection con = dbCreator.connectToDatabase();

            String query = "SELECT * FROM users WHERE login LIKE ? AND password LIKE ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            String userLogin = "";
            String userPass = "";
            String userType = "";
            int userId = 0;
            while (resultSet.next()) {
                userId = resultSet.getInt("id");
                userLogin = resultSet.getString("login");
                userPass = resultSet.getString("password");
                userType = resultSet.getString("usertype");
                }

            if (userType.equals("mentor")){
                user = new Mentor(userId, userLogin, userPass, userType);
            }

            else if (userType.equals("student")){
                user = new Student(userId, userLogin, userPass, userType);
            }
            else if (userType.equals("admin")){
                user = new Admin(userId, userLogin, userPass, userType);
            }

            resultSet.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;

    }
}
