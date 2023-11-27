import java.util.ArrayList;

public class Compendium {
    private ArrayList<Document> documents;
    private ArrayList<String> dictionary;

    public Compendium() {
        documents = new ArrayList<Document>() {};
        dictionary = new ArrayList<String>() {};
    };

    public String toString() {
        String result = "DICTIONARY: \n";
        for (String string : dictionary) {
            result += string + " ";
        }
        result += "\n";

        for (Document doc : documents) {
            result += doc.toString();
        }

        return result;
    };

    public void readFile( String fileName ) {

    };

    public ArrayList<Integer> search( String word ) {
        ArrayList<Integer> result = new ArrayList<Integer>() {};

        for (Document doc : documents) {
            if ( doc.search(word) ) {
                result.add(doc.getId());
            }
        }

        return result;
    };

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public ArrayList<String> getDictionary() {
        return dictionary;
    }
}
