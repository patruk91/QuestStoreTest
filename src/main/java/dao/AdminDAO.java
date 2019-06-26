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

        String firstName;
        String lastName;
        String phoneNum;
        String email;
        String address;
        int classID;

        Mentor mentor;

        while (result.next()){
            firstName = result.getString("first_name");
            lastName = result.getString("last_name");
            phoneNum = result.getString("phone_number");
            email = result.getString("email");
            address = result.getString("address");
            classID = result.getInt("class_id");
            mentor = new Mentor(id, firstName, lastName, phoneNum, email,address, classID);
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
