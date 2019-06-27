package dao;

import model.users.Mentor;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {
            AdminDAO adminDAO = new AdminDAO();

            Mentor mentor = adminDAO.getMentor(5);

            CodecoolerDAO codecoolerDAO = new CodecoolerDAO();

            codecoolerDAO.buyArtifact(31,2);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
