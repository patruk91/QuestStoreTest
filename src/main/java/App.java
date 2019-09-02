import com.sun.net.httpserver.HttpServer;
import controller.AdminController;
import controller.LoginController;
import controller.MentorController;
import controller.StudentController;

import java.net.InetSocketAddress;

public class App {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/static", new Static());
        server.createContext("/student", new StudentController());
        server.createContext("/admin", new AdminController());
        server.createContext("/mentor", new MentorController());
        server.createContext("/login", new LoginController());

        server.setExecutor(null);
        server.start();
    }

}
