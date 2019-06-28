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






    public List<Codecooler> getCodecoolers(int roomId) throws DBException{

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
                return resultList;
            }
            return null;

        } catch (SQLException e) {
            throw new DBException("SQLException occured in getCodecoolers(int roomId))");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getCodecoolers(int roomId)");
        }

    }
    //todo
    public void addLevel() {

    }
    //todo
    public void createNewClass() {

    }
}
