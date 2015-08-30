import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.*;
import java.awt.MediaTracker;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import twitter4j.TwitterException;
import twitter4j.auth.*;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Viewer extends JFrame implements ActionListener, ListSelectionListener, DocumentListener {

	private JFrame frmSteamScreenshotUploader;

	JComboBox<String> gameTitle;
	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
	JList<String> list;
	DefaultListModel<String> Lmodel = new DefaultListModel<String>();
	JLabel imagePlace = new JLabel("THIS IS NOT IMAGE FILE.");
	JLabel accountName = new JLabel("Please login first.");
	JButton auth = new JButton("Login");
	JTextArea text = new JTextArea();
	JButton submit = new JButton("Tweet (23)");
	

	
	

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
		String s;
		model.addElement("-- Game Title --");
		for (File f : p.gameList) {
			try {
				String fn = f.getName();
				s = p.getTitle(Integer.parseInt(fn));
				model.addElement(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		gameTitle = new JComboBox<String>(model);
		gameTitle.setBounds(12, 10, 228, 19);
		frmSteamScreenshotUploader.getContentPane().add(gameTitle);
		gameTitle.addActionListener(this);

		list = new JList<String>(Lmodel);
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBounds(12, 39, 228, 392);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		frmSteamScreenshotUploader.getContentPane().add(list);

		imagePlace.setBounds(252, 10, 360, 270);
		imagePlace.setHorizontalAlignment(JLabel.CENTER);
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
		text.getDocument().addDocumentListener(this);

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
			String value = JOptionPane.showInputDialog(this, "PIN”Ô†‚ð“ü—Í‚µ‚Ä‰º‚³‚¢");
			if (value == null) {
			} else {
				t.authorization(value);
				try {
					accountName.setText("@" + t.twitter.getScreenName());
					authentication = true;
					storeAccessToken(t.accessToken);
					submit.setEnabled(true);
				} catch (TwitterException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getActionCommand().equals("tweet")) {
			if (list.getSelectedIndex() != -1) {
				if (p.imageList[list.getSelectedIndex()].isFile()) {
					t.tweet(text.getText(), p.imageList[list.getSelectedIndex()]);
					text.setText("");
				}
			}
		}

		if (gameTitle.getSelectedIndex() > 0) {
			Lmodel.removeAllElements();
			p.listImage(gameTitle.getSelectedIndex() - 1);
			for (File f : p.imageList) {
				Lmodel.addElement(f.getName());
			}

		}

	}

	private AccessToken loadAccessToken() {
		ObjectInputStream is = null;
		try {
			is = new ObjectInputStream(new FileInputStream(token));
			AccessToken accessToken = (AccessToken) is.readObject();
			is.close();
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedIndex() != -1) {
			MediaTracker tracker = new MediaTracker(this);
			if (p.imageList[list.getSelectedIndex()].isFile()) {
				ImageIcon image = new ImageIcon(p.imageList[list.getSelectedIndex()].getAbsolutePath());
				double scale = (double) image.getIconWidth() / ((double) image.getIconWidth() / 360.0);
				Image resizeImage = image.getImage().getScaledInstance((int) scale, -1, Image.SCALE_SMOOTH);
				tracker.addImage(resizeImage, 1);
				ImageIcon resizedImage = new ImageIcon(resizeImage);
				try {
					tracker.waitForAll();
					imagePlace.setIcon(resizedImage);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			} else {
				imagePlace.setIcon(null);
				imagePlace.setText("THIS IS NOT IMAGE FILE.");
			}
		}
	}

	
	public void changedUpdate(DocumentEvent de) {
		int num = text.getText().length()+23;
		submit.setText("Tweet (" + num + ")");
		if (text.getText().length() >= 140) {
			submit.setEnabled(false);
		} else {
			submit.setEnabled(true);
		}
	}

	public void insertUpdate(DocumentEvent e) {
		int num = text.getText().length()+23;
		submit.setText("Tweet (" + num + ")");
		if (text.getText().length() > 117) {
			submit.setEnabled(false);
		}
		
	}

	public void removeUpdate(DocumentEvent e) {
		int num = text.getText().length()+23;
		submit.setText("Tweet (" + num + ")");
		if (text.getText().length() <= 118) {
			submit.setEnabled(true);
		}
	}
}