import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorPrinter {
	public void write(String text)
	{
		try{
			File file = new File(System.getProperty("user.dir") + File.separator + "User" + 
		 			  			 File.separator + "Assets" + File.separator + "Error.txt");
			
			if(!file.exists())
			{ 
				file.createNewFile();
			}
		    PrintWriter writer = new PrintWriter(new FileWriter(file, true));
		    writer.append(text + System.lineSeparator());
		
		    writer.close();
		} catch (IOException e) {
			Errors a = new Errors(9);
			a.setVisible(true);
			
			e.printStackTrace();
		}
	}
	public void delete()
	{
		File file = new File(System.getProperty("user.dir") + File.separator + "User" + 
	 			  			 File.separator + "Assets" + File.separator + "Error.txt");
		
		if(file.exists())
		{
			//Deletes existing (called at each new input with exception of graphing)
			//file.delete();
		}
		if(file.exists())
			System.out.println("Error not deleted");
		if(!file.exists())
			System.out.println("success, Error.txt was reparsed");
	}
}



