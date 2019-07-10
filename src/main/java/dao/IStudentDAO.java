package  dao;

import java.sql.SQLException;
import model.users.Student;
import model.users.User;
import java.util.List;

public interface IStudentDAO {
    //this interface contains methods to create and update codecooler
    // and show his level

    void createCodecooler(User user, Student student);
    List<Student> getCodecoolersFromRoom(int roomId) throws DBException; //what about this exception?
    int showLevelOfExperience(int id) throws SQLException;
}



