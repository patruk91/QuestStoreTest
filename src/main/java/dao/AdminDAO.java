package dao;

import model.users.Mentor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO implements IAdminDAO {
    DBCreator dbCreator = new DBCreator();

    public void addMentor() {

    }

    public void assignMentorToRoom() {

    }

    public void updateMentor() {

    }

    public Mentor getMentor(int id) throws SQLException {
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("SELECT * FROM mentorspersonals , classes WHERE user_id = ?");

        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        String firstName = result.getString("first_name");
        String lastName = result.getString("last_name");
        int phoneNum = result.getInt("phone_number");
        String address = result.getString("address");


        Mentor mentor = new Mentor(id,5);

        connection.close();
        return mentor;
    }

    public Mentor getMentor(String firstName, String lastName) {
        return null;
    }

    public void addLevel() {

    }

    public void createNewClass() {

    }
}
