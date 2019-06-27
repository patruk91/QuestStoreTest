package dao;

import model.items.Artifact;
import model.items.Quest;
import model.users.Codecooler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MentorDAO implements IMentorDAO {
    public Codecooler createCodecooler() {
        return null;
    }

    public Quest createNewQuest() {
        return null;
    }

    public void addQuestToAvailable() {

    }

    public void addQuestCategory() {

    }

    public Artifact createArtifact() {
        return null;
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

    public void updateArtifact(String artifactName, int newPrice) {
        DBCreator dbCreator = new DBCreator();
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("update  Artifacts set artifact_price = ? where artifact_name like ?");
            stm.setInt(1, newPrice);
            stm.setString(2, artifactName);
            stm.executeUpdate();
        }catch (SQLException e){
            System.out.println(e);
        }

    }

    public void addArtifactCategory() {

    }

    public void markAchivedQuests() {

    }

    public void markBoughtArtifacts() {

    }

    public int seeStudentWallet(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        while(result.next()){
            int codecoolerCoins = result.getInt("coolcoins");
            return codecoolerCoins;
        }
        return 0;
    }

    public Map<Integer, Integer> seeStudentsWallet(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();
        Map<Integer, Integer> resultmap = new HashMap<Integer, Integer>();

        while(result.next()){
            int codecoolerCoins = result.getInt("coolcoins");
            return null;
        }
        return null;
    }
}
