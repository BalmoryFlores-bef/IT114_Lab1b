package com.example.it114labr;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

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
    }

    //For the purposes of test I named the files in my assests Int.txt & Int2.txt
    public void processPress(View view)
            throws IOException {
        TextView tv;
        EditText et;
        EditText eto;

        et = (EditText) findViewById(R.id.edit_infile);
        tv = (TextView) findViewById(R.id.text_main);
        eto = (EditText) findViewById(R.id.edit_outfile);


        String inFile = et.getText().toString();

        String ofName = eto.getText().toString();

        try {
            InputStream infile = getAssets().open(inFile);
            Scanner sc = new Scanner(infile);

            //Counting values and creating empty array based on those parameters
            int count = 0;
            while (sc.hasNextDouble()) {
                sc.nextDouble();
                count++;
            }

            double[] arr = new double[count];
            sc.close();
            infile.close();

            //Filling array
            InputStream is2 = getAssets().open(inFile);
            Scanner sc2 = new Scanner(is2);

            int i = 0;
            while (sc2.hasNextDouble()) {
                arr[i] = sc2.nextDouble();
                i++;
            }
            sc2.close();
            is2.close();

            //Scans input for outfile and creates new file with that name

            File outfile = new File(getExternalFilesDir(null), ofName);
            FileWriter fw = new FileWriter(outfile);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            floor_it(arr, count);
            for (int j = 0; j < count; j++){
                pw.println(String.format("%.2f", arr[j]));
            }
            pw.close();

            //Writing values in strings app from infile
            //+ Flooring the values with .00 decimal form

            String display = "Values in file:\n";

            for (int j = 0; j < count; j++){
                display += String.format("%.2f", arr[j]) + "\n";
            }
            tv.setText(display);

        } catch (IOException e) {
            tv.setText("Error: File not found in assets.");
        }
    }

    public void floor_it(double[] a, int num_vals) {
        for (int i = 0; i < num_vals; i++) {
            a[i] = Math.floor(a[i]);
        }
    }
}