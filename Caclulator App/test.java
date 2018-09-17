import java.io.IOException;

public class test {
	public static void main( String[] args )
	{
		System.out.println("sad");
		Calculator c = new Calculator();
		c.setEquation("sin(0)+cos(90)");					// put test here
		
		//TODO : 0 rounding error
		
		try {
			System.out.println("2134");
			
			System.out.println(c.cycle());
		} catch (IOException e) {
			System.out.println("some IO error");
			e.printStackTrace();
		}
	}
}
