package dao;

import model.users.Mentor;

import java.sql.SQLException;

public class MainDBCreator {

    public static void main(String[] args) {
        try {
            AdminDAO adminDAO = new AdminDAO();

            Mentor mentor = adminDAO.getMentor(5);






            System.out.println(mentor.getFirstName() + " " + mentor.getLastName());
            System.out.println("mentors roomID: " + mentor.getRoomID());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
