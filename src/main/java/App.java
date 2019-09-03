import com.sun.net.httpserver.HttpServer;
import controller.*;
import dao.MentorDAO;
import dao.StudentDAO;
import dao.UserDAO;

import java.net.InetSocketAddress;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        MentorDAO mentorDao = new MentorDAO();
        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();
        AdminHelper adminHelper = new AdminHelper(mentorDao, userDAO, studentDAO);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/static", new Static());
        server.createContext("/student", new StudentController());
        server.createContext("/admin", new AdminController(adminHelper));
        server.createContext("/mentor", new MentorController());
        server.createContext("/login", new LoginController());

        server.setExecutor(null);
        server.start();
    }

}
