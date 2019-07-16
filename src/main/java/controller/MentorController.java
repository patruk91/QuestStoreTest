package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DBException;
import dao.MentorDAO;
import dao.StudentDAO;
import dao.UserDAO;
import model.users.Mentor;
import model.users.Student;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorController implements HttpHandler {

    private Mentor mentor;

    private UserDAO userDAO = new UserDAO();
    private MentorDAO mentorDAO = new MentorDAO();
    private StudentDAO studentDao = new StudentDAO();



    public void handle(HttpExchange httpExchange) throws IOException {

        int mentorId = 4;
        int classId = 2;

        try {
            String uri = httpExchange.getRequestURI().toString();
            if (uri.equals("/mentor/store")) {
            }

            if (uri.equals("/mentor/quests")) {
            }

            if (uri.equals("/mentor/students")) {
                showMyStudents(httpExchange, classId);
            }

            if (uri.equals("/mentor")) {
                showProfile(httpExchange, mentorId);

            }
            if (uri.equals("/mentor/addStudent")){
                //renderAddingStudentTemplate(httpExchange);
                addNewStudent(httpExchange);
            }

            else {
                showProfile(httpExchange, mentorId);
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("IOException in StudentController handle()");
        }
    }



    private void update() {

    }

    private void delete() {

    }

    private void addNewStudent(HttpExchange httpExchange) throws IOException{
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")){


            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/createUpdateStudent.twig");
            JtwigModel model = JtwigModel.newModel();

            response = template.render(model);
            sendResponse(httpExchange, response);


        }

        if (method.equals("POST")){
            //Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            System.out.println("buffer reader" + br);

            System.out.println("form data: " + formData + "!!!!");
            //Map inputs = parseFormData(formData);

            response = "<html><body>" +
                    "<h1>thank you</h1>" +
                    "</body></html>";


//            br.close();
//            isr.close();
//            String url = "/mentor/students";
//            httpExchange.getResponseHeaders().set("Location", url);
//            httpExchange.sendResponseHeaders(303, -1);
            sendResponse(httpExchange, response);




        }

        //sendResponse(httpExchange, response);
    }


    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private void showMyStudents(HttpExchange httpExchange, int id)throws IOException{
        List<Student> studentsList = new ArrayList<>();
        studentsList = getStudents(id);


        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentList.twig");
        JtwigModel model = JtwigModel.newModel();
        //System.out.println("list size" + studentsList.size());
        model.with("listName", studentsList);

        String response = template.render(model);
        //sendResponse(httpExchange, response);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private List<Student> getStudents(int roomId){
        List<Student> students = new ArrayList<>();
        try{
            students = studentDao.getStudentListFromRoom(roomId);
        }
        catch (DBException dbexc){
            System.out.println("db exc caught in mentor dao");
        }
        return students;
    }



    private void showProfile(HttpExchange httpExchange, int id){
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

    private User getUser(int id){
        User user = null;
        try {
            user = userDAO.seeProfile(id);
        }catch (DBException dbExc){
            System.out.println("There was no such user in DB");

        }
        return user;
    }

    private void sendResponse(HttpExchange httpExchange, String response) {
        try{
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();}
        catch (IOException IOExc) {
            System.out.println("Exception in mentor controller");
        }
    }
}
