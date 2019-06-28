package dao;


import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.SQLException;
import java.util.Map;

public interface IMentorDAO {

    void addMentor(User user, Mentor mentor) throws DBException;
    void updateMentorByID(Mentor mentor) throws DBException;
    void updateMentorByFullName(Mentor mentor) throws DBException;
    Mentor getMentor(int id) throws SQLException, DBException;
    Mentor getMentor(String firstName, String lastName) throws DBException;
    void assignMentorToRoom();

    int seeStudentWallet(int id) throws SQLException;
    Map<Integer, Integer> seeStudentsWallets() throws SQLException;

}
