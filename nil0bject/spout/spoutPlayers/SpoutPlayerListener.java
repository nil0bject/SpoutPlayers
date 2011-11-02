package nil0bject.spout.spoutPlayers;


//import org.getspout.spoutapi.SpoutManager;
//import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
//import org.getspout.spoutapi.event.spout.SpoutListener;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpoutPlayerListener extends PlayerListener {
    //@SuppressWarnings("unused")
	private final SpoutPlayers plugin;

    public SpoutPlayerListener(SpoutPlayers instance) {
        this.plugin = instance;
    }

    //EVENTS//
    
    @Override
    public void onPlayerJoin (final PlayerJoinEvent event) {
    	Player player = event.getPlayer(); // Get a player object from somewhere
    	this.plugin.addPlayer(player);
    }
    
    
    @Override
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
    	Player player = event.getPlayer(); // Get a player object from somewhere
    	this.plugin.respawns.add(player.getName());
	}
    
    
    @Override
    public void onPlayerQuit(final PlayerQuitEvent event) {
    	Player player = event.getPlayer(); // Get a player object from somewhere
    	this.plugin.removePlayer(player);
    }
    
}

