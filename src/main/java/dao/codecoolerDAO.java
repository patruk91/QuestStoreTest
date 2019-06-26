package dao;

import model.items.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class codecoolerDAO implements ICodecoolerDAO {
    DBCreator dbCreator = new DBCreator();

    public int showWallet(int id) throws SQLException {
        Connection con = dbCreator.connectToDatabase();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int ammountOfCoins = 0;

        try {
            stmt = con.prepareStatement("SELECT coolcoins FROM studentpersonals WHERE user_id = ?;");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while(rs.next()){
                ammountOfCoins = rs.getInt("coolcoins");
            }
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        return ammountOfCoins;
    }

//    public List<Artifact> showBoughtAtifacts(int id) throws SQLException{
//        Connection con = dbCreator.connectToDatabase();
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        List<Artifact> boughtArtifacts = new ArrayList<>();
//
//
//        try {
//            stmt = con.prepareStatement("SELECT  FROM studentpersonals WHERE user_id = ?;");
//            stmt.setInt(1, id);
//            rs = stmt.executeQuery();
//
//            while(rs.next()){
//                = rs.getInt("coolcoins");
//            }
//            stmt.close();
//            con.close();
//        } catch ( Exception e ) {
//            System.out.println(e);
//        }
//        return boughtArtifacts;
//    }

    public void buyArtifact() {

    }

    public void buyArtifactWithTeammates() {

    }

    public void showLevelOfExperience() {

    }
}
