package pt.inevo.jcali;
/**
 * Class that implements a double linked list, with a next
 * and previous pointer, and a index operator [].
 */
public class CIList <T> {
    
    protected CIListNode <T> _first;
    protected CIListNode <T> _last;
    protected CIListNode <T> _current;
    protected int _count;
    protected boolean _acceptRepeated;
    
    public CIList(boolean acceptRepeated)
    {
        _first = _last = _current = null;
        _count = 0;
        _acceptRepeated = acceptRepeated;
    }
    
    public CIList() {
        //TODO Set default on true for empty calls, dunno if it should be
        this(true);
    }

    public CIListNode <T> push (T item) { 
        return insertTail(item); 
    }
    
    //CIListNode<Item>* findItem (Item item, CIListNode<Item>* pos = NULL);
    
    /**
     * Returns the first Item of the list.
     */
    public T getFirstItem () {
    	if (_first==null) return null; 
        return _first.getItem(); 
    }
    
    /**
     * Returns the last Item of the list.
     */
    public T getLastItem () { 
    	if (_last==null) return null; 
        return _last.getItem(); 
    }
    
    /**
     * Gets the value of the Item at the specified position.
     */    
    public T getItemAt (CIListNode <T> pos) {
    	if (pos==null) return null;
        return pos.getItem(); 
    }
    
    /**
     * Sets the value of the Item at the specified position.
     */    
    public void setItemAt (CIListNode <T> pos, T item) {
    	if (pos!=null) {
    		pos.setItem(item);
    	}
    }
    
    
    /**
     * Gets a pointer to the first node of the list.
     */
    public CIListNode <T> getHeadPosition () { 
        return _first; 
    }
    
    /**
     * Gets a pointer to the last node of the list.
     */
    public CIListNode <T> getTailPosition () { 
        return _last; 
    }
    
    
    /**
     * Returns the number of nodes of the list.
     */
    public int getNumItems () { 
        return _count; 
    }
    
    /**
     * Verify if the list is empty.
     */
    public boolean isEmpty () { 
        return (_count==0); 
    }
    
    /**
     * Verify if position points to the first node of the list.
     */
    public boolean isFirst (CIListNode <T> pos) { 
        return (pos.equals(_first)); 
    }
    
    /**
     * Verify if position points to the last node of the list.
     */
    public boolean isLast (CIListNode <T> pos) { 
        return (pos.equals(_last)); 
    }
    
    /**
     * Unlink the last element(node) of(from) the list and returns it
     * @return A pointer to the element
     */
    public T pop ()
    {
        T tmp;
        
        tmp = _last.getItem();
        if (_last != _first) {
            _last = _last.prev();
            _last.setNext(null);
        }
        else {
            _last = null;
            _first = null;
        }
        _count--;
        _current = null;
        
        return tmp;
    }
    
    /**
     * Inserts a new node at the begin of the list, and returns a 
     * pointer to that node.
     * @param item The item we want to add to the list.
     * @return A pointer to the new node
     */
    public CIListNode <T> insertHead (T item)
    {
        CIListNode <T> pos = new CIListNode<T>(item);
        
        pos.setNext(_first);
        pos.setPrev(null);
        if (_first != null) {
            _first.setPrev(pos);
        }
        else {
            _last = pos;
        }
        _first = pos;
        _count++;
        
        return _first;
    }
    
    /**
     * Inserts a new node at the begin of the list, and returns a 
     * pointer to that node.
     * @param item The item we want to add to the list.
     * @return A pointer to the new node
     */
    public CIListNode <T> insertTail (T item)
    {
        CIListNode <T> pos = new CIListNode <T> (item);
        pos.setNext(null);
        pos.setPrev(_last);
        if (_last != null) {
            _last.setNext(pos);
        }
        else {
            _first = pos;
        }
        _last = pos;
        _count++;
        
        return _last;
    }
    
    /**
     * Appends another list to the current list.
     */
    public void joinListAfter (CIList<T> list)
    {
        int i;
        
        for (i=0; i < list.getNumItems(); i++) {
            push(list.get(i));
        }
    }
    
