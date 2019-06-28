package dao;

import model.items.Quest;

import java.sql.SQLException;

public interface IQuestDAO {
    void createNewQuest(Quest quest);
    void addQuestToAvailable();
    void updateQuestCategory(Quest quest);
    void updateQuest(String questName, int newValue) throws SQLException;
    void markAchivedQuests();
}
