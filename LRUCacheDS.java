import java.util.*;

class LRUCacheDS {

    static ArrayList<List<Integer>> events = new ArrayList<>();
    static ArrayList<List<Integer>> expectedEvents = new ArrayList<>();

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2); // null
        lruCache.put(1, 1); // null
        lruCache.put(2, 2); // null
        lruCache.get(1); //1
        lruCache.put(3, 3); //3
        lruCache.get(2);
        lruCache.put(4, 4);
        lruCache.get(1);
        lruCache.get(3);
        lruCache.get(4);

        System.out.println("Events " + events);


        //["LRUCache","put","put","put","put","put","get","put","get","get","put","get","put","put","put","get","put","get","get","get","get","put","put","get","get","get","put","put","get","put","get","put","get","get","get","put","put","put","get","put","get","get","put","put","get","put","put","put","put","get","put","put","get","put","put","get","put","put","put","put","put","get","put","put","get","put","get","get","get","put","get","get","put","put","put","put","get","put","put","put","put","get","get","get","put","put","put","get","put","put","put","get","put","put","put","get","get","get","put","put","put","put","get","put","put","put","put","put","put","put"]
        //[[10],[10,13],[3,17],[6,11],[10,5],[9,10],[13],[2,19],[2],[3],[5,25],[8],[9,22],[5,5],[1,30],[11],[9,12],[7],[5],[8],[9],[4,30],[9,3],[9],[10],[10],[6,14],[3,1],[3],[10,11],[8],[2,14],[1],[5],[4],[11,4],[12,24],[5,18],[13],[7,23],[8],[12],[3,27],[2,12],[5],[2,9],[13,4],[8,18],[1,7],[6],[9,29],[8,21],[5],[6,30],[1,12],[10],[4,15],[7,22],[11,26],[8,17],[9,29],[5],[3,4],[11,30],[12],[4,29],[3],[9],[6],[3,4],[1],[10],[3,29],[10,28],[1,20],[11,13],[3],[3,12],[3,8],[10,9],[3,26],[8],[7],[5],[13,17],[2,27],[11,15],[12],[9,19],[2,15],[3,16],[1],[12,17],[9,1],[6,19],[4],[5],[5],[8,1],[11,7],[5,2],[9,28],[1],[2,2],[7,4],[4,22],[7,24],[9,26],[13,28],[11,26]]
        //Out [null,null,null,null,null,null,-1,null,19,17,null,-1,null,null,null,-1,null,-1,5,-1,12,null,null,3,5,5,null,null,1,null,-1,null,30,5,30,null,null,null,-1,null,-1,24,null,null,18,null,null,null,null,14,null,null,18,null,null,-1,null,null,null,null,null,18,null,null,24,null,4,29,30,null,12,-1,null,null,null,null,29,null,null,null,null,17,22,18,null,null,null,24,null,null,null,20,null,null,null,29,18,18,null,null,null,null,20,null,null,null,null,null,null,null]
        //Ex [null,null,null,null,null,null,-1,null,19,17,null,-1,null,null,null,-1,null,-1,5,-1,12,null,null,3,5,5,null,null,1,null,-1,null,30,5,30,null,null,null,-1,null,-1,24,null,null,18,null,null,null,null,-1,null,null,18,null,null,-1,null,null,null,null,null,18,null,null,-1,null,4,29,30,null,12,-1,null,null,null,null,29,null,null,null,null,17,22,18,null,null,null,-1,null,null,null,20,null,null,null,-1,18,18,null,null,null,null,20,null,null,null,null,null,null,null]

        LRUCacheNeet lruCacheNeet = new LRUCacheNeet(2);
        lruCacheNeet.put(1, 1); // null
        lruCacheNeet.put(2, 2); // null
        lruCacheNeet.get(1); //1
        lruCacheNeet.put(3, 3); //3
        lruCacheNeet.get(2);
        lruCacheNeet.put(4, 4);
        lruCacheNeet.get(1);
        lruCacheNeet.get(3);
        lruCacheNeet.get(4);

        System.out.println("Expected " + expectedEvents);
    }

    private static class LRUCache {

        int capacity;
        int size;
        final LinkedList<Integer> cache = new LinkedList<>();
        final HashMap<Integer, Integer> allValues = new HashMap<>();

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            events.add(List.of());
        }

        public int get(int key) {
            Integer value = allValues.get(key);
            if (value == null) {
                events.add(List.of(-1));
                return -1;
            }
            registerUsage(key);
            events.add(List.of(value));
            return value;
        }

        public void put(int key, int value) {
            if (!allValues.containsKey(key)) {
                allValues.put(key, value);
                size++;
                cache.add(key);
                checkCapacity();
            } else {
                allValues.put(key, value);
                registerUsage(key);
            }
            events.add(List.of());
        }

        private void checkCapacity() {
            if (size > capacity) {
                if (!cache.isEmpty()) {
                    Node leastUsedNode = cache.leastUsed();
                    if (leastUsedNode != null) {
                        allValues.remove(leastUsedNode.key);
                        cache.removeNode(leastUsedNode);
                        size--;
                    }
                }
            }
        }

        private void registerUsage(int key) {
            Node byKey = cache.findByKey(key);
            cache.moveNodeToTop(byKey);
        }


        private static class Node {
            int key;
            Node next;

            Node(int key) {
                this.key = key;
            }

            Node() {
            }

        }

        private static class LinkedList<T> {
            private Node head;

            LinkedList() {
            }

            public boolean isEmpty() {
                return head == null;
            }

            public Node leastUsed() {
                //is tail
                if (head == null) return null;
                var temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                return temp;
            }

            public void add(Integer key) {
                Node node = new Node(key);
                if (head == null) {
                    head = node;
                    return;
                }

                var oldHead = head; //cache old head
                head = node;
                head.next = oldHead;
            }

            public void removeNode(Node node) {
                if (head == null) return;

                Node temp = head;
                Node previous = head;

                while (temp != null) {

                    if (node == temp) {
                        previous.next = temp.next;
                        temp = null;
                        continue;
                    }

                    previous = temp;
                    temp = temp.next;
                }
            }

            private void moveNodeToTop(Node node) {
                if (node == null) return;
                removeNode(node);
                add(node.key);
            }

            public Node findByKey(int key) {
                if (head == null) return null;
                Node temp = head;
                while (temp != null) {
                    if (temp.key == key) return temp;
                    temp = temp.next;
                }

                return null;
            }

        }

    }

    private static class LRUCacheNeet {

        private Map<Integer, NodeNeet> cache;
        private int capacity;

        private NodeNeet left;
        private NodeNeet right;

        public LRUCacheNeet(int capacity) {
            this.capacity = capacity;
            cache = new HashMap<>();

            //left = LRU , right = most recent
            this.left = new NodeNeet(0, 0);
            this.right = new NodeNeet(0, 0);
            this.left.next = this.right;
            this.right.prev = this.left;
            expectedEvents.add(List.of());
        }

        public int get(int key) {
            if (cache.containsKey(key)) {
                remove(cache.get(key));
                insert(cache.get(key));
                expectedEvents.add(List.of(cache.get(key).val));
                return cache.get(key).val;
            } else {
                expectedEvents.add(List.of(-1));
                return -1;
            }
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                remove(cache.get(key));
            }
            cache.put(key, new NodeNeet(key, value));
            insert(cache.get(key));

            if (cache.size() > capacity) {
                // remove from the list and delete the LRU from the hashmap
                NodeNeet lru = this.left.next;
                remove(lru);
                cache.remove(lru.key);
            }

            expectedEvents.add(List.of());

        }

        // remove node from list
        public void remove(NodeNeet nodeNeet) {
            NodeNeet prev = nodeNeet.prev;
            NodeNeet next = nodeNeet.next;

            prev.next = next;
            next.prev = prev;
        }

        // insert node at right
        public void insert(NodeNeet nodeNeet) {
            NodeNeet prev = this.right.prev;
            NodeNeet next = this.right;

            prev.next = nodeNeet;
            next.prev = nodeNeet;

            nodeNeet.next = next;
            nodeNeet.prev = prev;
        }

        private static class NodeNeet {

            private int key;
            private int val;

            NodeNeet next;
            NodeNeet prev;

            public NodeNeet(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }
    }

    class LRUCache2 {

        private final int capacity;
        private final LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };

        public LRUCache2(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            Integer value = cache.get(key);
            if (value == null) return -1;
            //todo move up
            moveUp(key, value);
            return value;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                //update and move up
                moveUp(key, value);
            } else {
                cache.put(key, value);
            }

        }


        private void moveUp(int key, int value) {
            cache.remove(key);
            cache.put(key, value);
        }
    }

}
