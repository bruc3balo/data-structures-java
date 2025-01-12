import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

class FilterInterview {
    public static void main(String[] args) {

        var it = new FilterIterator<>(Arrays.stream(new int[]{1, 2, 3, 4, 5}).iterator(), x -> x > 3);

        System.out.println(it.hasNext());
        System.out.println(it.hasNext());

        System.out.println(it.next());
        System.out.println(it.hasNext());

        System.out.println(it.next());
        System.out.println(it.hasNext());

    }

    static class FilterIterator<T> implements Iterator<T> {
        private final Iterator<T> iterator;
        private final Predicate<T> predicate;
        private T next;

        public FilterIterator(Iterator<T> iterator, Predicate<T> predicate) {
            this.iterator = iterator;
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            if (next != null) return true;
            while (iterator.hasNext()) {
                var temp = iterator.next();
                if (predicate.test(temp)) {
                    next = temp;
                    return true;
                }
            }
            return false;
        }

        @Override
        public T next() {
            if (this.next == null && !hasNext()) throw new NoSuchElementException();
            var temp = next;
            this.next = null;
            return temp;
        }

    }
}
