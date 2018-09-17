import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class CatalogPanel extends JPanel {
	
	String[][] catalog;
	
	public CatalogPanel(GUI currentGUI){
		super();
		
		this.setSize(850, 600);
		this.setBackground(new Color(100,100,100,255));
		this.setLayout(null);
		
		catalog = currentGUI.mSearch.clone();
		
		JPanel viewer = new JPanel();
		viewer.setLayout(new BoxLayout(viewer, BoxLayout.Y_AXIS));

		// viewer will aways be the exact right height, enough to fit all cards
		int viewHeight = 100 * catalog.length;
		System.out.println(viewHeight);

		viewer.setBounds(0, 0, 840, viewHeight);

		
		
		for(String[] method : catalog){
			// creates an catalog card for every method and adds it to the viewer
			CatalogCard current = new CatalogCard(method);
			
			viewer.add(current);
			viewer.add(Box.createRigidArea(new Dimension(0, 2)));
		}




		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(viewer);
		
		

		// creates a scrollpane that will house viewer, allowing it to fit on a panel smaller than it
		//scrollPane.setBounds(5, 20, 400, 580);
		scrollPane.setBounds(10, 10, 840, 570);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// adds to the panel
		add(scrollPane);	
		
		
	}

}