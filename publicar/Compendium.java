import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Compendium {
    private ArrayList<Document> documents;
    private ArrayList<String> dictionary;

    /**
     * Iniciailza los arraylists
     */
    public Compendium() {
        documents = new ArrayList<Document>() {};
        dictionary = new ArrayList<String>() {};
    };

    public String replaceSepCharacs( String line ) {
        String result = line;

        result = result.replace(',', ' ');
        result = result.replace(':', ' ');
        result = result.replace(';', ' ');
        result = result.replace('?', ' ');
        result = result.replace('!', ' ');
        result = result.replace('.', ' ');
        result = result.replace('(', ' ');
        result = result.replace(')', ' ');

        return result;
    }
    
    /**
     * DICTIONARY: 
     * word1 word2 word3
     * doc.toString()
     * @return result
     */
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

    public void readFile(String fileName) {
        boolean lookingForCat = false;
        boolean workingOnDic = false;
        Document docAux;
        int idCurrentDoc = 0;

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.equals("<DOC>")) {
                    lookingForCat = true;
                    idCurrentDoc++; 
                    continue;
                }

                if ( line.equals("<DIC>") ) {
                    workingOnDic = true;
                    continue;
                }

                if ( lookingForCat ) {
                    docAux = new Document( idCurrentDoc, line );
                    this.documents.add(docAux);
                    lookingForCat = false;
                    continue;
                }

                line = replaceSepCharacs(line);
                line = line.replace("  ", " ");
                line = line.strip();
                line = line.trim();

                String[] words = line.split(" ");

                for (String word : words) {
                    if ( word.isEmpty() ) { continue; }
                    word = word.toLowerCase();

                    if ( workingOnDic ) {
                        this.dictionary.add(word);
                    } else {
                        this.documents.get(idCurrentDoc - 1).addCard(word);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    };


    /**
     * @param word
     * @return devuelve una ArrayList de IDs cuyos documentos contienen word
     */
    public ArrayList<Integer> search( String word ) {
        ArrayList<Integer> result = new ArrayList<Integer>() {};

        for (Document doc : documents) {
            if ( doc.search(word) ) {
                result.add(doc.getId());
            }
        }

        return result;
    };

    /**
     * @return this.documents
     */
    public ArrayList<Document> getDocuments() {
        return documents;
    }

    /**
     * @return this.dictionary
     */
    public ArrayList<String> getDictionary() {
        return dictionary;
    }
}
