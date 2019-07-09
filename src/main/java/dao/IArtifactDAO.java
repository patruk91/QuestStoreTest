package dao;

import model.items.Artifact;
import java.sql.SQLException;
import java.util.List;

public interface IArtifactDAO {
    //this interface contains methods to process artifacts (show, create, update)

    List<Artifact> getArtifactsList() throws  SQLException, DBException;
    List<Artifact> getBoughtArtifactsList(int id) throws SQLException, DBException;
    void addArtifactCategory();
    void createArtifact(Artifact artifact) throws DBException;
    void updateArtifact(String artifactName, int newPrice)throws SQLException, DBException;

    //I am not shure in which interface this method should by but it's not implemented yet
    void markBoughtArtifacts();

}
