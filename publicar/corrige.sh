#! /bin/bash
tempo=10
total=0
diferencias=""
compilador=javac
interprete=java
fuentes=$(ls *.java 2>/dev/null)
directorio=practica3-pruebas
cantidad=$(ls *.java 2>/dev/null |wc -l)
numfuentes=0
nota=0
rm -rf *.tmp  $directorio/*.tmp $directorio/*.terr *.class $directorio/*.class $directorio/*.tmp.err
function genMata() {
if ! test -x mata ; then  # si ya existe no se genera
echo "#include <stdlib.h>
#include <sys/time.h>
#include <signal.h>
#include <unistd.h>
#include <stdio.h>

int f;
char *programa;

void SeAcabo(int i)
{
  kill(f,SIGKILL);
  fprintf(stderr,\"MATA matando... %s\n\",programa);
  fflush(stderr);
  exit(-1);
}
void Salir(int i)
{exit(1);}

int main(int argc,char *argv[])
{
  if (argc < 3)
      exit(1);

  programa = argv[2];
  signal(SIGALRM,SeAcabo);
  signal(SIGCHLD,Salir);
  alarm(atoi(argv[1]));

  if ((f = fork()) == 0)
        execvp(argv[2],&argv[2]);
  
  if (f != -1)
        while(1);
}
" > mata.c
gcc -o mata mata.c
mv mata $directorio
rm -rf mata.c
fi
}

function CorrigeResto(){
    nombre=$1
    nota=0
    if test -e $nombre.tmp; then
      diff -b $nombre.tmp $nombre.txt > d1.tmp
      nlin=$(cat d1.tmp|wc -l)
      if test $nlin -eq 0; then
        nota=1
      else
        nota=0
        diferencias="diferencias en fichero salida txt; ejecuta diff -b $nombre.txt $nombre.tmp"
      fi
      rm -rf d?.tmp
    else
    	nota=0
    	diferencias="no se ha generado $nombre.tmp"
    fi  
    return $nota
}


function ejecutaUna(){
  numlin=$(cat $1.terr | wc -l)
  if test $numlin -eq 0; then
  	if test -e $1.doc; then
  		./mata $tempo $interprete $1 $1.dat $1.doc >$1.tmp 2>$1.tmp.err
  	else
  		if test -e $1.dat; then
  			if test -e $1.dat2; then
  				./mata $tempo $interprete $1 $1.dat $1.dat2 >$1.tmp 2>$1.tmp.err
  			else
  				./mata $tempo $interprete $1 $1.dat >$1.tmp 2>$1.tmp.err
  			fi
  		
  		else 
  			./mata $tempo $interprete $1 >$1.tmp 2>$1.tmp.err
  		fi
  	fi
   	numlin=$(cat $1.tmp.err|wc -l)
   	if test $numlin -eq 0; then
      		CorrigeResto $1
      		nota=$?
      		if test $nota -eq 1; then
        		echo "Prueba $1: Ok"
        		total=$(echo "$total+0.5"|bc)
      		else
        		echo "Prueba $1: $diferencias" 
      		fi
   	else
      		echo "Prueba $1: Hay errores de ejecucion"
      		cat $1.tmp.err
   	fi
  else
   echo "Prueba $1: hay errores de compilacion" 
   cat $1.terr
  fi
 rm -rf $1.terr 
}


for fichero in $fuentes; do
 	if test $fichero == Card.java || test $fichero == Document.java || \
    	   test $fichero == Compendium.java || test $fichero == IndexTree.java || test $fichero == ErrorCorrector.java ; then
  		if test -f $fichero; then
  			let numfuentes=numfuentes+1
  		fi
 	fi
done

if test $numfuentes -eq 5 -a $cantidad -eq 5; then
  	continuar=true
else
  	continuar=false
  	echo "Error, ficheros fuente requeridos: Card.java, Document.java, Compendium.java, IndexTree.java y ErrorCorrector.java ; 0"
fi
genMata
if $continuar; then
 	$compilador *.java >/dev/null 2> errores.compilacion
 	grep "error:" errores.compilacion >numerror 
 	numlin=$(cat numerror | wc -l)
 	if test $numlin -ne 0; then
  		echo "Error de compilacion; 0"
  		rm -rf numerror
  		exit 1
 	else
  		rm -rf errores.compilacion 
 	fi
 	rm -rf numerror
 	mv *.class $directorio
 	cd  $directorio
 	ficherosprueba=$(ls *.java)
 	total=0
 	if test $# == 1; then
   		prueba=$1.java
   		if test -e $prueba; then
   			$compilador $prueba 2> $1.terr 	
   			ejecutaUna $1
   		fi
 	else
 		for prueba in $ficherosprueba; do
  			nombre=$(basename $prueba .java)
  			$compilador $prueba 2> $nombre.terr 
  			ejecutaUna $nombre
  			rm -rf d1.tmp $nombre.terr 
 		done
 	fi
 	echo "Nota: $total"
	rm -rf mata
fi
