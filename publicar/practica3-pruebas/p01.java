/**
* @author Alicia Garrido
* Se crea un objeto Compendium, se invoca su metodo readFile con un
* fichero con signos de puntuacion y diccionario. Se muestra el
* objeto Compendium por pantalla
*/

public class p01{
    public static void main(String[] args){
        Compendium conjunto=new Compendium();
        if(args.length==1)
            conjunto.readFile(args[0]);
        System.out.print(conjunto);
    }
}
