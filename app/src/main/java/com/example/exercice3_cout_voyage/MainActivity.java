package com.example.exercice3_cout_voyage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static  String gouvernorats []={"bizerte","Sousse","Tunis"};
    private  static String Essence []={"super sans plomb","gasoil super","gasoil"};

    private static  Double Distance [][]={
            {0.0,215.0,71.0},
            {215.0,0.0,150.0},
            {71.0,150.0,0.0}
    };
    private String Source="sousse";
    private String Destination="Bizerte";
    private String Essen="Gasoil";
    //liste des variables dans XML
    EditText txtConsoLittrekm;
    Spinner spnSource;
    Spinner spnDestination;
    TextView txtDistance;
    Spinner spnTypeEssence;
    TextView txtPrixLittre;
    RadioButton rdbAutoroute;
    RadioButton rdbRouteNational;
    CheckBox chKCafe;
    CheckBox chkPizza;
    CheckBox chkEau;
    CheckBox chkjus;
    Button btnCalculer;
    TextView txtmessageResultat;

    private void init(){
        txtConsoLittrekm = findViewById(R.id.txtConsoLittrekm);
        spnSource = findViewById(R.id.spnSource);
        spnDestination = findViewById(R.id.spnDestination);
        txtDistance = findViewById(R.id.txtDistance);
        spnTypeEssence = findViewById(R.id.spnTypeEssence);
        rdbAutoroute =findViewById(R.id.rdbAutoroute);
        rdbRouteNational = findViewById(R.id.rdbRouteNational);
        chKCafe =findViewById(R.id.chKCafe);
        chkPizza = findViewById(R.id.chkPizza);
        chkEau = findViewById( R.id.chkEau);
        chkjus = findViewById(R.id.chkjus);
        btnCalculer = findViewById(R.id.btnCalculer);
        txtmessageResultat = findViewById(R.id.txtmessageResultat);
        txtPrixLittre = findViewById(R.id.txtPrixLittre);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        ArrayAdapter<String> adaptaterGouvernorats = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,gouvernorats);
        spnSource.setAdapter(adaptaterGouvernorats);
        spnDestination.setAdapter(adaptaterGouvernorats);
        ArrayAdapter<String> adaptaterEssence = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Essence);
        spnTypeEssence.setAdapter(adaptaterEssence);

        spnSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Double dis = 0.0;
                Source = spnSource.getSelectedItem().toString();
                dis = selectDistance(Source,Destination);
                txtDistance.setText(dis.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        }
        );

        spnDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Double dis = 0.0;
                Destination = spnDestination.getSelectedItem().toString();
                dis = selectDistance(Source,Destination);
                txtDistance.setText(dis.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnTypeEssence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Double pri = 0.0;
                Essen = spnTypeEssence.getSelectedItem().toString();
                pri = getPrixEssence(Essen);
                txtPrixLittre.setText(pri.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCalculer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String v1 = txtConsoLittrekm.getText().toString().trim();
                float consVehicule = 0;
                if (v1.isEmpty() || v1.length() == 0 || v1.equalsIgnoreCase("") || txtConsoLittrekm == null) {
                    consVehicule = 0;
                }
                else {
                    consVehicule = Float.parseFloat(txtConsoLittrekm.getText().toString().trim());
                }
                float nbrekm = Float.parseFloat(txtDistance.getText().toString().trim());
                float prixcb = Float.parseFloat(txtPrixLittre.getText().toString().trim());
                float apprv = 0;
// par défaut : route nationale
                float peage = 0;
                if (nbrekm > 0.0) {
                    if (chkEau.isChecked()) {
                        apprv += 1;
                    }
                    if (chKCafe.isChecked()) {
                        apprv += 1.5;
                    }
                    if (chkPizza.isChecked()) {
                        apprv += 6;
                    }

                    if (chkjus.isChecked()) {
                        apprv += 2.5;
                    }
                    if (rdbAutoroute.isChecked()) {
                        peage += 3.450;
                    }
                }
                float consoTotale = (float) ((consVehicule / 100)* nbrekm * prixcb);
                float facture = consoTotale + apprv + peage;
                txtmessageResultat.setText(facture+" dtn.");
            }
        });
    }


    public double selectDistance(String source, String destination) {
        int idxSource, idxDestination;
        idxSource = returnIndex(source);
        idxDestination = returnIndex(destination);
        return Distance[idxSource][idxDestination];
    }
    private int returnIndex(String str) {
        int index = -1;
        for (int i = 0; i < gouvernorats.length; i++) {
            if (gouvernorats[i].equalsIgnoreCase(str)) {
                index = i;
                break;
            }
        }
        return index;
    }
    public Double getPrixEssence(String carBType) {
        double prix;
        switch (carBType.toLowerCase()) {
            case "super sans plomb":
                prix = 2.065;
                break;
            case "gasoil super":
                prix = 1.825;
                break;
            case "gasoil":
                prix = 1.560;
                break;
            default:
                prix = 0.0;
                break;
        }
        return prix;
    }
}


