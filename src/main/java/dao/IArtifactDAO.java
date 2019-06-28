package dao;


import model.items.Artifact;

import java.sql.SQLException;
import java.util.List;

public interface IArtifactDAO {
    void addArtifactCategory();
    void createArtifact(Artifact artifact);
    void updateArtifact(String artifactName, int newPrice)throws SQLException;
    void markBoughtArtifacts();
    List<Artifact> seeArtifactsList() throws  SQLException;
}
