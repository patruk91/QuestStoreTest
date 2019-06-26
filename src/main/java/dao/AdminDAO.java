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

    public Mentor getMentor(String firstName, String lastName) {
        return null;
    }

    public void addLevel() {

    }

    public void createNewClass() {

    }
}
