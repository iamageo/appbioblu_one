package com.bioblu.second_law;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bioblu.R;
import com.bioblu.controllers.OuvinteDeToque;
import com.bioblu.second_law.activity_cruzamento_2law;
import com.bioblu.second_law.activity_menu_2law;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static com.bioblu.main.activity_voiceRate.FILE_NAME;
public class activity_selectLetra2law extends AppCompatActivity {
    public int screenWidth;
    int y;
    private TextToSpeech textToSpeech; /** Sintetiza a fala do texto para reprodução imediata ou para criar um arquivo de som **/
    private int yx = 0,zx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 0, l5 = 1, l6 = 2;
    private int i = -1, j = -1;
    private int ix = -1, jx = -1;
    private TextView lista1, lista2, lista3, lista4, lista5, lista6;
    private String[] opcaoE = {"Homozigoto Dominante A", "Heterozigoto Dominante A", "Homozigoto Recessivo A"};
    private String[] opcaoD = {"Homozigoto Dominante B", "Heterozigoto Dominante B", "Homozigoto Recessivo B"};
    TextView[] cursorD = new TextView[3];
    TextView[] cursorE = new TextView[3];
    private String[] result = new String[3];
    public int velocidade;
    private String lado,ha1,ha2,he1,he2;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_letra2law);
        /** Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque **/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        y = 0;

        lista1 = findViewById(R.id.select_letra_1);
        lista2 = findViewById(R.id.select_letra_2);
        lista3 = findViewById(R.id.select_letra_3);
        lista4 = findViewById(R.id.select_letra_4);
        lista5 = findViewById(R.id.select_letra_5);
        lista6 = findViewById(R.id.select_letra_6);


        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_selectLetra2law.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ler_velocidade();
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak("Você está na tela de seleção de genes da segunda lei"+'\n'+" para navegar entre eles basta deslizar para cima ou para baixo"+'\n'+
                            "você terá três opções em cada lado da tela, deslize em um lado de cada vez"+ '\n' + "após chegar na opção desejada dê dois toques para selecionar" + '\n'
                            +"para retornar ao menu, faça um L ao contrario em qualquer parte da tela", TextToSpeech.QUEUE_ADD, null);
                }
            }
        });


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()){
                    textToSpeech.speak("", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2* 1000* 1000);

        cursorE[0] = lista1;
        cursorE[1] = lista2;
        cursorE[2] = lista3;

        cursorD[0] = lista4;
        cursorD[1] = lista5;
        cursorD[2] = lista6;

        initTela();

    }

    /** METODOS AQUI **/

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Rlayout = findViewById(R.id.relativeLayoutconceitoletra2law);
        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth, y) {


            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu_2law.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                Intent intent = new Intent(getApplicationContext(), activity_cruzamento_2law.class);
                if (lado == "E") {
                    if (i >= 0) {
                        y++;
                        if (opcaoE[i] == "Homozigoto Dominante A") {
                            if (y < 2){
                                ha1 = "A";
                                ha2 = "A";
                                textToSpeech.speak("Selecionado Homozigoto Dominante A", textToSpeech.QUEUE_FLUSH, null);
                            }else {
                                textToSpeech.speak("Selecionado Homozigoto Dominante A", textToSpeech.QUEUE_FLUSH, null);
                                he1 = "A";
                                he2 = "A";
                                intent.putExtra("resultado1", ha1);
                                intent.putExtra("resultado2", ha2);
                                intent.putExtra("resultado3", he1);
                                intent.putExtra("resultado4", he2);
                                startActivity(intent);
                                finish();

                            }

                        }
                        else if(opcaoE[i] == "Heterozigoto Dominante A") {
                            if (y < 2){

                                ha1 = "A";
                                ha2 = "a";
                                textToSpeech.speak("Selecionado Heterozigoto Dominante A", textToSpeech.QUEUE_FLUSH, null);

                            }else {
                                he1 = "A";
                                he2 = "a";
                                textToSpeech.speak("Selecionado Heterozigoto Dominante A", textToSpeech.QUEUE_FLUSH, null);

                                intent.putExtra("resultado1", ha1);
                                intent.putExtra("resultado2", ha2);
                                intent.putExtra("resultado3", he1);
                                intent.putExtra("resultado4", he2);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else if(opcaoE[i] == "Homozigoto Recessivo A"){
                            if (y < 2){

                                ha1 = "a";
                                ha2 = "a";
                                textToSpeech.speak("Selecionado Homozigoto Recessivo A", textToSpeech.QUEUE_FLUSH, null);

                            }else {
                                he1 = "a";
                                he2 = "a";
                                textToSpeech.speak("Selecionado Homozigoto Recessivo A", textToSpeech.QUEUE_FLUSH, null);

                                intent.putExtra("resultado1", ha1);
                                intent.putExtra("resultado2", ha2);
                                intent.putExtra("resultado3", he1);
                                intent.putExtra("resultado4", he2);
                                startActivity(intent);
                                finish();

                            }
                        }

                    }
                } else if (lado == "D") {
                    if (j >= 0) {
                        y++;
                        if (opcaoD[j] == "Homozigoto Dominante B") {
                            if (y < 2){

                                ha1 = "B";
                                ha2 = "B";
                                textToSpeech.speak("Selecionado Homozigoto Dominante B", textToSpeech.QUEUE_FLUSH, null);

                            }else {
                                he1 = "B";
                                he2 = "B";
                                textToSpeech.speak("Selecionado Homozigoto Dominante B", textToSpeech.QUEUE_FLUSH, null);

                                intent.putExtra("resultado1", ha1);
                                intent.putExtra("resultado2", ha2);
                                intent.putExtra("resultado3", he1);
                                intent.putExtra("resultado4", he2);
                                startActivity(intent);
                                finish();

                            }
                        }
                        else if (opcaoD[j] == "Heterozigoto Dominante B") {
                            if (y < 2){

                                ha1 = "B";
                                ha2 = "b";
                                textToSpeech.speak("Selecionado Heterozigoto Dominante B", textToSpeech.QUEUE_FLUSH, null);

                            }else {
                                he1 = "B";
                                he2 = "b";
                                textToSpeech.speak("Selecionado Heterozigoto Dominante B", textToSpeech.QUEUE_FLUSH, null);

                                intent.putExtra("resultado1", ha1);
                                intent.putExtra("resultado2", ha2);
                                intent.putExtra("resultado3", he1);
                                intent.putExtra("resultado4", he2);
                                startActivity(intent);
                                finish();

                            }
                        }
                        else if (opcaoD[j] == "Homozigoto Recessivo B"){
                            if (y < 2){

                                ha1 = "b";
                                ha2 = "b";
                                textToSpeech.speak("Selecionado Homozigoto Recessivo B", textToSpeech.QUEUE_FLUSH, null);

                            }else {
                                he1 = "b";
                                he2 = "b";
                                textToSpeech.speak("Selecionado Homozigoto Recessivo B", textToSpeech.QUEUE_FLUSH, null);

                                intent.putExtra("resultado1", ha1);
                                intent.putExtra("resultado2", ha2);
                                intent.putExtra("resultado3", he1);
                                intent.putExtra("resultado4", he2);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }
                }
            }
            @Override
            public void onSwipeTopE() {
                lado = "E";
                if (ix == 0 & i > 0) {
                    yx--;
                    i--;
                    caminharE();
                } else if (i > 0) {
                    i--;
                    ix--;
                } else {
                    i = 0;
                    ix = 0;
                }
                cursorE[ix].setBackgroundResource(R.drawable.linha_horizontal);
                cursorE[ix + 1].setBackgroundColor(Color.TRANSPARENT);
                textToSpeech.speak(opcaoE[i], textToSpeech.QUEUE_FLUSH, null);
            }




            @Override
            public void onSwipeBottomE() {
                lado = "E";
                if (i == opcaoE.length - 1) {
                    i = opcaoE.length - 1;

                } else if (ix == 3 & i <= opcaoE.length) {
                    yx++;
                    i++;
                    caminharE();
                } else if (i < opcaoE.length) {
                    i++;
                    ix++;
                }
                cursorE[ix].setBackgroundResource(R.drawable.linha_horizontal);

                textToSpeech.speak(opcaoE[i], textToSpeech.QUEUE_FLUSH, null);
                if (ix > 0) {
                    cursorE[ix - 1].setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void onSwipeTopD() {
                lado = "D";
                if (jx == 0 & j > 0) {
                    zx--;
                    j--;
                    caminharD();
                } else if (j > 0) {
                    j--;
                    jx--;
                } else {
                    j = 0;
                    jx = 0;
                }
                cursorD[jx].setBackgroundResource(R.drawable.linha_horizontal);
                cursorD[jx + 1].setBackgroundColor(Color.TRANSPARENT);
                textToSpeech.speak(opcaoD[j], textToSpeech.QUEUE_FLUSH, null);

            }

            @Override
            public void onSwipeBottomD() {
                lado = "D";
                if (j == opcaoD.length - 1) {
                    j = opcaoD.length - 1;

                } else if (jx == 3 & j <= opcaoD.length) {
                    zx++;
                    j++;
                    caminharD();
                } else if (j < opcaoD.length) {
                    j++;
                    jx++;
                }
                cursorD[jx].setBackgroundResource(R.drawable.linha_horizontal);

                textToSpeech.speak(opcaoD[j], textToSpeech.QUEUE_FLUSH, null);
                if (jx > 0) {
                    cursorD[jx - 1].setBackgroundColor(Color.TRANSPARENT);
                }
            }



            @Override
            public void caminharE() {
                lista1.setText(String.valueOf(opcaoE[l1 + yx]));
                lista2.setText(String.valueOf(opcaoE[l2 + yx]));
                lista3.setText(String.valueOf(opcaoE[l3 + yx]));
            }


            @Override
            public void caminharD() {
                lista4.setText(String.valueOf(opcaoD[l4 + zx]));
                lista5.setText(String.valueOf(opcaoD[l5 + zx]));
                lista6.setText(String.valueOf(opcaoD[l6 + zx]));

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(proximitySensorListener);
        textToSpeech.stop();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
    /*método para ler a velocidade de voz definida pelo usuário*/
    public void ler_velocidade() {
        FileInputStream fis = null;
        try {

            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text = br.readLine();
            velocidade = Integer.parseInt(String.valueOf(text));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

