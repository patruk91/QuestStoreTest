package dao;


import model.users.Mentor;

import java.sql.SQLException;

public interface IAdminDAO{
    void addMentor();
    void assignMentorToRoom();
    void updateMentor();
    Mentor getMentor(int id) throws SQLException;
    Mentor getMentor(String firstName, String lastName) throws SQLException;
    void addLevel();
    void createNewClass();
}