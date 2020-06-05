package com.bioblu.first_law;

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

import com.bioblu.controllers.OuvinteDeToque;
import com.bioblu.R;

import java.util.Locale;

public class activity_selecaoLetra extends AppCompatActivity  {

    public int screenWidth;
    int y;
    private TextToSpeech TTS;
    /**
     * Sintetiza a fala do texto para reprodução imediata ou para criar um arquivo de som
     **/
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 3;
    private int i = -1;
    private int ix = -1;
    private int fx = -1;
    private TextView lista1, lista2, lista3;
    private String[] opcao = {"A", "D", "V"};
    TextView[] cursor = new TextView[6];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    /**
     * String com informações de como usar o app
     **/
    private String introducao = "Escolha uma Variavel Para ultilizar no cruzamento  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_letra);
        Bundle dados = getIntent().getExtras();
        velocidade = dados.getInt("velocidade");
        TTS = new TextToSpeech(activity_selecaoLetra.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(new Locale("en", "US"));
                    TTS.setSpeechRate(velocidade);
                    TTS.setPitch(1);
                    TTS.speak("You are  at Letter selection screen for the crossing. after choosing the desired option with the swipe up or down, double-tap to select the letter.", TextToSpeech.QUEUE_FLUSH, null);
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
        y = 12;

        lista1 = findViewById(R.id.lista1);
        lista2 = findViewById(R.id.lista2);
        lista3 = findViewById(R.id.lista3);

        cursor[0] = lista1;
        cursor[1] = lista2;
        cursor[2] = lista3;

        initTela();

    }

    /**
     * METODOS AQUI
     **/

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Rlayout = findViewById(R.id.relativeLayoutconceito);

        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth, y) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {

                Intent intent = new Intent(getApplicationContext(), activity_cruzamento.class);
                if (i >= 0) {
                    switch (opcao[i]) {
                        case "A":
                            intent.putExtra("dominate", "A");
                            intent.putExtra("recessivo", "a");
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            finish();
                            break;
                        case "D":
                            intent.putExtra("dominate", "D");
                            intent.putExtra("recessivo", "d");
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            finish();
                            break;
                        case "V":
                            intent.putExtra("dominate", "V");
                            intent.putExtra("recessivo", "v");
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            finish();
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
                TTS.speak(opcao[i], TextToSpeech.QUEUE_FLUSH, null);
            }

            @Override
            public void onSwipeBottom() {
                if (i == opcao.length - 1) {
                    i = opcao.length - 1;

                } else if (ix == 3 & i <= opcao.length) {
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
            }

            @Override
            public void caminhar() {
                lista1.setText(String.valueOf(opcao[l1 + yx]));
                lista2.setText(String.valueOf(opcao[l2 + yx]));
                lista3.setText(String.valueOf(opcao[l3 + yx]));

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

}