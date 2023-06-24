import java.util.ArrayList;

// The java.util.Set structure doesn't call the equals method on the elements
// that are added, so I made my own minimal set structure
public class MySet<E> {
    ArrayList<E> arr = new ArrayList<>();

    public void add(E el) {
        boolean found = false;
        for (int i=0; i<arr.size(); i++) {
            if (arr.get(i).equals(el)) found = true;
        }
        if (!found) arr.add(el);
    }

    public int size() {
        return arr.size();
    }
}
