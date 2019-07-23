package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.users.Mentor;
import model.users.Student;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.*;

public class MentorController implements HttpHandler {

    private Mentor mentor;

    private UserDAO userDAO = new UserDAO();
    private MentorDAO mentorDAO = new MentorDAO();
    private StudentDAO studentDao = new StudentDAO();
    private SessionDAO sessionDAO = new SessionDAO();



    //this should be mentor.getClassId(); info about mentor's class is in 'classes' table
    int classId = 2;


    public void handle(HttpExchange httpExchange) throws IOException {



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
               int mentorId = getUserIdBySessionID(httpExchange);
                showProfile(httpExchange, mentorId);

            }
            if (uri.equals("/mentor/addStudent")){
                //renderAddingStudentTemplate(httpExchange);
                addNewStudent(httpExchange);
            }

            else {
                int mentorId = getUserIdBySessionID(httpExchange);
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
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = parseFormData(formData);

            User user = null;
            Student student = null;

            for (String key : inputs.keySet()){
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
            }catch (DBException dbexc){
                System.out.println("db exception caught in mentor controller");
            }

//            response = "<html><body>" +
//                    "<h1>thank you</h1>" +
//                    "</body></html>";

            br.close();
            isr.close();
            String url = "/mentor/students";
            httpExchange.getResponseHeaders().set("Location", url);
            httpExchange.sendResponseHeaders(303, -1);
           // sendResponse(httpExchange, response);

        }

        //sendResponse(httpExchange, response);
    }

    private int getUserIdBySessionID(HttpExchange httpExchange){
        //get userId form sessions table found by session id from cookie
        int mentorId = 0;

        // solution for only one cookie, found by index
//        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
//        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
//        String sessionId = cookie.getValue();
//        System.out.println("sessionID: " + sessionId);

        //second solution, if there is more cookie:
        Optional<HttpCookie> cookie = this.getCookieWithSessionId(httpExchange);
        String wrongSessionId = cookie.get().getValue();
        String sessionId = removeQuotationFromSessionId(wrongSessionId);
        System.out.println("sessionID: " + sessionId);


        try{
            mentorId = sessionDAO.getUserIdBySession(sessionId);
            System.out.println("mentor id: " + mentorId);
        }catch (DBException exc){
            System.out.println("DB exception cought in getUsernBySessionID");
        }
        return mentorId;
    }


    private String removeQuotationFromSessionId(String cookieString){
        String[] cookieValues = cookieString.split("=");
        String sessionIdwrong = cookieValues[0].trim();
        StringBuilder sb = new StringBuilder(sessionIdwrong);
        sb.deleteCharAt(sessionIdwrong.length()-1);
        sb.deleteCharAt(0);
        String sessionId = sb.toString();
        //System.out.println(sessionId + "session id in removequotation marks");
        return sessionId;

    }

    //this method return cookie with session id (in case of sending more than one cookie)
    private Optional<HttpCookie> getCookieWithSessionId(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = this.parseCookies(cookieStr);
        return this.findCookieByName("sessionId", cookies);
    }


    //this method create list witrh all cookies, in case if there is more than one cookie
    private List<HttpCookie> parseCookies(String cookieString){
        List<HttpCookie> cookies = new ArrayList<>();
        if(cookieString == null || cookieString.isEmpty()){ // what happens if cookieString = null?
            return cookies;
        }
        for(String cookie : cookieString.split(";")){
            int indexOfEq = cookie.indexOf('=');
            String cookieName = cookie.substring(0, indexOfEq);
            String cookieValue = cookie.substring(indexOfEq + 1, cookie.length());
            cookies.add(new HttpCookie(cookieName, cookieValue));
        }
        return cookies;
    }

    //this method return one coockie by its name
    private Optional<HttpCookie> findCookieByName(String name, List<HttpCookie> cookies){
        for(HttpCookie cookie : cookies){
            if(cookie.getName().equals(name))
                return Optional.ofNullable(cookie);
        }
        return Optional.empty();
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
