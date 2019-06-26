package dao;


import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;

import java.util.Map;

public interface IMentorDAO {

    Codecooler createCodecooler();
    Quest createNewQuest();
    void addQuestToAvailable();
    void addQuestCategory();
    Artifact createArtifact();
    void updateQuest();
    void updateArtifact();
    void addArtifactCategory();
    void markAchivedQuests();
    void markBoughtArtifacts();
    Map<Integer, Integer> seeStudentWallet();

}
