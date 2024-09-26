package com.example.codicefiscale;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RadioButton selectedRadioButton;



    //Dichiarazione risposta radiobutton
    private String gender_ans;

    private String name;
    private String surname;
    private String placeOfBirth;


    //Dichiarazione stringhe elementi spinner
    private Integer day_item;
    private String month_item;
    private Integer year_item;

    //Dichiarazione oggetti EditText
    private EditText name_edit;
    private EditText surname_edit;



    private AutoCompleteTextView placeOfBirth_AutoComp;



    //Dichiarazione oggetto RadioGroup
    private RadioGroup radioGroup;



    //Dichiarazione oggetti RadioButton
    private RadioButton male_rb;
    private RadioButton female_rb;


    //Dichiarazione oggetto button
    private Button submit;


    //Dichiarazione oggetti spinner
    private Spinner day_spinner;
    private Spinner month_spinner;
    private Spinner year_spinner;



    //Creiamo l'arraylist che conterrà gli elementi dello day_spinner
    private ArrayList<String> day = new ArrayList<>();

    //Creiamo l'arraylist che conterrà gli elementi dello month_spinner
    private ArrayList<String> month = new ArrayList<>();









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Inizializzazione oggetti EditText
        name_edit = findViewById(R.id.name_editText);
        surname_edit = findViewById(R.id.surname_editText);


        placeOfBirth_AutoComp = findViewById(R.id.place_of_birth_AutoCompTextView);



        //Inzializzazione oggetti spinner
        day_spinner = findViewById(R.id.day_spinner);
        month_spinner = findViewById(R.id.month_spinner);
        year_spinner = findViewById(R.id.year_spinner);

        //Inizializzazione oggetti RadioButton
        male_rb = findViewById(R.id.gender_male_radioButton);
        female_rb = findViewById(R.id.gender_female_radioButton);

        //Inizializzazione oggetto button
        submit = findViewById(R.id.submit_btn);

        //inizializzazione radioGroup
        radioGroup = findViewById(R.id.radioGroup);



        //Impostiamo l'AutoComplete
        try {
            List<String[]> suggestionsArray = CodiceFiscale.ReadCsv(MainActivity.this);
            Set<String> suggestions_set = new HashSet<>();


            for (String[] row: suggestionsArray){
                suggestions_set.add(row[1]);
            }

            List<String> suggestions = new ArrayList<>(suggestions_set);

            ArrayAdapter<String> ad =  new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,suggestions);
            placeOfBirth_AutoComp.setAdapter(ad);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        //TextWatcher
        name_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                //Quando l'utente inizia a digitare, rimuovi l'hint

                if(!s.toString().isEmpty()){
                    //rimuovo l'hint quando c'è testo
                    name_edit.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //se il campo è vuoto ripristino l'hint
                if(s.toString().isEmpty()){
                    name_edit.setHint("Inserisci il tuo nome");
                }

            }
        });

        //TextWatcher
        surname_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                //Quando l'utente inizia a digitare, rimuovi l'hint

                if(!s.toString().isEmpty()){
                    //rimuovo l'hint quando c'è testo
                    surname_edit.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //se il campo è vuoto ripristino l'hint
                if(s.toString().isEmpty()){
                    surname_edit.setHint("Inserisci il tuo cognome");
                }

            }
        });


        //TextWatcher
        placeOfBirth_AutoComp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                //Quando l'utente inizia a digitare, rimuovi l'hint

                if(!s.toString().isEmpty()){
                    //rimuovo l'hint quando c'è testo
                    placeOfBirth_AutoComp.setHint("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //se il campo è vuoto ripristino l'hint
                if(s.toString().isEmpty()){
                    placeOfBirth_AutoComp.setHint("Inserisci la tua data di nascita");
                }

            }
        });







        //Listener per il button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //se non tutte le opzioni vengono compilate stampiamo un messaggio


                //finire i radio button e il OnClick se non sono compilati tutti i campi




                //Ricavo il testo degli edit text
                name = name_edit.getText().toString().trim().replace(" ","");
                surname = surname_edit.getText().toString().trim().replace(" ","");;
                placeOfBirth = placeOfBirth_AutoComp.getText().toString().trim().replace(" ","");;


                //Ricaviamo l'id del radio selezionato
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // se è stato selezionato immagazzina la risposta nella variabile
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    gender_ans = selectedRadioButton.getText().toString();
                }






                // Se le opzioni non vengono compilate, stampiamo un messaggio
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(placeOfBirth) &&
                        !(day_item == null) && day_item.equals("Seleziona un giorno") ||
                        !(month_item == null) && month_item.equals("Seleziona un mese") ||
                        !(year_item == null) && year_item.equals("Seleziona un anno") && (male_rb!=null || female_rb!= null)) {

                    // Messaggio di errore
                    Toast.makeText(MainActivity.this, "Inserire tutti i campi", Toast.LENGTH_LONG).show();
                } else {


                    //reset campi
                    name_edit.setText("");
                    surname_edit.setText("");
                    placeOfBirth_AutoComp.setText("");
                    day_spinner.setSelection(0);
                    month_spinner.setSelection(0);
                    year_spinner.setSelection(0);
                    male_rb = null;
                    female_rb= null;
                    radioGroup.clearCheck();



                    //Creazione di un istanza per il codice fiscale
                    CodiceFiscale cf = new CodiceFiscale(name.toUpperCase(),surname.toUpperCase(),placeOfBirth.toUpperCase(),gender_ans,day_item,month_item.toUpperCase(),year_item,day,MainActivity.this);



                    //Adesso apriamo la prossima activity
                    Intent intent = new Intent(MainActivity.this,Second_activity.class);
                    startActivity(intent);






                }








            }
        });











        //Listener per il day_spinner con i metodi
        day_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Creiamo una stringa che conterrà il valore dell'elemento selezionato
                if(position>0) {
                    String day__item = adapterView.getItemAtPosition(position).toString();
                    day_item = Integer.parseInt(day__item);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        day.add("Seleziona un giorno");

        //Stampa tutti i giorni in un mese con un determinato formato
        for(int i=1;i<=31;i++){
            for(int j=1;j<=9;j++){
                if(i == j){
                    day.add("0" + String.valueOf(j));
                }



            }

            if(i>10) {
                day.add(String.valueOf(i));
            }



        }






        //Adapter per visualizzare i dati nello spinner
        ArrayAdapter<String> day_ad =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,day);

        //settiamo il menu a tendina
        day_ad.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        day_spinner.setAdapter(day_ad);







        //Listener per il month_spinner con i metodi
        month_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Creiamo una stringa che conterrà il valore dell'elemento selezionato
                if(position>0) {
                    month_item = adapterView.getItemAtPosition(position).toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        month.add("Seleziona un mese");
        month.add("Gennaio");
        month.add("Febbraio");
        month.add("Marzo");
        month.add("Aprile");
        month.add("Maggio");
        month.add("Giugno");
        month.add("Luglio");
        month.add("Agosto");
        month.add("Settembre");
        month.add("Ottobre");
        month.add("Novembre");
        month.add("Dicembre");

        //Adapter per visualizzare i dati nello spinner
        ArrayAdapter<String> month_ad =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,month);

        //settiamo il menu a tendina
        month_ad.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        month_spinner.setAdapter(month_ad);



        //Listener per il year_spinner con i metodi
        year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //Creiamo una stringa che conterrà il valore dell'elemento selezionato
                if(position>0) {
                    String year__item = adapterView.getItemAtPosition(position).toString();
                    year_item = Integer.parseInt(year__item);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Creiamo l'arraylist che conterrà gli elementi dello year_spinner
        ArrayList<String> year = new ArrayList<>();

        year.add("Seleziona un anno");

        //generiamo gli elementi
        for(int i=1900;i<=2024;i++){
            year.add(String.valueOf(i));
        }

        //Adapter per visualizzare i dati nello spinner
        ArrayAdapter<String> year_ad =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,year);

        //settiamo il menu a tendina
        year_ad.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        year_spinner.setAdapter(year_ad);







    }



}