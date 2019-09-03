package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import helpers.DataParser;
import model.items.Level;
import model.users.Admin;
import model.users.Mentor;
import model.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

public class AdminController implements HttpHandler {
    private AdminHelper adminHelper;

    public AdminController(AdminHelper adminHelper) {
        this.adminHelper = adminHelper;
    }

    public void handle(HttpExchange httpExchange) {
        int id = 1;
        try {
            String uri = httpExchange.getRequestURI().toString();
            String[] parsedUri = adminHelper.parseResponseURi(uri);

            if (uri.equals("/admin/classes")) {
            } else if (uri.equals("/admin/levels")) {
                adminHelper.showLevels(httpExchange);
            } else if (uri.equals("/admin/mentors")) {
                adminHelper.showMentors(httpExchange);
            } else if (uri.equals("/admin")) {
                adminHelper.showProfile(httpExchange, id);
            } else if (uri.equals("/admin/createMentor")) {
                adminHelper.addNewMentor(httpExchange);
            } else if (parsedUri.length > 2 && parsedUri[2].equals("update")) {
                adminHelper.updateProfile(parsedUri[3], httpExchange);
            } else {
                adminHelper.showProfile(httpExchange, id);
            }
        } catch (IOException e) {
            System.out.println("IOException in AdminController");
        } catch (DBException e) {
            System.out.println("DBException in AdminController");
        } catch (Exception e) {
            System.out.println("Unidentified exception in AdminController");
        }
    }

}





