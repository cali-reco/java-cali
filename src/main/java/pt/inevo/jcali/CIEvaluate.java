package pt.inevo.jcali;


/**
 * This class defines all the features we can use to classify a gesture.
 * --- Abreviation meaning
 *   Tl     - Scribble total length
 *   Ns     - Number of strokes of the scribble
 *   ch     - Convex Hull
 *   lt     - Largest Triangle
 *   lq     - Largest Quadrilateral
 *   er     - Enclosing Rectangle
 *   bb     - Bounding Box
 *   
 *   P      - Perimeter
 *   A      - Area
 *   
 *   Pch    - Convex Hull perimeter
 *   Pch2   - Convex Hull perimeter squared
 *   ...
 */

public enum CIEvaluate implements Values {
    //private static final boolean debug = false;


    /**
     * length divided by perimeter of convex hull
     */
    Tl_Pch {
        public String help(){
            return "length divided by perimeter of convex hull";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.getLen() / sc.convexHull().perimeter();
            //if (debug) System.out.println("Tl_Pch : "+ret);
            return ret;
        }
    },

    /**
     * squared perimeter of convex hull divided by area of convex hull 
     */
    Pch2_Ach {
        public String help(){
            return "squared perimeter of convex hull divided by area of convex hull";
        }
        public double evaluate(CIScribble sc){
            double ret = Math.pow(sc.convexHull().perimeter(),2) / sc.convexHull().area();
            //if (debug) System.out.println("Pch2_Ach : "+ret);
            return ret;
        }
    },

    /**
     * perimeter of convex hull divided by length divided by number of strokes 
     */
    Pch_Ns_Tl {
        public String help(){
            return "perimeter of convex hull divided by length divided by number of strokes";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.convexHull().perimeter()/(sc.getLen()/sc.getNumStrokes());
            //if (debug) System.out.println("Pch_Ns_Tl : "+ret);
            return ret;
        }
    },

    /**
     * number of points in the small triangle (???)
     */
    Hollowness {
        public String help(){
            return "number of points in the small triangle";
        }
        public double evaluate(CIScribble sc){
            double ret = (double)sc.ptsInSmallTri();
            //if (debug) System.out.println("Hollowness : "+ret);
            return ret;
        }
    },

    /**
     * number of strokes
     */
    Ns{
        public String help(){
            return "number of strokes";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.getNumStrokes();
            //if (debug) System.out.println("Ns : "+ret);
            return ret;
        }
    },

    /**
     * absolute x movement of first part of bounding box divided by total absolute x movement (???)
     */
    Hm_Wbb {
        public String help(){
            return "absolute x movement of first part of bounding box divided by total absolute x movement";
        }
        public double evaluate(CIScribble sc){
            CIList pbb = sc.boundingBox().getPoints();
            double x0 = ((CIPoint)(pbb.get(0))).x;
            double x1 = ((CIPoint)(pbb.get(1))).x;

            double ret = Math.abs( (x0-x1) / sc.hMovement() );
            //if (debug) System.out.println("Hm_Wbb : "+ret);
            return ret;
        }
    },



    /**
     * absolute y movement of second part of bounding box divided by total absolute y movement (???)
     */
    Vm_Hbb {
        public String help(){
            return "absolute y movement of second part of bounding box divided by total absolute y movement";
        }
        public double evaluate(CIScribble sc){
            CIList pbb = sc.boundingBox().getPoints();
            double y2 = ((CIPoint)(pbb.get(2))).y;
            double y1 = ((CIPoint)(pbb.get(1))).y;

            double ret = Math.abs((double)( (y2 - y1) / sc.vMovement()));
            //if (debug) System.out.println("Vm_Hbb : "+ret);
            return ret;
        }
    },

    /**
     * ????
     */
    Hbb_Wbb {
        public String help(){
            return "????";
        }
        public double evaluate(CIScribble sc){
            CIList pbb = sc.boundingBox().getPoints();

            double dw, dh, ret;

            dw = ((CIPoint)pbb.get(1)).x - ((CIPoint)pbb.get(0)).x;
            dh = ((CIPoint)pbb.get(2)).y - ((CIPoint)pbb.get(1)).y;

            if (dw == 0 || dh == 0) {
                ret = 0;
            } else {

                double tmp = Math.abs((double)dh / dw);
                if (tmp > 1) {
                    tmp = 1 / tmp;
                }
                ret = tmp;
            }
            //if (debug) System.out.println("Hbb_Wbb : "+ret);
            return ret;
        }
    },

