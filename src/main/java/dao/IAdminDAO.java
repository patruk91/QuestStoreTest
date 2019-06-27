package dao;


import model.users.Codecooler;
import model.users.Mentor;

import java.sql.SQLException;
import java.util.List;

public interface IAdminDAO{
    void addMentor();
    void assignMentorToRoom();
    void updateMentor();
    Mentor getMentor(int id) throws SQLException;
    Mentor getMentor(String firstName, String lastName) throws SQLException;
    List<Codecooler> getCodecoolers(int roomId) throws SQLException;
    void addLevel();
    void createNewClass();
}