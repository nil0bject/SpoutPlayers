package nil0bject.spout.spoutPlayers;

import org.bukkit.ChatColor;

	public class StringColour {
	    private StringBuilder _builder;
	    
	    StringColour() {
	        this._builder = new StringBuilder();
	    }
	    
	    public StringColour newLine() {
	        this._builder.append("\n");
	        return this;
	    }
	    
	    public StringColour append(ChatColor color, String text) {
	        this._builder.append(color).append(text);
	        return this;
	    }
	    
	    public StringColour aqua(String text) {
	        return this.append(ChatColor.AQUA, text);
	    }
	    
	    public StringColour black(String text) {
	        return this.append(ChatColor.BLACK, text);
	    }
	    
	    public StringColour blue(String text) {
	        return this.append(ChatColor.BLUE, text);
	    }
	    
	    public StringColour darkaqua(String text) {
	        return this.append(ChatColor.DARK_AQUA, text);
	    }
	    public StringColour darkblue(String text) {
	        return this.append(ChatColor.DARK_BLUE, text);
	    }
	    public StringColour darkgray(String text) {
	        return this.append(ChatColor.DARK_GRAY, text);
	    }
	    public StringColour darkgreen(String text) {
	        return this.append(ChatColor.DARK_GREEN, text);
	    }
	    public StringColour darkpurple(String text) {
	        return this.append(ChatColor.DARK_PURPLE, text);
	    }
	    public StringColour darkred(String text) {
	        return this.append(ChatColor.DARK_RED, text);
	    }
	    public StringColour gold(String text) {
	        return this.append(ChatColor.GOLD, text);
	    }
	    public StringColour gray(String text) {
	        return this.append(ChatColor.GRAY, text);
	    }
	    public StringColour green(String text) {
	        return this.append(ChatColor.GREEN, text);
	    }
	    public StringColour lightpurple(String text) {
	        return this.append(ChatColor.LIGHT_PURPLE, text);
	    }
	    public StringColour red(String text) {
	        return this.append(ChatColor.RED, text);
	    }
	    public StringColour white(String text) {
	        return this.append(ChatColor.WHITE, text);
	    }
	    public StringColour yellow(String text) {
	        return this.append(ChatColor.YELLOW, text);
	    }
	    public String toString() {
	        return this._builder.toString();
	    }
	    public String end() {
	        return this.toString();
	    }
	    public StringColour rainbowWords(String... words) {
	        int readcount = 0;
	        ChatColor[] colors = ChatColor.values();
	        while (readcount < words.length) {
	            for (int i = 0;i < colors.length;i++) {
	                this._builder.append(colors[i]).append(words[readcount]);
	                readcount += 1;
	                if (readcount == words.length) break;
	            }
	        }
	        return this;
	    }
	    public StringColour rainbow(String text) {
	        int readcount = 0;
	        ChatColor[] colors = ChatColor.values();
	        while (readcount < text.length()) {
	            for (int i = 0;i < colors.length;i++) {
	                this._builder.append(colors[i]).append(text.charAt(readcount));
	                readcount += 1;
	                if (readcount == text.length()) break;
	            }
	        }
	        return this;
	    }
	}