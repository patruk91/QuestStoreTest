package dao;

import model.users.Mentor;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {
            AdminDAO adminDAO = new AdminDAO();

            Mentor mentor = adminDAO.getMentor(5);

            CodecoolerDAO codecoolerDAO = new CodecoolerDAO();


            System.out.println("codecooler 25 coolcoins: " + codecoolerDAO.showWallet(25));

            System.out.println(mentor.getFirstName() + " " + mentor.getLastName());
            System.out.println("mentors roomID: " + mentor.getRoomID());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
