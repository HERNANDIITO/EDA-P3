// DNI 24504004T HERNANDEZ GARCIA, PABLO 

public class Card {
    private String word;
    private int frequency;
    
    /**
     * Constructor al que se le pasa por parametro una cadena que debe almacenar
     * en word en minusculas e inicializar a 1 la frecuencia.
     * 
     * Si el parametro es null o la cadena vacia, la frecuencia sera 0 y en word se almacena la cadena vacia
     * @param wordArg: word de la carta.
     */
    public Card( String wordArg ) {
        if ( wordArg == null || wordArg == "" ) {
            word = "";
            frequency = 0;
        } else {
            word = wordArg.toLowerCase();
            frequency = 1;
        }
    };

    /**
     * @param cardArg: card objetivo
     * @return {true} o {false} dependiendo de si el word de ambas cartas coincide
     */
    public boolean equals( Card cardArg ) {
        return word.equals(cardArg.getWord());
    };

    /**
     * Incrementa la frecuencia de la carta en una unidad.
     */
    public void increment() {
        frequency++;
    };

    /**
     * @return this.frequency
     */
    public int getFrequency() {
        return frequency;
    };

    /**
     * @return this.word
     */
    public String getWord() {
        return word;
    };

    /**
     * @return {word} {frequency}
     */
    public String toString() {
        return word + " " + frequency;
    };
}
