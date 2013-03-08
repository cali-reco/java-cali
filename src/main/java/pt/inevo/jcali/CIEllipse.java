package pt.inevo.jcali;
/**
 * Represents an ellipse. (solid, dashed or bold)
 */
public class CIEllipse extends CIShape {    
    
    protected CIPolygon _eliPoints = new CIPolygon();
    protected CIPoint[] _points = new CIPoint[4];
    
   /**
    * In this constructor we define all the features that are used
    * to identify ellipses. The set of features are different for
    * rotated and non ratated ellipses.
    * @param rotated tells if the shapes are rotated or not
    */
    public CIEllipse (boolean rotated)
    {
        super(rotated);
        
        if (rotated) {
            _features = new CIFeatures (CIEvaluate.Pch2_Ach, 13.2, 13.5, 19, 30, // separate from bold circles
                    CIEvaluate.Alq_Ach, 0.6, 0.65, 0.71, 0.78,
                    CIEvaluate.Hollowness, 0, 0, 0, 0);
        }
        else {
            _features = new CIFeatures (CIEvaluate.Alt_Abb, 0, 0, 0.4, 0.45,
                    CIEvaluate.Alt_Ach, 0.4, 0.43, 0.47, 0.52,
                    CIEvaluate.Ach_Abb, 0.6, 0.7, 0.8, 0.9//,
                    //&CIEvaluate::scLen_Pch, 0, 0, 1.5, 1.7
            );
        }
    }
    
    public CIEllipse() {
        this(true);
    }
    
    public CIEllipse (CIScribble sc, double dom, boolean dash, boolean bold)
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
    
    public CIEllipse (CIScribble sc, double dom, boolean dash) {
        this(sc, dom, dash, false);
    }
    
    public CIEllipse (CIScribble sc, double dom) {
        this(sc, dom, false, false);
    }
    
    public void draw(Object ptr) {
    }
    
    public String getName() { 
        return ("Ellipse"); 
    }
    
	 /**
	  * Makes a clone of the current ellipse
	  */
    public Object clone()
    {
        return new CIEllipse(_sc, _dom, _dashed, _bold);
    }
    
    /**
     * Computes the points of the recognized ellipse
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
        _eliPoints = calcEllipse(_points);
    }
    
    private CIPolygon calcEllipse(CIPoint[] points)
    {
        double a, b, d1, d2;
        double theta;
        int np=0;
        
        CIPoint[] pts = new CIPoint[1000];

        CIPoint pt = new CIPoint();
        pt.x = points[1].x;
        pt.y = points[1].y;
        theta = CIFunction.angle(points[0], points[3]);
        
        a = Math.sqrt(Math.pow(points[2].x - points[1].x,2) + Math.pow(points[2].y - points[1].y,2)) / 2;
        b = Math.sqrt(Math.pow(points[0].x - points[1].x,2) + Math.pow(points[0].y - points[1].y,2)) / 2;
        
        int x = 0;
        int y = (int)b;
        
        d1 = Math.pow(b,2)-Math.pow(a,2)*b+Math.pow(a,2)/4;
        pts[np] = new CIPoint();
        pts[np].x = x;
        pts[np].y = y;
        np++;
        
        while (Math.pow(a,2)*(y-1/2) > Math.pow(b,2)*(x+1)) {
            if(d1<0) {
                d1+=Math.pow(b,2)*(2*x+3);
                x++;
            }
            else {
                d1+=Math.pow(b,2)*(2*x+3)+Math.pow(a,2)*(-2*y+2);
                x++;
                y--;
            }
            pts[np] = new CIPoint();
            pts[np].x = x;
            pts[np].y = y;
            np++;
        }
        
        d2=Math.pow(b,2)*Math.pow(x+1/2,2)+Math.pow(a,2)*Math.pow(y-1,2)-Math.pow(a,2)*Math.pow(b,2);
        while(y>=-1) {
            if(d2<0) {
                d2+=Math.pow(b,2)*(2*x+2)+Math.pow(a,2)*(-2*y+3);
                x++;
                y--;
            }
            else {
                d2+=Math.pow(a,2)*(-2*y+3);
                y--;
            }
            pts[np] = new CIPoint();
            pts[np].x = x;
            pts[np].y = y;
            np++;
        }
        
        np--;
        
        _eliPoints = rotate(pts, theta, pt, a, b, np);
        return _eliPoints;
    }
    
    private CIPolygon rotate(CIPoint[] points, double theta, CIPoint p, double a, double b, int np)
    {
        CIPolygon pol = new CIPolygon();
        CIPoint[] _ellipsePoints = new CIPoint[4*np];
        
        for (int i=0; i<np; i++) {
        	_ellipsePoints[i] = new CIPoint();
            _ellipsePoints[i].x = Math.cos(theta)*(-points[np-i-1].x+a)-Math.sin(theta)*(points[np-i-1].y+b)+p.x;
            _ellipsePoints[i].y = Math.sin(theta)*(-points[np-i-1].x+a)+Math.cos(theta)*(points[np-i-1].y+b)+p.y;
            
        	_ellipsePoints[np+i] = new CIPoint();
            _ellipsePoints[np+i].x = Math.cos(theta)*(points[i].x+a)-Math.sin(theta)*(points[i].y+b)+p.x;
            _ellipsePoints[np+i].y = Math.sin(theta)*(points[i].x+a)+Math.cos(theta)*(points[i].y+b)+p.y;  
            
        	_ellipsePoints[2*np+i] = new CIPoint();
            _ellipsePoints[2*np+i].x =Math.cos(theta)*(points[np-i-1].x+a)-Math.sin(theta)*(-points[np-i-1].y+b)+p.x;
            _ellipsePoints[2*np+i].y =Math.sin(theta)*(points[np-i-1].x+a)+Math.cos(theta)*(-points[np-i-1].y+b)+p.y;
            
        	_ellipsePoints[3*np+i] = new CIPoint();
            _ellipsePoints[3*np+i].x =Math.cos(theta)*(-points[i].x+a)-Math.sin(theta)*(-points[i].y+b)+p.x;
            _ellipsePoints[3*np+i].y =Math.sin(theta)*(-points[i].x+a)+Math.cos(theta)*(-points[i].y+b)+p.y;
        }
        
        for (int j=0; j<4*np; j++) { 
            pol.addPoint(_ellipsePoints[j]);
        }
        return pol;
    }
}


