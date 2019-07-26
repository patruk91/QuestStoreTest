package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.items.Level;
import model.users.Mentor;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;

public class AdminController implements HttpHandler {
    private List mentorsList = new ArrayList();
    private MentorDAO mentorDao = new MentorDAO();
    private UserDAO userDAO = new UserDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public void handle(HttpExchange httpExchange) {

        int id = 1;
        try {
            String uri = httpExchange.getRequestURI().toString();
            String[] parsedUri = parseResponseURi(uri);

            if (uri.equals("/admin/classes")) {
            } else if (uri.equals("/admin/levels")) {
                showLevels(httpExchange);
            } else if (uri.equals("/admin/mentors")) {
                showMentors(httpExchange);
            } else if (uri.equals("/admin")) {
                showProfile(httpExchange, id);

            } else if (uri.equals("/admin/createMentor")) {
                addNewMentor(httpExchange);
            } else if (parsedUri.length > 2 && parsedUri[2].equals("update")) {
                updateProfile(parsedUri[3], httpExchange);
            } else {
                this.showProfile(httpExchange, id);
            }
        } catch (IOException e) {
            System.out.println("IOException in AdminController");
        } catch (DBException e) {
            System.out.println("DBException in AdminController");
        } catch (Exception e) {
            System.out.println("Unidentified exception in AdminController");
        }
    }

    private void showLevels(HttpExchange httpExchange) throws DBException, IOException {
        String method = httpExchange.getRequestMethod();
        AdminDAO adminDAO = new AdminDAO();
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


            System.out.println("name" + levels.get(0).getName());


            // get a template file
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/levelsPage.twig");

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
        if (method.equals("POST")) {
            Map<String, String> inputs;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();


            System.out.println("form data: " + formData + "!!!!");
            inputs = parseFormData(formData);
            String name = inputs.get("name");
            String rangeString = inputs.get("range");


            adminDAO.addLevel(name, Integer.valueOf(rangeString));


            String url = "/admin/levels";
            httpExchange.getResponseHeaders().set("Location", url);
            httpExchange.sendResponseHeaders(303, -1);

        }

    }

    private void showProfile(HttpExchange httpExchange, int id) throws IOException, DBException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        User user = userDAO.seeProfile(id);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNum();
        String email = user.getEmail();
        String address = user.getAddress();
        System.out.println("admin first name" + firstName);
        System.out.println("admin last name" + lastName);
        int totalStudents = studentDAO.getAllStudents().size();
        int totalMentors = mentorDao.getAllMentors().size();


        // fill the model with values
        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNum", phoneNumber);
        model.with("email", email);
        model.with("address", address);
        model.with("totalStudents", totalStudents);
        model.with("totalMentors", totalMentors);
        String response = template.render(model);

        sendResponse(httpExchange, response);
    }


    private void showMentors(HttpExchange httpExchange) throws IOException, DBException {
        // create a list with mentors from dao

        mentorsList = mentorDao.getAllMentors();


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/mentorList.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentorsList);

        String response = template.render(model);
        sendResponse(httpExchange, response);
    }


    private List<String> getMentorsNames(List<Mentor> mentorsList) {
        List<String> mentorsNames = new ArrayList<>();
        for (Mentor mentor : mentorsList
        ) {
            String firstName = mentor.getFirstName();
            String lastName = mentor.getLastName();
            String fullName = firstName + " " + lastName;
            mentorsNames.add(fullName);
        }
        return mentorsNames;

    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }


    private void addNewMentor(HttpExchange httpExchange) throws IOException, DBException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/admin/createUpdateMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            response = template.render(model);
            sendResponse(httpExchange, response);

        }

        if (method.equals("POST")) {
            Map<String, String> inputs;
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = parseFormData(formData);

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
            System.out.println(mentorsClass);

            user = new Mentor(login, password, "mentor");
            //default value of coolcoins and experience points is 0 as we create new student
            mentor = new Mentor(0, login, password, name, surname, phone, email, adress);

            mentorDao.addMentor(user, mentor);

            String url = "/admin/mentors";
            httpExchange.getResponseHeaders().set("Location", url);
            httpExchange.sendResponseHeaders(303, -1);

        }
    }


    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private String[] parseResponseURi(String uri) {


        System.out.println("PARSING DATA");
        String[] splitedUri = uri.split("/");

        for (String element : splitedUri
        ) {
            System.out.println(element);
        }
        return splitedUri;

    }


    private void updateProfile(String id, HttpExchange httpExchange) throws IOException, DBException {
        System.out.println("PROFILE UPDATE" + id);

        String response = "";
        String method = httpExchange.getRequestMethod();


        if (method.equals("GET")) {
            Mentor mentor = new Mentor("null", "null", "null");

            mentor = userDAO.getFullMentor(Integer.parseInt(id));


            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
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
            sendResponse(httpExchange, response);

        }

        if (method.equals("POST")) {
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = parseFormData(formData);

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
            System.out.println(mentorsClass);


            mentor = new Mentor(Integer.valueOf(id), login, password, name, surname, phone, email, adress);

            mentorDao.updateMentorByID(mentor);

            String url = "/admin/mentors";
            httpExchange.getResponseHeaders().set("Location", url);
            httpExchange.sendResponseHeaders(303, -1);

        }
    }

}





