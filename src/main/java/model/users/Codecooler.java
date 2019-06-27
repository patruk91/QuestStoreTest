package model.users;

import model.items.Quest;


import java.util.List;

public class Codecooler extends User {
    private int ammountOfCoins;
    private int roomID;
    private List<Quest> questList;
    private int lvlOfExp;


    public Codecooler(int id, String login, String password, String firstName, String lastName, String phoneNum, String email, String adress,String userType, int ammountOfCoins, int roomID, List<Quest> questList, int lvlOfExp) {
        super(id, login, password, firstName, lastName, phoneNum, email, adress, userType);
        this.ammountOfCoins = ammountOfCoins;
        this.roomID = roomID;
        this.questList = questList;
        this.lvlOfExp = lvlOfExp;
    }

    public Codecooler(int id, int ammountOfCoins, int roomID, List<Quest> questList, int lvlOfExp) {
        setId(id);
        this.ammountOfCoins = ammountOfCoins;
        this.roomID = roomID;
        this.questList = questList;
        this.lvlOfExp = lvlOfExp;
    }

    public Codecooler(int id, String login, String password, String userType) {
        setId(id);
        setLogin(login);
        setPassword(password);
        setUserType(userType);
    }

    public int getAmmountOfCoins() {
        return ammountOfCoins;
    }

    public int getRoomID() {
        return roomID;
    }

    public List<Quest> getQuestList() {
        return questList;
    }

    public int getLvlOfExp() {
        return lvlOfExp;
    }
}
