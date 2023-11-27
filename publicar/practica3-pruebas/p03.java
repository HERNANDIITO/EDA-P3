/**
*  @author Alicia Garrido
*  Se crea un objeto IndexTree. Se insertan distintos ides y se muestra 
*  el arbol por pantalla. Se insertan distintas cadenas en minusculas en 
*  el arbol, y a continuacion, se insertan distintos ides para algunas 
*  de estas cadenas en minusculas. Por ultimo se muestra el arbol por pantalla 
*/

public class p03{
    private static void insertChain(IndexTree tree){
         String[] chains={"skull","schedule","sport","war","man","deal","weather","phantom"};
         int[][] ides={{10,14,16},
                  {1,2,5,8,12,15,20,22},{},
                  {1,3,4,6,8,13,20},
                  {2,3,4,6,7,9,21},
                  {1,2,7,11,13,15},
                  {13,19,21},{10,14,16}};
         for(int i=0;i<chains.length;i++)
               System.out.println("inserta "+chains[i]+" -> "+tree.insert(chains[i]));
         for(int i=0;i<chains.length&&i<ides.length;i++){
             for(int j=0;j<ides[i].length;j++){
                 boolean done=tree.insertId(chains[i],ides[i][j]);
                 if(!done)
                    System.out.println("el id "+ides[i][j]+" no se inserta con "+chains[i]);
             }
         }
    }
    public static void main(String[] args){
         IndexTree tree=new IndexTree();
         String chain=new String("skull");
         int[] ides={13,19,21};
         for(int j=0;j<ides.length;j++){
              boolean done=tree.insertId(chain,ides[j]);
              if(!done)
                  System.out.println("el id "+ides[j]+" no se inserta con "+chain);
         }
         System.out.println("----- ARBOL -------");
         System.out.print(tree);
         p03.insertChain(tree);
         System.out.println("----- ARBOL -------");
         System.out.print(tree);
  }
}
