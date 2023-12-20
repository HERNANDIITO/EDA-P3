// DNI 24504004T HERNANDEZ GARCIA, PABLO 

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class IndexTree {
    private TreeMap<String, TreeSet<Integer>> tree;

    /**
     * Inicializa la variable de instancia
     */
    public IndexTree() {
        tree = new TreeMap<String, TreeSet<Integer>>();
    }

    /**
     * Devuelve un booleano que indica si el arbol esta vacio o no
     * @return true/false
     */
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    /**
     * Intenta nsertar la palbra pasada por parametro y devuelve un booleano que indica si lo ha conseguido
     * @param word
     * @return true/false
     */
    public boolean insert( String word ) {
        if ( word == null || word.isEmpty() ) { return false; }
        if ( tree.containsKey(word) ) { return false; }

        TreeSet<Integer> valueAux = new TreeSet<>();
        tree.put(word.toLowerCase(), valueAux);

        return true;
    }

    /**
     * Intenta insertar una ID en la palabra indicada y devuelve un booleano indicando si lo ha conseguido
     * @param word
     * @param id
     * @return true/false
     */
    public boolean insertId( String word, int id ) {
        if ( word == null || word.isEmpty() ) { return false; } 

        TreeSet<Integer> values = tree.get(word);
        if ( values == null ) { return false; }

        return values.add(id);
    }


    /**
     * Elimina una palabra del arbol y devuelve su contenido
     * @param word
     * @return TreeSet<Integer> PUEDE SER NULL
     */
    public TreeSet<Integer> erase( String word ) {
        if ( !tree.containsKey(word) ) { return null; }

        TreeSet<Integer> result = tree.get(word);
        tree.remove(word);

        return result;
    }

    /**
     * se le pasa por parametro el identificador de un documento. Elimina de los valores asociados a las palabras del arboldicho identificador.
     * Devuelve un ArrayList con las palabras de las cuales se haeliminado el identificador.
     * Si en el arbol no aparece el identificador pasado porparametro, el método devuelve una referencia vacia (null).
     * @param docId
     * @return ArrayList<String> PUEDE SER NULL
     */
    public ArrayList<String> erase(int docId) {
        ArrayList<String> result = new ArrayList<>();

        for ( String key : tree.keySet()) {
            if ( tree.get(key).remove(docId) ) {
                result.add(key);
            }
        }

        if ( result.isEmpty() ) {
            return null;
        }

        return result;
    }

    /**
     * Devuelve una copia del subconjunto del arbol formado por las palabras que comiencen por el caracter pasado por parametro.
     * Si en el arbol no hay ningun nodo cuya cadena comience por el caracter pasado por parametro el método devuelve una referenciavacia (null).
     * @param c
     * @return TreeMap<String, TreeSet<Integer>> PUEDE SER NULL
     */
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

    /**
     * se le pasa por parámetro el identificador de un documento. Devuelve un TreeSet con las palabras entre cuyos valores asociados se encuentre el identificador pasado por parámetro. Si en el árbol no aparece el identificador pasado por parámetro, el método devuelve una referencia vacía (null).
     * 
     * @param id
     * @return
     */
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

    /**
     * @return keyset
     */
    public Set<String> getWords() {
        return tree.keySet(); 
    }

    /**
     * Devuelve los documentos asignados a una palabra
     * @param word
     * @return docs
     */
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

    /**
     * Inserta un compendium
     * @param comp
     */
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
