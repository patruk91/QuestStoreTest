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

    public void updateQuest() {

    }

    public void updateArtifact() {

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
