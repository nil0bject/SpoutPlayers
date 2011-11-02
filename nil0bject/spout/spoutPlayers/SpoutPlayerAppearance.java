package nil0bject.spout.spoutPlayers;

//import nil0bject.spout.spoutPlayers.StringColour;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import net.minecraft.server.DataWatcher;
import net.minecraft.server.MathHelper;
import net.minecraft.server.Packet24MobSpawn;

import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.RenderDistance;
import org.getspout.spoutapi.player.SpoutPlayer;


public class SpoutPlayerAppearance {
	private final SpoutPlayers plugin;
	public HashMap<String, String> my = new HashMap<String, String>();
	

	public static String[] types = {
			"skin",
			"cape",
			"name",
			"grav",
			"jump",
			"fly",
			"walk",
			"swim",
			"air",
			"sight",
			"pack",
			"warn",
			"mob",
			"pm",
	};
	
	SpoutPlayer player;
	String username;
	String world;

	
	public SpoutPlayerAppearance(SpoutPlayers instance, Player player) {
		this.plugin = instance;
		this.player = (SpoutPlayer)player;
		this.username = player.getName();
		this.world = player.getWorld().getName();
		this.getAll();
	}
	
	public void refreshAll() {
		for (String type:my.keySet()){
			refresh(type);
		}
		
		
	}
	
	public void refresh(String type) {		
		if (this.my.get(type)!=null&&!this.my.get(type).equals("")&&!this.my.get(type).equals(".")) {
			if (type.equals("name")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("name"));
				SpoutManager.getAppearanceManager().setGlobalTitle(this.player, this.my.get("name"));
			} else if (type.equals("skin")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("skin"));
				SpoutManager.getAppearanceManager().setGlobalSkin(this.player, this.my.get("skin"));
			} else if (type.equals("cape")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("cape"));
				SpoutManager.getAppearanceManager().setGlobalCloak(this.player, this.my.get("cape"));
			} else if (type.equals("grav")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("grav"));
				player.setGravityMultiplier(Double.valueOf(this.my.get("grav")));
			} else if (type.equals("jump")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("jump"));
				player.setJumpingMultiplier(Double.valueOf(this.my.get("grav")));
			} else if (type.equals("fly")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("fly"));
				player.setCanFly(Boolean.valueOf(this.my.get("fly")));
			} else if (type.equals("walk")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("walk"));
				player.setWalkingMultiplier(Double.valueOf(this.my.get("walk")));
			} else if (type.equals("swim")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("swim"));
				player.setSwimmingMultiplier(Double.valueOf(this.my.get("swim")));
			} else if (type.equals("air")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("air"));
				player.setAirSpeedMultiplier(Double.valueOf(this.my.get("air")));
			} else if (type.equals("sight")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("sight"));
				player.setRenderDistance(RenderDistance.getRenderDistanceFromValue(Integer.valueOf(this.my.get("sight"))));
			} else if (type.equals("pack")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("pack"));
				player.setTexturePack(this.my.get("pack"));
			} else if (type.equals("warn")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("warn"));
				player.sendNotification("Warning", (this.my.get("warn")), Material.EGG);
				//this.set("warn", null);
				//this.setUrl(null, "warn", null);
			} else if (type.equals("pm")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("pm"));
				player.sendNotification("From: "+this.my.get("pm").substring(0, this.my.get("pm").indexOf(":")), (this.my.get("pm").substring(this.my.get("pm").indexOf(":")+1)), Material.TNT, Short.valueOf("0"), 5000);
				this.set("pm", null);
				this.setUrl(null, "pm", null);
			} else if (type.equals("mob")) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+this.username+" "+type+":"+this.my.get("mob"));
	
				DataWatcher tmp = null;
		        tmp = new DataWatcher();
		        Byte id = Byte.valueOf(this.my.get("mob"));
		
		        Location loc = player.getLocation();
		        Packet24MobSpawn packet = new Packet24MobSpawn();
		        packet.a = ((CraftPlayer) player).getEntityId();
		        packet.b = id.byteValue();
		        packet.c = MathHelper.floor(loc.getX() * 32.0D);
		        packet.d = MathHelper.floor(loc.getY() * 32.0D);
		        packet.e = MathHelper.floor(loc.getZ() * 32.0D);
		        packet.f = (byte) ((int) loc.getYaw() * 256.0F / 360.0F);
		        packet.g = (byte) ((int) (loc.getPitch() * 256.0F / 360.0F));
		        Field datawatcher;
		        try {
		            datawatcher = packet.getClass().getDeclaredField("h");
		            datawatcher.setAccessible(true);
		            datawatcher.set(packet, tmp);
		            datawatcher.setAccessible(false);
		        } catch (Exception e) {
		        	if (this.plugin.debug)  System.out.println("[SpoutPlayer]"+e);
		        }
		        for (Player p2 : Bukkit.getServer().getOnlinePlayers()) {
		            if(!player.getWorld().equals(p2.getWorld())) {
		                continue;
		            }
		            if (p2 == player) {
		                continue;
		            }
		            ((CraftPlayer) p2).getHandle().netServerHandler.sendPacket(packet);
		        }
	        }
		}//if !null
		
		
		
	}
