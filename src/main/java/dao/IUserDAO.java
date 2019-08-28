package dao;

import model.users.User;

public interface IUserDAO {
    User seeProfile(int id) throws DBException;
}
