package pt.inevo.jcali;
/**
 * Linear representation of Fuzzy Sets.
 * A fuzzy set is represented by four values as we can see in the
 * next figure.
 * 
 *
 *
 *   N(u)
 *    |
 *  1 +            .+---+.                    a - wa <= a <= b <= b + wb
 *    |           /       \
 *  0 +----------+--+---+--+----------> u     0 <= N(u) <= 1 for all u
 *               ^  a   b  ^
 *               |         |                  wa >= 0, wb >= 0
 *             a - wa      b + wb
 *
 * Notes: This class was completely rebuild from the original one 
 *	 developed by Filipe Barreira and Paulo Vilar
 */

public class CIFuzzySet {
	public double _a;   // smallest u st N(u) = 1
	public double _b;   // biggest  u st N(u) = 1
	public double _wa;  // width of left slope (wa) st  N(a - wa) = 0
	public double _wb;  // width of right slope (wb) st N(b + wb) = 0
	
	// Constructors and destructors
	public CIFuzzySet (double a, double b) { 
		init(a, b, 0, 0); 
	}
	
	public CIFuzzySet (double a, double b, double wa, double wb) {
		init (a, b, wa, wb); 
	}
	
	/**
	 * Create a fuzzy set from 4 values
	 * Input:  a, b, wa, wb - Values that define the limits of the fuzzy set
	 */
	public void init (double a, double b, double wa, double wb)
	{
		if (checkFuzzySet(a, b, wa, wb)) {
			_a = a; _b = b;
			_wa = wa; _wb = wb;
		}
		else {
			// cerr << "Attempting to create invalid fuzzy set" << a << b << wa << wb << endl;
			//abort();
		}
	}
	
	/**
	 * Evaluate the degree of membership for a specific value
	 * @param value point of evaluation
	 * @return degree of membership
	 */
	public double degOfMember (double value)
	{
   		if ((value < (_a - _wa)) || (value > (_b + _wb)) ) {
			return 0;   
		}
		else if (value >= _a && value <= _b)
		{
			return 1;
		}
		else if (value > _b && (value <= _b + _wb)) {
			return 1.0 - (value - _b) / _wb;
		}
		else if (value < _a && (value >= _a - _wa)) {
			return 1.0 + (value - _a) / _wa;
		}
		else {
			// cerr << "Invalid fuzzy set" << endl;
			//abort();
			return 0.0;
		}
	}
	
	/**
	 * Evaluate the distance to the fuzzy set
	 * @param value point of evaluation
	 * @return distance
	 */
	public double distance (double value)
	{
		double low = _a - _wa; 
		double high = _b + _wb;
		
		if ( (value >= low) && (value <= high) ) {
			return 0;
		}
		else if (value < low) {
			return (low - value);
		}
		else {
			return (value - high);
		}
	}
		
	/**
	 * Verify if the current fuzzy set is intersected by the fuzzy set fs.
	 * @param fs a fuzzy set
	 * @return true if they intersect each other, false otherwise
	 */
	public boolean intersects (CIFuzzySet fs)
	{
		if (fs == null) {
			return false;
		}
		if ((fs._b + fs._wb) < (_a - _wa) || ((fs._a - fs._wa) > (_b + _wb))) {
			return false;
		}
		else {
			return true;
		}
	}
		
	/**
	 * Verify if the value belongs to the fuzzy set
	 * @param value
	 * @return true if it belongs, false otherwise
	 */
	public boolean isInSet (double value)
	{
		if (value < (_a - _wa) || (value > (_b + _wb))) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Check fuzzy set limits
	 * @return true if ok, false otherwise
	 */
	public boolean checkFuzzySet(double a, double b, double wa, double wb)
	{
		return (a <= b) && (wa >= 0) && (wb >= 0);
	}
}