    /**
     * Inserts a new node in ascendent order. If exists a node with
     * the same ordValue, the insertion is not performed.
     * @param item The item we want to add to the list
     * @param ordVal Its order value.
     * @return null if there is no node with the same ordValue, 
     * or a pointer to the node with the same ordValue.
     */
    public CIListNode <T> insertInOrder (T item, double ordVal)
    {
        CIListNode <T> pos = new CIListNode<T>(item, ordVal);
        CIListNode <T> ptr;
        
        if (_first != null) {
            ptr = _first;
            while(ptr.next() != null && ptr.getOrdVal() < ordVal)
                ptr = ptr.next();
            
            if (!_acceptRepeated && ptr.getOrdVal() == ordVal) { 
            	// we found a node
                // with the same ordVal
                //delete pos;
                return ptr;
            }
            else
                if (ptr.next() == null && ptr.getOrdVal() < ordVal) {
                    pos.setNext(null);
                    pos.setPrev(_last);
                    if (_last != null) {
                        _last.setNext(pos);
                    }
                    else {
                        _first = pos;
                    }
                    _last = pos;
                }
                else {
                    pos.setNext(ptr);
                    if (ptr.prev() != null) {
                        ptr.prev().setNext(pos);
                    }
                    else {
                        _first = pos;
                    }
                    pos.setPrev(ptr.prev());
                    ptr.setPrev(pos);
                }
        }
        else {
            pos.setNext(null);
            pos.setPrev(null);
            _first = _last = pos;
        }
        _count++;
        return null;  // insertion OK!
    }
    
    /**
     * Inserts a new node in descendent order. If exists a node with
     * the same ordValue, the insertion is not performed.
     * @param item The item we want to add to the list
     * @param ordVal Its order value
     * @return null if there is no node with the same ordValue, 
     * or a pointer to the node with the same ordValue.
     */
    public CIListNode <T> insertInRevOrder (T item, double ordVal)
    {
        CIListNode <T> pos = new CIListNode<T>(item, ordVal);
        CIListNode <T> ptr;
        
        if (_first != null) {
            ptr = _first;
            while(ptr.next() != null && ptr.getOrdVal() > ordVal)
                ptr = ptr.next();
            
            if (!_acceptRepeated && ptr.getOrdVal() == ordVal) { // we found a node
                // with the same ordVal
                //delete pos;
                return ptr;
            }
            else
                if (ptr.next() == null && ptr.getOrdVal() > ordVal) {
                    pos.setNext(null);
                    pos.setPrev(_last);
                    if (_last != null) {
                        _last.setNext(pos);
                    }
                    else {
                        _first = pos;
                    }
                    _last = pos;
                }
                else {
                    pos.setNext(ptr);
                    if (ptr.prev() != null) {
                        ptr.prev().setNext(pos);
                    }
                    else {
                        _first = pos;
                    }
                    pos.setPrev(ptr.prev());
                    ptr.setPrev(pos);
                }
        }
        else {
            pos.setNext(null);
            pos.setPrev(null);
            _first = _last = pos;
        }
        _count++;
        return null;  // insertion OK!
    }
    
    /**
     * Inserts a new node before the node pointed by pos, and
     * returns a pointer to that new node.
     * @param item The item we want to add to the list 
     * @param pos and the position.
     * @return A pointer to the new node
     */
    public CIListNode <T> insertBefore (CIListNode <T> pos, T item)
    {
        CIListNode <T> np = new CIListNode<T>(item);
        
        if (pos != _first) {
            np.setNext(pos);
            pos.prev().setNext(np);
            np.setPrev(pos.prev());
            pos.setPrev(np);
            _count++;
            return np;
        }
        else {
            return insertHead(item);
        }
    }
    
    /**
     * Inserts a new node after the node pointed by pos, and
     * returns a pointer to that new node.
     * @param item The item we want to add to the list 
     * @param pos and the position.
     * @return A pointer to the new node
     */
    public CIListNode<T> insertAfter (CIListNode <T>pos, T item)
    {
        CIListNode <T> np = new CIListNode <T> (item);
        
        if (pos != _last) {
            return insertBefore(pos.next(), item);
        }
        else {
            return insertTail(item);
        }
    }
    
    /*-----| Iteration methods |-----*/
    /*----------------------------------------------------------------------------+
     | Description: Returns the Item pointed by pos, and makes pos point to the 
     |	       next/prev node in the list.
     |
     | Input: pos - current node of the list
     | Output: The item of the current node, and a new value for pos, ie. a pointer 
     |	  to the next/prev node in the list.
     +----------------------------------------------------------------------------*/
    public T getNextItem (CIListNode <T> pos)
    {
        T item = pos.getItem();
        
        pos = pos.next();
        return item;
    }
    
