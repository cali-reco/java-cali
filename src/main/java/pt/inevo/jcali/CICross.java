package pt.inevo.jcali;

public class CICross extends CICommand {
    
    public CICross (CIScribble sc, double dom) { 
        _sc = sc; 
        _dom = dom; 
        _features = null; 
    }
    
    public Object clone() { 
        return new CICross(_sc, _dom); 
    }
    
    public String getName() { 
        return "Cross"; 
    }
    
    
    public CICross ()
    {
        _features = new CIFeatures (CIEvaluate.Alq_Ach, 0.85, 0.95, 1, 1
                ,CIEvaluate.Plq_Pch, 0.98, 0.99, 1, 1
                ,CIEvaluate.Ns, 2, 2, 2, 2
        );
    }
    
    /**
     * This method analyse the scribble taking into account local
     * features of a cross. A cross is built up of two lines that
     * intersect each other. It starts by checking if the two strokes
     * are lines and after if they intersect.
     * @param sc A scribble 
     * @param _shapesList A list of all possible gestures
     */
    public double evalLocalFeatures(CIScribble sc, CIList _shapesList)
    {
        double val1, val2;
        int i;
        int nshapes = _shapesList.getNumItems();
        CIGesture gest = null;
        String name;
        
        val1 = val2 = 0;
        CIScribble scribb = (CIScribble) sc.clone();  // create a new scribble like the original
        CIScribble scLast = new CIScribble();
        scLast.addStroke(scribb.popStroke());  // Last stroke of the scribble
        
        for (i=0; i<nshapes; i++) {     // recognize second stroke
            ((CIGesture)_shapesList.get(i)).pushAttribs();
            
            val1 = ((CIGesture)_shapesList.get(i)).evalGlobalFeatures(scLast);
            if (val1 != 0.0) {
                gest = (CIGesture)_shapesList.get(i); 
                break;
            }
        }
        for (i=0; i<nshapes; i++) {
            ((CIGesture)_shapesList.get(i)).popAttribs();
        }
        
        if (gest != null) {
            name = gest.getName();
            if (!name.equals("Line")) {
                for (i=0; i<nshapes; i++) {     // recognize first stroke
                    ((CIGesture)_shapesList.get(i)).pushAttribs();
                    
                    val2 = ((CIGesture)_shapesList.get(i)).evalGlobalFeatures(scribb);
                    if (val2 != 0.0) {
                        gest = (CIGesture)_shapesList.get(i); 
                        break;
                    }
                }
                for (i=0; i<nshapes; i++) {
                    ((CIGesture)_shapesList.get(i)).popAttribs();
                }
                    
                
                if (gest != null) {
                    name = gest.getName();
                    if (!name.equals("Line")) {
                        val2 = intersect (sc) == false ? 0.0 : 1.0;
                    }
                    else {
                        val2 = 0;
                    }
                }
            } 
            else {
                val1 = 0;
            }
        }
        
        if (val2 < val1) {
            val1 = val2;
        }
        return val1;
    }
    
    /**
     * checks if the two lines of the scribble intersects
     * @param sc A scribble with two strokes
     * @return true if they intersect
     */
    private boolean intersect(CIScribble sc)
    {
        CIList strks;
        CIStroke strk1, strk2;
        int np1, np2;
        CIList pts1, pts2;
        
        strks = sc.getStrokes();
        strk1 = (CIStroke)strks.get(0);
        strk2 = (CIStroke)strks.get(1);
        
        np1 = strk1.getNumPoints();
        np2 = strk2.getNumPoints();
        pts1 = strk1.getPoints();
        pts2 = strk2.getPoints();
        
        CIVector v1 = new CIVector (((CIPoint)pts1.get(0)).x, ((CIPoint)pts1.get(0)).y, ((CIPoint)pts1.get(np1-1)).x, ((CIPoint)pts1.get(np1-1)).y);
        CIVector v2 = new CIVector (((CIPoint)pts2.get(0)).x, ((CIPoint)pts2.get(0)).y, ((CIPoint)pts2.get(np2-1)).x, ((CIPoint)pts2.get(np2-1)).y);
        
        boolean left1, left2;
        left1 = CIFunction.left(v1.startp, v1.endp, v2.startp) ^ CIFunction.left(v1.startp, v1.endp, v2.endp);
        left2 = CIFunction.left(v2.startp, v2.endp, v1.startp) ^ CIFunction.left(v2.startp, v2.endp, v1.endp);
        
        return left1 && left2;
    }
}
