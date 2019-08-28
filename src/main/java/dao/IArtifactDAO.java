package dao;

import model.items.Artifact;
import java.util.List;

public interface IArtifactDAO {
    //this interface contains methods to process artifacts (show, create, update)

    List<Artifact> getArtifactsList() throws  DBException;
    List<Artifact> getBoughtArtifactsList(int id) throws DBException;
    void addArtifactCategory();
    void createArtifact(Artifact artifact) throws DBException;
    void updateArtifact(int artifactId, int newPrice)throws DBException;

    void markBoughtArtifacts(int studentId, int questId)  throws DBException;
    Artifact getArtifact(int id) throws DBException;
    List<Artifact> getUsersArtifacts(int id) throws DBException;
    void addUserArtifact(int userID, int artifactID) throws DBException;
}
