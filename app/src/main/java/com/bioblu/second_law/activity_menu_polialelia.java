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
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
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


public class activity_menu_polialelia extends AppCompatActivity  {

    public int screenWidth;
    public int y;
    private TextToSpeech TTS;
    private int yx = 0, l1 = 0, l2 = 1;
    private int i = -1;
    private int ix = -1;
    private int fx = -1;
    private TextView lista1, lista2;
    private String[] opcao;
    TextView[] cursor = new TextView[4];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    String poli1;
    String poli2;
    String poli3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_polialelia);

        poli1 = getString(R.string.polialelia2);
        poli2 = getString(R.string.polialelia3);
        poli3 = getString(R.string.polialelia4);

        opcao = new String[] {poli1, poli2};

        /* tratamento de erro da api de fala */
        TTS = new TextToSpeech(activity_menu_polialelia.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ler_velocidade();
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(Locale.getDefault());
                    TTS.setSpeechRate(velocidade);
                    TTS.setPitch(1);
                    TTS.speak(poli3, TextToSpeech.QUEUE_FLUSH, null);
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
        lista1 = findViewById(R.id.lista_polialelia_id_1);
        lista2 = findViewById(R.id.lista_polialelia_id_2);


        cursor[0] = lista1;
        cursor[1] = lista2;


        initTela();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayout_polialelia);

        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                if (i >= 0) {
                    switch (opcao[i]) {
                        case "TUTORIAL":
                        case "TRAINING SCREEN": {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), activity_tutorial_polialelia.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            break;
                        }
                        case "CRUZAMENTO":
                        case "CROSSING": {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), activity_select_gene_coelho.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            break;
                        }
                        default:
                            System.out.println("Erro");
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

                } else if (ix == 2 & i <= opcao.length) {
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