package de.Stahrick.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class GUImainmenu {

	public static int framesizex = 1000;
	public static int framesizey = 500;
	public static JMenuBar menubar;
	public static JFrame frame;
	
	public static void main(String[] s) {
		new main();
		menucreation();
		
		frame = new JFrame("Twitch-ChatBot");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setJMenuBar(menubar);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel welcome = new JLabel();
		welcome.setText("Der Twitch-Bot hei\u00DFt sie willkommen.");
		welcome.setFont(new Font("Serif", Font.BOLD, 15));
		welcome.setBounds(370, 20, 700, 100);
		
		//JOptionPane.showConfirmDialog(frame, "Moechtest du das Programm wirklich schliessen?", "Programm beenden", JOptionPane.YES_NO_CANCEL_OPTION); //Trigger beim Schlie�en des Programms
		
		panel.add(welcome);
		frame.add(panel);
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(framesizex, framesizey);
		frame.setLocation(screensize.width/2 - framesizex/2, screensize.height/2 - framesizey/2);
		frame.setVisible(true);
	}
	
	
	public static void menucreation() {
		menubar = new JMenuBar();  //JFrame Men� oben am Fenster
		JMenu menucon = new JMenu("Verbindung");
		menucon.setMnemonic(KeyEvent.VK_A);
		menubar.add(menucon);
		
		JMenuItem connection = new JMenuItem("Verbinden");
		menucon.add(connection);
		connection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConnectionWindow.framecreation();
			}
		});
		JMenuItem disconnect = new JMenuItem("Verbindung trennen");
		disconnect.setToolTipText("Disconnect your Bot from Channel");
		menucon.add(disconnect);
		disconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main.bot.disconnect();
			}
		});
		
		JMenu menugiv = new JMenu("Giveaway");
		menubar.add(menugiv);
		
		JMenuItem giveaway = new JMenuItem("Giveaway");
		menugiv.add(giveaway);
		giveaway.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GiveawayWindow.framecreation();
			}
		});
		
		JMenu menusett = new JMenu("Einstellungen");
		menubar.add(menusett);
		
		JMenuItem settings = new JMenuItem("Einstellungen");
		menusett.add(settings);
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SettingsWindow.framecreation();
			}
		});
}
}
