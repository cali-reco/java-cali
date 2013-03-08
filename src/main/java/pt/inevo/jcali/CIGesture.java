package pt.inevo.jcali;
/**
 * Base class for all gestures, shapes and commands.
 */ 
public abstract class CIGesture implements Cloneable {
    protected CIScribble _sc;
    protected CIFeatures _features;
    protected double _dom;
    protected CIGesture _prevGesture;
    
    public CIGesture() {
        _sc = null;
        _dom = 0;
        _prevGesture = null;
    }
    
    public abstract String getName();
    public abstract String getGestureType();
    public abstract Object clone();
    
    public abstract double evalGlobalFeatures(CIScribble sc);
    
    public abstract double evalLocalFeatures(CIScribble sc, CIList _shapesList);
    
    public CIScribble getScribble() { 
        return _sc;
    }
    
    public double getDom() { 
        return _dom;
       }
    
    public void resetDom() { _dom = 0; }
    
    public void pushAttribs () {
        _prevGesture = (CIGesture)clone();
    }
    public CIFeatures getFeatures() {
        return _features;
    }
    public abstract void popAttribs ();
}




