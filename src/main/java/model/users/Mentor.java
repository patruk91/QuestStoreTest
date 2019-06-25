package model.users;



public class Mentor extends User {
    int roomID;

    public Mentor(int id, String login, String password, String firstName, String lastName, int phoneNum, String adress, int roomID) {
        super(id, login, password, firstName, lastName, phoneNum, adress);
        this.roomID = roomID;
    }
}