    /**
     * the length of the diagonal of the enclosing rectangle (rotated) 
     */
    Diag_er {
        public String help(){
            return "the length of the diagonal of the enclosing rectangle (rotated)";
        }
        public double evaluate(CIScribble sc){
            CIList pbb = sc.enclosingRect().getPoints();

            double dw, dh;

            dw = CIFunction.distance((CIPoint)pbb.get(2), (CIPoint)pbb.get(1));
            dh = CIFunction.distance((CIPoint)pbb.get(1), (CIPoint)pbb.get(0));

            double ret = Math.sqrt(dw*dw + dh*dh);
            //if (debug) System.out.println("Diag_er : "+ret);
            return ret;
        }
    },

    /**
     * the ratio between the sides of the enclosing (rotated) rectangle ( 0..1 ) 
     */
    Her_Wer {
        public String help(){
            return "the ratio between the sides of the enclosing (rotated) rectangle ( 0..1 )";
        }
        public double evaluate(CIScribble sc){
            CIList pbb = sc.enclosingRect().getPoints();

            double dw, dh, ret;

            dw = CIFunction.distance((CIPoint)pbb.get(2), (CIPoint)pbb.get(1));
            dh = CIFunction.distance((CIPoint)pbb.get(1), (CIPoint)pbb.get(0));

            if (dw == 0 || dh == 0) {
                ret = 0;
            } else {
                double tmp = dh / dw;
                if (tmp > 1) {
                    tmp = 1 / tmp;
                }
                ret = tmp;
            }
            //if (debug) System.out.println("Her_Wer : "+ret);
            return ret;
        }
    },

