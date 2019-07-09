package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class WalletDAO implements IWalletDAO {
    //    this class contains methods to process wallet and puchase artifacts

    //TODO Get DBCreator object to private filed of WallDao class instead of creating it in every method
    DBCreator dbCreator;

    WalletDAO() {
        dbCreator = new DBCreator();
    }

    public int showWallet(int id) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                int codecoolerCoins = result.getInt("coolcoins");
                return codecoolerCoins;
            }
            throw new DBException("Didnt find codecooler with id: " + id);
        } catch (SQLException e) {
            throw new DBException("SQLException occured in showWallet()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occured in showWallet()");
        }
    }

    public Map<Integer, Integer> seeStudentsWallets() throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select user_id, experience_points, coolcoins from studentpersonals ");
            ResultSet result = stm.executeQuery();
            Map<Integer, Integer> resultmap = new HashMap<Integer, Integer>();

            while (result.next()) {
                int codecoolerID = result.getInt("user_id");
                int codecoolerExperiencePoints = result.getInt("experience_points");
                int codecoolerCoins = result.getInt("coolcoins");
                int balance = codecoolerExperiencePoints - codecoolerCoins;
                resultmap.put(codecoolerID, balance);
                return resultmap;
            }
            throw new DBException("No students found");
        } catch (SQLException e) {
            throw new DBException("SQLException occured in seeStudentsWallet()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occured in seeStudentsWallet()");
        }
    }

    /*redundant method
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
    }*/

    public void buyArtifact(int userID, int artifactID) throws DBException {
        boolean checkIFcanBeBought = checkIfTransactionPossible(userID, artifactID);
        if (checkIFcanBeBought) {
            updateMoneyAmount(userID, artifactID);
            addArtifactToItems(userID, artifactID);
        }
    }


    //todo
    public void buyArtifactWithTeammates() {
    }

    private void updateMoneyAmount(int userID, int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();

            //REFACTOR: getCoolCoins(user id) is the same as showWallet(use id)
            //old code:
            //int coolcoins = getCoolCoins(userID);
            //new code:
            int coolcoins = showWallet(userID);
            //refactor end

            int artifactCost = getArtifactCost(artifactID);
            int restCoins = coolcoins - artifactCost;
            System.out.println(restCoins);
            PreparedStatement stm = connection.prepareStatement("update  studentpersonals set coolcoins = ? where user_id = ?");
            stm.setInt(1, restCoins);
            stm.setInt(2, userID);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occured in updateMoneyAmount()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception updateMoneyAmount()");
        }

    }

    //todo
    private void addArtifactToItems(int userID, int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("insert into  users_artifacts (user_id, artifact_id ) values  (?,?)");
            stm.setInt(1, userID);
            stm.setInt(2, artifactID);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("SQLException occured in addArtifactToItems()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occured in addArtifactToItems()");
        }
    }

    private boolean checkIfTransactionPossible(int userID, int artifactID) throws DBException {
        /*refactor:
        old code
        int coolcoins = getCoolCoins(userID);
        new code*/
        int coolcoins = showWallet(userID);
        int artifactCost = getArtifactCost(artifactID);
        System.out.println(coolcoins >= artifactCost);
        if (coolcoins >= artifactCost) {
            return true;
        }
        return false;
    }

    //TODO repeating method - to remove
//    private int getCoolCoins(int userID) throws SQLException{
//        DBCreator dbCreator = new DBCreator();
//        Connection connection = dbCreator.connectToDatabase();
//        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentpersonals where user_id = ? ");
//        stm.setInt(1, userID);
//        ResultSet result = stm.executeQuery();
//        int coolcoins = 0;
//        if (result.next()){
//            coolcoins = result.getInt("coolcoins");
//        }
//        System.out.println("coolcoins avalible" +coolcoins);
//        connection.close();
//        return coolcoins;
//    }

    private int getArtifactCost(int artifactID) throws DBException {
        try {
            DBCreator dbCreator = new DBCreator();
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select artifact_price from artifacts where  id = ? ");
            stm = connection.prepareStatement("select artifact_price from artifacts where  id = ? ");
            stm.setInt(1, artifactID);
            ResultSet result = stm.executeQuery();
            int artifactCost = 0;
            if (result.next()) {
                artifactCost = result.getInt("artifact_price");
            }
            System.out.println("artifact cost" + artifactCost);
            connection.close();
            return artifactCost;
        } catch (SQLException e) {
            throw new DBException("SQLException occured in getArtifactCost()");
        } catch (Exception e) {
            throw new DBException("Unidentified exception occured in getArtifactCost()");
        }
    }

}
