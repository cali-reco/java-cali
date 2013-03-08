package pt.inevo.jcali;
/**
 * This class represents the Delete gesture.
 * This gesture is like a zigzag line.
 */
public class CIDelete extends CICommand implements Values {
    
    public CIDelete (CIScribble sc, double dom) { 
        _sc = sc; 
        _dom = dom; 
        _features = null; 
        }

    public Object clone() { 
        return new CIDelete(_sc, _dom); 
    }
    
    public String getName() { 
        return "Delete"; 
        }

    public CIDelete ()
    {
    	/*
    	_features = new CIFeatures ("Her_Wer", 0.06, 0.08, 1, 1
                                   // ,&CIEvaluate::Ach_Aer, 0.62, 0.65, 1, 1   // Not bold Arrows
                                   ,"Tl_Pch", 1.5, 1.9, BIG, BIG    // Identifies
                                   ,"Hollowness", 0, 3, BIG, BIG  // Not Shapes
                                   ,"Ns", 1, 1, 1, 1
                                   );
        */
        _features = new CIFeatures (
        		CIEvaluate.Her_Wer, 0.06, 0.08, 1, 1
                // ,&CIEvaluate::Ach_Aer, 0.62, 0.65, 1, 1   // Not bold Arrows
        		//,CIEvaluate.Max_vM_vD_hM_hD, 4.0, 2000, BIG, BIG
                ,CIEvaluate.Tl_Pch, 1.5, 1.9, BIG, BIG    // Identifies
                ,CIEvaluate.Hollowness, 0, 3, BIG, BIG  // Not Shapes
                ,CIEvaluate.Ns, 1, 1, 1, 1
        );

    }

}

