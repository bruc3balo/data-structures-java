class Heaps {
    public static void main(String[] args) {
        MyMinHeap myMinHeap = new MyMinHeap(7);
        myMinHeap.insertKey(5);
        myMinHeap.insertKey(8);
        myMinHeap.insertKey(7);
        myMinHeap.insertKey(3);

        myMinHeap.printArray();
    }
}
