package model.users;

import java.util.List;

public class Admin extends User {
    private List<Mentor> mentorList;
    private List<Codecooler> codecoolerList;
    private List<Integer> listOfLevels;

    public Admin(int id, String login, String password, String firstName, String lastName, String phoneNum, String email, String address, String userType, List<Mentor> mentorList, List<Codecooler> codecoolerList, List<Integer> listOfLevels) {
        super(id, login, password, firstName, lastName, phoneNum, email, address, userType);
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

    public  Admin(int user_id, String  login,String password,String firstName,String lastName,String phoneNumber,String email,String address){
        setId(user_id);
        setLogin(login);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNumber);
        setEmail(email);
        setAddress(address);
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
