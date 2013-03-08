package pt.inevo.jcali;

/**
 * Represents a circle. (solid, dashed or bold)
 */
public class CICircle extends CIShape {
    protected double _radius;
    protected CIPoint _center = new CIPoint();
    protected CIPoint[] _points = new CIPoint[4];

	/**
	 * In this constructor we define all the features that are used to identify circles.
	 * @param rotated tells if the shapes are rotated or not
	 */
    public CICircle (boolean rotated)
    {
        super(rotated);
        
        _features = new CIFeatures (CIEvaluate.Pch2_Ach, 12.5, 12.5, 13.2, 13.5,
                                    CIEvaluate.Hollowness, 0, 0, 0, 0);
    }
    
    public CICircle() {
        this(true);
    }


    public double getRadius(){ return _radius;}
    public CIPoint getCenter(){ return _center;}
    
    public CICircle (CIScribble sc, double dom, boolean dash, boolean bold)
    { 
        _sc=sc;
        if(sc != null) {
            setUp(sc);
        }
        _dashed = dash; 
        _bold = bold;
        _open = false;
        _dom = dom;
        _features = null;
        _dashFeature = null;
    }
    
    public CICircle (CIScribble sc, double dom, boolean dash) {
        this(sc, dom, dash, false);
    }
    
    public CICircle (CIScribble sc, double dom) {
        this(sc, dom, false, false);
    }

    public void draw(Object ptr) {
        }
    
    public String getName() { 
        return ("Circle"); 
        }
    
    
    /**
     *  Computes the center and the radius of the recognized circle
     */
    public void setUp(CIScribble sc)
    {
        CIList points;
        double d1, d2;
        
        _sc = sc;
        points = sc.boundingBox().getPoints();
        _points[0] = (CIPoint)points.get(0);
        _points[1] = (CIPoint)points.get(1);
        _points[2] = (CIPoint)points.get(2);
        _points[3] = (CIPoint)points.get(3);
        d1 = Math.sqrt(Math.pow(_points[0].x-_points[1].x,2) + Math.pow(_points[0].y-_points[1].y,2));
        d2 = Math.sqrt(Math.pow(_points[2].x-_points[1].x,2) + Math.pow(_points[2].y-_points[1].y,2));
        _radius = (d1+d2)/2/2;
        _center.x = _points[0].x + d2/2;
        _center.y = _points[0].y + d1/2;
    }

	/**
	 * Makes a clone of the current circle.
	 */
    public Object clone()
    {
        return new CICircle(_sc, _dom, _dashed, _bold);
    }
}
