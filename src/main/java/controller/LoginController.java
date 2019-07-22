package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.LoginDAO;
import dao.QuestDAO;
import dao.SessionDAO;
import model.items.Quest;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;

public class LoginController implements HttpHandler {


    private User user;
    LoginDAO loginDao = new LoginDAO();
    SessionDAO sessionDao = new SessionDAO();



    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String uriStr = httpExchange.getRequestURI().toString();

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/login.twig");
            JtwigModel model = JtwigModel.newModel();


            String response = template.render(model);

        }




    }


    private void update() {

    }

    private void delete() {

    }
}
