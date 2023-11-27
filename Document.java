import java.util.ArrayList;

public class Document {
    private int id;
    private String category;
    private ArrayList<Card> general;

    /**
     * @param idParam: ID del documento
     * @param wordParam: Categoría del documento
     */
    public Document( int idParam, String wordParam ){
        id = idParam;
        general = new ArrayList<Card>();

        if ( wordParam == null ) {
            category = "";
        } else {
            category = wordParam;
        }
    }

    /**
     * Contendra las palabras que aparecen en un documento con su frecuencia.
     * @return null: en caso de que la lista general este vacia. Card: en caso de tener la frecuencia maxima.
     */
    public Card moreFrequently(){
        if ( general.isEmpty() ) {
            return null;
        }

        Card result = general.get(0);
        for (Card card : general) {
            if ( card.getFrequency() > result.getFrequency() ) {
                result = card;
            }
        }
        return result;
    }

    /**
     * Anade la carta a {general} si la cartaa todavia no existe, en caso de existir, incrementa su frecuencia.
     * @param wordParam
     */
    public void addCard( String wordParam ){
        if (search(wordParam)) {
            for (Card card : general) {
                if ( card.getWord().equals(wordParam) ) {
                    card.increment();
                }
            }
        } else {
            Card aux = new Card(wordParam);
            general.add(aux);
        }

    }

    /**
     * Busca una card en {general} cuya {word} coincida con {wordParam}
     * @param wordParam
     * @return devuelve {true} o {false} segun se haya encontrado.
     */
    public boolean search( String wordParam ){
        boolean found = false;
        for (Card card : general) {
            Card aux = new Card(wordParam);

            if ( card.equals(aux)) {
                found = true;
            }
        }
        return found;
    }
    
    /**
     * @return this.id
     */
    public int getId(){
        return id;
    }
    
    /**
     * @return this.category
     */
    public String getCategory(){
        return category;
    }

    /**
     * @return this.general
     */
    public ArrayList<Card> getGeneral(){
        return general;
    }

    /**
     * @return this.general
     */
    public String toString(){
        String result = id + " - " + category + "\n";
        for (Card card : general) {
            result += card.toString() + "\n"; 
        }

        return result;
    }
}