    /**
     * number of points in the convex hull divided by number of points 
     */
    pch_psc{
        public String help(){
            return "number of points in the convex hull divided by number of points";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.convexHull().getNumPoints() / (double)sc.getNumPoints();
            //if (debug) System.out.println("pch_psc : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().area() / convexHull().area()
     */
    Alt_Ach {
        public String help(){
            return "largestTriangle().area() / convexHull().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().area() / sc.convexHull().area();
            CIPolygon p=sc.largestTriangle();
            CIPolygon c=sc.convexHull();
            //if (debug) System.out.println("Alt_Ach : "+ret);
            return ret;
        }
    },

    /**
     * convexHull().area() / enclosingRect().area()
     */
    Ach_Aer {
        public String help(){
            return "convexHull().area() / enclosingRect().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.convexHull().area() / sc.enclosingRect().area();
            //if (debug) System.out.println("Ach_Aer : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().area() / enclosingRect().area()
     */
    Alt_Aer {
        public String help(){
            return "largestTriangle().area() / enclosingRect().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().area() / sc.enclosingRect().area();
            //if (debug) System.out.println("Alt_Aer : "+ret);
            return ret;
        }
    },

    /**
     * convexHull().area() / boundingBox().area()
     */
    Ach_Abb{
        public String help(){
            return "convexHull().area() / boundingBox().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.convexHull().area() / sc.boundingBox().area();
            //if (debug) System.out.println("Ach_Abb : "+ret);
            return ret;
        }
    },

    /**
     * largestQuad().area() / boundingBox().area()
     */
    Alq_Abb{
        public String help(){
            return "largestQuad().area() / boundingBox().area(";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestQuad().area() / sc.boundingBox().area();
            //if (debug) System.out.println("Alq_Abb : "+ret);
            return ret;
        }
    },

    /**
     * enclosingRect().area() / boundingBox().area()
     */
    Aer_Abb{
        public String help(){
            return "enclosingRect().area() / boundingBox().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.enclosingRect().area() / sc.boundingBox().area();
            //if (debug) System.out.println("Aer_Abb : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().area() / boundingBox().area()
     */
    Alt_Abb{
        public String help(){
            return "largestTriangle().area() / boundingBox().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().area() / sc.boundingBox().area();
            //if (debug) System.out.println("Alt_Abb : "+ret);
            return ret;
        }
    },

    /**
     * largestQuad().area() / convexHull().area()
     */
    Alq_Ach{
        public String help(){
            return "largestQuad().area() / convexHull().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestQuad().area() / sc.convexHull().area();
            //if (debug) System.out.println("Alq_Ach : "+ret);
            return ret;
        }
    },

    /**
     * largestQuad().area() / enclosingRect().area()
     */
    Alq_Aer{
        public String help(){
            return "largestQuad().area() / enclosingRect().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestQuad().area() / sc.enclosingRect().area();
            //if (debug) System.out.println("Alq_Aer : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().area() / largestQuad().area()
     */
    Alt_Alq{
        public String help(){
            return "largestTriangle().area() / largestQuad().area()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().area() / sc.largestQuad().area();
            //if (debug) System.out.println("Alt_Alq : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().perimeter() / convexHull().perimeter()
     */
    Plt_Pch{
        public String help(){
            return "largestTriangle().perimeter() / convexHull().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().perimeter() / sc.convexHull().perimeter();
            //if (debug) System.out.println("Plt_Pch : "+ret);
            return ret;
        }
    },

    /**
     * convexHull().perimeter() / enclosingRect().perimeter()
     */
    Pch_Per{
        public String help(){
            return "convexHull().perimeter() / enclosingRect().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.convexHull().perimeter()/sc.enclosingRect().perimeter();
            //if (debug) System.out.println("Pch_Per : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().perimeter() / enclosingRect().perimeter()
     */
    Plt_Per{
        public String help(){
            return "largestTriangle().perimeter() / enclosingRect().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().perimeter()/sc.enclosingRect().perimeter();
            //if (debug) System.out.println("Plt_Per : "+ret);
            return ret;
        }
    },

    /**
     * convexHull().perimeter() / boundingBox().perimeter()
     */
    Pch_Pbb{
        public String help(){
            return "convexHull().perimeter() / boundingBox().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.convexHull().perimeter() / sc.boundingBox().perimeter();
            //if (debug) System.out.println("Pch_Pbb : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().perimeter() / boundingBox().perimeter()
     */
    Plt_Pbb{
        public String help(){
            return "largestTriangle().perimeter() / boundingBox().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().perimeter() / sc.boundingBox().perimeter();
            //if (debug) System.out.println("Plt_Pbb : "+ret);
            return ret;
        }
    },

    /**
     * largestQuad().perimeter() / convexHull().perimeter()
     */
    Plq_Pch{
        public String help(){
            return "largestQuad().perimeter() / convexHull().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestQuad().perimeter() / sc.convexHull().perimeter();
            //if (debug) System.out.println("Plq_Pch : "+ret);
            return ret;
        }
    },

    /**
     * largestQuad().perimeter() / enclosingRect().perimeter()
     */
    Plq_Per{
        public String help(){
            return "largestQuad().perimeter() / enclosingRect().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestQuad().perimeter() / sc.enclosingRect().perimeter();
            //if (debug) System.out.println("Plq_Per : "+ret);
            return ret;
        }
    },

    /**
     * largestTriangle().perimeter() / largestQuad().perimeter()
     */
    Plt_Plq{
        public String help(){
            return "largestTriangle().perimeter() / largestQuad().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestTriangle().perimeter() / sc.largestQuad().perimeter();
            //if (debug) System.out.println("Plt_Plq : "+ret);
            return ret;
        }
    },

    /**
     * largestQuad().perimeter() / boundingBox().perimeter()
     */
    Plq_Pbb{
        public String help(){
            return "largestQuad().perimeter() / boundingBox().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.largestQuad().perimeter() / sc.boundingBox().perimeter();
            //if (debug) System.out.println("Plq_Pbb : "+ret);
            return ret;
        }
    },

    /**
     * enclosingRect().perimeter() / boundingBox().perimeter()
     */
    Per_Pbb{
        public String help(){
            return "enclosingRect().perimeter() / boundingBox().perimeter()";
        }
        public double evaluate(CIScribble sc){
            double ret = sc.enclosingRect().perimeter() / sc.boundingBox().perimeter();
            //if (debug) System.out.println("Per_Pbb : "+ret);
            return ret;
        }
    },

    /**
     * Horizontal movement divided by distance between endpoints 
     */
    /*public static double Hm_Th(CIScribble sc)
	{
    	double ret = sc.hMovement() / Math.abs(sc.endingPoint().x - sc.startingPoint().x);
    	//if (debug) System.out.println("Hm_Th : " + ret);
        return ret;
    }*/

    /**
     * Maximum of (Vert movement divided by vert dist, Horiz movement div by horiz dist)  
     */
    Max_vM_vD_hM_hD{
        public String help(){
            return "Maximum of (Vert movement divided by vert dist, Horiz movement div by horiz dist)";
        }
        public double evaluate(CIScribble sc){
            double ret = Math.max( sc.vMovement() / sc.vDist(), sc.hMovement() / sc.hDist() );
            //if (debug) System.out.println("dunno : " + ret);
            return ret;
        }
    };

    public abstract String help();
    public abstract double evaluate(CIScribble sc);
    /**
     * Prints the values of all the features of a CIScribble 
     */
    public static String getFeatures(CIScribble sc) {
        StringBuffer s = new StringBuffer();
        Class c = CIEvaluate.class;
        Object[] params = { sc };
        for(CIEvaluate e : CIEvaluate.values()) {
            s.append(e.name());
            s.append(": ");
            s.append(e.evaluate(sc));
            s.append('\n');
        }
        return s.toString();


        /*
                        Method m = c.getMethod(_ptrFunc, params);
                        d = (Double)m.invoke(null, new Object[] {sc});
                    } catch ( Exception e ) {
                        e.printStackTrace();
                        return 0;
                    }

                    double tmp = d.doubleValue();

                    if (_fuzzySet!=null) {
                        return _fuzzySet.degOfMember(tmp);
                    }
                    else {
                        return 0;
                    }
                }
                else {
                    return 0;
        */

    }
    }