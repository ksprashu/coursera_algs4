import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private int seekInsert;
    private int seekDelete;
    private int seekSample;
    private int sizeData;
    
    private boolean shuffled;
    
    private Item[] queue;
    
    private class QueueIterator implements Iterator<Item> {
        
        private Item[] q;
        private int seek = 0;
        
        public QueueIterator() {
            q = (Item[]) new Object[(int) sizeData];
            
            System.arraycopy(queue, seekDelete, q, 0, sizeData);
            StdRandom.shuffle(q);
        }
        
        public boolean hasNext() {
            return seek < q.length;
        }
        
        public Item next() {
            if (isEmpty()) throw new java.util.NoSuchElementException("empty deck");
            
            return q[seek++];
        }
        
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }        
    }
    
    public RandomizedQueue() {     // construct an empty randomized queue
        queue = (Item[]) new Object[1];
        sizeData = 0;
        seekInsert = 0;
        shuffled = false;
    }
    
    public boolean isEmpty() {    // is the queue empty?
        return sizeData <= 0;
    }
    
    public int size() {     // return the number of items on the queue
        return sizeData;
    }
    
    public void enqueue(Item item) {           // add the item
        if (item == null) throw new NullPointerException("null object added");
        
        queue[sizeData++] = item;
        
        shuffled = false;
        
        if (sizeData == queue.length) {
            resize(queue.length * 2);
        }
    }
    
    /*public Item dequeue() {    // delete and return a random item
        if (isEmpty()) throw new java.util.NoSuchElementException("empty queue");
        
        if (!shuffled) {
            shuffle();
            shuffled = true;
        }
        
        --sizeData;
        
        if (sizeData > 0 && sizeData <= queue.length / 4) {
            resize(queue.length / 2);
        }
        
        return queue[--seekInsert];
    }*/
    
    public Item dequeue() {    // delete and return a random item
        if (isEmpty()) throw new java.util.NoSuchElementException("empty queue");
        
        int seek = StdRandom.uniform(sizeData);
        
        Item temp = queue[seek];
        queue[seek] = queue[sizeData - 1];
        queue[sizeData - 1] = temp;
        
        Item item = queue[sizeData - 1];
        queue[sizeData - 1] = null;
                                            
        if (sizeData > 0 && sizeData <= queue.length / 4) {
            resize(queue.length / 2);
        }
        
        sizeData--;

        return item;
    }    
    
    public Item sample() {   // return (but do not delete) a random item
        if (isEmpty()) throw new java.util.NoSuchElementException("empty queue");

        /*if (!shuffled) {
            shuffle();
            shuffled = true;
        }*/
                
        return queue[StdRandom.uniform(sizeData)];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {         
        return new QueueIterator();
    }
    
    private void resize(int size) {
        Item[] copy = (Item[]) new Object[size];
        //int seek = 0;
        
        for (int i = 0; i < size(); i++) {
            copy[i] = queue[i];
        }
        
        queue = copy;
        //seekDelete = 0;
        //seekInsert = seek;
    }
    
    /*
    private void compress() {
        int N = seekInsert;
        for (int i = seekDelete; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);  // between i and N-1
            Item temp = queue[i];
            queue[i] = queue[r];
            queue[r] = temp;
        }
    }
    */
    
    private void shuffle() {
        int N = sizeData;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            Item temp = queue[i];
            queue[i] = queue[r];
            queue[r] = temp;
        }
    }
    
    public static void main(String[] args) {   // unit testing
        /*
         * RandomizedQueue<Integer> que = new RandomizedQueue<Integer>();
        
        que.enqueue(1);
        que.enqueue(2);
        que.enqueue(3);
        que.enqueue(4);
        que.enqueue(5);
        que.enqueue(6);
        
        que.dequeue();
        que.dequeue();
        que.dequeue();
        
        que.enqueue(7);
        que.enqueue(8);
        que.enqueue(9);
        
        for (int item : que) {
            StdOut.println(item);
        }
        */
        
        /*
        Iterator<Integer> iter1 = que.iterator();
        Iterator<Integer> iter2 = que.iterator();
        
        while(iter1.hasNext() || iter2.hasNext()) {
            StdOut.printf("Iter 1, Iter 2 = (%d, %d)\n", iter1.next(), iter2.next());
        }
        */
        
        /*
        RandomizedQueue<Double> dque = new RandomizedQueue<Double>();
        
        for(int i=0; i<1000000; i++) {
            dque.enqueue(StdRandom.uniform());
            dque.enqueue(StdRandom.uniform());
            dque.dequeue();
            //StdOut.printf("%f\n", dque.dequeue());
        }
        */
        
        test1();
        test2();
        test3();
    }
    
    private static void test1() {
       RandomizedQueue<Double> dque;
       double d;
       
       StdOut.print("enqueue...");
       
       dque = new RandomizedQueue<Double>();
       for (int i = 0; i < 5000; i++) {
           dque.enqueue(StdRandom.uniform());
       }
       
       if (dque == null) StdOut.println("null queue");
       
       while (!dque.isEmpty()) {
           d = dque.dequeue();
           if (d < 0 || d > 1) {
               StdOut.println("null value");
           }
       }
       
       StdOut.println("Passed");
    }
    
    private static void test2() {
        RandomizedQueue<Double> dque;
        double rand;
        boolean log;
        int count;

        log = false;
        
        StdOut.println();
        StdOut.println("enqueue and dequeue with 0.9, 0.1");
        
        for (int j = 5; j <= 50000; j *= 5) {
            dque = new RandomizedQueue<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.9) {
                    if (log) StdOut.printf("size = %d, ", dque.size());
                    dque.enqueue(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("enqueue");
                } else {
                    if (log) StdOut.print("dequeue...");
                    if (!dque.isEmpty()) {
                        dque.dequeue();
                        --count;
                        if (log) StdOut.println("done!");
                    } else {
                        if (log) StdOut.println("x");
                    }
                }
            }
            
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!dque.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", dque.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            }
        }
        
        log = false;
        
        StdOut.println();
        StdOut.println("enqueue and dequeue with 0.1, 0.9");
        
        for (int j = 5; j <= 50000; j *= 5) {
            dque = new RandomizedQueue<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.1) {
                    if (log) StdOut.printf("size = %d, ", dque.size());
                    dque.enqueue(StdRandom.uniform());
                    count++;
                    if (log) StdOut.printf("enqueue, size = %d\n", dque.size());
                } else {
                    if (log) StdOut.printf("size = %d, dequeue...", dque.size());
                    if (!dque.isEmpty()) {
                        dque.dequeue();
                        --count;
                        if (log) StdOut.printf("done!, size = %d\n", dque.size());
                    } else {
                        if (log) StdOut.println("x");
                    }
                }
            }
            
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!dque.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", dque.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            }
        }
    }
    
    private static void test3() {
        RandomizedQueue<Double> dque;
        double rand;
        boolean log;
        int count;

        log = false;
        
        StdOut.println();
        StdOut.println("enqueue and sample with 0.9, 0.1");
        
        for (int j = 5; j <= 50000; j *= 5) {
            dque = new RandomizedQueue<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.9) {
                    if (log) StdOut.printf("size = %d, ", dque.size());
                    dque.enqueue(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("enqueue");
                } else {
                    if (log) StdOut.print("sample...");
                    if (!dque.isEmpty()) {
                        dque.sample();
                        --count;
                        if (log) StdOut.println("done!");
                    } else {
                        if (log) StdOut.println("x");
                    }
                }
            }
            
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!dque.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", dque.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            }
        }
        
        log = false;
        
        StdOut.println();
        StdOut.println("enqueue and sample with 0.1, 0.9");
        
        for (int j = 5; j <= 50000; j *= 5) {
            dque = new RandomizedQueue<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.1) {
                    if (log) StdOut.printf("size = %d, ", dque.size());
                    dque.enqueue(StdRandom.uniform());
                    count++;
                    if (log) StdOut.printf("enqueue, size = %d\n", dque.size());
                } else {
                    if (log) StdOut.printf("size = %d, sample...", dque.size());
                    if (!dque.isEmpty()) {
                        dque.sample();
                        --count;
                        if (log) StdOut.printf("done!, size = %d\n", dque.size());
                    } else {
                        if (log) StdOut.println("x");
                    }
                }
            }
            
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!dque.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", dque.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            }
        }
    }
    
    private static void test4() {
        RandomizedQueue<Double> dque;
        double rand;
        boolean log;
        int count;

        log = false;
        
        StdOut.println();
        StdOut.println("enqueue, dequeue, sample with 0.8, 0.1, 0.1");
        
        for (int j = 5; j <= 50000; j *= 5) {
            dque = new RandomizedQueue<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.8) {
                    if (log) StdOut.printf("size = %d, ", dque.size());
                    dque.enqueue(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("enqueue");
                } else if (rand < 0.9) {
                    if (log) StdOut.print("dequeue...");
                    if (!dque.isEmpty()) {
                        dque.dequeue();
                        --count;
                        if (log) StdOut.println("done!");
                    } else {
                        if (log) StdOut.println("x");
                    }
                } else {
                    if (log) StdOut.print("sample...");
                    if (!dque.isEmpty()) {
                        dque.sample();
                        --count;
                        if (log) StdOut.println("done!");
                    } else {
                        if (log) StdOut.println("x");
                    }
                }
            }
            
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!dque.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", dque.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            }
        }
        
        log = false;
        
        StdOut.println();
        StdOut.println("enqueue, dequeue, sample with 0.1, 0.1, 0.8");
        
        for (int j = 5; j <= 50000; j *= 5) {
            dque = new RandomizedQueue<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
               rand = StdRandom.uniform();
                if (rand < 0.1) {
                    if (log) StdOut.printf("size = %d, ", dque.size());
                    dque.enqueue(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("enqueue");
                } else if (rand < 0.2) {
                    if (log) StdOut.print("dequeue...");
                    if (!dque.isEmpty()) {
                        dque.dequeue();
                        --count;
                        if (log) StdOut.println("done!");
                    } else {
                        if (log) StdOut.println("x");
                    }
                } else {
                    if (log) StdOut.print("sample...");
                    if (!dque.isEmpty()) {
                        dque.sample();
                        --count;
                        if (log) StdOut.println("done!");
                    } else {
                        if (log) StdOut.println("x");
                    }
                }
            }
            
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!dque.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", dque.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            }
        }
    }
    
}