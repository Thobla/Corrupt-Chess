package chessgame.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import chessgame.server.DataTypes.PawnData;

import java.io.IOException;
import java.util.HashMap;

public class GameServer {
    Server server;


    public GameServer () throws IOException{
        server = new Server();
        Network.register(server);
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
               if (object instanceof Packet) {
                  server.sendToAllTCP(object);
                  //connection.sendTCP(object);
                  
               }
               if (object instanceof PlayerAction) {
            	   server.sendToAllTCP(object);
            	   //connection.sendTCP(object);
               }
               if (object instanceof HashMap) {
            	   server.sendToAllTCP(object);
               }
            }
         });
        
        server.start();
        server.bind(54555);
        
        
        //Network.register(server);
        

    }

// call this to stop the server.
    public void stopServer() {
        server.stop();
    }

}
