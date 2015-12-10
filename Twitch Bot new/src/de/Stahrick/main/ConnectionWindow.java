package de.Stahrick.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class ConnectionWindow {

	public static JFrame frame;
	public static JLabel status;
	
	
	public static void framecreation() {
		int framesizex = 400;
		int framesizey = 600;
		
		frame = new JFrame("TwitchBot: Connection-Window");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		status = new JLabel();
		status.setText("Bereit");
		status.setBounds(50, 550, 300, 15);
		JLabel statusbeschr = new JLabel();
		statusbeschr.setBounds(5, 550, 50, 15);
		statusbeschr.setText("Status: ");
		JLabel twitchnamelabel = new JLabel("Twitch Name");
		twitchnamelabel.setBounds(10, 50, 80, 25);
		
		JTextField twitchname = new JTextField("Twitch Channel");
		twitchname.setBounds(90, 50, 300, 25);
		twitchname.setText(main.Streamername);
		twitchname.setToolTipText("Enter here your Twitch Name.");
		
		JButton connect = new JButton();
		connect.setBounds(270, 520, 100, 25);
		connect.setText("Verbinden");
		connect.setToolTipText("Connect");
		JButton cancel = new JButton();
		cancel.setBounds(30, 520, 100, 25);
		cancel.setText("Abbrechen");
		cancel.setToolTipText("Cancel");
		
		panel.add(connect);
		panel.add(twitchnamelabel);
		panel.add(twitchname);
		panel.add(cancel);
		panel.add(statusbeschr);
		panel.add(status);
		frame.add(panel);
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(framesizex, framesizey);
		frame.setLocation(screensize.width/2 - frame.getSize().width/2, screensize.height/2 - frame.getSize().height/2);
		frame.setVisible(true);
	}
}
