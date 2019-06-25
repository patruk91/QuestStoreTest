package model.users;



public class Mentor extends User {
    private int roomID;

    public Mentor(int id, String login, String password, String firstName, String lastName, int phoneNum, String adress, String userType, int roomID) {
        super(id, login, password, firstName, lastName, phoneNum, adress, userType);
        this.roomID = roomID;
    }

    public Mentor(int id, int roomID){
        super();
        setId(id);
        this.roomID = roomID;
    }

    public int getRoomID() {
        return roomID;
    }
}
