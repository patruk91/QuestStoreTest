package dao;

import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MentorDAO implements IMentorDAO {
    Connection connection;
    DBCreator dbCreator;

    public MentorDAO() {
        dbCreator = new DBCreator();

    }

    public void createCodecooler(User user, Codecooler codecooler) {
        createUser(user);
        try {
            int userID = getUserIdWithLogin(user);
            codecooler.setId(userID);
            createStudent(codecooler);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Failed to fetch user with this login");
        }

    }

    private void createUser(User user){
        String query = "INSERT INTO users(login, password, usertype) VALUES (?,?,?)";
        PreparedStatement statement = null;

        try {

            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);


            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());


            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void createStudent(Codecooler codecooler){
        String query = "INSERT INTO studentpersonals(address, class_id, coolcoins, email, experience_points," +
                " first_name, last_name, phone_number, user_id) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;

        try {
            connection = dbCreator.connectToDatabase();

            statement = connection.prepareStatement(query);


            statement.setString(1, codecooler.getAdress());
            statement.setInt(2, codecooler.getRoomID());
            statement.setInt(3, codecooler.getAmmountOfCoins());
            statement.setString(4, codecooler.getEmail());
            statement.setInt(5, codecooler.getLvlOfExp());
            statement.setString(6, codecooler.getFirstName());
            statement.setString(7, codecooler.getLastName());
            statement.setString(8, codecooler.getPhoneNum());
            statement.setInt(9, codecooler.getId());




            statement.executeUpdate();

            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private int getUserIdWithLogin(User user) throws Exception {
        ResultSet results = null;
        PreparedStatement statement = null;
        String query = "SELECT * FROM users WHERE login = ?;";
        try {

            connection = dbCreator.connectToDatabase();


            statement = connection.prepareStatement(query);

            statement.setString(1, user.getLogin());

            results = statement.executeQuery();

            results.next();

            int id = results.getInt("id");

            connection.close();

            return id;



        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Did not find user with this login SLQ Exception");

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Did not find user with this login");
        }


    }


    public void createNewQuest(Quest quest) {
        String query = "INSERT INTO quests(quest_award, quest_category, quest_description, quest_name) VALUES (?,?,?,?)";
        PreparedStatement statement = null;
        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);


            statement.setInt(1, quest.getReward());
            statement.setString(2, quest.getCategory());
            statement.setString(3, quest.getDiscription());
            statement.setString(4, quest.getName());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addQuestToAvailable() {

    }

    public void updateQuestCategory(Quest quest) {
        // Update based on quest id
        String query = "UPDATE quests SET quest_category = ? WHERE id = ?";
        PreparedStatement statement = null;
        try{
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);

            statement.setString(1, quest.getCategory());
            statement.setInt(2, quest.getId());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Artifact createArtifact() {
        return null;
    }

    public void updateQuest() {

    }

    public void updateArtifact() {

    }

    public void addArtifactCategory() {

    }

    public void markAchivedQuests() {

    }

    public void markBoughtArtifacts() {

    }

    public int seeStudentWallet(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        while(result.next()){
            int codecoolerCoins = result.getInt("coolcoins");
            return codecoolerCoins;
        }
        return 0;
    }

    public Map<Integer, Integer> seeStudentsWallet(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();
        Map<Integer, Integer> resultmap = new HashMap<Integer, Integer>();

        while(result.next()){
            int codecoolerCoins = result.getInt("coolcoins");
            return null;
        }
        return null;
    }
}
