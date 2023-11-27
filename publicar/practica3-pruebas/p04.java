/**
*  @author Alicia Garrido
*  Se crea un objeto IndexTree y un objeto Compendium. Se invoca readFile de 
*  compendium con un fichero con signos de puntuacion y diccionario. Se invoca 
*  insertCompendium del arbol con el compendium y se muestra el arbol por pantalla
*/

public class p04{
    public static void main(String[] args){
      IndexTree tree=new IndexTree();
      Compendium conjunto=new Compendium();
      if(args.length==1)
        conjunto.readFile(args[0]);
      tree.insertCompendium(conjunto);
      System.out.println("----- ARBOL -------");
      System.out.print(tree);
    }
}