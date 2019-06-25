package model.users;

import model.items.Quest;


import java.util.List;

public class Codecooler extends User {
    int ammountOfCoins;
    int roomID;
    List<Quest> questList;
    int lvlOfExp;

    public Codecooler(Builder builder) {

    }

    public static class Builder{

    }
}
