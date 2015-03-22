import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
    }
    
    private int size;
    
    private Node first;
    private Node last;
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (isEmpty()) throw new java.util.NoSuchElementException("empty deck");
            
            Item item = current.item;
            current = current.next;
            
            return item;
        }
        
        public void remove() {
            throw new UnsupportedOperationException("remove not supported");
        }
    }
    
    public Deque() {                  // construct an empty deque
        first = null;
        last = null;
        size = 0;
    }
    
    public boolean isEmpty() {        // is the deque empty?
        //StdOut.printf("first = %s, last = %s", first, last);
        return first == null && last == null;
    }
    
    public int size() {               // return the number of items on the deque
        return size;
    }
    
    public void addFirst(Item item) { // insert the item at the front
        if (item == null) throw new NullPointerException("null object was added");
        
        Node node = new Node();
        node.item = item;
        node.next = first;
        node.previous = null;
        if (first != null) first.previous = node;
        
        first = node;
        if (last == null) last = first;
        ++size;
    }
    
    public void addLast(Item item) {  // insert the item at the end
        if (item == null) throw new NullPointerException("null object was added");
        
        Node node = new Node();
        node.item = item;
        node.next = null;
        node.previous = last;
        if (last != null) last.next = node;
        
        last = node;
        if (first == null) first = last;
        ++size;
    }
    
    public Item removeFirst() {       // delete and return the item at the front
        if (isEmpty()) throw new java.util.NoSuchElementException("empty deck");
        
        //Node node = first;
        Item item = first.item;
        first = first.next;
        if (first != null) first.previous = null;
        else last = null;
        //node = null;
        --size;
                
        return item;
    }
    
    public Item removeLast() {        // delete and return the item at the end
        if (isEmpty()) throw new java.util.NoSuchElementException("empty deck");
        
        //Node node = last;
        Item item = last.item;
        last = last.previous;
        if (last != null) last.next = null;
        else first = null;
        //node = null;
        
        --size;
        
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() { 
        return new DequeIterator();
    }
    
    public static void main(String[] args) {   // unit testing

        /*
        Deque<Integer> deck = new Deque<Integer>();
        
        deck.addLast(3);
        deck.addLast(4);
        deck.addLast(5);
        deck.addFirst(2);
        deck.addFirst(1);
        
        StdOut.printf("size = %d\n", deck.size());
        
        StdOut.printf("Last = %d\n", deck.removeLast());
        StdOut.printf("size = %d\n", deck.size());
        
        Iterator<Integer> deckIterator = deck.iterator();
        Iterator<Integer> deckIterator2 = deck.iterator();
        
        while (deckIterator.hasNext()) {
            StdOut.printf("First = %d\n", deckIterator.next());
            StdOut.printf("Next = %d\n", deckIterator2.next());
        }        
        
        StdOut.printf("First = %d\n", deck.removeFirst());
        StdOut.printf("Second = %d\n", deck.removeFirst());
        StdOut.printf("Third = %d\n", deck.removeFirst());
        StdOut.printf("Fourth = %d\n", deck.removeFirst());
        
        StdOut.printf("size = %d\n", deck.size());        
        StdOut.printf("Empty deck? %s\n", deck.isEmpty());
        */
        
        /*
        Deque<Double> ddeck = new Deque<Double>();
        
        for(int i = 0; i<2; i++) {
            double num1 = StdRandom.uniform();
            double num2 = StdRandom.uniform();
            
            ddeck.addLast(num1);
            ddeck.addLast(num2);
            
            double num3 = ddeck.removeLast();
            
            StdOut.printf("Size = %d, ", ddeck.size());
            StdOut.printf("IsEmpty? %s\n", ddeck.isEmpty());
            
            double num4 = ddeck.removeLast();
            
            StdOut.printf("First: %f == %f: %s, ", num1, num4, (num1 == num4));
            StdOut.printf("Last: %f == %f: %s\n", num2, num3, (num2 == num3));
            
            StdOut.printf("Size = %d, ", ddeck.size());
            StdOut.printf("IsEmpty? %s\n", ddeck.isEmpty());
        }
        */
        
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        
        /*Deque<Double> ddeck = new Deque<Double>();
        ddeck.addFirst(0.04);
        ddeck.removeFirst();
        ddeck.removeFirst();
        */
        
        //Iterator<Double> iter = ddeck.iterator();
        
        //iter.remove();
        //iter.next();
    }
    
    private static void test1() {
        Deque<Double> ddeck;
        boolean log;
        
        log = false;
        
        StdOut.print("addfirst and addlast with 0.5, 0.5");
        
        ddeck = new Deque<Double>();
        
        for (int i = 0; i < 50000; i++) {
                if (StdRandom.uniform() < 0.5) {
                    ddeck.addFirst(StdRandom.uniform());
                    if (log) StdOut.println("addFirst");                
                } else {
                    ddeck.addLast(StdRandom.uniform());
                    if (log) StdOut.println("addLast");
                }             
            }
        
        StdOut.println("...Passed");
    }
    
    private static void test2() {
        Deque<Double> ddeck;
        boolean log;
        long count;
        double rand;
        
        log = false;
        
        StdOut.println();
        StdOut.println("addfirst and removefirst with 0.9, 0.1");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.9) {
                    ddeck.addFirst(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeFirst();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }
        
        log = false;
        
        StdOut.println();
        StdOut.println("addfirst and removefirst with 0.1, 0.9");
        
        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.1) {
                    ddeck.addFirst(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeFirst();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }
    }
    
    private static void test3() {
        Deque<Double> ddeck;
        boolean log;
        long count;
        double rand;
        
        log = false;
        
        StdOut.println();
        StdOut.println("addfirst and removelast with 0.9, 0.1");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.9) {
                    ddeck.addFirst(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeLast();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }        
        
        log = false;
        
        StdOut.println();
        StdOut.println("addfirst and removelast with 0.1, 0.9");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.1) {
                    ddeck.addFirst(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeLast();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }
    }
    
    private static void test4() {
        Deque<Double> ddeck;
        boolean log;
        long count;
        double rand;

        log = false;
        
        StdOut.println();
        StdOut.println("addlast and removelast with 0.9, 0.1");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.9) {
                    ddeck.addLast(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeLast();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }        
        
        log = false;
        
        StdOut.println();
        StdOut.println("addlast and removelast with 0.1, 0.9");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.1) {
                    ddeck.addLast(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeLast();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }
    }
    
    private static void test5() {
        Deque<Double> ddeck;
        boolean log;
        long count;
        double rand;

        log = false;
        
        StdOut.println();
        StdOut.println("addlast and removefirst with 0.9, 0.1");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.9) {
                    ddeck.addLast(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeFirst();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }        
        
        log = false;
        
        StdOut.println();
        StdOut.println("addlast and removefirst with 0.1, 0.9");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                if (StdRandom.uniform() < 0.1) {
                    ddeck.addLast(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeFirst();
                        count--;
                        if (log) StdOut.println("done");                    
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }
    }
    
    private static void test6() {        
        Deque<Double> ddeck;
        boolean log;
        long count;
        double rand;
        
        log = false;
        
        StdOut.println();
        StdOut.println("addfirst, addlast, removefirst, removelast with 0.4, 0.4, 0.1, 0.1");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.4) {
                    ddeck.addFirst(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else if (rand < 0.8) {
                    ddeck.addLast(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addLast");                    
                } else if (rand < 0.9) {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeFirst();
                        count--;
                        if (log) StdOut.println("done");
                    }
                } else {
                    if (log) StdOut.print("removingLast...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeLast();
                        count--;
                        if (log) StdOut.println("done");
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }        
        
        log = false;
        
        StdOut.println();
        StdOut.println("addfirst, addlast, removefirst, removelast with 0.1, 0.1, 0.4, 0.4");

        for (int j = 5; j <= 50000; j *= 5) {
            ddeck = new Deque<Double>();
            count = 0;
            
            for (int i = 0; i < j; i++) {
                rand = StdRandom.uniform();
                if (rand < 0.1) {
                    ddeck.addFirst(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addFirst");                
                } else if (rand < 0.2) {
                    ddeck.addLast(StdRandom.uniform());
                    count++;
                    if (log) StdOut.println("addLast");                    
                } else if (rand < 0.6) {
                    if (log) StdOut.print("removingFirst...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeFirst();
                        count--;
                        if (log) StdOut.println("done");
                    }
                } else {
                    if (log) StdOut.print("removingLast...");
                    if (!ddeck.isEmpty()) {
                        ddeck.removeLast();
                        count--;
                        if (log) StdOut.println("done");
                    }
                }             
            }
        
            StdOut.printf("%d times...", j);
            if (count == 0) {
                if (!ddeck.isEmpty()) {
                    StdOut.println("Failed");
                    StdOut.printf("isEmpty() failed, size() = %d\n", ddeck.size());
                } else {
                    StdOut.println("Passed");
                }
            } else {
                StdOut.println("Passed");
            } 
        }        
    }
    
    private static void test7() {
        Deque<Double> ddeck;
        Iterator<Double> iter = null;
        boolean log;
        
        log = false;
        
        StdOut.println();
        StdOut.println("exception handling with remove() and next()");
        
        for (int i = 1; i < 9; i++) { 
            ddeck = new Deque<Double>();
              
            StdOut.printf("\nCase #%d\n", i);
                  
            switch(i) {
              case 1: 
                  iter = ddeck.iterator();
                  break;
              case 2: 
                  iter = ddeck.iterator();
                  ddeck.addFirst(0.1); 
                  ddeck.removeFirst(); 
                  break;
              case 3:
                  ddeck.addFirst(0.1); 
                  iter = ddeck.iterator();
                  ddeck.removeFirst(); 
                  break;
              case 4:
                  ddeck.addFirst(0.1); 
                  ddeck.removeFirst(); 
                  iter = ddeck.iterator();                  
                  break;
              case 5:
                  ddeck.addFirst(0.1); 
                  ddeck.removeFirst(); 
                  iter = ddeck.iterator();                  
                  break;
              case 6:
                  ddeck.addFirst(0.1); 
                  ddeck.removeLast(); 
                  iter = ddeck.iterator();                  
                  break;
              case 7:
                  ddeck.addLast(0.1); 
                  ddeck.removeFirst(); 
                  iter = ddeck.iterator();                  
                  break;
              case 8:
                  ddeck.addLast(0.1); 
                  ddeck.removeLast(); 
                  iter = ddeck.iterator();                  
                  break;                                                                                     
            };
                        
            try {
                StdOut.print("remove() exception...");
                iter.remove();
            } catch (java.lang.UnsupportedOperationException e) {
                StdOut.println("caught!");
            }
        
            try {
                StdOut.print("next() exception...");
                iter.next();
            } catch (java.util.NoSuchElementException e) {
                StdOut.println("caught!");
            }
        }
    }
}