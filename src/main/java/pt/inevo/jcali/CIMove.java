package pt.inevo.jcali;
/**
 * This class represents the Move gesture.
 * This gesture is like a check mark.
 */
public class CIMove extends CICommand {
	
	public CIMove (CIScribble sc, double dom) {
		_sc = sc; 
		_dom = dom; 
		_features = null;
	}
	
	public Object clone() { 
		return new CIMove(_sc, _dom);
	}
	
	public String getName() { 
		return "Move";
	}
	CIMove () {
		_features = new CIFeatures (CIEvaluate.Alt_Ach, 0.75, 0.85, 1, 1      // These 2 identify
				,CIEvaluate.Plt_Pch, 0.95, 0.98, 1, 1
				,CIEvaluate.Tl_Pch, 0.2, 0.3, 0.83, 0.93 // and these 2 check if is open
				,CIEvaluate.Pch_Ns_Tl, 1.2, 1.3, 2, 2
				,CIEvaluate.Pch2_Ach, 18, 19, 25, 35     // Not Lines
				,CIEvaluate.Ns, 1, 1, 1, 1
		);
	}
}

