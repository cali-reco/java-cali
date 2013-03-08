package pt.inevo.jcali;

/**
 * Class that implements a polygon. It is built up of a set of
 * points. We can compute the area and perimeter of the polygon,
 * get the number of points or remove the last point added.
 */
public class CIPolygon implements Values {
    protected double _area;
    protected double _perim;
    protected CIList _points;
    
    public CIPolygon ()
    {
        _points = new CIList(); 
        _area = 0;
        _perim = 0;
    }
    
	// ~CIPolygon ()			    { delete _points; }

    // methods    
    public void addPoint (CIPoint pt) { 
        _points.insertTail(pt); 
    }
    
    public void addPoint (double x, double y) { 
        _points.insertTail(new CIPoint(x,y)); 
    }

    public CIList getPoints () { 
        return _points; 
    }
    
    /**
     * Number of points of the polygon
     */
    public int getNumPoints() { 
        return _points.getNumItems(); 
    }
    
    public void push (CIPoint pt) { 
        _points.insertTail(pt); 
    }
    
    public CIPoint pop () { 
        return (CIPoint)_points.pop(); 
        }



    /*----------------------------------------------------------------------------+
    | Description: Computes the area of the polygon, using a general algorithm.
    | Output: the area
    +----------------------------------------------------------------------------*/
    public double area()
    {
        if (_area == 0) {
	    	int numPoints = _points.getNumItems();
    		
    		if (numPoints < 3) {
            	_area = ZERO;
        		return _area;
    		}

    		for (int i = 0; i < numPoints - 1; i++) {
	    	    _area += ((CIPoint)_points.get(i)).x * ((CIPoint)_points.get(i+1)).y - ((CIPoint)_points.get(i+1)).x * ((CIPoint)_points.get(i)).y;
    		}
	    	
	    	_area /= 2;
	    	
    		if (_area == 0) {
	    	    _area = ZERO;
	    	}
        }
        return Math.abs(_area);
    }

    /*----------------------------------------------------------------------------+
    | Description: Computes the perimeter of the polygon, using a general algorithm.
    | Output: the perimeter
    +----------------------------------------------------------------------------*/
    // Works only if last point is the closing point (== first point) ???!?
    public double perimeter()
    {
        if (_perim == 0) {
            int numPoints = _points.getNumItems();
            
            for (int i = 0; i < numPoints - 1; i++) {
                _perim += CIFunction.distance(((CIPoint)_points.get(i)), ((CIPoint)_points.get(i+1)));
            }	
            
            if (_perim == 0) {
                _perim = ZERO;
            }
            
            if (numPoints < 3) {
                _perim *= 2;
            }
        }
        return _perim;
    }

}