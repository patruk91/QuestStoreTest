package model.users;



public class Mentor extends User {
    private int roomID;

    public Mentor(int id, String login, String password, String firstName, String lastName, int phoneNum, String email, String adress, String userType, int roomID) {
        super(id, login, password, firstName, lastName, phoneNum, email, adress, userType);
        this.roomID = roomID;
    }
    public Mentor(int id,  String firstName, String lastName, int phoneNum, String email, String adress,  int roomID) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPhoneNum(phoneNum);
        setEmail(email);
        setAdress(adress);
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
