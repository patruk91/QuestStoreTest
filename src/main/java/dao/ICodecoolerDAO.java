package  dao;

import java.sql.SQLException;
import model.users.Codecooler;
import model.users.User;
import java.util.List;

public interface ICodecoolerDAO {
    //this interface contains methods to create and update codecooler
    // and show his level

    void createCodecooler(User user, Codecooler codecooler);
    List<Codecooler> getCodecoolersFromRoom(int roomId) throws DBException; //what about this exception?
    int showLevelOfExperience(int id) throws SQLException;
}



