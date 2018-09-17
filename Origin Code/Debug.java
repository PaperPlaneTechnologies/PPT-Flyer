import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Debug {
	/*
	 * TODO change all print statements that were used in testing to write to the "Error.txt" file
	 */
	public static List<String> getError()
	{
		Path path = Paths.get(System.getProperty("user.dir") + File.separator + "User" + 
				 			  File.separator + "Assets" + File.separator + "Error.txt");
		
		try {
			List<String> data = Files.readAllLines(path);
			
			return data;
		} catch (IOException a) {
			Errors e = new Errors(6);
			e.setVisible(true);
		}
		
		return null;
	}
	
	public static void sendError()
	{
		if (JOptionPane.showConfirmDialog(null, "Would you like to help us fix your bug?", "Debug Notice",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
		{
			final String username = "paperplanetechnologies.errors@gmail.com";
			final String password = "Dragonfish4325$#@%";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {
				Message message = new MimeMessage(session);
				
				message.setFrom(new InternetAddress("paperplanetechnologies.errors@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("paperplanetechnologies.errors@gmail.com"));
				
				
				Security s = new Security(1);
				String account = "";
				
				if(s.isWindows())
				{
					account = s.getWindowsSerialNumber();
				} 
				else if(s.isMac()) 
				{
					account = s.getMacSerialNumber();
				}
				else if(s.isUnix())
				{
					account = s.getNixSerialNumber();	
				}
				
				message.setSubject(account + " : Error report");
				
				List<String> log = Debug.getError();
				
				if(!log.equals(null))
				{
					String logText = "";
					for(int x = 0; x < log.size(); x++)
					{
						logText = logText + log.get(x) + System.lineSeparator();
					}
					
					if(logText.equals(""))
					{
						Errors e = new Errors(6);
						e.setVisible(true);
					}
					
					System.out.println(logText);
					
					message.setText("User is reporting an error" + System.lineSeparator() + System.lineSeparator() + "Last input was" + System.lineSeparator() + System.lineSeparator() + "---------------" + System.lineSeparator() + logText + "---------------");
					
				} else {
					Errors e = new Errors(7);
					e.setVisible(true);
				}
				
				Transport.send(message);

				Errors e = new Errors(7);
				e.setVisible(true);
			} catch (MessagingException a) {
				Errors e = new Errors(5);
				e.setVisible(true);
			}		
		} else {
			Errors e = new Errors(11);
			e.setVisible(true);
		}		
		
	}
		
	public static void main(String args[])
	{
		sendError();
	}
}






















