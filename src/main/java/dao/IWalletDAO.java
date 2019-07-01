package dao;

import java.sql.SQLException;
import java.util.Map;

public interface IWalletDAO {
//    this interface contains methods to see, process wallet and purchase artifacts

    int showWallet(int id) throws SQLException;
    void buyArtifact(int userID, int artifactID) throws SQLException;
    void buyArtifactWithTeammates();
    Map<Integer, Integer> seeStudentsWallets() throws SQLException;

    //redundant method
    int seeStudentWallet(int id) throws SQLException;
}
