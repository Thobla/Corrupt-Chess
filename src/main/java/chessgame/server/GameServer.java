package chessgame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer {
    Server server;


    public GameServer () throws IOException{
        server = new Server();
        Network.register(server);
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
               if (object instanceof Packet) {
                  server.sendToAllTCP(object);
                  
               }
               if (object instanceof PlayerAction) {
            	   server.sendToAllTCP(object);
               }
            }
         });
        
        
        server.bind(54555);
        server.start();
        
        //Network.register(server);
        

    }


}
