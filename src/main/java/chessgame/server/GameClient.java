package chessgame.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import chessgame.app.Game;

public class GameClient implements IClient{
	Client client;
	String IpAddress;
	Game game;
	
	public GameClient(String IpAddress) throws IOException{
		this.IpAddress = IpAddress;
		this.client = new Client();
		this.client.start();
		
		Network.register(client);
		
		this.client.connect(5000, this.IpAddress, 54555);
		
		client.addListener(new Listener() {
		       public void received (Connection connection, Object object) {
		          if (object instanceof Packet) {
		             Packet packet = (Packet)object;
		             
		             //client og host bruker send to tcp
		             //what to do with the data
		             
		             game.handlePacket(packet);
		             
		          }
		       }
		    });
	}
	
	public Client getClient(){
		return this.client;
	}
}
