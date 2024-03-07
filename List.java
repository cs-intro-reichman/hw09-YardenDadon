/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }

    public Node getFirstNode() {
        return first;
    }

    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the first element in the list */
    public CharData getFirst() {
        return first.cp;
    }

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        // Your code goes here
        CharData cd = new CharData(chr);
        Node cur = new Node(cd);
        cur.next = first;               
        first = cur;        
        this.size++;                       
    }
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        if (size == 0) return "()";
        String str = "(";
        Node current = first;
        while (current != null) {
            str += current.toString() + " ";
            current = current.next;
        }
        str = str.substring(0, str.length() - 1) + ")";
        return str;
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
        // Your code goes here
        Node current = first;
        int count = 0;

        while (current != null)
        {
            if (current.cp.equals(chr))
                return count;
            current = current.next;
            count++;   
        }

        return -1;
    }

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {
        // Your code goes here
        if (indexOf(chr) > -1) 
        {
            get(indexOf(chr)).count++;
            return;
        }
        addFirst(chr);
    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        Node prev = first;
        Node cur = first.next;
        if (first.cp.equals(chr)) {
            first = first.next;
            size--;
            return true;
        }

        while (cur != null) 
        {
            if (cur.cp.equals(chr)) 
            {
                prev.next = cur.next;
                size--;
                return true;
            }
            prev = prev.next;
            cur = cur.next;
        }

        return false;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {
        // Your code goes here
        if (index >= size) 
        {
            throw new IndexOutOfBoundsException();
        }
         
        Node cur = first;
        for(int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.cp; 
    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node cur = first;
	    int i = 0;
        while (i < index) {
            cur = cur.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(cur);
    }
}