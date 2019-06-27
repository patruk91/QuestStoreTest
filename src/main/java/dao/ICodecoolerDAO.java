package  dao;


import java.sql.SQLException;

public interface ICodecoolerDAO{
    int showWallet(int id) throws SQLException;
    void buyArtifact();
    void buyArtifactWithTeammates();
    int showLevelOfExperience(int id) throws SQLException;
}