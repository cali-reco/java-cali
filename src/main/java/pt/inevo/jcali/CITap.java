package pt.inevo.jcali;
/**
 * This class represents a Tap gesture.
 */
public class CITap extends CICommand {
	private CIPoint _point = new CIPoint();
	
	public CITap ()
	{
		_features = null;
		_point.x = 0;
		_point.y = 0;
	}
	
	public CITap (CIScribble sc, double dom, CIPoint point) {
		_sc = sc; 
		_dom = dom; 
		_point = point; 
		_features = null;
	}
	
	public Object clone() { 
		return new CITap(_sc, _dom, _point);
	}
	
	public String getName() { 
		return "Tap"; 
	}

	void setUp(CIScribble sc)
	{
		_point = sc.startingPoint();
		_dom = 1;
		_sc = sc;
	}
}
