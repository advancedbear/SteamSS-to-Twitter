import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Viewer {

	private JFrame frmSteamScreenshotUploader;

	/**
	 * Launch the application.
	 */
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
		
		Parse p = new Parse();
		try {
			System.out.println(p.getTitle(7));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		JComboBox gameTitle = new JComboBox();
		gameTitle.setBounds(12, 10, 228, 19);
		frmSteamScreenshotUploader.getContentPane().add(gameTitle);
		
		JList list = new JList();
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setBounds(12, 39, 228, 392);
		frmSteamScreenshotUploader.getContentPane().add(list);
		
		JLabel imagePlace = new JLabel("New label");
		imagePlace.setBounds(252, 10, 360, 270);
		frmSteamScreenshotUploader.getContentPane().add(imagePlace);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(252, 319, 360, 64);
		frmSteamScreenshotUploader.getContentPane().add(textArea);
		
		JButton submit = new JButton("Tweet");
		submit.setBounds(252, 393, 360, 38);
		frmSteamScreenshotUploader.getContentPane().add(submit);
		
		JLabel accountName = new JLabel("New label");
		accountName.setBounds(252, 296, 257, 13);
		frmSteamScreenshotUploader.getContentPane().add(accountName);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(521, 292, 91, 21);
		frmSteamScreenshotUploader.getContentPane().add(btnNewButton);
		
	}
}
