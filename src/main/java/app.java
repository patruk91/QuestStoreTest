import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class app {
    public static void main(String[] args) throws Exception {
        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // set routes
        //server.createContext();

        server.setExecutor(null); // creates a default executor

        // start listening
        server.start();
    }

}
