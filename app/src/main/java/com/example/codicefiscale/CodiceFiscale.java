package com.example.codicefiscale;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CodiceFiscale {





    //Dichiarazione variabili per il codice fiscale
    private static String gender;
    private static Integer day;
    private static String month;
    private static Integer year;
    private static String name;
    private static String surname;
    private static String placeOfBirth;

    //Arraylist per effettuare bene il calcolo dei numeri relativi ai giorni nel codice fiscale
    private static ArrayList<String> dayArr;

    //Inizializzazione variabili consonanti e vocali
    private static String Consonants = "BCDFGHJKLMNPQRSTVWXYZ";
    private static String Vowels = "AEIOU";

    //Dichiarazione di una variabile che contiene il riferimento alla main activity
    private static Context c;




    //Dichiarazione delle Hasmap per le tabelle di conversione utili per calcolare l'ultimo numero
    private static Map<Character,Integer> char_to_even = new HashMap<>();  //caratteri e corrispettivi interi per posizioni pari
    private static Map<Character,Integer> char_to_odd = new HashMap<>();     //caratteri e corrispettivi interi per posizioni dispari

    private static Map<Integer,Character> number_to_char = new HashMap<>();  //per convertire il resto in una lettera


    //Inizializziamo la stringa che conterrà il giorno di nascita
    private static String d = "";






    //Costruttore
    public CodiceFiscale(String name, String surname, String placeOfBirth, String gender, Integer day, String month, Integer year, ArrayList<String> dayArr, Context c){
        this.name = name;
        this.surname = surname;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayArr = dayArr;
        this.c = c;



    }



    public static String getCF() throws IOException {



        String CFWCC = CalculateCodiceFiscale_WitouthControlChar(name,surname,year,day,gender,month,c,placeOfBirth);
        String ControlChar = CalculateControlChar(CFWCC);

        String CF = CalculateCodiceFiscale(CFWCC,ControlChar);

        return CF;

    }




    //FASE 1: Calcolo Consonanti e Vocali (nel caso mancassero consonanti)
    public static String CalculateSurname(String s){

        //Variabile per contare le consonanti
        int Consonants_Count = 0;

        //Stringa che conterrà le consonanti del cognome utili per costruire il codice fiscale
        String ConsSurname = "";










        //Scorriamo le due stringhe e confrontiamo i caratteri, se ci sono delle consonanti
        // le aggiungiamo alla stringa da ritornare
        for(char j: s.toCharArray()){
            for(char i: Consonants.toCharArray()){


                if(Consonants_Count<3){
                    if(i == j){
                        ConsSurname = ConsSurname + i;

                        Consonants_Count++;
                    }
                }




            }
        }

        //se non ci sono abbastanza consonanti nel cognome, controlliamo le vocali scorrendo le due stringhe e nel caso
        // ci fosse una corrispondenza le aggiungiamo alla stringa da ritornare
        if(Consonants_Count < 3){
            for(char j:s.toCharArray()){
                for(char i:Vowels.toCharArray()){

                    while (Consonants_Count<3){

                        if(i == j){
                            ConsSurname = ConsSurname + i;
                        }
                        Consonants_Count++;
                    }


                }
            }
        }


        return  ConsSurname;
    }










    //FASE 2: Calcolo Consonanti e Vocali (Se mancano le consonanti) del nome
    public static String CalculateName(String n){




        //Stringa che conterrà le consonanti del nome utili per costruire il codice fiscale
        String ConsName = "";
        int Consonants_Count = 0;



        //Scorriamo le due stringhe e confrontiamo i caratteri, se ci sono delle consonanti
        // le aggiungiamo alla stringa da ritornare



        for(char j: n.toCharArray()){
            for(char i: Consonants.toCharArray()){

                if(Consonants_Count > 5){
                    break;
                }

                if(i == j){
                    if (Consonants_Count!=2){
                        ConsName = ConsName + i;
                        Consonants_Count++;
                    }
                    Consonants_Count++;
                }
            }
        }




















        //se non ci sono abbastanza consonanti nel nome, controlliamo le vocali scorrendo le due stringhe e nel caso
        // ci fosse una corrispondenza le aggiungiamo alla stringa da ritornare
        if(Consonants_Count < 3){
            for(char j:n.toCharArray()){
                for(char i:Vowels.toCharArray()){

                    while (Consonants_Count < 3){

                        if(i == j){
                            ConsName = ConsName + i;
                        }

                        Consonants_Count++;
                    }


                }
            }
        }







        return ConsName;
    }



    //Prendiamo le ultime due cifre dell'anno di nascita
    public static String CalculateYear(Integer year){
        String y = "";

        y = y + String.valueOf(year).charAt(2) + String.valueOf(year).charAt(3);





        return y;
    }

    //In base al mese di nascita assegnamo una lettera
    public static String CalculateMonth(String month){
        String m = "";

        switch (month){
            case "GENNAIO":
                m = "A";
                break;
            case "FEBBRAIO":
                m = "B";
                break;
            case "MARZO":
                m = "C";
                break;
            case "APRILE":
                m = "D";
                break;
            case "MAGGIO":
                m = "E";
                break;
            case "GIUGNO":
                m = "H";
                break;
            case "LUGLIO":
                m = "L";
                break;
            case "AGOSTO":
                m = "M";
                break;
            case "SETTEMBRE":
                m = "P";
                break;
            case "OTTOBRE":
                m = "R";
                break;
            case "NOVEMBRE":
                m = "S";
                break;
            case "DICEMBRE":
                m = "T";
                break;

        }

        return m;



    }

    //il giorno viene aggiunto come valore se il soggetto è maschio mentre se e femmina il valore viene incrementato di 40
    public static String CalculateDay(Integer day, String gender){




        if(gender.equals("Sesso Femminile")){
            day = day + 40;
        }

        if(day > 0 && day < 10){
            d = "0" + String.valueOf(day);
        } else{
            d = String.valueOf(day);
        }






        return d;

    }





    //Leggiamo il csv file dedicato ai codici dei comuni di nascita in un array
    public static List<String[]> ReadCsv(Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(R.raw.comuni);
        String line = "";

        List<String[]> rows = new ArrayList<>();
        String [] row = new String[0];


        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null){

                row = line.split(",");
                rows.add(row);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return rows;

    }


    //Calcoliamo il Codice relativo al comune di nascita contenuto in un file csv
    public static String CalculateComune(Context c, String PlaceOfBirth) throws IOException {
        //Inizializziamo la Lista appena creata chiamando il metodo che ci darà le righe del file csv
        List<String[]> Csv = ReadCsv(c);
        //Stringa per tenere conto del risultato
        String results = "";



        //scansioniamo tutte le righe del file e se il comune di nascita selezionato risulta esserci prendiamo il relativo codice
        for (String[] row : Csv){
            if(row[1].equals(PlaceOfBirth)){
                results = row[5];

            }

        }



        return results;
    }

    //Calcolo Codice Fiscale senza carattere di controllo
    public static String CalculateCodiceFiscale_WitouthControlChar(String n, String s, Integer y, Integer d, String g, String m, Context c, String p) throws IOException {
        String CodiceWCC = "";

        String Name = CalculateName(n);
        String Surname = CalculateSurname(s);
        String Year = CalculateYear(y);
        String Month = CalculateMonth(m);
        String Day = CalculateDay(d, g);
        String Comune = CalculateComune(c,p);

        CodiceWCC = Surname + Name + Year + Month + Day + Comune;

        CodiceWCC.toUpperCase();




        return CodiceWCC;
    }

    //Calcolo Codice di controllo


    public static String CalculateControlChar(String c) {
        //inizializzazione tabella di conversione per posizioni pari
        char_to_even.put('0', 0); char_to_even.put('1', 1);
        char_to_even.put('2', 2); char_to_even.put('3', 3);
        char_to_even.put('4', 4); char_to_even.put('5', 5);
        char_to_even.put('6', 6); char_to_even.put('7', 7);
        char_to_even.put('8', 8); char_to_even.put('9', 9);
        char_to_even.put('A', 0); char_to_even.put('B', 1);
        char_to_even.put('C', 2); char_to_even.put('D', 3);
        char_to_even.put('E', 4); char_to_even.put('F', 5);
        char_to_even.put('G', 6); char_to_even.put('H', 7);
        char_to_even.put('I', 8); char_to_even.put('J', 9);
        char_to_even.put('K', 10); char_to_even.put('L', 11);
        char_to_even.put('M', 12); char_to_even.put('N', 13);
        char_to_even.put('O', 14); char_to_even.put('P', 15);
        char_to_even.put('Q', 16); char_to_even.put('R', 17);
        char_to_even.put('S', 18); char_to_even.put('T', 19);
        char_to_even.put('U', 20); char_to_even.put('V', 21);
        char_to_even.put('W', 22); char_to_even.put('X', 23);
        char_to_even.put('Y', 24); char_to_even.put('Z', 25);



        //inizializzazione tabella di conversione per posizioni dispari
        char_to_odd.put('0', 1); char_to_odd.put('1', 0);
        char_to_odd.put('2', 5); char_to_odd.put('3', 7);
        char_to_odd.put('4', 9); char_to_odd.put('5', 13);
        char_to_odd.put('6', 15); char_to_odd.put('7', 17);
        char_to_odd.put('8', 19); char_to_odd.put('9', 21);
        char_to_odd.put('A', 1); char_to_odd.put('B', 0);
        char_to_odd.put('C', 5); char_to_odd.put('D', 7);
        char_to_odd.put('E', 9); char_to_odd.put('F', 13);
        char_to_odd.put('G', 15); char_to_odd.put('H', 17);
        char_to_odd.put('I', 19); char_to_odd.put('J', 21);
        char_to_odd.put('K', 2); char_to_odd.put('L', 4);
        char_to_odd.put('M', 18); char_to_odd.put('N', 20);
        char_to_odd.put('O', 11); char_to_odd.put('P', 3);
        char_to_odd.put('Q', 6); char_to_odd.put('R', 8);
        char_to_odd.put('S', 12); char_to_odd.put('T', 14);
        char_to_odd.put('U', 16); char_to_odd.put('V', 10);
        char_to_odd.put('W', 22); char_to_odd.put('X', 25);
        char_to_odd.put('Y', 24); char_to_odd.put('Z', 23);

        //inizializzazione tabella per la conversione del resto
        number_to_char.put(0, 'A'); number_to_char.put(1, 'B');
        number_to_char.put(2, 'C'); number_to_char.put(3, 'D');
        number_to_char.put(4, 'E'); number_to_char.put(5, 'F');
        number_to_char.put(6, 'G'); number_to_char.put(7, 'H');
        number_to_char.put(8, 'I'); number_to_char.put(9, 'J');
        number_to_char.put(10, 'K'); number_to_char.put(11, 'L');
        number_to_char.put(12, 'M'); number_to_char.put(13, 'N');
        number_to_char.put(14, 'O'); number_to_char.put(15, 'P');
        number_to_char.put(16, 'Q'); number_to_char.put(17, 'R');
        number_to_char.put(18, 'S'); number_to_char.put(19, 'T');
        number_to_char.put(20, 'U'); number_to_char.put(21, 'V');
        number_to_char.put(22, 'W'); number_to_char.put(23, 'X');
        number_to_char.put(24, 'Y'); number_to_char.put(25, 'Z');

        int even_sum = 0;
        int odd_sum = 0;
        int Total = 0;
        int r = 0;

        //Prendiamo il codice fiscale incompleto e convertiamolo in valori numerici
        for (int i = 0; i < c.length(); i++) {
            char character = c.charAt(i);
            if (i % 2 == 0) {
                    odd_sum += char_to_odd.get(character);
            } else {
                    even_sum += char_to_even.get(character);
            }
        }





        //Facciamo la somma delle posizioni pari,dispari
        Total = even_sum + odd_sum;
        //Calcolo Resto
        r = Total % 26;

        //In base al resto determino il carattere con una tabella di conversione
        String ControlChar = "";
        if(number_to_char.containsKey(r)){
            ControlChar = String.valueOf(number_to_char.get(r));
        }





        return ControlChar;


    }


    public static String CalculateCodiceFiscale(String CFWCC, String ControlChar){
        String cf = "";


        cf = CFWCC + ControlChar;

        return cf;
    }






    //metodi getter
    public static String getName() {
        return name;
    }

    public static String getSurname() {
        return surname;
    }

    public static Integer getDay() {
        return day;
    }

    public static String getMonth() {
        return month;
    }

    public static Integer getYear() {
        return year;
    }


    public static String getGender() {
        return gender;
    }

    public static String getPlaceOfBirth() {
        return placeOfBirth;
    }


}
