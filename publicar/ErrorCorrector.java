// DNI 24504004T HERNANDEZ GARCIA, PABLO 

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class ErrorCorrector {

    /*
     * Clase para devolver comodamente la palabra corregida y la diferencia de edicion
     */
    public static class DistanceWord {
        public double distance;
        public String word;

        public DistanceWord( double distanceArg, String wordArg ) {
            distance = distanceArg;
            word = wordArg;
        }
    }

    public static void main(String[] args) {

        Locale idioma = new Locale("en");
        DecimalFormatSymbols caracs = new DecimalFormatSymbols(idioma);
        DecimalFormat distanceResult = new DecimalFormat ( " #.#### " , caracs );

        for (String arg : args) {
            if ( arg == null ) {
                errorHandler(0);
                return;
            }
        }

        if ( args[0] != "W" && args[0] != "N" ) {
            errorHandler(0);
            return;
        }

        String mode = args[0];

        Compendium compAux = new Compendium();
        
        IndexTree trueText = new IndexTree();
        compAux.readFile(args[1]);
        trueText.insertCompendium(compAux);

        ArrayList<String> toCorrect = new ArrayList<String>();

        try {
            File file = new File(args[2]);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine();
                line = replaceSepCharacs(line);
                String[] words = line.split(" ");

                for (String word : words) {
                    toCorrect.add(word);
                }

            }
            scanner.close();

        } catch (FileNotFoundException e) {

            System.out.println(e);
            e.printStackTrace();
            
        }

        if ( mode.equals("N") ) {
            System.out.println("WRONG WORDS (NORMALIZED)");
        } else {
            System.out.println("WRONG WORDS (WITHOUT NORMALIZING)");
        }
        
        for (String word : toCorrect) {
            DistanceWord minDistance = getMinDistance(word, trueText, mode);
            if ( minDistance.distance > 0 ) {
                String result = word + " " + minDistance.word + " " + distanceResult.format(minDistance.distance);
                System.out.println(result);
            }
        }

    }

    /**
     * Elimina los caracteres de separacion de un string y lo devuelve correctamente formateado
     * @param line
     * @return
     */
    public static String replaceSepCharacs( String line ) {
        String result = line;

        result = result.replace(',', ' ');
        result = result.replace(':', ' ');
        result = result.replace(';', ' ');
        result = result.replace('?', ' ');
        result = result.replace('!', ' ');
        result = result.replace('.', ' ');
        result = result.replace('(', ' ');
        result = result.replace(')', ' ');
        result = result.replace("  ", " ");
        result = result.strip();
        result = result.trim();

        return result;
    }

    /**
     * Gestiona los mensajes de error (un poco inutilo porque solo hay uno)
     * @param errorType
     */
    public static void errorHandler( int errorType ) {
        switch (errorType) {
            case 0:
                System.out.println("Error: wrong arguments");
                break;
        
            default:
                break;
        }
    }

    /**
     * Devuelve la distancia minima entre la palabra pasada por parametro y todas las contenidas en el arbol
     * @param wordArg
     * @param treeAux
     * @param mode
     * @return DistanceWord { distance: distancia minima, word: palabra corregida }
     */
    public static DistanceWord getMinDistance( String wordArg, IndexTree treeAux, String mode ) {
        double distance = -1;
        String realWord = "";
        wordArg = wordArg.toLowerCase();

        for (String word : treeAux.getWords() ) {
            word = word.toLowerCase();
            double distanceAux = getDistance(wordArg, word, mode);
            if ( distanceAux < distance || distance == -1 ) {
                // System.out.println("word: " + wordArg + " | Word: " + word + " | Distance: " + distanceAux);
                distance = distanceAux;
                realWord = word;
            }
        }

        DistanceWord result = new DistanceWord(distance, realWord);

        return result;
    }

    /**
     * Devuelve el numero minimo de entre tres pasados por parametro (parte de la funcion getDistance)
     * @param num1
     * @param num2
     * @param num3
     * @return uno de los argumentos
     */
    public static int min( int num1, int num2, int num3 ) {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(num1); nums.add(num2); nums.add(num3);
        return Collections.min(nums);
    }

    /**
     * Calcula la distancia de edicion entre word1 y word2 y la devuelve normalizada o no dependiendo de mode.
     * @param word1
     * @param word2
     * @param mode
     * @return distancia de edicion
     */
    public static double getDistance( String word1, String word2, String mode ) {
        double distance = 0;

        if ( word1 == null || word2 == null ) { return distance; }
        if ( word1.equals(word2) ) { return distance; }

        int X = word1.length() + 1;
        int Y = word2.length() + 1;

        if ( X == 0 ) { return Y; }
        if ( Y == 0 ) { return X; }
 
        int[][] matrix = new int[X][Y];
        
        // --- X ---
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }

        // --- Y ---
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i;
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if ( word1.charAt(i - 1) == word2.charAt(j - 1) ) {
                    matrix[i][j] = min(matrix[i-1][j] + 1, matrix[i-1][j-1], matrix[i][j-1] + 1);
                    continue;
                }

                matrix[i][j] = min(matrix[i-1][j] + 1, matrix[i-1][j-1] + 1, matrix[i][j-1] + 1);
            }
        }

        distance = matrix[matrix.length - 1][matrix[0].length - 1];

        if ( mode.equals("N") ) {
            distance = distance / ((X - 1) + (Y - 1));
            return distance;
        }

        return distance;
    }
}
