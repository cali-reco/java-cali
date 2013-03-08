package pt.inevo.jcali;

import java.util.ArrayList;

/* Starting header file: CIScribble.h */
 /*--------------------------------------------------------- -*- Mode: C++ -*- +
  | Module: CIScribble.h
  +-----------------------------------------------------------------------------+
  | Description: This class defines a scribble as a set of strokes. It offers
  |              a set of methods to compute some polygons used during the 
  |              recognition process.
  | 
  | Notes:       
  |
  | Author: Manuel Joao Fonseca
  |	  e-mail: mjf@ist.utl.pt
  |
  | Date: April 98
  | Changed: April 99
  +-----------------------------------------------------------------------------+
  |
  | Copyright (C) 1998, 1999, 2000 Manuel Jo�o da Fonseca
  |
  | This program is free software; you can redistribute it and/or
  | modify it under the terms of the GNU General Public License
  | as published by the Free Software Foundation; either version 2
  | of the License, or any later version.
  | 
  | This program is distributed in the hope that it will be useful,
  | but WITHOUT ANY WARRANTY; without even the implied warranty of
  | MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  | GNU General Public License for more details.
  | 
  | You should have received a copy of the GNU General Public License
  | along with this program; if not, write to the Free Software
  | Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
  | 
  +----------------------------------------------------------------------------*/

public class CIScribble implements Values {
	protected double _len;
	protected double _totalSpeed;
	protected CIList _strokes = new CIList();
	
	protected CIPolygon _boundingBox;    // The points are order
	protected CIPolygon _convexHull;
	protected CIPolygon _largestTriangleOld;
	protected CIPolygon _enclosingRect;
	protected CIPolygon _largestQuadOld;
	protected CIPolygon _largestTriangle;
	protected CIPolygon _largestQuad;
	
	public CIScribble () 
	{
		// Could all be deleted ...		
		_len = 0;
		_totalSpeed = 0;
		
		_boundingBox = null;
		_convexHull = null;
		_largestTriangleOld = null;
		_largestTriangle = null;
		_largestQuadOld = null;
		_largestQuad = null;
		_enclosingRect = null;
	}
	
	//  Number of strokes of the scribble
	public int getNumStrokes () { 
		return _strokes.getNumItems(); 
	}
	
	//  List of strokes of the scribble
	public CIList getStrokes()  {
		return _strokes; 
	}
	
	//  Scribble length
	public double getLen() { 
		return _len; 
	}
	
//	Drawing average speed of the scribble
	public double getAvgSpeed()	{ 
		return _totalSpeed / getNumStrokes(); 
	}
	
	
	
	/*
	 ~CIScribble () 
	 {
	 if (_boundingBox) delete _boundingBox;
	 if (_convexHull) delete _convexHull;
	 if (_largestTriangleOld) delete _largestTriangleOld;
	 if (_largestTriangle) delete _largestTriangle;
	 if (_largestQuadOld) delete _largestQuadOld;
	 if (_largestQuad) delete _largestQuad;
	 if (_enclosingRect) delete _enclosingRect;
	 
	 if (_strokes) {
	 int t = _strokes.getNumItems();
	 for (int i=0; i< t; i++) 
	 delete (*_strokes)[i];
	 delete _strokes;
	 }
	 }*/
	
	/*----------------------------------------------------------------------------+
	 | Description: Creates a copy (a clone) of the current scribble.
	 | Output: A new scribble like the current one.
	 +----------------------------------------------------------------------------*/
	
	public Object clone()
	{
		int ns, np;
		CIStroke strk, stroke;
		CIList pts;
		
		CIScribble scribb = new CIScribble();
		
		ns = getNumStrokes();
		for (int s=0; s<ns; s++) {
			strk = (CIStroke)_strokes.get(s);
			stroke = new CIStroke();
			np = strk.getNumPoints();
			pts = strk.getPoints();
			for (int p=0; p<np; p++) {
				CIPoint tp = (CIPoint)pts.get(p);
				stroke.addPoint(tp.x, tp.y, tp.getTime());
			}
			scribb.addStroke(stroke);
		}
		return scribb;
	}
	
	/*----------------------------------------------------------------------------+
	 | Description: Adds a new stroke to the end of the list of strokes manipulated
	 |              by the scribble. It also computes the new length of the scribble
	 |              and the new drawing speed.
	 | Input: A stroke
	 +----------------------------------------------------------------------------*/
	public void addStroke(CIStroke stroke)
	{
		_len += stroke.getLen();
		_totalSpeed += stroke.getDrawingSpeed();
		_strokes.push(stroke);
	}
	
	/*----------------------------------------------------------------------------+
	 | Description: Pops out the last stroke of the scribble
	 | Output: The last stroke
	 +----------------------------------------------------------------------------*/
	public CIStroke popStroke()
	{
		CIStroke strk;
		
		strk = (CIStroke)_strokes.pop();
		_len -= strk.getLen();
		_totalSpeed -= strk.getDrawingSpeed();
		
		/*
		 if (_boundingBox) delete _boundingBox;
		 if (_convexHull) delete _convexHull;
		 if (_largestTriangleOld) delete _largestTriangleOld;
		 if (_largestTriangle) delete _largestTriangle;
		 if (_largestQuadOld) delete _largestQuadOld;
		 if (_largestQuad) delete _largestQuad;
		 if (_enclosingRect) delete _enclosingRect;
		 */
		
		_boundingBox = null;
		_convexHull = null;
		_largestTriangle = null;
		_largestTriangleOld = null;
		_largestQuadOld = null;
		_largestQuad = null;
		_enclosingRect = null;
		
		return strk;
	}
	
