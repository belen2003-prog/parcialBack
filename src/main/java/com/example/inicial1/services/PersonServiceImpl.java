package com.example.inicial1.services;

import com.example.inicial1.entities.Person;
import com.example.inicial1.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceImpl {
    @Autowired
    private static PersonRepository personRepository;
    public PersonServiceImpl(PersonRepository personRepository){
        this.personRepository=personRepository;
    }

    //primero valido el tamaño del string ingresado y que tenga los caracteres correctos
    private static boolean validarDna(String[]dna){
        if(dna==null || dna.length==0){
            return false;  //ingreso una secuencia vacia o nula
        }
        //ahora vamos a ver q todas las cadenas tengan la misma longitud
        int fila0 =dna[0].length();
        for(String elemento :dna){
            if(elemento.length() != fila0){
                return false;
            }
            for(char letras : elemento.toCharArray()){ //converti el primer string en una arreglo de caracteres
                if(!"ACGT".contains(String.valueOf(letras))){
                    return false; //letras invalidas
                }
            }
        }
        return true; //el dna pasado es valido
    }

    private static int filas(String[] dna, int cantCoincidencias) {
        //obtengo la primera fila
        //String primerafila = f[0];
        for (String fila : dna) {
            //obtengo los primeros does elementos de la fila (0 y 1)
            String primerasDos = fila.substring(0,2);
            //System.out.println(primerasDos);
            if(primerasDos.charAt(0)==primerasDos.charAt(1)){
                //si son iguales comparo si son iguales las siguentes dos letras
                String siguientesDos =fila.substring(2,4);
                if(primerasDos.equals(siguientesDos)){
                    // System.out.println("posible mutante"+fila);
                    cantCoincidencias=cantCoincidencias+1;
                    //System.out.println("tenemos "+cantCoincidencias+" coincidencia/s y se encontro la cadena sig "+primerasDos+siguientesDos);
                } /*else{
                        System.out.println("no hay coincidencia la fila  "+fila);
                    }
                    */

            }else{
                //System.out.println("las primeras dos letras no coinciden");
                String otrasDos = fila.substring(2,4);
                //como hago para que vaya verificando que tenga 4 pociciones desde el primer elemento que busco en otros dos
                if (otrasDos.charAt(0)==otrasDos.charAt(1)){
                    //busco las dos que le siguen
                    String siguientes =fila.substring(4,6);
                    //System.out.println(siguientes);
                    if(otrasDos.equals(siguientes)){
                        //System.out.println("posible mutante"+fila);
                        cantCoincidencias=cantCoincidencias+1;
                        //System.out.println("tenemos "+cantCoincidencias+" coincidencia/s y se encontro la cadena sig "+otrasDos+siguientes);
                    }//else{
                    //System.out.println("no hay coincidencia en la fila  "+fila);
                    // }
                }

            }

        }
        return cantCoincidencias;
    }

    private static int columnas(String[] dna, int cantCoincidencias) {
        //verifico la longitud de la primera fila por lo que la matriz es nxn
        int longitud =dna[0].length();
        //System.out.println(longitud);
        //obtengo cada columna
        for(int columna=0;columna<longitud; columna++){
            //System.out.println("vuelta"+columna);
            //en la columna que estoy recorro 4 fila viendo el elemento que se encuentra en esa pocicion(columna)
            for(int filas=0;filas< dna.length-4;filas++){
                //primer letra de la fila la obtengo de la fila 0 columna 1 y asi sucesivo
                char primerL =dna[0].charAt(columna);
                //  System.out.println(primerL);
                int similitud=1;  //la primera letra es igual que con ella misma
                //ahora busco la fila que le sigue y leo su elemento de esa misma columna
                for (int j=filas+1 ;j<dna.length; j++){
                    if(primerL==dna[j].charAt(columna) ){
                        similitud=similitud+1;
                        //System.out.println(similitud+"de la letra"+primerL);
                    }else{
                        //System.out.println("no hay similitud con la sig fila de la columna");
                        //similitud=1;
                        primerL=dna[1].charAt(columna);
                    }
                }
                if(similitud ==4){
                    // System.out.println("mutante en columna " + columna + " desde la letra " + primerL );
                    cantCoincidencias++;
                    //System.out.println("Tenemos " + cantCoincidencias + " coincidencia/s en columnas");
                    break;
                }
            }
        }

        return cantCoincidencias;
    }

    private static int diagonal(String[] dna, int cantCoincidencias) {
        //recorro y compar la diagonal
        int e =dna[0].length();
        for (int i = 0; i < dna.length - 3; i++) {
            for (int j = 0; j < e-3; j++) {
                char primera=dna[i].charAt(j);
                if (primera == dna[i + 1].charAt(j + 1) &&
                        dna[i + 1].charAt(j + 1) == dna[i + 2].charAt(j + 2) &&
                        dna[i + 2].charAt(j + 2) == dna[i + 3].charAt(j + 3)) {
                    cantCoincidencias++;
                    //System.out.println("Coincidencia  en (" + i + ", " + j + ")");
                }

            }
        }
        //diagonal inversa
        for (int i = 0; i < dna.length - 3; i++) {
            for (int j = dna.length - 1; j >= 3; j--) {
                char primera=dna[i].charAt(j);
                //comparo elementos de la diagonal o subdiagonal mientras tenga 3 elementos por delante
                if (primera == dna[i + 1].charAt(j - 1) &&
                        dna[i + 1].charAt(j - 1) == dna[i + 2].charAt(j - 2) &&
                        dna[i + 2].charAt(j - 2) == dna[i + 3].charAt(j - 3)) {
                    cantCoincidencias++;
                    //System.out.println("Coincidencia en  inversa  (" + i + ", " + j + ")");
                }

            }
        }
        return cantCoincidencias;
    }


    public static boolean isMutant(String[] dna) throws Exception {
        if(!validarDna(dna)) {
            throw new Exception("El DNA no es válido"); //si el dna no es valido lanza excepcion
        }
        //busco en la base de datos un objeto person a travez de su dna
        String dnaS = String.join(",", dna); // Cambiar el formato para la consulta
        Optional<Person> dnabd = personRepository.findByDna(dnaS); // Usar el formato concatenado

        if (dnabd.isPresent()) {
            return dnabd.get().isMutante();
        }


        try {

            int cantCoincidencias = 0;
            cantCoincidencias = filas(dna, cantCoincidencias);
            cantCoincidencias = columnas(dna, cantCoincidencias);
            cantCoincidencias = diagonal(dna, cantCoincidencias);
            // return cantCoincidencias >= 2; //no deberia retornar true?
            if (cantCoincidencias >= 2) {
                return true;
            } else {
                return false;
            }


        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    // Metodo para guardar una persona
    public void savePerson(Person person) {
        personRepository.save(person); // Guardar en la base de datos
    }

}



