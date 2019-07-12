package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ArtifactDAO;
import dao.DBException;
import model.items.Artifact;
import model.users.Student;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class StudentController implements HttpHandler {


    Student student;


    public void handle(HttpExchange httpExchange) throws IOException {
        int id;
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/student/artifacts")) {
                artifacts(httpExchange);
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

        String firstName = "Jacke";
        String lastName = "Placke";
        String phoneNumber = "9840392/3";
        String email = "jacek@placek";
        // fill the model with values
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
