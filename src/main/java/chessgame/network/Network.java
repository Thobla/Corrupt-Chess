package chessgame.network;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import org.lwjgl.system.CallbackI;

public class Network extends Listener {
    static Server server;

    static int udpPort = 27960, tcpPort = 27960;

    public static void main(String[] args) throws Exception{
        System.out.println("Creating the server ...");
        server = new Server();

        server.getKryo().register(PacketMessage.class);

        server.bind(tcpPort,udpPort);

        server.start();
        server.addListener(new Network());

        System.out.println("server is operational!");


    }
}
