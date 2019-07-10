package controller.helpers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class AdminMentorsListHandler implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        // client's address
        String userAgent = httpExchange.getRequestHeaders().getFirst("User-agent");

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorList.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // fill the model with values
        //model.with("client", userAgent);


        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();


    }
}
