package dao;


import model.items.Artifact;

import java.sql.SQLException;

public interface IArtifactDAO {
    void addArtifactCategory();
    void createArtifact(Artifact artifact);
    void updateArtifact(String artifactName, int newPrice)throws SQLException;
    void markBoughtArtifacts();
}
