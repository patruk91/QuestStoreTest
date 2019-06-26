package dao;

import model.items.Artifact;
import model.items.Quest;
import model.users.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO{
    DBCreator dbCreator = new DBCreator();

    public ArrayList<Artifact> seeArtifactsList() {
        return null;
    }

    public List<Quest> seeQuestsList() throws SQLException {
        List<Quest> allQuests = new ArrayList<>();

        Connection con = dbCreator.connectToDatabase();
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            resultSet = stmt.executeQuery( "SELECT * FROM quests;" );
            while (resultSet.next() ) {
                int id = resultSet.getInt("id");
                String questName = resultSet.getString("quest_name");
                String questCategory = resultSet.getString("quest_category");
                String questDescription = resultSet.getString("quest_description");
                int questAward = resultSet.getInt("quest_award");
                Quest newQuest = new Quest(id, questName, questDescription, questCategory, questAward);
                allQuests.add(newQuest);
            }
            resultSet.close();
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        System.out.println("Operation done successfully");
        System.out.println("all quests size: " + allQuests.size());
        return allQuests;
    }

    public User seeProfile() {
        return null;
    }

    public void updateMyProfile() {

    }
}
