/**
 * Benchmark tool to compare the performance of various List implementation
 * This compares the performances of Sun java 1.5 LinkedList and ArrayList against
 * the QuickLinkedList. Lists are testing use fairly large data size ( performing
 * operations on lists of up to 1,000,000 elements)
 *
 * The methods tested here are add(to end of list and insertion to specific index),
 * remove, get, indexOf
 *
 *
 */



import java.util.*;


public class ListBenchmark{

    // store benchmark data for all the lists
    private static long[][] data;
    private static long total;

    // global contants
    private static final int tests = 3;
    private static final int testsize = 200000;
    private static final int add_size = 10000;
    private static final int remove_s = 50000;
    private static final int indexof_s = 50000;

    // benchmark specific variables
    private List list;
    private String id;
    private int tracker1, tracker2;
    private long[] result;

    public ListBenchmark(List list, String id){
        this.list = list;
        this.id = id;
        tracker1 = 0;
        tracker2 = 0;
        result = new long[tests];
    }

    public void fillList(){
        for (int i = 0; i < testsize; i++)
            list.add(i);
    }

    public long[] benchmark(){
        System.out.println("\n\n-----Benchmark: " + id + "-----");
        test_add();
        test_remove();
        test_get();
        return result;
    }


    // display speed of progress
    // print out "-" for every loop
    public void progressMeter(){
        if (tracker1 < 50)
            System.out.print("-");
        else {
            System.out.println();
            tracker1 = 0;
        }
    }


    public long runtime(long start, long stop){
        if((stop - start) == 0)
            return 1;
        else return (stop-start);
    }
			
    // test basic add operation
    public void test_add(){
        System.out.print("\n" + "add: " + "\t\t");

        long start, stop;
        start = System.currentTimeMillis();
		Random generator = new Random();
        for(int i = 0; i < 50; i++){
            for(int j = 0; j < add_size; j++){
				int index = generator.nextInt(list.size()+1);
                list.add(index, j);				
            }
            list.clear();
            progressMeter();
        }
        stop = System.currentTimeMillis();
        result[tracker2++] = runtime(start,stop);

        list.clear();
    }


    // test basic remove operation
    public void test_remove(){
        System.out.print("\n" + "remove: " + "\t");

        long start, stop;
        fillList();
        start = System.currentTimeMillis();
		Random generator = new Random();		
        for(int i = 0; i < 50; i++){
            for(int j = 0; j < list.size(); j+=remove_s){
				int index = generator.nextInt(list.size()+1);
                list.remove(index);
            }
            progressMeter();
        }
        stop = System.currentTimeMillis();
        result[tracker2++] = runtime(start,stop);

        list.clear();
    }


    // test get
    public void test_get(){
        System.out.print("\n" + "get : " + "\t\t");

        long start, stop;
        fillList();
        start = System.currentTimeMillis();
		Random generator = new Random();		
        for(int i = 0; i < 50; i++){
            for(int j = 0; j < list.size(); j+=indexof_s){
				int index = generator.nextInt(list.size()+1);
                list.get(index);
            }
            progressMeter();
        }
        stop = System.currentTimeMillis();
        result[tracker2++] = runtime(start,stop);

        list.clear();
    }



    // Run benchmark test
    public static void main(String args[]){

        List lq; ListBenchmark lb;
        long[][] data = new long[4][];
        String[] testname = {"add", "remove","get"};
		for(int j = 0; j < data.length; j++)
			data[j] = new long[15];
//			for(int k = 0; k < data[j].length; k++)
//				data[j][k] = 0;

        long start, stop;
        start = System.currentTimeMillis();


        //ArrayList
        lq = new ArrayList<Integer>();
        lb = new ListBenchmark(lq, "ArrayList");
        data[0] = lb.benchmark();

        //LinkedList
        lq = new LinkedList<Integer>();
        lb = new ListBenchmark(lq, "LinkedList");
        data[1] = lb.benchmark();

        //QuickLinkedList
        lq = new QuickLinkedList<Integer>();
        lb = new ListBenchmark(lq, "QuickLinkedList");
        data[2] = lb.benchmark();

        stop = System.currentTimeMillis();
        System.out.println("\n\n-----Benchmark Result-----");
        System.out.println("-----Total Time: " + (stop-start) + "ms\n");
        System.out.println("\t\tArrayL\tLinkedL\tQuick");
        for(int i = 0; i < data[0].length; i++)
            System.out.println(testname[i] + "\t\t" + data[0][i] + "\t" +
                                   data[1][i] + "\t"+ data[2][i]);
		

    }
}
