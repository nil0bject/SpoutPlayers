package nil0bject.spout.spoutPlayers;

import nil0bject.spout.spoutPlayers.SpoutPlayers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * @author nil0bject
 *
 */
public class SpoutPlayerCommands implements CommandExecutor {
    //@SuppressWarnings("unused")
    private final SpoutPlayers plugin;

    public SpoutPlayerCommands(SpoutPlayers instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
    	String name = sender.getName();
    	Player player = null;
    	if(args.length > 0){
    		name = args[0];
    		player = this.plugin.getServer().getPlayer(name);
    	}

	//// /type ////
		if(args.length == 1){ //// /sp <name>
			
			String title = SpoutManager.getAppearanceManager().getTitle((SpoutPlayer)player, player);
			String skinUrl = SpoutManager.getAppearanceManager().getSkinUrl((SpoutPlayer)player, player);
			String cloakUrl = SpoutManager.getAppearanceManager().getCloakUrl((SpoutPlayer)player, player);
			Double gravity = ((SpoutPlayer)player).getGravityMultiplier();
			sender.sendMessage("[" + this.plugin.pdf.getName() + "]"+title);
			sender.sendMessage("[" + this.plugin.pdf.getName() + "]skin: "+skinUrl);
			sender.sendMessage("[" + this.plugin.pdf.getName() + "]cape: "+cloakUrl);
			sender.sendMessage("[" + this.plugin.pdf.getName() + "]gravity: "+gravity);

			return true;
    	}

    	if(args.length == 2){
    		
    		
    		
    		if (sender.hasPermission("sp."+args[1]+".un"+args[1])) {
    			this.plugin.appearances.get(name).reset(args[1]);
    			if (sender.hasPermission("sp."+args[0]+".save")) {
	    			this.plugin.appearances.get(name).setUrl("", args[1], null);
    			}
    			this.plugin.appearances.get(name).reset(args[1]);
    			player.sendMessage("[" + this.plugin.pdf.getName() + "]Your "+args[1]+" has been reset!");
    			sender.sendMessage("[" + this.plugin.pdf.getName() + "]"+name+"s "+args[1]+" has been reset!");
				return true;
    		} else {
    			if (this.plugin.debug) System.out.println("[" + this.plugin.pdf.getName() + "]"+sender.getName()+" tried to use /"+args[0]);
				return false;
    		}
    	}
    		
    		
    		
    		
    		
    	if(args.length == 3){
    		if(((args[0].equals("@skin"))||(args[0].equals("@name"))||(args[0].equals("@cape"))||(args[0].equals("@grav")))){// /set <type> <name> <value>
    			if ((sender.hasPermission("sp."+args[0].substring(1)+".save"))) {
	    			this.plugin.appearances.get(sender.getName()).setType(args[0].substring(1), args[1], args[2]);
	    			sender.sendMessage("[" + this.plugin.pdf.getName() + "]set type:"+args[0].substring(1)+" name:"+args[1]+" value:"+args[2]+"!");

					return true;
				} else {
	    			sender.sendMessage("[" + this.plugin.pdf.getName() + "]You do not have save permissions!");

				}
			}
			// /set <username> <type> <value>
    		if (name.equals(sender.getName()) && !sender.hasPermission("sp."+args[1]+".changeOwn")) {
    			sender.sendMessage("[" + this.plugin.pdf.getName() + "]You do not have permission to change your own "+args[1]+"!");
    			return false;
    		}
    		if (!name.equals(sender.getName()) && !sender.hasPermission("sp."+args[1]+".changeAll")) {
    			sender.sendMessage("[" + this.plugin.pdf.getName() + "]You do not have permission to change other player "+args[1]+"s!");
    			return false;
			}
    		if (args[1].equals("pm")) {args[2] = sender.getName()+":"+args[2];} //add sender name to pm value
    		this.plugin.appearances.get(name).set(args[1], args[2]);
			if (sender.hasPermission("sp."+args[1]+".save")) {
    			this.plugin.appearances.get(name).setUrl("", args[1], args[2]);
			}
			this.plugin.appearances.get(name).getAll();
			this.plugin.appearances.get(name).refresh(args[1]);
			player.sendMessage("[" + this.plugin.pdf.getName() + "]Your "+args[1]+" has changed!");
			return true;
    	}
    	
    	
    	if(args.length == 4){

    	}

    	
    	if (this.plugin.debug) System.out.println("[" + this.plugin.pdf.getName() + "]"+commandLabel+":"+args+": Unknown command");
		return false;
    }

    
    
 
}


