public class Subset {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        
        for (int i = 0; i < count; i++) {
            StdOut.println(q.dequeue());
        }
    }
}