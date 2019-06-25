package model.users;


import java.util.List;

public class Admin extends User {
    List<Mentor> mentorList;
    List<Codecooler> codecoolerList;
    List<Integer> listOfLevels;

    public Admin(int id, String login, String password, String firstName, String lastName, int phoneNum, String adress, List<Mentor> mentorList, List<Codecooler> codecoolerList, List<Integer> listOfLevels) {
        super(id, login, password, firstName, lastName, phoneNum, adress);
        this.mentorList = mentorList;
        this.codecoolerList = codecoolerList;
        this.listOfLevels = listOfLevels;
    }
}
