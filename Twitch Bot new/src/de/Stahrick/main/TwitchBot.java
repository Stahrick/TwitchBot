package de.Stahrick.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class TwitchBot extends PircBot{
	
HashMap<String, Long> Viewertimecheck;

	public TwitchBot(){
		this.setName(main.Botname);
	}
	protected void onJoin(String channel, String sender, String login, String hostname) {
		if(channel.equalsIgnoreCase(main.channel)) {
			if(!sender.equalsIgnoreCase(main.Botname)) {
				if(!main.ViewerListms.containsKey(sender)) {
				long Date = System.currentTimeMillis();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date currentdate = new Date();
				Viewertimecheck.put(sender, Date);
				main.ViewerList.put(sender, dateFormat.format(currentdate));
				}
			}else{
				for(User user : this.getUsers(main.channel)) {
					if(!user.getNick().equalsIgnoreCase(main.Botname)) {
						if(!main.ViewerListms.containsKey(user.getNick())) {
							long Date = System.currentTimeMillis();
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
							Date currentdate = new Date();
							Viewertimecheck.put(sender, Date);
							main.ViewerList.put(user.getNick(), dateFormat.format(currentdate));
						}
					}
			  }
			}
		}else{
			return;
		}
	}
	protected void onQuit(String sourceNick, String sourceLogin, String sourceHostname, String reason) {
		if(!sourceNick.equalsIgnoreCase(main.Botname)) {
			if(!reason.equalsIgnoreCase("ban")) {
				long Date = System.currentTimeMillis();
				long oldtime = Viewertimecheck.get(sourceNick);
				long difference = Date - oldtime;
				if(difference >= 1800000) {
					Viewertimecheck.remove(sourceNick);
					main.ViewerListms.put(sourceNick, oldtime);
					return;
				}else{
					if(!main.ViewerListms.containsKey(sourceNick)) {
						Viewertimecheck.remove(sourceNick);
						main.ViewerList.remove(sourceNick);
					}else{
						Viewertimecheck.remove(sourceNick);
					}
				}
		  }else{
			  if(main.ViewerListms.containsKey(sourceNick)) {
				  main.ViewerListms.remove(sourceNick);
				  main.ViewerList.remove(sourceNick);
			  }
		  }
		}else{
			
		}
	}
	protected void onDisconnect() {
		try {
			this.reconnect();
		} catch (NickAlreadyInUseException e) {
			e.printStackTrace();
			this.changeNick(main.Botname + "*");
			this.kick(main.channel, main.Botname);
			this.changeNick(main.Botname);
			ConnectionWindow.status.setText("Botname bereits vergeben!");
			try {
				this.reconnect();
			} catch (IOException | IrcException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}
}