    public T getPrevItem (CIListNode <T> pos)
    {
        T item = pos.getItem();
        
        pos = pos.prev();
        return item;
    }
    
    /*----------------------------------------------------------------------------+
     | Description: Remove the node pointed by pos
     | Input: pos - A pointer to the node
     +----------------------------------------------------------------------------*/
    public void removeAt (CIListNode <T> pos)
    {
        if (pos.prev() != null) {
            pos.prev().setNext(pos.next());
        }
        else if (pos.next() != null) { 
            _first = pos.next();
        }
        else {
            _first = null;
        }
        
        if (pos.next() != null) {
            pos.next().setPrev(pos.prev());
        }
        else if (pos.prev() != null) {
            _last = pos.prev();
        }
        else _last = null;
        
        _count--;
        _current = null;
    }
    
    /*----------------------------------------------------------------------------+
     | Description: 	Remove all nodes from the list, but the items of the list 
     |		still exists.
     |
     | Note: DO NOT FORGET to delete the items before destroying the list 
     | 	(if you want to delete them)
     +----------------------------------------------------------------------------*/
    public void removeAllNodes ()
    {
        // deleteAll(_first);
        
        CIListNode <T> actual = _first;
        CIListNode <T> next;
        
        while(actual != null){
            next = actual.next();
            //delete actual;
            actual = next;
        }
        
        _count = 0;
        _first = null;
        _last = null;
        _current = null;
    }
    
    /*----------------------------------------------------------------------------+
     | Description: Finds an item in the list 
     | Output: A pointer to the node containing the item
     +----------------------------------------------------------------------------*/
    public CIListNode <T> findItem (T item, CIListNode <T> pos)
    {
        if (pos == null) {
            pos = _first;
        }
        
        while(pos != null && (item != pos.getItem())) {
            pos = pos.next();
        }
        return pos;
    }
    
    public CIListNode <T> findItem (T item)
    {
        return this.findItem(item, null);
    }
    
    /*----------------------------------------------------------------------------+
     | Description: Removes the first occurrence of an item from the list
     +----------------------------------------------------------------------------*/
    public void removeFirstOcc (T item)
    {
        CIListNode <T> temp = findItem(item);
        removeAt(temp);
    }
    
    /*----------------------------------------------------------------------------+
     | Description: Allows the use of the list as an Array.
     +----------------------------------------------------------------------------*/
    public T get(int index)
    {
        int dif1, dif2, i, idx=0;
        CIListNode <T> ptr;
        
        // distances to the index position, from the begining, the end and the
        // middle.
        
        if (_current != null) {
            idx = _current.getIdx();
            dif1 = index - idx;
        }
        else {
            dif1 = _count;
        }
        
        dif2 = _count - index -1;
        
        if (index < Math.abs(dif1) && index < dif2) {
            ptr = _first;
            for (i=0; i<index; i++) {
                ptr = ptr.next();
            }
        }
        else if (Math.abs(dif1) < index && Math.abs(dif1) < dif2) {
            ptr = _current;
            if (dif1 > 0) {
                for (i=idx; i<index; i++)
                    ptr = ptr.next();
            }
            else {
                for (i=idx; i>index; i--)
                    ptr = ptr.prev();
            }
        }
        else {
            ptr = _last;
            for (i=_count-1; i>index; i--) {
                ptr = ptr.prev();
            }
        }
        
        if (_current != null) {
            _current.setIdx(-1);
        }
        
        _current = ptr;

        // TODO Sometimes null pointer, why ?
        if (_current==null) {
        	return null;
        }
        
		_current.setIdx(index);
        
        return (_current.getItem());
    }
    
    /*----------------------------------------------------------------------------+
     |		     Protected members of the class
     +----------------------------------------------------------------------------*/
    
    /*----------------------------------------------------------------------------+
     | Description: Delete recursively all the nodes of the list.
     +----------------------------------------------------------------------------*/
    public void deleteAll (CIListNode <T> pos)
    {
        //TODO What does this do in Java? (Ensure that it does not get called...)
        if (pos != null) {
            deleteAll(pos.next());
            //delete pos;
        }
    }

	public boolean is_acceptRepeated() {
		return _acceptRepeated;
	}

	public void set_acceptRepeated(boolean repeated) {
		_acceptRepeated = repeated;
	}
}
