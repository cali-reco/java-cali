package pt.inevo.jcali;

public class CIPoint {
	public double x;
	public double y;
	protected double _time;

	public CIPoint () { 
	    x = 0; 
	    y = 0; 
	    _time = 0; 
	}
	
	public CIPoint (double xx, double yy, double time) { 
	    x=xx; 
	    y=yy; 
	    _time=time; 
	}
	
	public CIPoint (double xx, double yy) {
	    this(xx, yy, 0);
	}
	
	public boolean equals(CIPoint p) {
	    return x == p.x && y == p.y;
	}
	
	public double getTime() {
	    return _time;
	}
}