import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import javax.swing.text.ViewFactory;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Image;

import javax.swing.Action;
import javax.swing.ImageIcon;

import javax.swing.*; 
import javax.swing.text.Element; 
import javax.swing.text.View; 
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView; 
import javax.swing.text.html.ParagraphView; 

public class GUI extends JFrame {
	
	/*				Change Log			[for lines to be accurate remove this text]
	148   : setBackground(bGColour);
	152   : contentPane.setBackground(cBGColour);					//add customization for colour || picture upload
	
	- added -
	43-99 : mSearch values updated
	132   : C.getUserPref()
	171   : Calc.setFont(new Font(C.userValue[2], Font.PLAIN, Integer.parseInt(C.userValue[4])));
	172   : Calc.setSelectedTextColor(fColour);
	
	- needs to be implemented || fixed - 
	method search functions
		Max's GUI lines 218 to 322 
	 */
	
	// TODO: 
	// alter button size basic 60x40
	// new gui dimensions total 1250 x700
	// left panel 350x700
	
	// implement catalog jpanel
	// search cards above
	// description below???
	
	static int inputValues = 0;
	
	JPanel contentPane;
	Dimension old;
	JTextComponent currentPane;
	private HashMap<KeyStroke, Action> actionMap = new HashMap<KeyStroke, Action>();
	String [][] mSearch = { 
		//Methods from methods( ) 
		//--------------------		Statistics 
		{"RNDInt", "(lower bound, upper bound, trials)", "Returns a random value given the boundries and trials", "rndint statistics number random"},
		{"RNDSeed", "(seed)", "Generates a random number from the given seed", "rndseed statistics number random"},
		{"RNDNum", "(trials)", "Generates a series of random numbers equal to the input trials", "rndnum statistics number random"},
		{"RNDNorm", "(mean, standard devation, trials)", "Creats a random normal sample", "rndnorm statistics number random"},
		{"normPDF", "(mean, standard devation, point)", "Returns the cumulative probablity left of the point in the normal distribution", "normpdf statistics number normal distribution"},
		{"normCDF", "(mean, standard devation, lower bound, upper bound)", "Returns the area between the upper and lower boundries in the normal distribution", "normcdf statistics number normal distribution"},
		{"invNorm", "(mean, standard devation, area)", "Returns the value that is equal to the given probablity", "invnorm normal inverse distribution statistics number"},
		
		{"tPDF", "(degrees of freedom, point)", "Returns the probablity density for the given point", "tpdf distribution statistics number"},
		{"tCDF", "(degrees of freedom, lower bound, upper bound)", "Returns the area between the upper and lower boundries in the t distribution", "tcdf distribution statistics number"},
		{"invT", "(area, degrees of freedom", "Returns the value that is equal to the given probablity", "invt inverse distribution statistics number"},
		
		{"binomPDF", "(trials, success probability, value", "_____________________", "binomcdf binomial distribution statistics number"},
		{"rndBinomial", "(trial, success probability, number of returns)", "Returns a frequency distribution of the possible number of successful outcomes in a given number of trials in each of which there is the same probability of success", "binomial distribution statistics random rndbinomail"},
			//Requires tables
		//{"x^2CDF", "()", "", ""},
		//{"x^2PDF", "()", "", ""},
		//{"invx^2", "()", "", ""},
		//--------------------		Geometry
		{"Triangle", "(base, height)","Finds the area of the triangle with the given dementions", "triangle geometry triangle area shape"},
				
		{"Rectangle", "(width, height)", "Finds the area of the rectangle with the given dementions", "rectangle geometry rectangle square area shape"},
		{"Parallelogram", "(width, height)", "Finds the area of the parallelogram with the given dementions", "parallelogram geometry area shape"},
		{"Circle", "(radius)", "Finds the area of the circle with demention radius", "circle geometry circle area shape"},
			{"CircleC", "(radius)", "Calculates the circumfrence of the given radius", "circle radius circumfrence geometry shape"},
			{"Sephere", "(radius)", "Calculates the volue of the sephere with the given radius", "cirlce vloume radius geometry shape"},
		{"Trapezoid", "(base1, base2, height)", "Finds the area of the trapezoid with the given dementions", "trapezoid geometry trapezoid circle area shape"},
		{"Square", "(width)", "Finds the area of the square with demention width", "square geometry square rectangle area shape"},
		//-------------------		Coordinate plane
		{"Distance", "(x1, y1, x2, y2)", "Calculates the distance between two points on a coordinate plane", "distance solve algebra geometry precalculus points coordinate plane"},
		{"Midpoint", "(x1, y1, x2, y2)", "Calculates the midpoint", "midpoint coordinate plane algebra geometry precalculus points"},
		{"Slope", "(x1, y1, x2, y2)", "Calculates the slope between two points on a coordinate plane", "slope points algebra geometry precalculus coordinate plane"},
		//-------------------		Algebra
		{"Quadratic", "(binomial)", "Solves the given binomal with the quadratic equation", "quadratic equation algebra equation binomial polynomial "},
		//-------------------		Trigonometry
		{"sin", "(degrees)", "Returns the ratio of the side opposite a given angle to the hypotenuse, in a right triangle", "trigonometry triangle sine degrees"},
		{"cos", "(degrees)", "Returns the ratio of the side adjacent a given angle to the hypotenuse, in a right triangle", "trigonometry triangle cosine degrees"},
		{"tan", "(degrees)", "Returns the ratio of the side opposite a given angle to the adjacent side, in a right triangle", "trigonometry triangle tangent degrees"},
		//---
		{"sin-1", "(ratio)", "Returns the degree that is equal to the ratio of the side opposite a given angle to the hypotenuse, in a right triangle", "trigonometry triangle inverse sine degrees sin-1"},
		{"cos-1", "(ratio)", "Returns the degree that is equal to the ratio of the side adjacent a given angle to the hypotenuse, in a right triangle", "trigonometry triangle inverse cocine degrees cos-1"},
		{"tan-1", "(ratio)", "Returns the degree that is equal to the ratio of the side opposite a given angle to the adjacent side, in a right triangle", "trigonometry triangle inverse tangent degrees tan-1"},
		//---
		{"sec", "(degrees)", "Returns the ratio of the hypotenuse to the shorter side adjacent to an acute angle; the reciprocal of cosine", "trigonometry triangle inverse secant degrees"},
		{"csc", "(degrees)", "Returns the ratio of the hypotenuse to the side opposite an acute angle; the reciprocal of sine", "trigonometry triangle inverse cosecant degrees"},
		{"cot", "(degrees)", "Returns the ratio of the side (other than the hypotenuse) adjacent to a particular acute angle to the side opposite the angle; the reciprocal of tangent",  "trigonometry cotriangle tangent degrees"},
		//---
		//{"sec-1},
		//{"csc-1},
		//{"cot-1},
		//-------------------		Misc. number
		{"C", "(integer 1, integer 2)", "Returns the number of combinations of the given integers", "ncr combinations number"},
		{"factorial!", "()!", "the product of an integer and all the integers below it. (Parentheses are not required but highly suggested in complex equations)", "product multiplication number"},
		{"ToFraction", "(decimal)", "Converts a decimal into a fraction", "tofraction number fraction algebra geometry statistics precalculus equation special"},
		{"Sum", "(number, ...)", "Returns the sum of the input numbers", "sum math addition"},
		{"Average", "(number, ...)", "Returns the average of all input numbers", "sum math average mean Σ sigma"},
		{"Σ", "(start, end, variable : operation)", "Returns the answer to the input equation within the given range (defult variable is 'n')", "Σ sigma sum avarge math equation algebra geometry statistics precalculus"},
		{"Sigma", "(start, end, variable : operation)", "Returns the answer to the input equation within the given range (defult variable is 'n')", "Σ sigma sum avarge math equation algebra geometry statistics precalculus"},
		{"Factor", "(value)", "Returns the factors of the given value", "number factor factorize math prime resolve"},
		{"LCM", "(value1, value2)", "Returns the lowest quantity that is a multiple of two or more given quantities", "number math lowest common multiple"},
		{"GCD", "(value1, value2)", "Returns the largest positive number that divides each of the integers", "number math greatest common divisor"},
		{"degToRad", "(degree)", "Converts the given degrees to radians", "math geometry statistics precalculus degtorad degrees radians convert"},
		{"radToDeg", "(radian)", "Converts the given radians to degrees", "math geometry statistics precalculus radtodeg degrees radians convert"},
		{"rectToPolar", "(x, y)", "Converts from rectangular coordinates to polar coordinates", "rectangular coordinates polar geometry algebra precalculus number convert"},
		{"polarToRect", "(r, θ)", "Converts from polar coordinates to rectangular coordinates", "rectangular coordinates polar geometry algebra precalculus number convert"},
		{"%", "(number)", "converts number into percent", "number math algebra statistics"},
		//-------------------		Variables AO
		//{"delVar", "(variable)", "Deletes the varible from memory", "variable operation system"},
		//{"clrList", "(List)", "Clears all values form the list input", "variable list operation system"}
		//-------------------		Operation type & syntax
		/*
		 * TODO add constant for method found loop conditional (line 443) that = number of methods before here																	 that contain (...)
		 */
		
		//{"Logic Operations", "Any combination of 'T' or 'F' that follows boolean logic" ,"Uses \"T\" and \"F\" or numerical values to produce a result according to the laws of Boolean logic", "logic operation special boolean true false"}
		//Hidden credits to developers c:
	//	{"Max Williams", "Head of Project and Lead Software Developer", "max williams turkeyslab developer"},
	//	{"Nick Quigley", "Developer of Graphing Software", "nick quigley developer"},
	//	{"Sophie Opferman", "Web designer", "sophie opferman developer"}
		//Hidden credits to beta testers & Donors
	};		
	
	
	
