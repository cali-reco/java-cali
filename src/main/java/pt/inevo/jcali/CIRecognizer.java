package pt.inevo.jcali;
/**
 * A recognizer that uses fuzzy logic. It can recognize
 * multistroke and unistroke gestures, invariant in rotation and
 * scale.
 * It uses a list of all possible shapes and commands, and during
 * the recognition process computes the similarity between the
 * scribble and the different gestures.
 */
public class CIRecognizer {
    protected CIList _shapesList;
    protected double _alfaCut;
    protected CIShape _unknownShape;
    protected CITap _tap;
    
   public CIList getShapesList() {
        return _shapesList;
    }
    /**
     * Constructor 
     * @param rotated Looks like this doesn't do anything
     * @param alfaCut If the probability of a shape is below this 
     * threshold, it will not be returned
     */
    public CIRecognizer (boolean rotated, double alfaCut)
    {
        _alfaCut = alfaCut;
        
        _shapesList = new CIList();
        _unknownShape = new CIUnknown();
        _tap = new CITap();
        
//      Gestures (Shapes and Commands) identified by the recognizer. 
//      The next lines create a list with all gestures identified by the recognizer
//      If you want to add a new gesture to the recognizer, just add it to the list.
        
        //_shapesList.insertTail(new CIDelete());
      
//      Commands ----------
        _shapesList.insertTail(new CIDelete());
        _shapesList.insertTail(new CIWavyLine());
        _shapesList.insertTail(new CICopy());
        _shapesList.insertTail(new CIMove());
        //_shapesList->insertTail(new CITap());
        _shapesList.insertTail(new CICross());
        
//      Shapes ------------
        _shapesList.insertTail(new CILine(rotated));
        _shapesList.insertTail(new CITriangle(rotated));
        _shapesList.insertTail(new CIRectangle(rotated));
        _shapesList.insertTail(new CICircle(rotated));
        _shapesList.insertTail(new CIEllipse(rotated));
        _shapesList.insertTail(new CIDiamond(rotated));
        _shapesList.insertTail(new CIArrow(rotated));
  

    }
    
    /**
     * Identifies shapes based on a scribble. It starts by looking
     * for global geometric features and then for local features
     * 
     * Notes: If the application wants to manipulate the gestures returned as new
     * entities, it must clone them, because the gestures return by the
     *        recognizer are always the same. (The gestures returned are the ones
     *        created in the recognizer constructor)
     * 
     * @param sc A scribble
     * @return A list of plausible shapes ordered by degree of certainty.
     *
     */
    public CIList recognize(CIScribble sc)
    {
        double val, val2;
        int i;
        int nshapes = _shapesList.getNumItems();
        
        CIList _shapes2Return = new CIList();
        
        for (i=0; i<nshapes; i++) {     // set doms of all gestures to zero
            ((CIGesture)_shapesList.get(i)).resetDom();
        }
        
        if (sc.getLen() < 10) {
            _tap.setUp(sc);
            _shapes2Return.insertInOrder(_tap, 1 - 0);
        } 
        else {
            
            /*
             if (sc->getNumPoints() == 1) { // This piece of code is used to 
             CIStroke *strk;            // avoid scribbles of just one point
             CIList<CIPoint *> *pts;
             
             strk = (*sc->getStrokes())[0];
             pts = strk->getPoints();
             strk->addPoint((*pts)[0]->x,(*pts)[0]->y);
             }
             */
            
            for (i=0; i<nshapes; i++) {
                String nam = ((CIGesture)_shapesList.get(i)).getName(); // Para apagar
                //System.out.println("--- recognize ---");
                val = ((CIGesture)_shapesList.get(i)).evalGlobalFeatures(sc);
                //System.out.println("-----------------");
        
                // if probability is above alfa valua ...
                if (val > _alfaCut) {
                    val2 = ((CIGesture)_shapesList.get(i)).evalLocalFeatures(sc, _shapesList);
                    if (val2 < val) {
                        val = val2;
                    }
                    if (val > _alfaCut) {
                        nam = ((CIGesture)_shapesList.get(i)).getName(); // Para apagar
                        _shapes2Return.insertInOrder((CIGesture)_shapesList.get(i), 1 - val); 
                        // (1-val) is used because the method insertInOrder creates an
                        // ascendant list, and we want a descendant one.
                    }
                }
            }
        }
        
        if (_shapes2Return.getNumItems() == 0) {
            _unknownShape.setUp(sc);
            _shapes2Return.insertInOrder(_unknownShape, 1 - 0);
        }
        
        return _shapes2Return;
    }
}

