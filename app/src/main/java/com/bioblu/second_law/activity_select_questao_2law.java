package com.bioblu.second_law;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteDeToque;
import com.bioblu.controllers.OuvinteTelaTutorial;

import static com.bioblu.main.activity_voiceRate.FILE_NAME;

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


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;


public class activity_select_questao_2law extends AppCompatActivity {
    public int screenWidth;
    int y;
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2;
    private int i = -1;
    private int ix = -1;
    private String [] r1 = {"C", "P", "c", "p"};
    private String [] r2 = {"P", "A", "p", "a"};
    private String [] r3 = {"D", "E", "d", "e"};
    private TextToSpeech textToSpeech;
    private TextView listaQ1, listaQ2, listaQ3;
    private String[] opcao = {"Questão 1", "Questão 2", "Questão 3"};
    TextView[] cursor = new TextView[4];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_questao_2law);

        //Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        y = 100;

        listaQ1 = findViewById(R.id.listaQ1);
        listaQ2 = findViewById(R.id.listaQ2);
        listaQ3 = findViewById(R.id.listaQ3);

        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_select_questao_2law.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ler_velocidade();
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak("Você está na tela de seleção de questão"+'\n'+" após escolher a opção desejada com o deslize para cima ou para baixo, dê dois toques para selecionar a questão", TextToSpeech.QUEUE_FLUSH, null);
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

        cursor[0] = listaQ1;
        cursor[1] = listaQ2;
        cursor[2] = listaQ3;

        initTela();

    }

    /**
     * METODOS AQUI
     **/

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Rlayout = findViewById(R.id.relativeLayoutquestao2law);

        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth, y) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu_2law.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                Intent intent = new Intent(getApplicationContext(), activity_questao_2law.class);
                if (i >= 0) {
                    switch (opcao[i]) {
                        case "Questão 1":
                            intent.putExtra("q", "Em aves Domésticas, O gene C condiciona plumagem branca e o seu alelo recessivo, plumagem Colorida . o gene P determina patas com plumas e o seu alelo recessivo, patas sem plumas. Esses pares de genes são autossômicos e segregam-se independentemente. Uma ave branca com patas com plumas, homozigota para os dois pares de genes, foi cruzada com uma colorida com patas sem plumas. Se os descendentes obitidos forem cruazados entre si, qual os genes serão gerados?"+'\n'+"Os genes parentais foram informados, informe os genes da geração F2");
                            intent.putExtra("r0", r1[0]);
                            intent.putExtra("r1", r1[1]);
                            intent.putExtra("r2", r1[2]);
                            intent.putExtra("r3", r1[3]);
                            startActivity(intent);
                            break;
                        case "Questão 2":
                            intent.putExtra("q", "Em coelhos, o gene P produz pelagem preta e o seu alelo recessivo p, pelagem parda desde que esteja presente o gene A.\n" + "Os animais aa são sempre albinos. Considerando que ocorra segregação independente entre esses genes, \n" + "a apartir do cruzamento Duplo heterozigoto  com um duplo recessivo, qual foram os genes gerados."+'\n'+"Os genes parentais foram informados, informe os genes da geração F2");
                            intent.putExtra("r0", r2[0]);
                            intent.putExtra("r1", r2[1]);
                            intent.putExtra("r2", r2[2]);
                            intent.putExtra("r3", r2[3]);
                            startActivity(intent);
                            break;
                        case "Questão 3":
                            intent.putExtra("q", "A audição normal depende da presença de pelo menos um dominante de dois pares de genes, D e E, que se segregam independentemente.\n" + "Caso você examine a prole de uma grande número de casamentos em que ambos os cônjuges são duplos heterozigotos para esses dois pares de genes,\n" + "qual são os genes gerados."+'\n'+"Os genes parentais foram informados, informe os genes da geração F2");
                            intent.putExtra("r0", r3[0]);
                            intent.putExtra("r1", r3[1]);
                            intent.putExtra("r2", r3[2]);
                            intent.putExtra("r3", r3[3]);
                            startActivity(intent);
                            break;
                    }
                }
            }

            @Override
            public void onSwipeTop() {
                if (ix == 0 & i > 0) {
                    yx--;
                    i--;
                    caminhar();
                } else if (i > 0) {
                    i--;
                    ix--;
                } else {
                    i = 0;
                    ix = 0;
                }
                cursor[ix].setBackgroundResource(R.drawable.linha_horizontal);
                cursor[ix + 1].setBackgroundColor(Color.TRANSPARENT);

                textToSpeech.speak(opcao[i], TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onSwipeBottom() {
                if (i == opcao.length - 1) {
                    i = opcao.length - 1;

                } else if (ix == 2 & i <= opcao.length) {
                    yx++;
                    i++;
                    caminhar();
                } else if (i < opcao.length) {
                    i++;
                    ix++;
                }
                cursor[ix].setBackgroundResource(R.drawable.linha_horizontal);

                textToSpeech.speak(opcao[i], TextToSpeech.QUEUE_FLUSH, null);

                if (ix > 0) {
                    cursor[ix - 1].setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void caminhar() {
                listaQ1.setText(String.valueOf(opcao[l1 + yx]));
                listaQ2.setText(String.valueOf(opcao[l2 + yx]));
                listaQ3.setText(String.valueOf(opcao[l3 + yx]));
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
