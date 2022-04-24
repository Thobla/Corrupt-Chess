package chessgame.localServer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class LocalNetwork extends Listener {

static Server server;
    //Ports to listen on
    static int udpPort = 27960, tcpPort = 27960;
    static boolean messageReceived = false;

    public static void main(String[] args) throws Exception {

        System.out.println("Creating the server...");
        //Create the server
        server = new Server();
        Kryo kryo = server.getKryo();

        //Register a packet class.
        kryo.register(PacketMessage.class);
        //We can only send objects as packets if they are registered.

        //Bind to a port
        server.bind(tcpPort, udpPort);

        //Start the server
        server.start();
        //server.update(0);

        //Add the listener
        server.addListener(new LocalNetwork());

        System.out.println("Server is operational!");
    }

    //This is run when a connection is received!
    public void connected(Connection c){
        System.out.println("Received a connection from "+c.getRemoteAddressTCP().getHostString());
        //maybe change to c.isConnected

        while(c.isConnected()){
            //Reads message
            Scanner msg = new Scanner(System.in);

            String message = msg.nextLine();
            //Create a message packet.
            PacketMessage packetMessage = new PacketMessage();
            //Assign the message text.
            packetMessage.message = "This is a message from Localserver: "+ message;

            //Send the message
            c.sendTCP(packetMessage);




        }
    }

    //This is run when we receive a packet.
    public void received(Connection c, Object communication){
        //We will do nothing here.
        //We do not expect to receive any packets.
        //(But if we did, nothing would happen)
        if(communication instanceof PacketMessage){
            PacketMessage packet = (PacketMessage) communication;
            System.out.println("received a message from the client:" +packet.message);

            messageReceived = true;
        }
//        else {
//            while (!(communication instanceof PacketMessage)) {
//
//
//                //Reads message
//                Scanner msg1 = new Scanner(System.in);
//
//                String message = msg1.nextLine();
//                //Create a message packet.
//                PacketMessage packetMessage = new PacketMessage();
//                //Assign the message text.
//                packetMessage.message = "This is a message from Clientserver: " + message;
//
//                //Send the message
//                c.sendTCP(packetMessage);
//
//
//            }
//        }
    }

    //This is run when a client has disconnected.
    public void disconnected(Connection c){
        System.out.println("A client disconnected!");
    }
}
