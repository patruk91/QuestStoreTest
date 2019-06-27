package dao;

import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    DBCreator dbCreator = new DBCreator();

    public List<Artifact> seeArtifactsList() throws SQLException{
        List<Artifact> allArtifacts = new ArrayList();

        Connection con = dbCreator.connectToDatabase();
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            resultSet = stmt.executeQuery( "SELECT * FROM artifacts;" );
            while (resultSet.next() ) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("artifact_name");
                String category = resultSet.getString("artifact_category");
                String description = resultSet.getString("artifact_description");
                int price = resultSet.getInt("artifact_price");
                boolean availability = resultSet.getBoolean("artifact_availability");
                Artifact newArtifact = new Artifact(id, name, description, category, price, availability);
                allArtifacts.add(newArtifact);
            }
            resultSet.close();
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        System.out.println("Operation done successfully");
        System.out.println("all artifact size: " + allArtifacts.size());
        return allArtifacts;
    }

    public List<Quest> seeQuestsList() throws SQLException {
        List<Quest> allQuests = new ArrayList();

        Connection con = dbCreator.connectToDatabase();
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            resultSet = stmt.executeQuery( "SELECT * FROM quests;" );
            while (resultSet.next() ) {
                int id = resultSet.getInt("id");
                String questName = resultSet.getString("quest_name");
                String questCategory = resultSet.getString("quest_category");
                String questDescription = resultSet.getString("quest_description");
                int questAward = resultSet.getInt("quest_award");
                Quest newQuest = new Quest(id, questName, questDescription, questCategory, questAward);
                allQuests.add(newQuest);
            }
            resultSet.close();
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        System.out.println("Operation done successfully");
        System.out.println("all quests size: " + allQuests.size());
        return allQuests;
    }
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
        }
        return null;
    }

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
}
