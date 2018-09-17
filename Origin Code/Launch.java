import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Launch extends JFrame {

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
					Launch frame = new Launch();
					frame.setVisible(false);

					Security s = new Security(1);
					
					if(s.security())
					{
						GUI g = new GUI();
						g.setVisible(true);
						
						frame.setVisible(false);
						frame.dispose();
						
					} else {
						frame.setVisible(false);
						frame.dispose();
					} 
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Launch() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/*
			TODO add logo
			 	ImageIcon image = new ImageIcon("image/pic1.jpg");
				JLabel label = new JLabel("", image, JLabel.CENTER);
				JPanel panel = new JPanel(new BorderLayout());
				panel.add( label, BorderLayout.CENTER );
		 */
	}
}


