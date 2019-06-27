package  dao;


import java.sql.SQLException;
import model.items.Artifact;
import java.util.List;

public interface ICodecoolerDAO {
    int showWallet(int id) throws SQLException;

    void buyArtifact(int userID, int artifactID) throws SQLException;

    List<Artifact> showBoughtArtifacts(int id) throws SQLException;

    void buyArtifactWithTeammates();

    int showLevelOfExperience(int id) throws SQLException;
}