/////////////////////GET//////////////////////////////
	
	public void getAll(){
		for (String type:types){
			this.set(type, getUrl(type));
		}
	}
	
	public String getUrl(String type) {
		String url = "";
		if (this.plugin.mysql) { //retrieve url from mysql
			Statement stmt = null;
        	ResultSet rs = null;
        	try {
        		Class.forName("com.mysql.jdbc.Driver");
        	} catch (Exception e) {
				e.printStackTrace();
			}

        	try {
        		Connection con = DriverManager.getConnection( "jdbc:mysql://"+this.plugin.cfg.getString("mysql.address")+":"+this.plugin.cfg.getProperty("mysql.port")+"/"+this.plugin.cfg.getString("mysql.database")+"?user="+this.plugin.cfg.getString("mysql.user")+"&password="+this.plugin.cfg.getString("mysql.pass"));
        		 stmt = con.createStatement();
        		 
        		 //retrieve the player type value
				try {					
					rs = stmt.executeQuery("SELECT world, "+type+" FROM SPplayers WHERE username='"+this.username+"'");
				    while (rs.next()) {
				    	if (rs.getString("world")!=null) {
					    	if (rs.getString("world").equals("")) {
					    		url = rs.getString(type);	
					    	} else 
					    		if (rs.getString("world").equals(this.world)) {	
						    		url = rs.getString(type);
						    		break;
					    	}
				    	}
				    }
				    
				} catch (Exception e) {
					if (this.plugin.debug)  System.out.println("[SpoutPlayer] exception: SELECT world, "+type+" FROM SPplayers WHERE username='"+username+"'");
				}
				if (url==null) { ///ummmmmmmm test this!!
					try {
						rs = stmt.executeQuery("SELECT world, "+type+" FROM SPplayers WHERE username='@global'");
					    while (rs.next()) {
				    		url = rs.getString(type);
				    		if (rs.getString("world")==this.world) {
					    		break;
					    	}
					    }
					} catch (Exception exc) {
						if (this.plugin.debug)  System.out.println("[SpoutPlayer] sql exception: could not retrieve @global "+type);
					}
				}
				if (url!=null) {
					try {
						rs = stmt.executeQuery("SELECT url FROM SP"+type+"s WHERE name='"+url+"'");
					    while (rs.next()) {
					    		url = rs.getString("url");
								if (this.plugin.debug)  System.out.println("[SpoutPlayer] replaced "+type+" with "+url);
					    }
					} catch (Exception e) {
						if (this.plugin.debug)  System.out.println("[SpoutPlayer] sql exception: could not retrieve value for "+url+"'s in SP"+type+"s");
					}	
				}
        	} catch (Exception e) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer] sql exception: could not connect to database");
        	}
		} else { //retrieve url from config.yml
			
			if (this.plugin.cfg.getProperty("players."+username+"."+type)!=null) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer] config:"+type+" "+username+" "+ this.plugin.cfg.getString("players."+username+"."+type));
				url = this.plugin.cfg.getString("players."+username+"."+type);
			} else if (this.plugin.cfg.getProperty("players.@global."+type)!=null) {
					url = this.plugin.cfg.getString("players.@global."+type);
			} else {
				url = null;
			}
			if (this.plugin.cfg.getProperty(type+"s."+url)!=null) {
				url = this.plugin.cfg.getString(type+"s."+url);
			}
		}
		//cleanup url
		if (url != null) {
			if (url.contains("*")) url = url.replace("*", username);
		}
		//return
    	return url;
	}
//////////////////////////////////////////////////////////////////
	
