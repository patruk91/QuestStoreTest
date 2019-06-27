package dao;


import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;
import model.users.User;

import java.sql.SQLException;
import java.util.Map;

public interface IMentorDAO {

    void createCodecooler(User user, Codecooler codecooler);
    Quest createNewQuest();
    void addQuestToAvailable();
    void addQuestCategory();
    Artifact createArtifact();
    void updateQuest();
    void updateArtifact();
    void addArtifactCategory();
    void markAchivedQuests();
    void markBoughtArtifacts();
    int seeStudentWallet(int id) throws SQLException;
    Map<Integer, Integer> seeStudentsWallets() throws SQLException;

}
