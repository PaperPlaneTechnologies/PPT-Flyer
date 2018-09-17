import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileSystemView;

public class Notes extends JPanel{
	
	/*
	 * 
	 * 
	 * GUI : 277 : 650, 600
	 * 			JPanel gPanel = (JPanel) new GraphingPanel(C);
				tabbedPane.addTab("Graph", gPanel);
		
				Right.add(tabbedPane);
	 */
	JTextPane notePane;
	
	public Notes(Calcs currentCalcs){
		
		setBounds(5, 5, 650, 600);
		setLayout(null);
		
		JScrollPane rScroll = new JScrollPane();
		rScroll.setBounds(5, 5, 635, 565);
		rScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(rScroll);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 0, 0, 500));
		
		rScroll.setColumnHeaderView(menuBar);
		
		//Notes noteF = new Notes();			//object that handles operations for notes

		notePane = new JTextPane();
		notePane.setContentType("text/html");
		rScroll.setViewportView(notePane);
		
		
		/*
		 * Notes menu stuffs
		 */
		
		JButton notesLoad = new JButton("LOAD");
		notesLoad.setFont(new Font("Calibri", Font.PLAIN, 12));
		notesLoad.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				String hold = "";
				File file = null;
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Slecet a \".txt\" file to load");

				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isFile()) {
						
						file = jfc.getSelectedFile();
					}
				}
				
				List<String> text = loadFile(file.toPath());
				
				for(int x = 0; x < text.size(); x++)
				{
					hold = hold + text.get(x) + System.getProperty("line.separator");
				}
				hold = hold.substring(0, hold.length() - 1);

				notePane.setText(null);
				notePane.setText(hold);
				hold = "";
			}
		});
		
		JButton notesSave = new JButton("SAVE");
		notesSave.putClientProperty("notes", super.getClass());
		notesSave.setFont(new Font("Calibri", Font.PLAIN, 12));
		notesSave.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("Saving");
				/*
				 * TODO Nick help me here
				 */
				Wizards w = new Wizards(notesSave.getClientProperty("notes"));
				w.setVisible(true);
			}
		});
		
		Integer[] sizes = new Integer[100];
		
		for(int x = 1; x < 100; x++)
		{
			sizes[x] = x;
		}
		
		JComboBox size = new JComboBox(sizes);
		size.setMaximumSize(new Dimension(60, 30));
		size.setSelectedIndex(Integer.parseInt(currentCalcs.userValue[4]));
		
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		
		int userSetFont = 0;
		
		while(userSetFont < fonts.length)
		{
			if(fonts[userSetFont].equals(currentCalcs.userValue[2]))
			{
				notePane.setFont(new Font(fonts[userSetFont], Font.PLAIN, 12));
				break;
			}
			userSetFont++;
		}
		
		JComboBox fontSelect = new JComboBox(fonts);
		fontSelect.setMaximumSize(new Dimension(150, 30));
		fontSelect.setSelectedIndex(userSetFont);
		fontSelect.setFont(new Font("Calibri", Font.BOLD, 12));
		
		menuBar.add(fontSelect);
		menuBar.add(size);
		
		JSeparator separator = new JSeparator();
		menuBar.add(separator);
		menuBar.add(notesSave);
		menuBar.add(notesLoad);
		
		
		notePane.setFont(new Font(fonts[fontSelect.getSelectedIndex()], size.getSelectedIndex(), Integer.parseInt(currentCalcs.userValue[4])));;
		
	}
	public static void saveFile(Notes n, String name, File dir)
	{
		System.out.println("save called");
		/*
		 * Default plain text notes with no date input
		 */
		JTextPane ntp = n.notePane;
		
		if(name.equals(""))
			name = "Untitled";

		System.out.println("file name = " + name);
		
		File file = new File(dir, name + ".txt");

		dir.mkdir();		//Catch if files missing to prevent errors
		
		try (BufferedWriter fileOut = new BufferedWriter(new FileWriter(file))) {
			ntp.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<String> loadFile(Path path)
	{
		List<String> data = null;
		
		try {
			data = Files.readAllLines(path);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		return data;
	}
}







