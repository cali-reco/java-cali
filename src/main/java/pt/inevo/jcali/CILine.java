package pt.inevo.jcali;
/**
 * Represents a line. (solid, dashed or bold)
 */

public class CILine extends CIShape {
	
	public CIPoint[] _points = new CIPoint[2];
	
	/**
	 * In this constructor we define all the features that are used 
	 * to identify lines. The set of features are different for
	 * rotated and non ratated lines.
	 * @param rotated tells if the shapes are ratated or not.
	 */
	public CILine (boolean rotated)
	{
		super(rotated);
		
		_normalFeature = new CIFeatures (CIEvaluate.Tl_Pch, 0.4, 0.45, BIG, BIG);
		_dashFeature = new CIFeatures (CIEvaluate.Tl_Pch, 0, 0, 0.4, 0.45,
				CIEvaluate.Pch_Ns_Tl, 5, 10, BIG, BIG);
		
		_features = new CIFeatures (CIEvaluate.Her_Wer, 0, 0, 0.06, 0.08);
	}
	
        public CIPoint getPoint(int i){
            return _points[i];
        }
        
	public CILine (CIScribble sc, CIPoint a, CIPoint b, double dom, boolean dash, boolean bold)
	{
		_sc=sc;
		_points[0] = a; 
		_points[1] = b; 
		_dashed = dash; 
		_bold = bold;
		_open = false;
		_dom = dom;
		_features = null;
		_dashFeature = null;
	}
	
	/**
	 * Computes the points of the recognized line
	 */
	public void setUp(CIScribble sc)
	{
		CIList points;
		
		_sc = sc;
		points = sc.enclosingRect().getPoints();
		_points[0] = (CIPoint)points.get(0);
		_points[1] = (CIPoint)points.get(2);
	}
	
	
	//public void draw(void *ptr) {
	public void draw(Object ptr) {
		
	}
	
	public String getName() { 
		return "Line"; 
	}
	
	/**
	 * Makes a clone of the current line.
	 */
	public Object clone()
	{
		return new CILine(_sc, _points[0], _points[1], _dom, _dashed, _bold);
	}
	
}