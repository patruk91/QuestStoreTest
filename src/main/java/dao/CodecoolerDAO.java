package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CodecoolerDAO implements ICodecoolerDAO {

    public int showWallet(int id) throws SQLException {
        DBCreator dbCreator = new DBCreator();
        Connection connection = dbCreator.connectToDatabase();
        PreparedStatement stm = connection.prepareStatement("select coolcoins from studentspersonals where user_id = ? ");
        stm.setInt(1, id);
        stm.executeQuery();
        return 0;
    }

    public void buyArtifact() {

    }

    public void buyArtifactWithTeammates() {

    }

    public void showLevelOfExperience() {

    }
}
