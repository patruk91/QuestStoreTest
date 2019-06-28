package dao;


import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.SQLException;
import java.util.List;

public interface IAdminDAO{
    void addMentor(User user, Mentor mentor) throws DBException;
    void assignMentorToRoom();
    void updateMentorByID(Mentor mentor) throws DBException;
    void updateMentorByFullName(Mentor mentor) throws DBException;
    Mentor getMentor(int id) throws SQLException, DBException;
    Mentor getMentor(String firstName, String lastName) throws DBException;
    List<Codecooler> getCodecoolers(int roomId) throws DBException;
    void addLevel();
    void createNewClass();
}