import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

/*
 * 					TODO
 * Add catch block for if file has been deleted
 * 
 * Security.txt == Math Methods.txt
 */

public class Security extends JFrame{
	static String sn = null;
	static String MAC = null;	
	
	String email = "";
	Boolean request = true;
	
	private JPanel contentPane;
	private JTextField field;
	
	public boolean isWindows() {
		return (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0);
	}
	public boolean isMac() {
		return (System.getProperty("os.name").toLowerCase().indexOf("mac") >= 0);
	}
	public boolean isUnix() {
		return (System.getProperty("os.name").toLowerCase().indexOf("nix") >= 0 || 
				System.getProperty("os.name").toLowerCase().indexOf("nux") >= 0 || 
				System.getProperty("os.name").toLowerCase().indexOf("aix") > 0 );
	}
	public boolean permissions()
	{
		Path file = Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "User" + 
							  System.getProperty("file.separator") + "Assets");
		/*
		 * Lock on file ("Assets") that houses security data
		 */
		
		System.out.println("     " + file);
		
	    AclFileAttributeView aclAttr = Files.getFileAttributeView(file, AclFileAttributeView.class);
	    
	    try{
		    UserPrincipalLookupService upls = file.getFileSystem().getUserPrincipalLookupService();
		    UserPrincipal user = upls.lookupPrincipalByName(System.getProperty("user.name"));
		    AclEntry.Builder builder = AclEntry.newBuilder();       
		    builder.setPermissions(EnumSet.of(AclEntryPermission.APPEND_DATA, AclEntryPermission.DELETE,
		    								  AclEntryPermission.DELETE_CHILD, AclEntryPermission.EXECUTE, 
		    								  AclEntryPermission.READ_ACL, AclEntryPermission.READ_ATTRIBUTES, 
		    								  AclEntryPermission.READ_DATA, AclEntryPermission.READ_NAMED_ATTRS, 
		    								  AclEntryPermission.SYNCHRONIZE, AclEntryPermission.WRITE_ACL,
		    								  AclEntryPermission.WRITE_ATTRIBUTES, AclEntryPermission.WRITE_DATA,
		    								  AclEntryPermission.WRITE_NAMED_ATTRS, AclEntryPermission.WRITE_OWNER));
		    builder.setPrincipal(user);
		    builder.setType(AclEntryType.ALLOW);
		    aclAttr.setAcl(Collections.singletonList(builder.build()));
		    System.out.println("[permission set]\n");

	    } catch(Exception IO) {
	    	IO.printStackTrace();
	    	System.out.println("79");
			Errors e = new Errors(1);
			e.setVisible(true);
	    	IO.printStackTrace();
	    	return false;
	    }
	    //---------
	    /*
	     * prevents user from altering values at system
	     */
		File system = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "User" + 
							   System.getProperty("file.separator") + "Assets" + System.getProperty("file.separator")
							   + "System.txt");
		if(!system.exists() || system.isDirectory())
		{
		 	/*
	    	 * If there is no file create pop up for error and close software 
	    	 * 
	    	 * Error : "System not found please restore"
	    	 */
			System.out.println("97");
			Errors e = new Errors(2);
			e.setVisible(true);
			return false;
		}
		//system.setReadOnly();
		system.setReadable(true);
		return true;
	}
	//----------
	public final String getWindowsSerialNumber() {
		if (sn != null) {
			return sn;
		}
		
		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "wmic", "bios", "get", "serialnumber" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Scanner sc = new Scanner(is);
		try {
			while (sc.hasNext()) {
				String next = sc.next();
				if ("SerialNumber".equals(next)) {
					sn = sc.next().trim();
					break;
				}
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}
	public final String getMacSerialNumber() {

		if (sn != null) {
			return sn;
		}

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "/usr/sbin/system_profiler", "SPHardwareDataType" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String marker = "Serial Number";
		try {
			while ((line = br.readLine()) != null) {
				if (line.contains(marker)) {
					sn = line.split(":")[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}

		return sn;
	}
	public final String getNixSerialNumber() {

		if (sn == null) {
			readDmidecode();
		}
		if (sn == null) {
			readLshal();
		}
		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}
		return sn;
	}
	private BufferedReader read(String command) {

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(command.split(" "));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new BufferedReader(new InputStreamReader(is));
	}
	private void readDmidecode() {

		String line = null;
		String marker = "Serial Number:";
		BufferedReader br = null;

		try {
			br = read("dmidecode -t system");
			while ((line = br.readLine()) != null) {
				if (line.indexOf(marker) != -1) {
					sn = line.split(marker)[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	private void readLshal() {

		String line = null;
		String marker = "system.hardware.serial =";
		BufferedReader br = null;

		try {
			br = read("lshal");
			while ((line = br.readLine()) != null) {
				if (line.indexOf(marker) != -1) {
					sn = line.split(marker)[1].replaceAll("\\(string\\)|(\\')", "").trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	//----------
	public String createEncryptPassword() {
		/*
		 * Use to encrypt passwords using MD5 algorithm
		 * @param password should be a plain text password.
		 * @return a hex String that results from encrypting the given password.
		 * 
		 * This creates the encrypted password for the ".txt" file that will store the user specific information
		 * to be used for anti-theft measures
		 * 		 
		 */
		
		String possibles = "0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{||}~!@#$%&abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String password = "";
		
		for(int x = 0; x <= 500; x++)
		{
			password = password + possibles.charAt((int)(Math.random() * 100));
		}
		try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	        StringBuilder hexString = new StringBuilder();
	       
	        for (int i=0;i<byteData.length;i++) {
	            String hex=Integer.toHexString(0xff & byteData[i]);
	            if(hex.length()==1) 
	               hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }
	    catch(java.security.NoSuchAlgorithmException missing) {
	        return "Error " + password;
	    }
	}
	public String encryptText(String natural)
	{
		String encrypt = "";
		String possibles = "0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{||}~!@#$%&abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		ArrayList<Integer> deci = new ArrayList<Integer>();
		
		int z = 0;
		boolean keepOfPlace = false;
		
		System.out.println(natural);
		
		while(z < natural.length())
		{
			if(z % 3 == 0 && (z != natural.length()-1))
			{
				/* 
				 * Every 4th value is random to avoid patterned detection
				 */
				deci.add((int)possibles.charAt((int)(Math.random() * 100)) * 20 + 15);

				z++;
				keepOfPlace = true;
			} else {
				if(keepOfPlace)
				{
					deci.add((int)natural.charAt(z - 1) * 20 + 15);
					
					keepOfPlace = false;
				}
				deci.add((int)natural.charAt(z) * 20 + 15);
				
				z++;
			}
		}
		
		//adds delimiter
		for(int x = 0; x < deci.size(); x++)
		{
			encrypt = encrypt + deci.get(x) + "-";
		}
		
		encrypt = encrypt.substring(0, encrypt.length() - 1);

		return encrypt;
	}
	public String decryptText(String encrypted)
	{
		System.out.println("encrypted = " + encrypted);
		
		int[] castedChar = 
			{
				(int)'a',(int)'b',(int)'c',(int)'d',(int)'e',(int)'f',(int)'g',(int)'h',(int)'i',(int)'j',(int)'k',(int)'l',(int)'m',(int)'n',(int)'o',(int)'p',(int)'q',(int)'r',(int)'s',(int)'t',(int)'u',(int)'v',(int)'w',(int)'x',(int)'y',(int)'z',
				(int)'A',(int)'B',(int)'C',(int)'D',(int)'E',(int)'F',(int)'G',(int)'H',(int)'I',(int)'J',(int)'K',(int)'L',(int)'M',(int)'N',(int)'O',(int)'P',(int)'Q',(int)'R',(int)'S',(int)'T',(int)'U',(int)'V',(int)'W',(int)'X',(int)'Y',(int)'Z',
				(int)'0',(int)'1',(int)'2',(int)'3',(int)'4',(int)'5',(int)'6',(int)'7',(int)'8',(int)'9',
				(int)')',(int)'!',(int)'@',(int)'#',(int)'$',(int)'%',(int)'^',(int)'&',(int)'*',(int)'(',
				(int)'-',(int)'=',(int)',',(int)'.',(int)'/',(int)'`',
				(int)'_',(int)'+',(int)'<',(int)'>',(int)'|',(int)'~'
			};
		char[] naturalChar = 
		{
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
			'0','1','2','3','4','5','6','7','8','9',
			')','!','@','#','$','%','^','&','*','(',
			'-','=',',','.','/','`',
			'_','+','<','>','|','~'
		};
		//-----
		String decrypt = "";
		String[] hold  = encrypted.split("-");
		int[] intValue = new int[hold.length];
		ArrayList<Integer> code = new ArrayList<Integer>();

		int x = 0;
		while(x < hold.length)
		{
			intValue[x] = Integer.parseInt(hold[x]);
			x++;
		}
		x = 0;
		while(x < intValue.length)
		{
			if((x % 4 != 0) || (x == intValue.length - 1))
			{
				/*
				 * Removes the random value that is added
				 */
				code.add((intValue[x] - 15) / 20);
			}
			x++;
		}
		
		x = 0;
		
		int place = 0;
		while(x < naturalChar.length && place < code.size())
		{
			if(code.get(place) == castedChar[x])
			{
				decrypt = decrypt + naturalChar[x];
				place++;
				x = -1;
			}
			x++;
		}
		return decrypt;
	}
	public boolean verify(String sn)
	{
		/*
		 * Gets all of users actual variables
		 */
		String asn = "";
		if(isWindows())
		{
			 asn = getWindowsSerialNumber();
		} 
		else if(isMac()) 
		{
			 asn = getMacSerialNumber();
		}
		else if(isUnix())
		{
			asn = getNixSerialNumber();	
		} else {
			Errors e = new Errors(3);
			e.setVisible(true);
		}
		
		sn = decryptText(sn);
		
		System.out.println("Actual SN = " + asn);
		System.out.println("Onfile SN = " + sn);
		
		if(asn.equals(sn))
		{
			/*
			 * When on file is the actual
			 */
			return true;
		}
		
		return false;
	}
	//---------
	public boolean printTo()
	{
		Path path = Paths.get(System.getProperty("user.dir") + System.getProperty("file.separator") + "User" + 
							  System.getProperty("file.separator") + "Assets" + System.getProperty("file.separator")
							  + "System.txt");
		File system = new File(path.toString());

		if(system.exists() && !system.isDirectory())
		{
			System.out.println(":D");
			/*
			 * File has not been deleted we can proceed
			 */
			try{
				List<String> data = Files.readAllLines(path);
				
				try{
					System.out.println("input to verify " + data.get(0));
					boolean valid = verify(data.get(0));
					
					if(valid)
					{
						/*
						 * Launch software
						 */
						System.out.println("beep boop!");
						return true;
					}
					if(!valid)
					{
						/*
						 * User holds stolen copy
						 * 
						 * Error : "Your copy isn't valid please contact paperplanetechnologies@gmail.com"
						 */
						Errors e = new Errors(4);
						e.setVisible(true);
						return false;
					}
				
				} catch (IndexOutOfBoundsException io) {
					/*
					 * First launch data needs to be created
					 * 
					 * 		Date 			:	__.__.____ 
					 * 		Serial Number	:	x-x-x-x-x-x-x-x-x
					 * 		Key				:	x-x-(...)-x-x
					 * 
					 * 		TODO Add database connection systems here
					 */
					
					email = JOptionPane.showInputDialog("Please input the email associated with your purchase");
					/*
					 * TODO create catches to close launcher if user presses "cancel" and if emails do not match
					 */
					
					
					System.out.println("broke");
					
					FileWriter fw = new FileWriter(system);
					
					if(isWindows())
					{
						 sn = getWindowsSerialNumber();
						 System.out.println("Windows OS");
					} 
					else if(isMac()) 
					{
						 sn = getMacSerialNumber();
						 System.out.println("Mac OS");
					}
					else if(isUnix())
					{
						sn = getNixSerialNumber();	
						System.out.println("Linux OS");
					} else {
						Errors e = new Errors(3);
						e.setVisible(true);
						
						fw.flush();
						fw.close();
						
						return false;
					}
					System.out.println("sn printed = " + sn);
					sn = encryptText(sn);
					fw.write(sn + System.getProperty("line.separator"));
					
					fw.flush();
					fw.close();
					
					/*
					 * Return statement here means that the method created the type specific data 
					 * Copy is verified in translative property
					 */
					return true;
				}
				
			} catch (IOException e01) {
				e01.printStackTrace();
			 	/*
		    	 * there is no system file : create pop up for error and close software 
		    	 * 
		    	 * Error : "Assets not found please restore"
		    	 */
				Errors e = new Errors(1);
				e.setVisible(true);

				return false;
			}
		} else {
			System.out.println("sad.");
		 	/*
	    	 * If there is no file create pop up for error and close software 
	    	 * 
	    	 * Error : "System not found please restore"
	    	 */
			Errors e = new Errors(2);
			e.setVisible(true);

			return false;
		}
		return false;
	}
	//----------
	public boolean security()
	{
		/* Upon download
		 * 	unique download link save to database in plain text & saved to user's Security.txt with our encryption
		 * 	user's input email is save to database
		 * 	user's "First Launch" counter is set to ' 0 '
		 * ===
		 * Serial Number		:	unique id for each machine
		 * ===
		 * Security.txt is hidden, undelete-able, read-only, and password protect
		 * 
		 * First Launch			:	there is no data in Security.txt
		 *	Compares "userEmail" to database's "userEmail"			:	if valid continue
		 		if user's "First Launch counter" > 0 deny access and prompt to contact support email address		TODO
		 			*	Support checks if email came from user who purchased 	: if correct continue 
		 			*	Asks user as to why they have requested a second install
		 			*		If user seems to have a valid reason	:	reset "First Launch" counter
		 			*		Else pull our anti-piracy card and increase "First Launch" counter to 9999 to id as treat
		 			
		 			*	In event that user is running on self built "System" is returned and will not throw errors except in event of selfbuilt transfers
		 			
		 *	Compares user's "key" to database's	:	if valid continue
		 		if not valid	: 	that means key has already been used and version is pirated
		 						
		 *	After valid download checks
		 											Security.java Steps
		 		1)	device serial number is added to be used to check for valid copy	:	follows standard encryption pattern
		 		
		 *	For later Launches
		 											Security.java Steps
		 		1)	check for serial number												: 	if matches launch software
		 		2)	if connected to network check for updates
		 				opens pop-up window with download link to updated JAR file
		 											
		 											Upon update's "First Launch"
				1)	deletes old JAR file
				2) 	replaces with new
				3)	leaves "Security.txt", "Variables.txt", and "User.txt" untouched
		 */
		//-----
		/*
		 * Sets file permissions to deny user access to "Assets" folder contents 
		 * 
		 * needs to be implemented with Sophie
		 */

		if(permissions())
		{
			if(printTo())
			{
				System.out.println("PPT - FLyer is running");
				return true;
			}
			else {
				System.out.println("Print to detected discrepencies");
				return false;
			}
		} else {
			System.out.println("System || Assets not found by permission");
			return false;
		}
		
	}
	Security(int x)
	{
		/*
		 * Used for launch
		 */
	}
	Security()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(dim.width/2 - (400/2), dim.height/2 - (700/2), 400, 700);
		
		setTitle("Launcher");
		 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 255, 255)));
		contentPane.setBackground(new Color(204, 230, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		field = new JTextField();
		field.setFont(new Font("Calibri", Font.PLAIN, 14));
		field.setForeground(Color.GRAY);
		field.setText("Email used at purchase");
		field.setBounds(15, 270, 355, 30);
		contentPane.add(field);
		field.setColumns(10);
		
		field.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				field.setText("");
				field.setForeground(Color.BLACK);
			}
		});
		field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyCode() == KeyEvent.VK_ENTER)
				{
					email = field.getText();
					System.out.println(field.getText());
				}
			}
		});
		
		/*
		 * Icons and lables
		 */
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.DARK_GRAY);
		lblEmail.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblEmail.setBounds(15, 240, 86, 29);
		contentPane.add(lblEmail);
	}
}




/*
 * https://www.javatpoint.com/example-to-connect-to-the-mysql-database
 * 
 * for mysql database : with sophie
 */







