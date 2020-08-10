package com.bioblu.second_law;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteTelaTutorial;
import com.bioblu.main.main_menu;

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



public class activity_select_gene_coelho extends AppCompatActivity {
    public String escolha_gene;
    public String [] genes = new String[4];
    public String gene_selecionado_fala;

    public int screenWidth;
    public int y;
    private TextToSpeech TTS;
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l5 = 3;
    private int i = -1;
    private int ix = -1;
    private int fx = -1;
    private TextView lista1, lista2, lista3, lista4;
    private String[] opcao = {"Selvagem", "Chinchila", "Himalaia", "Albino"};
    TextView[] cursor = new TextView[5];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gene_coelho);
        /* tratamento de erro da api de fala */
        TTS = new TextToSpeech(activity_select_gene_coelho.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ler_velocidade();
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(new Locale("pt", "BR"));
                    TTS.setSpeechRate(velocidade);
                    TTS.setPitch(1);
                    TTS.speak("você está na tela de escolha de genes da polialelia"+'\n'+
                            "você deve selecionar 4 alelos, onde os dois primeiros serão referentes ao primeiro coelho, e os dois ultimos ao segundo coelho"+'\n'+"após isso você será redirecionado para tela de cruzamento", TextToSpeech.QUEUE_FLUSH, null);
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
                    TTS.speak("", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };


        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2* 1000* 1000);


        //Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        lista1 = findViewById(R.id.lista_coelho1);
        lista2 = findViewById(R.id.lista_coelho2);
        lista3 = findViewById(R.id.lista_coelho3);
        lista4 = findViewById(R.id.lista_coelho4);

        cursor[0] = lista1;
        cursor[1] = lista2;
        cursor[2] = lista3;
        cursor[3] = lista4;

        initTela();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayout_select_gene_coelho);

        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                Intent intent = new Intent(getApplicationContext(), activity_cruzamento_coelho.class);
                if (x == 1) {
                    TTS.speak("Confirmado Gene "+ gene_selecionado_fala, TextToSpeech.QUEUE_FLUSH, null);
                    genes[0] = escolha_gene;
                } else if (x == 2) {
                    TTS.speak("Confirmado Gene "+ gene_selecionado_fala, TextToSpeech.QUEUE_FLUSH, null);
                    genes[1] = escolha_gene;
                } else if (x == 3) {
                    TTS.speak("Confirmado Gene "+ gene_selecionado_fala, TextToSpeech.QUEUE_FLUSH, null);
                    genes[2] = escolha_gene;
                } else if (x == 4) {
                    TTS.speak("Confirmado Gene "+ gene_selecionado_fala, TextToSpeech.QUEUE_FLUSH, null);
                    genes[3] = escolha_gene;
                    intent.putExtra("genes_escolhido1", genes[0]);
                    intent.putExtra("genes_escolhido2", genes[1]);
                    intent.putExtra("genes_escolhido3", genes[2]);
                    intent.putExtra("genes_escolhido4", genes[3]);
                    finish();
                    startActivity(intent);
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
                TTS.speak(opcao[i], TextToSpeech.QUEUE_FLUSH, null);

                escolha_gene = opcao[i];
                gene_selecionado_fala = opcao[i];
            }

            @Override
            public void onSwipeBottom() {
                if (i == opcao.length - 1) {
                    i = opcao.length - 1;

                } else if (ix == 4 & i <= opcao.length) {
                    yx++;
                    i++;
                    caminhar();
                } else if (i < opcao.length) {
                    i++;
                    ix++;
                }
                cursor[ix].setBackgroundResource(R.drawable.linha_horizontal);

                TTS.speak(opcao[i], TextToSpeech.QUEUE_FLUSH, null);

                if (ix > 0) {
                    cursor[ix - 1].setBackgroundColor(Color.TRANSPARENT);
                }

                escolha_gene = opcao[i];
                gene_selecionado_fala = opcao[i];
            }

            @Override
            public void caminhar() {
                lista1.setText(String.valueOf(opcao[l1 + yx]));
                lista2.setText(String.valueOf(opcao[l2 + yx]));
                lista3.setText(String.valueOf(opcao[l3 + yx]));
                lista4.setText(String.valueOf(opcao[l5 + yx]));

            }

        });
    }

    @Override
    protected void onDestroy() {
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(proximitySensorListener);
        TTS.stop();
        if (TTS != null) {
            TTS.stop();
            TTS.shutdown();
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
