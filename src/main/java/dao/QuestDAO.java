package dao;

import model.items.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestDAO {
    Connection connection;
    DBCreator dbCreator;


    public void createNewQuest(Quest quest) {
        String query = "INSERT INTO quests(quest_award, quest_category, quest_description, quest_name) VALUES (?,?,?,?)";
        PreparedStatement statement = null;
        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);


            statement.setInt(1, quest.getReward());
            statement.setString(2, quest.getCategory());
            statement.setString(3, quest.getDiscription());
            statement.setString(4, quest.getName());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //todo
    public void addQuestToAvailable() {

    }

    public void updateQuestCategory(Quest quest) {
        // Update based on quest id
        String query = "UPDATE quests SET quest_category = ? WHERE id = ?";
        PreparedStatement statement = null;
        try{
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);

            statement.setString(1, quest.getCategory());
            statement.setInt(2, quest.getId());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void updateQuest(String questName, int newValue) {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("update  Quests set quest_award = ? where quest_name like ?");
            stm.setInt(1, newValue);
            stm.setString(2, questName);
            stm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }
    }


    //todo
    public void markAchivedQuests() {

    }

}
