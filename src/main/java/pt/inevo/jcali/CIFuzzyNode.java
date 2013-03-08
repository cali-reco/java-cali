package pt.inevo.jcali;

/**
 * This class represents a node which has a fuzzyset and the
 * feature (a function) associated to it. It provides a method to
 * compute the degree of membership.
 */
public class CIFuzzyNode {
	protected CIFuzzySet _fuzzySet;
	protected CIEvaluate _ptrFunc;
	
	public CIFuzzyNode (CIFuzzySet fuzzy, CIEvaluate ptrF)
	{
		_ptrFunc = ptrF;
		_fuzzySet = fuzzy;
	}
	
	/**
	 * Computes the degree of membership for the current scribble,
	 * using the fuzzyset and the function feature of the FuzzyNode.
	 * @param sc a scribble
	 * @return the value of the degree of membership
	 */    
	public double dom(CIScribble sc)
	{
		if (_ptrFunc != null) {
			Double d;
			
			d = _ptrFunc.evaluate(sc);
			
			if (_fuzzySet!=null) {
				return _fuzzySet.degOfMember(d);
			}
			else {
				return 0;
			}
		}
		else {
			return 0;
		}
	}
	
    public CIFuzzySet getFuzzySet() {
        return _fuzzySet;
    }

    public CIEvaluate getPtrFunc() {
        return _ptrFunc;
    }
}

