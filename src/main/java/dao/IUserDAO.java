package dao;


import model.items.Artifact;
import model.items.Quest;
import model.users.User;

import java.util.ArrayList;

public interface IUserDAO {


    ArrayList<Artifact> seeArtifactsList();
    ArrayList<Quest> seeQuestsList();
    User seeProfile();
    void updateMyProfile();

}
