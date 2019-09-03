package controller;

import com.sun.net.httpserver.HttpExchange;
import dao.*;
import helpers.DataParser;
import model.items.Level;
import model.users.Mentor;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminHelper {
    private List mentorsList;
    private MentorDAO mentorDao;
    private UserDAO userDAO;
    private StudentDAO studentDAO;
    private AdminDAO adminDAO;
    private UtilityService utilityService;

    public AdminHelper(MentorDAO mentorDao,
                       UserDAO userDAO,
                       StudentDAO studentDAO,
                       AdminDAO adminDAO,
                       UtilityService utilityService) {
        this.mentorsList = new ArrayList();
        this.mentorDao = mentorDao;
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.adminDAO = adminDAO;
        this.utilityService = utilityService;
    }

    public void showLevels(HttpExchange httpExchange) throws DBException, IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            String response;
            List<Level> levels = new ArrayList<>();
            try {
                levels = adminDAO.getLevelList();
            } catch (NullPointerException e) {
                System.out.println(e.toString());
            } catch (SQLException e) {
                System.out.println(e.toString());
            }

            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/levelsPage.twig");
            JtwigModel model = JtwigModel.newModel();
            model.with("levels", levels);
            response = template.render(model);
            utilityService.sendResponse(httpExchange, response);
        }
        if (method.equals("POST")) {
            Map<String, String> inputs;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            inputs = DataParser.parseFormData(formData);
            String name = inputs.get("name");
            String rangeString = inputs.get("range");

            adminDAO.addLevel(name, Integer.valueOf(rangeString));

            String url = "/admin/levels";
            utilityService.sendRedirect(httpExchange, url);
        }

    }

    public void showProfile(HttpExchange httpExchange, int id) throws IOException, DBException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        User user = userDAO.seeProfile(id);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNum();
        String email = user.getEmail();
        String address = user.getAddress();
        int totalStudents = studentDAO.getAllStudents().size();
        int totalMentors = mentorDao.getAllMentors().size();

        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNum", phoneNumber);
        model.with("email", email);
        model.with("address", address);
        model.with("totalStudents", totalStudents);
        model.with("totalMentors", totalMentors);
        String response = template.render(model);

        utilityService.sendResponse(httpExchange, response);
    }


    public void showMentors(HttpExchange httpExchange) throws IOException, DBException {
        mentorsList = mentorDao.getAllMentors();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorList.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentorsList);
        String response = template.render(model);
        utilityService.sendResponse(httpExchange, response);
    }


    public void addNewMentor(HttpExchange httpExchange) throws IOException, DBException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/admin/createUpdateMentor.twig");
            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
            utilityService.sendResponse(httpExchange, response);
        }

        if (method.equals("POST")) {
            Map<String, String> inputs;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            inputs = DataParser.parseFormData(formData);

            User user = null;
            Mentor mentor = null;

            String name = inputs.get("name");
            String surname = inputs.get("surname");
            String login = inputs.get("login");
            String password = inputs.get("password");
            String email = inputs.get("email");
            String adress = inputs.get("adress");
            String phone = inputs.get("phone");
            String mentorsClass = inputs.get("classes");
            user = new Mentor(login, password, "mentor");
            //default value of coolcoins and experience points is 0 as we create new student
            mentor = new Mentor(0, login, password, name, surname, phone, email, adress);
            mentorDao.addMentor(user, mentor);

            String url = "/admin/mentors";
            utilityService.sendRedirect(httpExchange, url);
        }
    }


    public String[] parseResponseURi(String uri) {
        String[] splitedUri = uri.split("/");
        return splitedUri;
    }


    public void updateProfile(String id, HttpExchange httpExchange) throws IOException, DBException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            Mentor mentor = userDAO.getFullMentor(Integer.parseInt(id));
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/admin/UpdateMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            model.with("firstName", mentor.getFirstName());
            model.with("lastName", mentor.getLastName());
            model.with("login", mentor.getLogin());
            model.with("password", mentor.getPassword());
            model.with("phone", mentor.getPhoneNum());
            model.with("email", mentor.getEmail());
            model.with("address", mentor.getAddress());

            response = template.render(model);
            utilityService.sendResponse(httpExchange, response);
        }

        if (method.equals("POST")) {
            Map<String, String> inputs;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            inputs = DataParser.parseFormData(formData);

            String name = inputs.get("name");
            String surname = inputs.get("surname");
            String login = inputs.get("login");
            String password = inputs.get("password");
            String email = inputs.get("email");
            String adress = inputs.get("adress");
            String phone = inputs.get("phone");
            Mentor mentor = new Mentor(Integer.valueOf(id), login, password, name, surname, phone, email, adress);
            mentorDao.updateMentorByID(mentor);

            String url = "/admin/mentors";
            utilityService.sendRedirect(httpExchange, url);
        }
    }
}
