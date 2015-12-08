package de.Stahrick.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Giveaway {

	public static long randomtime;
	public static HashMap<Long, String> Giveawayleute;
	public static ArrayList<String> Giveawayausschluss;
	public static boolean giveawayon = false;
	public static long giveawayanzahl;
	public static int zuschaueranzahlraw;
	public static String codeword;
	
	static delay d = new delay();
	static Thread th = new Thread(d);
	
	public static void autogiveaway(){
		
		long currenttime = System.currentTimeMillis();
		long maxtime = currenttime +  3600000;
		Random r = new Random();
		randomtime = currenttime + ((long)(r.nextLong()*(maxtime-currenttime)));
		
		try{
			zuschaueranzahlraw = Integer.parseInt(GiveawayWindow.minzuschauer.getText());
		}catch(InputMismatchException exception){
			GiveawayWindow.error.start();
		}
		Timer timer = new Timer();
		TimerTask timertask = new TimerTask() {
			@Override
			public void run() {
				if(zuschaueranzahlraw <= main.bot.getUsers(main.channel).length) {
					if(System.currentTimeMillis() >= randomtime) {
						if(giveawayon == false) {
							giveawayon = true;
							giveawayanzahl = 0;
							codeword = "!giveaway";
							main.bot.sendMessage(main.channel, "Giveaway Time");
							main.bot.sendMessage(main.channel, "Giveaway Time");
							main.bot.sendMessage(main.channel, "Giveaway Time");
							main.bot.sendMessage(main.channel, "Schreibe jetzt !giveaway in den Chat ein. Um an dem Giveaway teilzunehmen.");
							main.bot.sendMessage(main.channel, "Bitte nur einmal f\u00FCr das Giveaway eintragen.");
							th.start();
						}
					}
				}
			}
		};timer.scheduleAtFixedRate(timertask, 0, 5000);
	}
	
	public static void giveaway() {
		try{
			zuschaueranzahlraw = Integer.parseInt(GiveawayWindow.minzuschauer.getText());
		}catch(InputMismatchException exception){
			GiveawayWindow.error.start();
		}
		if(zuschaueranzahlraw <= main.bot.getUsers(main.channel).length) {
			if(giveawayon == false) {
				giveawayon = true;
				giveawayanzahl = 0;
				if(!GiveawayWindow.giveawaykeyword.getText().equalsIgnoreCase("Giveaway Chat Keyword z.B. !giveaway")) {
					if(GiveawayWindow.giveawaykeyword.getText() != null) {
						codeword = GiveawayWindow.giveawaykeyword.getText();
					}
				}else{
					codeword = "!giveaway";
				}
					
				main.bot.sendMessage(main.channel, "Giveaway Time");
				main.bot.sendMessage(main.channel, "Giveaway Time");
				main.bot.sendMessage(main.channel, "Giveaway Time");
				main.bot.sendMessage(main.channel, "Schreibe jetzt " + codeword + " in den Chat ein. Um an dem Giveaway teilzunehmen.");
				main.bot.sendMessage(main.channel, "Bitte nur einmal f\u00FCr das Giveaway eintragen.");
				th.start();
			}
		}
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		if(codeword != "!giveaway") {
			if(message.equalsIgnoreCase("!giveaway")) {
				if(giveawayon == true) {
					if(!Giveawayleute.containsValue(sender)) {
						giveawayanzahl++;
						Giveawayleute.put(giveawayanzahl, sender);
					}else{
						Giveawayausschluss.add(sender);
					}
				}else{
					main.bot.sendMessage(main.channel, sender + "Es findet im Moment kein Giveaway statt.");
				}
			}
			if(message.equalsIgnoreCase(codeword)) {
				if(giveawayon == false) {
					if(!Giveawayleute.containsValue(sender)) {
						
					}else{
						Giveawayausschluss.add(sender);
					}
				}else{
					main.bot.sendMessage(main.channel, sender + "Es findet im Moment kein Giveaway statt.");
				}
			}
	}else{
		if(message.equalsIgnoreCase(codeword)) {
			if(giveawayon == true) {
				if(!Giveawayleute.containsValue(sender)) {
					giveawayanzahl++;
					Giveawayleute.put(giveawayanzahl, sender);
				}else{
					Giveawayausschluss.add(sender);
				}
			}else{
				main.bot.sendMessage(main.channel, sender + "Es findet im Moment kein Giveaway statt.");
			}
		}
	}
  }
}

class delay implements Runnable{
	@Override
	public void run() {
		try {
			Thread.sleep(60000);
			main.bot.sendMessage(main.channel, "Ihr habt noch 1 Minute Zeit am Giveaway teilzunehmen!");
			main.bot.sendMessage(main.channel, "Schreib " + Giveaway.codeword + " in den Chat um teilzunehmen!");
			Thread.sleep(50000);
			main.bot.sendMessage(main.channel, "Ihr habt noch 10 Sekunden Zeit am Giveaway teilzunehmen!");
			main.bot.sendMessage(main.channel, "Schreib " + Giveaway.codeword + " in den Chat um teilzunehmen!");
			Thread.sleep(10000);
			main.bot.sendMessage(main.channel, "Das Giveaway wurde beendet!");
			Giveaway.giveawayon = false;
			String winnername = null;
			while(Giveaway.Giveawayleute.size() > 0) {
				Random r = new Random();
				int random = r.nextInt((int) Giveaway.giveawayanzahl) + 1;
				winnername = Giveaway.Giveawayleute.get(random);
				if(Giveaway.Giveawayausschluss.contains(winnername)) {
					Giveaway.Giveawayleute.remove(Giveaway.giveawayanzahl, winnername);
					Giveaway.Giveawayausschluss.remove(winnername);
				}else{
					break;
				}
			}
			if(winnername.equals(null)){
				main.bot.sendMessage(main.channel, "Es konnte leider kein Gewinner f\u00FCr das Giveaway gefunden werden!");
			}else{
			main.bot.sendMessage(main.channel, "Wir haben einen Gewinner gefunden");
			main.bot.sendMessage(main.channel, "Der Gewinner des Giveaways ist" + "\u001B" + winnername);  //Gewinner kontaktieren + check ob Gewinner da ist
			Giveaway.Giveawayleute.clear();
			Giveaway.Giveawayausschluss.clear();
			Giveaway.giveawayanzahl = 0;
			Giveaway.codeword = "!giveaway";
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

