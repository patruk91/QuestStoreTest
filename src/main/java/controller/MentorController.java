package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import helpers.CookieHelper;
import helpers.DataParser;
import model.items.Artifact;
import model.items.Quest;
import model.users.Mentor;
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
    private ArtifactDAO artifactDao = new ArtifactDAO();
    private QuestDAO questDAO = new QuestDAO();
    private WalletDAO walletDAO = new WalletDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();


    public void handle(HttpExchange httpExchange) {

        try {
            int mentorId = cookieHelper.getUserIdBySessionID(httpExchange);
            int classId = mentorDAO.getMentorById(mentorId).getRoomID();
            String uri = httpExchange.getRequestURI().toString();
            String[] parsedUri = parseResponseURi(uri);

            if (uri.equals("/mentor/store")) {
                showArtifacts(httpExchange);
            } else if (uri.equals("/mentor/quests")) {
                showQuests(httpExchange);
            } else if (uri.equals("/mentor/students")) {
                showMyStudents(httpExchange, classId);
            } else if (uri.equals("/mentor")) {
                showProfile(httpExchange, mentorId);
            } else if (uri.equals("/mentor/addStudent")) {
                addNewStudent(httpExchange, classId);
            } else if (uri.contains("/mentor/addStudent/")) {
                update(httpExchange);
            } else if (uri.contains("/mentor/editArtifact/")) {
                updateArtif(httpExchange);
            } else if (uri.equals("/mentor/addArtifact")) {
                addArtifact(httpExchange);
            }

            else if (parsedUri.length > 2 && parsedUri[2].equals("studentView")){
                showStudent(httpExchange, Integer.parseInt(parsedUri[3]));
            }

            else if (parsedUri.length > 2 && parsedUri[2].equals("markQuest")){
                showQuests(httpExchange, Integer.parseInt(parsedUri[3]));
            }
            else{
                showProfile(httpExchange, mentorId);
            }
        } catch (IOException e) {
            System.out.println("IOException in MentorController");
        } catch (DBException e) {
            System.out.println("DBException in MentorController");
        } catch (Exception e) {
            System.out.println("Unidentified exception in MentorController");
        }

    }


    private void addArtifact(HttpExchange httpExchange) throws IOException, DBException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/createArtifact.twig");
            JtwigModel model = JtwigModel.newModel();

            response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);

        } else if (method.equals("POST")) {
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = DataParser.parseFormData(formData);

            String title = inputs.get("title");
            System.out.println("title");
            int value = Integer.valueOf(inputs.get("value"));
            System.out.println(value);
            String description = inputs.get("description");
            System.out.println(description);

            //default category: basic, default availability true,
            Artifact artifact = new Artifact(0, title, description, "basic", value, true);

            artifactDao.createArtifact(artifact);

            br.close();
            isr.close();
            String url = "/mentor/store";
            UtilityService.sendRedirect(httpExchange, url);
        }
    }

    private void updateArtif(HttpExchange httpExchange) throws IOException, DBException {
        String uri = httpExchange.getRequestURI().toString();
        String method = httpExchange.getRequestMethod();
        int artifactId = 0;

        if (method.equals("GET")) {
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/updateArtifact.twig");
            JtwigModel model = JtwigModel.newModel();


            artifactId = this.getIdFromUri(uri);
            Artifact artifact = artifactDao.getArtifact(artifactId);
            model.with("title", artifact.getName());
            model.with("value", artifact.getPrice());
            model.with("description", artifact.getDescription());


            String response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);
        }

        if (method.equals("POST")) {
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = DataParser.parseFormData(formData);

            artifactId = this.getIdFromUri(uri);
            int newPrice = Integer.valueOf(inputs.get("value"));


            artifactDao.updateArtifact(artifactId, newPrice);


            br.close();
            isr.close();
            String url = "/mentor/store";
            UtilityService.sendRedirect(httpExchange, url);
        }
    }

    private void showStudent(HttpExchange httpExchange, int UserId) throws DBException, IOException {
        String response = "";
        User user = new Student();
        String method = httpExchange.getRequestMethod();

        user = userDAO.seeProfile(UserId);


        if (method.equals("GET")) {
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/student/profileForMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            List<Quest> completedQuests = questDAO.getUsersQuests(UserId);
            List<Artifact> purchasedArtifacts = artifactDAO.getUsersArtifacts(UserId);

            model.with("purchasedArtifacts", purchasedArtifacts);
            model.with("completedQuests", completedQuests);
            model.with("firstName", user.getFirstName());
            model.with("lastName", user.getLastName());
            model.with("address", user.getAddress());
            model.with("phoneNumber", user.getPhoneNum());
            model.with("email", user.getEmail());
            model.with("coolcoins", user.getAmountOfCoins());
            model.with("experience_points", user.getLvlOfExp());
            model.with("class", user.getRoomID());

            response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);
        }
    }


    private void showArtifacts(HttpExchange httpExchange) throws IOException, DBException {
        String uri = httpExchange.getRequestURI().toString();
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/artifactsMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            ArtifactDAO artifactDAO = new ArtifactDAO();
            List<Artifact> artifactList = artifactDAO.getArtifactsList();

            model.with("artifactList", artifactList);
            String response = template.render(model);

            UtilityService.sendResponse(httpExchange, response);

        }


    }

    private void showQuests(HttpExchange httpExchange) throws IOException, DBException {
        String uri = httpExchange.getRequestURI().toString();
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {

            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/questsMentor.twig");
            JtwigModel model = JtwigModel.newModel();

            QuestDAO questDAO = new QuestDAO();
            List<Quest> questList = questDAO.getQuestsList();

            model.with("questList", questList);
            String response = template.render(model);

           UtilityService.sendResponse(httpExchange, response);
        }
    }

    private void update(HttpExchange httpExchange) throws IOException, DBException {
        System.out.println("update executed");
        String uri = httpExchange.getRequestURI().toString();
        String method = httpExchange.getRequestMethod();
        int userId = 0;

        if (method.equals("GET")) {
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/updateStudent.twig");
            JtwigModel model = JtwigModel.newModel();


            userId = this.getIdFromUri(uri);
            User user = userDAO.seeProfile(userId);
            model.with("name", user.getFirstName());
            model.with("surname", user.getLastName());
            model.with("login", user.getLogin());
            model.with("password", user.getPassword());
            model.with("email", user.getEmail());
            model.with("adress", user.getAddress());
            model.with("phone", user.getPhoneNum());


            String response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);
        }

        if (method.equals("POST")) {
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = DataParser.parseFormData(formData);

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


            studentDao.updateStudent(student);


            br.close();
            isr.close();
            String url = "/mentor/students";
            UtilityService.sendRedirect(httpExchange, url);
        }
    }


    private int getIdFromUri(String uri) {
        String[] uriElements = uri.split("/");
        //System.out.println(uriElements[3]);
        Integer id = Integer.valueOf(uriElements[3]);
        //System.out.println(id);
        return id;
    }

    private void addNewStudent(HttpExchange httpExchange, int classId) throws IOException, DBException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/createUpdateStudent.twig");
            JtwigModel model = JtwigModel.newModel();

            response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);

        }

        if (method.equals("POST")) {
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = DataParser.parseFormData(formData);

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


            studentDao.createStudent(user, student);

            br.close();
            isr.close();
            String url = "/mentor/students";
            UtilityService.sendRedirect(httpExchange, url);
        }
    }


    private void showMyStudents(HttpExchange httpExchange, int id) throws IOException, DBException {
        List<Student> studentsList = new ArrayList<>();
        studentsList = studentDao.getStudentListFromRoom(id);

        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/studentList.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("listName", studentsList);

        String response = template.render(model);

        UtilityService.sendResponse(httpExchange, response);
    }




    private void showProfile(HttpExchange httpExchange, int id) throws DBException, IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/profileMentor.twig");
        JtwigModel model = JtwigModel.newModel();

        //User user = userDAO.seeProfile(id);

        Mentor user = mentorDAO.getMentorById(id);

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNum();
        String email = user.getEmail();
        String address = user.getAddress();
        int room = user.getRoomID();

        model.with("firstName", firstName);
        model.with("lastName", lastName);
        model.with("phoneNum", phoneNumber);
        model.with("email", email);
        model.with("address", address);
        model.with("class", room);

        String response = template.render(model);

        UtilityService.sendResponse(httpExchange, response);
    }



    private String[] parseResponseURi(String uri) {
        //System.out.println("PARSING DATA");
        String[] splitUri = uri.split("/");

        for (String element : splitUri
        ) {
            System.out.println(element);
        }
        return splitUri;

    }

    private void showQuests(HttpExchange httpExchange, int UserId) throws IOException {

        User user = new Student();
        String method = httpExchange.getRequestMethod();
        try {
            user = userDAO.seeProfile(UserId);
        }catch(DBException e){
            e.printStackTrace();
        }

        if (method.equals("GET")){
            String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/mentor/studentsQuests.twig");
            JtwigModel model = JtwigModel.newModel();

            try{QuestDAO questDAO = new QuestDAO();
                List<Quest> questList = questDAO.getQuestsList();

                model.with("questList", questList);
            }catch (DBException e){
                e.printStackTrace();
            }

            String response = template.render(model);
            UtilityService.sendResponse(httpExchange, response);
        }

        else if (method.equals("POST")){
            Map<String, String> inputs = new HashMap<>();
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();

            System.out.println("form data: " + formData + "!!!!");
            inputs = DataParser.parseFormData(formData);
            int questId = Integer.valueOf(inputs.get("questId"));
            System.out.println(questId);

            try{
                questDAO.markAchievedQuests(questId, UserId);

                // add money to coolcoins
                int coolcoins = walletDAO.showWallet(UserId);
                int questCost = questDAO.getQuest(questId).getReward();
                int newCoinValue = coolcoins + questCost;

                studentDao.updateCoins(UserId, newCoinValue);

                //todo add coolcoins to level of exc
                int newExpPoints = studentDao.getExperiencePoints(UserId) + questDAO.getQuest(questId).getReward();
                studentDao.updateExpPoint(UserId, newExpPoints);


            }catch(DBException exc){
                exc.printStackTrace();
            }

            br.close();
            isr.close();
            String url = "/mentor/students";
            UtilityService.sendRedirect(httpExchange, url);
        }
    }
}

