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

    public void addLevel() {

    }

    public void createNewClass() {

    }
}
