package pt.inevo.jcali;
/**
 * Class that abstracts a node of a double linked list. The node
 * is build of an item where we save the information (data) and two
 * pointers, one to the next node in the list and the other to the 
 * previous node.
 */

//template <class Item>

public class CIListNode <T> implements Comparable<T>{
    protected T _item;
    
    protected CIListNode <T> _next;
    protected CIListNode <T> _prev;
    
    protected int _idx;	 // index value of the last item retrieved
    protected double _ordValue; // value used to order the list
    
    /**
     * Constructor of the class. Initializes the instance variables
     * @param item The item we want to store into the list node.
     */
    public CIListNode(T item, double ordVal)
    {
        _item = item;
        _next = _prev = null;
        _idx = -1;	// default value, node not numbered.
        _ordValue = ordVal;
    }
    
    public CIListNode(T item) {
        this(item, 0);
    }
    
    /**
     * Returns the Item stored into the list node.
     */
    public T getItem () { 
        return _item; 
    }

    /**
     * Returns a pointer to the next node of the list.
     */
    public CIListNode <T> next () { 
        return _next; 
    }

    /**
     * Description: Returns a pointer to the previous node of the list.
     */
    public CIListNode <T> prev () { 
        return _prev; 
       }

    /**
     * Returns the index value.
     */
    public int getIdx () { 
        return _idx; 
    }

    /**
     * Returns the order value.
     */
    public double getOrdVal () { 
        return _ordValue; 
    }

    /**
     * Stores or updates the data into the list node.
     */
    public void setItem (T item) { 
        _item = item; 
    }

    /**
     * Sets the value of the pointer to the next node.
     */
    public void setNext (CIListNode <T> pos) { 
        _next = pos; 
    }

    /**
     * Sets the value of the pointer to the previous node.
     */
    public void setPrev (CIListNode <T> pos) { 
        _prev = pos; 
    }

    /**
     * Sets the value of the index value.
     */
    public void setIdx (int idx) { 
        _idx = idx; 
       }

    /**
     * Sets the value of the order value.
     */ 
    public void setOrdVal (double ordVal) { 
        _ordValue = ordVal; 
        }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(T o) {
        double thisValue = this.getOrdVal(); 
        double thatValue = ((CIListNode<T>)o).getOrdVal();
        
        if (thisValue < thatValue) {
            return -1;
        }
        else if (thisValue == thatValue) {
            return 0;
        }
        else {
            return 1;
        }
    }
}