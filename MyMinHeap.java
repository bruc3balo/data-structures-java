//heaps
class MyMinHeap {
    int[] heapArray; // array of elements in heap
    int capacity; //maximum possible sie of min heap
    int heapSize; //current no of elements in heap

    public MyMinHeap(int capacity) {
        this.heapSize = 0;
        this.capacity = capacity;
        heapArray = new int[capacity];
    }

    public MyMinHeap(int[] heapArray) {
        this.heapArray = heapArray;
        this.capacity = heapArray.length;
        this.heapSize = heapArray.length;
        heapifyAllNodes();
    }

    public Integer getParent(int index) {
        //gets parent node
        int parent = (index - 1) / 2;
        if (heapSize == 0) return null;
        return parent;
    }

    public Integer getLeft(int index) {
        //returns left node
        int left = (2 * index) + 1;
        if (left >= heapSize) return null;
        return left;
    }

    public Integer getRight(int index) {
        //return tight node
        int right = (2 * index) + 2;
        if (right >= heapSize) return null;
        return right;
    }

    public int getMin() {
        //get minimum root value
        return 0;
    }

    public Integer extractMin() {
        //gets and removes minimum value (root)
        if (heapSize <= 0) return null;

        if (heapSize == 1) {
            heapSize--;
            return heapArray[0];
        }

        int root = heapArray[0];
        heapArray[0] = heapArray[heapSize - 1];
        heapSize--;
        minHeapify(0);
        return root;
    }

    public void insertKey(int k) {
        //insert new key / value
        if (heapSize == capacity) throw new IllegalStateException("Heap full");
        heapSize++;
        int index = heapSize - 1;

        //insertion at end of array / heap
        heapArray[index] = k;

        siftUp(index);
    }

    private boolean isLeaf(int index) {
        return index > (heapSize / 2) && index <= heapSize;
    }

    private void siftUp(int index) {
        Integer parentIndex = getParent(index);

        //i.e. move up anything less (current and parent)
        //swap till top
        while ((index != 0 && parentIndex != null) && (heapArray[index] < heapArray[parentIndex])) {
            //swap
            int temp = heapArray[index];
            heapArray[index] = heapArray[parentIndex];
            heapArray[parentIndex] = temp;


            //for the next iteration
            index = parentIndex;
            parentIndex = getParent(index);
        }

    }

    private void siftDown(int index) {
        int leftIndex = getLeft(index);
        int rightIndex = getRight(index);

        while ((leftIndex < heapSize && (heapArray[index] > heapArray[leftIndex]))
                ||
                rightIndex < heapSize && (heapArray[index] > heapArray[rightIndex])) {

            //swap with the smallest child
            int smallestIndex = rightIndex >= heapSize || heapArray[leftIndex] < heapArray[rightIndex] ? leftIndex : rightIndex;

            //swap
            int temp = heapArray[smallestIndex];
            heapArray[smallestIndex] = heapArray[index];
            heapArray[index] = temp;

            //for the next iteration
            index = smallestIndex;
            leftIndex = getLeft(index);
            rightIndex = getRight(index);
        }

    }

    public void decreaseKey(int index, int newVal) {
        //deletion
        heapArray[index] = newVal;
        Integer parent = getParent(index);
        while (index != 0 && heapArray[parent] > heapArray[index]) {
            int temp = heapArray[index];
            heapArray[index] = heapArray[parent];
            heapArray[parent] = temp;
            index = parent;
        }
    }

    public void deleteKey(int index) {
        decreaseKey(index, Integer.MIN_VALUE);
        extractMin();
    }

    public void minHeapify(int index) {
        Integer left = getLeft(index);
        Integer right = getRight(index);
        int smallest = index;

        //left is smaller?
        if ((left != null) && left < heapSize && heapArray[left] < heapArray[index]) smallest = left;

        //right is smaller
        if ((right != null) && right < heapSize && heapArray[right] < heapArray[smallest]) smallest = right;

        //smaller has changed do swap
        if (smallest != index) {
            //swap
            int temp = heapArray[index];
            heapArray[index] = heapArray[smallest];
            heapArray[smallest] = temp;
            minHeapify(smallest);
        }
    }

    public void heapifyAllNodes() {
        //from bottom level to top level exclude last level
        for (int i = (heapSize / 2) - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }

    public boolean contains(int val) {
        for (int i = 0; i < heapSize; i++) if (heapArray[i] == val) return true;
        return false;
    }

    public void printArray() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < heapSize; i++) {
            sb.append(heapArray[i]).append(", ");
        }
        sb.append("]");
        System.out.println(sb);
    }

    public int getHeight() {
        return (int) (Math.ceil(Math.log(heapSize + 1)) - 1);
    }

}
