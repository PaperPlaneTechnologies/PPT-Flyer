import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Errors extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
            // Set cross-platform Java L&F (also called "Metal")
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
	    	e.printStackTrace();
	    }
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Errors f = new Errors(11);
					f.setVisible(true);
					for(int x = 1; x < 10; x++)
					{
						Errors frame = new Errors(x);
					//	frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Errors(int x) {
		if(x == 1) 
		{
			/*
			 * When Assets is deleted
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error : Assets not found");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 255, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("The folder \"Assets\" has been deleted\r\nPlease restore or contact");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 160, 235, 30);
			contentPane.add(email);
		}
		if(x == 2)
		{
			/*
			 * When system deleted
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error : System not found");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 255, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("The file \"System.txt\" has been deleted\r\nPlease restore or contact");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 160, 235, 30);
			contentPane.add(email);
		}
		if(x == 3)
		{
			/*
			 * When OS is not supported : (probable pirate copy)
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error : Your OS is not supported");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(60, 80, 330, 30);
			contentPane.add(errorMessage);
			
			JTextPane systemList = new JTextPane();
			systemList.setForeground(Color.DARK_GRAY);
			systemList.setFont(new Font("Calibri", Font.PLAIN, 17));
			systemList.setText("Windows\r\nMacintosh\r\nLinux");
			systemList.setEditable(false);
			systemList.setBounds(159, 138, 90, 70);
			contentPane.add(systemList);
			
			JLabel systemsHead = new JLabel("Supported Systems");
			systemsHead.setForeground(Color.DARK_GRAY);
			systemsHead.setFont(new Font("Calibri", Font.PLAIN, 18));
			systemsHead.setBounds(130, 121, 145, 23);
			contentPane.add(systemsHead);
		}
		if(x == 4)
		{
			/*
			 * When user holds invalid copy
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setForeground(Color.BLACK);
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error : Piracy is a crime");
			errorMessage.setForeground(Color.BLACK);
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 80, 244, 30);
			contentPane.add(errorMessage);
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("If you would like to purchase a copy \r\n contact or downlaod at");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 175, 235, 30);
			contentPane.add(email);
			
			JTextPane download = new JTextPane();
			download.setText("http://paperplanetechnologies.com/");
			download.setForeground(new Color(0, 0, 128));
			download.setFont(new Font("Calibri", Font.PLAIN, 15));
			download.setBounds(102, 200, 235, 30);
			contentPane.add(download);
		}
		if(x == 5)
		{
			/*
			 * When user is offline during required connection 
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error : You are offline");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 255, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("Please try again once you have established a connection");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
		}
		if(x == 6)
		{
			/*
			 * When the error log file is missing || empty
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error: Error.txt");
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 260, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("In order for us to help you with any errors you may encounter, please restore \"Error.txt\"");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 125, 266, 60); 
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 205, 235, 30);
			contentPane.add(email);
			
			JTextArea errorInput = new JTextArea();
			errorInput.setBounds(100, 220, 266, 200);
			contentPane.add(errorInput);
		}
		if(x == 7)
		{
			/*
			 * When user has successfully sent an email
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Thank you for your help!");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("It was sent!");
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(150, 75, 122, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("Sorry about the inconvience.\r\nWe will look into this, if you have\r\nquestions or concerns contact us at");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 115, 266, 70);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 195, 235, 30);
			contentPane.add(email);
		}
		if(x == 8)
		{
			/*
			 * When user attempts to load a non txt file
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Error: File not supported");
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 260, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("Currently we can only support .txt files \r\n But soon you will be to \r\naccess your Google account!");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
		}
		if(x == 9)
		{
			/*
			 * When UserPrefrences is missing
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Could not access setting");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 255, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("We were unable to find your\r\nsaved settings.\r\n Please restore or contact");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 160, 235, 30);
			contentPane.add(email);
		}
		if(x == 10)
		{
			/*
			 * When variables is missing
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("Error");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("Could find variables");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 255, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("We were unable to find your\r\nvariables. \r\n Please restore or contact");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 116, 266, 44);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 160, 235, 30);
			contentPane.add(email);
		}
		if(x == 11)
		{
			/*
			 * When variables is missing
			 */
			setResizable(false);
			setAlwaysOnTop(true);
			setTitle("");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(null);
			contentPane.setBackground(Color.WHITE);
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel errorMessage = new JLabel("We understand");
			
			errorMessage.setFont(new Font("Calibri", Font.PLAIN, 25));
			errorMessage.setBounds(100, 75, 255, 30);
			contentPane.add(errorMessage);	
			
			JTextPane txtpnTheFilesystemtxt = new JTextPane();
			txtpnTheFilesystemtxt.setFont(new Font("Calibri", Font.PLAIN, 15));
			txtpnTheFilesystemtxt.setText("We know helping others isn't for everyone. \r\nIf you have questions contact");
			txtpnTheFilesystemtxt.setEditable(false);
			txtpnTheFilesystemtxt.setBounds(100, 110, 270, 70);
			contentPane.add(txtpnTheFilesystemtxt);
			
			JTextPane email = new JTextPane();
			email.setText("paperplanetechnologies@gmail.com");
			email.setForeground(new Color(0, 0, 128));
			email.setFont(new Font("Calibri", Font.PLAIN, 15));
			email.setBounds(102, 180, 235, 30);
			contentPane.add(email);
		}
	}
}








 