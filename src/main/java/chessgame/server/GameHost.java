package chessgame.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import chessgame.utils.EntityManager;

public class GameHost implements IClient{
	Client client;
	String IpAddress;
	
	public GameHost(String IpAddress) throws IOException{
		this.IpAddress = IpAddress;
		this.client = new Client();
		this.client.start();
		
		Network.register(client);
		
		//needs to get the IP address
		this.client.connect(5000, this.IpAddress, 54555);
		
		client.addListener(new Listener() {
		       public void received (Connection connection, Object object) {
		          if (object instanceof PlayerAction) {
		             EntityManager action = (EntityManager)object;
		             
		             //what to do with the recived
		             
		          }
		       }
		    });
	}
	
	public Client getClient(){
		return this.client;
	}
}