	/*----------------------------------------------------------------------------+
	 | Description: Computes the correct timeout, based on the drawing speed.
	 | Output: Timeout, in milliseconds
	 | Notes: Actually it returns a constant value, because the formula used to 
	 |        the "best" timeout is not very good. We are searching for a better one :-)
	 +----------------------------------------------------------------------------*/
	public int getTimeOut()
	{
		return 550; // to delete <<<<<<<<<<
		/*
		 double avs = getAvgSpeed();
		 if (avs >= 900)
		 return TIMEOUT_BASE + 0;
		 else if (avs <= 100)
		 return TIMEOUT_BASE + 400;
		 else // y=-0.5*x+450
		 return (int) (TIMEOUT_BASE + (-0.5*avs + 450));
		 */
	}
	
//	------------- Polygons
	
	/*----------------------------------------------------------------------------+
	 | Description: Computes the convex hull of the scribble, using the Graham's
	 |              algorithm. After the computation of the convex hull, we perform
	 |              a simple filtration to eliminate points that are very close.
	 | Output: A polygon that is the convex hull.
	 +----------------------------------------------------------------------------*/
	public CIPolygon convexHull()
	{
		if (_convexHull == null) {
			_convexHull = new CIPolygon();
			CIList ordedPoints = new CIList(false);
			CIPoint min;
			CIPoint tmp;
			int np, i;
			CIList pts;
			
			min = findLowest();
			ordedPoints = ordPoints(ordedPoints, min);
			np = ordedPoints.getNumItems();
			_convexHull.push((CIPoint)ordedPoints.get(np-1));
			_convexHull.push((CIPoint)ordedPoints.get(0));
			
			pts = _convexHull.getPoints();
			i = 1;
			
			int nc = _convexHull.getNumPoints();
			
			while (np > 2 && i < np && nc >= 2) {
				if ( CIFunction.left((CIPoint)pts.get(nc-2), (CIPoint)pts.get(nc-1), (CIPoint)ordedPoints.get(i)) ) {
					_convexHull.push((CIPoint)ordedPoints.get(i));
					i++;
					nc++;
				}
				else {
					tmp = _convexHull.pop();
					nc--;
				}
			}
			//_convexHull = filterConvexHull(); // reduce the number of points
		}
		return _convexHull;
	}
	
	
	/*----------------------------------------------------------------------------+
	 | Description: Computes the bounding box of the convex hull.
	 | Output: A polygon that is the bounding box.
	 +----------------------------------------------------------------------------*/
	public CIPolygon boundingBox()
	{
		if (_boundingBox == null) {
			convexHull();
			
			CIList pts = _convexHull.getPoints();
			int np = _convexHull.getNumPoints();
			double x1, x2, y1, y2;
			
			x1 = x2 = ((CIPoint) pts.get(0)).x;
			y1 = y2 = ((CIPoint) pts.get(0)).y;
			
			for ( int i = 0; i < np ; i++) {
				CIPoint tp = (CIPoint)pts.get(i);
				if (tp.x < x1) {
					x1 = tp.x;
				}
				if (tp.x > x2) {
					x2 = tp.x;
				}
				if (tp.y < y1) {
					y1 = tp.y;
				}
				if (tp.y > y2) {
					y2 = tp.y;
				}
			}
			
			// Tranfer the points to a polygon
			_boundingBox = new CIPolygon();
			_boundingBox.addPoint(new CIPoint(x1, y1));
			_boundingBox.addPoint(new CIPoint(x2, y1));
			_boundingBox.addPoint(new CIPoint(x2, y2));
			_boundingBox.addPoint(new CIPoint(x1, y2));
			_boundingBox.addPoint(new CIPoint(x1, y1));
		}
		return _boundingBox;
	}  
	
