package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ArtifactDAO;
import dao.DBException;
import dao.QuestDAO;
import model.items.Artifact;
import model.items.Quest;
import model.users.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentController implements HttpHandler {

    List<Quest> questList = new ArrayList<>();
    Student student = new Student(2, "test", "test", "Jacek", "placek", "666", "jacek@placek", "Szczebrzeszyn", "student", 500, 5, questList, 50);


    public void handle(HttpExchange httpExchange) throws IOException {
        int id;
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/student/artifacts")) {
                artifacts(httpExchange);
            } if (uri.equals("/student/quests")){
                quests(httpExchange);
            } if (uri.equals("/student/transactions")) {
                transactions(httpExchange);
            } else {
                profile(httpExchange);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException in StudentController");
        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("DBException in StudentController");
        }

    }

    private void transactions(HttpExchange httpExchange) throws DBException, IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/transactions.twig");
        JtwigModel model = JtwigModel.newModel();

        QuestDAO questDAO = new QuestDAO();
        List<Quest> questList = questDAO.getQuestsList();

        model.with("questList", questList);


        String response = template.render(model);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void quests(HttpExchange httpExchange) throws DBException, IOException {


        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/quests.twig");
        JtwigModel model = JtwigModel.newModel();

        QuestDAO questDAO = new QuestDAO();
        List<Quest> questList = questDAO.getQuestsList();

        model.with("questList", questList);


        String response = template.render(model);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void artifacts(HttpExchange httpExchange) throws DBException, IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/artifacts.twig");
        JtwigModel model = JtwigModel.newModel();

        ArtifactDAO artifactDAO = new ArtifactDAO();
        List<Artifact> artifactList = artifactDAO.getArtifactsList();


        model.with("artifactList", artifactList);


        String response = template.render(model);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();


    }

    private void profile(HttpExchange httpExchange) throws IOException {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        String phoneNumber = student.getPhoneNum();
        String email = student.getEmail();
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNumber", phoneNumber);
        model.with("email", email);
        String response = template.render(model);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }


    private void update() {

    }

    private void delete() {

    }


}
