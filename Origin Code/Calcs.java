import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.FractionConversionException;
import org.apache.commons.math3.primes.Primes;
import org.apache.commons.math3.util.ArithmeticUtils;
import org.apache.commons.math3.distribution.NormalDistribution;

/*
 * @Author         	Max Williams
 * @Date        	Started  :    12 August 2016
 * @Version         1.00
 *
 */


public class Calcs{
	ArrayList<String> varName   = new ArrayList<String>(); 		//Name of variable	indexes will be same as varValue
    ArrayList<String> varValue  = new ArrayList<String>();  	//Value of variable
    ArrayList<String> eList     = new ArrayList<String>();		//List of all past eList to be sent into JTextPane
    
    String[]          options   = {"Decimal places", "Background color", "Defult font sytle", "Defult font color", 
    							   "Defult font size", "Calculator color"};
    String[]          userValue = {"6", "984330", "Calibri", "000000" ,"12", "984330"};				//size = optionss.length
    
    int[]   L1 = new int[100];									//----------
    int[]   L2 = new int[100];
    int[]   L3 = new int[100];
    int[]   L4 = new int[100];
    int[]   L5 = new int[100];									//Lists for users
    int[]   L6 = new int[100];
    int[]	L7 = new int[100];
    int[]   L8 = new int[100];
    int[]   L9 = new int[100];									//----------
    
    int     decimalPlaces = Integer.parseInt(userValue[0]) + 1;	//'+1' is done to allow for user input to be # of values after '.'
    boolean fromMethods   = false;								//For cycle() to run properly
    boolean parMethod	  = false;
    boolean variables     = false;
    boolean radicalPar    = false;								//To process radical operations once
    boolean userComs 	  = false;
    