	// QUICKFIX
	public Rectangle getRectangle() {
		convexHull();
		
		CIList pts = _convexHull.getPoints();
		int np = _convexHull.getNumPoints();
		int x1, x2, y1, y2;
		int x, y;
		
		x1 = x2 = (int)((CIPoint) pts.get(0)).x;
		y1 = y2 = (int)((CIPoint) pts.get(0)).y;
		
		for ( int i = 0; i < np ; i++) {
			CIPoint tp = (CIPoint)pts.get(i);
			x = (int)tp.x;
			y = (int)tp.y;
			if (x < x1) {
				x1 = x;
			}
			if (x > x2) {
				x2 = x;
			}
			if (y < y1) {
				y1 = y;
			}
			if (y > y2) {
				y2 = y;
			}
		}
		return new Rectangle(x1, y1, x2-x1, y2-y1);
	}
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes the largest triangle that fits inside the convex hull
		 | Output: A polygon that is the largest triangle.
		 | Notes: We used the algorithm described by J.E. Boyce and D.P. Dobkin.
		 +----------------------------------------------------------------------------*/
		public CIPolygon largestTriangle()
		{
			if (_largestTriangle == null) {
				convexHull();
				
				int ia, ib, ic, i;
				int ripa = 0, ripb = 0, ripc = 0; // indexes of rooted triangle
				double area, triArea;
				
				int numPts = _convexHull.getNumPoints();
				CIList pts = _convexHull.getPoints();
				
				if (numPts <= 3) {
					_largestTriangle = new CIPolygon();
					for (i=0; i < numPts; i++) {
						_largestTriangle.addPoint((CIPoint)pts.get(i));
					}
					for (i= numPts; i < 4; i++) {
						_largestTriangle.addPoint((CIPoint)pts.get(0));
					}
					return _largestTriangle;
				}
				
//				computes one rooted triangle with root in the first point of the convex hull
				ia = 0;
				area = 0;
				triArea = 0;
				for(ib=1; ib <= numPts-2; ib++) {
					if (ib >= 2) {
						ic = ib + 1;
					}
					else {
						ic = 2;
					}
		            Object [] res =  compRootedTri (pts, ia, ib, ic, numPts);
		            area = (Double) res[0];
		            ic=(Integer) res[1];
					if (area > triArea) {
						triArea = area;
						ripa = ia;
						ripb = ib;
						ripc = ic;
					}
				} // ripa, ripb and ripc are the indexes of the points of the rooted triangle
				
				
//				computes other triangles and choose the largest one
				double finalArea = triArea;
				int pf0, pf1, pf2;   // indexes of the final points
				int fipa=0, fipb=0, fipc=0; 
				int ib0;
				pf0 = ripa;
				pf1 = ripb;
				pf2 = ripc;
				
				for(ia = ripa+1; ia <= ripb; ia++) {
					triArea = 0;
					if (ia == ripb) {
						ib0 = ripb + 1;
					}
					else {
						ib0 = ripb;
					}
					area = 0;
					for(ib = ib0; ib <= ripc; ib++) {
						if (ib == ripc) {
							ic = ripc + 1;
						}
						else {
							ic = ripc;
						}
		                Object [] res = compRootedTri (pts, ia, ib, ic, numPts);
		                area = (Double) res[0];
		                ic=(Integer) res[1];
						if (area > triArea) {
							triArea = area;
							fipa = ia;
							fipb = ib;
							fipc = ic;
						}
					}
					if(triArea > finalArea) {
						finalArea = triArea;
						pf0 = fipa;
						pf1 = fipb;
						pf2 = fipc;
					}
				}
				
				// Tranfer the points to a polygon
				_largestTriangle = new CIPolygon();
				_largestTriangle.addPoint((CIPoint)pts.get(pf0));
				_largestTriangle.addPoint((CIPoint)pts.get(pf1));
				_largestTriangle.addPoint((CIPoint)pts.get(pf2));
				_largestTriangle.addPoint((CIPoint)pts.get(pf0));
			}
			return _largestTriangle;
		}  
		
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes the largest quadrilateral that fits inside the convex hull
		 | Output: A polygon that is the largest quadrilateral.
		 | Notes: We used the algorithm described by J.E. Boyce and D.P. Dobkin.
		 +----------------------------------------------------------------------------*/
		public CIPolygon largestQuad()
		{
			if (_largestQuad == null) {
				convexHull();
				
				int i, ia, ib, ic, ic0;
				int ripa=0, ripb=0, ripc=0; // indexes for rooted triangle
				double area, triArea;
				int numPts = _convexHull.getNumPoints();
				CIList pts = _convexHull.getPoints();
				
				if (numPts <= 4) {
					_largestQuad = new CIPolygon();
					for (i=0; i < numPts; i++)
						_largestQuad.addPoint((CIPoint)pts.get(i));
					for (i= numPts; i < 5; i++)
						_largestQuad.addPoint((CIPoint)pts.get(0));
					return _largestQuad;
				}
				
				// computes one rooted triangle        
				ia = 0;
				area = 0;
				triArea = 0;
				for(ib=1; ib <= numPts-2; ib++) {
					if (ib >= 2) {
						ic = ib + 1;
					}
					else {
						ic = 2;
					}
		            Object [] res = compRootedTri (pts, ia, ib, ic, numPts);
		            area = (Double) res[0];
		            ic=(Integer) res[1];
					
					if (area > triArea) {
						triArea = area;
						ripa = ia;
						ripb = ib;
						ripc = ic;
					}
				}
				
//				computes the rooted quadrilateral based on a rooted triangle
				int fipa=0, fipb=0, fipc=0, fipd=0; // indexes for final values
				int id, ib0;
				double quadArea;
				
				quadArea = 0;
				for(ib = ripa+1; ib <= ripb; ib++) {
					if (ib == ripb) {
						ic0 = ripb + 1;
					}
					else {
						ic0 = ripb;
					}
					
					for(ic = ic0; ic <= ripc; ic++) {
						if (ic == ripc) {
							id = ripc + 1;
						}
						else {
							id = ripc;
						}
						
		                Object [] res = compRootedQuad (pts, ia, ib, ic, id, numPts);
		                area = (Double) res[0];
		                id=(Integer) res[1];
						if (area > quadArea) {
							quadArea = area;
							fipa = ia;
							fipb = ib;
							fipc = ic;
							fipd = id;
						}
					}
				}
				
//				computes other quadrilaterals and choose the largest one
				int pf0, pf1, pf2, pf3;
				double finalArea = quadArea;
				pf0 = fipa;
				pf1 = fipb;
				pf2 = fipc;
				pf3 = fipd;
				ripa = fipa;
				ripb = fipb;
				ripc = fipc;
				int ripd = fipd;
				
				for(ia = ripa+1; ia <= ripb; ia++) {
					if (ia == ripb) {
						ib0 = ripb + 1;
					}
					else {
						ib0 = ripb;
					}
					
					quadArea = 0;
					area = 0;
					for(ib=ib0; ib <= ripc; ib++) {
						if (ib == ripc) {
							ic0 = ripc + 1;
						}
						else {
							ic0 = ripc;
						}
						
						for (ic = ic0; ic <= ripd; ic++) {
							if (ic == ripd) {
								id = ripd + 1;
							}
							else {
								id = ripd;
							}
							
		                    Object [] res = compRootedQuad (pts, ia, ib, ic, id, numPts);
		                    area = (Double) res[0];
		                    id=(Integer) res[1];
							if (area > quadArea) {
								quadArea = area;
								fipa = ia;
								fipb = ib;
								fipc = ic;
								fipd = id;
							}
						}
					}
					
					if(quadArea > finalArea) {
						finalArea = quadArea;
						pf0 = fipa;
						pf1 = fipb;
						pf2 = fipc;
						pf3 = fipd;
					}
				}
				
				// Tranfer the points to a polygon
				_largestQuad = new CIPolygon();
				_largestQuad.addPoint((CIPoint)pts.get(pf0));
				_largestQuad.addPoint((CIPoint)pts.get(pf1));
				_largestQuad.addPoint((CIPoint)pts.get(pf2));
				_largestQuad.addPoint((CIPoint)pts.get(pf3));
				_largestQuad.addPoint((CIPoint)pts.get(pf0));
			}
			return _largestQuad;
		}
		
		
		/**
		 * Computes the enclosing rectangle (rotated) that includes the 
		 * convex hull
		 * @return A polygon that is a rotated rectangle.
		 */
		public CIPolygon enclosingRect()
		{
			if (_enclosingRect == null) {
				convexHull();
				
				int num = _convexHull.getNumPoints();
				CIList pts = _convexHull.getPoints();
				double minx=0, miny=0, maxx=0, maxy=0;
				int minxp=0, minyp=0, maxxp=0, maxyp=0;
				double ang,dis;
				double xx,yy;
				double area;
				double min_area = 0.0;
				double p1x=0, p1y=0, p2x=0, p2y=0, p3x=0, p3y=0, p4x=0, p4y=0;
				
				if (num < 2) {  // is just a point
					_enclosingRect = new CIPolygon();
					_enclosingRect.addPoint((CIPoint)pts.get(0));
					_enclosingRect.addPoint((CIPoint)pts.get(0));
					_enclosingRect.addPoint((CIPoint)pts.get(0));
					_enclosingRect.addPoint((CIPoint)pts.get(0));
					_enclosingRect.addPoint((CIPoint)pts.get(0));
				}
				else if (num < 3) {  // is a line with just two points
					_enclosingRect = new CIPolygon();
					_enclosingRect.addPoint((CIPoint)pts.get(0));
					_enclosingRect.addPoint((CIPoint)pts.get(1));
					_enclosingRect.addPoint((CIPoint)pts.get(1));
					_enclosingRect.addPoint((CIPoint)pts.get(0));
					_enclosingRect.addPoint((CIPoint)pts.get(0));
				}
				else {  // ok it's normal :-)
					for(int i=0; i < num-1; i++) {
						for(int a=0; a < num; a++) {
							
							CIVector v1 = new CIVector((CIPoint)pts.get(i), (CIPoint)pts.get(i+1));
							CIVector v2 = new CIVector((CIPoint)pts.get(i), (CIPoint)pts.get(a));
							ang = CIFunction.angle(v1, v2);
							
							dis = v2.length();
							xx = dis*Math.cos(ang);
							yy = dis*Math.sin(ang);
							
							if(a==0) {
								minx=maxx=xx;
								miny=maxy=yy;
								minxp=maxxp=minyp=maxyp=0;
							}
							if(xx<minx) {
								minxp=a;
								minx=xx;
							}
							if(xx>maxx) {
								maxxp=a;
								maxx=xx;
							}
							if(yy<miny) {
								minyp=a;
								miny=yy;
							}
							if(yy>maxy) {
								maxyp=a;
								maxy=yy;
							}
							
							//delete v1;
							//delete v2;        
						}   
						CIPoint p1 = CIFunction.closest((CIPoint)pts.get(i), (CIPoint)pts.get(i+1), (CIPoint)pts.get(minxp));
						CIPoint p2 = CIFunction.closest((CIPoint)pts.get(i), (CIPoint)pts.get(i+1), (CIPoint)pts.get(maxxp));
						
						CIPoint paux = new CIPoint(((CIPoint)pts.get(i)).x + 100, ((CIPoint)pts.get(i)).y);
						CIVector v3= new CIVector((CIPoint)pts.get(i), paux);
						CIVector v4= new CIVector((CIPoint)pts.get(i), (CIPoint)pts.get(i+1));
						ang = CIFunction.angle(v3, v4);
						
						CIPoint paux1 = new CIPoint(p1.x+100*Math.cos(ang+M_PI_2), p1.y+100*Math.sin(ang+M_PI_2));
						CIPoint paux2 = new CIPoint(p2.x+100*Math.cos(ang+M_PI_2), p2.y+100*Math.sin(ang+M_PI_2));
						
						CIPoint p3 = CIFunction.closest(p2,paux2, (CIPoint)pts.get(maxyp));
						CIPoint p4 = CIFunction.closest(p1,paux1, (CIPoint)pts.get(maxyp));
						
						area = CIFunction.quadArea(p1,p2,p3,p4);
						
						if ((i==0)||(area < min_area))
						{
							min_area = area;
							p1x=p1.x;
							p1y=p1.y;
							p2x=p2.x;
							p2y=p2.y;
							p3x=p3.x;
							p3y=p3.y;
							p4x=p4.x;
							p4y=p4.y;
						}
						
					}
					_enclosingRect = new CIPolygon();
					_enclosingRect.addPoint(new CIPoint(p1x, p1y));
					_enclosingRect.addPoint(new CIPoint(p2x, p2y));
					_enclosingRect.addPoint(new CIPoint(p3x, p3y));
					_enclosingRect.addPoint(new CIPoint(p4x, p4y));
					_enclosingRect.addPoint(new CIPoint(p1x, p1y));
				}
			}
			
			return _enclosingRect;
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Returns the first point of the scribble.
		 +----------------------------------------------------------------------------*/
		public CIPoint startingPoint()
		{
			CIStroke s = (CIStroke)_strokes.get(0);
			return (CIPoint)s.getPoints().get(0);
			
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Returns the last point of the scribble.
		 +----------------------------------------------------------------------------*/
		public CIPoint endingPoint()
		{       
			CIStroke strk = (CIStroke)_strokes.get(getNumStrokes()-1);
			
			return (CIPoint)strk.getPoints().get(strk.getNumPoints()-1);
		}
		
		
		/*----------------------------------------------------------------------------+
		 | Description: Writes all the points of the scribble to a file
          +----------------------------------------------------------------------------
		public void writeTo(FileOutputStream fos) 
		{
			int ns, np;
			CIStroke strk;
			CIList pts;
			
			ns = getNumStrokes();
			
			PrintWriter pw = new PrintWriter(fos);
			
			pw.println(ns);
			
			for (int s=0; s<ns; s++) {
				strk = (CIStroke)_strokes.get(s);
				np = strk.getNumPoints();
				pts = strk.getPoints();
				pw.println(np);
				
				for (int p=0; p<np; p++) {
					CIPoint point = (CIPoint)pts.get(p);
					pw.println(point.x + " " + point.y + " " + point.getTime());
				}
			}
         } */
		
		/*----------------------------------------------------------------------------+
		 | Description: Reads all the points of the scribble from a file
          +----------------------------------------------------------------------------
//		Store and retrieve methods
		public void readFrom(FileInputStream fis, boolean useTime) 
		{
			int ns, np, i, k, x, y;
			double t = 0;
			CIStroke strk;
			
			// Used to be a C++ input stream
			//TODO Implement this properly.
			/*
			 ar >> ns;
			 for(i=0; i < ns; i++) {
			 ar >> np;
			 for(k=0; k < np; k++) {
			 if (useTime)
			 ar >> x >> y >> t;
			 else
			 ar >> x >> y;
			 if (k==0)
			 strk = new CIStroke();
			 strk.addPoint(x,y,t);
			 }
			 addStroke(strk);
			 }
			 */
    /*}
		
		public void readFrom(FileInputStream fis) {
			this.readFrom(fis, false);
         } */
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes the number of points inside a small triangle, computed
		 |              from the largest triangle.
		 | Output: Number of points inside.
		 | Notes: Some of the lines commented were used to return a list with the points
		 |        inside.
		 +----------------------------------------------------------------------------*/
		public int ptsInSmallTri ()
		{
			//CIPolygon *inP = new CIPolygon();
			CIPolygon tri = smallTriangle();
			CIList points = tri.getPoints();
			CIPoint[] pt = new CIPoint[3];  // points of the triangle
			CIPoint cp; // current point of the scribble
			double[] m = new double[3];
			double dx, dy;
			double[] x = new double[3];
			int i, inter;
			int ns, np;
			CIStroke strk;
			CIList pts;
			
			int empty = 0; // number of points inside the triangle
			
			for (i=0; i<3; i++) {
				pt[i] = (CIPoint)points.get(i); // just to be faster!
			}
			
			for (i=0; i<3; i++) {
				dx = pt[i].x - pt[(i+1)%3].x;
				if (dx == 0) {
					m[i] = BIG;
					continue;
				}
				dy = pt[i].y - pt[(i+1)%3].y;
				m[i] = dy/dx;
			}
			
//			Computation of the number of points of the scribble inside the triangle
			
			ns = getNumStrokes();
			for (int s=0; s<ns; s++) {
				strk = (CIStroke)_strokes.get(s);
				np = strk.getNumPoints();
				pts = strk.getPoints();
				
				for (int p=0; p<np; p++) {
					cp = (CIPoint)pts.get(p);
					inter = 0;
					if (cp.x >= pt[0].x && cp.x >= pt[1].x && cp.x >= pt[2].x) {
						continue;
					}
					else if (cp.x <= pt[0].x && cp.x <= pt[1].x && cp.x <= pt[2].x) {
						continue;
					}
					else if (cp.y >= pt[0].y && cp.y >= pt[1].y && cp.y >= pt[2].y) {
						continue;
					}
					else if (cp.y <= pt[0].y && cp.y <= pt[1].y && cp.y <= pt[2].y) {
						continue;
					}
					else {
						for (i=0; i<3; i++) {
							if (m[i] == 0) {
								continue;
							}
							else if (m[i] >= BIG) {
								x[i] = pt[i].x;
								if (x[i] >= cp.x) {
									inter++;
								}
							}
							else {
								x[i] = (double) (cp.y - pt[i].y + m[i]*pt[i].x)/m[i];
								if (x[i] >= cp.x && (x[i] < ((pt[i].x > pt[(i+1) %3].x) ? pt[i].x : pt[(i+1) %3].x))) {
									inter++;
								}
								
							}
						}
						if ((inter%2)!=0) {
							empty++;
						}
						
						//inP.addPoint(cp);
					}
				}
			}
			//return inP;
			
			//delete tri;
			
			return empty;
		}
		
		
		/*----------------------------------------------------------------------------+
		 | Description: Return the number of points of the scribble
		 +----------------------------------------------------------------------------*/
		public int getNumPoints()
		{
			int ns, nPoints;
			
			nPoints = 0;
			ns = getNumStrokes();
			for (int s=0; s<ns; s++) {
				nPoints += ((CIStroke)_strokes.get(s)).getNumPoints();
			}
			return nPoints;
		}
		
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes the sum of all the absolute movements in X
		 +----------------------------------------------------------------------------*/
		public double hMovement()
		{
			int ns, np;
			CIStroke strk;
			CIList pts;
			double mov;
			
			mov = 0;
			ns = getNumStrokes();
			for (int s=0; s<ns; s++) {
				strk = (CIStroke)_strokes.get(s);
				np = strk.getNumPoints();
				pts = strk.getPoints();
				
				for (int p=0; p<np-1; p++) {
					mov += Math.abs(((CIPoint)pts.get(p+1)).x - ((CIPoint)pts.get(p)).x);
				}
			}
			return mov;
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes the sum of all the absolute movements in Y
		 +----------------------------------------------------------------------------*/
		public double vMovement()
		{
			int ns, np;
			CIStroke strk;
			CIList pts;
			double mov;
			
			mov = 0;
			ns = getNumStrokes();
			for (int s=0; s<ns; s++) {
				strk = (CIStroke)_strokes.get(s);
				np = strk.getNumPoints();
				pts = strk.getPoints();
				
				for (int p=0; p<np-1; p++) {
					mov += Math.abs(((CIPoint)pts.get(p+1)).y - ((CIPoint)pts.get(p)).y);
				}
			}
			return mov;
		}
		/*----------------------------------------------------------------------------+
		 | Description: Computes the distance between xmin and xmax
		 +----------------------------------------------------------------------------*/
		public double hDist()
		{
			boolean flag = false;
		
			double xmin = 0.0, xmax = 0.0; 

			int ns = getNumStrokes();
			for (int s=0; s<ns; s++) {
				CIStroke strk = (CIStroke)_strokes.get(s);
				int np = strk.getNumPoints();
				CIList pts = strk.getPoints();
				for (int p=0; p<np; p++) {
					int x = (int)((CIPoint)pts.get(p)).x;
					if (!flag) {
						xmin = x;
						xmax = x;
						flag = true;
					} else {
						if (x<xmin) xmin = x;
						if (x>xmax) xmax = x;
					}
				}
			}
			if (flag) {
				return xmax-xmin;
			} else {
				return 0.0;
			}
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes the distance between ymin and ymax
		 +----------------------------------------------------------------------------*/
		public double vDist()
		{
			boolean flag = false;
			
				double ymin = 0.0, ymax = 0.0; 

				int ns = getNumStrokes();
				for (int s=0; s<ns; s++) {
					CIStroke strk = (CIStroke)_strokes.get(s);
					int np = strk.getNumPoints();
					CIList pts = strk.getPoints();
					for (int p=0; p<np; p++) {
						int y = (int)((CIPoint)pts.get(p)).y;
						if (!flag) {
							ymin = y;
							ymax = y;
							flag = true;
						} else {
							if (y<ymin) ymin = y;
							if (y>ymax) ymax = y;
						}
					}
				}
				if (flag) {
					return ymax-ymin;
				} else {
					return 0.0;
				}
		}
		
//		------------------------------------------------------------
//		------ Private Members -------------------------------------
		
		/*----------------------------------------------------------------------------+
		 | Description: Computes a small triangle that is 60% of the largest triangle.
		 | Output: A polygon
		 | Notes:
		 +----------------------------------------------------------------------------*/
		public CIPolygon smallTriangle()
		{
			CIPolygon tri = new CIPolygon();
			CIList points;
			CIPoint p1, p2, p3;
			CIPoint m1, m2, m3;
			CIPoint t1, t2, t3;
			
			if (_largestTriangle == null) {
				largestTriangle();
			}
			
			if (_largestTriangle == null) {
				points = null;
			}
			
			points = _largestTriangle.getPoints();
			
			p1 = (CIPoint)points.get(0);
			p2 = (CIPoint)points.get(1);
			p3 = (CIPoint)points.get(2);
			
			m1 = new CIPoint();
			m1.x = p3.x + (p1.x - p3.x)/2;
			m1.y = p3.y + (p1.y - p3.y)/2;
			m2 = new CIPoint();
			m2.x = p1.x + (p2.x - p1.x)/2;
			m2.y = p1.y + (p2.y - p1.y)/2;
			m3 = new CIPoint();
			m3.x = p2.x + (p3.x - p2.x)/2;
			m3.y = p2.y + (p3.y - p2.y)/2;
			
			t1 = new CIPoint();
			t1.x = m3.x + (p1.x - m3.x)*0.6;
			t1.y = m3.y + (p1.y - m3.y)*0.6;
			t2 = new CIPoint();
			t2.x = m1.x + (p2.x - m1.x)*0.6;
			t2.y = m1.y + (p2.y - m1.y)*0.6;
			t3 = new CIPoint();
			t3.x = m2.x + (p3.x - m2.x)*0.6;
			t3.y = m2.y + (p3.y - m2.y)*0.6;
			
			tri.addPoint(t1);
			tri.addPoint(t2);
			tri.addPoint(t3);
			tri.addPoint(t1);
			
			return tri;
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Reduce the number of points from the convex hull, eliminating 
		 |              the points that are too close (5 pixels).
		 +----------------------------------------------------------------------------*/
		public CIPolygon filterConvexHull()
		{
			CIPolygon _convexHull2 = new CIPolygon();
			int np, i;
			CIList pts;
			CIPoint pt, pti;
			
			pts = _convexHull.getPoints();
			np = _convexHull.getNumPoints();
			pt = (CIPoint)pts.get(0);
			_convexHull2.push(pt);
			
			for (i=1; i<np; i++) {
				pti = (CIPoint)pts.get(i);
				if (CIFunction.distance(pt, pti) > 5) {
					_convexHull2.push(pti);
					pt = pti;
				}
				else if (i == np-1) {
					_convexHull2.push((CIPoint)pts.get(0));
				}
			}
			// delete _convexHull;
			return _convexHull2;
		}
		
		
		/*----------------------------------------------------------------------------+
		 | Description: Selects the point with the lowest y
		 +----------------------------------------------------------------------------*/
		public CIPoint findLowest ()
		{
			CIPoint min;
			int ns, np, s, p;
			CIList pts;
			
			
			min = (CIPoint)((CIStroke)_strokes.get(0)).getPoints().get(0); // gets the first point of the first stroke
			ns = getNumStrokes();
			for (s=0; s<ns; s++) {
				np = ((CIStroke)_strokes.get(s)).getNumPoints();
				pts = ((CIStroke)_strokes.get(s)).getPoints();
				for (p=0; p<np; p++) { 
					CIPoint tp = (CIPoint)pts.get(p);
					if (tp.y < min.y) {
						min = tp;
					}
					else if (tp.y == min.y && tp.x > min.x) {
						min = tp;
					}
				}
			}
			return min;
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Order all scribble points by angle.
		 | Output: A list of points order by angle
		 | Notes: This method is used during the computation of the convex hull.
		 +----------------------------------------------------------------------------*/
		public CIList ordPoints(CIList ordedPoints, CIPoint min)
		{
			int ns, np, s, p;
			CIList pts;
			CIListNode ptr;
			double ang;
			
			ordedPoints.insertInOrder(min, 0);
			
			ns = getNumStrokes();
			for (s=0; s<ns; s++) {
				np = ((CIStroke)_strokes.get(s)).getNumPoints();
				pts = ((CIStroke)_strokes.get(s)).getPoints();
				for (p=0; p<np; p++) {
					CIPoint tp = (CIPoint)pts.get(p);
					ang = CIFunction.theta (min, tp);
					ptr = ordedPoints.insertInOrder(tp, ang);
					if (ptr != null) { // there is another point with the same angle
						// so we compute the distance and save the big one.
						if (CIFunction.distance(min, tp) > CIFunction.distance (min, (CIPoint)ptr.getItem())) {
							ptr.setItem(tp);
						}
					}
				}
			}
			return ordedPoints;
		}
		
		
		/*----------------------------------------------------------------------------+
		 | Description: Auxiliar method used during the computation of the largest triangle
		 +----------------------------------------------------------------------------*/
    	public Object[] compRootedTri(CIList pts, int ripa, int ripb, int ripc, int np)
		{
			CIPoint pa,pb,pc;
			int ia, ib, ic;
			double area, _trigArea = 0;
			
//			computes one rooted triangle        
			ia = ripa;
			ib = ripb;
			for(ic=ripc; ic < np - 1; ic++ ) {
				pa = (CIPoint)(pts.get(ia));
				pb = (CIPoint)(pts.get(ib));
				pc = (CIPoint)(pts.get(ic));
				if( (area=CIFunction.triangleArea(pa, pb, pc)) > _trigArea ) {
					ripc = ic;
					_trigArea = area;
				}
				else {
					break;
				}
			}
        	return new Object[] {_trigArea,ripc} ;
		}
		
		/*----------------------------------------------------------------------------+
		 | Description: Auxiliar method used during the computation of the largest 
		 |              quadrilateral
		 +----------------------------------------------------------------------------*/
    	public Object[] compRootedQuad(CIList pts, int ripa, int ripb, int ripc, int ripd, int np)
		{
			CIPoint pa,pb,pc,pd;
			int id;
			
			double area, _trigArea = 0;
			
//			computes one rooted triangle        
			pa = (CIPoint)(pts.get(ripa));
			pb = (CIPoint)(pts.get(ripb));
			pc = (CIPoint)(pts.get(ripc));
			for(id=ripd; id < np - 1; id++ ) {
				pd = (CIPoint)(pts.get(id));
				if( (area=CIFunction.quadArea(pa, pb, pc, pd)) > _trigArea ) {
					ripd = id;
					_trigArea = area;
				}
				else {
					break;
				}
			}
        	return new Object[] {_trigArea,ripd} ;
		}
		
		
//		------------------------------------------------------------
		/* This cicle uses all the points of the scribble
		 int ns, np;
		 CIStroke *strk;
		 CIList<CIPoint *> *pts;
		 
		 ns = getNumStrokes();
		 for (int s=0; s<ns; s++) {
		 strk = (*_strokes)[s];
		 np = strk.getNumPoints();
		 pts = strk.getPoints();
		 for (int p=0; p<np; p++) {
		 
		 // do something with the points.
		  // (*pts)[p]
		   
		   }
		   }
		   
		   */
		
//		---------- To Delete one day ------------------
		
		public CIPolygon largestTriangleOld()
		{
			if (_largestTriangleOld == null) {
				convexHull();
				
				CIPoint p1,p2,p3;
				CIPoint[] _trigPts = new CIPoint[3];
				int i,j,k;
				double area;
				double _trigArea = 0;
				int numPts = _convexHull.getNumPoints();
				CIList pts = _convexHull.getPoints();
				
				for( i=0; i < numPts; i++ )
					for( j=0; j< numPts; j++ )
						for( k=0; k<numPts; k++ ) {
							p1 = (CIPoint)(pts.get(i));
							p2 = (CIPoint)(pts.get(j));
							p3 = (CIPoint)(pts.get(k));
							if( (area=CIFunction.triangleArea(p1, p2, p3)) > _trigArea ) {
								_trigPts[0]=p1;
								_trigPts[1]=p2;
								_trigPts[2]=p3;
								_trigArea = area;
							}
						}
				
				// Tranfer the points to a polygon
				_largestTriangleOld = new CIPolygon();
				_largestTriangleOld.addPoint(_trigPts[0]);
				_largestTriangleOld.addPoint(_trigPts[1]);
				_largestTriangleOld.addPoint(_trigPts[2]);
				_largestTriangleOld.addPoint(_trigPts[0]);
			}
			return _largestTriangleOld;
		}  
		
		
		public CIPolygon largestQuadOld()
		{
			if (_largestQuadOld == null) {
				convexHull();
				
				CIPoint p1,p2,p3,p4;
				CIPoint[] _trigPts = new CIPoint[4];
				int i,j,k,l;
				double area, _quadArea = 0;
				int numPts = _convexHull.getNumPoints();
				CIList pts = _convexHull.getPoints();
				
				for( i=0; i < numPts; i++ )
					for( j=0; j< numPts; j++ )
						for( k=0; k<numPts; k++ )
							for( l=0; l<numPts; l++ ) {
								p1 = (CIPoint)pts.get(i);
								p2 = (CIPoint)pts.get(j);
								p3 = (CIPoint)pts.get(k);
								p4 = (CIPoint)pts.get(l);
								if( (area=CIFunction.quadArea(p1, p2, p3, p4)) > _quadArea ) {
									_trigPts[0]=p1;
									_trigPts[1]=p2;
									_trigPts[2]=p3;
									_trigPts[3]=p4;
									_quadArea = area;
								}
							}
				
				// Tranfer the points to a polygon
				_largestQuadOld = new CIPolygon();
				_largestQuadOld.addPoint(_trigPts[0]);
				_largestQuadOld.addPoint(_trigPts[1]);
				_largestQuadOld.addPoint(_trigPts[2]);
				_largestQuadOld.addPoint(_trigPts[3]);
				_largestQuadOld.addPoint(_trigPts[0]);
			}
			return _largestQuadOld;
		}
		
		
	}
	
