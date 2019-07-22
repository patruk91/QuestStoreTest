package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.DBException;
import dao.LoginDAO;
import dao.QuestDAO;
import dao.SessionDAO;
import model.items.Quest;
import model.users.EmptyUser;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoginController implements HttpHandler {


    private User user;
    LoginDAO loginDao = new LoginDAO();
    SessionDAO sessionDao = new SessionDAO();



    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String uriStr = httpExchange.getRequestURI().toString();

        if (method.equals("GET")) {
            //here we check again is any new coookie and destroy it
            String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
            if (cookieStr != null){
                HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
                cookie.setMaxAge(0);
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
            }

            //render login template
            JtwigTemplate template = JtwigTemplate.classpathTemplate("/templates/login.twig");
            JtwigModel model = JtwigModel.newModel();
            String response = template.render(model);


            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        // todo how to get cookie value by its name not number of cookie
        //todo remove cookie after session ends
        //todo logout

        if (method.equals("POST")){

            //get data from form
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            List<String> parsedForm = parseFormData(formData);

            //First element on list is login  second is password
            List<String> inputs = this.parseFormData(formData);
            System.out.println(inputs);

            //check is user in database
            if (loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).isPresent()){
                System.out.println("user exist");
                String userType = loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).get().getUserType();
                int userId = loginDao.getUserByLogin(inputs.get(0), inputs.get(1)).get().getId();
                String url = "";
                url= "/" + userType;

                //create cookie with session ID
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                HttpCookie cookie = new HttpCookie("sessionId", randomUUIDString);

                //save sessionId to sessions table
                try{
                    sessionDao.addSession(randomUUIDString, userId);
                }catch (DBException exc ){
                    System.out.println("DB exception caought in loginController");
                }


                //prepare response with redirect to other page
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
                httpExchange.getResponseHeaders().set("Location", url);
                httpExchange.sendResponseHeaders(303, -1);

            }

            else {

                String response = "<html><body>" +
                        "<h1> Sorry there is no such user " +
                        "!</h1></body><html>";

                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }


        }


    }

    private List<String> parseFormData(String formData) throws UnsupportedEncodingException {
        List<String> result = new ArrayList<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            result.add(value);
        }
        return result;
    }


    private void update() {

    }

    private void delete() {

    }
}
