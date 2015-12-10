package de.Stahrick.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class ConnectionWindow {

	public static JFrame frame;
	public static JLabel status;
	
	
	public static void framecreation() {
		int framesizex = 1000;
		int framesizey = 800;
		
		frame = new JFrame("TwitchBot: Connection-Window");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		status = new JLabel();
		status.setText("Bereit");
		status.setBounds(50, 750, 300, 15);
		JLabel statusbeschr = new JLabel();
		statusbeschr.setBounds(5, 750, 50, 15);
		statusbeschr.setText("Status: ");
		
		
		JButton connect = new JButton();
		connect.setBounds(800, 720, 160, 25);
		connect.setText("Verbinden");
		connect.setToolTipText("Connect");
		
		panel.add(connect);
		panel.add(statusbeschr);
		panel.add(status);
		frame.add(panel);
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(framesizex, framesizey);
		frame.setLocation(screensize.width/2 - frame.getSize().width/2, screensize.height/2 - frame.getSize().height/2);
		frame.setVisible(true);
	}
}
