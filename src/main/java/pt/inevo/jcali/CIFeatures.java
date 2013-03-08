package pt.inevo.jcali;
/**
 * This class represents the features that will classify a gesture (shape/command).
 * Notes: It works as a way to simplify the definition of features for each gesture.
 */
public class CIFeatures {
	// CIList of CIFuzzyNodes
	protected CIList _nodesList; //of cifuzzynodes !
    
    public CIList getNodesList() {
        return _nodesList;
    }

    public CIFeatures(CIEvaluate ptrF, double awa, double a, double b, double bwb)
    {
        _nodesList = new CIList();
        
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a, b, a-awa, bwb-b), ptrF));
    }
    
    public CIFeatures (CIEvaluate ptrF1, double awa1, double a1, double b1, double bwb1,
            CIEvaluate ptrF2, double awa2, double a2, double b2, double bwb2)
    {
        _nodesList = new CIList();
        
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a1, b1, a1-awa1, bwb1-b1), ptrF1));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a2, b2, a2-awa2, bwb2-b2), ptrF2));
    }
    
    public CIFeatures (CIEvaluate ptrF1, double awa1, double a1, double b1, double bwb1,
            CIEvaluate ptrF2, double awa2, double a2, double b2, double bwb2,
            CIEvaluate ptrF3, double awa3, double a3, double b3, double bwb3)
    {
        _nodesList = new CIList();
        
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a1, b1, a1-awa1, bwb1-b1), ptrF1));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a2, b2, a2-awa2, bwb2-b2), ptrF2));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a3, b3, a3-awa3, bwb3-b3), ptrF3));
    }
    
    public CIFeatures (CIEvaluate ptrF1, double awa1, double a1, double b1, double bwb1,
            CIEvaluate ptrF2, double awa2, double a2, double b2, double bwb2,
            CIEvaluate ptrF3, double awa3, double a3, double b3, double bwb3,
            CIEvaluate ptrF4, double awa4, double a4, double b4, double bwb4)
    {
        _nodesList = new CIList();
        
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a1, b1, a1-awa1, bwb1-b1), ptrF1));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a2, b2, a2-awa2, bwb2-b2), ptrF2));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a3, b3, a3-awa3, bwb3-b3), ptrF3));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a4, b4, a4-awa4, bwb4-b4), ptrF4));
    }
    
    public CIFeatures (CIEvaluate ptrF1, double awa1, double a1, double b1, double bwb1,
            CIEvaluate ptrF2, double awa2, double a2, double b2, double bwb2,
            CIEvaluate ptrF3, double awa3, double a3, double b3, double bwb3,
            CIEvaluate ptrF4, double awa4, double a4, double b4, double bwb4,
            CIEvaluate ptrF5, double awa5, double a5, double b5, double bwb5)
    {
        _nodesList = new CIList();
        
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a1, b1, a1-awa1, bwb1-b1), ptrF1));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a2, b2, a2-awa2, bwb2-b2), ptrF2));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a3, b3, a3-awa3, bwb3-b3), ptrF3));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a4, b4, a4-awa4, bwb4-b4), ptrF4));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a5, b5, a5-awa5, bwb5-b5), ptrF5));
    }
    
    public CIFeatures (CIEvaluate ptrF1, double awa1, double a1, double b1, double bwb1,
            CIEvaluate ptrF2, double awa2, double a2, double b2, double bwb2,
            CIEvaluate ptrF3, double awa3, double a3, double b3, double bwb3,
            CIEvaluate ptrF4, double awa4, double a4, double b4, double bwb4,
            CIEvaluate ptrF5, double awa5, double a5, double b5, double bwb5,
            CIEvaluate ptrF6, double awa6, double a6, double b6, double bwb6)
    {
        _nodesList = new CIList();
        
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a1, b1, a1-awa1, bwb1-b1), ptrF1));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a2, b2, a2-awa2, bwb2-b2), ptrF2));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a3, b3, a3-awa3, bwb3-b3), ptrF3));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a4, b4, a4-awa4, bwb4-b4), ptrF4));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a5, b5, a5-awa5, bwb5-b5), ptrF5));
        _nodesList.insertTail(new CIFuzzyNode(new CIFuzzySet(a6, b6, a6-awa6, bwb6-b6), ptrF6));
    }
    
    public double evaluate(CIScribble sc)
    {
        if (_nodesList != null) {
            double tmp, dom = 1;
            int nn = _nodesList.getNumItems();
            
            for (int i=0; i<nn; i++)
            {
                tmp = ((CIFuzzyNode)_nodesList.get(i)).dom(sc);
                if (tmp < dom) {
                    dom = tmp;
                }
                if (dom == 0) {
                    break;
                }
            }
            return dom;
        }
        else {
            return 0;
        }
    }
}