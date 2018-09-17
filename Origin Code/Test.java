import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

//CTRL + SHIFT + NUMPAD_DIVIDE

//'alt' + 'shift' + 'r' : replace all of variable
//'crtl' + 'F11' : runs class 

public class Test extends Calcs{
	   public static void main(String args []) throws IOException
	   {
		   Test t = new Test();
		   
		   /*
		   for(int x = 1; x < 10; x++)
		   {
			   String E = genE(x);
			   
			   System.out.println("\n===================================================\n     " + x);
			   System.out.println("EQUATION-------- " + E);
		       try {
		    	   System.out.println("ANSWER---------- " + t.cycle(E));
		       } catch (NumberFormatException | StringIndexOutOfBoundsException a) {
		    	   System.out.println(a);
		    	   System.out.println("ERROR----------- " + t.cycle(E));
		       }
		   }
		  */
		  System.out.println("\n============================================\n     Manual Test");
	      String e = "recttopolar(5, 1)";
	
	      System.out.println("Equation = " + e + "\n");
	      System.out.println("\nAnswer   = " + t.cycle(e) );
		   
		   
	       //   '  √  ' is radical     key	
	       //   '  —  ' is subtraction key
	   }
	   //    		if(eList.size() > 0)	//TEST parameter	remove before deployment
	  
	   /*\
	    				Where am I ?
	    
		*			add web connection, user debug reports, test logic cycle, max operation time out limit : <after> : separate package of just calculator 


	    *  Found Errors		:		Those solved are removed from this list
	    *  	CREATE MAXIMUM VALUE LIMITS	: finite number || time hang in software || input value limits
	    *  	Parenthesis methods calling trim() a second time : unable to locate reason for double calling of before & after parenthesis()
	    *  		//but not when radical par is called : may ignore as uses little data
	    *  	24*2+51√745^28/1 results in E+___ error
	    *  	3+3(√(8) && (√(9))+9 isolating all but √3 & √9	
	    *  
	    *  
	    *  IndexOutOfBoundsException & NumberFormatException
	    *  	
	   \*/
	   
	   /*
	    * 			Legal
	    * 
	    * Apache Commons
	    * 	https://www.whitesourcesoftware.com/...blog/top-10-apache-license-questions-answered/
	    */
	   
       //Funnel metrics 
	 		/*
	 		 * Market 
	 		 * Product
	 		 * Team
	 		 * Business model
	 		 * Competition
	 		 */
	   //-----
	   
	public static String genE(int x)
	   {
		   String E = Integer.toString(x);
		   char op[]  = {'^', '*', '/', '+', '—', '√', '—', '*', '/', '+', '—', '+'};
		   
		   int operand = (int)(Math.random() * 10);
		   
		   int y       = 0;
		   
		   while(y < 16)
		   {
			   operand = (int)(Math.random() * 10);
			   
			   if(operand >= 3)					//generates a random number to be added to E
			   {
				   E = E + (int)(Math.random() * 10);
			   } 
			   //generates an operation to be added to E
			   if( (operand < 3) && ((E.charAt(E.length()-1) == '0') || (E.charAt(E.length()-1) == '1') || (E.charAt(E.length()-1) == '2') || (E.charAt(E.length()-1) == '3') ||
					   				 (E.charAt(E.length()-1) == '4') || (E.charAt(E.length()-1) == '5') || (E.charAt(E.length()-1) == '6') || (E.charAt(E.length()-1) == '7') ||
					   				 (E.charAt(E.length()-1) == '8') || (E.charAt(E.length()-1) == '9') ))	
			   {
				   int val =(int)(Math.random() * 10);
				   //System.out.println(val);
				   E = E + op[val];
			   }
			   y++;
		   }
		   if(E.charAt(E.length()-1) == '+' || E.charAt(E.length()-1) == '*' || E.charAt(E.length()-1) == '/' || 
		      E.charAt(E.length()-1) == '^' || E.charAt(E.length()-1) == '√' || E.charAt(E.length()-1) == '—' )
			  
			   E = E.substring(0, E.length()-1);
		   
		   return E;
	   }
	   
	  
}
	/*									Notes
	 * num(=num			:		within GUI
	 * 
	 * !! methods with input parameters that exceed required are nullified 
	 * 
	 */
	
	//https://triplebyte.com/company/register : for new hires after site created
	
	//ALL Equations are written for find cases but will display in visual sub and super scripts for proper comprehension 
	//Example  :  Log(sub)(normal)   ||   x^(super)    so the display will read the same as if written in text

	//  "http://tools.oratory.com/altcodes.html" for special char
	
	/*==============================		TODO		==============================
		
		Syntax detection try catch block 
		Methods spreadsheet completion
		Variables file work correctly
		Customization file that works for GUI class
		Cloud storage
		Graphing (Nick)
		Spreadsheets (Nick and Max)
		Adding the rest of all math symbols to be usable (sum() methods ect.)
		Program ability    "Pgrm:"
		User added methods "PgrmÃ‚Â»  Name  :(  Program  )" 		//Notes stored on phone for how to
		Add to methods() : trim whitespace within (...) set
		Proper unknown syntax catch
		Trimming long equations to be under x char to allow user to view method called 
		
	    ==============================		JFrame		==============================
		
		Proper layout ratios
		add windowClosing  : saveAll			x
		add windowOpen     : getVar				x
		Customization file
		TextWraps
		Visual catalog form GUI sMehtods
		
		==============================		GUI Keep		==============================
		
		Search bar
		
		==============================		ERRORS		==============================
		
		001 : File is not found : returned when user attempts to launch program and deletes "Variables.txt" || closes and deletes
		002 : No value saved (lastAnswer)
		
		==============================		Next Update		==============================
		
		Allow users to add there own tags to methods
		User created methods with wizard support
	
	*/