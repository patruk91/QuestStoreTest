package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.AdminDAO;
import dao.DBException;
import dao.MentorDAO;
import dao.UserDAO;
import model.items.Level;
import model.users.Admin;
import model.users.Mentor;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminController implements HttpHandler {
    List mentorsList = new ArrayList();
    MentorDAO mentorDao = new MentorDAO();
    UserDAO userDAO = new UserDAO();

    private Admin admin;

    public void handle(HttpExchange httpExchange) throws IOException {

        int id = 1;
        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/admin/classes")) {
            }

            if (uri.equals("/admin/levels")) {
                showLevels(httpExchange);
            }

            if (uri.equals("/admin/mentors")) {
                showMentors(httpExchange);
            }

            if (uri.equals("/admin")) {
                showProfile(httpExchange, id);

            } else {
                this.showProfile(httpExchange, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IOException in AdminController handle()");
        }
    }

    private void showLevels(HttpExchange httpExchange) throws IOException {
        String response = "";


        AdminDAO adminDAO = new AdminDAO();
        List<Level> levels = new ArrayList<>();
        try {
            levels = adminDAO.getLevelList();
        } catch (NullPointerException e) {
            System.out.println(e.toString());
        } catch (SQLException e) {
            System.out.println(e.toString());
        }


        System.out.println("name" + levels.get(0).getName());


        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("static/html/admin/levelsPage.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values;
        model.with("levels", levels);


        System.out.println("fillet model with data");
        // render a template to a string
        response = template.render(model);

        System.out.println("model render complete ");
        // send the results to a the client



        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private void showProfile(HttpExchange httpExchange, int id){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        User user = getUser(id);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNum();
        String email = user.getEmail();
        System.out.println("admin first name" + firstName);
        System.out.println("admin last name" + lastName);


        // fill the model with values
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNum", phoneNumber);
        model.with("email", email);
        String response = template.render(model);

        sendResponse(httpExchange, response);
    }



    private void showMentors(HttpExchange httpExchange) {
        // create a list with mentors from dao
        try {
            mentorsList = getMentorsNames(mentorDao.getAllMentors());
        }catch (DBException dbexc) {
            System.out.println("this is db exception");
        }

        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorList.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("listName", mentorsList);

        String response = template.render(model);
        sendResponse(httpExchange, response);
    }




    private List<Mentor> getMentorsList(){
        try {
            mentorsList = mentorDao.getAllMentors();
        }catch (DBException exc) {
            System.out.println("This is DB Exception caught in Admin DAO");
        }
        return mentorsList;
    }

    private List<String> getMentorsNames(List<Mentor> mentorsList){
        List<String> mentorsNames = new ArrayList<>();
        for (Mentor mentor: mentorsList
             ) {
            String firstName = mentor.getFirstName();
            String lastName = mentor.getLastName();
            String fullName = firstName + " " + lastName;
            mentorsNames.add(fullName);
        }
        return mentorsNames;

    }

    private void sendResponse(HttpExchange httpExchange, String response) {
       try{
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();}
       catch (IOException IOExc) {
           System.out.println("Exception in admin controller");
       }
    }


    //todo should we throw some new exception here?
    private User getUser(int id){
        User user = null;
        try {
            user = userDAO.seeProfile(id);
        }catch (DBException dbExc){
            System.out.println("There was no such user in DB");

        }
        return user;
    }

    private void update() {

    }

    private void delete() {

    }
}





