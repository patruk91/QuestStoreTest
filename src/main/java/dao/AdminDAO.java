package dao;

import model.users.Mentor;

import java.sql.Connection;
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
