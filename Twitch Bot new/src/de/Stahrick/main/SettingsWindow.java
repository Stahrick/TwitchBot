package de.Stahrick.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.*;


public class SettingsWindow {
	static JLabel status;
	static JFrame frame;
	static JMenuBar menubar;
	
	public static void framecreation() {
	
	final JTextField botnametext;	
	final JTextField twitchnametext;
	final JTextField oAuthtext;
	final JTextField aktivtime;
	final JCheckBox aktivitaet;
	Saving sav = new Saving();
	final Thread t = new Thread(sav);
		
	frame = new JFrame("TwitchBot: Settings-Window");			//Erstellt Frame
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		//Bei schlieﬂen des Fensters Operation
	frame.setResizable(false);
	
	JPanel panel = new JPanel();
	panel.setLayout(null);
	
	JLabel botname = new JLabel("Botname");
	botname.setBounds(174, 30, 60, 15);
	JLabel twitchname = new JLabel("Twitch Streamer Name");
	twitchname.setBounds(10, 30, 132, 15);
	JLabel oAuth = new JLabel("oAuthPassword");
	oAuth.setBounds(10, 90, 282, 15);
	JLabel aktivtimebesch = new JLabel("Aktiver Zuschauer Zeit");
	aktivtimebesch.setBounds(10, 145, 300, 15);
	status = new JLabel("Bereit");
	status.setBounds(50, 530, 300, 15);
	JLabel statusbeschr = new JLabel("Status:");
	statusbeschr.setBounds(5, 530, 40, 15);
	
	botnametext = new JTextField("");
	botnametext.setText(main.Botname);
	botnametext.setToolTipText("Gib hier den Twitch-Bot Namen ein");
	botnametext.setBounds(174, 53, 118, 21);
	twitchnametext = new JTextField("");
	twitchnametext.setText(main.Streamername);
	twitchnametext.setToolTipText("Gib hier deinen Twitch Namen ein");
	twitchnametext.setBounds(10, 53, 132, 21);
	oAuthtext = new JTextField("");
	oAuthtext.setToolTipText("oAuth Token des Accounts deines Bots eingeben");
	oAuthtext.setBounds(10, 111, 282, 21);
	aktivtime = new JTextField("");
	aktivtime.setBounds(10, 169, 200, 21);
	aktivtime.setToolTipText("Wie viel Zeit muss der Zuschauer zugucken, um ihn in die Giveaway Datei aufzunehmen");
	
	aktivitaet = new JCheckBox();									//JCheckbox
	aktivitaet.setText("Aktivit\u00E4tspr\u00FCfung aller Nutzer");
	aktivitaet.setToolTipText("Activity control of all users.");
	aktivitaet.setBounds(10, 251, 190, 26);
	
	
	JButton speichern = new JButton();
	speichern.setText("Speichern");
	speichern.setToolTipText("Save all settings");
	speichern.setBounds(821, 495, 118, 25);
	
	
	panel.add(statusbeschr);
	panel.add(status);
	panel.add(botname);
	panel.add(botnametext);
	panel.add(twitchname);
	panel.add(twitchnametext);
	panel.add(oAuth);
	panel.add(oAuthtext);
	panel.add(aktivitaet);
	panel.add(aktivtime);
	panel.add(aktivtimebesch);
	panel.add(speichern);
	
	
	frame.add(panel);
	
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	frame.setSize(1000, 600);
	frame.setLocation(screensize.width/2 - 1000/2, screensize.height/2 - 600/2);
	frame.setVisible(true);
	
	speichern.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			t.start();
			Properties prop = new Properties();
			OutputStream output = null;
			try{
				output = new FileOutputStream(main.directory + "config.properties");
				prop.setProperty("Streamername", twitchnametext.getText());
				prop.setProperty("Botname", botnametext.getText());
				prop.setProperty("oAuthToken", oAuthtext.getText());
				if(aktivitaet.isSelected() == true) {
					prop.setProperty("Activitycheck", "true");
				}else{
					prop.setProperty("Activitycheck", "false");
				}
				prop.setProperty("Aktivitaetszeit", aktivtime.getText());
				prop.store(output, null);
			} catch(IOException f) {
				f.printStackTrace();
			} finally {
				if(output != null) {
					try{
						output.close();
						main.Configreading();
					} catch(IOException g) {
						g.printStackTrace();
					}
				}
			}
		}
	});
	
	
	/*frame.addWindowListener(new java.awt.event.WindowAdapter() {
		@SuppressWarnings("unused")
		public void Windowclosing(java.awt.event.WindowEvent windowevent) {
			if(JOptionPane.showConfirmDialog(frame, "M\u00F6chtest du das Programm wirklich schlie\u00DFen?", "Wirklich schlie\u00DFen?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	});*/
	
	}
	
}
class Saving implements Runnable{
	@Override
	public void run() {
		SettingsWindow.status.setText("Einstellungen erfolgreich gespeichert!");
		try {
			Thread.sleep(5000);
			SettingsWindow.status.setText("Bereit");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
