package dao;

import model.items.Artifact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArtifactDAO {
    Connection connection;
    DBCreator dbCreator;

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
