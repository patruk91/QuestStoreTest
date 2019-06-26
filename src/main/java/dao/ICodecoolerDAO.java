package  dao;


import model.items.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface ICodecoolerDAO{
    int showWallet(int id) throws SQLException;
    List<Artifact> showBoughtAtifacts(int id) throws SQLException;
    void buyArtifact();
    void buyArtifactWithTeammates();
    void showLevelOfExperience();
}