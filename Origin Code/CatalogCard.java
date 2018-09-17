import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CatalogCard extends JPanel{
	
	String name, input, description, tags;
	
	public CatalogCard(String[] method){
		super();
		
		this.setMinimumSize(new Dimension(800, 100));
		this.setMaximumSize(new Dimension(800, 100));
		this.setPreferredSize(new Dimension(800, 100));

		this.setLayout(null);
		this.setBackground(Color.DARK_GRAY);
		
		
		
		name = method[0];
		input = method[1];
		description = "<html><body>" + method[2] + "</body></html>";
		tags = method[3];

		JLabel nameLabel = new JLabel("<html><h3>" + name + "</h3></html>");
		nameLabel.setBounds(5, 5, 100, 20);
		add(nameLabel);
		
		JLabel inputLabel = new JLabel("Input as: " + name + input);
		inputLabel.setBounds(110, 5, 480, 20);
		add(inputLabel);
		
		// TODO: implement character counter and newline breaks for description
		JLabel descLabel = new JLabel(description);
		descLabel.setBounds(5, 25, 650, 70);
		add(descLabel);
		
		// tags section
		String fixedTags = "<html><i>";
		String secondTags = "<html><i>";
		int lastSpace = 0;
		int tagCount = 0;
		boolean secondLabel = false;
		
		for(int count = 0; count < tags.length(); count++){
			if(' ' == tags.charAt(count)){
				String tempTag = tags.substring(lastSpace, count);
				tagCount++;
				lastSpace = count + 1;
				
				if(!secondLabel){
					fixedTags += tempTag + "<br>";
				}
				else{
					secondTags += tempTag + "<br>";
				}
				
				
				if(tagCount == 5){
					fixedTags += "</html>";
					secondLabel = true;
				}
			}
		
		}
		if(!secondLabel){
			fixedTags += "</i></html>";
		}
		else{
			secondTags += "</i></html>";
		}
		
		JLabel tagLabel = new JLabel(fixedTags);
		tagLabel.setBounds(660, 5, 70, 90);
		add(tagLabel);
		
		if(secondLabel){
			JLabel tagLabel2 = new JLabel(secondTags);
			tagLabel2.setBounds(730, 5, 70, 90);
			add(tagLabel2);
		}
		

	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		setBackground(new Color(220,220,220));
		
	// 	g.drawString(name, 10, 10);
		
	//	g.drawString(description, 300, 10);
	//	g.drawString(tags, 700, 10);
		
	}

}