
public interface Methods {
	public boolean containsMethod( String E );
	
	String [][] methods = { 
			//Methods from methods( ) 
			//--------------------		Statistics 
			{"RNDInt", "(lower bound, upper bound, trials)", "Returns a random value given the boundries and trials", "rndint statistics number random"},
			{"RNDSeed", "(seed)", "Generates a random number from the given seed", "rndseed statistics number random"},
			{"RNDNum", "(trials)", "Generates a series of random numbers equal to the input trials", "rndnum statistics number random"},
			{"RNDNorm", "(mean, standard devation, trials)", "Creats a random normal sample", "rndnorm statistics number random"},
			{"normPDF", "(mean, standard devation, point)", "Returns the cumulative probablity left of the point in the normal distribution", "normpdf statistics number normal distribution"},
			{"normCDF", "(mean, standard devation, lower bound, upper bound)", "Returns the area between the upper and lower boundries in the normal distribution", "normcdf statistics number normal distribution"},
			{"invNorm", "(mean, standard devation, area)", "Returns the value that is equal to the given probablity", "invnorm normal inverse distribution statistics number"},
			
			{"tPDF", "(degrees of freedom, point)", "Returns the probablity density for the given point", "tpdf distribution statistics number"},
			{"tCDF", "(degrees of freedom, lower bound, upper bound)", "Returns the area between the upper and lower boundries in the t distribution", "tcdf distribution statistics number"},
			{"invT", "(area, degrees of freedom", "Returns the value that is equal to the given probablity", "invt inverse distribution statistics number"},
			
			{"binomPDF", "(trials, success probability, value", "_____________________", "binomcdf binomial distribution statistics number"},
			{"rndBinomial", "(trial, success probability, number of returns)", "Returns a frequency distribution of the possible number of successful outcomes in a given number of trials in each of which there is the same probability of success", "binomial distribution statistics random rndbinomail"},
				//Requires tables
			//{"x^2CDF", "()", "", ""},
			//{"x^2PDF", "()", "", ""},
			//{"invx^2", "()", "", ""},
			//--------------------		Geometry
			{"Triangle", "(base, height)","Finds the area of the triangle with the given dementions", "triangle geometry triangle area shape"},
					
			{"Rectangle", "(width, height)", "Finds the area of the rectangle with the given dementions", "rectangle geometry rectangle square area shape"},
			{"Parallelogram", "(width, height)", "Finds the area of the parallelogram with the given dementions", "parallelogram geometry area shape"},
			{"Circle", "(radius)", "Finds the area of the circle with demention radius", "circle geometry circle area shape"},
				{"CircleC", "(radius)", "Calculates the circumfrence of the given radius", "circle radius circumfrence geometry shape"},
				{"Sephere", "(radius)", "Calculates the volue of the sephere with the given radius", "cirlce vloume radius geometry shape"},
			{"Trapezoid", "(base1, base2, height)", "Finds the area of the trapezoid with the given dementions", "trapezoid geometry trapezoid circle area shape"},
			{"Square", "(width)", "Finds the area of the square with demention width", "square geometry square rectangle area shape"},
			//-------------------		Coordinate plane
			{"Distance", "(x1, y1, x2, y2)", "Calculates the distance between two points on a coordinate plane", "distance solve algebra geometry precalculus points coordinate plane"},
			{"Midpoint", "(x1, y1, x2, y2)", "Calculates the midpoint", "midpoint coordinate plane algebra geometry precalculus points"},
			{"Slope", "(x1, y1, x2, y2)", "Calculates the slope between two points on a coordinate plane", "slope points algebra geometry precalculus coordinate plane"},
			//-------------------		Algebra
			{"Quadratic", "(binomial)", "Solves the given binomal with the quadratic equation", "quadratic equation algebra equation binomial polynomial "},
			//-------------------		Trigonometry
			{"sin", "(degrees)", "Returns the ratio of the side opposite a given angle to the hypotenuse, in a right triangle", "trigonometry triangle sine degrees"},
			{"cos", "(degrees)", "Returns the ratio of the side adjacent a given angle to the hypotenuse, in a right triangle", "trigonometry triangle cosine degrees"},
			{"tan", "(degrees)", "Returns the ratio of the side opposite a given angle to the adjacent side, in a right triangle", "trigonometry triangle tangent degrees"},
			//---
			{"sin-1", "(ratio)", "Returns the degree that is equal to the ratio of the side opposite a given angle to the hypotenuse, in a right triangle", "trigonometry triangle inverse sine degrees sin-1"},
			{"cos-1", "(ratio)", "Returns the degree that is equal to the ratio of the side adjacent a given angle to the hypotenuse, in a right triangle", "trigonometry triangle inverse cocine degrees cos-1"},
			{"tan-1", "(ratio)", "Returns the degree that is equal to the ratio of the side opposite a given angle to the adjacent side, in a right triangle", "trigonometry triangle inverse tangent degrees tan-1"},
			//---
			{"sec", "(degrees)", "Returns the ratio of the hypotenuse to the shorter side adjacent to an acute angle; the reciprocal of cosine", "trigonometry triangle inverse secant degrees"},
			{"csc", "(degrees)", "Returns the ratio of the hypotenuse to the side opposite an acute angle; the reciprocal of sine", "trigonometry triangle inverse cosecant degrees"},
			{"cot", "(degrees)", "Returns the ratio of the side (other than the hypotenuse) adjacent to a particular acute angle to the side opposite the angle; the reciprocal of tangent",  "trigonometry cotriangle tangent degrees"},
			//---
				//TODO idk hard maths
			//{"sec-1},
			//{"csc-1},
			//{"cot-1},
			//-------------------		Misc. number
			{"C", "(integer 1, integer 2)", "Returns the number of combinations of the given integers", "ncr combinations number"},
			{"factorial!", "()!", "the product of an integer and all the integers below it. (Parentheses are not required but highly suggested in complex equations)", "product multiplication number"},
			{"ToFraction", "(decimal)", "Converts a decimal into a fraction", "tofraction number fraction algebra geometry statistics precalculus equation special"},
			{"Sum", "(number, ...)", "Returns the sum of the input numbers", "sum math addition"},
			{"Average", "(number, ...)", "Returns the average of all input numbers", "sum math average mean Σ sigma"},
			{"Σ", "(start, end, variable : operation)", "Returns the answer to the input equation within the given range (defult variable is 'n')", "Σ sigma sum avarge math equation algebra geometry statistics precalculus"},
			{"Sigma", "(start, end, variable : operation)", "Returns the answer to the input equation within the given range (defult variable is 'n')", "Σ sigma sum avarge math equation algebra geometry statistics precalculus"},
			{"Factor", "(value)", "Returns the factors of the given value", "number factor factorize math prime resolve"},
			{"LCM", "(value1, value2)", "Returns the lowest quantity that is a multiple of two or more given quantities", "number math lowest common multiple"},
			{"GCD", "(value1, value2)", "Returns the largest positive number that divides each of the integers", "number math greatest common divisor"},
			{"degToRad", "(degree)", "Converts the given degrees to radians", "math geometry statistics precalculus degtorad degrees radians convert"},
			{"radToDeg", "(radian)", "Converts the given radians to degrees", "math geometry statistics precalculus radtodeg degrees radians convert"},
			{"rectToPolar", "(x, y)", "Converts from rectangular coordinates to polar coordinates", "rectangular coordinates polar geometry algebra precalculus number convert"},
			{"polarToRect", "(r, θ)", "Converts from polar coordinates to rectangular coordinates", "rectangular coordinates polar geometry algebra precalculus number convert"},
			{"%", "(number)", "converts number into percent", "number math algebra statistics"},
			//-------------------		Operation type & syntax
			/*
			 * TODO add constant for method found loop conditional (line 443) that = number of methods before here																	 that contain (...)
			 */
		};		
}
