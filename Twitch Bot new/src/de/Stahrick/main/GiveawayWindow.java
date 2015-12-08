package de.Stahrick.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

public class GiveawayWindow {
	
	
	public static JButton autogiveaway;
	public static JLabel status;
	public static JTextField minzuschauer;
	public static JTextField giveawaykeyword;
	public static JFrame frame;
	
	static Thread th = new Thread(new wait());
	static Thread error = new Thread(new error());

	public static void framecreation() {
	frame = new JFrame("TwitchBot: Giveaway-Window");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setResizable(false);
	frame.addWindowListener(new closewindow());
	
	JPanel panel = new JPanel();
	panel.setLayout(null);
	
	JLabel statusname = new JLabel("Status:");
	statusname.setBounds(5, 440, 40, 15);
	status = new JLabel("Bereit");
	status.setBounds(50, 440, 300, 15);
	
	minzuschauer = new JTextField();
	minzuschauer.setBounds(300, 350, 160, 25);
	minzuschauer.setText("Mindest Zuschaueranzahl");
	minzuschauer.setToolTipText("Minium of viewers for autogiveaway");
	giveawaykeyword = new JTextField();
	giveawaykeyword.setBounds(300, 250, 160, 25);
	giveawaykeyword.setText("Giveaway Chat Keyword z.B. !giveaway");
	giveawaykeyword.setToolTipText("Giveaway Chat Keyword f.e. !giveaway");
	
	autogiveaway = new JButton();
	autogiveaway.setPreferredSize(new Dimension(160, 25));
	autogiveaway.setBounds(300, 400, 160, 25);
	autogiveaway.setText("Autogiveaway starten");
	autogiveaway.setToolTipText("Autogiveaway start");
	
	panel.add(autogiveaway);
	panel.add(statusname);
	panel.add(status);
	panel.add(minzuschauer);
	panel.add(giveawaykeyword);
	
	frame.add(panel);
	
	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	frame.setSize(500, 500);
	frame.setLocation(screensize.width/2 - frame.getSize().width/2, screensize.height/2 - frame.getSize().height/2);
	frame.setVisible(true);
	}
	
	public static void framemanagment() {
		autogiveaway.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Giveaway.autogiveaway();
				th.start();
			}
		});
	}
}

class wait implements Runnable{
	@Override
	public void run() {
		GiveawayWindow.status.setText("Autogiveaway gestartet!");
		try {
			Thread.sleep(5000);
			GiveawayWindow.status.setText("Bereit");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
}

class error implements Runnable{
	@Override
	public void run() {
		GiveawayWindow.status.setText("Autogiveaway Error: Es wurde keine Zuschaueranzahl angegeben.");
		try {
			Thread.sleep(5000);
			GiveawayWindow.status.setText("Bereit");
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class closewindow implements WindowListener {
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		if(e.getWindow().getName() == GiveawayWindow.frame.getName()) {
			GUI.status.setText("Bereit");
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
