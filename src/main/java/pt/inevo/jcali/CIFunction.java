package pt.inevo.jcali;
/**
 * Class with a set of static functions used by other classes.
 */
public class CIFunction {

	/**
	 * Computes the area of a triangle
	 */
    public static double triangleArea(CIPoint p1, CIPoint p2, CIPoint p3) {
        double area = p1.x * p2.y - p2.x * p1.y;
        area += p2.x * p3.y - p3.x * p2.y;
        area += p3.x * p1.y - p1.x * p3.y;

        return Math.abs(area/2);    
    }
    
	/**
	 * Computes the area of a rectangle
	 */
    public static double quadArea(CIPoint p1, CIPoint p2, CIPoint p3, CIPoint p4) {
        double area = p1.x * p2.y - p2.x * p1.y;
        area += p2.x * p3.y - p3.x * p2.y;
        area += p3.x * p4.y - p4.x * p3.y;
        area += p4.x * p1.y - p1.x * p4.y;
        
        return Math.abs(area/2);
    }

    /**
     * Computes the "angle" made by the line defined by the 2 points
     */
    public static double theta(CIPoint p, CIPoint q) {
        double dx = q.x - p.x, ax = Math.abs(dx),
        dy = q.y - p.y, ay = Math.abs(dy);
        
        double t = (ax + ay == 0) ? 0 : (double) dy / (ax + ay);
        
        if (dx < 0) 
            t = 2 - t;
        else if (dy < 0) 
            t = 4 + t;
        
        return t*90;
    }

    /**
     * Computes the distance between two points
     */
    public static double distance(CIPoint p, CIPoint q) {
        return Math.sqrt(Math.pow(q.x-p.x,2) + Math.pow(q.y-p.y,2));       
    }
    
    public static boolean left(CIPoint a, CIPoint b, CIPoint c) {
        return (a.x * b.y - a.y * b.x + a.y * c.x - a.x * c.y + b.x * c.y - c.x * b.y) > 0;
    }
    
    public static double angle(CIVector a, CIVector b) {
        return Math.atan2(CIFunction.cross(a, b), CIFunction.dot(a, b));        
    }
    
    public static double angle(CIPoint a, CIPoint b) {
        return Math.atan2(b.y - a.y, b.x - a.x);        
    }


	/**
	 * return point on line (p1, p2) which is closer to p3
	 */
    public static CIPoint closest(CIPoint p1, CIPoint p2, CIPoint p3) {
        double d;
    	
        if (Math.abs(p2.x - p1.x) < 1e-10) 
    	return new CIPoint(p1.x, p3.y);

        if ((p1.equals(p3)) || (p2.equals(p3)))
            return p3;

        if (Math.abs(p2.y - p1.y) < 1e-10)
             return new CIPoint(p3.x, p1.y);

        d = p2.x - p1.x;
        double m = (p2.y - p1.y) / d;

        double b1, b2, x, y;
        b1 = p2.y - m * p2.x;
        b2 = p3.y + 1/m * p3.x;
        x = (b2 - b1) / (m + 1/m);
        y = m * x + b1;

        return new CIPoint(x, y);    
    }
    
    public static double cross(CIVector a, CIVector b) {
        double dx1 = a.endp.x - a.startp.x, dx2 = b.endp.x - b.startp.x,
        dy1 = a.endp.y - a.startp.y, dy2 = b.endp.y - b.startp.y;
        return dx1 * dy2 - dy1 * dx2;        
    }
    
    public static double dot(CIVector a, CIVector b) {
        double dx1 = a.endp.x - a.startp.x, dx2 = b.endp.x - b.startp.x,
        dy1 = a.endp.y - a.startp.y, dy2 = b.endp.y - b.startp.y;
        return dx1 * dx2 + dy1 * dy2;    
    }
}