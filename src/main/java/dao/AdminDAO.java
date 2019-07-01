package dao;

import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AdminDAO implements IAdminDAO {
    Connection connection;
    DBCreator dbCreator;

    AdminDAO(){
        dbCreator = new DBCreator();
    }


    //todo
    public void addLevel() {

    }
    //todo
    public void createNewClass() {

    }
}
