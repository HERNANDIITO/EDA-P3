/**
*  @author Alicia Garrido
*  Se crea un objeto IndexTree. Se insertan distintas cadenas en minusculas 
*  en el arbol y se muestra por pantalla
*/
public class p02{

  public static void main(String[] args){
    IndexTree tree=new IndexTree();
    String[] chains={"schedule","sport","war","man","deal","weather","food","apple","pumpkin","phantom"};
    for(int i=0;i<chains.length;i++)
      System.out.println("se inserta "+chains[i]+" --> "+tree.insert(chains[i]));
    System.out.println("----- ARBOL -------");
    System.out.print(tree);
  }

}
