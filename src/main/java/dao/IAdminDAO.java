package dao;


import model.users.Codecooler;
import model.users.Mentor;
import model.users.User;

import java.sql.SQLException;
import java.util.List;

public interface IAdminDAO{

    /*this interface contains methods which are left after splitting everything into other interfaces -
    this are two admin's methods
    both are still NOT implemented in AdminDAO*/

    void addLevel();
    void createNewClass();
}