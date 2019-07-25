package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import helpers.CookieHelper;
import model.items.Artifact;
import model.items.Quest;
import model.users.Student;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

public class MentorController implements HttpHandler {

    private UserDAO userDAO = new UserDAO();
    private StudentDAO studentDao = new StudentDAO();
    private CookieHelper cookieHelper = new CookieHelper();
    private MentorDAO mentorDAO = new MentorDAO();


    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            int mentorId = cookieHelper.getUserIdBySessionID(httpExchange);
            int classId = mentorDAO.getMentorById(mentorId).getRoomID();
            String uri = httpExchange.getRequestURI().toString();

            if (uri.equals("/mentor/store")) {
                showArtifacts(httpExchange);
            }

            else if (uri.equals("/mentor/quests")) {
                showQuests(httpExchange);
            }

            else if (uri.equals("/mentor/students")) {
                showMyStudents(httpExchange, classId);
            }

            else if (uri.equals("/mentor")) {
                showProfile(httpExchange, mentorId);

            }

            else if (uri.equals("/mentor/addStudent")) {
                addNewStudent(httpExchange, classId);

            }

            else if (uri.contains("/mentor/addStudent/")) {
                update(httpExchange);
            }
            else{
                showProfile(httpExchange, mentorId);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("IOException in StudentController handle()");
        }

    }


    private void showArtifacts(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().toString();
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            try {
                JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/artifactsMentor.twig");
                JtwigModel model = JtwigModel.newModel();

                ArtifactDAO artifactDAO = new ArtifactDAO();
                List<Artifact> artifactList = artifactDAO.getArtifactsList();

                model.with("artifactList", artifactList);
                String response = template.render(model);

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (DBException exc) {
                System.out.println("DB EXC cought in mentorl controller");
                exc.printStackTrace();
            }
        }
    }

        private void showQuests (HttpExchange httpExchange) throws IOException {
            String uri = httpExchange.getRequestURI().toString();
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {
                try {
                    JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/questsMentor.twig");
                    JtwigModel model = JtwigModel.newModel();

                    QuestDAO questDAO = new QuestDAO();
                    List<Quest> questList = questDAO.getQuestsList();

                    model.with("questList", questList);
                    String response = template.render(model);

                    httpExchange.sendResponseHeaders(200, response.length());
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } catch (DBException exc) {
                    System.out.println("DB EXC cought in mentorl controller");
                    exc.printStackTrace();
                }
            }
        }

        private void update (HttpExchange httpExchange) throws UnsupportedEncodingException, IOException {
            System.out.println("update executed");
            String uri = httpExchange.getRequestURI().toString();
            String method = httpExchange.getRequestMethod();
            int userId = 0;

            if (method.equals("GET")) {
                String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
                JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/updateStudent.twig");
                JtwigModel model = JtwigModel.newModel();


                try {
                    userId = this.getIdFromUri(uri);
                    User user = userDAO.seeProfile(userId);
                    model.with("name", user.getFirstName());
                    model.with("surname", user.getLastName());
                    model.with("login", user.getLogin());
                    model.with("password", user.getPassword());
                    model.with("email", user.getEmail());
                    model.with("adress", user.getAddress());
                    model.with("phone", user.getPhoneNum());
                } catch (DBException exc) {
                    System.out.println("DB exception cought in update Student in mentor controller");
                }

                String response = template.render(model);
                sendResponse(httpExchange, response);
            }

            if (method.equals("POST")) {
                Map<String, String> inputs = new HashMap<>();
                InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                System.out.println("form data: " + formData + "!!!!");
                inputs = parseFormData(formData);

                Student student = null;
                userId = this.getIdFromUri(uri);


                String name = inputs.get("name");
                String surname = inputs.get("surname");
                String login = inputs.get("login");
                String password = inputs.get("password");
                String email = inputs.get("email");
                String adress = inputs.get("adress");
                String phone = inputs.get("phone");

                //we dont use level, what are levels? is it class?
                String level = inputs.get("levels");

                student = new Student(userId, login, password, name, surname, phone, email, adress);


                try {
                    studentDao.updateStudent(student);
                } catch (DBException dbexc) {
                    System.out.println("db exception caught in mentor controller");
                }


                br.close();
                isr.close();
                String url = "/mentor/students";
                httpExchange.getResponseHeaders().set("Location", url);
                httpExchange.sendResponseHeaders(303, -1);
            }
        }



        private int getIdFromUri (String uri){
            String[] uriElements = uri.split("/");
            System.out.println(uriElements[3]);
            Integer id = Integer.valueOf(uriElements[3]);
            System.out.println(id);
            return id;
        }

        private void delete () {

        }

        private void addNewStudent (HttpExchange httpExchange, int classId) throws IOException {
            String response = "";
            String method = httpExchange.getRequestMethod();

            if (method.equals("GET")) {
                String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
                JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/createUpdateStudent.twig");
                JtwigModel model = JtwigModel.newModel();

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
                Student student = null;

                for (String key : inputs.keySet()) {
                    String value = inputs.get(key);
                    String name = inputs.get("name");
                    String surname = inputs.get("surname");
                    String login = inputs.get("login");
                    String password = inputs.get("password");
                    String email = inputs.get("email");
                    String adress = inputs.get("adress");
                    String phone = inputs.get("phone");
                    String level = inputs.get("levels");
                    user = new Student(login, password, "student");
                    //default value of coolcoins and experience points is 0 as we create new student
                    student = new Student(0, login, password, name, surname, phone, email, adress, classId, 0, 0);
                }

                try {
                    studentDao.createStudent(user, student);
                } catch (DBException dbexc) {
                    System.out.println("db exception caught in mentor controller");
                }


                br.close();
                isr.close();
                String url = "/mentor/students";
                httpExchange.getResponseHeaders().set("Location", url);
                httpExchange.sendResponseHeaders(303, -1);

            }

        }


        private static Map<String, String> parseFormData (String formData) throws UnsupportedEncodingException {
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

        private void showMyStudents (HttpExchange httpExchange,int id)throws IOException {
            List<Student> studentsList = new ArrayList<>();
            studentsList = getStudents(id);

            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentList.twig");
            JtwigModel model = JtwigModel.newModel();
            model.with("listName", studentsList);

            String response = template.render(model);

            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private List<Student> getStudents ( int roomId){
            List<Student> students = new ArrayList<>();
            try {
                students = studentDao.getStudentListFromRoom(roomId);
            } catch (DBException dbexc) {
                System.out.println("db exc caught in mentor dao");
            }
            return students;
        }


        private void showProfile (HttpExchange httpExchange,int id){
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/profileMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            User user = getUser(id);

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String phoneNumber = user.getPhoneNum();
            String email = user.getEmail();

            // fill the model with values
            model.with("firstName", firstName);
            model.with("lastName", lastName);
            model.with("phoneNum", phoneNumber);
            model.with("email", email);
            String response = template.render(model);

            sendResponse(httpExchange, response);
        }

        private User getUser ( int id){
            User user = null;
            try {
                user = userDAO.seeProfile(id);
            } catch (DBException dbExc) {
                System.out.println("There was no such user in DB");

            }
            return user;
        }

        private void sendResponse (HttpExchange httpExchange, String response){
            try {
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (IOException IOExc) {
                System.out.println("Exception in mentor controller");
            }
        }
    }

