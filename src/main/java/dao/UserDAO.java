package dao;

import model.items.Artifact;
import model.items.Quest;
import model.users.Admin;
import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{

    DBCreator dbCreator = new DBCreator();



    //todo mentor and admin profile
    public User seeProfile(int id) throws SQLException {
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select usertype from users where id=? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();
        String userType = new String();
        if(result.next()){
            userType = result.getString("usertype");
        }

        if (userType.equals("codecooler")){
            System.out.println("i am codecooler");
            Codecooler codecooler = getFullCodecoolerObject(id);
            return codecooler;
        }else if(userType.equals(("mentor"))){
            System.out.println("im mentor");
            Mentor mentor = getFullMentor(id);
            return mentor;
        }else if (userType.equals("admin")){
            System.out.println("i am admin");
            Admin admin = getFullADmin(id);
            return admin;
        }

        return null;
    }


    //todo
    public void updateMyProfile() {

    }


    private Codecooler getFullCodecoolerObject(int id) throws SQLException{
        DBCreator creator = new DBCreator();
        Connection connection = creator.connectToDatabase();
        System.out.println("connected");
        PreparedStatement stm = connection.prepareStatement("select * from users left  join  studentpersonals on users.id=studentpersonals.user_id  where id= ? ");
        stm.setInt(1 ,id);

        ResultSet result = stm.executeQuery();
        System.out.println("query executed");
        Codecooler codecooler;
        while (result.next()){
            int user_id = result.getInt("id");

            String login = result.getString(("login"));
            System.out.println(login);
            String password = result.getString("password");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNumber = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");
            int classID = result.getInt("class_id");
            int experiencePoints = result.getInt("experience_points");
            int coolcoins = result.getInt("coolcoins");

            codecooler = new Codecooler(user_id, login, password, firstName, lastName,phoneNumber, email, address, classID, experiencePoints, coolcoins);
            return codecooler;


        }
        return null;
    }

    private Mentor getFullMentor(int id) throws SQLException{
        DBCreator creator = new DBCreator();
        Connection connection = creator.connectToDatabase();
        System.out.println("connected");
        PreparedStatement stm = connection.prepareStatement("select * from users left  join  mentorspersonals on users.id=mentorspersonals.user_id  where id= ? ");
        stm.setInt(1 ,id);

        ResultSet result = stm.executeQuery();
        System.out.println("query executed");
        Mentor mentor;
        while (result.next()){
            int user_id = result.getInt("id");

            String login = result.getString(("login"));
            System.out.println(login);
            String password = result.getString("password");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNumber = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");



            mentor = new Mentor(user_id, login, password, firstName, lastName, phoneNumber, email, address);
            return mentor ;


        }

        return null;
    }

    private Admin getFullADmin(int id) throws SQLException{
        DBCreator creator = new DBCreator();
        Connection connection = creator.connectToDatabase();
        System.out.println("connected");
        PreparedStatement stm = connection.prepareStatement("select * from users left  join  mentorspersonals on users.id=mentorspersonals.user_id  where id= ? ");
        stm.setInt(1 ,id);

        ResultSet result = stm.executeQuery();
        System.out.println("query executed");
        Admin admin;
        while (result.next()){
            int user_id = result.getInt("id");

            String login = result.getString(("login"));
            System.out.println(login);
            String password = result.getString("password");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNumber = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");



            admin = new Admin(user_id, login, password, firstName, lastName, phoneNumber, email, address);
            return admin ;


        }

        return null;
    }
}
