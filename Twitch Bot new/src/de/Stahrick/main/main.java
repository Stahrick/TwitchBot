package de.Stahrick.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
//import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class main{

public static HashMap<String, String> ViewerList;
public static HashMap<String, Long> ViewerListms;
public static String Streamername = null;
public static String channel = "#" + Streamername;
public static String Botname = null;
public static String oauthtoken = null;
public static String userName = System.getProperty("user.name");
public static String directory = userName + File.separator + "Documents/TwitchBot/";
public static String Activitycheck;
public static String Aktivaetszeit;
public static int Aktivaetszeitint = Integer.parseInt(Aktivaetszeit);
public static TwitchBot bot;

public void onEnable() {
	Configcreation();
	Filecheck();
	Filereading();
}

public void onDisable() {
	Filesaving();
	bot.disconnect();
	ViewerList.clear();
	ViewerListms.clear();
}
	
	public static void botconfiguration() {
		
		bot = new TwitchBot();
		bot.setVerbose(true);
	
	Timer timer = new Timer();
	timer.schedule(new TimerTask() {
		@Override
		public void run() {
			//class Viewercheck();
		}
		
	}, 0, 1800000);  //1800000 ms for 30 Minutes
}
	
	public static void botconnection() {
		if(Streamername != null) {
		if(oauthtoken != null) {
		if(Botname != null) {
			try {
				ConnectionWindow.status.setText("Connecting...");
				bot.connect("irc.twitch.tv", 6667, oauthtoken);
				bot.joinChannel(channel);
				bot.sendMessage(channel, "Piep Piep Assistant droped Piep Piep");
				ConnectionWindow.status.setText("Bot connected!");
			} catch (NickAlreadyInUseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IrcException e) {
				e.printStackTrace();
			}
		}else{JDialog dialog = new JDialog(); dialog.setAlwaysOnTop(true); JOptionPane.showMessageDialog(dialog, "Connection fehlgeschlagen, da der Botname nicht gesetzt wurde!", "Botname not set", JOptionPane.ERROR_MESSAGE);}
		}else{JDialog dialog = new JDialog(); dialog.setAlwaysOnTop(true); JOptionPane.showMessageDialog(dialog, "Connection fehlgeschlagen, da der oAuthToken nicht gesetzt wurde!", "oAuthToken not set", JOptionPane.ERROR_MESSAGE);}
		}else{JDialog dialog = new JDialog(); dialog.setAlwaysOnTop(true); JOptionPane.showMessageDialog(dialog, "Connection fehlgeschlagen, da dein Streamname nicht gesetzt wurde!", "Streamname not set", JOptionPane.ERROR_MESSAGE);}
	}


@SuppressWarnings( "unchecked" )
 public void Filereading() {
	File ViewerListfile = new File(directory + "ViewerList.txt");
	File ViewerListfilems = new File(directory + "ViewerListms.txt");   //Giveaway Gewinnchance File erstellen usw.
	
	try {																	//ViewerList
		FileInputStream f = new FileInputStream(ViewerListfile);
		ObjectInputStream s = new ObjectInputStream(f);
		ViewerList= (HashMap<String,String>)s.readObject();
		s.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	try {
		FileInputStream f = new FileInputStream(ViewerListfilems);
		ObjectInputStream s = new ObjectInputStream(f);
		ViewerListms = (HashMap<String, Long>)s.readObject();
		s.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
 }
 
 public void Filesaving() {
	 File ViewerListfile = new File(directory + "ViewerList.txt");
	 File ViewerListfilems = new File(directory + "ViewerListms.txt");
	 try {
		FileOutputStream f = new FileOutputStream(ViewerListfile);
		ObjectOutputStream s = new ObjectOutputStream(f);
		s.writeObject(ViewerList);
		s.flush();
		bot.sendMessage(Streamername, "Dateien wurden zu 50% gespeichert!");
		s.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	 try {
		 FileOutputStream f = new FileOutputStream(ViewerListfilems);
		 ObjectOutputStream s = new ObjectOutputStream(f);
		 s.writeObject(ViewerListms);
		 s.flush();
		 bot.sendMessage(Streamername, "Alle Dateien wurden abgespeichert!");
		 s.close();
	 } catch (FileNotFoundException e) {
		 e.printStackTrace();
	 } catch (IOException e) {
		 e.printStackTrace();
	 }
 }
 
 public void Configcreation() {
	File Config = new File(directory + "config.properties");
	if(!Config.exists()) {
		try {
			Config.mkdirs();
			Config.createNewFile();
			
			Properties prop = new Properties();
			OutputStream output = null;
			
			try{
				output = new FileOutputStream(Config);
				prop.setProperty("Streamername", null);
				prop.setProperty("Botname", null);
				prop.setProperty("oAuthToken", null);
				prop.setProperty("Activitycheck", null);
				prop.setProperty("Aktivitaetszeit", "30");
				prop.store(output, null);
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				if(output != null) {
					try{
						output.close();
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 }
 
 public static void Configreading() {
	 Properties prop = new Properties();
	 InputStream input = null;

		try {
			input = new FileInputStream(directory + "config.properties");
			prop.load(input);
			Botname = prop.getProperty("Botname");
			Streamername = prop.getProperty("Streamername");
			oauthtoken = prop.getProperty("oAuthToken");
			Activitycheck = prop.getProperty("Activitycheck");
			Aktivaetszeit = prop.getProperty("Aktivitaetszeit");
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(input != null) {
				try{
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
 }
 
 public void Filecheck() {
	File ViewerListfile = new File(directory + "ViewerList.txt");
	File ViewerListfilems = new File(directory + "ViewerListms.txt");
	if(!ViewerListfile.exists()) {
		try {
			ViewerListfile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	if(!ViewerListfilems.exists()) {
		try {
			ViewerListfilems.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
 }
/* 
 public void Autogiveawaycreation() {
	 File Autogiveaway = new File(directory + "Autogiveaway.txt");
	 if(!Autogiveaway.exists()) {
		 try {
			Autogiveaway.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 HashMap<String,List<String>> Example = new HashMap<String,List<String>>();
	 
	 try{
		 FileOutputStream f = new FileOutputStream(Autogiveaway);
		 ObjectOutputStream s = new ObjectOutputStream(f);
		 //s.writeObject(obj);
	 } catch(FileNotFoundException e){
		 e.printStackTrace();
	 } catch(IOException e) {
		 e.printStackTrace();
	 }
 }*/
}
//public static HashMap<String, Long> ViewerListms = new HashMap<String, Long>();
//public static HashMap<String, String> ViewerList = new HashMap<String, String>();