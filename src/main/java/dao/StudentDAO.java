package dao;

import model.users.Student;
import model.users.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class StudentDAO implements IStudentDAO {
    //this class contains methods to create and update codecooler
    // and show his level

    //TODO Get DBCreator object to private filed of WallDao class instead of creating it in every method

    Connection connection;
    DBCreator dbCreator;

    public StudentDAO() {
        dbCreator = new DBCreator();
    }


    public void createCodecooler(User user, Student student) {
        createUser(user);
        try {
            int userID = getUserIdWithLogin(user);
            student.setId(userID);
            createStudent(student);
        } catch(DBException e) {
            e.printStackTrace();
            System.out.println("Failed to fetch user with this login");
        }
    }


    public List<Student> getCodecoolersFromRoom(int roomId) throws DBException{

        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from studentpersonals where room_id = ? ");
            stm.setInt(1, roomId);
            ResultSet result = stm.executeQuery();
            List<Student> resultList = new LinkedList<Student>();

            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                Student student = new Student(id, firstName, lastName, phoneNum, email, address, roomId);
                resultList.add(student);
                return resultList;
            }
            return null;

        } catch (SQLException e) {
            throw new DBException("SQLException occured in getCodecoolers(int roomId))");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getCodecoolers(int roomId)");
        }
    }


    public int showLevelOfExperience(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select experience_points from studentpersonals where user_id = ? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        while(result.next()){
            int experiencePoints = result.getInt("experience_points");
            return experiencePoints;
        }
        return 0;
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

    private void createStudent(Student student){
        String query = "INSERT INTO studentpersonals(address, class_id, coolcoins, email, experience_points," +
                " first_name, last_name, phone_number, user_id) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;

        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);
            statement.setString(1, student.getAdress());
            statement.setInt(2, student.getRoomID());
            statement.setInt(3, student.getAmmountOfCoins());
            statement.setString(4, student.getEmail());
            statement.setInt(5, student.getLvlOfExp());
            statement.setString(6, student.getFirstName());
            statement.setString(7, student.getLastName());
            statement.setString(8, student.getPhoneNum());
            statement.setInt(9, student.getId());

            statement.executeUpdate();

            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private int getUserIdWithLogin(User user) throws DBException {
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
            throw new DBException("Did not find user with this login SLQ Exception");

        } catch (Exception e){
            e.printStackTrace();
            throw new DBException("Did not find user with this login");
        }

    }


}
