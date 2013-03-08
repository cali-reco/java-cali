package pt.inevo.jcali;
/**
 * Class that represents a stroke as a set of points. Each point of
 * the stroke has a temporal mark, which is used to calculate the
 * drawing speed of the stroke.
 */
public class CIStroke implements Values{
	protected CIList _points = new CIList();
	protected double _len;
	protected double _speed;
	protected CIPoint _lastPoint;
	protected double _firstTime;
	
	public CIStroke ()
	{
		_len = 0;
		_speed = 0;
		_firstTime = 0;
	}

	/**
	 * Returns number of points of the stroke
	 * @return number of points 
	 */
	public int getNumPoints() {	
		return _points.getNumItems(); 
	}
	
	/**
	 * Stroke length
	 * @return Stroke length
	 */
	public double getLen() { 
		return _len;  
	}
	
	/**
	 * List of points of the stroke
	 * @return List of points of the stroke
	 */
	public CIList getPoints() { 
		return _points; 
	}
	
	/**
	 * Average drawing speed
	 * @return Average drawing speed
	 */
	public double getDrawingSpeed() {  
		return _speed; 
	}
	
	/**
	 * Adds a point to the stroke. Inserts the point at the end of
	 * the list of points, computes the new length of the stroke and
	 * the drawing speed.
	 * @param x x coord
	 * @param y y coord
	 * @param time temporal mark in millis
	 */
	public void addPoint(double x, double y, double time)
	{
		_points.push(new CIPoint(x, y, time));
		if (getNumPoints() > 1) {
			_len += Math.sqrt(Math.pow(_lastPoint.x - x,2) + Math.pow(_lastPoint.y - y,2));
			
			if (time == _firstTime) {
				_speed = BIG;
			}
			else {
				_speed = (double) _len / (time - _firstTime);
			}
		} 
		else {
			_firstTime = time;
		}
		_lastPoint = new CIPoint(x,y);
	}
	
	/**
	 * Adds a point to the stroke. Inserts the point at the end of
	 * the list of points, computes the new length of the stroke and
	 * the drawing speed. (sets temporal mark to 0)
	 * @param x x coord
	 * @param y y coord
	 */
	public void addPoint(double x, double y) {
		this.addPoint(x, y, 0);
	}
	
}