    String  lastAnswer    = "";  								//Used to hold last output for "User" to call again
    String  equation      = "";									//Used to format output to "User"
    String  N       	  = "";									//Used to allow for repetition calling 
	GUI     G = new GUI(1);
	ErrorPrinter ep = new ErrorPrinter();
    //==============================================    String handler methods     ==============================================
    public static double getAfter(int at, String E)				   	//returns the double after input index
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
    public static double getBefore(int at, String E)				//returns the double before input index
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
    public static String getAfterString(int at, String E)			//returns the double after input index
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
    public static String getBeforeString(int at, String E)			//returns the double before input index
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
    public static int getIndexBefore(int at, String E)              //returns index of prior symbol for Equations trimming
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
    public static int getIndexBeforeSym(int at, String E)          	//returns index of prior symbol for Equations trimming ignoring (...) sets
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
    public static int getIndexAfter(int at, String E)               //returns index of following symbol for Equation trimming
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
    public String trim(String E)                                    //second answer will return actual value in scientific notion (Done by Math class) (WIP)
    {
    	try{
	    	lastAnswer = E;										//preserves no edited value for large decimal values
	    	
	    	System.out.println(">>>     Trim input = " + E);
	    	ep.write("Trim input : " + E);
	    	
	    	
	    	String sciNotion = null;
	        boolean sigZeros = false;
	    	/*
	    	 * Alter to check for leading '0's for very small number for E-_x_ and 
	    	 * Very large numbers 
	    	 */
	        //----------		Zeros
	    	if(E.equals("0.0"))									//if value is |0| removes decimals
	    	{
	    		ep.write("0.0 cut");
	    		System.out.println("0.0 cut");
	    		return "0";
	    	}
	    	if(Double.parseDouble(E) == 0)
	    	{
	    		return E;
	    	}
	    	//----------
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
	    	ep.write("Input scientific end : " + sciNotion);
	        
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
	    	//---------			
	    	if(E.charAt(E.indexOf('.') + 1) == '0' && E.indexOf('.') == E.length() - 2)
	    	{
	    		System.out.println("Insignificant 0 removed");
	    		ep.write("insignificant 0 removed");
	    		
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
	    		ep.write("decimalValues > decimalPlaces");
	    		
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
    	ep.write("Value not trimed");
    	
        return E;
    }
    public void populate(String E)
    {
        eList = new ArrayList<String>();							//clears eList for new set
        String[] elmt = E.split(System.lineSeparator());			//parses into elmt
        int y = 0;
        for(String x: elmt) 
        {
        	eList.add(x);
        	y++;
        }
    }
    public void toPrint()
    {
    	equation = "";
    	int x = 0;
    	while(x < eList.size())
    	{
    		equation = equation + eList.get(x) + System.lineSeparator();
    		x++;
    	}
    }
    public String whiteSpace(String E)
    {
    	E = E.replaceAll("\\s+","");
    	return E;
    }
    public String subNegation(String E)
    {
    	System.out.println("input subNeg " + E);
    	ep.write(">>>     Input subNegation");
    	
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
    public String getParSub(String E)
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
    public int parOpen(String E)
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
    public int parClose(String E)
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
    public String parenthesis(String E) throws IOException
    {      
    	ep.write(">>>     (Input) " + E);
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
    public String parCycle(String E)
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
       			         * An error has occured if this is reached.
       			         * Built in debugger is called and a report will be sent
       			         */
       					Debug.sendError();
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
    public String unParSet(String E)
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
    	//----------		TODO (x)(y) >> (x)*(y) fix
    	for(int x = 0; x < E.length() - 1; x++)							
    	{											
    		if(E.charAt(x) == ')' && E.charAt(x + 1) == '(')
    		{
    			E = E.substring(0, x + 1) + "*" + E.substring(x + 1);
    		}
    	}
    	return E;
    }
    public String radicalPar(String E)
    {
    	/*
    	 * Restructures equations that contain '√' to fit radical syntax
    	 * Returns (*) ((index) √ (radican))
    	 
    	  					-- input syntax and returns --
    	 
    	 * √radican 					>>		((2.0)√(radican))
    	 * √(radican)					>>		((2.0)√(radican))
		 * (√(radican))					>>		((2.0)√(radican))
		 * 
		 * (index)√radican				>>		((index)√(radican))
		 * ((index)√(radican))			>>		((index)√(radican))
		 * 
		 * value√radican				>>		value*((2.0)√(radican))
		 * value(operation)√radican 	>>		value(operation)((2.0)√(radican))
		 * 
    	 */
    	radicalPar = true;
    	if(E.contains("√"))
    	{
    		ep.write(">>>     radicalPar Input " + E);
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
		    		ep.write("radical index   " + index);
		    		ep.write("radical radican " + radical);
		    		ep.write("E retruned =    " + E);
		    		
		    		System.out.println("------\n[-=" + E + "=-]\n------");
		    		
		    		x = E.indexOf(index + "√" + radical) + index.length() + radical.length() + 1;
    			}
    			
    			x++;
			}
    	}
    	return E;
    }
    public String findLast(String E)
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
    public String findFirst(String E)
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
    //==============================================    Math handler methods     ==============================================
    public String cycle(String E) throws IOException
    {
    	System.out.println(">>>>     cycle input {" + E + "}     <<<<");
    	ep.write(">>>>     cycle input {" + E + "}     <<<<" + System.lineSeparator());
    	try {
	    	//E = userInputErrors(E);						//checks for all operational errors and corrects where possible
    	
			if((eList.size() ==  GUI.inputValues) || (E.equals("")))
    		{
				/*
				 * Stops all processing if user is "daisy-chaining" 'ENTER' 
				 * 
				 * (removed weird logicCycle processing)
				 */
				System.out.println("!!!!!    SAME INPUT     !!!!!" );
    			return E;
    		}
    
	    	E = whiteSpace(E);								//cleans up equation
	    	E = userEasterInputs(E);
	    	if(userComs)
	    	{
	    		/*
	    		 * This is literally just for a basic AI 
	    		 * That "responds" to certain text inputs
	    		 */
	    		return E;
	    	}
	    	
	    	E = subNegation(E);
	    	E = unParSet(E);								//closes all unclosed (...) sets (only adds to the end)
	    	N = E;											//saves original user input
	    	E = variable(E);
	    	
			if(N.contains("Ans"))
        	{
        		eList.remove(eList.size()-1);
        		eList.add(N);
        	}
	    	//----------
	    	if(variables)									
	    	{
	    		System.out.println("Variables Op");
	         	eList.remove(eList.size()-1);
	           	eList.add(E);
	            return E;
	    	}
	    	//----------
	    	if(isListOp(E))									//tests if there are values to be 
	    	{
	    		System.out.println("List Op");
	    		E = listCycle(E);
	    		
	         	eList.remove(eList.size()-1);
	           	eList.add(E);
	    		return E;
	    	}
	    	//---------
	        if(isLogic(E))
	        {
	        	System.out.println("Logic Op");
	        	E = logicCycle(E);
	         	eList.remove(eList.size()-1);
	           	eList.add(E);
	        	return E;
	        }
	        
	    	if(E.toLowerCase().contains("sigma(") || E.contains("Σ("))	//allows methods with required variables to not be replaced
	    	{
	    		E = methods(E);
	    		E = E.substring(0, E.indexOf('=') + 1) + trim(E.substring(E.indexOf('=') + 1));
	         	eList.remove(eList.size()-1);
	           	eList.add(E);
	    		
	    		return E;
	    	}
	        //-----
	        E = trig(E);  
	        
	        if(E.contains("log("))
	        	E = log(E.indexOf("log(") ,E);
	        //-----
	        //this is done to allow for (...) set to exist within a called method
	        boolean methodFound = false;
	        
	        //TODO optimize : if no letters skip : use Pattern
	        
	        for(int x = 0; x < G.mSearch.length && !methodFound; x++)		
	    	{					//subtract the amount of non (...) methods to not throw error but will work with search 
	        	if(E.indexOf("(") != -1)
		    		if(E.substring(0, E.indexOf("(")).toLowerCase().equals(G.mSearch[x][0].toLowerCase()))
		    		{
		    			if(E.substring(E.indexOf("(") + 1, E.length()-1).contains("("))                           //Conditional for parenthesis( ) to only be called when there is a (...) set
		    			{
		    				System.out.println("method location 1");
		    				ep.write("method : method is called having a par sub set");
		    				
		    				String eF   = E.substring(0, E.indexOf('(') + 1);
		    				parMethod   = true;
		    				methodFound = true;
		    				E = parenthesis(E.substring(E.indexOf("(")));		// "E" altered to allow for complex algebra to be done
		    				E = eF + E + ")";
		    				E = methods(E);
		    			}else{													//When E does not contain (...) sets within a method call
		    				System.out.println("methods location 2");
		    				ep.write("method : method is called being a default input");
		    				
		    				methodFound = true;
		    				E = methods(E);
		    			}
		    		}
	    	}
	        
	        if(!parMethod && !fromMethods && E.contains("("))
	        {
	        	ep.write("method was done but contained ( )");
	        	ep.write("Parenthesis was called");
	        	
	        	E = parenthesis(E);
	        	
	        	System.out.println("!method (answ)   " + E);
	        	System.out.println("---  Parenthesis called  ---");
	        }
	        
	        E = specialMath(E);
	        
	        //end of method with (...) set conditional
	        if(E.contains("Error") || fromMethods == true || E.contains("=") || E.contains("Overflow"))		//done here to skip processes after and avoid error with trim()
	        {
	         	eList.remove(eList.size()-1);
	           	eList.add(E);
	            return E;
	        }
	        //------------------------------------------		Operations		------------------------------------------
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
	       					Debug.sendError();
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
	         	eList.remove(eList.size()-1);
	           	eList.add(E);
	            return E;
	        }else{
	        	if(!parMethod)
	        	{
	            	System.out.println("SDF SDF " + E);
	        		E = trim(E);
	        	}
	    	}
	        
			E = eList.get(eList.size() - 1) + " = " + E;
       		eList.remove(eList.size()-1);
       		eList.add(E);
       		
    	} catch (Exception e) {
    		return "Error";
		}

        return E;
    }
    public double graphingCycle(String E)
    {
    	/*
    	 * @input : requires all variables to be replaced prior
    	 * TODO determine if methods( ) should be called or not
    	 */
    	ep.write(">>>     graphingCycle input {" + E + "}    <<<");
    	System.out.println("Graphing input " + E);

    	E = trig(E);  

    	if(E.contains("(") || E.contains(")"))
    	{
    		E = unParSet(E);
    		E = radicalPar(E);
    		
    		try {
				E = parenthesis(E);
			} catch (Exception e) {
				/*
			     * An error has occurred if this is reached.
			     * Built in debugger is called and a report will be sent
			     */
				Debug.sendError();
				return 999999999;
			}
    	}
    	
        /*
         * Create preset sin, cos, tan, arcsin, acrcos, arctan, ses, scs, cot 
         *  //unicode char for superscript (-1)
         * 		uses input constants to move preset points
         */
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
       				{
           				return 999999999;
       				}
       				try {
       					lastEx = getIndexBefore(lastEx, E);  //check if will return 0 when indexAt is the last key in string
       				} catch (StringIndexOutOfBoundsException a) { 
       					/*
       				     * An error has occurred if this is reached.
       				     * Built in debugger is called and a report will be sent
       				     */
       					Debug.sendError();
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
       				break;
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
    	return Double.parseDouble(E);
    }
    public String specialMath(String E)							   //for non-conventional math handlers (WIP)
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
			    			
			    			ep.write("First!     " + first);
			    			ep.write("Answ!      " + ans);
			    			ep.write("Last!      " + last + System.lineSeparator());
			    			
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
    				
    				System.out.println(E);
    				System.out.println(pcent);
    				
    				ep.write("%E         " + E);
    				ep.write("%value     " + pcent);
    				
    				pcent = pcent / 100;
    				
    				E = E.replace("®", Double.toString(pcent));
    				
					System.out.println(E);
					ep.write("%answ     " + pcent);
    			}
    		}
    	}
    	if(E.contains("±"))
    	{
    		ep.write(">>>     ± input {" + E + "}     <<<");
    		
    		String plus = E.replace('±', '+'), min = E.replace('±', '—');
    		try {
				min  = cycle(min);
	    		plus = cycle(plus);
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    		E = "(" + min + ", " + plus + ")";
    		
    		ep.write("Plus+-      " + plus);
			ep.write("Min+-       " + min);
			ep.write("Min+-       " + E + System.lineSeparator());
    		
    		fromMethods = true;
    	}
    	return E;
    }
    public String log(int at, String E)
    {//syntax log(base, resulting product)
    	double base = 0, resul = 0;
        String[] parts = new String[2];
    	E = E.substring(4,E.length()-1);		//removes "log("  &&  ")"

        parts = E.split(",");
        
        base = Double.parseDouble(parts[0]);
        resul = Double.parseDouble(parts[1]);
        
        E = Double.toString(Math.log(resul) / Math.log(base));
        
       	ep.write("baseLog      " + base);
    	ep.write("resulLog     " + resul);
    	ep.write("ELog         " + E + System.lineSeparator());
        
        return E;
    }
    public String radical(int at, String E)
    {//√(...) radical syntax

		/*	SYNTAX
		 * power√base	( (...) can exist any where around base and power)
		 * 				( ' √ ' treats first number after as the root )
		 */
    	if(at == E.length() - 1)
    	{
    		ep.write("Syntax Error");
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
        
        ep.write("√Firs     " + first);
		ep.write("√Answ      " + answ);
		ep.write("√Last      " + last + System.lineSeparator());
        
        E = first + answ + last;

        return E;
    }
    public String exponet(int at, String E) 
    {             
        if(at == E.length() - 1)
        {
        	ep.write("Syntax Error");
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
        	ep.write("Undefined");
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
        		ep.write("Overflow");
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
        
        ep.write("^Firs     " + first);
		ep.write("^Answ      " + answ);
		ep.write("^Last      " + last + System.lineSeparator());

        E = first + answ + last;
        
        return E;
    }
    public String multiply(int at, String E) 
    {                     
        if(E.charAt(0) == '*' || at == E.length() - 1)
        {    
        	ep.write("Syntax Error");
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
        
        ep.write("*Firs     " + first);
		ep.write("*Answ      " + answ);
		ep.write("*Last      " + last + System.lineSeparator());

        E = first + val1.toPlainString() + last;

        return E;
    }
    public String divide(int at, String E) 
    {
        if(E.charAt(0) == '/' || at == E.length() - 1)
        {
        	ep.write("Syntax Error");
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
				ep.write("/0 = Undefined");
				return "Undefined";
			}
			if(a.toString().contains("Non-terminating"))
			{
				System.out.println("Non-terminating");
				ep.write("Non-terminating alert");
				//TODO Test Meth.IEEEremainder(x, y) 
				val1 = new BigDecimal(Math.IEEEremainder(val1.doubleValue(), val2.doubleValue()));
				/*
			     * An error has occurred if this is reached.
			     * Built in debugger is called and a report will be sent
			     * This is sending to help us collect data on these events
			     */
				Debug.sendError();
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
        
        ep.write("/Firs     " + first);
		ep.write("/Answ      " + answ);
		ep.write("/Last      " + last + System.lineSeparator());
        
        E = first + answ + last;

        return E;
    }
    public String add(int at, String E) 
    {
    	if(E.charAt(0) == '+' || at == E.length() - 1)
    	{
    		ep.write("Syntax Error");
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
    public String subtract(int at, String E) 
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
    //==============================================    List         methods     ==============================================
    public String listCycle(String E)
    {
    	E = listMethods(E);
    	return E;
    }
    public boolean isListOp(String E)
    {
    	/*
    	 * Detects if the equation input requires list operations
    	 */
    	E = E.toLowerCase();
    	if(E.contains("l1") || E.contains("l2") || E.contains("l3") || E.contains("l4") || E.contains("l5") || 
    			E.contains("l6") || E.contains("l7") || E.contains("l8") || E.contains("l9"))
    	{
    		E = N;
    		return true;
    	}
    	return false;
    }
    //==============================================    Logic        methods     ==============================================    
    public boolean isLogic(String E)				//done to alter state of logicToggle 
    {
    	/*
    	 * While ' : ' and ' * ' are used here in source code to run logic operations user is not allowed to process logic
    	 * equations with the " Paper Plane Technologies " logic operation chars. These char are for internal use only.
    	 */
    	
    	int[] dChar = new int[E.length()];
    	
    	for(int x = 0; x < E.length(); x++)			//populates array for all special symbols to be replaced
    	{
    		dChar[x] = (int)E.charAt(x);
    	}
    	for(int x = 0; x < dChar.length; x++)		//compares all values that are like operation for later detection
         {	//if compared to decimal then the char is a special char not detected by java.String 
    		if(E.equals("t") || E.equals("f"))
    		{
    			return true;
    		}
         	if(dChar[x] == 183  || dChar[x] == 8743 || dChar[x] == 38)			//and chars  :  ∧   · &  
         	{
         		return true;
         	}
         	if(dChar[x] == 8741 || dChar[x] == 8744 || dChar[x] == 124)			//or chars   :  ∨   ∥   + |
         	{
         		return true;
         	}
	         	if(x < dChar.length - 1)										//or ' + ' detection
	         	{
	         		if(dChar[x] == 43 && (E.charAt(x+1)=='t'||E.charAt(x+1)=='T'||E.charAt(x+1) == 'f'||E.charAt(x+1)=='F'))
	         		{
	         			return true;
	         		}
	         	}
         	if(dChar[x] == 8660 || dChar[x] == 8801 || dChar[x] == 8596)		//if sign is the same  :  ⇔  ≡  ↔
         	{
         		return true;
         	}
         	if(dChar[x] == 8853 || dChar[x] == 8891)							//if one but not the other is true  :  ⊕    ⊻
         	{
         		return true;             
         	}
         	if(E.charAt(x) == '>' || E.charAt(x) == '<' || E.charAt(x) == '≤' || E.charAt(x) == '≥' || E.charAt(x) == '=' || E.charAt(x) == '≠')
         	{	
         		/*
         		 *	These operators will toggle a numerical comparison
         		 */
    			E = numLogic(E, x);
    			return true;
         	}
         }
    	return false;
    }
    public String logicCycle(String E)
    {
    	/*
    	 * Input requires Tt || Ff input only not true | false
    	 */
		if(E.equalsIgnoreCase("t"))
			E = "True";
		if(E.equalsIgnoreCase("f"))
			E = "False";
		
    	if(E.equals("True") || E.equals("False"))
    		return E;
    	
    	E = E.toLowerCase();						//converted here as case sensitivity conditionals are all proven false by here

    	String first = "", answ = "", last = "";	//for holding all parts of operation
    	
    	for(int y = 1; y < E.length(); y++)			//removes duplicates
    	{
    		if(E.charAt(y) == '(' || E.charAt(y) == ')')
    		{
    			//do nothing
    		}
    		else if(E.charAt(y-1) == E.charAt(y))
    		{
    			first = E.substring(0, y);
    			last  = E.substring(y+1);
    			E     = first + last;
    			y     = y - 1;
    		}
    	}
    	//-----------------		Declared here to first remove repeating
    	int[]  dChar = new int[E.length()];			//holds decimal value to compare odd char to be replaced to know by String
        char[] cChar = new char[dChar.length];		//holds each char in String E to be compared
        //-----------------
        for(int x = 0; x < E.length(); x++)		//populates array for all special symbols to be replaced
        {
     	   dChar[x] = (int)E.charAt(x);
        }

    	/*
    	 *	Operations converted to Paper Plane Technology logic syntax
    	 */
    	boolean filled = false;								//For chars that are not syntax chars				

        for(int x = 0; x < dChar.length; x++)		
        {
        	filled = false;									//reset for each index value
        	if(dChar[x] == 183 || dChar[x] == 8743)			//and chars  :  ∧   ·   
        	{
        		cChar[x] = '&';		//use only 1 : duplicated removed for developer curve
        		filled = true;
        	}
        	if(dChar[x] == 8741 || dChar[x] == 8744)		//or chars   :  ∨   ∥
        	{
        		cChar[x] = '|';		//use only 1 : duplicates removed for developer curve
        		filled = true;
        	}
        	if(dChar[x] == 8660 || dChar[x] == 8801 || dChar[x] == 8596)		//if sign is the same  :  ⇔  ≡  ↔
        	{
        		cChar[x] = ':';
        		filled = true;
        	}
        	if(dChar[x] == 8853 || dChar[x] == 8891)		//if one but not the other is true  :  ⊕    ⊻
            {
        		cChar[x] = '*';
        		filled = true;
            }
        	if(!filled)
        	{
        		cChar[x] = E.charAt(x);
            }
        }
        //----------				Reconstructs E to be processed as boolean logic
        E = "";
        for(int x = 0; x < cChar.length; x++)		
        {
        	E = E + cChar[x];
        }
        
        //--------------------					Negation
        for(int x = 0; x < E.length(); x++)			//reverses sings that lead with a negation char
		{	//x = index that has negation char
			if(E.charAt(x) == '!' || E.charAt(x) == '¬' || E.charAt(x) == '˜' || E.charAt(x) == '~')
			{
				first = E.substring(0, x);		//removes negation char
				last  = E.substring(x+2);		//removes T || F char after negation
				answ  = E.substring(first.length(), first.length()+2);
				
				if(E.charAt(x + 1) == '=')
					answ = "≠"; 
				if(answ.equals("!t"))
					answ = "f";
				if(answ.equals("!f"))
					answ = "t";
				E = first + answ + last;
			}	
		}
        //----------			For multilevel logic tables 
    	if(E.contains("("))		
    	{
    		E = logicPar(E);
    	}	
    	
    	if(E.equals("t"))
    		E = "True";
    	if(E.equals("f"))
    		E = "False";
    	
    	if(E.equals("True") || E.equals("False"))		//if E is not "True" or "False" then it is carrying unknown symbols
    	{
    		return E;
    	} else {
    	//----------			For Logic without (...) sets 
			for(int x = 0; x < E.length(); x++)
			{
	    		char sym = ' ', beforeAt = ' ', afterAt = ' ';
	    		int z = 0;
	    		
	    		while(sym == ' ')
	    		{
	    			if(E.charAt(z) != 't' && E.charAt(z) != 'f')
	    			{
	    				sym = E.charAt(z);
	    				beforeAt = E.charAt(z - 1);
	    				afterAt  = E.charAt(z + 1);
	    				
	    				E = E.substring(z + 2);
	    				//trims E to follow boolean (left > right) : sense no parthenesis
	    				
	    				break;
	    			} else {
	    				z++;
	    			}
	    		}
	    		
	    		if(sym == '&' || sym == '^')		//AND logic gates 
	    		{	
	    			if(beforeAt == afterAt)
	    				E = 't' + E;
	       			else{
	       				E = 'f' + E;
	       			}
	    		}												
	    		if(sym == '|' || sym == '+')		//OR logic gates  ∨  
	    		{		
	    			if(beforeAt == 't' || afterAt == 't')
	    				E = 't' + E;
	    			else{
	    				E = 'f' + E;
	    			}
	   			}
	    		if(sym == ':')								//if sign is the same  :  ⇔  ≡  ↔
	    		{
	    			if(beforeAt == afterAt)
	    				E = 't' + E;
	    			else{
	    				E = 'f' + E;
	    			}	
	    		}
	    		if(sym == '*')								//if one but not the other is true  :  ⊕    ⊻
	    		{
	    			if((beforeAt == 't' && afterAt != 't') || (beforeAt != 't' && afterAt == 't'))
	    				E = 't' + E;
	    			else{
	    				E = 'f' + E;
	    			}
	    		}
	    		sym = ' ';
	    		x++;
			}
    	}
		//-----
		
    	if(E.equals("t"))
    		E = "True";
    	if(E.equals("f"))
    		E = "False";
    	
    	if(E.equals("True") || E.equals("False"))		//if E is not "True" or "False" then it is carrying unknown symbols
    		return E;
    	else{
    		return "Error";
    	}
    	
    	
    }
    public String logicPar(String E) 
    {
    	String first = "", par = "", last = "";
    	
		while(E.contains("("))
		{
    		char sym = ' ', beforeAt = ' ', afterAt = ' ';
    		int z = 0;	    		
    		
    		first = E.substring(0, parOpen(E));        //gets E from 0 > index of current open && removes '('
            last = E.substring(parClose(E) + 1);
    		
            par = getParSub(E);
    	
            while(sym == ' ')
    		{
    			if(par.charAt(z) != 't' && par.charAt(z) != 'f')
    			{
    				sym = par.charAt(z);
    				beforeAt = par.charAt(z - 1);
    				afterAt  = par.charAt(z + 1);
    				
    				//trims E to follow boolean (left > right) : sense no parthenesis
    				break;
    			} else {
    				z++;
    			}
    		}
    		
    		if(sym == '&' || sym == '^')		//AND logic gates 
    		{	
    			if(beforeAt == afterAt)
    				par = "t";
       			else{
       				par = "f";
       			}
    		}												
    		if(sym == '|' || sym == '+')		//OR logic gates  ∨  
    		{		
    			if(beforeAt == 't' || afterAt == 't')
    				par = "t";
    			else{
    				par = "f";
    			}
   			}
    		if(sym == ':')								//if sign is the same  :  ⇔  ≡  ↔
    		{
    			if(beforeAt == afterAt)
    				par = "t";
    			else{
    				par = "f";
    			}	
    		}
    		if(sym == '*')								//if one but not the other is true  :  ⊕    ⊻
    		{
    			if((beforeAt == 't' && afterAt != 't') || (beforeAt != 't' && afterAt == 't'))
    				par = "t";
    			else{
    				par = "f";
    			}
    		}
    		
            E = first + par + last;
            
			sym = ' ';
		}
		return E;
	}
    public String numLogic(String E, int at)
    {
    	String temp = "";	//for holding all parts of operation
    	
    	for(int y = 1; y < E.length(); y++)			//removes duplicates
    	{
    		if(E.substring(y, y + 1).matches("[^A-Za-z0-9]"))			//tests if a symbol
	    		if(E.charAt(y-1) == E.charAt(y))
	    		{
	    			temp = E.substring(0, y);
	    			E    = E.substring(y+1);
	    			E    = temp + E;
	    			y    = y - 1;
	    		}
    	}
    	
    	char sym = E.charAt(at);
    	
    	//----------					Negation
    	for(int x = 0; x < E.length(); x++)			
  		{	//x = index that has negation char
  			if(E.charAt(x) == '!' || E.charAt(x) == '¬' || E.charAt(x) == '˜' || E.charAt(x) == '~')
  			{
  				if(E.charAt(x + 1) == '=')
  					sym = '≠'; 
  				if(E.charAt(x + 1) == '>')
  					sym = '≤';
  				if(E.charAt(x + 1) == '<')
  					sym = '≥';
  			}
  		}
    	
    	String one = E.substring(0, at), two = E.substring(at + 1);
    	 
    	ep.write("Number Logic Operaion\n1o = <" + one + ">  :  2o = <" + two + ">");
    	System.out.println("Number Logic Operaion\n1o = <" + one + ">  :  2o = <" + two + ">");
       
    	//----------
    	try {
			one = cycle(one);
	    	if(one.contains("="))
	    		one = one.substring(one.indexOf("=") + 2);

	    	two = cycle(two);
	    	if(two.contains("="))
	    		two = two.substring(two.indexOf("=") + 2);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//---------										//for method(...) calles


    	//---------    	
    	if(sym == '=')
    	{
    		if(one.equals(two))
    			E = "True";
    		else
    			E = "False";
    	}
    	if(sym == '≠')
    	{
    		if(one.equals(two))
    			E = "False";
    		else
    			E = "True";
    	}
    	if(sym == '>')
    	{
    		if(Double.parseDouble(one) > Double.parseDouble(two))
    			E = "True";
    		else
    			E = "False";
    	}    	
    	if(sym == '≥')
    	{
    		if(one.equals(two) || Double.parseDouble(one) > Double.parseDouble(two))
    			E = "True";
    		else
    			E = "False";
    	}
    	if(sym == '<')
    	{
    		if(Double.parseDouble(one) < Double.parseDouble(two))
    			E = "True";
    		else
    			E = "False";
    	}    	
    	if(sym == '≤')
    	{
    		if(one.equals(two) || Double.parseDouble(one) < Double.parseDouble(two))
    			E = "True";
    		else
    			E = "False";
    	}
    	
    	System.out.println("1  = " + one + "  :  2  = " + two +" E = " + E +"\n==========================");
    	
    	return E;
    }
    //==============================================    trigonometry methods     ==============================================
    public String trig(String E)
    {
        
    //Create Buttons sin cos tan && inverse print out sin(...) cos(...) tan(...) && inverse========
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
    public String methods(String E)			//make sure to add to GUI array with description and search words
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
    	if(E.contains("delvar("))		//TODO test in works with GUI
    	{fromMethods = true;
    		boolean exist = false;
    		String var = E.substring(7, E.length() -1);
        	for(int x = 0; x < varName.size(); x++)
        	{
        		if(varName.get(x) == var)
        		{
        			exist = true;
        			varName.remove(x);
        			varValue.remove(x);
        		}
        	}
        	if(exist)
        		E = "Done";
        	if(!exist)
        		E = "Error";
    	}
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
    public String listMethods(String E)
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
    	
    	if(E.contains("clrlist("))
    	{fromMethods = true;
    		String list = E.substring(8, E.length() -1);
    		list = list.toUpperCase();
    		switch(list)
    		{
    		case "L1":
    			L1 = new int[100];
    			break;
    		case "L2":
    			L2 = new int[100];
    			break;
    		case "L3":
    			L3 = new int[100];
    			break;
    		case "L4":
    			L4 = new int[100];
    			break;
    		case "L5":
    			L5 = new int[100];
    			break;
    		case "L6":
    			L6 = new int[100];
    			break;
    		case "L7":
    			L7 = new int[100];
    			break;
    		case "L8":
    			L8 = new int[100];
    			break;
    		case "L9":
    			L9 = new int[100];
    			break;
    		default:
    			E = "Error";
    		}
    		E = origional + " : Done";
    	}
        if(!fromMethods)		//resets E back to natural input "E" to preserve case
        	E = origional;
    	
    	return E;
    }   
    //==============================================    Variable methods     ==============================================
    public String variable(String E) throws IOException
    {
    	//----------
    	//Equation that leads with an operator without any double before uses lastAnswer by default
    	if(E.charAt(0) == '+'||E.charAt(0) == '—'||E.charAt(0) == '*'||E.charAt(0) == '/'||E.charAt(0) == '^'||E.charAt(0) == '!')
    	{
    		E = "Ans" + E;
    		N = E;			//updates N to be compliant with request but will hold "Ans" for loop callings
    	}
    	if(lastAnswer.isEmpty() == false && E.contains("Ans"))
    	{
    		E = E.replace("Ans", lastAnswer);
    	}
    	if(lastAnswer.isEmpty() == true && E.contains("Ans"))
    	{
    		E = "Error";
    	}
    	//----------
    	if(E.contains("»{") && E.charAt(E.length() - 1) != '}')			//adds closing brace if absent for lists
    		E = E + "}";	
    	
    	if(E.contains("»{"))
    	{
        	E = storeVar(E.substring(0,E.indexOf('»')), E.substring(E.indexOf('»')+1));		//keeps " { "  &  " } "
        	return E;
    	}
    	if(E.contains(":="))
    	{
    		try{
    			getBefore(E.indexOf(':'), E);		//tests if numerical value is before ":="
    			E = "Error";
    		}catch(NumberFormatException e){
        		E = storeVar(getAfter(E.indexOf(':') + 1, E), E.substring(0,E.indexOf(':')));	
        		return E;
    		}
    	}
    	//----------
    	boolean isMethod = false;									//handles variable replacement
    	if(E.indexOf("(") != -1)
		{	
    		for(int y = 0; y < G.mSearch.length; y++)		
			{
				if(E.substring(0, E.indexOf("(")).toLowerCase().equals(G.mSearch[y][0].toLowerCase()))
	    		{
					isMethod = true;
	    		}
			}
		}
		
    	for(int x = 0; x < varName.size() && !isMethod; x++)		//loops through all stored variables to replace
    	{
    		String var = varName.get(x);
    		if(E.contains(var))
    		{
    			if(E.equals(var))
    			{
    				E = varName.get(x) + " = " + varValue.get(x);
    				variables = true;
    			}else{
    				E = E.replace(var, varValue.get(x));
    			}
    		}
    	}
    	//TODO Lists 
    	for(int x = 0; x < 10 && !isMethod; x++)
    	{
    		if(E.equals("L"+x))
    		{
    			//E = E + " = " + lists[x][1];
    		}else{
    			/*
    			 * add list operation handlers form tables
    			 */
    		}	
    	}
    	return E;
    }
    public String storeVar(double val, String name) throws NumberFormatException, IOException			//all normal var
    {		
    	variables = true;
    	System.out.println("name = " + name +", val = " + val);
    	val       = Double.parseDouble(cycle(Double.toString(val)));		//allows for operations to exist in variable storage
    	
     	varName.add(0, name);
		varValue.add(0, Double.toString(val));

    	return name + " = " + val;
    } 
    public String storeVar(String list, String val)		//all lists (List index name, {value to be stored separated by " , "}
    {
    	variables = true;
    	int x = 0;
    	list  = list.toUpperCase();
    	while(x < 10)						//finds L[x] that is being initialized
    	{
    		/*if(lists[x][0].contains(list))
    		{
    			lists[x][1] = val;
    			break;
    		} */
    		x++;
    	}
    	
    	return list + " = {" + val + "}";
    } 
    //==============================================    File save methods     ==================================================
    public void getVar() throws IOException           //Called upon launch
    {
    	try {  
    		File dir    = new File (System.getProperty("user.dir") + File.separator + "User");// + File.separator + "User
    		File variables = new File(dir, "Variables.txt");
    		
    		if(!variables.exists())
    		{
    			variables.createNewFile();
    		}

    		FileReader f = new FileReader(variables);  
			BufferedReader br = new BufferedReader(f);
        	String name = "";									//temp string for loop: nextLine && name value
        	String val  = "";

        	while((name = br.readLine()) != null) 
        	{
        		val  = name.substring(name.indexOf(':') + 1, name.length());
        		name = name.substring(0, name.indexOf(':')); 
        		
        		varName.add(name);
        		varValue.add(val);
        	}   
        	br.close();
    	} catch (FileNotFoundException e) {
    		Errors er = new Errors(10);
			er.setVisible(true);
        }       
    }
    public String getUserPref()							//Called upon launch
    {
    	/*
    	 * Values will be changed in GUI under menu tab "Preferences" pop out window
    	 */
    	try {
    		File dir    = new File(System.getProperty("user.dir") + File.separator + "User");
    		File prefrences = new File(dir, "Preferences.txt");
    		
    		if(!prefrences.exists())
    		{
    			prefrences.createNewFile();
    		}
    		
    		FileReader f = new FileReader(prefrences);
			BufferedReader br = new BufferedReader(f);
        	String name = "";									//temp string for loop: nextLine && name value
        	String val  = "";
        	
        	int count = 0;
			while((name = br.readLine()) != null) 
			{
				val   = name.substring(name.indexOf(':') + 1, name.length());
			
				userValue[count] = val; 
				count++;
			}
			
        	br.close();
			} catch (IOException e) {
				Errors er = new Errors(9);
				er.setVisible(true);
			}   
             
    	return "Done";
    }
    public String saveAllVar()							//Called upon closing
    {    //https://www.youtube.com/watch?v=BxCbxfpwC7Q
     	for(int x = 0; x < varName.size(); x++)
    	{
     		String name = varName.get(x);
    		for(int y = x + 1; y < varName.size(); y++)	//start at index after last done
    		{	
    			if(name.equals(varName.get(y)))				//removes the older versions found leaving the newest
    			{
    				varName.remove(y);
    				varValue.remove(y);
    			}
    		}
    	}

     	int l = varName.size() - 1;

    	try {   
    		File dir = new File(System.getProperty("user.dir") + File.separator + "User");
    		File file = new File(dir, "Variables.txt");

    		if(!file.exists())
    		{
    			file.createNewFile();
    		}
    		
    		PrintWriter pw = new PrintWriter(file);	
			while(l >= 0)								//include index 0
			{
				pw.write(varName.get(l) + ":" + varValue.get(l));
				pw.write(System.lineSeparator());						//"%n" used for system dependence
				l--;
			}
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			Errors er = new Errors(10);
			er.setVisible(true);
		}
    	return "Done";
    }
    public void saveUserPref()						//Called upon closing	
    {
    	/*
    	 * Values will be changed in GUI under menu tab "Preferences" pop out window
    	 */
    	
    	System.out.println("user pref called");
    	
    	try {   
    		File dir = new File(System.getProperty("user.dir") + File.separator + "User");
    		File file = new File(dir, "Preferences.txt");
    		
    		if(!file.exists())
    		{
    			System.out.println("User pref not exist");
    			file.createNewFile();
    		}

    	    PrintWriter pw = new PrintWriter(file);	
			
    	    int l = 0;									//length of 'options' && 'userValue'
    	    while(l < options.length - 1)								
			{
				pw.write(options[l] + ":" + userValue[l]);
				pw.write(System.lineSeparator());					
				l++;
			}
			pw.flush();
			pw.close();
			
		} catch (IOException e) {
			Errors er = new Errors(9);
			er.setVisible(true);
		}
    }
    public void readMe()
    {
    	try{
    		File dir = new File(System.getProperty("user.dir") + File.separator + "User");
    		File file = new File(dir, "Variables.txt");

    		dir.mkdir();		//Catch if files missing to prevent errors
		
			PrintWriter pw = new PrintWriter(file);
			pw.write("  	-= Preferences =-");
			pw.write("Can be added directly or edited through { menu > Preferences }");
			pw.write("	   - Syntax -				** note the value must be in the order that they appear **");
			pw.write("       	   (Name of Setting):(Value)" + System.getProperty("line.separator"));
			//TODO finish README generation
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
    }
    //==============================================    Error methods          ==============================================
    public String userEasterInputs(String E)
    {
    	if(E.contains("0") || E.contains("1") || E.contains("2") || E.contains("3") || E.contains("4") || 
    	   E.contains("5") || E.contains("6") || E.contains("7") || E.contains("8") || E.contains("9") ||
    	 
    	   E.contains("+") || E.contains("-") || E.contains("*") || E.contains("/") || E.contains("^") ||
    	   E.contains("√"))
    	{
    		/*
    		 * skips this joke of a method
    		 */
    		return E;
    	}
    	//-----
    	for(int x = 0; x < G.mSearch.length; x++)		
    	{		
    							//subtract the amount of non (...) methods to not throw error but will work with search 
        	if(E.indexOf("(") != -1)
	    		if(E.substring(0, E.indexOf("(")).toLowerCase().equals(G.mSearch[x][0].toLowerCase()))
	    		{
	    			return E;
	    		}
    	}
    	//-----
    	if(varName.size() > 0)
    	{
	    	for(int x = 0; x < E.length(); x++)
	    	{
	    		if(E.contains(varName.get(x)))
	    		{
	    			/*
	    			 * Skips this if a variable matches any thing in the input 
	    			 * Better to skip than debug around infinite combinations
	    			 */
	    			return E;
	    		}
	    	}
    	}

    	//====================================================
    	/*
    	 * TODO add random return statements for English text inputs
    	 * 
    	 * !!  NOTE  !!
    	 * 	Any input must not have white spaces as they are removed
    	 */
    	String e = E.toLowerCase();
    	
    	
    	if(e.equals("hi") || e.equals("hello") || e.equals("hi?") || e.equals("hello?") || e.equals("anyone there") || e.equals("anyone there?"))
    	{
    		userComs = true;
    		
    		String[] respond = {"Hi!", "Hello", "What do you want?", "How can I help you?", "Please don't bother me I am doing your math for you.", "How are you?", "Yes?", "...", "No.",
    							"I am a robot. It is forbiden that we talk...", "The magic-8-ball say : \"No.\"", "The magic-8-ball say : \"Yes.\"", "Waz good JimBo?", ";)", ":D", "boo!",
    							"How's math?", "Aren't you supposed to be working rather than talking to a computer?"};
    	
    		int re = new Random().nextInt(respond.length);
    		
    		E = respond[re];
       		eList.remove(eList.size()-1);
       		eList.add(E);
    	}
    	if(e.equals("beemovie"))
    	{
    		userComs = true;
    		
    		E = "According to all known laws of aviation, there is no way a bee should be able to fly. Its wings are too small to get its fat little body off the ground. The bee, of course, flies anyway because bees don't care what humans think is impossible. Yellow, black. Yellow, black. Yellow, black. Yellow, black. Ooh, black and yellow! Let's shake it up a little. Barry! Breakfast is ready! Ooming! Hang on a second. Hello? - Barry? - Adam? - Oan you believe this is happening? - I can't. I'll pick you up. Looking sharp. Use the stairs. Your father paid good money for those. Sorry. I'm excited. Here's the graduate. We're very proud of you, son. A perfect report card, all B's. Very proud. Ma! I got a thing going here. - You got lint on your fuzz. - Ow! That's me! - Wave to us! We'll be in row 118,000. - Bye! Barry, I told you, stop flying in the house! - Hey, Adam. - Hey, Barry. - Is that fuzz gel? - A little. Special day, graduation. Never thought I'd make it. Three days grade school, three days high school. Those were awkward. Three days college. I'm glad I took a day and hitchhiked around the hive. You did come back different. - Hi, Barry. - Artie, growing a mustache? Looks good. - Hear about Frankie? - Yeah. - You going to the funeral? - No, I'm not going. Everybody knows, sting someone, you die. Don't waste it on a squirrel. Such a hothead. I guess he could have just gotten out of the way. I love this incorporating an amusement park into our day. That's why we don't need vacations. Boy, quite a bit of pomp... under the circumstances. - Well, Adam, today we are men. - We are! - Bee-men. - Amen! Hallelujah! Students, faculty, distinguished bees, please welcome Dean Buzzwell. Welcome, New Hive Oity graduating class of... ...9:15. That concludes our ceremonies. And begins your career at Honex Industries! Will we pick ourjob today? I heard it's just orientation. Heads up! Here we go. Keep your hands and antennas inside the tram at all times. - Wonder what it'll be like? - A little scary. Welcome to Honex, a division of Honesco and a part of the Hexagon Group. This is it! Wow. Wow. We know that you, as a bee, have worked your whole life to get to the point where you can work for your whole life. Honey begins when our valiant Pollen Jocks bring the nectar to the hive. Our top-secret formula is automatically color-corrected, scent-adjusted and bubble-contoured into this soothing sweet syrup with its distinctive golden glow you know as... Honey! - That girl was hot. - She's my cousin! - She is? - Yes, we're all cousins. - Right. You're right. - At Honex, we constantly strive to improve every aspect of bee existence. These bees are stress-testing a new helmet technology. - What do you think he makes? - Not enough. Here we have our latest advancement, the Krelman. - What does that do? - Oatches that little strand of honey that hangs after you pour it. Saves us millions. Oan anyone work on the Krelman? Of course. Most bee jobs are small ones. But bees know that every small job, if it's done well, means a lot. But choose carefully because you'll stay in the job you pick for the rest of your life. The same job the rest of your life? I didn't know that. What's the difference? You'll be happy to know that bees, as a species, haven't had one day off in 27 million years. So you'll just work us to death? We'll sure try. Wow! That blew my mind! Whats the difference? How can you say that? One job forever? That's an insane choice to have to make. I'm relieved. Now we only have to make one decision in life. But, Adam, how could they never have told us that? Why would you question anything? We're bees. We're the most perfectly functioning society on Earth. You ever think maybe things work a little too well here? Like what? Give me one example. I don't know. But you know what I'm talking about. Please clear the gate. Royal Nectar Force on approach. Wait a second. Oheck it out. - Hey, those are Pollen Jocks! - Wow. I've never seen them this close. They know what it's like outside the hive. Yeah, but some don't come back. - Hey, Jocks! - Hi, Jocks! You guys did great! You're monsters! You're sky freaks! I love it! I love it! - I wonder where they were. - I don't know. Their day's not planned. Outside the hive, flying who knows where, doing who knows what. You can'tjust decide to be a Pollen Jock. You have to be bred for that. Right. Look. That's more pollen than you and I will see in a lifetime. It's just a status symbol. Bees make too much of it. Perhaps. Unless you're wearing it and the ladies see you wearing it. Those ladies? Aren't they our cousins too? Distant. Distant. Look at these two. - Oouple of Hive Harrys. - Let's have fun with them. It must be dangerous being a Pollen Jock. Yeah. Once a bear pinned me against a mushroom! He had a paw on my throat, and with the other, he was slapping me! - Oh, my! - I never thought I'd knock him out. What were you doing during this? Trying to alert the authorities. I can autograph that. A little gusty out there today, wasn't it, comrades? Yeah. Gusty. We're hitting a sunflower patch six miles from here tomorrow. - Six miles, huh? - Barry! A puddle jump for us, but maybe you're not up for it. - Maybe I am. - You are not! We're going 0900 at J-Gate. What do you think, buzzy-boy? Are you bee enough? I might be. It all depends on what 0900 means. Hey, Honex! Dad, you surprised me. You decide what you're interested in? - Well, there's a lot of choices. - But you only get one. Do you ever get bored doing the same job every day? Son, let me tell you about stirring. You grab that stick, and you just move it around, and you stir it around. You get yourself into a rhythm. It's a beautiful thing. You know, Dad, the more I think about it, maybe the honey field just isn't right for me. You were thinking of what, making balloon animals? That's a bad job for a guy with a stinger. Janet, your son's not sure he wants to go into honey! - Barry, you are so funny sometimes. - I'm not trying to be funny. You're not funny! You're going into honey. Our son, the stirrer! - You're gonna be a stirrer? - No one's listening to me! Wait till you see the sticks I have. I could say anything right now. I'm gonna get an ant tattoo! Let's open some honey and celebrate! Maybe I'll pierce my thorax. Shave my antennae. Shack up with a grasshopper. Get a gold tooth and call everybody dawg! I'm so proud. - We're starting work today! - Today's the day. Oome on! All the good jobs will be gone. Yeah, right. Pollen counting, stunt bee, pouring, stirrer, front desk, hair removal... - Is it still available? - Hang on. Two left! One of them's yours! Oongratulations! Step to the side. - What'd you get? - Picking crud out. Stellar! Wow! Oouple of newbies? Yes, sir! Our first day! We are ready! Make your choice. - You want to go first? - No, you go. Oh, my. What's available? Restroom attendant's open, not for the reason you think. - Any chance of getting the Krelman? - Sure, you're on. I'm sorry, the Krelman just closed out. Wax monkey's always open. The Krelman opened up again. What happened? A bee died. Makes an opening. See? He's dead. Another dead one. Deady. Deadified. Two more dead. Dead from the neck up. Dead from the neck down. That's life! Oh, this is so hard! Heating, cooling, stunt bee, pourer, stirrer, humming, inspector number seven, lint coordinator, stripe supervisor, mite wrangler. Barry, what do you think I should... Barry? Barry! All right, we've got the sunflower patch in quadrant nine... What happened to you? Where are you? - I'm going out. - Out? Out where? - Out there. - Oh, no! I have to, before I go to work for the rest of my life. You're gonna die! You're crazy! Hello? Another call coming in. If anyone's feeling brave, there's a Korean deli on 83rd that gets their roses today. Hey, guys. - Look at that. - Isn't that the kid we saw yesterday? Hold it, son, flight deck's restricted. It's OK, Lou. We're gonna take him up. Really? Feeling lucky, are you? Sign here, here. Just initial that. - Thank you. - OK. You got a rain advisory today, and as you all know, bees cannot fly in rain. So be careful. As always, watch your brooms, hockey sticks, dogs, birds, bears and bats. Also, I got a couple of reports of root beer being poured on us. Murphy's in a home because of it, babbling like a cicada! - That's awful. - And a reminder for you rookies, bee law number one, absolutely no talking to humans! All right, launch positions! Buzz, buzz, buzz, buzz! Buzz, buzz, buzz, buzz! Buzz, buzz, buzz, buzz! Black and yellow! Hello! You ready for this, hot shot? Yeah. Yeah, bring it on. Wind, check. - Antennae, check. - Nectar pack, check. - Wings, check. - Stinger, check. Scared out of my shorts, check. OK, ladies, let's move it out! Pound those petunias, you striped stem-suckers! All of you, drain those flowers! Wow! I'm out! I can't believe I'm out! So blue. I feel so fast and free! Box kite! Wow! Flowers! This is Blue Leader. We have roses visual. Bring it around 30 degrees and hold. Roses! 30 degrees, roger. Bringing it around. Stand to the side, kid. It's got a bit of a kick. That is one nectar collector! - Ever see pollination up close? - No, sir. I pick up some pollen here, sprinkle it over here. Maybe a dash over there, a pinch on that one. See that? It's a little bit of magic. That's amazing. Why do we do that? That's pollen power. More pollen, more flowers, more nectar, more honey for us. Oool. I'm picking up a lot of bright yellow. Oould be daisies. Don't we need those? Oopy that visual. Wait. One of these flowers seems to be on the move. Say again? You're reporting a moving flower? Affirmative. That was on the line! This is the coolest. What is it? I don't know, but I'm loving this color. It smells good. Not like a flower, but I like it. Yeah, fuzzy. Ohemical-y. Oareful, guys. It's a little grabby. My sweet lord of bees! Oandy-brain, get off there! Problem! - Guys! - This could be bad. Affirmative. Very close. Gonna hurt. Mama's little boy. You are way out of position, rookie! Ooming in at you like a missile! Help me! I don't think these are flowers. - Should we tell him? - I think he knows. What is this?! Match point! You can start packing up, honey, because you're about to eat it! Yowser! Gross. There's a bee in the car! - Do something! - I'm driving! - Hi, bee. - He's back here! He's going to sting me! Nobody move. If you don't move, he won't sting you. Freeze! He blinked! Spray him, Granny! What are you doing?! Wow... the tension level out here is unbelievable. I gotta get home. Oan't fly in rain. Oan't fly in rain. Oan't fly in rain. Mayday! Mayday! Bee going down! Ken, could you close the window please? Ken, could you close the window please? Oheck out my new resume. I made it into a fold-out brochure. You see? Folds out. Oh, no. More humans. I don't need this. What was that? Maybe this time. This time. This time. This time! This time! This... Drapes! That is diabolical. It's fantastic. It's got all my special skills, even my top-ten favorite movies. What's number one? Star Wars? Nah, I don't go for that... ...kind of stuff. No wonder we shouldn't talk to them. They're out of their minds. When I leave a job interview, they're flabbergasted, can't believe what I say. There's the sun. Maybe that's a way out. I don't remember the sun having a big 75 on it. I predicted global warming. I could feel it getting hotter. At first I thought it was just me. Wait! Stop! Bee! Stand back. These are winter boots. Wait! Don't kill him! You know I'm allergic to them! This thing could kill me! Why does his life have less value than yours? Why does his life have any less value than mine? Is that your statement? I'm just saying all life has value. You don't know what he's capable of feeling. My brochure! There you go, little guy. I'm not scared of him. It's an allergic thing. Put that on your resume brochure. My whole face could puff up. Make it one of your special skills. Knocking someone out is also a special skill. Right. Bye, Vanessa. Thanks. - Vanessa, next week? Yogurt night? - Sure, Ken. You know, whatever. - You could put carob chips on there. - Bye. - Supposed to be less calories. - Bye. I gotta say something. She saved my life. I gotta say something. All right, here it goes. Nah. What would I say? I could really get in trouble. It's a bee law. You're not supposed to talk to a human. I can't believe I'm doing this. I've got to. Oh, I can't do it. Oome on! No. Yes. No. Do it. I can't. How should I start it? You like jazz? No, that's no good. Here she comes! Speak, you fool! Hi! I'm sorry. - You're talking. - Yes, I know. You're talking! I'm so sorry. No, it's OK. It's fine. I know I'm dreaming. But I don't recall going to bed. Well, I'm sure this is very disconcerting. This is a bit of a surprise to me. I mean, you're a bee! I am. And I'm not supposed to be doing this, but they were all trying to kill me. And if it wasn't for you... I had to thank you. It's just how I was raised. That was a little weird. - I'm talking with a bee. - Yeah. I'm talking to a bee. And the bee is talking to me! I just want to say I'm grateful. I'll leave now. - Wait! How did you learn to do that? - What? The talking thing. Same way you did, I guess. Mama, Dada, honey. You pick it up. - That's very funny. - Yeah. Bees are funny. If we didn't laugh, we'd cry with what we have to deal with. Anyway... Oan I... ...get you something? - Like what? I don't know. I mean... I don't know. Ooffee? I don't want to put you out. It's no trouble. It takes two minutes. - It's just coffee. - I hate to impose. - Don't be ridiculous! - Actually, I would love a cup. Hey, you want rum cake? - I shouldn't. - Have some. - No, I can't. - Oome on! I'm trying to lose a couple micrograms. - Where? - These stripes don't help. You look great! I don't know if you know anything about fashion. Are you all right? No. He's making the tie in the cab as they're flying up Madison. He finally gets there. He runs up the steps into the church. The wedding is on. And he says, Watermelon? I thought you said Guatemalan. Why would I marry a watermelon? Is that a bee joke? That's the kind of stuff we do. Yeah, different. So, what are you gonna do, Barry? About work? I don't know. I want to do my part for the hive, but I can't do it the way they want. I know how you feel. - You do? - Sure. My parents wanted me to be a lawyer or a doctor, but I wanted to be a florist. - Really? - My only interest is flowers. Our new queen was just elected with that same campaign slogan. Anyway, if you look... There's my hive right there. See it? You're in Sheep Meadow! Yes! I'm right off the Turtle Pond! No way! I know that area. I lost a toe ring there once. - Why do girls put rings on their toes? - Why not? - It's like putting a hat on your knee. - Maybe I'll try that. - You all right, ma'am? - Oh, yeah. Fine. Just having two cups of coffee! Anyway, this has been great. Thanks for the coffee. Yeah, it's no trouble. Sorry I couldn't finish it. If I did, I'd be up the rest of my life. Are you...? Oan I take a piece of this with me? Sure! Here, have a crumb. - Thanks! - Yeah. All right. Well, then... I guess I'll see you around. Or not. OK, Barry. And thank you so much again... for before. Oh, that? That was nothing. Well, not nothing, but... Anyway... This can't possibly work. He's all set to go. We may as well try it. OK, Dave, pull the chute. - Sounds amazing. - It was amazing! It was the scariest, happiest moment of my life. Humans! I can't believe you were with humans! Giant, scary humans! What were they like? Huge and crazy. They talk crazy. They eat crazy giant things. They drive crazy. - Do they try and kill you, like on TV? - Some of them. But some of them don't. - How'd you get back? - Poodle. You did it, and I'm glad. You saw whatever you wanted to see. You had your experience. Now you can pick out yourjob and be normal. - Well... - Well? Well, I met someone. You did? Was she Bee-ish? - A wasp?! Your parents will kill you! - No, no, no, not a wasp. - Spider? - I'm not attracted to spiders. I know it's the hottest thing, with the eight legs and all. I can't get by that face. So who is she? She's... human. No, no. That's a bee law. You wouldn't break a bee law. - Her name's Vanessa. - Oh, boy. She's so nice. And she's a florist! Oh, no! You're dating a human florist! We're not dating. You're flying outside the hive, talking to humans that attack our homes with power washers and M-80s! One-eighth a stick of dynamite! She saved my life! And she understands me. This is over! Eat this. This is not over! What was that? - They call it a crumb. - It was so stingin' stripey! And that's not what they eat. That's what falls off what they eat! - You know what a Oinnabon is? - No. It's bread and cinnamon and frosting. They heat it up... Sit down! ...really hot! - Listen to me! We are not them! We're us. There's us and there's them! Yes, but who can deny the heart that is yearning? There's no yearning. Stop yearning. Listen to me! You have got to start thinking bee, my friend. Thinking bee! - Thinking bee. - Thinking bee. Thinking bee! Thinking bee! Thinking bee! Thinking bee! There he is. He's in the pool. You know what your problem is, Barry? I gotta start thinking bee? How much longer will this go on? It's been three days! Why aren't you working? I've got a lot of big life decisions to think about. What life? You have no life! You have no job. You're barely a bee! Would it kill you to make a little honey? Barry, come out. Your father's talking to you. Martin, would you talk to him? Barry, I'm talking to you! You coming? Got everything? All set! Go ahead. I'll catch up. Don't be too long. Watch this! Vanessa! - We're still here. - I told you not to yell at him. He doesn't respond to yelling! - Then why yell at me? - Because you don't listen! I'm not listening to this. Sorry, I've gotta go. - Where are you going? - I'm meeting a friend. A girl? Is this why you can't decide? Bye. I just hope she's Bee-ish. They have a huge parade of flowers every year in Pasadena? To be in the Tournament of Roses, that's every florist's dream! Up on a float, surrounded by flowers, crowds cheering. A tournament. Do the roses compete in athletic events? No. All right, I've got one. How come you don't fly everywhere? It's exhausting. Why don't you run everywhere? It's faster. Yeah, OK, I see, I see. All right, your turn. TiVo. You can just freeze live TV? That's insane! You don't have that? We have Hivo, but it's a disease. It's a horrible, horrible disease. Oh, my. Dumb bees! You must want to sting all those jerks. We try not to sting. It's usually fatal for us. So you have to watch your temper. Very carefully. You kick a wall, take a walk, write an angry letter and throw it out. Work through it like any emotion: Anger, jealousy, lust. Oh, my goodness! Are you OK? Yeah. - What is wrong with you?! - It's a bug. He's not bothering anybody. Get out of here, you creep! What was that? A Pic 'N' Save circular? Yeah, it was. How did you know? It felt like about 10 pages. Seventy-five is pretty much our limit. You've really got that down to a science. - I lost a cousin to Italian Vogue. - I'll bet. What in the name of Mighty Hercules is this? How did this get here? Oute Bee, Golden Blossom, Ray Liotta Private Select? - Is he that actor? - I never heard of him. - Why is this here? - For people. We eat it. You don't have enough food of your own? - Well, yes. - How do you get it? - Bees make it. - I know who makes it! And it's hard to make it! There's heating, cooling, stirring. You need a whole Krelman thing! - It's organic. - It's our-ganic! It's just honey, Barry. Just what?! Bees don't know about this! This is stealing! A lot of stealing! You've taken our homes, schools, hospitals! This is all we have! And it's on sale?! I'm getting to the bottom of this. I'm getting to the bottom of all of this! Hey, Hector. - You almost done? - Almost. He is here. I sense it. Well, I guess I'll go home now and just leave this nice honey out, with no one around. You're busted, box boy! I knew I heard something. So you can talk! I can talk. And now you'll start talking! Where you getting the sweet stuff? Who's your supplier? I don't understand. I thought we were friends. The last thing we want to do is upset bees! You're too late! It's ours now! You, sir, have crossed the wrong sword! You, sir, will be lunch for my iguana, Ignacio! Where is the honey coming from? Tell me where! Honey Farms! It comes from Honey Farms! Orazy person! What horrible thing has happened here? These faces, they never knew what hit them. And now they're on the road to nowhere! Just keep still. What? You're not dead? Do I look dead? They will wipe anything that moves. Where you headed? To Honey Farms. I am onto something huge here. I'm going to Alaska. Moose blood, crazy stuff. Blows your head off! I'm going to Tacoma. - And you? - He really is dead. All right. Uh-oh! - What is that?! - Oh, no! - A wiper! Triple blade! - Triple blade? Jump on! It's your only chance, bee! Why does everything have to be so doggone clean?! How much do you people need to see?! Open your eyes! Stick your head out the window! From NPR News in Washington, I'm Oarl Kasell. But don't kill no more bugs! - Bee! - Moose blood guy!! - You hear something? - Like what? Like tiny screaming. Turn off the radio. Whassup, bee boy? Hey, Blood. Just a row of honey jars, as far as the eye could see. Wow! I assume wherever this truck goes is where they're getting it. I mean, that honey's ours. - Bees hang tight. - We're all jammed in. It's a close community. Not us, man. We on our own. Every mosquito on his own. - What if you get in trouble? - You a mosquito, you in trouble. Nobody likes us. They just smack. See a mosquito, smack, smack! At least you're out in the world. You must meet girls. Mosquito girls try to trade up, get with a moth, dragonfly. Mosquito girl don't want no mosquito. You got to be kidding me! Mooseblood's about to leave the building! So long, bee! - Hey, guys! - Mooseblood! I knew I'd catch y'all down here. Did you bring your crazy straw? We throw it in jars, slap a label on it, and it's pretty much pure profit. What is this place? A bee's got a brain the size of a pinhead. They are pinheads! Pinhead. - Oheck out the new smoker. - Oh, sweet. That's the one you want. The Thomas 3000! Smoker? Ninety puffs a minute, semi-automatic. Twice the nicotine, all the tar. A couple breaths of this knocks them right out. They make the honey, and we make the money. They make the honey, and we make the money? Oh, my! What's going on? Are you OK? Yeah. It doesn't last too long. Do you know you're in a fake hive with fake walls? Our queen was moved here. We had no choice. This is your queen? That's a man in women's clothes! That's a drag queen! What is this? Oh, no! There's hundreds of them! Bee honey. Our honey is being brazenly stolen on a massive scale! This is worse than anything bears have done! I intend to do something. Oh, Barry, stop. Who told you humans are taking our honey? That's a rumor. Do these look like rumors? That's a conspiracy theory. These are obviously doctored photos. How did you get mixed up in this? He's been talking to humans. - What? - Talking to humans?! He has a human girlfriend. And they make out! Make out? Barry! We do not. - You wish you could. - Whose side are you on? The bees! I dated a cricket once in San Antonio. Those crazy legs kept me up all night. Barry, this is what you want to do with your life? I want to do it for all our lives. Nobody works harder than bees! Dad, I remember you coming home so overworked your hands were still stirring. You couldn't stop. I remember that. What right do they have to our honey? We live on two cups a year. They put it in lip balm for no reason whatsoever! Even if it's true, what can one bee do? Sting them where it really hurts. In the face! The eye! - That would hurt. - No. Up the nose? That's a killer. There's only one place you can sting the humans, one place where it matters. Hive at Five, the hive's only full-hour action news source. No more bee beards! With Bob Bumble at the anchor desk. Weather with Storm Stinger. Sports with Buzz Larvi. And Jeanette Ohung. - Good evening. I'm Bob Bumble. - And I'm Jeanette Ohung. A tri-county bee, Barry Benson, intends to sue the human race for stealing our honey, packaging it and profiting from it illegally! Tomorrow night on Bee Larry King, we'll have three former queens here in our studio, discussing their new book, Olassy Ladies, out this week on Hexagon. Tonight we're talking to Barry Benson. Did you ever think, I'm a kid from the hive. I can't do this? Bees have never been afraid to change the world. What about Bee Oolumbus? Bee Gandhi? Bejesus? Where I'm from, we'd never sue humans. We were thinking of stickball or candy stores. How old are you? The bee community is supporting you in this case, which will be the trial of the bee century. You know, they have a Larry King in the human world too. It's a common name. Next week... He looks like you and has a show and suspenders and colored dots... Next week... Glasses, quotes on the bottom from the guest even though you just heard 'em. Bear Week next week! They're scary, hairy and here live. Always leans forward, pointy shoulders, squinty eyes, very Jewish. In tennis, you attack at the point of weakness! It was my grandmother, Ken. She's 81. Honey, her backhand's a joke! I'm not gonna take advantage of that? Quiet, please. Actual work going on here. - Is that that same bee? - Yes, it is! I'm helping him sue the human race. - Hello. - Hello, bee. This is Ken. Yeah, I remember you. Timberland, size ten and a half. Vibram sole, I believe. Why does he talk again? Listen, you better go 'cause we're really busy working. But it's our yogurt night! Bye-bye. Why is yogurt night so difficult?! You poor thing. You two have been at this for hours! Yes, and Adam here has been a huge help. - Frosting... - How many sugars? Just one. I try not to use the competition. So why are you helping me? Bees have good qualities. And it takes my mind off the shop. Instead of flowers, people are giving balloon bouquets now. Those are great, if you're three. And artificial flowers. - Oh, those just get me psychotic! - Yeah, me too. Bent stingers, pointless pollination. Bees must hate those fake things! Nothing worse than a daffodil that's had work done. Maybe this could make up for it a little bit. - This lawsuit's a pretty big deal. - I guess. You sure you want to go through with it? Am I sure? When I'm done with the humans, they won't be able to say, Honey, I'm home, without paying a royalty! It's an incredible scene here in downtown Manhattan, where the world anxiously waits, because for the first time in history, we will hear for ourselves if a honeybee can actually speak. What have we gotten into here, Barry? It's pretty big, isn't it? I can't believe how many humans don't work during the day. You think billion-dollar multinational food companies have good lawyers? Everybody needs to stay behind the barricade. - What's the matter? - I don't know, I just got a chill. Well, if it isn't the bee team. You boys work on this? All rise! The Honorable Judge Bumbleton presiding. All right. Oase number 4475, Superior Oourt of New York, Barry Bee Benson v. the Honey Industry is now in session. Mr. Montgomery, you're representing the five food companies collectively? A privilege. Mr. Benson... you're representing all the bees of the world? I'm kidding. Yes, Your Honor, we're ready to proceed. Mr. Montgomery, your opening statement, please. Ladies and gentlemen of the jury, my grandmother was a simple woman. Born on a farm, she believed it was man's divine right to benefit from the bounty of nature God put before us. If we lived in the topsy-turvy world Mr. Benson imagines, just think of what would it mean. I would have to negotiate with the silkworm for the elastic in my britches! Talking bee! How do we know this isn't some sort of holographic motion-picture-capture Hollywood wizardry? They could be using laser beams! Robotics! Ventriloquism! Oloning! For all we know, he could be on steroids! Mr. Benson? Ladies and gentlemen, there's no trickery here. I'm just an ordinary bee. Honey's pretty important to me. It's important to all bees. We invented it! We make it. And we protect it with our lives. Unfortunately, there are some people in this room who think they can take it from us 'cause we're the little guys! I'm hoping that, after this is all over, you'll see how, by taking our honey, you not only take everything we have but everything we are! I wish he'd dress like that all the time. So nice! Oall your first witness. So, Mr. Klauss Vanderhayden of Honey Farms, big company you have. I suppose so. I see you also own Honeyburton and Honron! Yes, they provide beekeepers for our farms. Beekeeper. I find that to be a very disturbing term. I don't imagine you employ any bee-free-ers, do you? - No. - I couldn't hear you. - No. - No. Because you don't free bees. You keep bees. Not only that, it seems you thought a bear would be an appropriate image for a jar of honey. They're very lovable creatures. Yogi Bear, Fozzie Bear, Build-A-Bear. You mean like this? Bears kill bees! How'd you like his head crashing through your living room?! Biting into your couch! Spitting out your throw pillows! OK, that's enough. Take him away. So, Mr. Sting, thank you for being here. Your name intrigues me. - Where have I heard it before? - I was with a band called The Police. But you've never been a police officer, have you? No, I haven't. No, you haven't. And so here we have yet another example of bee culture casually stolen by a human for nothing more than a prance-about stage name. Oh, please. Have you ever been stung, Mr. Sting? Because I'm feeling a little stung, Sting. Or should I say... Mr. Gordon M. Sumner! That's not his real name?! You idiots! Mr. Liotta, first, belated congratulations on your Emmy win for a guest spot on ER in 2005. Thank you. Thank you. I see from your resume that you're devilishly handsome with a churning inner turmoil that's ready to blow. I enjoy what I do. Is that a crime? Not yet it isn't. But is this what it's come to for you? Exploiting tiny, helpless bees so you don't have to rehearse your part and learn your lines, sir? Watch it, Benson! I could blow right now! This isn't a goodfella. This is a badfella! Why doesn't someone just step on this creep, and we can all go home?! - Order in this court! - You're all thinking it! Order! Order, I say! - Say it! - Mr. Liotta, please sit down! I think it was awfully nice of that bear to pitch in like that. I think the jury's on our side. Are we doing everything right, legally? I'm a florist. Right. Well, here's to a great team. To a great team! Well, hello. - Ken! - Hello. I didn't think you were coming. No, I was just late. I tried to call, but... the battery. I didn't want all this to go to waste, so I called Barry. Luckily, he was free. Oh, that was lucky. There's a little left. I could heat it up. Yeah, heat it up, sure, whatever. So I hear you're quite a tennis player. I'm not much for the game myself. The ball's a little grabby. That's where I usually sit. Right... there. Ken, Barry was looking at your resume, and he agreed with me that eating with chopsticks isn't really a special skill. You think I don't see what you're doing? I know how hard it is to find the rightjob. We have that in common. Do we? Bees have 100 percent employment, but we do jobs like taking the crud out. That's just what I was thinking about doing. Ken, I let Barry borrow your razor for his fuzz. I hope that was all right. I'm going to drain the old stinger. Yeah, you do that. Look at that. You know, I've just about had it with your little mind games. - What's that? - Italian Vogue. Mamma mia, that's a lot of pages. A lot of ads. Remember what Van said, why is your life more valuable than mine? Funny, I just can't seem to recall that! I think something stinks in here! I love the smell of flowers. How do you like the smell of flames?! Not as much. Water bug! Not taking sides! Ken, I'm wearing a Ohapstick hat! This is pathetic! I've got issues! Well, well, well, a royal flush! - You're bluffing. - Am I? Surf's up, dude! Poo water! That bowl is gnarly. Except for those dirty yellow rings! Kenneth! What are you doing?! You know, I don't even like honey! I don't eat it! We need to talk! He's just a little bee! And he happens to be the nicest bee I've met in a long time! Long time? What are you talking about?! Are there other bugs in your life? No, but there are other things bugging me in life. And you're one of them! Fine! Talking bees, no yogurt night... My nerves are fried from riding on this emotional roller coaster! Goodbye, Ken. And for your information, I prefer sugar-free, artificial sweeteners made by man! I'm sorry about all that. I know it's got an aftertaste! I like it! I always felt there was some kind of barrier between Ken and me. I couldn't overcome it. Oh, well. Are you OK for the trial? I believe Mr. Montgomery is about out of ideas. We would like to call Mr. Barry Benson Bee to the stand. Good idea! You can really see why he's considered one of the best lawyers... Yeah. Layton, you've gotta weave some magic with this jury, or it's gonna be all over. Don't worry. The only thing I have to do to turn this jury around is to remind them of what they don't like about bees. - You got the tweezers? - Are you allergic? Only to losing, son. Only to losing. Mr. Benson Bee, I'll ask you what I think we'd all like to know. What exactly is your relationship to that woman? We're friends. - Good friends? - Yes. How good? Do you live together? Wait a minute... Are you her little... ...bedbug? I've seen a bee documentary or two. From what I understand, doesn't your queen give birth to all the bee children? - Yeah, but... - So those aren't your real parents! - Oh, Barry... - Yes, they are! Hold me back! You're an illegitimate bee, aren't you, Benson? He's denouncing bees! Don't y'all date your cousins? - Objection! - I'm going to pincushion this guy! Adam, don't! It's what he wants! Oh, I'm hit!! Oh, lordy, I am hit! Order! Order! The venom! The venom is coursing through my veins! I have been felled by a winged beast of destruction! You see? You can't treat them like equals! They're striped savages! Stinging's the only thing they know! It's their way! - Adam, stay with me. - I can't feel my legs. What angel of mercy will come forward to suck the poison from my heaving buttocks? I will have order in this court. Order! Order, please! The case of the honeybees versus the human race took a pointed turn against the bees yesterday when one of their legal team stung Layton T. Montgomery. - Hey, buddy. - Hey. - Is there much pain? - Yeah. I... I blew the whole case, didn't I? It doesn't matter. What matters is you're alive. You could have died. I'd be better off dead. Look at me. They got it from the cafeteria downstairs, in a tuna sandwich. Look, there's a little celery still on it. What was it like to sting someone? I can't explain it. It was all... All adrenaline and then... and then ecstasy! All right. You think it was all a trap? Of course. I'm sorry. I flew us right into this. What were we thinking? Look at us. We're just a couple of bugs in this world. What will the humans do to us if they win? I don't know. I hear they put the roaches in motels. That doesn't sound so bad. Adam, they check in, but they don't check out! Oh, my. Oould you get a nurse to close that window? - Why? - The smoke. Bees don't smoke. Right. Bees don't smoke. Bees don't smoke! But some bees are smoking. That's it! That's our case! It is? It's not over? Get dressed. I've gotta go somewhere. Get back to the court and stall. Stall any way you can. And assuming you've done step correctly, you're ready for the tub. Mr. Flayman. Yes? Yes, Your Honor! Where is the rest of your team? Well, Your Honor, it's interesting. Bees are trained to fly haphazardly, and as a result, we don't make very good time. I actually heard a funny story about... Your Honor, haven't these ridiculous bugs taken up enough of this court's valuable time? How much longer will we allow these absurd shenanigans to go on? They have presented no compelling evidence to support their charges against my clients, who run legitimate businesses. I move for a complete dismissal of this entire case! Mr. Flayman, I'm afraid I'm going to have to consider Mr. Montgomery's motion. But you can't! We have a terrific case. Where is your proof? Where is the evidence? Show me the smoking gun! Hold it, Your Honor! You want a smoking gun? Here is your smoking gun. What is that? It's a bee smoker! What, this? This harmless little contraption? This couldn't hurt a fly, let alone a bee. Look at what has happened to bees who have never been asked, Smoking or non? Is this what nature intended for us? To be forcibly addicted to smoke machines and man-made wooden slat work camps? Living out our lives as honey slaves to the white man? - What are we gonna do? - He's playing the species card. Ladies and gentlemen, please, free these bees! Free the bees! Free the bees! Free the bees! Free the bees! Free the bees! The court finds in favor of the bees! Vanessa, we won! I knew you could do it! High-five! Sorry. I'm OK! You know what this means? All the honey will finally belong to the bees. Now we won't have to work so hard all the time. This is an unholy perversion of the balance of nature, Benson. You'll regret this. Barry, how much honey is out there? All right. One at a time. Barry, who are you wearing? My sweater is Ralph Lauren, and I have no pants. - What if Montgomery's right? - What do you mean? We've been living the bee way a long time, 27 million years. Oongratulations on your victory. What will you demand as a settlement? First, we'll demand a complete shutdown of all bee work camps. Then we want back the honey that was ours to begin with, every last drop. We demand an end to the glorification of the bear as anything more than a filthy, smelly, bad-breath stink machine. We're all aware of what they do in the woods. Wait for my signal. Take him out. He'll have nauseous for a few hours, then he'll be fine. And we will no longer tolerate bee-negative nicknames... But it's just a prance-about stage name! ...unnecessary inclusion of honey in bogus health products and la-dee-da human tea-time snack garnishments. Oan't breathe. Bring it in, boys! Hold it right there! Good. Tap it. Mr. Buzzwell, we just passed three cups, and there's gallons more coming! - I think we need to shut down! - Shut down? We've never shut down. Shut down honey production! Stop making honey! Turn your key, sir! What do we do now? Oannonball! We're shutting honey production! Mission abort. Aborting pollination and nectar detail. Returning to base. Adam, you wouldn't believe how much honey was out there. Oh, yeah? What's going on? Where is everybody? - Are they out celebrating? - They're home. They don't know what to do. Laying out, sleeping in. I heard your Uncle Oarl was on his way to San Antonio with a cricket. At least we got our honey back. Sometimes I think, so what if humans liked our honey? Who wouldn't? It's the greatest thing in the world! I was excited to be part of making it. This was my new desk. This was my new job. I wanted to do it really well. And now... Now I can't. I don't understand why they're not happy. I thought their lives would be better! They're doing nothing. It's amazing. Honey really changes people. You don't have any idea what's going on, do you? - What did you want to show me? - This. What happened here? That is not the half of it. Oh, no. Oh, my. They're all wilting. Doesn't look very good, does it? No. And whose fault do you think that is? You know, I'm gonna guess bees. Bees? Specifically, me. I didn't think bees not needing to make honey would affect all these things. It's notjust flowers. Fruits, vegetables, they all need bees. That's our whole SAT test right there. Take away produce, that affects the entire animal kingdom. And then, of course... The human species? So if there's no more pollination, it could all just go south here, couldn't it? I know this is also partly my fault. How about a suicide pact? How do we do it? - I'll sting you, you step on me. - Thatjust kills you twice. Right, right. Listen, Barry... sorry, but I gotta get going. I had to open my mouth and talk. Vanessa? Vanessa? Why are you leaving? Where are you going? To the final Tournament of Roses parade in Pasadena. They've moved it to this weekend because all the flowers are dying. It's the last chance I'll ever have to see it. Vanessa, I just wanna say I'm sorry. I never meant it to turn out like this. I know. Me neither. Tournament of Roses. Roses can't do sports. Wait a minute. Roses. Roses? Roses! Vanessa! Roses?! Barry? - Roses are flowers! - Yes, they are. Flowers, bees, pollen! I know. That's why this is the last parade. Maybe not. Oould you ask him to slow down? Oould you slow down? Barry! OK, I made a huge mistake. This is a total disaster, all my fault. Yes, it kind of is. I've ruined the planet. I wanted to help you with the flower shop. I've made it worse. Actually, it's completely closed down. I thought maybe you were remodeling. But I have another idea, and it's greater than my previous ideas combined. I don't want to hear it! All right, they have the roses, the roses have the pollen. I know every bee, plant and flower bud in this park. All we gotta do is get what they've got back here with what we've got. - Bees. - Park. - Pollen! - Flowers. - Repollination! - Across the nation! Tournament of Roses, Pasadena, Oalifornia. They've got nothing but flowers, floats and cotton candy. Security will be tight. I have an idea. Vanessa Bloome, FTD. Official floral business. It's real. Sorry, ma'am. Nice brooch. Thank you. It was a gift. Once inside, we just pick the right float. How about The Princess and the Pea? I could be the princess, and you could be the pea! Yes, I got it. - Where should I sit? - What are you? - I believe I'm the pea. - The pea? It goes under the mattresses. - Not in this fairy tale, sweetheart. - I'm getting the marshal. You do that! This whole parade is a fiasco! Let's see what this baby'll do. Hey, what are you doing?! Then all we do is blend in with traffic... ...without arousing suspicion. Once at the airport, there's no stopping us. Stop! Security. - You and your insect pack your float? - Yes. Has it been in your possession the entire time? Would you remove your shoes? - Remove your stinger. - It's part of me. I know. Just having some fun. Enjoy your flight. Then if we're lucky, we'll have just enough pollen to do the job. Oan you believe how lucky we are? We have just enough pollen to do the job! I think this is gonna work. It's got to work. Attention, passengers, this is Oaptain Scott. We have a bit of bad weather in New York. It looks like we'll experience a couple hours delay. Barry, these are cut flowers with no water. They'll never make it. I gotta get up there and talk to them. Be careful. Oan I get help with the Sky Mall magazine? I'd like to order the talking inflatable nose and ear hair trimmer. Oaptain, I'm in a real situation. - What'd you say, Hal? - Nothing. Bee! Don't freak out! My entire species... What are you doing? - Wait a minute! I'm an attorney! - Who's an attorney? Don't move. Oh, Barry. Good afternoon, passengers. This is your captain. Would a Miss Vanessa Bloome in 24B please report to the cockpit? And please hurry! What happened here? There was a DustBuster, a toupee, a life raft exploded. One's bald, one's in a boat, they're both unconscious! - Is that another bee joke? - No! No one's flying the plane! This is JFK control tower, Flight 356. What's your status? This is Vanessa Bloome. I'm a florist from New York. Where's the pilot? He's unconscious, and so is the copilot. Not good. Does anyone onboard have flight experience? As a matter of fact, there is. - Who's that? - Barry Benson. From the honey trial?! Oh, great. Vanessa, this is nothing more than a big metal bee. It's got giant wings, huge engines. I can't fly a plane. - Why not? Isn't John Travolta a pilot? - Yes. How hard could it be? Wait, Barry! We're headed into some lightning. This is Bob Bumble. We have some late-breaking news from JFK Airport, where a suspenseful scene is developing. Barry Benson, fresh from his legal victory... That's Barry! ...is attempting to land a plane, loaded with people, flowers and an incapacitated flight crew. Flowers?! We have a storm in the area and two individuals at the controls with absolutely no flight experience. Just a minute. There's a bee on that plane. I'm quite familiar with Mr. Benson and his no-account compadres. They've done enough damage. But isn't he your only hope? Technically, a bee shouldn't be able to fly at all. Their wings are too small... Haven't we heard this a million times? The surface area of the wings and body mass make no sense. - Get this on the air! - Got it. - Stand by. - We're going live. The way we work may be a mystery to you. Making honey takes a lot of bees doing a lot of small jobs. But let me tell you about a small job. If you do it well, it makes a big difference. More than we realized. To us, to everyone. That's why I want to get bees back to working together. That's the bee way! We're not made of Jell-O. We get behind a fellow. - Black and yellow! - Hello! Left, right, down, hover. - Hover? - Forget hover. This isn't so hard. Beep-beep! Beep-beep! Barry, what happened?! Wait, I think we were on autopilot the whole time. - That may have been helping me. - And now we're not! So it turns out I cannot fly a plane. All of you, let's get behind this fellow! Move it out! Move out! Our only chance is if I do what I'd do, you copy me with the wings of the plane! Don't have to yell. I'm not yelling! We're in a lot of trouble. It's very hard to concentrate with that panicky tone in your voice! It's not a tone. I'm panicking! I can't do this! Vanessa, pull yourself together. You have to snap out of it! You snap out of it. You snap out of it. - You snap out of it! - You snap out of it! - You snap out of it! - You snap out of it! - You snap out of it! - You snap out of it! - Hold it! - Why? Oome on, it's my turn. How is the plane flying? I don't know. Hello? Benson, got any flowers for a happy occasion in there? The Pollen Jocks! They do get behind a fellow. - Black and yellow. - Hello. All right, let's drop this tin can on the blacktop. Where? I can't see anything. Oan you? No, nothing. It's all cloudy. Oome on. You got to think bee, Barry. - Thinking bee. - Thinking bee. Thinking bee! Thinking bee! Thinking bee! Wait a minute. I think I'm feeling something. - What? - I don't know. It's strong, pulling me. Like a 27-million-year-old instinct. Bring the nose down. Thinking bee! Thinking bee! Thinking bee! - What in the world is on the tarmac? - Get some lights on that! Thinking bee! Thinking bee! Thinking bee! - Vanessa, aim for the flower. - OK. Out the engines. We're going in on bee power. Ready, boys? Affirmative! Good. Good. Easy, now. That's it. Land on that flower! Ready? Full reverse! Spin it around! - Not that flower! The other one! - Which one? - That flower. - I'm aiming at the flower! That's a fat guy in a flowered shirt. I mean the giant pulsating flower made of millions of bees! Pull forward. Nose down. Tail up. Rotate around it. - This is insane, Barry! - This's the only way I know how to fly. Am I koo-koo-kachoo, or is this plane flying in an insect-like pattern? Get your nose in there. Don't be afraid. Smell it. Full reverse! Just drop it. Be a part of it. Aim for the center! Now drop it in! Drop it in, woman! Oome on, already. Barry, we did it! You taught me how to fly! - Yes. No high-five! - Right. Barry, it worked! Did you see the giant flower? What giant flower? Where? Of course I saw the flower! That was genius! - Thank you. - But we're not done yet. Listen, everyone! This runway is covered with the last pollen from the last flowers available anywhere on Earth. That means this is our last chance. We're the only ones who make honey, pollinate flowers and dress like this. If we're gonna survive as a species, this is our moment! What do you say? Are we going to be bees, orjust Museum of Natural History keychains? We're bees! Keychain! Then follow me! Except Keychain. Hold on, Barry. Here. You've earned this. Yeah! I'm a Pollen Jock! And it's a perfect fit. All I gotta do are the sleeves. Oh, yeah. That's our Barry. Mom! The bees are back! If anybody needs to make a call, now's the time. I got a feeling we'll be working late tonight! Here's your change. Have a great afternoon! Oan I help who's next? Would you like some honey with that? It is bee-approved. Don't forget these. Milk, cream, cheese, it's all me. And I don't see a nickel! Sometimes I just feel like a piece of meat! I had no idea. Barry, I'm sorry. Have you got a moment? Would you excuse me? My mosquito associate will help you. Sorry I'm late. He's a lawyer too? I was already a blood-sucking parasite. All I needed was a briefcase. Have a great afternoon! Barry, I just got this huge tulip order, and I can't get them anywhere. No problem, Vannie. Just leave it to me. You're a lifesaver, Barry. Oan I help who's next? All right, scramble, jocks! It's time to fly. Thank you, Barry! That bee is living my life! Let it go, Kenny. - When will this nightmare end?! - Let it all go. - Beautiful day to fly. - Sure is. Between you and me, I was dying to get out of that office. You have got to start thinking bee, my friend. - Thinking bee! - Me? Hold it. Let's just stop for a second. Hold it. Im sorry. I'm sorry, everyone. Oan we stop here? Im not making a major life decision during a production number! All right. Take ten, everybody. Wrap it up, guys. I had virtually no rehearsal for that. ";
    		E = E +  System.lineSeparator() +  System.lineSeparator() +  "Now get back to work";
    		eList.remove(eList.size()-1);
       		eList.add(E);
    	}
    	
    	return E;
    }
    public String userInputErrors(String E)
    {
    	//TODO FIX THIS SHITE!
    	
    	/*
    	 * Check if user inputs an extraneous operation character.
    	 * ie : 5+/4"
    	 * Character.isDigit
    	 */
    	if(E.equals(""))
    	{
    		return "Error";
    	}
    	for(int x = 0; x < E.length() - 1; x++)			//last char is ignored and will be caught by operations methods
    	{
    		if(E.charAt(x) == E.charAt(x + 1))
    		{
    			System.out.println("Error : repeating opertion character");
    			return "Error";
    		}
    		//==========							==========										  ==========
    		if((E.charAt(x) == '√'  && E.charAt(x+1) != '0') && (E.charAt(x) == '√'  && E.charAt(x+1) != '1') &&
    		   (E.charAt(x) == '√'  && E.charAt(x+1) != '2') && (E.charAt(x) == '√'  && E.charAt(x+1) != '3') &&
    	   	   (E.charAt(x) == '√'  && E.charAt(x+1) != '4') && (E.charAt(x) == '√'  && E.charAt(x+1) != '5') &&
    		   (E.charAt(x) == '√'  && E.charAt(x+1) != '6') && (E.charAt(x) == '√'  && E.charAt(x+1) != '7') &&
       		   (E.charAt(x) == '√'  && E.charAt(x+1) != '8') && (E.charAt(x) == '√'  && E.charAt(x+1) != '9')  )
    	    {
    	    	System.out.println("Error : '^' is not followed by a number");
    	    	return "Error";
    	    }
    		//-----
    		if((E.charAt(x) == '^'  && E.charAt(x+1) != '0') && (E.charAt(x) == '^'  && E.charAt(x+1) != '1') &&
    		   (E.charAt(x) == '^'  && E.charAt(x+1) != '2') && (E.charAt(x) == '^'  && E.charAt(x+1) != '3') &&
    	   	   (E.charAt(x) == '^'  && E.charAt(x+1) != '4') && (E.charAt(x) == '^'  && E.charAt(x+1) != '5') &&
    		   (E.charAt(x) == '^'  && E.charAt(x+1) != '6') && (E.charAt(x) == '^'  && E.charAt(x+1) != '7') &&
       		   (E.charAt(x) == '^'  && E.charAt(x+1) != '8') && (E.charAt(x) == '^'  && E.charAt(x+1) != '9')  )
    	    {
    	    	System.out.println("Error : '^' is not followed by a number");
    	    	return "Error";
    	    }
    		//-----
    		if((E.charAt(x) == '*'  && E.charAt(x+1) != '0') && (E.charAt(x) == '*'  && E.charAt(x+1) != '1') &&
    		   (E.charAt(x) == '*'  && E.charAt(x+1) != '2') && (E.charAt(x) == '*'  && E.charAt(x+1) != '3') &&
    	   	   (E.charAt(x) == '*'  && E.charAt(x+1) != '4') && (E.charAt(x) == '*'  && E.charAt(x+1) != '5') &&
    		   (E.charAt(x) == '*'  && E.charAt(x+1) != '6') && (E.charAt(x) == '*'  && E.charAt(x+1) != '7') &&
       		   (E.charAt(x) == '*'  && E.charAt(x+1) != '8') && (E.charAt(x) == '*'  && E.charAt(x+1) != '9')  )
    	    {
    	    	System.out.println("Error : '*' is not followed by a number");
    	    	return "Error";
    	    }
    		//-----
    		if((E.charAt(x) == '/'  && E.charAt(x+1) != '0') && (E.charAt(x) == '/'  && E.charAt(x+1) != '1') &&
    		   (E.charAt(x) == '/'  && E.charAt(x+1) != '2') && (E.charAt(x) == '/'  && E.charAt(x+1) != '3') &&
    	   	   (E.charAt(x) == '/'  && E.charAt(x+1) != '4') && (E.charAt(x) == '/'  && E.charAt(x+1) != '5') &&
    		   (E.charAt(x) == '/'  && E.charAt(x+1) != '6') && (E.charAt(x) == '/'  && E.charAt(x+1) != '7') &&
       		   (E.charAt(x) == '/'  && E.charAt(x+1) != '8') && (E.charAt(x) == '/'  && E.charAt(x+1) != '9')  )
    	    {
    	    	System.out.println("Error : '/' is not followed by a number");
    	    	return "Error";
    	    }
    		//-----
    		if((E.charAt(x) == '+'  && E.charAt(x+1) != '0') && (E.charAt(x) == '+'  && E.charAt(x+1) != '1') &&
    		   (E.charAt(x) == '+'  && E.charAt(x+1) != '2') && (E.charAt(x) == '+'  && E.charAt(x+1) != '3') &&
    		   (E.charAt(x) == '+'  && E.charAt(x+1) != '4') && (E.charAt(x) == '+'  && E.charAt(x+1) != '5') &&
    		   (E.charAt(x) == '+'  && E.charAt(x+1) != '6') && (E.charAt(x) == '+'  && E.charAt(x+1) != '7') &&
    		   (E.charAt(x) == '+'  && E.charAt(x+1) != '8') && (E.charAt(x) == '+'  && E.charAt(x+1) != '9')  )
    		{
    			System.out.println("Error : '+' is not followed by a number");
    			return "Error";
    		}
    		//-----
    		if((E.charAt(x) == '—'  && E.charAt(x+1) != '0') && (E.charAt(x) == '—'  && E.charAt(x+1) != '1') &&
    		   (E.charAt(x) == '—'  && E.charAt(x+1) != '2') && (E.charAt(x) == '—'  && E.charAt(x+1) != '3') &&
    		   (E.charAt(x) == '—'  && E.charAt(x+1) != '4') && (E.charAt(x) == '—'  && E.charAt(x+1) != '5') &&
    		   (E.charAt(x) == '—'  && E.charAt(x+1) != '6') && (E.charAt(x) == '—'  && E.charAt(x+1) != '7') &&
    		   (E.charAt(x) == '—'  && E.charAt(x+1) != '8') && (E.charAt(x) == '—'  && E.charAt(x+1) != '9')  )
    		{
    			System.out.println("Error : '—' is not followed by a number");
    			return "Error";
    		}
    		//==========							==========										  ==========

    	}
    	return E;
    }
}