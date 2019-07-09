package  dao;

import java.sql.SQLException;
import model.users.Codecooler;
import model.users.User;
import java.util.List;

public interface ICodecoolerDAO {
    //this interface contains methods to create and update codecooler
    // and show his level

    void createCodecooler(User user, Codecooler codecooler) throws DBException;
    List<Codecooler> getCodecoolersListFromRoom(int roomId) throws DBException; //what about this exception?
    int getExperiencePoints(int id) throws SQLException, DBException;
}



