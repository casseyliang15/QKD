package com.example.casseyliang.finalintegration4;

import android.Manifest;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;



import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GridLabelRenderer;
import android.graphics.Paint;

public class MainActivity extends AppCompatActivity {



    int totalrows = 121; //117
    int totalrowsc = 150;
    int totalcolumn=10002; //20002
    int totalcolumnc= 12802;
    int hammingweight[][] = new int[totalrows][256];
    int hammingweightc[][] = new int[totalrowsc][256];
    //AES_algo
    
    String[] value;
    double[][] amplitude_FR = new double[totalrows][totalcolumn];
    double[][] amplitude_LR = new double[totalrowsc][totalcolumnc];
    //readCSV

    double C[][] = new double[256][amplitude_FR[0].length];
    double maximums[] = new double[C.length];
    double Cc[][] = new double[256][amplitude_LR[0].length];
    double maximumsc[] = new double[Cc.length];
    //correlation and assigning graph

    int maxindex = 0;
    int tryindex = 0;
    int maxindexc = 0;
    int tryindexc = 0;
    //finding the key

    private LineGraphSeries series,series2, series3;
    //plotting graph

    int input;
    EditText keyinput;
    Button submitButton;
    TextView myresult;
    Button submitButton2;
    TextView myresult2;
    EditText keyinput2;
    //user interaction

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyinput = (EditText) findViewById(R.id.keyinput);
        submitButton = (Button) findViewById(R.id.submitButton);
        myresult = (TextView) findViewById(R.id.PrintResult);
        submitButton2 = (Button) findViewById(R.id.submitButton2);
        keyinput2 = (EditText) findViewById(R.id.keyinput2);
        myresult2 = (TextView) findViewById(R.id.PrintResult2);



        //--String data = "";
        //--StringBuffer sbuffer = new StringBuffer();

        final int array[][] = new int[totalrows][16];
        final int cipher[][] = new int[totalrowsc][16];
        final int mycolumn[] = new int[totalrows];
        final int mycolumnc[] = new int[totalrowsc];
        final int mykey[] = new int[256];
        final int addroundkey[][] = new int[totalrows][256];
        final int sbox_result[][] = new int[totalrows][256];
        final int addroundcipher[][]= new int[totalrowsc][256];
        final int addroundkeyc[][] = new int[totalrowsc][256];
        final int sbox_resultc[][] = new int[totalrowsc][256];
        final int ciphermapping[]= new int[totalrowsc];
        int k = 0;
        int z=0;


        final int[] sbox = {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76
                , 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0
                , 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15
                , 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75
                , 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84
                , 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf
                , 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8
                , 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2
                , 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73
                , 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb
                , 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79
                , 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08
                , 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a
                , 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e
                , 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf
                , 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16};



