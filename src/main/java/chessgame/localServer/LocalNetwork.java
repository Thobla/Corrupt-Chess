package chessgame.localServer;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.util.Date;

public class LocalNetwork extends Listener {

//Server object
static Server server;
    //Ports to listen on
    static int udpPort = 27960, tcpPort = 27960;

    public static void main(String[] args) throws Exception {
        System.out.println("Creating the server...");
        //Create the server
        server = new Server();

        //Register a packet class.
        server.getKryo().register(PacketMessage.class);
        //We can only send objects as packets if they are registered.

        //Bind to a port
        server.bind(tcpPort, udpPort);

        //Start the server
        server.start();

        //Add the listener
        server.addListener(new LocalNetwork());

        System.out.println("Server is operational!");
    }

    //This is run when a connection is received!
    public void connected(Connection c){
        System.out.println("Received a connection from "+c.getRemoteAddressTCP().getHostString());
        //Create a message packet.
        PacketMessage packetMessage = new PacketMessage();
        //Assign the message text.
        packetMessage.message = "Hei";

        //Send the message
        c.sendTCP(packetMessage);
        //Alternatively, we could do:
        //c.sendUDP(packetMessage);
        //To send over UDP.
    }

    //This is run when we receive a packet.
    public void received(Connection c, Object p){
        //We will do nothing here.
        //We do not expect to receive any packets.
        //(But if we did, nothing would happen)
    }

    //This is run when a client has disconnected.
    public void disconnected(Connection c){
        System.out.println("A client disconnected!");
    }
}
