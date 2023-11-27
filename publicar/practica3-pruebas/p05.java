/**
*  @author Alicia Garrido
*  Se crea un objeto IndexTree. Se insertan distintas cadenas en mayusculas 
*  y/o minusculas. Se invoca getWords del arbol y se muestra por pantalla.
*/
import java.util.*;
public class p05{

  public static void main(String[] args){
      IndexTree tree=new IndexTree();
      String[] chains={"schedule","SPORT","war","man","deal","PhAnToM","weather","Skull"};
      for(int i=0;i<chains.length;i++)
          System.out.println("se inserta "+chains[i]+" --> "+tree.insert(chains[i]));
      Set<String> palabras=tree.getWords();
      System.out.println("----- CONJUNTO DE PALABRAS DEL ARBOL -------");
      System.out.println(palabras);
  }

}
