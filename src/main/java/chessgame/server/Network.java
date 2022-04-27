package chessgame.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import chessgame.entities.Button;
import chessgame.entities.Door;
import chessgame.entities.Pawn;
import chessgame.entities.Player;
import chessgame.server.DataTypes.ButtonData;
import chessgame.server.DataTypes.DoorData;
import chessgame.server.DataTypes.PawnData;
import chessgame.server.DataTypes.PlayerData;
import chessgame.utils.EntityManager;


public class Network {
    static public final int port = 54555;
    
    // This registers objects that are going to be sent over the network.
    static public void register (EndPoint endPoint) {
        //Kryo kryo = endPoint.getKryo();
        //kryo.register(chessgame.server.Packet.class);
        
        Kryo kryo = endPoint.getKryo();
        //kryo.register(PlayerAction.class);
        //kryo.register(Packet.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(ButtonData.class);
        kryo.register(PawnData.class);
        kryo.register(DoorData.class);
        kryo.register(PlayerData.class);
        //kryo.register(EntityManager.class);
        kryo.register(Door.class);
        kryo.register(Pawn.class);
        kryo.register(Button.class);
        kryo.register(Player.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(com.badlogic.gdx.math.Vector2.class);
    }
}
