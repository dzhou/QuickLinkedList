/**
 * ListAccuracy.java
 * Junit test for QuickLinkedList implementation
 * This tests all methods implemented by the QuickLinkedList
 * and compare output/result against those of ArrayList or
 * LinkedList.
 */

import java.util.*;
import junit.framework.TestCase;
import junit.textui.TestRunner;

public class ListAccuracy extends TestCase{

    private static List array, linked, unrolled, unrolled2;
    private static int tracker;

    // create the lists
    public void setUp(){
        array = new ArrayList<Integer>();
        linked = new LinkedList<Integer>();
        unrolled = new QuickLinkedList<Integer>();
        unrolled2 = new QuickLinkedList<Integer>();
    }


    public void testConstructor(){

        // Test default constructor
        unrolled = new QuickLinkedList<Integer>();
        assertEquals(1000, ((QuickLinkedList)unrolled).getSublistSize());
        assertEquals(0, unrolled.size());

        // Test constructor #2
        unrolled = new QuickLinkedList<Integer>(3000);
        assertEquals(3000, ((QuickLinkedList)unrolled).getSublistSize());
        assertEquals(0, unrolled.size());

        Collection<Integer> c = new ArrayList<Integer>();
        for(int i = 0; i < 1000; i+=2){
            c.add(i);
        }

        // Test constructor #3
        unrolled = new QuickLinkedList<Integer>(c);
        assertEquals(1000, ((QuickLinkedList)unrolled).getSublistSize());
        assertEquals(c.size(), unrolled.size());

        for(int i = 0; i < c.size(); i++){
            assertEquals(((ArrayList)c).get(i), unrolled.get(i));
        }

        // Test constructor #4
        unrolled = new QuickLinkedList<Integer>(c, 500);
        assertEquals(500, ((QuickLinkedList)unrolled).getSublistSize());
        assertEquals(c.size(), unrolled.size());

        for(int i = 0; i < c.size(); i++){
            assertEquals(((ArrayList)c).get(i), unrolled.get(i));
        }
    }


    // test add, add to index, isEmpty methods
    public void testAdd(){

        assertEquals(array.isEmpty(), unrolled.isEmpty());

        // add to end of the list
        for(int i = 0; i < 10000; i++){
            array.add(i);
            unrolled.add(i);
        }

        assertEquals(array.isEmpty(), unrolled.isEmpty());
        assertEquals(array.size(), unrolled.size());

        // test random insertion
        for(int i = 5000; i < 15000; i++){
            int a = (int)(Math.random() * (10000));
            array.add(i, a);
            unrolled.add(i, a);
        }

        assertEquals(array.isEmpty(), unrolled.isEmpty());
        assertEquals(array.size(), unrolled.size());

        Object[] t1 = array.toArray();
        Object[] t2 = unrolled.toArray();

        for(int i = 0; i < array.size(); i++){
            assertEquals(t1[i].toString(), t2[i].toString());
        }
    }


    // test remove element, remove index, clear
    public void testRemove(){

        for(int i = 0; i < 10000; i++){
            linked.add(i);
            unrolled.add(i);
        }

        // random remove from index
        for(int i = 0; i < 10000; i+=5){
            int a = (int)(Math.random() * 5000);
            assertEquals(linked.remove(a), unrolled.remove(a));
        }

        // remove from end of lists
        for(int i = 0; i < 5000; i+=5){
            linked.remove(new Integer(i));
            unrolled.remove(new Integer(i));
        }

        assertEquals(linked.size(), unrolled.size());
        for(int i = 0; i < linked.size(); i++){
            assertEquals(linked.get(i), unrolled.get(i));
        }

        array.clear();
        unrolled.clear();
        assertEquals(array.isEmpty(), unrolled.isEmpty());
        assertEquals(0, unrolled.size());
    }


    // tests indexOf and contains methods
    public void testFinder(){

        for(int i = 0; i < 100; i++){
            array.add(i);
            unrolled.add(i);
        }

        for(int i = 0; i < 500; i++){
            int a = (int)(Math.random() * 200);
            int b = (int)(Math.random() * 200);
            assertEquals(array.indexOf(a), unrolled.indexOf(a));
            assertEquals(array.contains(b), unrolled.contains(b));
        }
    }


    // test get and set method
    public void testGets(){

        for(int i = 0; i < 10000; i++){
            array.add(i);
            unrolled.add(i);
        }

        // run set on randomly index
        for(int i = 0; i < 10000; i+=3){
            int a = (int)(Math.random() * 10000);
            array.set(i, a);
            unrolled.set(i, a);
        }

        for (int i = 0; i < 10000; i++){
            assertEquals(array.get(i), unrolled.get(i));
        }
    }


    // test optimize method
    public void testOptimize(){
        for(int i = 0; i < 10000; i++){
            int num = (int)(Math.random() * 1000000);
            unrolled.add(num);
            unrolled2.add(num);
        }

        for(int j = 100; j < 10000; j+=500){
            ((QuickLinkedList)unrolled).optimize(j);
            assertTrue(unrolled.equals(unrolled2));
        }
    }


    // Run all the tests
    public static void main(String args[]){
        TestRunner.run(ListAccuracy.class);
    }

}
