import java.util.*;


class WordLadder {
    public static void main(String[] args) {
        //   String beginWord = "hit";
        //   String endWord = "cog";
        //   List<String> wordList = new ArrayList<>(List.of("hot","dot","dog","lot","log","cog"));

//        String beginWord = "hot";
//        String endWord = "dog";
//        List<String> wordList = new ArrayList<>(List.of("hot","dog","dot"));//
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<>(List.of("hot", "dot", "tog", "cog"));
        System.out.println("Expected Output is " + ladderLengthNeet(beginWord, endWord, wordList));

        System.out.println("Output is " + ladderLength(beginWord, endWord, wordList));
    }

    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;
        int l = beginWord.length();
        HashMap<String, HashSet<String>> streak = new HashMap<>();

        String word = null;

        for (int i = 0; i < wordList.size(); i++) {

            //begin word may not be in wordlist
            if (word == null && (!wordList.get(0).equals(beginWord))) {
                word = beginWord;
                i--;
            } else {
                word = wordList.get(i);
            }


            //calculate transform sequence
            //1. currentWord and nextWord

            for (String w : wordList) {
                if (w.equals(word)) continue;
                int t = weight(w, word, l);
                if (t == 1) {
                    HashSet<String> wordL = streak.getOrDefault(word, new HashSet<>());
                    wordL.add(w);
                    streak.put(word, wordL);
                    //word = w;
                }
            }
        }

        return dijkstrasAlgorithm(beginWord, endWord, streak).length;
    }

    public static int weight(String word1, String word2, int l) {
        //char[] char = new char[26];
        int diff = 0;

        for (int i = 0; i < l; i++) {
            diff += word2.charAt(i) != word1.charAt(i) ? 1 : 0;
        }


        return diff;
    }

    public static int ladderLengthNeet(String beginWord, String endWord, List<String> wordList) {
        Map<String, List<String>> adjlist = new HashMap<>();
        wordList.add(beginWord);
        for (String word : wordList) {
            StringBuilder string = null;
            for (int i = 0; i < word.length(); i++) {
                string = new StringBuilder(word);
                string.setCharAt(i, '*');
                List<String> wordlist = adjlist.getOrDefault(string.toString(), new ArrayList<String>());
                wordlist.add(word);
                adjlist.put(string.toString(), wordlist);
            }
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int step = 1;
        StringBuilder string = null;
        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int j = 0; j < size; j++) {
                String str = queue.poll();

                for (int i = 0; i < str.length(); i++) {
                    string = new StringBuilder(str);
                    string.setCharAt(i, '*');
                    for (String pat : adjlist.get(string.toString())) {
                        if (pat.equals(endWord)) {
                            return step;
                        }
                        if (visited.contains(pat)) {
                            continue;
                        }
                        queue.offer(pat);
                        visited.add(pat);
                    }
                }
            }
            // step++;
        }
        return 0;
    }


    private static class Edge {
        public String from;
        public String to;
        public int weight;

        public Edge(String from, String to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

    }

    public static String[] dijkstrasAlgorithm(String start, String end, HashMap<String, HashSet<String>> graph) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) return new String[]{};
        System.out.println(graph);

        String orphan = "~@#$%";
        int l = start.length();
        int[] cost = new int[graph.size()];
        String[] parent = new String[graph.size()];
        boolean[] completed = new boolean[graph.size()];
        HashMap<String, Integer> labelIndex = new HashMap<>();

        int count = 0;
        for (String label : graph.keySet()) {
            labelIndex.put(label, count);
            cost[count] = Integer.MIN_VALUE;
            completed[count] = false;
            parent[count] = orphan;
            count++;
        }

        System.out.println(labelIndex);

        PriorityQueue<Map.Entry<String, Integer>> q = new PriorityQueue<>(Map.Entry.comparingByValue());


        HashMap<String, Integer> m = new HashMap<>();
        m.put(start, cost[labelIndex.get(start)]);
        q.addAll(m.entrySet());
        m.clear();

        while (!q.isEmpty()) {
            Map.Entry<String, Integer> e = q.poll();

            String currentV = e.getKey();

            Integer currentI = labelIndex.get(currentV);
            if (completed[currentI]) continue;
            System.out.println(currentV);
            HashSet<String> neighbour = graph.get(currentV);

            for (String n : neighbour) {
                Integer ni = labelIndex.get(n);

                Integer currentWeight = cost[ni];
                String currentParent = parent[ni];

                String possibleParent = currentV;
                int weightFromPossibleParent = weight(possibleParent, n, l);

                if (currentParent.equals(orphan)) {
                    //set values

                    parent[ni] = possibleParent;
                    cost[ni] = weightFromPossibleParent;
                } else {
                    //compare cost form start vs cost from current

                    //cost form start
                    int costFromStart = 0;
                    String c = currentParent;
                    Integer ci = labelIndex.get(currentParent);

                    while (!c.equals(start)) {
                        int cCost = cost[ci];
                        costFromStart += cCost;
                        c = parent[ci];
                        ci = labelIndex.get(c);
                    }

                    //cost from current
                    int costFromCurrent = 0;
                    c = currentV;
                    ci = currentI;

                    while (!c.equals(start)) {
                        int cCost = cost[ci];
                        costFromCurrent += cCost;
                        c = parent[ci];
                        ci = labelIndex.get(c);
                    }

                    if (costFromCurrent < costFromStart) {
                        parent[ni] = possibleParent;
                        cost[ni] = weightFromPossibleParent;
                    } else {
                        parent[ni] = currentParent;
                        cost[ni] = currentWeight;
                    }

                }

                m.put(n, cost[ni]);
                q.addAll(m.entrySet());
                m.clear();

            }

            completed[currentI] = true;
        }

        String c = end;
        int i = labelIndex.get(c);

        Stack<String> path = new Stack<>();
        path.push(c);


        System.out.println("key : " + labelIndex.keySet().stream().sorted().toList());
        System.out.println("cost : " + Arrays.stream(cost).boxed().toList());
        System.out.println("parent : " + Arrays.stream(cost).boxed().toList());


        while (!c.equals(start)) {
            c = parent[i];
            i = labelIndex.get(c);
            path.push(c);
        }

        String[] minPath = new String[path.size()];
        count = 0;
        while (!path.isEmpty()) {
            minPath[count++] = path.pop();
        }

        return minPath;
    }

    /*private static String[] dijkstrasAlgorithm(String start, String end, HashSet<Edge> graph) {
        if (!graph.containsKey(start) || !graph.containsKey(end)) return new String[]{};

        HashMap<String,HashMap<>>



        String[] minCost = new String[]{};
        return minCost;
    }*/

}


