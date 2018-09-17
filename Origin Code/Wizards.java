import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Wizards extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fileName;
	File file = new File(System.getProperty("user.dir") + File.separator + "User" + File.separator + "Notes");
	private JButton Browse;
	private JTextField path;
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
					Wizards frame = new Wizards(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//==========						LOAD						==========

	public Path getPath()
	{
		return file.toPath();
	}
	
	//==========						SAVE						==========
	
	public Wizards(Notes n) {
		System.out.println("-= The wizard was called =-");
		
		setBackground(SystemColor.textText);
		setResizable(false);
		setTitle("Save");
		/*
		 * Boolean 'ls' is used to determine which wizard should be display
		 */
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setForeground(SystemColor.desktop);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//=====
		fileName = new JTextField();
		fileName.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if(fileName.getText().contains("~") ||fileName.getText().contains("#") ||fileName.getText().contains("%") ||fileName.getText().contains("&") ||fileName.getText().contains("*") ||
					   fileName.getText().contains("{") ||fileName.getText().contains("}") ||fileName.getText().contains("\\") ||fileName.getText().contains(":") ||fileName.getText().contains("<") ||
					   fileName.getText().contains(">") ||fileName.getText().contains("/") ||fileName.getText().contains("+") ||fileName.getText().contains("|") ||fileName.getText().contains("\"") )
					{
						JOptionPane.showMessageDialog(null, "File name can not contain the following   " + System.lineSeparator() + "    ~ # % & * { } : < > \\ / + | \"   ", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						Notes.saveFile(n, fileName.getText(), file);
	
						setVisible(false);
						dispose();
					}
				}
			}
		});
		
		fileName.setFont(new Font("Calibri", Font.PLAIN, 18));
		fileName.setBounds(125, 80, 282, 30);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		JButton Save = new JButton("Save");
		Save.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if(fileName.getText().contains("~") ||fileName.getText().contains("#") ||fileName.getText().contains("%") ||fileName.getText().contains("&") ||fileName.getText().contains("*") ||
				   fileName.getText().contains("{") ||fileName.getText().contains("}") ||fileName.getText().contains("\\") ||fileName.getText().contains(":") ||fileName.getText().contains("<") ||
				   fileName.getText().contains(">") ||fileName.getText().contains("/") ||fileName.getText().contains("+") ||fileName.getText().contains("|") ||fileName.getText().contains("\"") )				
				{
					JOptionPane.showMessageDialog(null, "File name can not contain the following   " + System.lineSeparator() + "    ~ # % & * { } : < > \\ / + | \"   ", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					n.saveFile(n, fileName.getText(), file);

					setVisible(false);
					dispose();
				}
			}
		});
		Save.setBounds(125, 155, 90, 25);
		contentPane.add(Save);
		
		
		Browse = new JButton("Browse");
		Browse.setBounds(221, 155, 90, 25);
		contentPane.add(Browse);
		
		//======			labels			=====
		JLabel saveTxt = new JLabel("File name");
		saveTxt.setFont(new Font("Calibri", Font.PLAIN, 20));
		saveTxt.setBounds(25, 80, 90, 30);
		contentPane.add(saveTxt);
		
		JLabel Location = new JLabel("Location");
		Location.setToolTipText("The location your notes will be saved to ");
		Location.setForeground(Color.GRAY);
		Location.setFont(new Font("Calibri", Font.PLAIN, 20));
		Location.setBounds(25, 120, 90, 30);
		contentPane.add(Location);
		
		JLabel lblNewLabel = new JLabel("Save");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 25));
		lblNewLabel.setBounds(165, 11, 115, 40);
		contentPane.add(lblNewLabel);
		
		path = new JTextField();
		path.setForeground(Color.GRAY);
		path.setFont(new Font("Calibri", Font.PLAIN, 15));
		path.setBounds(125, 120, 282, 30);
		path.setText(file.toString());
		contentPane.add(path);
		path.setColumns(10);
		
		JButton Close = new JButton("Cancel");
		Close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		Close.setBounds(317, 155, 90, 25);
		contentPane.add(Close);
	
		Browse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Choose a directory to save your file");
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) 
				{
					if (jfc.getSelectedFile().isDirectory()) 
					{
						
						file = jfc.getSelectedFile();
						path.setText(file.toString());
					}
				}

			}
		});
	}
}