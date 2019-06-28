package dao;

import model.items.Quest;

import java.sql.SQLException;
import java.util.List;

public interface IQuestDAO {
    //this interface contains methods to process quests: show, create, update

    List<Quest> seeQuestsList() throws SQLException;
    void createNewQuest(Quest quest);
    void addQuestToAvailable();
    void updateQuestCategory(Quest quest);
    void updateQuest(String questName, int newValue) throws SQLException;
    void markAchivedQuests();
}
