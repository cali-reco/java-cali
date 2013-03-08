package pt.inevo.jcali;
/* Starting header file: CIVector.h */
/*--------------------------------------------------------- -*- Mode: C++ -*- +
| Module: CIVector.h
+-----------------------------------------------------------------------------+
| Description: Defines a vector using two points.
| 
| Notes:       
|
| Author: Manuel Joao Fonseca
|	  e-mail: mjf@ist.utl.pt
|
| Date: April 99
|
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

public class CIVector {
    public CIPoint startp;
    public CIPoint endp;
    
    public CIVector(double sx, double sy, double ex, double ey) {
    	startp = new CIPoint(sx, sy); 
    	endp = new CIPoint(ex, ey);
    }
    
    public CIVector() {
    	this(0, 0, 0, 0);    	
    }

    public CIVector(CIPoint a, CIPoint b) { 
    	startp = a; 
    	endp = b; 
    }

    public double length() {
    	return CIFunction.distance(startp, endp);
    }
}