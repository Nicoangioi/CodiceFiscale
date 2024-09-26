package com.example.codicefiscale;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Second_activity extends AppCompatActivity {




    //Otteniamo i dati del codice fiscale

    private String name = CodiceFiscale.getName();
    private String surname = CodiceFiscale.getSurname();
    private String day = String.valueOf(CodiceFiscale.getDay());
    private String month = CodiceFiscale.getMonth();
    private Integer year = CodiceFiscale.getYear();
    private String gender = CodiceFiscale.getGender();
    private String placeOfBirth = CodiceFiscale.getPlaceOfBirth();
    private String CF = CodiceFiscale.getCF();

    public Second_activity() throws IOException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.secondactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        TextView cf = findViewById(R.id.CF_textView);
        TextView Name = findViewById(R.id.Name_textview);
        TextView Surname = findViewById(R.id.Surname_textview);
        TextView DateOfBirth = findViewById(R.id.DateOfBirth_textview);
        TextView PlaceOfBirth = findViewById(R.id.PlaceOfBirth_textview);
        TextView Gender = findViewById(R.id.gender_textview);

        Button home = findViewById(R.id.Home_btn);
        Button copy = findViewById(R.id.Copy_btn);






        //Concateniamo tutte le stringhe per formare la data di nascita
        String Date_of_birth = day + " " + month +  " " + year;



        //settiamo il testo delle view di questa activity con i dati calcolati e ottenuti precedentemente
        cf.setText(CF);
        Name.setText(name);
        Surname.setText(surname);
        DateOfBirth.setText(Date_of_birth);
        PlaceOfBirth.setText(placeOfBirth);
        Gender.setText(gender.toUpperCase());


        //Settiamo il bottone per tornare indetro (home)
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Second_activity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        //Settiamo il bottone per copiare il codice fiscale negli appunti
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Copiamo negli appunti
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText("text",cf.getText().toString());
                cm.setPrimaryClip(cd);

                //Stampiamo un messaggio per dire che il cf è stato copiato
                Toast.makeText(Second_activity.this, "Copiato negli appunti", Toast.LENGTH_SHORT).show();
            }
        });
















    }
}