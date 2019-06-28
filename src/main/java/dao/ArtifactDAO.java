package dao;

import model.items.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDAO {
    Connection connection;
    DBCreator dbCreator;

    public List<Artifact> seeArtifactsList() throws SQLException{
        List<Artifact> allArtifacts = new ArrayList();

        Connection con = dbCreator.connectToDatabase();
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            resultSet = stmt.executeQuery( "SELECT * FROM artifacts;" );
            while (resultSet.next() ) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("artifact_name");
                String category = resultSet.getString("artifact_category");
                String description = resultSet.getString("artifact_description");
                int price = resultSet.getInt("artifact_price");
                boolean availability = resultSet.getBoolean("artifact_availability");
                Artifact newArtifact = new Artifact(id, name, description, category, price, availability);
                allArtifacts.add(newArtifact);
            }
            resultSet.close();
            stmt.close();
            con.close();
        } catch ( Exception e ) {
            System.out.println(e);
        }
        System.out.println("Operation done successfully");
        System.out.println("all artifact size: " + allArtifacts.size());
        return allArtifacts;
    }


    //todo
    public void addArtifactCategory() {

    }

    public void createArtifact(Artifact artifact) {
        String query = "INSERT INTO Artifacts (artifact_name, artifact_category, artifact_description, artifact_price, artifact_availability)" +
                " VALUES (?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            connection = dbCreator.connectToDatabase();
            statement = connection.prepareStatement(query);

            statement.setString(1, artifact.getName());
            statement.setString(2, artifact.getCategory());
            statement.setString(3, artifact.getDiscription());
            statement.setInt(4, artifact.getPrice());
            statement.setBoolean(5, artifact.isAvaliability());

            statement.executeUpdate();
            connection.close();

        } catch (SQLException e){
            e.printStackTrace();
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

    //todo
    public void markBoughtArtifacts() {

    }
}
