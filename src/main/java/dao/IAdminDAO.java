package dao;


import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.SQLException;
import java.util.List;

public interface IAdminDAO{
    

    List<Codecooler> getCodecoolers(int roomId) throws DBException;
    void addLevel();
    void createNewClass();
}