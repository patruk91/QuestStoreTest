package model.users;


import java.util.List;

public class Admin extends User {
    List<Mentor> mentorList;
    List<Codecooler> codecoolerList;
    List<Integer> listOfLevels;

    public Admin(Builder builder) {
    }

    public static class Builder{}
}
