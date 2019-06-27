package dao;

import model.items.Artifact;
import org.postgresql.core.SqlCommand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CodecoolerDAO implements ICodecoolerDAO {

    public int showWallet(int id) throws SQLException {
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

    public void buyArtifact(int userID, int artifactID)throws SQLException {
        boolean checkIFcanBeBought = checkIfTransactionPossible(userID, artifactID);

        while (checkIFcanBeBought){
            updateMoneyAmount(userID,artifactID);
            addArtifactToItems(userID, artifactID);
        }

    }

    public List<Artifact> showBoughtArtifacts(int userId) throws SQLException{
        DBCreator dbCreator = new DBCreator();
        Connection con = dbCreator.connectToDatabase();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Artifact> boughtArtifacts = new ArrayList();


        try {
            stmt = con.prepareStatement("SELECT art.id, artifact_name, artifact_category, artifact_description, artifact_price\n" +
                    "FROM users_artifacts usersArt INNER JOIN artifacts art\n" +
                    "ON usersArt.artifact_id = art.id\n" +
                    "WHERE user_id = ?;");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("artifact_name");
                String discription = rs.getString("artifact_description");
                String category = rs.getString("artifact_category");
                int price = rs.getInt("artifact_price");
                Artifact nextArtifact = new Artifact(id, name, category, price, discription);

                boughtArtifacts.add(nextArtifact);
            }
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        return boughtArtifacts;
    }

    public void buyArtifactWithTeammates() {

    }

    public int showLevelOfExperience(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select experience_points from studentpersonals where user_id = ? ");
        stm.setInt(1, id);
        ResultSet result = stm.executeQuery();

        while(result.next()){
            int experiencePoints = result.getInt("experience_points");
            return experiencePoints;
        }
        return 0;
    }

    private void updateMoneyAmount(int userID, int artifactID) throws SQLException{

        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();

        int coolcoins = getCoolCoins(userID);
        int artifactCost = getArtifactCost(artifactID);
        int restCoins = coolcoins - artifactCost;
        System.out.println(restCoins);
        PreparedStatement stm = connection.prepareStatement("update  studentpersonals set coolcoins = ? where user_id = ?");
        stm.setInt(1, restCoins);
        stm.setInt(2, userID);
        stm.executeUpdate();




    }

    //todo
    private void addArtifactToItems(int userID, int artifactID) throws SQLException{
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();


        PreparedStatement stm = connection.prepareStatement("insert into  users_artifacts (user_id, artifact_id ) values  (?,?)");
        stm.setInt(1, userID);
        stm.setInt(2, artifactID);
        stm.executeUpdate();
    }

    private boolean checkIfTransactionPossible(int userID, int artifactID) throws SQLException {

        int coolcoins = getCoolCoins(userID);

        int artifactCost = getArtifactCost(artifactID);


        System.out.println(coolcoins >= artifactCost);
        if (coolcoins >= artifactCost){return true;}
        return false;
    }


    private int getCoolCoins(int userID) throws SQLException{
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
        stm.setInt(1, userID);
        ResultSet result = stm.executeQuery();
        int coolcoins = 0;
        if (result.next()){
            coolcoins = result.getInt("coolcoins");
        }
        System.out.println(coolcoins);
        connection.close();
        return coolcoins;
    }
    private int getArtifactCost(int artifactID) throws SQLException{
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select artifact_price from artifacts where  id = ? ");
        stm = connection.prepareStatement("select artifact_price from artifacts where  id = ? ");
        stm.setInt(1, artifactID);
        ResultSet result = stm.executeQuery();
        int artifactCost = 0;
        if (result.next()){
            artifactCost = result.getInt("artifact_price");
        }
        System.out.println(artifactCost);
        connection.close();
        return artifactCost;
    }
}