//////////////////////SET/////////////////////////////////////////
	
	
	public void set(String type, String value) {
		if (this.plugin.debug)  System.out.println("[SpoutPlayer] set:"+type+" "+username+" "+ value);
		this.my.put(type, value);
			
	}
	
	
	public void setWorld(String world, String skin, String cloak) {

	}
	
	public void setUrl(String world, String type, String url) {
		if (this.plugin.mysql) { //save url to mysql
			Statement stmt = null;
        	try {
        		Class.forName("com.mysql.jdbc.Driver");
        	} catch (Exception e) {
				e.printStackTrace();
			}

        	try {
        		Connection con = DriverManager.getConnection( "jdbc:mysql://"+this.plugin.cfg.getString("mysql.address")+":"+this.plugin.cfg.getProperty("mysql.port")+"/"+this.plugin.cfg.getString("mysql.database")+"?user="+this.plugin.cfg.getString("mysql.user")+"&password="+this.plugin.cfg.getString("mysql.pass"));
        		 stmt = con.createStatement();
        		 //retrieve the player type value
				try {
					ResultSet rs = null;
					rs = stmt.executeQuery("SELECT username FROM SPplayers WHERE username='"+this.username+"' AND world='"+world+"'");
					if (rs.next()){
						stmt.executeUpdate("UPDATE SPplayers SET world='"+world+"',"+type+"='"+url+"' WHERE username='"+this.username+"' AND world='"+world+"'");
					} else {
						stmt.executeUpdate("INSERT INTO SPplayers (username,world,"+type+") VALUES ('"+this.username+"','"+world+"','"+url+"')");
					}
				} catch (Exception e) {
					if (this.plugin.debug)  System.out.println("[SpoutPlayer] Tried to save, but the SELECT, UPDATE OR INSERT failed");
				}
        	} catch (Exception e) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer] sql exception: could not connect to database");
        	}
		} else {
			this.plugin.cfg.setProperty("players."+this.username+"."+type,url);
			this.plugin.cfg.save();
		}
	}
	
	public void setType(String type, String name, String value) {
		if (this.plugin.mysql) { //save url to mysql
			Statement stmt = null;
        	try {
        		Class.forName("com.mysql.jdbc.Driver");
        	} catch (Exception e) {
				e.printStackTrace();
			}

        	try {
        		Connection con = DriverManager.getConnection( "jdbc:mysql://"+this.plugin.cfg.getString("mysql.address")+":"+this.plugin.cfg.getProperty("mysql.port")+"/"+this.plugin.cfg.getString("mysql.database")+"?user="+this.plugin.cfg.getString("mysql.user")+"&password="+this.plugin.cfg.getString("mysql.pass"));
        		 stmt = con.createStatement();
        		 //retrieve the player type value
				try {
					ResultSet rs = null;
					rs = stmt.executeQuery("SELECT name FROM SP"+type+"s WHERE name='"+name+"'");
					if (rs.next()){
						stmt.executeUpdate("UPDATE SP"+type+"s SET url='"+value+"' WHERE name='"+name+"'");
					} else {
						stmt.executeUpdate("INSERT INTO SP"+type+"s (name, url) VALUES ('"+name+"','"+value+"')");
					}
				} catch (Exception e) {
					if (this.plugin.debug)  System.out.println("[SpoutPlayer] Tried to save, but the SELECT, UPDATE OR INSERT failed");
				}
        	} catch (Exception e) {
				if (this.plugin.debug)  System.out.println("[SpoutPlayer] sql exception: could not connect to database");
        	}
		} else {
			this.plugin.cfg.setProperty(type+"s."+name,value);
			this.plugin.cfg.save();
		}
		
	}
//////////////////////////////////////////////////////////////////
	

/////////////////////RESET/////////////////////////////////////
	
	public void reset(String type) {
		if (type.equals("name")) {
	    	SpoutManager.getAppearanceManager().resetGlobalTitle(player); //reset the skin
		} else if (type.equals("skin")) {
	    	SpoutManager.getAppearanceManager().resetGlobalSkin(player); //reset the skin
		} else if (type.equals("cape")) {
			SpoutManager.getAppearanceManager().resetGlobalCloak(player); //reset the cloak
		} else if (type.equals("grav")) {
			this.my.put("grav", "0");
		} else if (type.equals("warn")) {
			this.my.put("warn", "");
		}
    }
	
	
	
	


	
/////////////////////////////////////////////////////////////////////////

}
