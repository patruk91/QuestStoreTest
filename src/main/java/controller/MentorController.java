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

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MentorController implements HttpHandler {

    private Mentor mentor;
    private UserDAO userDAO = new UserDAO();
    private MentorDAO mentorDAO = new MentorDAO();
    private StudentDAO studentDao = new StudentDAO();


    public void handle(HttpExchange httpExchange) throws IOException {

        int mentorId = 4;
        int classId = 2;
        //tabela aktywne sesje:
        //id, sessionId, userId, login,
        // wez sessioId z cookie
        // z bazy danych z sesjami wez id uzytkownika po sessionID

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

            } else {
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


    private void showMyStudents(HttpExchange httpExchange, int id){
        List<Student> studentsList = new ArrayList<>();
        studentsList = getStudents(id);
//        try {
//            studentsList = studentDao.getStudentListFromRoom(id);
//        }catch (DBException dbexc) {
//            System.out.println("this is db exception caught in mentor controller");
//        }
        System.out.println("ShowMyStudent executed");

        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentList.twig");
        JtwigModel model = JtwigModel.newModel();
        System.out.println("list size" + studentsList.size());
        model.with("listName", studentsList);
        //model.with("listName", )

        String response = template.render(model);
        sendResponse(httpExchange, response);
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
            System.out.println("Exception in admin controller");
        }
    }
}