        final int[] invsbox= {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb
                , 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb
                , 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e
                , 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25
                , 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92
                , 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84
                , 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06
                , 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b
                , 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73
                , 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e
                , 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b
                , 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4
                , 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f
                , 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef
                , 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61
                , 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d};
        //AES algo

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions,1);
        try {
            InputStream is = new FileInputStream(new File("sdcard/DIP/LR_150_12802.csv"));
        }catch(IOException e){
            Log.e("printing","error");
        }
        //giving access permission to perform RW to phone or emulator



        //File sdcard = Environment.getExternalStorageDirectory();
        //File CSV_FR= new File(sdcard,"output_1311_2.csv"); //output.csv
        File CSV_FR= new File("sdcard/DIP/output_1311_2.csv");
        GraphView graph3 = findViewById(R.id.graph3);

        try {
            BufferedReader br = new BufferedReader(new FileReader(CSV_FR));
            String csvLine;
            int i = 0;
            int j = 0;
            while ((csvLine = br.readLine()) != null) {
                //Log.e("printing", "content "+csvLine);
                String value[] = csvLine.split(",");
                try {
                    if(i<totalrows) {
                        for (j = 0; j < totalcolumn; j++) {
                            amplitude_FR[i][j] = Double.parseDouble(value[j]);
                        }
                        i++;
                    }
                } catch (Exception e) {
                    Log.e("Problem", e.toString());
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.e("printing", amplitude_FR[0][0] + " " + amplitude_FR[0][1] + " " + amplitude_FR[1][0]);


        //File CSV_LR = new File( sdcard,"LR_150_12802.csv");
        File CSV_LR = new File( "sdcard/DIP/LR_150_12802.csv");
        try {
            BufferedReader br = new BufferedReader(new FileReader(CSV_LR));
            String csvLine;
            int i = 0;
            int j = 0;
            while ((csvLine = br.readLine()) != null) {
                //Log.e("printing", "content "+csvLine);
                String value[] = csvLine.split(",");
                series3 = new LineGraphSeries<>();
                try {
                    if(i<totalrowsc) {
                        for (j = 0; j < totalcolumnc; j++) {
                            amplitude_LR[i][j] = Double.parseDouble(value[j]);
                        }
/*
                        if(i<1) {
                            for (j = 0; j < totalcolumnc; j++) {
                                series3.appendData(new DataPoint(j, amplitude_LR[i][j]), true, totalcolumnc);
                                // Log.e("printing", "content " + amplitude_FR[i][j]);
                               // Log.e("appending", "content " + amplitude_LR[i][j]);
                            }
                            series3.setColor(Color.parseColor("#A9A9A9"));
                            graph3.getViewport().setMinX(0);
                            graph3.getViewport().setMaxX(totalcolumnc);
                            graph3.getViewport().setXAxisBoundsManual(true);
                            graph3.addSeries(series3);
                        }
*/

                        i++;
                    }
                } catch (Exception e) {
                    Log.e("Problem", e.toString());
                }
            }
            br.close();
            Log.e("my cipher amplitude is", "printing " + amplitude_LR[1][0] + " " + amplitude_LR[1][1] + " " + amplitude_LR[1][2]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //readCSV


        //AES algo
        //File plain_FR= new File(sdcard,"plain_1311.txt"); //plain2.txt
        File plain_FR= new File("sdcard/DIP/plain_1311.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(plain_FR));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (k < totalrows) {
                    for (int l = 0; l < 16; l++) {
                        array[k][l] = Integer.parseInt(parts[l], 16);
                        //Log.e("printing","content: "+line);
                    }
                    k++;
                }
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Log.e("printing", array[0][0] + " " + array[0][1] + " " + array[8][1]);
        //reading plaintext


/*
        File cipher_LR= new File(sdcard,"input_A.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(cipher_LR));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("   ");
                if (z < totalrows) {
                    for (int l = 0; l < 16; l++) {
                        cipher[z][l] = Integer.parseInt(parts[l].trim(), 10);
                        //Log.e("printing cipher","content: "+line);
                    }
                    z++;
                }
            }
            br.close();
            Log.e("my cipher is", "printing " + cipher[0][0] + " " + cipher[0][1] + " " + cipher[0][2]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //File file3= new File(sdcard,"CipherText-3.txt");
        File file3= new File("sdcard/DIP/CipherText-3.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file3));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (z < totalrowsc) {
                    for (int l = 0; l < 16; l++) {
                        cipher[z][l] = Integer.parseInt(parts[l], 16);
                        //Log.e("printing cipher","content: "+line);
                    }
                    z++;
                }
            }
            br.close();
            //Log.e("my cipher is", "printing " + cipher[0][0] + " " + cipher[0][1] + " " + cipher[0][2]);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //reading ciphertext

        //up to here reading data


        submitButton.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                input = Integer.valueOf(keyinput.getText().toString());
                                                //Log.e("receiving input", "is " + input);

                                                GraphView graph = findViewById(R.id.graph);
                                                graph.removeSeries(series);
                                                //assigning the value to a column. making ip data


                                                if (input >= 0 && input <= 15) {
                                                    for (int m = 0; m < totalrows; m++) {
                                                        mycolumn[m] = array[m][input];
                                                    }
                                                    //Log.e("my column is", "printing " + mycolumn[0] + " " + mycolumn[1] + " " + mycolumn[2]);


                                                    //addroundkey operation
                                                    for (int n = 0; n < 256; n++) {
                                                        mykey[n] = n;
                                                        for (int o = 0; o < totalrows; o++) {
                                                            addroundkey[o][n] = mycolumn[o] ^ mykey[n];
                                                        }
                                                    }
                                                    //Log.e("algo", "done calculating");


                                                    //S-box operation
                                                    for (int p = 0; p < 256; p++) {
                                                        for (int q = 0; q < totalrows; q++) {
                                                            sbox_result[q][p] = sbox[addroundkey[q][p]];
                                                        }
                                                    }

                                                    //Hamming Weight operation
                                                    for (int r = 0; r < 256; r++) {
                                                        for (int s = 0; s < totalrows; s++) {
                                                            hammingweight[s][r] = Integer.bitCount(sbox_result[s][r]);
                                                            //Log.e("hamming weight", "index "+s+","+r+" is "+hammingweight[s][r]);
                                                        }
                                                    }
                                                    //AES algo

                                                    //correlation coefficient
                                                    int n = hammingweight.length;
                                                    for (int t = 0; t < hammingweight[0].length; t++) {
                                                        for (int u = 0; u < amplitude_FR[0].length; u++) {
                                                            double sx = 0.0;
                                                            double sy = 0.0;
                                                            double sxx = 0.0;
                                                            double syy = 0.0;
                                                            double sxy = 0.0;

                                                            for (int i = 0; i < hammingweight.length; i++) {
                                                                double x = hammingweight[i][t];
                                                                double y = amplitude_FR[i][u];

                                                                sx += x;
                                                                sy += y;
                                                                sxx += x * x;
                                                                syy += y * y;
                                                                sxy += x * y;
                                                            }

                                                            // covariation
                                                            double cov = sxy / n - sx * sy / n / n;
                                                            // standard error of x
                                                            double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
                                                            // standard error of y
                                                            double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

                                                            // correlation is just a normalized covariation
                                                            C[t][u] = cov / sigmax / sigmay;
                                                            //Log.e("printing","correlation "+C[t][u]);
                                                        }
                                                    }

                                                    //correlation coefficient
                                                    double max = 0.0;
                                                    for (int i = 0; i < C.length; i++) {
                                                        max = C[i][0];
                                                        for (int j = 1; j < C[0].length; j++) {
                                                            if (max < Math.abs(C[i][j]))
                                                                max = Math.abs(C[i][j]);
                                                        }
                                                        maximums[i] = max;
                                                        // Log.e("printing","maximum for key "+i+" is "+maximums[i]);
                                                    }


                                                    for (tryindex = 0; tryindex < maximums.length; tryindex++) {
                                                        if (maximums[tryindex] > max) {
                                                            max = maximums[tryindex];
                                                            maxindex = tryindex;
                                                        }
                                                    }
                                                    //Log.e("my key is ", "printing " + maxindex);
                                                    //finding the key

                                                    //GraphView graph = findViewById(R.id.graph);
                                                    series = new LineGraphSeries<>();
                                                    for (int i = 0; i < C.length; i++) {
                                                        series.appendData(new DataPoint(i, maximums[i]), true, 256);
                                                        series.setColor(Color.parseColor("#000001"));
                                                    }

                                                    graph.addSeries(series);
                                                    graph.getViewport().setMinX(0);
                                                    graph.getViewport().setMaxX(255);
                                                    graph.getViewport().setXAxisBoundsManual(true);
                                                    graph.getViewport().setMinY(0);
                                                    graph.getViewport().setMaxY(1);
                                                    graph.getViewport().setYAxisBoundsManual(true);
                                                    graph.setTitle("First Round DPA with Hamming Weight Power Model");
                                                    //graph.getGridLabelRenderer().setVerticalAxisTitle("Maximum Correlation");
                                                    //graph.getGridLabelRenderer().setHorizontalAxisTitle("Key");
                                                    //graph.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
                                                   // graph.getGridLabelRenderer().setVerticalLabelsSecondScaleAlign(Paint.Align.LEFT);
                                                   // graph.getGridLabelRenderer().setLabelsSpace(25);
                                                    //graph.getGridLabelRenderer().setVerticalLabelsVAlign(GridLabelRenderer.VerticalLabelsVAlign.MID);

                                                    myresult.setText("The predicted key number " + input + " is: " + maxindex);

                                                } else {
                                                    myresult.setText("invalid input. Please enter integer in range of 0 to 15.");
                                                }
/*
                                                    for(int w=0;w<totalrows;w++)
                                                    {
                                                        //Log.e("Hamming weight","index "+w+" is "+hammingweight[0][w]);
                                                        //Log.e("addround","index "+w+" is "+addroundkey[0][w]);
                                                        //Log.e("s box","index "+w+" is "+sbox_result[0][w]);
                                                        Log.e("CSV","index "+w+" is "+amplitude_FR[w][0]);
                                                    }
*/
                                                //Log.e("printing: ","plain text 14,4 and 50,6 is "+array[14][4]+" "+array[50][6]);
                                                //Log.e("printing: ","csv 14,4 and 50,6 is "+amplitude_FR[14][4]+" "+amplitude_FR[50][6]);
                                            }//close void
                                        }//close onclick submit
        ); //close the onClick for submit button





        //graph 2 : last round
        submitButton2.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 GraphView graph2 = findViewById(R.id.graph2);
                                                 graph2.removeSeries(series2);
                                                 int outputmapping=0;
                                                 //assigning the value to a column. making ip data


                                                 try {
                                                     input = Integer.parseInt(keyinput2.getText().toString());

                                                     if (input >= 0 && input <= 15)
                                                     {
                                                         for (int m = 0; m < totalrowsc; m++) {
                                                             mycolumnc[m] = cipher[m][input];

                                                         }
                                                         Log.e("my column is", "printing " + mycolumnc[0] + " " + mycolumnc[1] + " " + mycolumnc[2]);

                                                         switch(input){
                                                             case 0: outputmapping=0;break;
                                                             case 1: outputmapping=13;break;
                                                             case 2: outputmapping=10;break;
                                                             case 3: outputmapping=7;break;
                                                             case 4: outputmapping=4;break;
                                                             case 5: outputmapping=1;break;
                                                             case 6: outputmapping=14;break;
                                                             case 7: outputmapping=11;break;
                                                             case 8: outputmapping=8;break;
                                                             case 9: outputmapping=5;break;
                                                             case 10: outputmapping=2;break;
                                                             case 11: outputmapping=15;break;
                                                             case 12: outputmapping=12;break;
                                                             case 13: outputmapping=9;break;
                                                             case 14: outputmapping=6;break;
                                                             case 15: outputmapping=3;break;
                                                         }
                                                         //output mapping for inv shift rows
                                                         Log.e("printing", "my input and outputmapping is " + input+" and "+ outputmapping);

                                                         for (int m = 0; m < totalrowsc; m++) {
                                                             ciphermapping[m] = cipher[m][outputmapping];
                                                         }
                                                         Log.e("my ciphermapping is", "printing " + ciphermapping[0] + " " + ciphermapping[1] + " " +ciphermapping[2]);
                                                         //take outputmapping array


                                                         //addroundkey operation
                                                         for (int n = 0; n < 256; n++) {
                                                             mykey[n] = n;
                                                             for (int o = 0; o < totalrowsc; o++) {
                                                                 addroundkeyc[o][n] = mycolumnc[o] ^ mykey[n];
                                                             }
                                                         }
                                                         Log.e("addroundkey", "printing " + addroundkeyc[0][0] + " " + addroundkeyc[0][1] + " " +addroundkeyc[0][2]);
                                                         //Log.e("algo", "done calculating");


                                                         //inv S-box operation
                                                         for (int p = 0; p < 256; p++) {
                                                             for (int q = 0; q < totalrowsc; q++) {
                                                                 sbox_resultc[q][p] = invsbox[addroundkeyc[q][p]];
                                                             }
                                                         }
                                                         Log.e("invsbox", "printing " + sbox_resultc[0][0] + " " + sbox_resultc[0][1] + " " +sbox_resultc[0][2]);


                                                         //addround ciphertext for hamming distance
                                                         for(int p=0; p<256;p++){
                                                             for(int q=0;q<totalrowsc;q++)
                                                             {
                                                                 addroundcipher[q][p]= sbox_resultc[q][p]^ciphermapping[q];
                                                             }
                                                         }
                                                         Log.e("hamming distance", "printing " + addroundcipher[0][0] + " " +addroundcipher[0][1] + " " +addroundcipher[0][2]);

                                                         //Hamming Weight operation-->now become hamming distance after addround ciphertext
                                                         for (int r = 0; r < 256; r++) {
                                                             for (int s = 0; s < totalrowsc; s++) {
                                                                 hammingweightc[s][r] = Integer.bitCount(addroundcipher[s][r]);
                                                                 //Log.e("hamming weight", "index "+s+","+r+" is "+hammingweight[s][r]);
                                                             }
                                                         }
                                                         Log.e("hamming weight", "printing " + hammingweightc[0][0] + " " + hammingweightc[0][1] + " " +hammingweightc[0][2]);
                                                         //AES algo

                                                         //correlation coefficient
                                                         int n = hammingweightc.length;
                                                         for (int t = 0; t < hammingweightc[0].length; t++) {
                                                             for (int u = 0; u < amplitude_LR[0].length; u++) {
                                                                 double sx = 0.0;
                                                                 double sy = 0.0;
                                                                 double sxx = 0.0;
                                                                 double syy = 0.0;
                                                                 double sxy = 0.0;

                                                                 for (int i = 0; i < hammingweightc.length; i++) {
                                                                     double x = hammingweightc[i][t];
                                                                     double y = amplitude_LR[i][u];

                                                                     sx += x;
                                                                     sy += y;
                                                                     sxx += x * x;
                                                                     syy += y * y;
                                                                     sxy += x * y;
                                                                 }

                                                                 // covariation
                                                                 double cov = sxy / n - sx * sy / n / n;
                                                                 // standard error of x
                                                                 double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
                                                                 // standard error of y
                                                                 double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

                                                                 // correlation is just a normalized covariation
                                                                 Cc[t][u] = cov / sigmax / sigmay;
                                                                 //Log.e("printing","correlation "+C[t][u]);
                                                             }
                                                         }

                                                         //correlation coefficient
                                                         double maxc = 0.0;
                                                         for (int i = 0; i < Cc.length; i++) {
                                                             maxc = Cc[i][0];
                                                             for (int j = 1; j < Cc[0].length; j++) {
                                                                 if (maxc < Math.abs(Cc[i][j]))
                                                                     maxc = Math.abs(Cc[i][j]);
                                                             }
                                                             maximumsc[i] = maxc;
                                                             // Log.e("printing","maximum for key "+i+" is "+maximums[i]);
                                                         }


                                                         for(tryindexc=0; tryindexc<maximumsc.length;tryindexc++ ){
                                                             if(maximumsc[tryindexc]>maxc)
                                                             {
                                                                 maxc=maximumsc[tryindexc];
                                                                 maxindexc=tryindexc;
                                                             }
                                                         }
                                                         Log.e("my key is ","printing "+maxindexc);
                                                         //finding the key

                                                         //GraphView graph = findViewById(R.id.graph);
                                                         series2 = new LineGraphSeries<>();
                                                         for (int i = 0; i < Cc.length; i++) {
                                                             series2.appendData(new DataPoint(i, maximumsc[i]), true, 256);
                                                             series2.setColor(Color.parseColor("#000001"));
                                                         }

                                                         graph2.addSeries(series2);
                                                         graph2.getViewport().setMinX(0);
                                                         graph2.getViewport().setMaxX(255);
                                                         graph2.getViewport().setXAxisBoundsManual(true);
                                                         graph2.getViewport().setMinY(0);
                                                         graph2.getViewport().setMaxY(1);
                                                         graph2.getViewport().setYAxisBoundsManual(true);
                                                         myresult2.setText("Key "+input+" is broken successfully: "+maxindexc);

                                                     }else{
                                                         myresult2.setText("invalid input. Please enter integer in range of 0 to 15.");
                                                     }
                                                 }catch(Exception e){
                                                     //myresult2.setText("catching exception");
                                                     // friendly error to the user: field is incorrect
                                                     e.printStackTrace();
                                                 }

                                             }//close void
                                         }//close onclick submit

        ); //close the onClick for submit button

    }
}
