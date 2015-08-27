import java.awt.EventQueue;
import java.awt.event.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import twitter4j.TwitterException;
import twitter4j.auth.*;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Viewer extends JFrame implements ActionListener {

	private JFrame frmSteamScreenshotUploader;

	JComboBox gameTitle = new JComboBox();
	JList list = new JList();
	JLabel imagePlace = new JLabel("New label");
	JLabel accountName = new JLabel("Please login first.");
	JButton auth = new JButton("Login");
	JTextArea text = new JTextArea();
	JButton submit = new JButton("Tweet");

	File token = new File("token");
	boolean loading = false;
	boolean authentication = false;
	
	Tweet t = new Tweet();
	Parse p = new Parse();
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewer window = new Viewer();
					window.frmSteamScreenshotUploader.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Viewer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSteamScreenshotUploader = new JFrame();
		frmSteamScreenshotUploader.setTitle("Steam ScreenShot Uploader for Twitter");
		frmSteamScreenshotUploader.setBounds(100, 100, 640, 480);
		frmSteamScreenshotUploader.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSteamScreenshotUploader.getContentPane().setLayout(null);
		
		gameTitle.setBounds(12, 10, 228, 19);
		frmSteamScreenshotUploader.getContentPane().add(gameTitle);
		
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBounds(12, 39, 228, 392);
		frmSteamScreenshotUploader.getContentPane().add(list);
		
		imagePlace.setBounds(252, 10, 360, 270);
		frmSteamScreenshotUploader.getContentPane().add(imagePlace);
		
		submit.setEnabled(false);
		submit.setBounds(252, 393, 360, 38);
		frmSteamScreenshotUploader.getContentPane().add(submit);
		submit.addActionListener(this);
		submit.setActionCommand("tweet");

		accountName.setBounds(252, 296, 257, 13);
		frmSteamScreenshotUploader.getContentPane().add(accountName);
		
		auth.setBounds(521, 292, 91, 21);
		frmSteamScreenshotUploader.getContentPane().add(auth);
		auth.addActionListener(this);
		auth.setActionCommand("auth");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(252, 319, 360, 64);
		frmSteamScreenshotUploader.getContentPane().add(scrollPane);
		scrollPane.setViewportView(text);
		text.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		if (loadAccessToken() != null) {
			t.authorization(loadAccessToken());
			loading = true;
			try {
				accountName.setText(t.twitter.getScreenName());
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			submit.setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("auth")) {
			System.out.println("push auth key.");
			t.TwitterAuth();
			String value = JOptionPane.showInputDialog(this, "PINî‘çÜÇì¸óÕÇµÇƒâ∫Ç≥Ç¢");
			if (value == null) {
			} else {
				t.authorization(value);
				try {
					accountName.setText("@"+t.twitter.getScreenName());
					authentication = true;
					storeAccessToken(t.accessToken);
					submit.setEnabled(true);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getActionCommand().equals("tweet")){
		}
	}
	

	private AccessToken loadAccessToken() {
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(token));
			AccessToken accessToken = (AccessToken) is.readObject();
			return accessToken;
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void storeAccessToken(AccessToken accessToken) {
		ObjectOutputStream os = null;
		try {
			if (!loading) {
				os = new ObjectOutputStream(new FileOutputStream(token));
				os.writeObject(accessToken);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
