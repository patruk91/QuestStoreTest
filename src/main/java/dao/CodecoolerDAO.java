package dao;

import model.users.Codecooler;
import model.users.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class CodecoolerDAO implements ICodecoolerDAO {
    //this class contains methods to create and update codecooler
    // and show his level

    //TODO Get DBCreator object to private filed of WallDao class instead of creating it in every method

    private Connection connection;
    private DBCreator dbCreator;

    public CodecoolerDAO() {
        dbCreator = new DBCreator();
    }


    public void createCodecooler(User user, Codecooler codecooler) throws  DBException {
        createUser(user);
        int userID = getUserIdWithLogin(user);
        codecooler.setId(userID);
        createStudent(codecooler);

    }


    public List<Codecooler> getCodecoolersListFromRoom(int roomId) throws DBException{

        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from studentpersonals where room_id = ? ");
            stm.setInt(1, roomId);
            ResultSet result = stm.executeQuery();
            List<Codecooler> resultList = new LinkedList<Codecooler>();

            while (result.next()) {
                int id = result.getInt("id");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String phoneNum = result.getString("phone_number");
                String email = result.getString("email");
                String address = result.getString("address");
                Codecooler codecooler = new Codecooler(id, firstName, lastName, phoneNum, email, address, roomId);
                resultList.add(codecooler);
            }

            return resultList;


        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getCodecoolers(int roomId))");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in getCodecoolers(int roomId)");
        }
    }


    public int getExperiencePoints(int id) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select experience_points from studentpersonals where user_id = ? ");
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getInt("experience_points");
            }

            throw new DBException("No entry with id: " + id);

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in getExperiencePoints()");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in getExperiencePoints()");
        }
    }


    private void createUser(User user) throws DBException {
        String query = "INSERT INTO users(login, password, usertype) VALUES (?,?,?)";

        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserType());
            statement.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createUser()");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in createUser()");
        }

    }

    private void createStudent(Codecooler codecooler) throws DBException {
        String query = "INSERT INTO studentpersonals(address, class_id, coolcoins, email, experience_points," +
                " first_name, last_name, phone_number, user_id) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, codecooler.getAddress());
            statement.setInt(2, codecooler.getRoomID());
            statement.setInt(3, codecooler.getAmountOfCoins());
            statement.setString(4, codecooler.getEmail());
            statement.setInt(5, codecooler.getLvlOfExp());
            statement.setString(6, codecooler.getFirstName());
            statement.setString(7, codecooler.getLastName());
            statement.setString(8, codecooler.getPhoneNum());
            statement.setInt(9, codecooler.getId());

            statement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occurred in createStudent()");

        } catch (Exception e){
            throw new DBException("Unidentified exception occurred in createStudent()");
        }

    }

    private int getUserIdWithLogin(User user) throws DBException {
        String query = "SELECT * FROM users WHERE login = ?;";
        try {

            connection = dbCreator.connectToDatabase();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            ResultSet results = statement.executeQuery();
            results.next();
            int id = results.getInt("id");
            connection.close();
            return id;


        } catch (SQLException e) {
            throw new DBException("Did not find user with this login SLQ Exception");

        } catch (Exception e){
            throw new DBException("Did not find user with this login");
        }

    }


}
