package clientServer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.Scanner;

public class ClientNetwork extends Listener {

    //Our client object.
    static Client client;
    //IP to connect to.
    static String ip = "LocalHost"; // change localhoast to userchoice
    //Ports to connect on.
    static int tcpPort = 27960, udpPort = 27960;

    //A boolean value.
    static boolean messageReceived = false;


    public static void main(String[] args) throws Exception {
        //Create the client.
        client = new Client();

        Kryo kryo = client.getKryo();
        //Register the packet object.
        kryo.register(PacketMessage.class);

        System.out.println("Connecting to the server...");

        //Start the client
        //The client MUST be started before connecting can take place.
        new Thread(client).start();

        //Connect to the server - wait 5000ms before failing.
        // increase if needed.
        client.connect(50000, ip, tcpPort, udpPort);

        //Add a listener
        client.addListener(new ClientNetwork());

        System.out.println("Connected! The client program is now waiting for a packet...\n");

        //This is here to stop the program from closing before we receive a message.
        while(client.isConnected()){
//            Thread.sleep(50000000);
            client.setKeepAliveTCP(999999999);
            System.out.println("hva skjer 1234");
            Scanner msg = new Scanner(System.in);
            PacketMessage packetMessage = new PacketMessage();
            String message = msg.nextLine();
            //client.update(999999999);

        }
        // when player dies, client will exit.
        System.out.println("Client will now exit.");
        //System.exit(0);

    }
    //This is run when a connection is received!
    public void connected(Connection c){
        System.out.println("Received a connection from "+c.getRemoteAddressTCP().getHostString());
        while(!messageReceived){
            //Reads message
            Scanner msg = new Scanner(System.in);
            System.out.println("venter");

            String message = msg.nextLine();
            //Create a message packet.
            PacketMessage packetMessage = new PacketMessage();
            //Assign the message text.
            packetMessage.message = "This is a message from ClientServer: "+ message;

            //Send the message
            c.sendTCP(packetMessage);


        }
    }


    //I'm only going to implement this method from Listener.class because I only need to use this one.
    public void received(Connection connection, Object object){
        //Is the received packet the same class as PacketMessage.class?
        if(object instanceof PacketMessage){


            //Cast it, so we can access the message within.
            PacketMessage fromHost = (PacketMessage) object;
            System.out.println("received a message from the host: "+fromHost.message);

            //We have now received the message!
            messageReceived = true;
        }
//        else{
//            while(!(object instanceof PacketMessage)){
//                //Reads message
//                Scanner msg = new Scanner(System.in);
//
//                String message = msg.nextLine();
//                //Create a message packet.
//                PacketMessage packetMessage = new PacketMessage();
//                //Assign the message text.
//                packetMessage.message = "This is a message from Localserver: "+ message;
//
//                //Send the message
//                client.sendTCP(packetMessage);
//
//            }
//
//        }
    }
}
