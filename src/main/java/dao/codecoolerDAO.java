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

    public List<Artifact> showBoughtArtifacts(int userId) throws SQLException{

        Connection con = dbCreator.connectToDatabase();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Artifact> boughtArtifacts = new ArrayList<>();


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

    public void buyArtifact() {

    }

    public void buyArtifactWithTeammates() {

    }

    public void showLevelOfExperience() {

    }
}
