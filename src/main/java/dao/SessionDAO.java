package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAO {
    DBCreator dbCreator = new DBCreator();

    public int getUserIdBySession(int sessionId) throws  DBException {
        int userId=0;
        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("select * from sessions where sessionid = ?");

            stm.setInt(1, sessionId);

            ResultSet result = stm.executeQuery();
            connection.close();


            while (result.next()) {
                userId = result.getInt("userid");
            }

            return userId;
        } catch (SQLException e) {
            throw new DBException("SQLException occured in getMentor(int id)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getMentor(int id)");
        }
    }

    public void addSession(int sessionId, int userId) throws DBException {

        try {
            Connection connection = dbCreator.connectToDatabase();
            PreparedStatement stm = connection.prepareStatement("insert into sessions(sessionid, userid) values (?, ?)");

            stm.setInt(1, sessionId);
            stm.setInt(2, userId);

            stm.executeUpdate();
            connection.close();

        } catch (SQLException e) {
            throw new DBException("SQLException occured in getMentor(int id)");

        } catch (Exception e){
            throw new DBException("Unidentified exception occured in getMentor(int id)");
        }
    }

}
