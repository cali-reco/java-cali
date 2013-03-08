package pt.inevo.jcali;
/**
 * Base class for all commands.
 */

public abstract class CICommand extends CIGesture {
	
	public CICommand () {
		_sc = null; 
	}
	
	public abstract String getName();
	
	public String getGestureType() { 
		return "Command"; 
	}
	
	public double evalLocalFeatures(CIScribble sc, CIList _shapesList) { 
		return 1; 
	}
	
	/**
	 * Computes the degree of membership for the scribble, taking
	 * into account the fuzzysets for the current command.
	 * This evaluation is made based on global features.
	 * 
	 * Notes: This method is the same for all commands.
	 * 
	 * @param sc A scribble
	 * @return degree of membership
	 */
	public double evalGlobalFeatures(CIScribble sc)
	{
		_dom = _features.evaluate(sc);
		if (_dom > 0) {
			_sc = sc;
		}
		else { 
			_sc = null;
		}
		return _dom;
	}
	
	/**
	 * Recovers the previous atributes of the command
	 */
	public void popAttribs()
	{
		if (_prevGesture != null) {
			_sc = _prevGesture.getScribble();
			_dom = _prevGesture.getDom();
			_prevGesture = null;
		}
	}

	public abstract Object clone(); 
}


