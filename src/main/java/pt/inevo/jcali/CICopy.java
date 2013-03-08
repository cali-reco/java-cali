package pt.inevo.jcali;
  /**
   * This class represents the Copy gesture.
   * The gesture is like a C.
   */
public class CICopy extends CICommand {
    
    public CICopy (CIScribble sc, double dom) { 
        _sc = sc; 
        _dom = dom; 
        _features = null; 
    }
    
    public CICopy ()
    {
        _features = new CIFeatures (CIEvaluate.Pch2_Ach, 13, 14, 18, 23
                ,CIEvaluate.Tl_Pch, 0.45, 0.7, 0.85, 0.9
                ,CIEvaluate.Alt_Ach, 0.45, 0.48, 0.7, 0.75   // Not Ellipses
                ,CIEvaluate.Alt_Alq, 0.62, 0.65, 0.81, 0.83 // Not Rectangles & Diamonds
                ,CIEvaluate.Plq_Pch, 0.9, 0.94, 0.98, 0.989 // Not Rectangles & Diamonds
                ,CIEvaluate.Ns, 1, 1, 1, 1
        );
    }
    
    public Object clone() { 
        return new CICopy(_sc, _dom); 
    }
    
    public String getName() { 
        return "Copy"; 
    }
}
