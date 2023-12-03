import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class IndexTree {
    private TreeMap<String, TreeSet<Integer>> tree;

    public IndexTree() {
        // TreeMap<clave, valor>
        tree = new TreeMap<String, TreeSet<Integer>>();
    }

    public boolean isEmpty() {
        return tree.isEmpty();
    }

    public boolean insert( String word ) {
        if ( word == null || word.isEmpty() ) { return false; }
        if ( tree.containsKey(word) ) { return false; }

        TreeSet<Integer> valueAux = new TreeSet<>();
        tree.put(word.toLowerCase(), valueAux);

        return true;
    }

    public boolean insertId( String word, int id ) {
        if ( word == null || word.isEmpty() ) { return false; } 

        TreeSet<Integer> values = tree.get(word);
        if ( values == null ) { return false; }

        return values.add(id);
    }

    public TreeSet<Integer> erase( String word ) {
        if ( !tree.containsKey(word) ) { return null; }

        TreeSet<Integer> result = tree.get(word);
        tree.remove(word);

        return result;
    }

    public ArrayList<String> erase(int docId) {
        ArrayList<String> result = new ArrayList<>();

        for ( String key : tree.keySet()) {
            if ( tree.get(key).remove(docId) ) {
                result.add(key);
            }
        }

        return result;
    }

    public TreeMap<String, TreeSet<Integer>> search( char c ) {
        TreeMap<String, TreeSet<Integer>> result = new TreeMap<String, TreeSet<Integer>>();
        String initial = String.valueOf(c);

        for ( String key : tree.keySet()) {
            if ( key.startsWith(initial) ) {
                TreeSet<Integer> value = new TreeSet<>();

                for (int id : tree.get(key)) {
                    value.add(id);
                }

                result.put(key, value);
            }
        }

        if (result.isEmpty()) { return null; }
        return result;
    }

    public TreeSet<String> search( int id ) {
        TreeSet<String> result = new TreeSet<>();

        for (String key : tree.keySet()) {
           if ( tree.get(key).contains(id) ) {
            result.add(key);
           } 
        }

        if ( result.isEmpty() ) { return null; }

        return result;
    }

    public Set<String> getWords() {
        return tree.keySet(); 
    }

    public TreeSet<Integer> getDocuments( String word ) {
        return tree.get(word);
    }

    public String toString() {
        String result = "";

        for (String key : tree.keySet()) {
            String keyString = "";
            
            keyString += (key + " *");
            
            for (int id : tree.get(key)) {
                keyString += " " + id + " -";
            }
            
            if ( keyString.endsWith("-") ) {
                keyString = keyString.substring(0, keyString.length() - 1);
            }
            
            result += keyString += '\n';
        }


        return result;
    }

    public void insertCompendium( Compendium comp ) {

        if ( comp ==  null ) { return; }

        for (String word : comp.getDictionary()) {
            TreeSet<Integer> IDs = new TreeSet<>();

            for (Document doc : comp.getDocuments()) {
                if ( doc.search(word) ) {
                    IDs.add(doc.getId());
                }
            }

            if ( tree.containsKey(word) ) {
                tree.get(word).addAll(IDs);
            } else {
                tree.put(word, IDs);
            }

        }
    }
}
