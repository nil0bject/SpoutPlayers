package nil0bject.spout.spoutPlayers;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.*;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class SpoutPlayers extends JavaPlugin {
	public  boolean debug = false;
	public  boolean mysql = false;
	public  long updateAppearanceDelay=100L;
	public  PluginDescriptionFile pdf;
	public  Configuration cfg;
    public final SpoutPlayerListener playerlistener = new SpoutPlayerListener(this);
    public  ArrayList<String> respawns = new ArrayList<String>();
    public  HashMap<String, SpoutPlayerAppearance> appearances = new HashMap<String, SpoutPlayerAppearance>();


	@Override
	public void onDisable() {
		// cancel tasks
		this.getServer().getScheduler().cancelTasks(this);
        if (this.debug) System.out.println("[" + this.pdf.getName() + "] disabled.");
	}

	@Override
	public void onEnable() {
		// initialise vars
		this.pdf=this.getDescription();
		// open config.yml
        if (!new File(getDataFolder(), "config.yml").exists()) {
            try {
                getDataFolder().mkdir();
                new File(getDataFolder(),"config.yml").createNewFile();
            } 
            catch(Exception e) {
                e.printStackTrace();
                if (this.debug) System.out.println("[" + this.pdf.getName() + "]Could not create "+ getDataFolder().getAbsolutePath() +"/config.yml");
                getServer().getPluginManager().disablePlugin(this); //Cleanup
                return;
            }
        }
        
        ////////////////CONFIGURATION/////////////////////
        this.cfg = this.getConfiguration(); // Get config
        //create default config
        if (this.cfg.getKeys().isEmpty()) { // Config hasn't been made
            if (this.debug) System.out.println("[" + this.pdf.getName() + "]"+ getDataFolder().getAbsolutePath() +"/config.yml is empty. Addding default values");
            this.cfg.setProperty("mysql.address", "");
            this.cfg.setProperty("mysql.port", "3306");
            this.cfg.setProperty("mysql.database", "");
            this.cfg.setProperty("mysql.user", "");
            this.cfg.setProperty("mysql.pass", "");
            this.cfg.setProperty("skins.Notch", "http://minecraft.net/skin/Notch.png");
            this.cfg.setProperty("players.@global.skin", "Notch");
            this.cfg.setProperty("capes.1M", "http://www.minecraftwiki.net/images/f/f7/1MCape.png");
            this.cfg.setProperty("players.@global.cape", "1M");
            this.cfg.setProperty("gravs.low", "0.1");
            this.cfg.setProperty("players.@global.grav", "low");
            this.cfg.setProperty("preferences.debug", this.debug);
            this.cfg.setProperty("preferences.updateFreq", this.updateAppearanceDelay);
            this.cfg.setProperty("preferences.mysql", this.mysql);
            cfg.save();
        }

        //load preferences
        this.debug = this.cfg.getBoolean("preferences.debug", true);
        this.updateAppearanceDelay = Long.valueOf(this.cfg.getString("preferences.updateFreq"));
        this.mysql = this.cfg.getBoolean("preferences.mysql", false);
        
        if (mysql) {
        	Statement stmt = null;
        	try {
        		Class.forName("com.mysql.jdbc.Driver");
        	} catch (Exception e) {
				e.printStackTrace();
			}

        	try {
        		Connection con = DriverManager.getConnection( "jdbc:mysql://"+cfg.getString("mysql.address")+":"+cfg.getProperty("mysql.port")+"/"+cfg.getString("mysql.database")+"?user="+cfg.getString("mysql.user")+"&password="+cfg.getString("mysql.pass"));
				
        		stmt = con.createStatement();
        		
        		//check for existance, create if null

        		//tables
			    try {
			    	stmt.executeUpdate("ALTER TABLE "+cfg.getString("mysql.database")+".SPplayers MODIFY COLUMN skin TEXT  CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL, MODIFY COLUMN cape TEXT CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;");
			    	stmt.executeQuery("SELECT * FROM SPplayers");
			    } catch (Exception e) {
					stmt.executeUpdate("CREATE TABLE  "+cfg.getString("mysql.database")+".SPplayers (username char(32) PRIMARY KEY NOT NULL, world char(32) DEFAULT NULL) ENGINE=MyISAM DEFAULT CHARSET=latin1");
				}
			    
			    for (String type: SpoutPlayerAppearance.types) {
				    try {
						stmt.executeQuery("SELECT * FROM SP"+type+"s");
					} catch (Exception e) {
						stmt.executeUpdate("CREATE TABLE  "+cfg.getString("mysql.database")+".SP"+type+"s (  name char(32) NOT NULL PRIMARY KEY,  url text NOT NULL) ENGINE=MyISAM DEFAULT CHARSET=latin1");
					}
					try {//columns
						stmt.executeQuery("SELECT "+type+" FROM SPplayers");
					} catch (Exception e) {
						stmt.executeUpdate("ALTER TABLE SPplayers ADD COLUMN "+type+" text DEFAULT NULL");
					}
			    }		    
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	if (this.debug) System.out.println("[" + this.pdf.getName() + "] finished loading mysql");
        }
        
        if (this.debug) System.out.println("[" + this.pdf.getName() + "] finished loading config");

        
        
        PluginManager pm = getServer().getPluginManager();
        this.getCommand("set").setExecutor(new SpoutPlayerCommands(this));
        if (this.debug) System.out.println("[" + this.pdf.getName() + "] set SpoutPlayerCommands to executor of /set");
        //pm.registerEvent(Event.Type.CUSTOM_EVENT, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerlistener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerlistener, Priority.Normal, this); //Priority.monitor

        //refresh skins and thing
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

            public void run() {
            	if (!appearances.isEmpty()) {
            		for(String name:appearances.keySet()){
            			if(appearances.get(name)!=null){
            				appearances.get(name).refreshAll();
            			}
            		}
            	}
            }
        }, 0L, this.updateAppearanceDelay);

        System.out.println("[" + this.pdf.getName() + "]"  + " by " + this.pdf.getAuthors().get(0) + " version " + this.pdf.getVersion() + " enabled.");

        
	}

	public void removePlayer(Player player) {
		String name = player.getName();
		appearances.remove(name);
		if (this.debug) System.out.println("[" + this.pdf.getName() + "] Removed player:"  + name);

	}

	public void addPlayer(Player player) {
		String name = player.getName();
		SpoutPlayerAppearance appearance = new SpoutPlayerAppearance(this, player);
		if (this.debug) System.out.println("[" + this.pdf.getName() + "] Added player:"  + appearance.player.getName());

	    appearance.refreshAll();
        appearances.put(name, appearance);
	}



}
