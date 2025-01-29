import java.util.ArrayList;
import java.util.List;

public class PlayerComparator {

    private static class Player implements Comparable<Player> {
        private final String name;
        private final int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Player o) {
            int compare = Integer.compare(score, o.score);
            return switch (compare) {
                case -1 -> 1;
                case 1 -> -1;
                default -> name.compareTo(o.name);
            };
        }

        @Override
        public String toString() {
            return "Player{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Alice", 50));
        players.add(new Player("Bob", 100));
        players.add(new Player("Charlie", 50));
        players.add(new Player("David", 75));

        players.stream().sorted().forEach(System.out::println);
    }
}
