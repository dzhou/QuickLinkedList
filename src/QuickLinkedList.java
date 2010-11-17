/**
 * QuickListedList stores the elements internally as several arraylist of specified size.
 * It takes advantage of basic operations of both LinkedList and ArrayList thus
 * the list is not synchronized and at worst case, the performance is roughly equivalent
 * to that of either LinkedList or ArrayList.
 *
 * Since the internal sublists are stored as ArrayLists, for operations such as add,
 * get, and indexOf, QuickLinkedList is almost equivalent to ArrayList in terms of performance.
 * However for operation such as add and remove to an index position, the QuickLinkedList can
 * be potentially several magnitudes faster than ArrayList and LinkedList.
 *
 * QuickLinkedList is designed and optimized to handle large number of elements. In
 * fact, the default size of the sublist is 1000, which means for list containing less
 * than 1000 elements, the QuickLinkedList is essentially an ArrayList.
 *
 * This implementation is by no means completely bug-free. If you find any bugs please
 * contact the author at zhouk@seas.upenn.edu. Thank you.
 *
 * @author Kefei D. Zhou
 * @version 0.1.6
 * @date Oct 2006
 */

import java.io.Serializable;
import java.util.*;

public class QuickLinkedList<E> extends AbstractList<E>
    implements Serializable, Cloneable, Collection<E>, List<E>, Iterable<E> {


    // fragmente arrays to store the "list"
    private LinkedList<ArrayList<E>> list;

    // size: track the size of the entire list
    // fragment: size of each sublist
    private int size, fragment;

    // serial version number
    public static final long serialVersionUID = 42L;


    /**
     * Construct an empty list with the default sublist size of 1000
     *
     */
    public QuickLinkedList() {
        size = 0;
        fragment = 1000;
        list = new LinkedList<ArrayList<E>>();
        list.add(new ArrayList<E>((int)(fragment * 1.1)));
    }


    /**
     * Construct an empty list with the specific sublist size
     *
     */
    public QuickLinkedList(int fragment) {

        if(fragment < 0)
            throw new IllegalArgumentException();
        size = 0;
        this.fragment = fragment;
        list = new LinkedList<ArrayList<E>>();
        list.add(new ArrayList<E>((int)(fragment * 1.1)));
    }


    /**
     * Constructs a list containing the elements of the specified collection,
     * in the order they are returned by the collection's iterator.
     */
    public QuickLinkedList(Collection<? extends E> c) {
        if(c == null)
            throw new NullPointerException();

        size = 0;
        fragment = 1000;
        list = new LinkedList<ArrayList<E>>();
        list.add(new ArrayList<E>((int)(fragment * 1.1)));

        Iterator<? extends E> it = c.iterator();
        while(it.hasNext())
            this.add((E)it.next());
    }


    /**
     * Constructs a list containing the elements of the specified collection
     * using sublists of specific size.
     */
    public QuickLinkedList(Collection<? extends E> c, int fragment) {
        if(c == null)
            throw new NullPointerException();

        if(fragment < 0)
            throw new IllegalArgumentException();
        size = 0;
        this.fragment = fragment;
        list = new LinkedList<ArrayList<E>>();
        list.add(new ArrayList<E>((int)(fragment * 1.1)));

        Iterator<? extends E> it = c.iterator();
        while(it.hasNext())
            this.add((E)it.next());
    }


    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list.
     */
    public int size() {
        return size;
    }


    /**
     * Return the size of sublists
     * @return size of the sublists
     */
    public int getSublistSize(){
        return fragment;
    }


    /**
     * Tests if this list has no elements.
     * @return true if this list has no elements; false otherwise.
     */
    public boolean isEmpty() {
        return (size==0? true:false);
    }


    /**
     * Returns true if this list contains the specified element.
     * @param o element whose presence in this List is to be tested.
     * @return true if the specified element is present; false otherwise.
     */
    public boolean contains(Object o) {
        if (indexOf(o) == -1)
            return false;
        else
            return true;
    }


    /**
     * Searches for the first occurence of the given argument, testing
     * for equality using the equals method.
     * @param o An object to search for.
     * @return the index of the first occurrence of the argument in this list;
     *          returns -1 if the object is not found.
     */
    public int indexOf(Object o) {
        ListIterator<ArrayList<E>> it = list.listIterator(0);
        while(it.hasNext()) {
            ArrayList<E> l = it.next();
            int loc = l.indexOf(o);
            if (loc != -1)
                return loc;
        }
        return -1;
    }


    /**
     * Returns an array containing all of the elements in this list in the correct order.
     * @return an array containing all of the elements in this list in the correct order.
     *
     */
    public Object[] toArray() {
        Object[] obj = new Object[size];
        int index = 0;

        ListIterator<ArrayList<E>> it = list.listIterator(0);
        while(it.hasNext()) {
            ArrayList<E> l = it.next();
            for (int i = 0; i < l.size(); i++) {
                obj[index++] = l.get(i);
            }
        }
        return obj;
    }


    /**
     * Returns the element at the specified position in this list.
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index >= size())
     */
    public E get(int index) {

        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        else {
            ListIterator<ArrayList<E>> it = list.listIterator(0);
            while(it.hasNext()) {
                ArrayList<E> array = it.next();
                if (array.size() <= index)
                    index -= array.size();
                else
                    return array.get(index);
            }
            return null; // unreachable
        }
    }


    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws IndexOutOfBoundsException if index out of range (index < 0 || index >= size()).
     */
    public E set(int index, E element) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();
        else {
            ListIterator<ArrayList<E>> it = list.listIterator(0);
            while(it.hasNext()) {
                ArrayList<E> array = it.next();
                if (array.size() <= index)
                    index -= array.size();
                else {
                    E current = array.get(index);
                    array.set(index, element);
                    return current;
                }
            }
            return null; // unreachable
        }
    }


    /**
     * Appends the specified element to the end of this list.
     * @param o element to be appended to this list.
     * @return true (as per the general contract of Collection.add).
     */
    public boolean add(E o) {
        if (list.getLast().size() < fragment)
            list.getLast().add(o);
        else {
            ArrayList<E> l = new ArrayList<E>((int)(fragment * 1.1));
            l.add(o);
            list.add(l);
        }
        size++;
        return true;
    }


    /**
    * Inserts the specified element at the specified position in this list.
    * Shifts the element currently at that position (if any) and any subsequent
    * elements to the right (adds one to their indices).
    * @param index index at which the specified element is to be inserted.
    * @param element element to be inserted.
    * @throws IndexOutOfBoundsException if index is out of range (index < 0 || index > size()).
    */
    public void add(int index, E o) {

        if (index > size || index < 0)
            throw new IndexOutOfBoundsException();

        ListIterator<ArrayList<E>> it = list.listIterator(0);
        while(it.hasNext()) {
            ArrayList<E> array = it.next();
            if (array.size() <= index) {
                index -= array.size();
            } else {
                array.add(index, o);
                size++;
                return;
            }
        }
    }


    /**
     * Removes a single instance of the specified element from this list, if it
     * is present (optional operation). More formally, removes an element e such
     * that (o==null ? e==null : o.equals(e)), if the list contains one or more
     * such elements. Returns true if the list contained the specified element (or
     * equivalently, if the list changed as a result of the call).
     * @param o element to be removed from this list, if present.
     * @return true if the list contained the specified element.
     */
    public boolean remove(Object o) {
        ListIterator<ArrayList<E>> it = list.listIterator(0);
        while(it.hasNext()) {
            ArrayList<E> array = it.next();
            if (array.contains(o)) {
                array.remove(o);
                size--;
                return true;
            }
        }
        return false;
    }


    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * @param index the index of the element to removed.
     * @return the element that was removed from the list.
     * @throws IndexOutOfBoundsException if index out of range (index < 0 || index >= size())
     */
    public E remove(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException();

        ListIterator<ArrayList<E>> it = list.listIterator(0);
        while(it.hasNext()) {
            ArrayList<E> l = it.next();
            if (l.size() <= index)
                index -= l.size();
            else {
                size--;
                return l.remove(index);
            }
        }
        return null; // unreachable
    }


    /**
     * Appends all of the elements in the specified Collection to the end of this
     * list, in the order that they are returned by the specified Collection's Iterator.
     * The behavior of this operation is undefined if the specified Collection is
     * modified while the operation is in progress. (This implies that the behavior
     * of this call is undefined if the specified Collection is this list, and this
     * list is nonempty.)
     * @param c the elements to be inserted into this list.
     * @return true if this list changed as a result of the call.
     * @throws NullPointerException if the specified collection is null.
     *
     */
    public boolean addAll(Collection<? extends E> c) {
        if(c == null)
            throw new NullPointerException();

        Iterator<? extends E> it = c.iterator();
        while(it.hasNext()){
            this.add((E)it.next());
        }

        if(c.size() > 0)
            return true;
        else return false;
    }


    /**
     * Removes all of the elements from this list. The list will be \
     * empty after this call returns.
     *
     */
    public void clear() {
        size = 0;
        list.clear();
        list.add(new ArrayList<E>((int)(fragment * 1.1)));
    }


    /**
     * Redistribute the entire list evenly throughout. After many add/remove to
     * index operation, the list can potentially becomes unevenly distributed and
     * see a decrease in performance with other operations.
     *
     * @param fragment new sublist size
     *
     */
    public void optimize(int fragment) {

        if(fragment < 0)
            throw new IllegalArgumentException();

        this.fragment = fragment;
        LinkedList<ArrayList<E>> l2 = new LinkedList<ArrayList<E>>();
        l2.add(new ArrayList<E>((int)(fragment * 1.1)));

        ListIterator<ArrayList<E>> it = list.listIterator(0);
        while(it.hasNext()) {
            ArrayList<E> array = it.next();
            for (int i = 0; i < array.size(); i++) {

                if (l2.getLast().size() < fragment) {
                    l2.getLast().add(array.get(i));
                }
                else {
                    ArrayList<E> nextList = new ArrayList<E>((int)(fragment * 1.1));
                    nextList.add(array.get(i));
                    l2.add(nextList);
                }
            }
        }

        list = l2;
    }


    /**
     * Returns a shallow copy of this ArrayList instance. (The elements
     * themselves are not copied.)
     * @return a clone of this ArrayList instance.
     *
     */
    public Object clone() {
        try {
            return super.clone();
        } catch(Exception e) {
            return null;
        }
    }


    /**
     * Compare two QuickLinkedList for equality. Two list are equal iff
     * all elements in the list are equal
     */
    public boolean equals(QuickLinkedList ql){
        if(ql.size() != size)
            return false;
        for(int i = 0; i < size; i++)
            if(ql.get(i) != this.get(i))
                return false;
        return true;
    }


//End
}
