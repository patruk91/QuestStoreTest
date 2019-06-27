package  dao;


<<<<<<< HEAD
import java.sql.SQLException;

public interface ICodecoolerDAO{
    int showWallet(int id) throws SQLException;
    void buyArtifact(int userID, int artifactID) throws SQLException;
=======
import model.items.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface ICodecoolerDAO{
    int showWallet(int id) throws SQLException;
    List<Artifact> showBoughtArtifacts(int id) throws SQLException;
    void buyArtifact();
>>>>>>> showWallet
    void buyArtifactWithTeammates();

    int showLevelOfExperience(int id) throws SQLException;

}

