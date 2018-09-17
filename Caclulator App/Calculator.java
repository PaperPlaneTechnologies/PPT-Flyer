import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.FractionConversionException;
import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.util.ArithmeticUtils;

public class Calculator implements Methods {
    String[]          options   = {"Decimal places", "Background color", "Defult font sytle", "Defult font color", 
    							   "Defult font size", "Calculator color"};
    String[]          userValue = {"6", "984330", "Calibri", "000000" ,"12", "984330"};				//size = optionss.length
    
    private int     decimalPlaces = Integer.parseInt(userValue[0]) + 1;	//'+1' is done to allow for user input to be # of values after '.'
    private boolean fromMethods   = false;								//For cycle() to run properly
    private boolean parMethod	  = false;
    private boolean radicalPar    = false;								//To process radical operations once
    
    // Equation and answer variables 
    private String  lastAnswer    = "";  								// Holds the trimmed value from the last input : updated before sent to display	
    private String  equation      = "";									// Holds the current input equation				
    
    private String  rawEquationInput       	  = "";						// Holds a copy of the equation as it was inputed by the user, to be displayed with the calculated answer ( as operations change the display of the equation )
    
    //==============================================    Equation Parsing	==============================================
    private static double getAfter(int at, String E)				   	//returns the double after input index
    {
        for(int x = at + 1; x < E.length(); x++)
        {
            if(E.charAt(x) == '(')
                return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == ')')
                return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == '^')
                return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == '√')
                return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == '*')
                return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == '/')
            	return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == '+')
                return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == '—')
            	return Double.parseDouble(E.substring(at + 1, x));
            if(E.charAt(x) == ',')
                return Double.parseDouble(E.substring(at + 1, x));
            if(x == E.length()-1)
                return Double.parseDouble(E.substring(at + 1));
        }
        return 12345.54321;
    }   
    private static double getBefore(int at, String E)					//returns the double before input index
    {
        int x = at - 1;
        while(x > -1)
        {
            if(E.charAt(x) == '(')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == ')')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == '^')
               return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == '√')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == '*')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == '/')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == '+')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == '—')
                return Double.parseDouble(E.substring(x + 1, at));
            if(E.charAt(x) == ',')
                return Double.parseDouble(E.substring(x + 1, at));
            if(x == 0)
                return Double.parseDouble(E.substring(0, at));
            x--;
        }
        return 12345.54321;
    }   
    private static String getAfterString(int at, String E)				//returns the double after input index
    {
        for(int x = at + 1; x < E.length(); x++)
        {
            if(E.charAt(x) == '(')
                return (E.substring(at + 1, x));
            if(E.charAt(x) == ')')
                return (E.substring(at + 1, x));
            if(E.charAt(x) == '^')
                return (E.substring(at + 1, x));
            if(E.charAt(x) == '√')
                return (E.substring(at + 1, x));
            if(E.charAt(x) == '*')
                return (E.substring(at + 1, x));
            if(E.charAt(x) == '/')
            	return (E.substring(at + 1, x));
            if(E.charAt(x) == '+')
                return (E.substring(at + 1, x));
            if(E.charAt(x) == '—')
            	return (E.substring(at + 1, x));
            if(E.charAt(x) == ',')
                return (E.substring(at + 1, x));
            if(x == E.length()-1)
                return (E.substring(at + 1));
        }
        return "12345.54321";
    }   
    private static String getBeforeString(int at, String E)				//returns the double before input index
    {
    	/*
    	 * Returns the value before the current operation as a String value
    	 */
        int x = at - 1;
        while(x > -1)
        {
            if(E.charAt(x) == '(')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == ')')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == '^')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == '√')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == '*')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == '/')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == '+')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == '—')
                return (E.substring(x + 1, at));
            if(E.charAt(x) == ',')
                return (E.substring(x + 1, at));
            if(x == 0)
                return (E.substring(0, at));
            x--;
        }
        return "12345.54321";
    }   
    private static int getIndexBefore(int at, String E)             	//returns index of prior symbol for Equations trimming
    {
        int x = at-1;
        while(x > -1)
        {
            if(E.charAt(x) == '(')
                return x;
            if(E.charAt(x) == ')')
                return x;
            if(E.charAt(x) == '^')
                return x;
            if(E.charAt(x) == '√')
            	return x;
            if(E.charAt(x) == '*')
                return x;
            if(E.charAt(x) == '/')
                return x;
            if(E.charAt(x) == '+')
                return x;
            if(E.charAt(x) == '—')
                return x;
            if(E.charAt(x) == ',')
            	return x;
            if(x == 0)
                return 0;
            x--;
        }
        return 0;
    }
    private static int getIndexBeforeSym(int at, String E)          	//returns index of prior symbol for Equations trimming ignoring (...) sets
    {
        int x = at-1;
        while(x > -1)
        {
            if(E.charAt(x) == '^')
                return x;
            if(E.charAt(x) == '√')
            	return x;
            if(E.charAt(x) == '*')
                return x;
            if(E.charAt(x) == '/')
                return x;
            if(E.charAt(x) == '+')
                return x;
            if(E.charAt(x) == '—')
                return x;
            if(E.charAt(x) == ',')
            	return x;
            if(E.charAt(x) == '0')
                return x;
            if(E.charAt(x) == '1')
                return x;
            if(E.charAt(x) == '2')
                return x;
            if(E.charAt(x) == '3')
                return x;
            if(E.charAt(x) == '4')
                return x;
            if(E.charAt(x) == '5')
                return x;
            if(E.charAt(x) == '6')
                return x;
            if(E.charAt(x) == '7')
                return x;
            if(E.charAt(x) == '8')
                return x;
            if(E.charAt(x) == '9')
                return x;
            if(x == 0)
                return 0;
            x--;
        }
        return 0;
    }
    private static int getIndexAfter(int at, String E)              	//returns index of following symbol for Equation trimming
    {
        int x = at+1;
        while(x < E.length())
        {
            if(E.charAt(x) == '(')
                return x;
            if(E.charAt(x) == ')')
                return x;
            if(E.charAt(x) == '^')
                return x;
            if(E.charAt(x) == '√')
            	return x;
            if(E.charAt(x) == '*')
                return x;
            if(E.charAt(x) == '/')
                return x;
            if(E.charAt(x) == '+')
                return x;
            if(E.charAt(x) == '—')
                return x;
            if(E.charAt(x) ==',')
            	return x;
            if(x == E.length()-1)
                return x;
            x++;
        }
        return 1234567890;
    }
    private String trim(String E)                                    	//second answer will return actual value in scientific notion (Done by Math class) (WIP)
    {
    	/*
    	 * Modifies the returned answer to be in a cleaner format to be sent to display
    	 * 
    	 * 		( always called before returning answer )
    	 */
    	try{
	    	lastAnswer = E;										//preserves no edited value for large decimal values
	    	
	    	System.out.println(">>>     Trim input = " + E);
	    	
	    	String sciNotion = null;
	        boolean sigZeros = false;
	    	/*
	    	 * Alter to check for leading '0's for very small number for E-_x_ and 
	    	 * Very large numbers 
	    	 */
	        //----------		Zeros
	    	if(E.equals("0.0"))									//if value is |0| removes decimals
	    	{
	    		System.out.println("0.0 cut");
	    		return "0";
	    	}
	    	if(Double.parseDouble(E) == 0)
	    	{
	    		return E;
	    	}
	    	//----------		Makes scientific notation more readable
	    	if(E.contains("E") && (E.charAt(E.indexOf('E')+1)=='1' || E.charAt(E.indexOf('E')+1)=='2' || E.charAt(E.indexOf('E')+1)=='3' ||
	    						   E.charAt(E.indexOf('E')+1)=='5' || E.charAt(E.indexOf('E')+1)=='6' || E.charAt(E.indexOf('E')+1)=='7' ||
	    						   E.charAt(E.indexOf('E')+1)=='8' || E.charAt(E.indexOf('E')+1)=='9' || E.charAt(E.indexOf('E')+1)=='+' || 
	    						   E.charAt(E.indexOf('E')+1)=='-') )
			{
	    		sciNotion = E.substring(E.indexOf('E'));
	    		if(sciNotion.contains("+"))
	    		{
	    			sciNotion = "E" + sciNotion.substring(sciNotion.indexOf('+') + 1);
	    		}
			}
	    	
	    	System.out.println(">>>     Input sciNotion : " + sciNotion);
	        
	    	//---------			For small numbers
	        if(E.contains("E-"))
	        {
	        	sigZeros = true;
	        }
	        if(E.charAt(0) == '0' && E.charAt(1) == '.')		//detects if there are leading '0' in a small number
	        {
	        	int    signifance = 0;
	        	while(signifance < E.length() && E.charAt(signifance) == '0')
	        	{
	        		signifance++;
	        	}
	        	if(signifance > decimalPlaces)
	        	{
	        		E = E.charAt(signifance) + "." + E.substring(signifance + 1) + "E-" + signifance;
	        	}
	        }
	        
	        //----------		For large numbers 
		    if(!sigZeros)
		    {
		    	int significants = 15;				//value used to avoid length 15 error when '-' is present
		    	if(E.indexOf('.') == -1)			//.0 added to avoid errors in next conditional
		    	{
		    		E = E + ".0";
		    	}
		    	if(E.contains("-"))
		    	{
		    		significants = 16;
		    	}
	        	if(E.substring(0, E.indexOf('.')).length() > significants)
		        {
		        	int powerBase = 0;
		        	
		        	if(E.charAt(0) != '-')
		        	{
			        	powerBase = E.length() - 3;
		        		E = E.charAt(0) + "." + E.substring(1, decimalPlaces) + "E" + powerBase;
		        	}
		        	if(E.charAt(0) == '-')
		        	{
			        	powerBase = E.length() - 4;
		        		E = E.substring(0, 2) + "." + E.substring(2, decimalPlaces) + "E" + powerBase;
		        	}
		        	
		        	return E;
		        }
		    }
		    
	    	//---------			removes trailing 'xxx.0' case
	    	if(E.charAt(E.indexOf('.') + 1) == '0' && E.indexOf('.') == E.length() - 2)
	    	{
	    		System.out.println("Insignificant 0 removed");
	    		
	    		E = E.substring(0, E.indexOf('.'));
	    	}
	    	
	    	//----------		
	    	String trim = "";
	    	if(E.indexOf('.') > -1)
	    	{
	    		trim = E.substring(E.indexOf('.') + 1);		//hold just decimal values
	    	}
	    	if(trim.contains("E"))
	    	{
	    		trim = trim.substring(0, trim.indexOf('E'));
	    	}
	    	if(trim.length() > decimalPlaces)					//if value holds more decimal places than requested
	    	{
	    		System.out.println("decimalValuess > deciamlPalces");
	    		
	    		E = E.substring(0, E.indexOf('.') + 1) + trim.substring(0, decimalPlaces);
	    		
	    		if(sciNotion != null)
	    		{
	    			E = E + sciNotion;
	    		}
	    		return E;
	    	}
    	} catch (Exception e) {
    		/*
             * An error has occurred if this is reached.
             * The debugger is not called as it is typicall
             * a result from user inputs
             */
    	}
    	System.out.println("Value is not trimed");
    	
        return E;
    }
    private String whiteSpace()
    {
    	/*
    	 * Removes white space from the equation WITHOUT altering the stored value
    	 */
    	return this.equation.replaceAll("\\s+","");
    }
    private String subNegation(String E)
    {
    	/*
    	 * determines if the short dash ( - ) is a negative modifier or a subtraction sign ( long dash — )
    	 */
    	System.out.println("input subNeg " + E);
    	
		for(int x = 0; x < E.length(); x++)
		{
			if(E.charAt(x) == '-')
			{
				try {
    				if(Character.isDigit(E.charAt(x - 1)))
    				{
    					E = E.substring(0, x) + "—" + E.substring(x + 1);
    				}
    				if(E.charAt(x - 1) == ')')
    				{
    					E = E.substring(0, x) + "—" + E.substring(x + 1);
    				}
    				if(E.charAt(x - 1) == 'x')
    				{
    					E = E.substring(0, x) + "—" + E.substring(x + 1);
    				}
				} catch (Exception so1) {
					/*
			         * An error has occured if this is reached.
			         * Built in debugger is called and a report will be sent
			         */
					return "Error";
		    	}
			}
		}
    	
    	return E;
    }
    //==============================================    Parenthesis methods     ==============================================
    private String getParSub(String E)
    {
    	/*
    	 * Returns the values within the deepest (...) set being operated and removes ' ( ' and ' ) '
    	 */
        int open = 0, close = 0, x = 0;            

        while(x < E.length())                      //Finds last '('
        {
            if(E.charAt(x) == '(')
                open = x;
            x++;
        }
        x = E.length()-1;                          //Resets x counter to last index in E to decrement form
        while(open < x)                            //Finds ')' form index of '('
        {
            if(E.charAt(x) == ')')
                close = x;
            x--;
        }
        return E.substring(open+1, close);        //Trims '(' && ')' from substring
    }
    private int parOpen(String E)
    {
        int x = 0, open = 0;
        while(x < E.length())                    //Finds last '('
        {
            if(E.charAt(x) == '(')
                open = x;
               x++;
        }
        return open;
    }  
    private int parClose(String E)
    {
        int open = parOpen(E), x = open, close = 0;
        while(x < E.length())                    //Finds first ')' after last '(' is found
        {
            if(E.charAt(x) == ')')
            {
                close = x;
                return close;
            }
               x++;
        }
        return close;
    }
    private String parenthesis(String E) throws IOException
    {      
    	System.out.println("(input)      " + E);
        
    	int open = 0, close = 0, i = 0;
        String par = "", first = "", last = "";
        while(i < E.length())                   //done in loop to get all (...) sets 
        {            
        	if(!E.contains("("))
        		return E;
            par = getParSub(E);                 //gets substring from open where true
            open = parOpen(E);
            close = parClose(E);                //gets index value of par length w/in E (open is length of first to par length (not E) to remove ')' [not -1 bc inclusive])
            
            try	{
            	if(E.charAt(close + 1) == '√')			//if par set is a radical operation is ignored
                {
            		//Do Nothing
                } else {
                	par = parCycle(par);                 //goes through all math ops
                   
                	first = E.substring(0, open);        //gets E from 0 > index of current open && removes '('
                    last = E.substring(close+1);
                    
                    E = first + par + last;
                }
            } catch (StringIndexOutOfBoundsException s01) {
                par = parCycle(par);                 //goes through all math ops
                
                first = E.substring(0, open);        //gets E from 0 > index of current open && removes '('
                last = E.substring(close+1);
                
                E = first + par + last;
            }
            
            if(!E.contains("(") && !parMethod)        //conditional to check if E has no (...) sets to be sent to cycle( E )
            {
            	E = cycle(E);
                return E;
            }
            i++;                                     //inc until open < E.length( )
        }
        return E;                                    //E should be equal to first + par + last;
    }   
    private String parCycle(String E)
    {
        E = trig(E);        
        E = specialMath(E);

        for(int x = 0; x < E.length(); x++)        //finds all '^' in E and replaces
       	{
       		if(E.charAt(x) == '^')
       		{
       			int lastEx = x;
       			
       			while(E.charAt(getIndexAfter(lastEx, E)) == '^')
       			{
        			lastEx = getIndexAfter(lastEx, E);
        		}               
        		while(lastEx > 0 && E.charAt(lastEx) == '^')
       			{
       				E = exponet(lastEx, E);
       				if(E.equals("Undefined"))
           				return E;
       				try {
       					lastEx = getIndexBefore(lastEx, E);  //check if will return 0 when indexAt is the last key in string
       				} catch (StringIndexOutOfBoundsException a) { 
       					/*
       			         * An error has occurred if this is reached.
       			         */
       					break;
       				}
        		}
               
        	}
       	}	
     	for(int x = 0; x < E.length(); x++)        //finds all '√' in E and replaces
       	{
     		if(E.charAt(x) == '√')
     		{
     			E = radical(x, E);
     			x = 0;
     		}
       	}   
       	for(int x = 0; x < E.length(); x++)        //finds all '*' && '/' in E and replaces
       	{
       		if(E.charAt(x) == '*'){
       			E = multiply(x , E);
       			x = 0;
       		}
       		if(E.charAt(x) == '/'){
       			E = divide(x , E);
       			if(E.equals("Undefined"))
       				return E;
       			x = 0;
       		}
       	}   
        for(int x = 0; x < E.length(); x++)         //finds all '+' && '-' in E and replaces
       	{
    		if(E.charAt(x) == '+' && E.charAt(x - 1) != 'E')
    		{
    			E = add(x , E);
	       		x = 0;
	       	}
       		if(E.charAt(x) == '—' && E.charAt(x - 1) != 'E'){
       			E = subtract(x , E);
       			x = 0;
       		}
       	}
        return E;
    }
    private String unParSet(String E)
    {
    	/*
    	 * While this is done to close all unclosed (...) sets it is not a complex AI.
    	 * This code will only add [' ) ' || ' ( '] to the [end || beginning] of the equation processed
    	 * for closing operations to properly detect the (...) sets for proper parsing.
    	 */
    	int open = 0, close = 0;
    	int y = 0;
    	//----------		Excess close at front || open at end 
    	while(E.charAt(y) == ')')
    	{
    		if(E.charAt(y) == ')')
    		{
    			E = E.substring(1);
    		} else {
    			break;
    		}
    	}
    	
    	y = E.length() - 1;
    	
    	while(E.charAt(y) == '(')
    	{
    		if(E.charAt(y) == '(')
    		{
    			E = E.substring(0, y);
    		} else {
    			break;
    		}
    	}
    	//----------		Empty parenthesis removed	
    	if(E.contains("(") || E.contains(")"))
    	{
    		int x = 0;
    		while(x < E.length() - 1)
    		{
    			if(E.charAt(x) == '(' && E.charAt(x + 1) == ')')
    			{
    				E = E.substring(0, x) + E.substring(x + 2);
    				x = x - 1;
    			}
    			x++;
    		}
    	}
    	//----------		Closing AI
    	if(E.indexOf(')') < E.indexOf('(') && E.indexOf(')') > 0)
    	{
    		E = "(" + E;
    	}

    	for(int x = 0; x < E.length(); x++)
    	{
    		if(E.charAt(x) == '(')
    		{
    			open++;
    		}
    		if(E.charAt(x) == ')')
    		{
    			close++;
    		}
    	}
    	//---------			 Adds '(' & ')' to complete sets
    	if(open != close)
    	{
    		while(open != close)
    		{
    			if(close > open)
    			{
    				E = "(" + E;
    				open++;
    			}
    			if(close < open)
    			{
    				E = E + ")";
    				close++;
    			}
    		}
    	}
    	//----------		TODO TEST :: (x)(y) >> (x)*(y) fix
    	for(int x = 0; x < E.length() - 1; x++)							
    	{											
    		if(E.charAt(x) == ')' && E.charAt(x + 1) == '(')
    		{
    			E = E.substring(0, x + 1) + "*" + E.substring(x + 1);
    		}
    	}
    	return E;
    }
    private String radicalPar(String E)
    {
    	/*
    	 * Restructures equations that contain '√' to fit radical syntax
    	 * Returns (*) ((index) √ (radican))
    	 
    	  					-- input syntax and returns --
    	 
    	 *					-- Square roots
    	 * √radican 					>>		((2.0)√(radican))
    	 * √(radican)					>>		((2.0)√(radican))
		 * (√(radican))					>>		((2.0)√(radican))
		 * 
		 * 					-- Nonsquared roots
		 * (index)√radican				>>		((index)√(radican))
		 * ((index)√(radican))			>>		((index)√(radican))
		 * operation((index)√(radican)) >>		operation*((index)√(radican))		TODO :: TEST
		 * 
		 * 					-- Operations before square roots
		 * value√radican				>>		value*((2.0)√(radican))
		 * value(operation)√radican 	>>		value(operation)((2.0)√(radican))
		 * 
    	 */
    	radicalPar = true;
    	if(E.contains("√"))
    	{
    		System.out.println("radicalPar input " + E);
    		
	    	int x = 0;
	    	while(x < E.length())
			{
		    	if(E.charAt(x) == '√')
		    	{
		    		String index = "", radical = "", first = "", last = "";
		    		int loc = x, parAfter = 0;;
		    		
		    		//------		index		------
		    		try						//when user uses "(index)√radican" syntax
		    		{
		    			if(E.charAt(loc - 1) == ')')
		    			{
		    				index = E.substring(getIndexBeforeSym(loc - 1, E), loc - 1);
		    				index = "*((" + Double.parseDouble(index) + ")";
		    			} else {
		    				//no index declared
		    				index = "*((2.0)";
		    			}
		    		} catch (StringIndexOutOfBoundsException s01) {
		    			//no index declared and at beginning
		    			index = "*((2.0)";
		    		}
		    		
		    		//------		radical		-----
		    		if(E.charAt(loc + 1) == '(')
		    		{
		    			radical  = Double.parseDouble(findFirst(E.substring(loc + 1))) + "";
		    			if((radical.charAt(radical.length() - 1) == ')' && radical.charAt(radical.length() - 2) == ')'))
		    			{
		    				parAfter = -1;
		    			}
		    		}
		    		if(E.charAt(loc + 1) != '(')
		    		{
		    			radical  = "(" + getAfterString(loc, E) + "))";
		    			parAfter = 2;
		    		}
		    		//-----			first		-----
		    		first = E.substring(0, E.indexOf(findLast(E.substring(0, loc))));
		    		try {
		    			if(E.charAt(loc - 1) != ')')
		    			{
		    				//missing declared radical
		    				first = E.substring(0, loc);
		    			}
		    			//check for lagging ')'
		    			if(first.charAt(first.length() - 1) == '(')
			    		{
			    			first = first.substring(0, first.length() - 1);
			    		}			
		    		} catch (StringIndexOutOfBoundsException s01) {
		    			//there is nothing leading the loc
		    			first = "";
		    		}
		    		//-----			last		-----
		    		last = E.substring(loc + radical.length() - parAfter);
		    		try {
			    		if(last.charAt(0) == ')')
			    		{
			    			last = last.substring(1);
			    		}
		    		} catch (StringIndexOutOfBoundsException s01) {
		    			//there is no opertations after
		    			last = "";
		    		}
		    		
		    		//-----			Concats		-----
		    		if(!first.isEmpty())
		    		{
		    			//removes index '*' if first ends with an operand
		    			if(first.charAt(first.length()-1)!='0'&&first.charAt(first.length()-1)!='1'&&first.charAt(first.length()-1)!='2'
		    			 &&first.charAt(first.length()-1)!='3'&&first.charAt(first.length()-1)!='4'&&first.charAt(first.length()-1)!='5'
		    			 &&first.charAt(first.length()-1)!='6'&&first.charAt(first.length()-1)!='7'&&first.charAt(first.length()-1)!='8'
		    			 &&first.charAt(first.length()-1)!='9')
		    			{
		    				index = index.substring(1);
		    			}
		    		}
		    		
		    		E = first + index + "√" + radical + last;
		    		
		    		if(E.charAt(0) == '*')
		    		{
		    			E = E.substring(1);				//removes leading ' * ' if ' √ ' is the first operand
		    		}
		    		
		    		System.out.println("radical index   " + index);
		    		System.out.println("radical radican " + radical);
		    		
		    		System.out.println("------\n[-=" + E + "=-]\n------");
		    		
		    		x = E.indexOf(index + "√" + radical) + index.length() + radical.length() + 1;
    			}
    			
    			x++;
			}
    	}
    	return E;
    }
    private String findLast(String E)
    {
    	/*
    	 * Finds (...) set closest to E.length( )
    	 * Keeps (...) sets
    	 */
    	//used in correlation with radical and exponent
    	int x = E.length() - 1, open = 0, close = 0;
    	if(!E.contains("("))		//for no (...) sets to be processed
    	{
    		return E;
    	}
    	while(x < E.length())
    	{
    		if(E.charAt(x) == '(')
    			open++;
    		if(E.charAt(x) == ')')
    			close++;
    		if(open == close)
    		{
    			return E.substring(x);
    		}
    		x--;
    	}
    	return E;
    }
    private String findFirst(String E)
    {
    	/*
    	 * Finds (...) set closest to index ' 0 '
    	 * Keeps (...) sets
    	 */
    	//used in correlation with radical and exponent
    	
    	int x = 0, open = 0, close = 0;
    	while(x < E.length())
    	{
    		if(E.charAt(x) == '(')
    			open++;
    		if(E.charAt(x) == ')')
    			close++;
    		if(open == close)
    		{
    			if(!E.contains("("))
    				return E;
    			return E.substring(0, x + 1);
    		}
    		x++;
    	}
    	return E;
    }
    //==============================================    Math Operations		==============================================
    public String cycle() throws IOException
    {
    	/*
    	 * used to be called from mainActivity as equation is updated from the 'enter' button
    	 */
    	return cycle(this.equation);
    }
    public String cycle(String E) throws IOException
    {
    	System.out.println(">>>>     cycle input {" + E + "}     <<<<");
    	try {
    		// modifies the equation to make it make mathematical sense 
	    	E = whiteSpace();								//cleans up equation
	    	E = subNegation(E);								// determines if number is negative or being subtracted
	    	E = unParSet(E);								// closes all unclosed (...) sets (only adds to the end)
	    	
			if(rawEquationInput.contains("Ans"))
        	{
				/*
				 * Replaces the "Ans" word with the last recorded answer
				 */
				this.equation = equation.replaceAll("Ans", this.lastAnswer);
        	}
			
	    	this.rawEquationInput = E;						// saves (modified) original user input to be displayed with the answer

	    	//----------
	        // TODO :: possible to elimate this section as variables are now obslete
	    	if(E.toLowerCase().contains("sigma(") || E.contains("Σ("))	//allows methods with required variables to not be replaced
	    	{
	    		E = methods(E);
	    		E = E.substring(0, E.indexOf('=') + 1) + trim(E.substring(E.indexOf('=') + 1));
	    		
	    		return E;
	    	}
	        //-----
	        E = trig(E);  
	        
	        if(E.contains("log("))
	        	E = log(E.indexOf("log(") ,E);
	        //-----
	        
	        if( containsMethod(E) )
	        {
	        	/*
	        	 * compares the input to the list of methods to determine if operations need to be done  
	        	 */
	        	E = methods(E);
	        }
	        
	        if(!parMethod && !fromMethods && E.contains("("))
	        {
	        	E = parenthesis(E);
	        	
	        	System.out.println("!method (answ)   " + E);
	        	System.out.println("---  Parenthesis called  ---");
	        }
	        
	        E = specialMath(E);
	        
	        //end of method with (...) set conditional
	        if(E.contains("Error") || fromMethods == true || E.contains("=") || E.contains("Overflow"))		//done here to skip processes after and avoid error with trim()
	        {
	            return E;
	        }
	        //------------------------------------------		Operations		------------------------------------------
	        /*
	         * Input  : current operation determined by order of operations
	         * Output : the calculated value of the input as a non-trimmed value
	         */
	        for(int x = 0; x < E.length(); x++)        //finds all '√' in E and replaces
	       	{
	     		if(E.charAt(x) == '√')
	     		{
	     			E = radical(x, E);
	     			x = 0;
	     		}
	       	} 
	        for(int x = 0; x < E.length(); x++)        //finds all '^' in E and replaces
	       	{
	       		if(E.charAt(x) == '^')
	       		{
	       			int lastEx = x;
	       			
	       			while(E.charAt(getIndexAfter(lastEx, E)) == '^')
	       			{
	        			lastEx = getIndexAfter(lastEx, E);
	        		}               
	        		while(lastEx > 0 && E.charAt(lastEx) == '^')
	       			{
	       				E = exponet(lastEx, E);
	       				if(E.equals("Undefined"))
	           				return E;
	       				try {
	       					lastEx = getIndexBefore(lastEx, E);  //check if will return 0 when indexAt is the last key in string
	       				} catch (StringIndexOutOfBoundsException a) { 
	       					/*
	       			         * An error has occurred if this is reached.
	       			         * Built in debugger is called and a report will be sent
	       			         */
	       					break;
	       				}
	        		}
	               
	        	}
	       	}	
	       	for(int x = 0; x < E.length(); x++)        //finds all '*' && '/' in E and replaces
	       	{
	       		if(E.charAt(x) == '*'){
	       			E = multiply(x , E);
	       			x = 0;
	       		}
	       		if(E.charAt(x) == '/'){
	       			E = divide(x , E);
	       			if(E.equals("Undefined"))
	       				return E;
	       			x = 0;
	       		}
	       	}   
	        for(int x = 0; x < E.length(); x++)         //finds all '+' && '-' in E and replaces
	       	{
	    		if(E.charAt(x) == '+' && E.charAt(x - 1) != 'E')
	    		{
	    			E = add(x , E);
		       		x = 0;
		       	}
	       		if(E.charAt(x) == '—' && E.charAt(x - 1) != 'E'){
	       			E = subtract(x , E);
	       			x = 0;
	       		}
	       	}
	        
	        //----------------------------------------------------------------------------------------------------------------
	        if(E.contains("Error") || fromMethods == true || E.contains("=") || E.contains("Undefined") || E.contains("Overflow"))
	        	//Separate check and prints for methods() for trim(E) to call
	        {
	            return E;
	        }else{
	        	if(!parMethod)
	        	{
	            	System.out.println("SDF SDF " + E);
	        		E = trim(E);
	        	}
	    	}
	        
	        this.lastAnswer = E;					// saves the found value
	        
			E = this.rawEquationInput + " = " + E;  // creates String to be returned as user modified user input
       		
    	} catch (Exception e) {
    		return "Error";
		}

        return E;
    }
    /*
     * cycle is called from the enter button within the main activity and sends the text from the display
     * 
     * Input  : String E; raw String from text display 
     * Process: Raw input is modified to make it readable by removing white space, and closing open (...) blocks then saved as @rawEquationInput
     * 					Order of Operations
     * 			Trig
     * 			Log
     * 			ContiansMethod check and performed
     * 			Parenthesis
     * 			Special math symbols calculated
     * 			Error/ finished equation check
     * 			Mathematical operations
     * 			Final error/ finished equation check
     * 			lastAnswer is saved as whole value
     * 			equation is returned as a trimmed "<rawEquationInput> \n __" with equation on scroll line
     * 			
     * Output : trimmed String value for 6 decimal places that is displayed on the display 
     */
    private String specialMath(String E)							   //for non-conventional math handlers (WIP)
    {
    	if(E.contains("!"))
    	{
    		BigDecimal ans = new BigDecimal("1");
    		String first = "", last = "";
    		int at = 0;
    		while(E.contains("!"))
    		{
    			if(E.charAt(at) == '!')
    			{
    				//syntax check
		    		if(E.charAt(at-1) == '0' || E.charAt(at-1) == '1' || E.charAt(at-1) == '2' || 
		    		   E.charAt(at-1) == '3' || E.charAt(at-1) == '4' || E.charAt(at-1) == '5' || 
		    		   E.charAt(at-1) == '6' || E.charAt(at-1) == '7' || E.charAt(at-1) == '8' || 
		    		   E.charAt(at-1) == '9')
		    		{
		    			try {
			        		BigDecimal fac = new BigDecimal(getBeforeString(E.indexOf("!"), E));
			    			
			    			while(fac.intValue() > 0)
			    			{
			    				ans = ans.multiply(fac);
			    				fac = fac.subtract(new BigDecimal("1"));
			    			}
			    			
			    			last  = E.substring(at + 1);
			    			if(getIndexBefore(at, E) != 0)
			    			{
			    				first = E.substring(0, getIndexBefore(at, E) + 1);
			    			} else {
			    				first = E.substring(0, getIndexBefore(at, E));
			    			}
			    			
			    			System.out.println("First!      " + first);
			    			System.out.println("Answ!       " + ans);
			    			System.out.println("Last!       " + last + "\n");
			    			
			    			E = first + ans + last;
			    			
			    			
			    			at = 0;
		    			} catch (NumberFormatException n01) {
		    				E = "Error";
		    			}
		    		}else{
		    			E = "Error";
		    		}
    			}
	    		at++;
    		}
    	}
    	if(E.contains("%"))
    	{
    		double pcent = 0;
    		for(int x = 0; x < E.length(); x ++)
    		{
    			if(E.charAt(x) == '%')
    			{
    				//TODO add to catalog
    				
    				pcent = getBefore(x, E);
    				E = E.substring(0, getIndexBefore(x, E)) + "®" + E.substring(x + 1);
    				
    				System.out.println("%E         " + E);
    				System.out.println("%value     " + pcent);
    				
    				pcent = pcent / 100;
    				
    				E = E.replace("®", Double.toString(pcent));
    				
					System.out.println("%answ     " + pcent);
    			}
    		}
    	}
    	if(E.contains("±"))
    	{
    		System.out.println(">>>     ± input {" + E + "}     <<<");
    		
    		String plus = E.replace('±', '+'), min = E.replace('±', '—');
    		try {
				min  = cycle(min);
	    		plus = cycle(plus);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		E = "(" + min + ", " + plus + ")";
    		
    		System.out.println("Plus+-      " + plus);
    		System.out.println("Min+-       " + min);
    		System.out.println("Fin+-       " + E );
    		
    		fromMethods = true;
    	}
    	return E;
    }
    private String log(int at, String E)
    {//syntax log(base, resulting product)
    	double base = 0, resul = 0;
        String[] parts = new String[2];
    	E = E.substring(4,E.length()-1);		//removes "log("  &&  ")"

        parts = E.split(",");
        
        base = Double.parseDouble(parts[0]);
        resul = Double.parseDouble(parts[1]);
        
        E = Double.toString(Math.log(resul) / Math.log(base));
        
        System.out.println("baseLog      " + base);
        System.out.println("resulLog     " + resul);
        System.out.println("ELog         " + E + System.lineSeparator());
        
        return E;
    }
    private String radical(int at, String E)
    {//√(...) radical syntax

		/*	SYNTAX
		 * power√base	( (...) can exist any where around base and power)
		 * 				( ' √ ' treats first number after as the root )
		 */
    	if(at == E.length() - 1)
    	{
    		return "Syntax Error";
    	}
		if(!radicalPar)				//If has not been altered to fit syntax yet
			E = radicalPar(E);
		
     	while(at < E.length())		//Relocates '√' after syntax creation
     	{
     		if(E.charAt(at) == '√')
     		{
     			break;
     		}
     		at++;
       	} 
     	//-----
    	String first = "", last = "", answ = "", index = "", rad = "";
    	
    	index = findLast(E.substring(0, at));
    	index = index.substring(1, index.length() - 1);
    	
    	rad = findFirst(E.substring(at + 1));
    	rad = rad.substring(1, rad.length() - 1);
    	
    	/*
    	 * ( Radical ) operations are done in parenthesis method prior to entry
    	 */
    	
    	first = E.substring(0, at - (index.length() + 3));
    	try { 
    		last = E.substring(at + (rad.length() + 4));			//"+ 4" is to exclude inclusive value of substring
    	} catch (StringIndexOutOfBoundsException a01) {
    		last = "";
    	}

   		answ = Double.toString(Math.pow(Double.parseDouble(rad), 1 /Double.parseDouble(index)));
 
    	
    	System.out.println("√First     " + first);
        System.out.println("√Answer    " + answ);
        System.out.println("√Last      " + last + "\n");
        
        E = first + answ + last;

        return E;
    }
    private String exponet(int at, String E) 
    {             
        if(at == E.length() - 1)
        {
            return "Syntax Error";
        }
        String answ = "1", first = "", last = "", w1 = "", w2 = "";
        
        /*
         * w1 = base
         * w2 = power
         */
        
        w1 = getBefore(at, E) + "";
        w2 = getAfter(at, E) + "";        
        
        if(getBefore(at, E) == 0 && getAfter(at, E) == 0)
        {
        	return "Undefined";
        }
        //----------
        
        if(w2.contains(".") && (w2.indexOf('.') == w2.length() - 2 && w2.charAt(w2.length() - 1) == '0'))
        {
        	/*
        	 * When w2 is a integer
        	 */
        	answ = new BigDecimal(w1).pow((int)getAfter(at, E)).stripTrailingZeros().toPlainString();
        	
        } else {
        	/*
        	 * When w2 is a decimal
        	 * 
        	 * w2 is converted to a fraction
        	 * 
        	 * The numerator is the index of the radical as an integer
        	 * The denominator is the power of the base of the radican 
        	 * 
        	 *  https://math.stackexchange.com/questions/21381/how-to-calculate-a-decimal-power-of-a-number
    		 *	x^a/b=( (b)√(x^(a)) )
        	 */
        	try {
	        	Fraction f = new Fraction(getAfter(at, E));
	        	BigDecimal base = new BigDecimal(w1);
	        	
	        	int power = f.getNumerator();
	        	double index = f.getDenominator();
	        	
        		double radican = base.pow(power).stripTrailingZeros().doubleValue();
        		answ = Math.pow(radican, 1 / index) + "";
        		
        	} catch (NumberFormatException | FractionConversionException f01) {
        		return "Overflow";
        	}
        }
        
        //----------
	    if(getIndexBefore(at, E) != 0)                                //checks for if 'at' is at the beginning
            first = E.substring(0 , getIndexBefore(at, E) + 1);
        if(getIndexBefore(at, E) == 0)
        	first = "";
            
        if(getIndexAfter(at,E) == E.length() - 1)			//If operation is at the end
            last = "";    
        else 
            last = E.substring(getIndexAfter(at,E)); 
        
        System.out.println("^First     " + first);
        System.out.println("^Answer    " + answ);
        System.out.println("^Last      " + last);
        
        E = first + answ + last;
        
        return E;
    }
    private String multiply(int at, String E) 
    {                     
        if(E.charAt(0) == '*' || at == E.length() - 1)
        {    
            return "Syntax Error";
        }
        
        String first = "", last = "", answ = "", one = "", two = "";
        
        one = getBeforeString(at, E);
        two = getAfterString(at, E);
        
        if(!one.contains("."))
        {
        	one = one + ".0";
        }
        if(!two.contains("."))
        {
        	two = two + ".0";
        }
        
		BigDecimal val1 = new BigDecimal(one);
		BigDecimal val2 = new BigDecimal(two);
		
		val1 = val1.multiply(val2).stripTrailingZeros();
		
        //----------
        if(getIndexBefore(at, E) != 0)                      //If operation is at the beginning
            first = E.substring(0 , getIndexBefore(at, E) + 1);
        else 
            first = "";
        
        if(getIndexAfter(at,E) == E.length() - 1)			//If operation is at the end
            last = "";    
        else 
            last = E.substring(getIndexAfter(at,E));    
        
        System.out.println("*First     " + first);
        System.out.println("*Answ      " + val1.toPlainString());
        System.out.println("*Last      " + last);
        
        E = first + val1.toPlainString() + last;

        return E;
    }
    private String divide(int at, String E) 
    {
        if(E.charAt(0) == '/' || at == E.length() - 1)
        {
            return "Syntax Error";
        }                                                             
        
        String first = "", last = "", answ = "";
		BigDecimal val1 = new BigDecimal(getBeforeString(at, E));
		BigDecimal val2 = new BigDecimal(getAfterString(at, E));

		try {
			val1 = val1.divide(val2, 20, RoundingMode.HALF_UP);
		} catch (ArithmeticException a) {
			if(getAfter(at, E) == 0)
			{
				System.out.println("Can not divide by '0'");
				return "Undefined";
			}
			if(a.toString().contains("Non-terminating"))
			{
				System.out.println("Non-terminating");
				//TODO Test Meth.IEEEremainder(x, y) 
				val1 = new BigDecimal(Math.IEEEremainder(val1.doubleValue(), val2.doubleValue()));
				/*
			     * An error has occurred if this is reached.
			     * Built in debugger is called and a report will be sent
			     * This is sending to help us collect data on these events
			     */
			}
		}
        answ = val1.toString();
		
        //----------
        if(getIndexBefore(at, E) != 0)                      //If operation is at the beginning
            first = E.substring(0 , getIndexBefore(at, E) + 1);
        else 
            first = "";
        
        if(getIndexAfter(at,E) == E.length() - 1)			//If operation is at the end
            last = "";    
        else 
            last = E.substring(getIndexAfter(at,E));    
        
        System.out.println("/First     " + first);
        System.out.println("/Answ      " + answ);
        System.out.println("/Last      " + last);
        
        E = first + answ + last;

        return E;
    }
    private String add(int at, String E) 
    {
    	if(E.charAt(0) == '+' || at == E.length() - 1)
    	{
            return "Syntax Error";
        }
    	
        String first = "", last = "", answ = "";
		BigDecimal val1 = new BigDecimal(getBeforeString(at, E));
		BigDecimal val2 = new BigDecimal(getAfterString(at, E));
        
        val1 = val1.add(val2);
        answ = val1.toString();
        
        //----------
        if(getIndexBefore(at, E) != 0)                      //If operation is at the beginning
            first = E.substring(0 , getIndexBefore(at, E) + 1);
        else 
            first = "";
        
        if(getIndexAfter(at,E) == E.length() - 1)			//If operation is at the end
            last = "";    
        else 
            last = E.substring(getIndexAfter(at,E));    
        
        System.out.println("+First     " + first);
        System.out.println("+Answ      " + answ);
        System.out.println("+Last      " + last);
        
        E = first + answ + last;
        
        return E;
    }
    private String subtract(int at, String E) 
    {
        //Detection char is ' — ' 
    	if(E.charAt(0) == '—' || at == E.length() - 1){
            return "Syntax Error";
        }
    	
    	String first = "", last = "", answ = "";
 		BigDecimal val1 = new BigDecimal(getBeforeString(at, E));
 		BigDecimal val2 = new BigDecimal(getAfterString(at, E));
         
         val1 = val1.subtract(val2);
         answ = val1.toString();
         
         //----------
         if(getIndexBefore(at, E) != 0)                      //If operation is at the beginning
             first = E.substring(0 , getIndexBefore(at, E) + 1);
         else 
             first = "";
         
         if(getIndexAfter(at,E) == E.length() - 1)			//If operation is at the end
             last = "";    
         else 
             last = E.substring(getIndexAfter(at,E));    
         
        System.out.println("—First     " + first);
        System.out.println("—Answ      " + answ);
        System.out.println("—Last      " + last);
        
        E = first + answ + last;
    
        return E;
    }
    private String trig(String E)
    {
        System.out.println("trig input : " + E);
    	//TODO :: Create Buttons sin cos tan && inverse print out sin(...) cos(...) tan(...) && inverse========
    	//TODO :: test
        int count = 0;
        while(count < E.length()-1)
        {
            String num = "", first = "", last = "", answ = "";
            double Dn = 0.0;
            int index = 0;


            if(E.contains("sin(") == true)
            {
                index = E.indexOf("sin(");
                num = E.substring(index+4, E.indexOf( ')', index));    
                Dn = Double.parseDouble(num);
                
                answ = Double.toString(Math.sin(Math.toRadians(Dn)));                        
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "sin(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }
        


            if(E.contains("cos(") == true)
            {
                index = E.indexOf("cos(");        
                num = E.substring(index+4, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(Math.cos(Math.toRadians(Dn)));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "cos(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }    


            if(E.contains("tan(") == true)
            {
                index = E.indexOf("tan(");        
                num = E.substring(index+4, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);
                
                answ = Double.toString(Math.tan(Math.toRadians(Dn)));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "tan(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }
            if(E.contains("sec("))
            {
            	index = E.indexOf("sec(");        
                num = E.substring(index+4, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(1.0 / (Math.cos(Math.toRadians(Dn))));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "sec(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }
            if(E.contains("csc("))
            {
                index = E.indexOf("csc(");
                num = E.substring(index+4, E.indexOf( ')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(1.0 / (Math.sin(Math.toRadians(Dn))));                        
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "csc(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }
            if(E.contains("cot("))
            {
                index = E.indexOf("cot(");        
                num = E.substring(index+4, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);
                
                answ = Double.toString(1.0 / (Math.tan(Math.toRadians(Dn))));           
                
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "cot(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }

            //==========			Inverse operations			==========

            if(E.contains("sin-1(") == true)
            {    
                index = E.indexOf("sin-1(");        
                num = E.substring(index+6, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(Math.asin(Math.toRadians(Dn)));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "sin-1(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }


            if(E.contains("cos-1(") == true)
            {
                index = E.indexOf("cos-1(");        
                num = E.substring(index+6, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(Math.acos(Math.toRadians(Dn)));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "cos-1(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }    


            if(E.contains("tan-1(") == true)
            {
                index = E.indexOf("tan-1(");        
                num = E.substring(index+6, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(Math.atan(Math.toRadians(Dn)));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "tan-1(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }
            
            if(E.contains("sec-1("))
            {
            	index = E.indexOf("sec(");        
                num = E.substring(index+6, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);

                answ = Double.toString(Math.cos(Math.toRadians(1.0 / Dn)));            //check if double can be parsed as a String
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                
                last = "sec-1(" + num + ")";
                
                index = E.indexOf(last) + last.length();
                
                last = E.substring(index);
                
                E = first + answ + last;
            }
            /*
             * 
             * TODO inverse of SCS COS & COT functions
             * 
            if(E.contains("csc-1("))
            {
                index = E.indexOf("csc(");
                num = E.substring(index+6, E.indexOf( ')', index));    

                answ = Double.toString(Math.sin(Math.toRadians(1.0 / Dn)));                        
        
                if(getIndexBefore(index, E) > 0)                                //checks for if E is at the beginning
                    first = E.substring(0, (getIndexBefore(index, E) + 1));
                if(getIndexBefore(index, E) == 0)
                    first = "";
                if(last.isEmpty() == true)                                 //set to true : to check before populating to be able to skip over lines (current + 11 && current + 12)
                {
                    for(int i = 0; i <E.length(); i++)
                    {
                        if(E.charAt(i) == '*' || E.charAt(i) == '/' || E.charAt(i) == '+' || E.charAt(i) == '—')
                        {
                            last = E.substring(getIndexAfter(index,E) + 1);
                            break;
                        }    


                    }
                }
                if(last.isEmpty() == false)
                    last = E.substring(getIndexAfter(index,E));
                E = first + answ + last;
            }
            if(E.contains("cot-1("))
            {
                index = E.indexOf("cot-1(");        
                num = E.substring(index+6, E.indexOf(')', index));    
                Dn = Double.parseDouble(num);
                
                answ = Double.toString(Math.atan(1 / Math.toRadians(Dn)));           
                
                if(getIndexBefore(index, E) != 0)                                //checks for if E is at the beginning
                    first = E.substring(0 , getIndexBefore(index, E) + 1);
                if(getIndexBefore(index, E) == 0)
                    first = "";
        
                   if(last.isEmpty() == true)                           //set to true : to check before populating to be able to skip over lines (current + 11 && current + 12)
                {
                    for(int i = 0; i <E.length(); i++)
                    {
                        if(E.charAt(i) == '*' || E.charAt(i) == '/' || E.charAt(i) == '+' || E.charAt(i) == '—')
                        {
                            last = E.substring(getIndexAfter(index,E) + 1);
                            break;
                        }    


                    }
                }
                if(last.isEmpty() == false)
                    last = E.substring(getIndexAfter(index,E));
                E = first + answ + last;
            }
            */
        count++;
        }
    return E;
    }
    //==============================================    Solution methods          ==============================================
    private String methods(String E)			//make sure to add to GUI array with description and search words
    {
    	String origional = E;				//Declared here to preserve case sensitivity
    	if(!E.contains("Σ"))					//done as ' Σ ' has lower case : add all of special char notations
    		E = E.toLowerCase();
    //==============================================    Statistics  methods          ==============================================
    	if(E.contains("invnorm("))
    	{fromMethods = true;
    		E = E.substring(8, E.length() - 1);
    		System.out.println(E);
    		String[] val = new String[3];
    		double point = 0;
    		val = E.split(",");
    		/* val[0] = mean
    		 * val[1] = standard deviation
    		 * val[2] = area 
    		 */
    		try{
    			if(val.length == 3)
    			{
    				NormalDistribution nd = new NormalDistribution(Double.parseDouble(val[0]), Double.parseDouble(val[1]));
    				E = origional + " = " + nd.inverseCumulativeProbability(Double.parseDouble(val[2]));
    			}
    			if(val.length == 1)
    			{
    				NormalDistribution snd = new NormalDistribution(0, 1);
    				E = origional + " = " + snd.inverseCumulativeProbability(Double.parseDouble(val[0]));
    			}
    		} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
    			E = "Error";
    		}
    	}
    	if(E.contains("normcdf("))
    	{fromMethods = true;
    		E = E.substring(8, E.length() - 1);
    		String[] val = new String[4];
    		double density = 0;
    		val = E.split(",");
    		/* val[0] = mean
    		 * val[1] = standard deviation
    		 * val[2] = min
    		 * val[3] = max
    		 */
    		try{
    			NormalDistribution nd = new NormalDistribution(Double.parseDouble(val[0]), Double.parseDouble(val[1]));
    			if(val.length == 2 || val.length == 1)
    			{
        			NormalDistribution snd = new NormalDistribution(0, 1);
        			if(val.length == 2)
        			    density = snd.cumulativeProbability(Double.parseDouble(val[0])) - snd.cumulativeProbability(Double.parseDouble(val[1]));
        			if(val.length == 1)
        				density = snd.cumulativeProbability(Double.parseDouble(val[0]));
    			}
    			if(val.length == 3)
    			{
    				density = nd.cumulativeProbability(Double.parseDouble(val[2]));
    				E = Double.toString(density);
    				E = trim(E);
    				E = origional + " = " + E;
    			}
    			if(val.length == 4)
    			{
    				density = nd.cumulativeProbability(Double.parseDouble(val[3])) - nd.cumulativeProbability(Double.parseDouble(val[2]));
    				E = Double.toString(density);
    				E = trim(E);
    				E = origional + " = " + E;
    			}
    		} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
    			System.out.println("Input Error");
    			E = "Error";
    		}
    	}
    	if(E.contains("normpdf("))
    	{fromMethods = true;
    		E = E.substring(8, E.length() - 1);
    		String[] val = new String[3];
    		double density = 0;
    		val = E.split(",");
    		/* val[0] = mean
    		 * val[1] = standard deviation
    		 * val[2] = point ' x '
    		 */
    		try{
    			NormalDistribution nd = new NormalDistribution(Double.parseDouble(val[0]), Double.parseDouble(val[1]));
    			density = nd.density(Double.parseDouble(val[2]));
    			E = Double.toString(density);
    			E = trim(E);
    			E = origional + " = " + E;
    		} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
    			NormalDistribution nd = new NormalDistribution();
    			density = nd.density(Double.parseDouble(val[0]));
    			E = Double.toString(density);
    			E = trim(E);
    			E = origional + " = " + E;
    		}
    	}
    	if(E.contains("rndnorm("))
    	{fromMethods = true;
    		E = E.substring(8, E.length() - 1);
    		Double[] val  = new Double[3];
    		String[] hold = new String[3];
    		hold = E.split(",");
    		
    		for(int x = 0; x < 3; x++)
    		{
    			try{
    				val[x] = Double.parseDouble(hold[x]);
    			}
    			catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
    				break;
    			}
    			
    		}
    		
			Random r = new Random();
   			String t = "{";
			double a = 0;
			int count= 0;

    		try {
    			try{
    	    		/* val[0] = mean
    	    		 * val[1] = standard deviation
    	    		 * val[2] = trials
    	    		 */ 
	    			//Both user set MEAN & STANDARD DEVATION with N TRIALs
	    			t = hold[2];
	    			t = "{";
	    			
	    			while(count < val[2])
	    			{
	    				a = r.nextGaussian() * val[1]+ val[0];
	    				a        = Double.parseDouble(trim(Double.toString(a)));
	    				t        = t + Double.toString(a) + ", ";
	    				count++;
	    			}
	    			
	    			E = origional = " = " + t.substring(0, t.length() - 2) + "}";
	    		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
	    			/*
	    			 * val[0] = mean
    	    		 * val[1] = standard deviation
    	    		 */
	    			//user user set MEAN & STANDARD DEVATION with 1 trial
	    			t = hold[1];
	    			t = "{";
	    			
    				a = r.nextGaussian() * val[1]+ val[0];
    				a = Double.parseDouble(trim(Double.toString(a)));
    				t = t + Double.toString(a) + ", ";
	    			
	    			E = origional = " = " + t.substring(0, t.length() - 2) + "}";
	    		}
	    			
    		} catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {
    			/*
    			 * val[0] = number of trials
    			 */
    			//NO user set mean & standard deviation    		
    			while(count < val[0])
    			{
    				a = r.nextGaussian();
    				a = Double.parseDouble(trim(Double.toString(a)));
    				t = t + Double.toString(a) + ", ";
    				count++;
    			}
    			
    			E = origional = " = " + t.substring(0, t.length() - 2) + "}";
    			
    		}
    	}
    	if(E.contains("rndbinomial("))
    	{fromMethods = true;
    		E = E.substring(12, E.length() - 1);
       		String[] val = new String[3];
    		val = E.split(",");
    		/* val[0] = n
    		 * val[1] = %
    		 * val[2] = number of trials
    		 */
    		
    		    		
   		}
    	if(E.contains("rndnum("))
    	{fromMethods = true;
    		E = E.substring(7, E.length() - 1);
    		try{
    			int trials = Integer.parseInt(E);
    			E = origional + " = {";  
    			while(trials > 0)
    			{
    				E = E + trim(Double.toString(Math.random() * 100)) + ", ";
    				trials--;
    			}
    			E = E.substring(0, E.length() - 2) + "}";
    		} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
				E = "";
				E = origional + " = " + trim(Double.toString(Math.random() * 100));
			}
    	}
    	if(E.contains("rndint("))		
    	{fromMethods = true;
    		E = E.substring(7, E.length() -1);
    		String[] val = new String[3];
    		val = E.split(",");
    		int lower = 0, upper = 10, trials = 1;
    		double rnd = 0;
    		
    		try {
    			lower  = Integer.parseInt(val[0]);
    			upper  = Integer.parseInt(val[1]);
    			trials = Integer.parseInt(val[2]);
				Random r = new Random();
				E      = origional + " = {";
    			while(trials > 0)
    			{
    				rnd = r.nextInt((upper - lower) + 1) + lower;
    				E   = E + rnd + ", ";
    				trials--;
    			}
    			E = E.substring(0, E.length() - 2) + "}";
			} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
				E = "";
				E = origional + " = " + trim(Double.toString(Math.random() * 100));
			}    		
    	}
    	if(E.contains("rndseed("))
    	{fromMethods = true;
    		/* Generates a random number given the seed
    		 * If no seed input will process as a random number being called
    		 */
    		try{
    			Random r = new Random(Long.parseLong(E = E.substring(8, E.length() -1)));
    			E        = origional + " = " + trim(Double.toString(r.nextDouble()));
    		} catch (NumberFormatException e) {
    			//' Random r = new Random(16005); ' is " .420 "
				E = origional + " = " + trim(Double.toString(Math.random() * 100));
    		}
    	}
    //==============================================    Geometry  methods          ==============================================
    // http://mdk12.msde.maryland.gov/instruction/curriculum/hsa/geometry/math_reference_sheet.html
    	if(E.contains("triangle("))				//to be called form windowed methods (only finds area)
    	{fromMethods = true;					//syntax = (base, height)
    		char negate = E.charAt(0);
    		
    		E = E.substring(E.indexOf("triangle(") + 9, E.length()-1);
    		System.out.println(E);
            String[] cord = new String[2];
            cord = E.split(",");
            
            try{
            	E = Double.toString(.5 * (Double.parseDouble(cord[0]) * Double.parseDouble(cord[1])));
            	if(negate == '-')
            		E = negate + E;
            	E = trim(E);
            	E = origional + " = " + E;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
            	E = "Error";
            }
    	}
    	//---------		4 - Sided objects
      	if(E.contains("rectangle(") || E.contains("parallelogram("))		    //to be called form windowed methods (only finds area)
    	{fromMethods = true;					//syntax = (base, height)
    		if(E.contains("rectangle("))
    			E = E.substring(10, E.length()-1);
    		if(E.contains("parallelogram("))
    			E = E.substring(14, E.length()-1);
            String[] cord = new String[2];
            cord = E.split(",");

            try{
	    		E = Double.toString(Double.parseDouble(cord[0]) * Double.parseDouble(cord[1]));
	    		E = trim(E);
	    		E = origional + " = " + E;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
            	E = "Error";
            }
    	}
      	if(E.contains("rectangleVolume(") || E.contains("parallelogramvolume("))
      	{fromMethods = true;
      		if(E.contains("parallelogramvolume("))
      		{
      			E = E.substring(20, E.length() - 1);
      		} else {
      			E = E.substring(16, E.length() - 1);
      		}
      		String[] dem = E.split(",");
      		
      		try{
      			E = Double.toString(Double.parseDouble(dem[0]) * Double.parseDouble(dem[1]) * Double.parseDouble(dem[2]));
      			E = trim(E);
      			E = origional + "=" + E;
      		} catch (NumberFormatException | ArrayIndexOutOfBoundsException a) {
      			E = "Error";
      		}
      	}
      	if(E.contains("square("))
      	{fromMethods = true;
      		E = E.substring(7, E.length()-1);
      		try{
      			E = Double.toString(Double.parseDouble(E) * Double.parseDouble(E));
      			E = trim(E);
      			E = origional + " = " + E;
      		} catch(NumberFormatException a) {
      			E = "Error";
      		}
      	}
      	if(E.contains("cubevolume("))
      	{fromMethods = true;
      		E = E.substring(11, E.length() - 1);
      		System.out.println("sdf <" + E +">");
     		try{
     			E = Double.toString(Math.pow(Double.parseDouble(E), 3));
      			E = trim(E);
      			E = origional + " = " + E;
      		} catch(NumberFormatException a) {
      			E = "Error";
      		}
      	}
      	if(E.contains("trapazoid("))		    
    	{fromMethods = true;					//syntax = (base1, base2, height )
    		E = E.substring(10, E.length()-1);
            String[] cord = new String[1];
            cord = E.split(",");
	            try{
	    		E = Double.toString((.5 * (Double.parseDouble(cord[0]) + Double.parseDouble(cord[1]))) * Double.parseDouble(cord[2]));
	    		E = trim(E);
	    		E = origional + " = " + E;
	      	} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
    	}
      	//---------		Circles
      	if(E.contains("circle("))		    
    	{fromMethods = true;					//syntax = (radius)
    		E = E.substring(7, E.length()-1);
            try{
	    		E = Double.toString(Math.PI * (Double.parseDouble(E) * Double.parseDouble(E)));
	    		E = trim(E);
	    		E = origional + " = " + E;			//lastAnswer = E :  !SA
            } catch(NumberFormatException a) {
	      		E = "Error";
	      	}
    	}
      	if(E.contains("circlec("))				//syntax = (radius)
      	{fromMethods = true;
      		E = E.substring(8, E.length()-1);
      		try{
	      		E = Double.toString(2*Math.PI*Double.parseDouble(E));
	      		E = trim(E);
	      		E = origional + " = " + E;
      		} catch(NumberFormatException a) {
	      		E = "Error";
	      	}
      	}	
      	if(E.contains("sephere("))				//syntax = (radius)
      	{fromMethods = true;
      		E = E.substring(8, E.length() - 1);
      		try{
	      		E = Double.toString(4 * Math.PI * Double.parseDouble(E) * Double.parseDouble(E));
	      		E = trim(E);
	      		E = origional + " = " + E;
      		} catch(NumberFormatException a) {
	      		E = "Error";
	      	}
      	}
    	if(E.contains("sepherev("))				//syntax = (radius) 	
      	{fromMethods = true;
      		E = E.substring(9, E.length() - 1);
      		double radius = Double.parseDouble(E);
      		try{
	      		E = Double.toString(4/3 * Math.PI * Math.pow(radius, 3));
	      		E = trim(E);
	      		E = origional + " = " + E;
      		} catch(NumberFormatException a) {
	      		E = "Error";
	      	}
      	}
    	if(E.contains("sepheresa("))			//syntax = (radius)
    	{fromMethods = true;
    		E = E.substring(10, E.length() - 1);
    		double radius = Double.parseDouble(E);
    		try{
    			E = Double.toString(4 * Math.PI * radius * radius);
    			E = trim(E);
    			E = origional + "=" + E;
    		} catch (NumberFormatException a) {
    			E = "Error";
    		}
    	}
      	//----------  Cylinders
      	if(E.contains("cylindersa("))			//synatx = (radius, height)
      	{fromMethods = true;
      		E = E.substring(11, E.length() - 1);	
      		String[] dem = E.split(",");
      		try{
      			E = Double.toString(2 * Math.PI * Double.parseDouble(dem[0]) * Double.parseDouble(dem[1]) + (2 * Math.PI * Math.pow(Double.parseDouble(dem[0]), 2)));
      			E = trim(E);
    			E = origional + "=" + E;
      		} catch (NumberFormatException a) {
      			E = "Error";
      		}
      	}
      	if(E.contains("cylindersas("))			//synatx = (radius, height)
      	{fromMethods = true;	
      		E = E.substring(12, E.length() - 1);	
      		String[] dem = E.split(",");
      		try{
      			E = Double.toString(2 * Math.PI * Double.parseDouble(dem[0]) * Double.parseDouble(dem[1]));
      			E = trim(E);
    			E = origional + "=" + E;
      		} catch (NumberFormatException a) {
      			E = "Error";
      		}
      	}
      	if(E.contains("cylindervolume("))		//syntax = (radius, height)
      	{fromMethods = true;
      		E = E.substring(15, E.length() - 1);
      		String[] dem = E.split(",");
      		
      		try{
      			E = Double.toString(Math.PI * Double.parseDouble(dem[0]) * Double.parseDouble(dem[0]) * Double.parseDouble(dem[1]));
      			E = trim(E);
    			E = origional + "=" + E;
      		} catch (NumberFormatException a) {
      			E = "Error";
      		}
      	}
      	//----------	Square Pyramids
      	if(E.contains("squarepyramidV(")) 		//syntax = (base length, height)
      	{fromMethods = true;
      		E = E.substring(15, E.length() - 1);
      		String[] dem = E.split(",");
      		
      		try{
      			E = Double.toString(Double.parseDouble(dem[0]) / 3 * Double.parseDouble(dem[1]) * Double.parseDouble(dem[1]));
      			E = trim(E);
    			E = origional + "=" + E;
      		} catch (NumberFormatException a) {
      			E = "Error";
      		}
      	}
      	if(E.contains("squarepyramidsa("))		//syntax = (base length, height)
      	{fromMethods = true;
      		E = E.substring(16, E.length() - 1);
      		String[] dem = E.split(",");
      		
      		try{
      			E = Double.toString(Math.pow(Double.parseDouble(dem[0]), 2) + 2 * Double.parseDouble(dem[0]) * Math.pow((Math.pow(Double.parseDouble(dem[0]), 2) / 4) + Math.pow(Double.parseDouble(dem[1]), 2), .5));
      			E = trim(E);
    			E = origional + "=" + E;
      		} catch (NumberFormatException a) {
      			E = "Error";
      		}
      	}
    //==============================================    Algebra  methods          ==============================================
      	//http://tutorial.math.lamar.edu/pdf/Algebra_Cheat_Sheet.pdf
      	//http://www.regentsprep.org/regents/math/algtrig/formulasheetalgebra2trig.pdf
        if(E.contains("quadratic("))									//Quadratic equation only	
        {fromMethods = true;
            double a = 0, b = 0, c = 0;
            String first = "", last = "";
            E = E.substring(10, E.length()-1);                            //Removes "PolySolve(" && ")"
            try{
	            if(E.contains("x^2"))
	            {
	                int at = E.indexOf("x^2");
	                if(at == 0)
	                    a = 1;
	                if(at != 0)
	                    a = Double.parseDouble(E.substring(0, at));
	                if(E.charAt(0) == '—')
	                	a = Double.parseDouble("-" + a);
	            }
	            if(E.contains("x") && E.contains("x^2"))
	            {
	                int at = E.indexOf("x", E.indexOf("x^2")+2);    //at = index of x
	                b = getBefore(at, E);
	                if(E.charAt(at-2) == '—')
	                	b = Double.parseDouble("-" + b);
	            }
	            if(E.charAt(E.length()-1) != 'x')
	            {
	                int at = getIndexBefore(E.length()-1, E);
	                c = getAfter(at, E);
	                if(E.charAt(at-2) == '—')
	                	c = Double.parseDouble("-" + c);
	            }
	            if(b * b - 4 * a * c < 0)
	            {
	                E = "Error: Does not cross the x-axis";
	            }
	            
	            if(b * b - 4 * a * c >= 0)
	            {
	            	            	
	                first = Double.toString(  -1 * ((-b + Math.sqrt(b * b - 4 * a * c)) / (2*a))   );
	                last =  Double.toString(  -1 * ((-b - Math.sqrt(b * b - 4 * a * c))/ (2 * a))  );
	                
	                first = trim(first);
	                last  = trim(last);
	                                
	                if(first.charAt(0) != '-')
	                    first = "+ " + first;
	                if(first.charAt(0) == '-')
	                    first = "— " + first.substring(1);
	                if(last.charAt(0) != '-')
	                    last = "+ " + last;
	                if(first.charAt(0) == '-')
	                    last = "— " + last.substring(1);
	                
	                first = trim(first);
	                last  = trim(last);
	                
	                E = origional + " = (x " + first + ") (x " + last + ")";
	            }
            } catch(NumberFormatException | StringIndexOutOfBoundsException a1) {
	      		E = "Error";
	      	}
        }
        if(E.contains("distance("))						        //syntax = (x1,y1,x2,y2)
        {fromMethods = true;
            String[] cord = new String[4];
            String d = "";
            double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
            
            E = E.substring(9,E.length()-1);
            cord = E.split(",");
            try{
	            x1 = Double.parseDouble(cord[0]);
	            y1 = Double.parseDouble(cord[1]);
	            x2 = Double.parseDouble(cord[2]);
	            y2 = Double.parseDouble(cord[3]);
	            
	            d = trim(Double.toString(Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2))));
	          
	            E = origional + " = " + d;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
        }
        if(E.contains("midpoint("))							//syntax = (x1,y1,x2,y2)
        {fromMethods = true;
	        String[] cord = new String[4];
	        String mx = "", my = "";
	        double x1 = 0, x2 = 0, y1 = 0, y2 = 0;
	        
	        E = E.substring(9,E.length()-1);
	        cord = E.split(",");
	        
	        try{
		        x1 = Double.parseDouble(cord[0]);
		        y1 = Double.parseDouble(cord[1]);
		        x2 = Double.parseDouble(cord[2]);
		        y2 = Double.parseDouble(cord[3]);
		        
		        mx = trim(Double.toString((x2 + x1)/ 2));
		        my = trim(Double.toString((y2 + y1)/ 2));
		
		        E = origional + " = (" + mx + ", " + my + ")";
	        } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
        }
        if(E.contains("slope("))						   //syntax = (x1,y1,x2,y2)
        {fromMethods = true;
        	E = E.substring(6,E.length()-1);
        	String[] cord = new String[4];
            double x1 = 0, x2 = 0, y1 = 0, y2 = 0, m = 0;
            cord = E.split(",");
            try{
	            x1 = Double.parseDouble(cord[0]);
	            y1 = Double.parseDouble(cord[1]);
	            x2 = Double.parseDouble(cord[2]);
	            y2 = Double.parseDouble(cord[3]);
	          
	            m = Double.parseDouble(trim(Double.toString(((y2 - y1) / (x2 - x1)))));
	            E = origional + " = " + m;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
        }
    //==============================================    Misc.  methods          ==============================================
    	
    //==============================================    Misc. numeric  methods          ==============================================
    	if(E.contains("factor("))
    	{fromMethods = true;
    		int value = Integer.parseInt((E.substring(7, E.length() - 1)));
    		
    		E = origional + " = " + Primes.primeFactors(value);
    	}
    	if(E.contains("lcm("))
    	{fromMethods = true;
    		String[] values = E.substring(4, E.length() - 1).split(",");
    		
    		E = origional + " = " + ArithmeticUtils.lcm(Long.parseLong(values[0]), Long.parseLong(values[1])); 
    	}
      	if(E.contains("gcd("))
    	{fromMethods = true;
    		String[] values = E.substring(4, E.length() - 1).split(",");
    		
    		E = origional + " = " + ArithmeticUtils.gcd(Long.parseLong(values[0]), Long.parseLong(values[1])); 
    	}
      	if(E.contains("degtorad("))
      	{fromMethods = true;
      		double deg = Double.parseDouble(E.substring(9, E.length() - 1));
      		
      		E = origional + " = " + Math.toRadians(deg);
      	}
       	if(E.contains("degtorad("))
      	{fromMethods = true;
      		double deg = Double.parseDouble(E.substring(9, E.length() - 1));
      		
      		E = origional + " = " + Math.toDegrees(deg);
      	}
       	if(E.contains("recttopolar("))
       	{fromMethods = true;
       		String[] rect = E.substring(12, E.length() - 1).split(",");
       		double r 	 = Double.parseDouble(trim(Double.toString(Math.sqrt((Math.pow(Double.parseDouble(rect[0]), 2) + Math.pow(Double.parseDouble(rect[1]), 2))))));
       		double theta = Double.parseDouble(trim(Double.toString(Math.atan2(Double.parseDouble(rect[1]), Double.parseDouble(rect[0])))));
       		
       		//TODO test
       		
       		E = origional + " = (" + r + ", " + theta + ")";
       	}
    	if(E.contains("ncr("))
    	{fromMethods = true;
    		int N, K = 0;
    		BigInteger ret = BigInteger.ONE;

    		N = Integer.parseInt(E.substring(4, E.indexOf(',')));
    		K = Integer.parseInt(E.substring(E.indexOf(',') + 1, E.indexOf(')')));
    		
    		for (int k = 0; k < K; k++) {
    	        ret = ret.multiply(BigInteger.valueOf(N-k))
    	                 .divide(BigInteger.valueOf(k+1));
    	    }
    		
    		E = origional + " = " + ret.toString();
    	}
    	if(E.contains("interiorangles("))			//syntax = (number of sides)
        {fromMethods = true;
        	E = E.substring(15, E.length() - 1);
        	try{
	        	double sides = Double.parseDouble(E);
	        	E = Double.toString((180 * (sides - 2) / sides));
	        	E = trim(E);
				E = origional + "=" + E;
        	} catch (NumberFormatException a) {
        		E = "Error";
        	}
        }
    	if(E.contains("tofraction("))
        {	//Takes a Decimal value and converts to the numerical form
        	fromMethods = true;
        	System.out.println("toFraction called");
        	try{
	        	double value  = Double.parseDouble(E.substring(11, E.length() - 1));
	        	System.out.println("     " + value);
	        	Fraction f = new Fraction(value);
	        	E = origional + " = " + f.toString();
        	} catch(NumberFormatException a) {
	      		E = "Error";
	      	}
        }
        if(E.contains("sigma(") || E.contains("Σ("))		
    	{//http://www.mathsisfun.com/algebra/sigma-notation.html		(start, end, operation)
        	/* 
        	 * In the event that a list operation calls "Σ" || "sigma" the method is in listMethods.
        	 * this is processed the same a sum just different input options 
        	 */
    		fromMethods = true;
    		int     start = 0, end = 0, count = 1;
    		double  sum   = 0;
    		String  op    = "", var = "n", e = null;

    		String[] values = new String[3];			//creates list based on amount of values needed
    		E = E.substring(E.indexOf('(') + 1, E.length() - 1);
    		values = E.split(",");
    		/* Syntax
    		 * [0] = Start index
    		 * [1] = End   index
    		 * [2] = Variable : Operation 
    		 */
    		
			try						//catch for no start value assigned : default is 1
			{
	    		start = Integer.parseInt(values[0]);
				end   = Integer.parseInt(values[1]);
				op    = values[2];
			} catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
				start = 1;
				end   = Integer.parseInt(values[0]);
				op    = values[1];			
			}
    	
    		if(op.contains(":"))						//if variable change from n requested
    		{
    			var = op.substring(0, op.indexOf(':'));
    			op  = op.substring(op.indexOf(':') + 1);
    		}

			e = op;										//preserves equation for cyclic use
    		
			count = start;
			while(count <= end)
			{
				op = op.replace(var, Integer.toString(count));
				sum = sum + Double.parseDouble(parCycle(op));
				count++;
				op = e;
			}
			E = trim(E);
       		E = origional + " = " + sum;
   		}
    	
    	if(E.contains("sum("))
    	{fromMethods = true;
    		E = E.substring(4, E.length()-1);
    		int values = 1, x = 0;
    		double total = 0;
    		
    		while(x < E.length())					//done to have an infinite amount of values to sum
    		{
    			if(E.charAt(x) == ',')
    			{
    				values = values + 1;
    			}
				x++;
    		}
            String[] cord = new String[values];
            cord = E.split(",");
            x = 0;
            
            try{
	            while(x < values)
	            {
	            	total = total + Double.parseDouble(cord[x]);
	            	x++;
	            }
	            E = trim(E);
	            E = origional + " = " + total;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
    	}
    	if(E.contains("average("))
    	{fromMethods = true;
    		E = E.substring(8, E.length()-1);
    		int values = 1, x = 0;
    		double total = 0;
    		while(x < E.length())					//done to have an infinite amount of values to sum
    		{
    			if(E.charAt(x) == ',')
    			{
    				values++;
    			}
				x++;
    		}
    		String[] cord = new String[values];
    		cord = E.split(",");
            x = 0;
            
            try{
	            while(x < values)
	            {
	            	total = total + Double.parseDouble(cord[x]);
	            	x++;
	            }
	    		total = total / values;
	            E = origional + " = " + total;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
    	}
    	if(E.contains("intrest("))				//syntax = principal, rate, time 
    	{fromMethods = true;
    		E = E.substring(8, E.length() - 1);
    		String val[] = E.split(",");
    		try{
    			E = Double.toString(Double.parseDouble(val[0]) * (1 + (Double.parseDouble(val[1]) * Double.parseDouble(val[2]))));
    			E = trim(E);
    			E = origional + "=" + E;
    		} catch (NumberFormatException a) {
    			E = "Error";
    		}
    	}
        if(!fromMethods)		//resets E back to natural input "E" to preserve case
        	E = origional;
      
        return E;
    }
    private String listMethods(String E)
    {
    	String origional = E; 						//hold user input for case & ease to follow multiple equations
    	E = E.toLowerCase();
    	if(E.contains("sum(") || E.contains("sigma(") || E.contains("Σ("))
    	{fromMethods = true;
    		if(E.contains("sum("))
    			E = E.substring(4, E.length()-1);
    		if(E.contains("sigma("))
    			E = E.substring(6, E.length() - 1);
    		if(E.contains("Σ("))
    			E = E.substring(2, E.length() - 1);
    		
    		//TODO Finish these methods Biiiitch!
    		
    		int values = 1, x = 0;
    		double total = 0;
    		
    		while(x < E.length())					//done to have an infinite amount of values to sum
    		{
    			if(E.charAt(x) == ',')
    			{
    				values = values + 1;
    			}
				x++;
    		}
            String[] cord = new String[values];
            cord = E.split(",");
            x = 0;
            
            try{
	            while(x < values)
	            {
	            	total = total + Double.parseDouble(cord[x]);
	            	x++;
	            }
	            E = origional + " = " + total;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
    	}
    	if(E.contains("average("))
    	{fromMethods = true;
    		E = E.substring(8, E.length()-1);
    		int values = 1, x = 0;
    		double total = 0;
    		while(x < E.length())					//done to have an infinite amount of values to sum
    		{
    			if(E.charAt(x) == ',')
    			{
    				values++;
    			}
				x++;
    		}
    		String[] cord = new String[values];
    		cord = E.split(",");
            x = 0;
            
            try{
	            while(x < values)
	            {
	            	total = total + Double.parseDouble(cord[x]);
	            	x++;
	            }
	    		total = total / values;
	            E = origional + " = " + total;
            } catch(NumberFormatException | ArrayIndexOutOfBoundsException a) {
	      		E = "Error";
	      	}
    	}
    	
    	if(!fromMethods)		//resets E back to natural input "E" to preserve case
        	E = origional;
    	
    	return E;
    }   
    //==============================================    Public Class methods     ==============================================
    public Calculator()
    {
    	/*
    	 * object is created on launch with no values 
    	 * value is updated from setEquation() on each 'enter' button press
    	 */
    }
    public void setEquation(String equation)
    {
    	/*
    	 * called from 'enter' operation
    	 */
    	this.equation = equation;
    }
    public String getLastAnswer()
    {
    	/*
    	 * called directly from last Ans button 
    	 */
    	
    	return this.lastAnswer;
    }
    public boolean containsMethod(String E) {
		//this is done to allow for (...) set to exist within a called method
        boolean methodFound = false;
        
        try {
			for(int x = 0; x < Methods.methods.length && !methodFound; x++)		
	    	{					//subtract the amount of non (...) methods to not throw error but will work with search 
	        	if(E.indexOf("(") != -1)
	        	{
		    		if(E.substring(0, E.indexOf("(")).toLowerCase().equals(Methods.methods[x][0].toLowerCase()))
		    		{
		    			if(E.substring(E.indexOf("(") + 1, E.length()-1).contains("("))                           //Conditional for parenthesis( ) to only be called when there is a (...) set
		    			{
		    				System.out.println("method location 1");
		    				
		    				String eF   = E.substring(0, E.indexOf('(') + 1);
		    				parMethod   = true;
		    				methodFound = true;
		    				E = parenthesis(E.substring(E.indexOf("(")));		// "E" altered to allow for complex algebra to be done
		    				E = eF + E + ")";
		    				// E = methods(E); 	old code kept in case of errors in debugging class inheritance

		    				return true;
		    			}else{													//When E does not contain (...) sets within a method call
		    				System.out.println("methods location 2");
		    				
		    				methodFound = true;
		    				// E = methods(E); 	old code kept in case of errors in debugging class inheritance
		    				return true;
		    			}
		    		}
	        	}
	    	}
        } catch ( Exception e ) {
        	e.printStackTrace();
        	System.out.println("idk something went wrong with parenthesis in 'containsMethod' method");
        }
		return false;
	}
}



















