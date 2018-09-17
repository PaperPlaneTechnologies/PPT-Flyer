// Created by : Nick Quigley
// Last major revision : 01.23.2017 @ 10:23
// fixed polyline function and added more degrees
// work in progress

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Graphing extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	// things used to draw the master grid
	private int WIDTH = 800;
	private int HEIGHT = 500;
	
	private double pointSpacer;
	
	private int halfWidth, widthDiviser;
	private int halfHeight, heightDiviser;
	


	private int[] xlist = new int[100];
	private int[] ylist = new int[100];
	
	private double[] xDouble = new double[100];
	private double[] yDouble = new double[100];
	
	private Color myColor;
	private ArrayList<Graphing> gArray;
	private Calcs currentCalcs;
	
	private String currentEquation = "";
	
	Graphing(Calcs C){
		
		// creates a drawing space for the line
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.LIGHT_GRAY);

		pointSpacer = WIDTH / 100; //decides how many pixels are between each point on the polyline

		halfWidth = WIDTH/2;
		widthDiviser = WIDTH/20; // divide the width-xval by this in order to place it in range (-10,10)

		halfHeight = HEIGHT/2;
		heightDiviser = HEIGHT/20; // used to change yval from graph coordinates to graphING coords
		
		// this arraylist is what holds every instance of this class to be drawn later. start with one
		gArray = new ArrayList<Graphing>();
		gArray.add(this);
		
		this.currentCalcs = C;
		

	}

	
	public void setVariables(String eq){
		
		
		// will use these to call setVariables at the end
		double five = 0, four = 0, three = 0, two = 0, one = 0;
		int signIndex, zero = 0;
		
		//condenses the string into a uniform form
		eq = eq.replaceAll(" ", "");
		
		int xHelper = 0;
		String tempEq = currentCalcs.subNegation(eq.toLowerCase());
		do{
			xHelper = tempEq.indexOf("x", xHelper);
			
			if (xHelper == 0){
				tempEq = tempEq.substring(0, xHelper) + "X" + tempEq.substring(xHelper+1);
			}
			
			else if(xHelper > 0){
				
				// looks at the character directly before the x
				// if its a digit, adds a * for syntax later on
				if(Character.isDigit(tempEq.charAt(xHelper - 1))){
					tempEq = tempEq.substring(0, xHelper) + "*X" + tempEq.substring(xHelper+1);
				} else {
					tempEq = tempEq.substring(0, xHelper) + "X" + tempEq.substring(xHelper+1);
				}
			}
			
			
			
		}while(xHelper >= 0);
		
		currentEquation = tempEq.toLowerCase();
		System.out.println(currentEquation);
		
		
		// ------ Creating all of those lists I need
		if(tempEq != "")
		{
			for(int count = 0; count < 100; count++){
				double xval = count * pointSpacer; // fills out 100 values between 0 and 300 in order to make a smooth line
				
				xlist[count] = (int) xval;
	
				// adjusts the x value to be in range (-10, 10) for purposes of calculation
				if(xval < halfWidth){
					xval = (halfWidth - xval) / -widthDiviser; 
				}
				else if (xval >= halfWidth){
					xval = (xval - halfWidth) / widthDiviser;
				}
				
				xDouble[count] = xval;
				
				String listEquation = currentEquation;
				
				
				listEquation = listEquation.replaceAll("x", Double.toString(xval));
				
				
				double yval = currentCalcs.graphingCycle(listEquation);
				yDouble[count] = yval;
				
				yval = (-1 * yval) + 10;
				ylist[count] = (int) (yval * heightDiviser);			
				
			}
		}

		
	
		/*
		System.out.println(fifthDegree + "," + fourthDegree + "," + thirdDegree + "," 
				+ secondDegree + "," + firstDegree + "," + displacer);
		*/
		
		repaint();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		// the assumed window at the beginning will be -10, 10 for both x and y axes
		// so for x : 1 unit = 30 pixels
		// y : 1 unit = 20 pixels


		// y-axis stuff
		g.drawLine(halfWidth, 0, halfWidth, HEIGHT);
		for(int i = heightDiviser; i < HEIGHT; i += heightDiviser){
			
			 g.drawLine( (int)(halfWidth - pointSpacer), i, (int)(halfWidth + pointSpacer), i);
		}
		
		//x-axis stuff
		g.drawLine(0, halfHeight, WIDTH, halfHeight);
		for(int i = widthDiviser; i < WIDTH; i += widthDiviser){
			
			// using pointSpacer to maintain consistency
			 g.drawLine(i, (int)(halfHeight - pointSpacer), i, (int)(halfHeight + pointSpacer));
		}
	
		// paints every instance of the function variables
		// basically at this point every instance of graphing is just holding the instance variables of degrees
		
		// TODO: Change color every time
		// recognize if the graph has a current equation of an empty line
		for(Graphing unit : gArray){
			if(unit.getCurrentEquation() != ""){
				unit.drawFunction(g);
			}
		}
		
		
		
	
	//	g2.drawFunction(g, Color.BLUE);
		
		
		//drawLine(x1, y1, x2, y2)
		// (0,0) top left, (300,200) bottom right
		
		
		}

	public void addLine(Graphing other){
		
		// adds another instance of graphing to the list. that way, it will paint that extra instance in the paint function
		gArray.add(other);
		repaint();
	}
	
	// sets color of current line
	public void setColor(Color c){ myColor = c;	}

	

	public void drawFunction(Graphics g){
		// the assumed window at the beginning will be -10, 10 for both x and y axes
		// so for x : 1 unit = 30 pixels
		// y : 1 unit = 20 pixels
	
		
		g.setColor(this.myColor);

		/*

		for(int count = 0; count < 100; count++){
			double xval = count * pointSpacer; // fills out 100 values between 0 and 300 in order to make a smooth line
			
			xlist[count] = (int) xval;

			// adjusts the x value to be in range (-10, 10) for purposes of calculation
			if(xval < halfWidth){
				xval = (halfWidth - xval) / -widthDiviser; 
			}
			else if (xval >= halfWidth){
				xval = (xval - halfWidth) / widthDiviser;
			}
			
			xDouble[count] = xval;
			
			String tempEq = currentEquation;
			
			
			tempEq = tempEq.replaceAll("x", Double.toString(xval));
			
			
			double yval = currentCalcs.graphingCycle(tempEq);
			yDouble[count] = yval;
			
			yval = (-1 * yval) + 10;
			ylist[count] = (int) (yval * heightDiviser);			
			
		}
		
		*/

		for(int count = 0 ; count <  100; count++){
			System.out.println("("+ xDouble[count] + ", " + yDouble[count]+ ")");
		}
		
		
		g.drawPolyline(xlist, ylist, 100);

	}
	
	public void setCurrentEquation(String eq){
		currentEquation = eq;
	}
	
	public String getCurrentEquation(){
		return currentEquation;
	}
	
	public ArrayList<Graphing> getGArray(){
		return gArray;
	}
	
	public int[] getXlist() {
		return xlist;
	}

	public int[] getYlist() {
		return ylist;
	}
	
	public double[] getXDouble(){
		return xDouble;
	}
	
	public double[] getYDouble(){
		return yDouble;
	}
}



