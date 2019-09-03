import com.sun.net.httpserver.HttpServer;
import controller.*;
import dao.*;
import helpers.CookieHelper;

import java.net.CookieHandler;
import java.net.InetSocketAddress;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        MentorDAO mentorDao = new MentorDAO();
        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();
        AdminDAO adminDAO = new AdminDAO();
        UtilityService utilityService = new UtilityService();
        SessionDAO sessionDAO = new SessionDAO();
        CookieHelper cookieHelper = new CookieHelper(sessionDAO);
        ArtifactDAO artifactDAO = new ArtifactDAO();
        QuestDAO questDAO = new QuestDAO();
        WalletDAO walletDAO = new WalletDAO();
        LoginDAO loginDAO = new LoginDAO();


        AdminHelper adminHelper = new AdminHelper(mentorDao, userDAO, studentDAO, adminDAO, utilityService);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/static", new Static());
        server.createContext("/student", new StudentController(userDAO, cookieHelper, questDAO,
                artifactDAO, studentDAO, utilityService));
        server.createContext("/admin", new AdminController(adminHelper));
        server.createContext("/mentor", new MentorController(userDAO, studentDAO, cookieHelper,
                mentorDao, artifactDAO, questDAO,
                walletDAO, utilityService));
        server.createContext("/login", new LoginController(loginDAO, sessionDAO,
                cookieHelper, utilityService));

        server.setExecutor(null);
        server.start();
    }

}
