/**
*  @author Alicia Garrido
*  Se invoca la aplicacion ErrorCorrector con la opcion normalizar, un fichero de datos 
*  que contiene un diccionario con pocas palabras y un texto corto para corregir.
*/

public class p08{
  public static void main(String[] args){
    String[] param1={"N",args[0],args[1]};
    ErrorCorrector.main(param1);
  }
}
