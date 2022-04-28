package chessgame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GameServer {
    static Server server;
    static int serverStatus = 0;


    public GameServer () throws IOException{
        if (serverStatus == 0) {

            server = new Server();
            Network.register(server);
            server.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof HashMap || object instanceof List || object instanceof PausePing) {
                        server.sendToAllTCP(object);
                    }
                }
            });

            server.start();
            server.bind(54555);
            serverStatus = 1;
        }

        //Network.register(server);

    }



// call this to stop the server.
    public void stopServer() {

        this.server.stop();
        System.out.println("was killed");
        serverStatus = 0;
    }

}
