package pt.inevo.jcali;

public class CIArrow extends CIShape implements Values {
    protected CIPoint[] _points = new CIPoint[2];
    
    public CIArrow (boolean rotated)
    {
        super(rotated);
        
        _features = new CIFeatures (CIEvaluate.Ns, 2, 2, BIG, BIG);
    }
    
    public CIArrow() {
        this(true);
    }

    public CIArrow (CIScribble sc, CIPoint a, CIPoint b, double dom, boolean dash, boolean bold)
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

    public CIArrow (CIScribble sc, CIPoint a, CIPoint b, double dom, boolean dash) {
    	this(sc, a, b, dom, dash, false);	
    }
    
    public CIArrow (CIScribble sc, CIPoint a, CIPoint b, double dom) {
        this(sc, a, b, dom, false, false);
    }
              
    public String getName() { 
    	return "Arrow"; 
    }  
    
    /**
     * Computes the points of the recognized arrow
     */
    public void setUp(CIScribble sc)
    {
        _sc = sc;
    }

    /**
     * Makes a clone of the current arrow.
     */
    public Object clone()
    {
        return new CIArrow(_sc, _points[0], _points[1], _dom, _dashed, _bold);
    }


    public double evalGlobalFeatures(CIScribble sc)
    {
        _sc = sc;
        _dom = _features.evaluate(sc);
        _dashed = false;
        _bold = false;
        _open = false;
        return _dom;
    }

    /**
     * Evaluate the scribble based on local features. An arrow is
     * something with a Move or Triangle at the end.
     * @param sc A scribble
     * @param _shapesList A list of possible gestures
     * @return dom
     */
    public double evalLocalFeatures(CIScribble sc, CIList _shapesList)
    {
        double val;
        int i;
        int nshapes = _shapesList.getNumItems();
        CIGesture lastShp;
        CIUnknown firstShp;


        lastShp = null;
        val = 0;
        if (sc.getNumStrokes() > 1) {
            CIScribble scribb = (CIScribble) sc.clone();  // create a new scribble like the original
    	    CIScribble scLast = new CIScribble();
    	    scLast.addStroke(scribb.popStroke());  // Last stroke of the scribble

    	    for (i=0; i<nshapes; i++) {
    	        ((CIGesture)_shapesList.get(i)).pushAttribs();
                if (val == ((CIGesture)_shapesList.get(i)).evalGlobalFeatures(scLast)) {
     			    lastShp = (CIGesture)_shapesList.get(i); 
                    break;
                }
    	    }

            if (lastShp != null) {
                firstShp = new CIUnknown();
                firstShp.setUp(scribb);
                String name2 = lastShp.getName();

                if (!name2.equals("Move") || !name2.equals("Triangle")) {
                    // verificar se o ultimo ponto do rabo da seta est� perto da ponta
                    CIList points;
                    CIPoint[] pts = new CIPoint[3];
                    
                    CIPoint pc = new CIPoint();
                    CIPoint sp = new CIPoint();
                    CIPoint ep = new CIPoint();
        
                    points = scLast.largestTriangle().getPoints();
                    pts[0] = (CIPoint)points.get(0);
                    pts[1] = (CIPoint)points.get(1);
                    pts[2] = (CIPoint)points.get(2);
                    
                    pc.x = (pts[0].x + pts[1].x + pts[2].x) / 3;
                    pc.y = (pts[0].y + pts[1].y + pts[2].y) / 3;

                    // distances from the starting point and ending point 
                    // to the center point
                    double dxysp, dxyep, r;
                    sp = scribb.startingPoint();
                    ep = scribb.endingPoint();
                    dxysp = Math.abs(sp.x - pc.x) + Math.abs(sp.y - pc.y);
                    dxyep = Math.abs(ep.x - pc.x) + Math.abs(ep.y - pc.y);
                    r = dxyep / dxysp;

                    if (firstShp.isBold() || r <= 0.6 || r >= 1.7) {
                        setUp(scribb); 
                        _points[0] = sc.startingPoint();
                        _points[1] = pc;
                        _dashed = firstShp.isDashed();
                        _bold = firstShp.isBold();
                    }
                    else {
                        val = 0;
                    }
                } 
                else {
                    val = 0;
                }
            }
    	    for (i=0; i<nshapes; i++) {
    	        ((CIGesture)_shapesList.get(i)).popAttribs();
    	    }
        }
        return val;
    }

    /**
     * @see pt.inesc.jcali.CIShape#draw(java.lang.Object)
     */
    public void draw(Object ptr) {
    }

}