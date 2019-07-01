package dao;

import model.items.Artifact;
import java.sql.SQLException;
import java.util.List;

public interface IArtifactDAO {
    //this interface contains methods to process artifacts (show, create, update)

    List<Artifact> showAllArtifacts() throws  SQLException;
    List<Artifact> showBoughtArtifacts(int id) throws SQLException;
    void addArtifactCategory();
    void createArtifact(Artifact artifact);
    void updateArtifact(String artifactName, int newPrice)throws SQLException;

    //I am not shure in which interface this method should by but it's not implemented yet
    void markBoughtArtifacts();

}
