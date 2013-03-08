package pt.inevo.jcali;

/**
 * Represents a diamond. (solid, dashed or bold)
 */
public class CIDiamond extends CIShape {

    protected CIPoint[] _points = new CIPoint[4];
   
    /**
     * In this constructor we define all the features that are used
     * to identify diamonds. The set of features are different for
     * rotated and non ratated diamonds.
     * @param rotated tells if the shapes are rotated or not
     */
    public CIDiamond (boolean rotated)
    {
        super(rotated);
        
        if (rotated) {
            _features = new CIFeatures (CIEvaluate.Alq_Ach, 0.78, 0.85, 1, 1, // separate from ellipses
                                        CIEvaluate.Pch2_Ach, 14.5, 15.5, 21.5, 26, // Separate from bold lines
                                        CIEvaluate.Alq_Aer, 0.52, 0.56, 0.72, 0.78, // Separate from rectangles
                                        CIEvaluate.Alt_Alq, 0.5, 0.53, 0.62, 0.7,
                                        CIEvaluate.Hollowness, 0, 0, 1, 1);
        }
        else {
            _features = new CIFeatures (CIEvaluate.Alt_Abb, 0, 0, 0.4, 0.45);
        }
    }
    
    public CIDiamond() {
        this(true);
    }

    public CIDiamond (CIScribble sc, CIPoint a, CIPoint b, CIPoint c, CIPoint d, double dom, boolean dash, boolean bold)
    { 
        _sc=sc;
        _points[0] = a; 
        _points[1] = b; 
        _points[2] = c; 
        _points[3] = d;
        _dashed = dash; 
        _bold = bold;
        _open = false;
        _dom = dom;
        _features = null;
        _dashFeature = null;
    }
    
    public CIDiamond (CIScribble sc, CIPoint a, CIPoint b, CIPoint c, CIPoint d, double dom, boolean dash) {
        this(sc, a, b, c, d, dom, dash, false);
    }
    public CIDiamond (CIScribble sc, CIPoint a, CIPoint b, CIPoint c, CIPoint d, double dom) {
        this(sc, a , b, c, d, dom, false, false);
    }

    
    public void draw(Object ptr) {
        }
    
    public String getName() { 
        return ("Diamond"); 
        }

    
	/**
	 * Makes a clone of the current diamond.
	 */
    public Object clone()
    {
        return new CIDiamond(_sc, _points[0], _points[1], _points[2], _points[3], _dom, _dashed, _bold);
    }

	/**
	 * Computes the points of the recognized diamond
	 */
    public void setUp(CIScribble sc)
    {
        CIList points;
        
        _sc = sc;
        if (_rotated) {
            points = sc.enclosingRect().getPoints();
        }
        else {
            points = sc.boundingBox().getPoints();
        }

        _points[0] = (CIPoint)points.get(0);
        _points[1] = (CIPoint)points.get(1);
        _points[2] = (CIPoint)points.get(2);
        _points[3] = (CIPoint)points.get(3);

        //     This is not the correct code to compute the points of the diamond.
    }



}


