package pt.inevo.jcali;
/* Starting header file: CIUnknown.h */
 /*--------------------------------------------------------- -*- Mode: C++ -*- +
  | Module: CIUnknown.h
  +-----------------------------------------------------------------------------+
  | Description: This represents all the unrecognized gestures. They can be solid
  |              dashed or bold.
  | 
  | Notes:       
  |
  | Author: Manuel Joao Fonseca
  |	  e-mail: mjf@ist.utl.pt
  |
  | Date: April 98, April 99
  +----------------------------------------------------------------------------
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

public class CIUnknown extends CIShape {
	
	public CIUnknown (CIScribble sc, double dom) { 
		_sc = sc; 
		_dom = dom; 
		_features = null; 
	}
	
	public double evaluate(CIScribble sc) { 
		return 0;
	}
	
	public void draw(Object ptr) {
	}
	
	public String getName () { 
		return ("Unknown"); 
	}
	
	public Object clone() { 
		return new CIUnknown(_sc, _dom); 
	}
	
	public CIUnknown()
	{ 
		_features = null; 
	}
	
	/*----------------------------------------------------------------------------+
	 | Description: Computes some features of the unknown gesture, like if it is 
	 |              solid, dashed or bold.
	 | Input: A scribble.
	 +----------------------------------------------------------------------------*/
	public void setUp(CIScribble sc)
	{
		_sc = sc;
		_dashed = false;
		_bold = false;
		_open = false;
		_dom = 1;
		
		if (_dashFeature.evaluate(sc) != 0.0) {
			_dashed = true;
		}
		else if (_openFeature != null) {
			if (_openFeature.evaluate(sc) != 0.0) {
				_open = true;
			}
			else {
				if (_boldFeature.evaluate(sc) != 0.0) {
					_bold = true;
				}
			}
		}
	}
}

