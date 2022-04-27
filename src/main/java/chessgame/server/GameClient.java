package chessgame.server;

import java.io.IOException;
import java.util.HashMap;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import chessgame.app.Game;

public class GameClient implements IClient{
	Client client;
	String IpAddress;
	
	public GameClient(Game game, String IpAddress) throws IOException{
		this.IpAddress = IpAddress;
		this.client = new Client();
		this.client.start();
		
		Network.register(client);
		
		
		this.client.connect(5000, "10.111.46.73", 54555);

		client.addListener(new Listener() {
		       public void received (Connection connection, Object object) {
		          if (object instanceof HashMap) {
		        	  game.handlePacket(object);
		             
		          }
		       }
		    });
	}
	
	public Client getClient(){
		return this.client;
	}
}
