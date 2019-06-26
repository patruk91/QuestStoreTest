package model.users;


import java.util.List;

public class Admin extends User {
    private List<Mentor> mentorList;
    private List<Codecooler> codecoolerList;
    private List<Integer> listOfLevels;

    public Admin(int id, String login, String password, String firstName, String lastName, String phoneNum, String email, String adress, String userType, List<Mentor> mentorList, List<Codecooler> codecoolerList, List<Integer> listOfLevels) {
        super(id, login, password, firstName, lastName, phoneNum, email, adress, userType);
        this.mentorList = mentorList;
        this.codecoolerList = codecoolerList;
        this.listOfLevels = listOfLevels;
    }

    public Admin(int id, List<Mentor> mentorList, List<Codecooler> codecoolerList, List<Integer> listOfLevels) {
        setId(id);
        this.mentorList = mentorList;
        this.codecoolerList = codecoolerList;
        this.listOfLevels = listOfLevels;
    }

    public List<Mentor> getMentorList() {
        return mentorList;
    }

    public List<Codecooler> getCodecoolerList() {
        return codecoolerList;
    }

    public List<Integer> getListOfLevels() {
        return listOfLevels;
    }
}
