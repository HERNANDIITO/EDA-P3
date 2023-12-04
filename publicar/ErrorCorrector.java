import java.util.ArrayList;
import java.util.Collections;

public class ErrorCorrector {
    public static void main(String[] args) {

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

        char mode = args[0].charAt(0);
        

    }

    public static void errorHandler( int errorType ) {
        switch (errorType) {
            case 0:
                System.out.println("Error: wrong arguments");
                break;
        
            default:
                break;
        }
    }

    public static int min( int num1, int num2, int num3 ) {
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(num1); nums.add(num2); nums.add(num3);
        return Collections.min(nums);
    }

    public static int getDistance( String word1, String word2 ) {
        int distance = 0;

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

        return matrix[matrix.length][matrix[0].length];
    }
}