	/*
	 * mSearch syntax
	 * [x][0] = method name
	 * [x][1] = method inputs
	 * [x][2] = description of the method and what it is used for
	 * [x][3] = search key words for 
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
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI(int x)
	{
		//for Calcs.java to create objects to work with
	}
	
	@SuppressWarnings("serial")
	public GUI() {
		Calcs currentCalcs = new Calcs();
		
	    addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				try {
					currentCalcs.getVar();
					currentCalcs.getUserPref();
				} catch (IOException e1) {					
					/*
					 * Error handled internally with in "getVar" and "getUserPref"
					 */
				}
			}
			public void windowClosed(WindowEvent e) {
				currentCalcs.saveAllVar();
				currentCalcs.saveUserPref();
			}
		});
		
		setTitle("PPT - Flyer");
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	 // (new ImageIcon(GUI.class.getResource("/graphics/add subtract.png")))
	//	ImageIcon taskbarIcon = new ImageIcon(GUI.class.getResource("/graphics/ppt-light.png"));
	//	setIconImage(taskbarIcon.getImage());
		
		List<Image> icons = new ArrayList<Image>();
		
	//	ImageIcon icon16 = new ImageIcon(GUI.class.getResource("/graphics/ppt-light16x16.png"));
		ImageIcon icon32 = new ImageIcon(GUI.class.getResource("/graphics/ppt-light32x32.png"));
	//	ImageIcon iconTrans = new ImageIcon(GUI.class.getResource("/graphics/ppt-transparent.png"));
	//	ImageIcon taskbar = new ImageIcon(GUI.class.getResource("/graphics/taskbar.png"));
	//	ImageIcon icon64 = new ImageIcon(GUI.class.getResource("/graphics/ppt-dark64x64.png"));
		// DOES NOT FIND THESE PLZ FIX
		
		//TODO find a better compression than current 
		/*
		 * The icons are loaded determined by dimensions of the .png file with respective to the users settings
		 * Now we have a 32x32 being loaded (taskbar) 
		 * We need to find a way to better compress the .png file (the Image class is not very good see other types)
		 * 
		 */
		
	//	icons.add(icon16.getImage());
	//	icons.add(icon32.getImage());
	//	icons.add(iconTrans.getImage());
		icons.add(icon32.getImage());
		
		setIconImages(icons);
		
		//Centers Flyer
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2 - (1250/2), dim.height/2 - (700/2), 1250, 700);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);					//add customization for colour || picture upload
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		leftPanel.setBackground(Color.DARK_GRAY);
		leftPanel.setBounds(0, 0, 340, 700);
		contentPane.add(leftPanel);
		leftPanel.setLayout(null);
		
		JScrollPane cScroll = new JScrollPane();
		cScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		cScroll.setBounds(10, 10, 320, 250);
		leftPanel.add(cScroll);
		
		
		JTextPane calcPane = new JTextPane();
		/*
		 * Allows for text wrapping
		 * Will modify later to allow for MATH <HTML> markups
		 */
		/*calcPane.setEditorKit(new HTMLEditorKit(){ 
	           @Override 
	           public ViewFactory getViewFactory(){ 
	 
	               return new HTMLFactory(){ 
	                   public View create(Element e){ 
	                      View v = super.create(e); 
	                      if(v instanceof InlineView){ 
	                          return new InlineView(e){ 
	                              public int getBreakWeight(int axis, float pos, float len) { 
	                                  return GoodBreakWeight; 
	                              } 
	                              public View breakView(int axis, int p0, float pos, float len) { 
	                                  if(axis == View.X_AXIS) { 
	                                      checkPainter(); 
	                                      int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len); 
	                                      if(p0 == getStartOffset() && p1 == getEndOffset()) { 
	                                          return this; 
	                                      } 
	                                      return createFragment(p0, p1); 
	                                  } 
	                                  return this; 
	                                } 
	                            }; 
	                      } 
	                      else if (v instanceof ParagraphView) { 
	                          return new ParagraphView(e) { 
	                              protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) { 
	                                  if (r == null) { 
	                                        r = new SizeRequirements(); 
	                                  } 
	                                  float pref = layoutPool.getPreferredSpan(axis); 
	                                  float min = layoutPool.getMinimumSpan(axis); 
	                                  // Don't include insets, Box.getXXXSpan will include them. 
	                                    r.minimum = (int)min; 
	                                    r.preferred = Math.max(r.minimum, (int) pref); 
	                                    r.maximum = Integer.MAX_VALUE; 
	                                    r.alignment = 0.5f; 
	                                  return r; 
	                                } 
	 
	                            }; 
	                        } 
	                      return v; 
	                    } 
	                }; 
	            } 
	        }); 
	        */
		cScroll.setViewportView(calcPane);
		calcPane.addKeyListener(new KeyAdapter() {	//use array list to store each line
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					currentCalcs.equation = calcPane.getText();
					currentCalcs.populate(currentCalcs.equation);
					
					try {
						currentCalcs.fromMethods = false;
						currentCalcs.cycle(currentCalcs.eList.get(currentCalcs.eList.size() - 1));
					} catch (Exception e1) {
						calcPane.setText("");
					}
					//Clears all old data from error tracking
					// AO currentCalcs.ep.delete();
					currentCalcs.toPrint();
					
					inputValues = currentCalcs.eList.size();
					
					calcPane.setText(currentCalcs.equation); 		//So that all previous math are retained for user use with "-" being detection char for what line we are using
					System.out.println("\n========== DONE ==========\n");
				}
		
			}
		});
		currentPane = calcPane;
		calcPane.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				currentPane = calcPane;
			}

			public void focusLost(FocusEvent e) { }
			
		});
	    //==============================================    Right Display     ==============================================
		
		// This section brought to you by Nick Quigley
		
		
		
			JPanel rightPanel = new JPanel();								//Defult JPanelfor Notes Tables Graphs and Calculations
			rightPanel.setBackground(Color.DARK_GRAY);
			rightPanel.setBounds(350, 0, 882, 653);
			getContentPane().add(rightPanel);
			rightPanel.setLayout(null); 

			JButton btnResize = new JButton("Toggle");
			btnResize.setBounds(243, 636, 97, 25);
			btnResize.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//old holds user's previous size value
					Component right = contentPane.getComponentAt(600, 600);
					if(right != null){
						old = getSize();
						setSize(370, 700);
					}

					else {
						setSize(old);
					}
				}
			});
			leftPanel.add(btnResize);
			
			
			//everything will just get put into Right
			
			// it'll default to the notes interface
			
			
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(5, 5, 865, 613);
			rightPanel.add(tabbedPane);
			
			// ---------- Notes Panel ----------
			
			Notes nPanel = new Notes(currentCalcs);
			
		/*	
			
			JScrollPane rScroll = new JScrollPane();
			rScroll.setBounds(5, 5, 635, 565);
			rScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			nPanel.add(rScroll);
			
			JMenuBar menuBar = new JMenuBar();
			menuBar.setMargin(new Insets(0, 0, 0, 500));
			
			rScroll.setColumnHeaderView(menuBar);
			
			Notes noteF = new Notes();			//object that handles operations for notes

			JTextPane Notes = new JTextPane();
			Notes.setContentType("text/html");
			rScroll.setViewportView(Notes);
			
			
			
			 // Notes menu stuffs
			 
			
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
							
							System.out.println(file);
						}
					}
					
					List<String> text = noteF.loadFile(file.toPath());
					
					for(int x = 0; x < text.size(); x++)
					{
						hold = hold + text.get(x) + System.getProperty("line.separator");
					}
					hold = hold.substring(0, hold.length() - 1);

					Notes.setText(null);
					Notes.setText(hold);
					hold = "";
				}
			});
			
			JButton notesSave = new JButton("SAVE");
			notesSave.setFont(new Font("Calibri", Font.PLAIN, 12));
			notesSave.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					System.out.println("Saving");
//					Wizards w = new Wizards(Notes);
//					w.setVisible(true);
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
					Notes.setFont(new Font(fonts[userSetFont], Font.PLAIN, 12));
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
			
			
			Notes.setFont(new Font(fonts[fontSelect.getSelectedIndex()], size.getSelectedIndex(), Integer.parseInt(currentCalcs.userValue[4])));;
		
		*/
			
		tabbedPane.addTab("Notes", nPanel);
			
		// ——————————— Graphing panel ——————————
				
		JPanel gPanel = (JPanel) new GraphingPanel(currentCalcs, this);
		tabbedPane.addTab("Graph", gPanel);
		
		
		// Catalog panel
		
		// Catalog panel
		
		JPanel cPanel = (JPanel) new CatalogPanel(this);
		tabbedPane.addTab("Catalog", cPanel);
		
		JButton btnCalcClr = new JButton("");
		btnCalcClr.setIcon(new ImageIcon(GUI.class.getResource("/graphics/clr.png")));
		btnCalcClr.setBounds(220, 270, 55, 40);
		btnCalcClr.setBackground(null);
		btnCalcClr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcPane.setText("");
			}
		});
		leftPanel.add(btnCalcClr);
		
		JButton btnAddSub = new JButton("");
		btnAddSub.setIcon(new ImageIcon(GUI.class.getResource("/graphics/add subtract.png")));
		btnAddSub.setBounds(275, 270, 55, 40);
		btnAddSub.setBackground(null);
		leftPanel.add(btnAddSub);
		
		JButton btn0 = new JButton("");
		btn0.setIcon(new ImageIcon(GUI.class.getResource("/graphics/0.png")));
		btn0.setBounds(10, 405, 60, 40);
		btn0.setBackground(null);
		btn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "0");
			}
		});
		leftPanel.add(btn0);
		
		JButton btn1 = new JButton("");
		btn1.setIcon(new ImageIcon(GUI.class.getResource("/graphics/1.png")));
		btn1.setBounds(10, 360, 60, 40);
		btn1.setBackground(null);
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "1");
			}
		});
		leftPanel.add(btn1);
		
		JButton btn2 = new JButton("");
		try{
			btn2.setIcon(new ImageIcon(GUI.class.getResource("/graphics/2.png")));
		} catch (NullPointerException exc) {
			System.out.println("2 not working");
		}
		btn2.setBounds(80, 360, 60, 40);
		btn2.setBackground(null);
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "2");
			}
		});
		leftPanel.add(btn2);
		
		JButton btn3 = new JButton("");
		btn3.setIcon(new ImageIcon(GUI.class.getResource("/graphics/3.png")));
		btn3.setBounds(150, 360, 60, 40);
		btn3.setBackground(null);
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "3");
			}
		});
		leftPanel.add(btn3);
		
		JButton btn4 = new JButton("");
		btn4.setIcon(new ImageIcon(GUI.class.getResource("/graphics/4.png")));
		btn4.setBounds(10, 315, 60, 40);
		btn4.setBackground(null);
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "4");
			}
		});
		leftPanel.add(btn4);
		
		JButton btn5 = new JButton("");
		btn5.setIcon(new ImageIcon(GUI.class.getResource("/graphics/5.png")));
		btn5.setBounds(80, 315, 60, 40);
		btn5.setBackground(null);
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "5");
			}
		});
		leftPanel.add(btn5);
		
		JButton btn6 = new JButton("");
		btn6.setIcon(new ImageIcon(GUI.class.getResource("/graphics/6.png")));
		btn6.setBounds(150, 315, 60, 40);
		btn6.setBackground(null);
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "6");
			}
		});
		leftPanel.add(btn6);
		
		JButton btn7 = new JButton("");
		btn7.setIcon(new ImageIcon(GUI.class.getResource("/graphics/7.png")));
		btn7.setBounds(10, 270, 60, 40);
		btn7.setBackground(null);
		btn7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "7");
			}
		});
		leftPanel.add(btn7);
		
		JButton btn8 = new JButton("");
		btn8.setIcon(new ImageIcon(GUI.class.getResource("/graphics/8.png")));
		btn8.setBounds(80, 270, 60, 40);
		btn8.setBackground(null);
		btn8.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String present = currentPane.getText();
					currentPane.setText(present + "8");
				}
		});
		leftPanel.add(btn8);
		
		JButton btn9 = new JButton("");
		btn9.setIcon(new ImageIcon(GUI.class.getResource("/graphics/9.png")));
		btn9.setBounds(150, 270, 60, 40);
		btn9.setBackground(null);
		btn9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "9");
			}
		});
		leftPanel.add(btn9);
		
		JButton btnMulti = new JButton("");
		btnMulti.setIcon(new ImageIcon(GUI.class.getResource("/graphics/multiply.png")));
		btnMulti.setBackground(null);
		btnMulti.setBounds(220, 360, 55, 40);
		btnMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "*");
			}
		});
		leftPanel.add(btnMulti);
		
		JButton btnDivide = new JButton("");
		btnDivide.setIcon(new ImageIcon(GUI.class.getResource("/graphics/divide.png")));
		btnDivide.setBounds(275, 360, 55, 40);
		btnDivide.setBackground(null);
		btnDivide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "/");
			}
		});
		leftPanel.add(btnDivide);
		
		JButton btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(GUI.class.getResource("/graphics/add.png")));
		btnAdd.setBounds(220, 405, 55, 40);
		btnAdd.setBackground(null);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "+");
			}
		});
		leftPanel.add(btnAdd);
		
		JButton btnSub = new JButton("");
		btnSub.setIcon(new ImageIcon(GUI.class.getResource("/graphics/subtract.png")));
		btnSub.setBounds(275, 405, 55, 40);
		btnSub.setBackground(null);
		btnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "-");
			}
		});
		leftPanel.add(btnSub);
		
		JButton btnEnter = new JButton("");
		btnEnter.setIcon(new ImageIcon(GUI.class.getResource("/graphics/enter.png")));
		btnEnter.setBounds(80, 450, 130, 40);
		btnEnter.setBackground(null);
		btnEnter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				currentCalcs.equation = calcPane.getText();
				currentCalcs.populate(currentCalcs.equation);
				
				try {
					currentCalcs.cycle(currentCalcs.eList.get(currentCalcs.eList.size() - 1));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				/* TODO : NOTE
				 * Enter operations
				 */
				
				currentCalcs.toPrint();
				calcPane.setText(currentCalcs.equation); 		
				System.out.println("\n========== DONE ==========\n");
			}
		});

		leftPanel.add(btnEnter);
		
		JButton btnNeg = new JButton("");
		btnNeg.setIcon(new ImageIcon(GUI.class.getResource("/graphics/negate.png")));
		btnNeg.setBounds(150, 405, 60, 40);
		btnNeg.setBackground(null);
		btnNeg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "-");
			}
		});
		leftPanel.add(btnNeg);
		
		JButton btnDec = new JButton("");
		btnDec.setIcon(new ImageIcon(GUI.class.getResource("/graphics/decimal.png")));
		btnDec.setBounds(80, 405, 60, 40);
		btnDec.setBackground(null);
		btnDec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + ".");
			}
		});
		leftPanel.add(btnDec);
		
		JButton btnOpenPar = new JButton("");
		btnOpenPar.setIcon(new ImageIcon(GUI.class.getResource("/graphics/(.png")));
		btnOpenPar.setBounds(220, 315, 55, 40);
		btnOpenPar.setBackground(null);
		btnOpenPar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "(");
			}
		});
		leftPanel.add(btnOpenPar);
		
		JButton btnClosePar = new JButton("");
		btnClosePar.setIcon(new ImageIcon(GUI.class.getResource("/graphics/).png")));
		btnClosePar.setBounds(275, 315, 55, 40);
		btnClosePar.setBackground(null);
		btnClosePar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + ")");
			}
		});
		leftPanel.add(btnClosePar);
		
		JButton btnRadical = new JButton("");
		btnRadical.setIcon(new ImageIcon(GUI.class.getResource("/graphics/radical.png")));
		btnRadical.setBounds(275, 450, 55, 40);
		btnRadical.setBackground(null);
		btnRadical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "âˆš");
			}
		});
		leftPanel.add(btnRadical);
		
		JButton btnExponent = new JButton("");
		btnExponent.setIcon(new ImageIcon(GUI.class.getResource("/graphics/exponent.png")));
		btnExponent.setBounds(220, 450, 55, 40);
		btnExponent.setBackground(null);
		btnExponent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String present = currentPane.getText();
				currentPane.setText(present + "^");
			}
		});
		leftPanel.add(btnExponent);
		
		JButton btnTrig = new JButton("Trig");
		btnTrig.setHorizontalAlignment(SwingConstants.RIGHT);
		btnTrig.setBounds(10, 450, 60, 40);
		btnTrig.setBackground(null);
		btnTrig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * Opens the new window for user to select desired trigonometry equation
				 */
				
				//TODO alter to display buttons and not text input
				String operand =  JOptionPane.showInputDialog(calcPane, "!Hey! @ ~808");
				currentPane.setText(currentPane.getText() + operand);
			}
		});
		//leftPanel.add(btnTrig);
		
		
		/*==========		SHORTCUTS		==========
		KeyStroke crtlL = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(crtlL, new AbstractAction("action1") {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Ctrl-L pressed: ");
				String hold = "";
				File file = new File(System.getProperty("user.dir") + File.separator + "User" + File.separator + "Notes");
				
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Slecet a file to load");

				int returnValue = jfc.showSaveDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					if (jfc.getSelectedFile().isDirectory()) {
						file = jfc.getSelectedFile();
						
						System.out.println(file);
					}
				}
				
				Notes noteF = new Notes();
				
				List<String> text = noteF.loadFile(file.toPath());
				
				for(int x = 0; x < text.size(); x++)
				{
					hold = hold + text.get(x) + System.getProperty("line.separator");
				}
				hold = hold.substring(0, hold.length() - 1);

				Notes.setText(null);
				Notes.setText(hold);
				hold = "";
			}
		});
		KeyStroke crtls = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		actionMap.put(crtls, new AbstractAction("action1") {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Ctrl-S pressed: ");
				
				System.out.println("Save called");
				Wizards w = new Wizards(Notes);
				w.setVisible(true);
			}
		});
		// add more actions..

		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
			KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(e);
				  
			if(actionMap.containsKey(keyStroke)) { 
				final Action a = actionMap.get(keyStroke);
				final ActionEvent ae = new ActionEvent(e.getSource(), e.getID(), null );
				
				SwingUtilities.invokeLater( new Runnable() {
					@Override
					public void run() {
						a.actionPerformed(ae);
					}
				});
				
				return true;
			}
			return false;
			}
		});
		*/
		
		
		//Dynamic resize methods
		contentPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Dimension newSize  = contentPane.getBounds().getSize();
				
				double rpw = newSize.getWidth() - leftPanel.getWidth();
				double rph = newSize.getHeight();
				
				rightPanel.setBounds(360, 0, (int)rpw -20, (int)rph);
				rightPanel.revalidate();
				rightPanel.repaint();
				
				tabbedPane.setBounds(10, 5, (int)rpw - 45, (int)rph - 20);
				tabbedPane.revalidate();
				tabbedPane.repaint();
				
				nPanel.setBounds(10, 5, (int)rpw - 45, (int)rph - 20);
				nPanel.revalidate();
				nPanel.repaint();

//				rScroll.setBounds(10, 5, (int)rpw - 45, (int)rph - 20);
//				rScroll.revalidate();
//				rScroll.repaint();
//				
//				Notes.setBounds(10, 5, (int)rpw - 45, (int)rph - 20);
//				Notes.revalidate();
//				Notes.repaint();

				
//				GraphingPanel.reSize(contentPane);
				/*
				 * TODO get nick to create method in his visual classes that resize to same format 
				 */
			}
		});
	}
}







