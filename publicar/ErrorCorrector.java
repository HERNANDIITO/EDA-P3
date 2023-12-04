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

        Compendium toCorrect = new Compendium();
        toCorrect.readFile(args[1]);
        
        Compendium base = new Compendium();
        base.readFile(args[2]);


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

    public static int getDistance( String word1, String word2 ) {
        int distance = 0;

        if ( word1 == null || word2 == null ) { return distance; }
        if ( word1.equals(word2) ) { return distance; }

        int n = word1.length();
        int m = word2.length();

        int[][] matrix = new int[n][m];

        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i;
        }

        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                if ( word1.charAt(i) == word2.charAt(j) ) {
                    matrix[i][j] = 0;
                }

                
            }
        }

        return distance;
    }
}
