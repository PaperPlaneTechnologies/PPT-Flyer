import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GraphingPanel extends JPanel{

	// initializing them now so that we can use them later
	public Graphing g1, g2, g3, g4, g5, g6, g7, g8, g9, g10;
	JButton btnDraw;
	Calcs currentCalcs;
	
	GraphingPanel(Calcs currentCalcs, GUI currentGUI){

		this.setBackground(new Color(100,100,100,255));
		this.setBounds(0, 0, 828, 572);
		//this.setPreferredSize(new Dimension(828, 572));
		this.setLayout(null);
		this.currentCalcs = currentCalcs;

		// the main graph, the one we will draw everything on top of  
		// will NEVER hold an equation
		Graphing graph = new Graphing(currentCalcs);
		graph.setColor(Color.BLACK);
		graph.setBounds(25, 10, 800, 500);
		this.add(graph);
		
		//initializing g1 as a default
		g1 = new Graphing(currentCalcs);
		g1.setColor(Color.RED);
		graph.addLine(g1);
		
		JTextField yInput = new JTextField();
		yInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Draw");
				btnDraw.doClick();
				
			}
		});
		yInput.setText("Enter an equation here");
		yInput.addFocusListener(new FocusListener() {

			boolean firstTouch = true;
			public void focusGained(FocusEvent arg0) {
				if(firstTouch){
					yInput.setText("");
					firstTouch = false;
					currentGUI.currentPane = yInput;
				}
	
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				
				
			}
			
		});
		yInput.setBounds(345, 520, 222, 22);
		this.add(yInput);


		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Y\u2081", "Y\u2082", "Y\u2083", 
				"Y\u2084", "Y\u2085", "Y\u2086", "Y\u2087", "Y\u2088", "Y\u2089", "Y\u2081\u2080"}));
		comboBox.setBounds(282, 520, 51, 22);


		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){

				// grabs whatever item was last selected
				String selected = (String) comboBox.getSelectedItem();

				switch(selected){ // will make the current equation for the selected line appear in the textbox

				case "Y\u2081" : 
				yInput.setText(g1.getCurrentEquation());
				
				break;

				case "Y\u2082" : 
				
				if(g2 == null){
					g2 = new Graphing(currentCalcs);
					g2.setColor(Color.CYAN);
					graph.addLine(g2);
				}
				yInput.setText(g2.getCurrentEquation());
					
				break;

				case "Y\u2083" : 
				if(g3 == null){
					g3 = new Graphing(currentCalcs);
					g3.setColor(Color.GREEN);
					graph.addLine(g3);
				}
				yInput.setText(g3.getCurrentEquation());
				break;

				case "Y\u2084" : 
				if(g4 == null){
					g4 = new Graphing(currentCalcs);
					g4.setColor(Color.MAGENTA);
					graph.addLine(g4);
				}
				yInput.setText(g4.getCurrentEquation());
				break;

				case "Y\u2085" : 
				if(g5 == null){
					g5 = new Graphing(currentCalcs);
					g5.setColor(Color.ORANGE);
					graph.addLine(g5);
				}
				yInput.setText(g5.getCurrentEquation());
				break;

				case "Y\u2086" : 
				if(g6 == null){
					g6 = new Graphing(currentCalcs);
					g6.setColor(Color.BLUE);
					graph.addLine(g6);
				}
				yInput.setText(g6.getCurrentEquation());
				break;

				case "Y\u2087" : 
				if(g7 == null){
					g7 = new Graphing(currentCalcs);
					g7.setColor(Color.YELLOW);
					graph.addLine(g7);
				}
				yInput.setText(g7.getCurrentEquation());
				break;

				case "Y\u2088" : 
				if(g8 == null){
					g8 = new Graphing(currentCalcs);
					g8.setColor(new Color(175, 255, 175, 255));
					graph.addLine(g8);
				}
				yInput.setText(g8.getCurrentEquation());
				break;

				case "Y\u2089" : 
				if(g9 == null){
					g9 = new Graphing(currentCalcs);
					g9.setColor(new Color(175, 0, 255, 255));
					graph.addLine(g9);
				}
				yInput.setText(g9.getCurrentEquation());
				break;

				// called only for Y10
				default : 
				if(g10 == null){
					g10 = new Graphing(currentCalcs);
					g10.setColor(new Color(175, 75, 75, 255));
					graph.addLine(g10);
				}
				yInput.setText(g10.getCurrentEquation());
				break;

				}
			}
		});
		this.add(comboBox);

		btnDraw = new JButton("Draw");
		btnDraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String eq = yInput.getText();

				// this will signal setVariables which line is going to be drawn
				// +1 to make it so y1 will get called with int 1, to lessen confusion
				int lineToDraw = comboBox.getSelectedIndex() + 1;

				switch(lineToDraw){
				
				case 1:
					g1.setVariables(eq);
					break;

				case 2:
					g2.setVariables(eq);
					break;

				case 3:
					g3.setVariables(eq);
					break;

				case 4:
					g4.setVariables(eq);
					break;

				case 5:
					g5.setVariables(eq);
					break;

				case 6:
					g6.setVariables(eq);
					break;

				case 7:
					g7.setVariables(eq);
					break;

				case 8:
					g8.setVariables(eq);
					break;

				case 9:
					g9.setVariables(eq);
					break;

				case 10:
					g10.setVariables(eq);
					break;
						
				default:
					System.out.println("error");
					break;
				}
				
				graph.repaint();

			}
		});
		btnDraw.setBounds(279, 552, 88, 25);
		this.add(btnDraw);

		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				yInput.setText("");
				
				// this will signal setVariables which line is going to be cleared
				
				// +1 to make it so y1 will get called with int 1, to lessen confusion
				
				int lineToClear = comboBox.getSelectedIndex() + 1;

				switch(lineToClear){
				
				case 1:
					g1.setVariables("");
					break;

				case 2:
					g2.setVariables("");
					break;

				case 3:
					g3.setVariables("");
					break;

				case 4:
					g4.setVariables("");
					break;

				case 5:
					g5.setVariables("");
					break;

				case 6:
					g6.setVariables("");
					break;

				case 7:
					g7.setVariables("");
					break;

				case 8:
					g8.setVariables("");
					break;

				case 9:
					g9.setVariables("");
					break;

				case 10:
					g10.setVariables("");
					break;
						
				default:
					System.out.println("error");
					break;
				}
				
				graph.repaint();

			}
		});
		btnClear.setBounds(379, 552, 88, 25);
		this.add(btnClear);

		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				yInput.setText("");
				for(Graphing g : graph.getGArray()){
					g.setVariables("");
				}
				graph.repaint();
			}
		});
		btnClearAll.setBounds(479, 552, 88, 25);
		this.add(btnClearAll);
		
		JButton btnFindIntersect = new JButton("Find Intersect");
		btnFindIntersect.setBounds(657, 534, 111, 25);
		btnFindIntersect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] gList = new String[] {"Y\u2081", "Y\u2082", "Y\u2083", 
						"Y\u2084", "Y\u2085", "Y\u2086", "Y\u2087", "Y\u2088", "Y\u2089", "Y\u2081\u2080"};
				

			      JPanel myPanel = new JPanel();
			      myPanel.add(new JLabel("First Line:"));
			      
			      JComboBox<String> comboOne = new JComboBox<String>(gList);
			      
			      JComboBox<String> comboTwo = new JComboBox<String>(gList);
			      
			      myPanel.add(comboOne);
			      myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			      myPanel.add(new JLabel("Second Line:"));
			      myPanel.add(comboTwo);
			      

			      int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Select desired lines", JOptionPane.OK_CANCEL_OPTION);
			      if (result == JOptionPane.OK_OPTION) {
			      //   System.out.println("x value: " + xField.getText());
			      //   System.out.println("y value: " + yField.getText());
			    	  int selectedOne = comboOne.getSelectedIndex();
			    	  int selectedTwo = comboTwo.getSelectedIndex();
			    	  
			    	  Graphing graphOne = null;
			    	  Graphing graphTwo = null;
			    	  
			    	  switch(selectedOne){
			    	  
			    	  case 0:
			    		  graphOne = g1;
			    		  break;
			    		  
			    	  case 1:
			    		  graphOne = g2;
			    		  break;
			    		  
			    	  case 2: 
			    		  graphOne = g3;
			    		  break;
			    	
			    	  case 3:
			    		  graphOne = g4;
			    		  break;
			    		  
			    	  case 4: 
			    		  graphOne = g5;
			    		  break;
			    		  
			    	  case 5:
			    		  graphOne = g6;
			    		  break;
			    		  
			    	  case 6:
			    		  graphOne = g7;
			    		  break;
			    		  
			    	  case 7:
			    		  graphOne = g8;
			    		  break;
			    		  
			    	  case 8:
			    		  graphOne = g9;
			    		  break;
			    		  
			    	  case 9:
			    		 graphOne = g10;
			    		 break;
			    			  
			    	default:
			    		System.out.println("error!");
			    	  
			    	  }
			    	  
			    	  
			    	  switch(selectedTwo){
			    	  case 0:
			    		  graphTwo = g1;
			    		  break;
			    		  
			    	  case 1:
			    		  graphTwo = g2;
			    		  break;
			    		  
			    	  case 2: 
			    		  graphTwo = g3;
			    		  break;
			    	
			    	  case 3:
			    		  graphTwo = g4;
			    		  break;
			    		  
			    	  case 4: 
			    		  graphTwo = g5;
			    		  break;
			    		  
			    	  case 5:
			    		  graphTwo = g6;
			    		  break;
			    		  
			    	  case 6:
			    		  graphTwo = g7;
			    		  break;
			    		  
			    	  case 7:
			    		  graphTwo = g8;
			    		  break;
			    		  
			    	  case 8:
			    		  graphTwo = g9;
			    		  break;
			    		  
			    	  case 9:
			    		 graphTwo = g10;
			    		 break;
			    			  
			    	default:
			    		System.out.println("error!");
			    		
			    	  }
			    	  
			    	  ArrayList<double[]> interList = intersect(graphOne, graphTwo);
			    	  if(interList == null){
			    		  JOptionPane.showMessageDialog(null, "No intersect was found", "Failed", JOptionPane.ERROR_MESSAGE);
			    	  }
			    	  else{
			    		  String temp = "";
			    		  // allows for new lines
			    		  for(double[] point : interList){
			    			  // enters it in (x,y) form
			    			  temp += "(" + point[0];
			    			  temp += ", " + point[1] + "), ";
			    		  }
			    		  temp += ".";
			    		  JOptionPane.showMessageDialog(null, "Intersects found!" + temp, "Intersects found!", JOptionPane.INFORMATION_MESSAGE);
			    	  }
			      }
				
				
			}
		});
		add(btnFindIntersect);
		

	}
	
	public ArrayList<double[]> intersect(Graphing one, Graphing two){
		
		if(one == null || two == null){
			return null;
		}
		else if("" == one.getCurrentEquation() || "" == two.getCurrentEquation()){
			// searching for empties
			return null;
		}
		else if(one.getCurrentEquation() == two.getCurrentEquation()){
			return null;
		}
		
		double[] yListOne = one.getYDouble();		
		double[] yListTwo = two.getYDouble();
		
		String equationOne = one.getCurrentEquation();
		String equationTwo = two.getCurrentEquation();
		
		// only need one yList since the values will be the same for both
		double[] xList = one.getXDouble();
		
		ArrayList<double[]> returnList = new ArrayList<double[]>();
		
		// basically we're going to iterate through listOne
		// for index n in listOne, we compare index n-1 and n+1 in listTwo
		// if n-1 is lower and n+1 is higher, or vice versa, intersect is between these!
		// finding intersect: look in center. if exact match, return!
		// else look either up or down depending. I think this is Binary search
		
		for(int count = 1; count < 99; count++){
			
			double n = yListOne[count];
			double nMinus = yListTwo[count-1];
			double nPlus = yListTwo[count+1];
			
			boolean intersect = false;
		//	System.out.println(nMinus + " | " + n + " | " + nPlus);
			if(nMinus < n){
				// n-1 less than n
				if(nPlus > n){
					// there is an intersect true!
					intersect = true;
				}
				
			}
			else if (nMinus > n){ // n-1 > n
				if(nPlus < n){
					intersect = true;
				}
			}
			
			
			
			// you'll notice there is no equal to portion. if either of these is equal to n, then it is not an intersect 
			
			// ----- past this point we assume an intersect is found.
			// if no intersect is found, spit us out and check the next value
			
			if(intersect){
				// find! that! point!
				
				boolean found = false;
				double decimalPlace = Math.pow(10, ((-1) * Integer.parseInt(currentCalcs.userValue[0])));
				
				
				double xBelow = xList[count - 1];
				double xAbove = xList[count + 1];
				double lastDifference = 99999;
				
				while(!found){
					
					
					
					double xBetween = (xAbove + xBelow)/2;
					
					
					double y1 = currentCalcs.graphingCycle(equationOne.replaceAll("x", xBetween + ""));
					double y2 = currentCalcs.graphingCycle(equationTwo.replaceAll("x", xBetween + ""));
					lastDifference = Math.abs(y1) - Math.abs(y2);
					
					if(lastDifference < decimalPlace){
						
						double[] temp = new double[2];
						
										
						System.out.println(xBetween + "," + y1);
						temp[0] = xBetween;
						temp[1] = y1;
						
						returnList.add(temp);
						//found=true;
						break;
					}
					
				
					
					if(xBelow <= 0 && xAbove <= 0){
						
						if(y1 > y2){
							xAbove = xBetween;
						}
						else{
							xBelow = xBetween;
						}
						
						// to the next iteration!
					}
					else if(xBelow >= 0 && xAbove >= 0){
						
						if(y1 > y2){
							
							xBelow = xBetween;
						}
						else{
							xAbove = xBetween;
						}
						
						// to the next iteration
					}
					
					else{
						// if one is above zero and one below
						// we'll divide the interval into two additional halves to test which way the intersect is
						
						double xTestAbove = (xBetween + xAbove) / 2;
						double xTestBelow = (xBetween + xAbove) / 2;
						
						
						// this part finds the difference between the two points, then goes to whichever difference is smaller
						double yTestAbove = currentCalcs.graphingCycle(equationOne.replaceAll("x", xTestAbove + ""))
										- currentCalcs.graphingCycle(equationTwo.replaceAll("x", xTestAbove + ""));
						
						
						double yTestBelow = currentCalcs.graphingCycle(equationOne.replaceAll("x", xTestBelow + ""))
										- currentCalcs.graphingCycle(equationTwo.replaceAll("x", xTestBelow + ""));
						
						
						if(Math.abs(yTestAbove) < Math.abs(yTestBelow)){
							// if above is smaller than below, we go upwards
							xBelow = xBetween;
						}
						else{
							xAbove = xBetween;
						}
					}
					
					
				}
				
			}
			
	
		}
		
				
		return returnList;
	}
	
	/*
	// TODO: 
	// Table intersect
	
	// FOR TABLE:
	 * just pull up xlist and ylist
	
	
	// FOR INTERSECT:
	// compare values above and below
	// so for index 3 on list 1, look at indices 2 and 4 on list 2
	// use cut in half and search
	// at 10^-4, approximate
	
	*/
	
	
		
		
		
		
		
}
