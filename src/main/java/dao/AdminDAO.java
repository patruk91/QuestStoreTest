package dao;

import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator = new DBCreator();

    public void addMentor(User user, Mentor mentor) {
        createUser(user);
        try {
            int userID = getUserIdWithLogin(user);
            mentor.setId(userID);
            createMentor(mentor);
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
            dbCreator.connectToDatabase().close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void createMentor(Mentor mentor){
        String query = "INSERT INTO mentorpersonals(user_id, first_name, last_name, phone_number, email, adress) VALUES(?,?,?,?,?,?);";
        PreparedStatement statement = null;

        try {
            connection = dbCreator.connectToDatabase();

            statement = connection.prepareStatement(query);

            statement.setInt(1, mentor.getId());
            statement.setString(2, mentor.getFirstName());
            statement.setString(3, mentor.getLastName());
            statement.setString(4, mentor.getPhoneNum());
            statement.setString(5, mentor.getEmail());
            statement.setString(6, mentor.getAdress());
            statement.executeUpdate();

            dbCreator.connectToDatabase().close();

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

            return results.getInt("id");



        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Did not find user with this login SLQ Exception");

        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Did not find user with this login");
        }


    }

    public void assignMentorToRoom() {

    }

    public void updateMentor() {

    }

    public Mentor getMentor(int id) throws SQLException {
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select * from mentorspersonals left join classes on " +
                "mentorspersonals.user_id = classes.user_id where mentorspersonals.user_id = ?;");

        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();


        while (result.next()){
            String firstName = result.getString("first_name");
            String lastName= result.getString("last_name");
            String phoneNum = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");
            int classID = result.getInt("class_id");
            Mentor mentor = new Mentor(id, firstName, lastName, phoneNum, email,address, classID);
            return mentor;
        }


        connection.close();

        return null;



    }

    public Mentor getMentor(String firstName, String lastName) throws SQLException {
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM mentorspersonals WHERE first_name = ? AND last_name = ?");

        stm.setString(1, firstName);
        stm.setString(2, lastName);
        ResultSet result = stm.executeQuery();


        int userID = 0;
        if(result.next()){
            userID = result.getInt("user_id");
        }

        stm = connection.prepareStatement("select * from mentorspersonals left join classes on " +
                "mentorspersonals.user_id = classes.user_id where mentorspersonals.user_id = ?;");
        stm.setInt(1, userID);

        result = stm.executeQuery();


        while (result.next()){
            int mentorID = result.getInt("user_id");
            String phoneNum = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");
            int classID = result.getInt("class_id");
            Mentor mentor = new Mentor(mentorID, firstName, lastName, phoneNum, email,address, classID);
            return mentor;
        }


        connection.close();

        return null;
    }

    public List<Codecooler> getCodecoolers(int roomId) throws SQLException{

        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select * from studentpersonals where room_id = ? ");
        stm.setInt(1, roomId);
        ResultSet result = stm.executeQuery();
        List<Codecooler> resultList = new LinkedList<Codecooler>();

        while(result.next()){
            int id = result.getInt("id");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            String phoneNum = result.getString("phone_number");
            String email = result.getString("email");
            String address = result.getString("address");
            Codecooler codecooler = new Codecooler(id, firstName, lastName, phoneNum, email, address, roomId);
            resultList.add(codecooler);
            return resultList;
        }
        return null;

    }

    public void addLevel() {

    }

    public void createNewClass() {

    }
}
