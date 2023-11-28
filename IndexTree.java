import java.util.TreeMap;
import java.util.TreeSet;

public class IndexTree {
    private TreeMap<String, TreeSet<Integer>> tree;

    public IndexTree() {
        tree = new TreeMap<String, TreeSet<Integer>>();
    }

    public boolean isEmtpy() {
        return tree.isEmpty();
    }

    public boolean insert( String word ) {
        boolean inserted = false;
        return inserted;
    }
}
