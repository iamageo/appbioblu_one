package com.bioblu.main;

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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static com.bioblu.main.activity_voiceRate.FILE_NAME;

public class activity_selectConceitos extends AppCompatActivity {

    public int screenWidth;
    int y;
    private TextToSpeech textToSpeech;
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 3, l5 = 4, l6 = 5, l7 = 6, l8 = 7 , l9 = 8, l10 = 9, l11 = 10, l12 = 11;
    private int i = -1;
    private int ix = -1;
    private TextView lista1, lista2, lista3, lista4, lista5, lista6, lista7, lista8, lista9, lista10, lista11, lista12;

    private String conceito_0;
    private String conceito_1;
    private String conceito_2;
    private String conceito_3;
    private String conceito_4;
    private String conceito_5;
    private String conceito_6;
    private String conceito_7;
    private String conceito_8;
    private String conceito_9;
    private String conceito_10;
    private String conceito_11;
    private String conceito_12;
    private String conceito_13;
    private String conceito_14;
    private String conceito_15;
    private String conceito_16;
    private String conceito_17;
    private String conceito_18;
    private String conceito_19;
    private String conceito_20;
    private String conceito_21;
    private String conceito_22;
    private String conceito_23;
    private String conceito_24;
    private String conceito_25;

    private String[] opcao;
    TextView[] cursor = new TextView[12];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_conceitos);

        conceito_0 = getString(R.string.conceito_0);
        conceito_1 = getString(R.string.conceito_1);
        conceito_2 = getString(R.string.conceito_2);
        conceito_3 = getString(R.string.conceito_3);
        conceito_4 = getString(R.string.conceito_4);
        conceito_5 = getString(R.string.conceito_5);
        conceito_6 = getString(R.string.conceito_6);
        conceito_7 = getString(R.string.conceito_7);
        conceito_8 = getString(R.string.conceito_8);
        conceito_9 = getString(R.string.conceito_9);
        conceito_10 = getString(R.string.conceito_10);
        conceito_11 = getString(R.string.conceito_11);
        conceito_12 = getString(R.string.conceito_12);
        conceito_13 = getString(R.string.conceito_13);
        conceito_14 = getString(R.string.conceito_14);
        conceito_15 = getString(R.string.conceito_15);
        conceito_16 = getString(R.string.conceito_16);
        conceito_17 = getString(R.string.conceito_17);
        conceito_18 = getString(R.string.conceito_18);
        conceito_19 = getString(R.string.conceito_19);
        conceito_20 = getString(R.string.conceito_20);
        conceito_21 = getString(R.string.conceito_21);
        conceito_22 = getString(R.string.conceito_22);
        conceito_23 = getString(R.string.conceito_23);
        conceito_24 = getString(R.string.conceito_24);
        conceito_25 = getString(R.string.conceito_25);

        opcao = new String[] {conceito_1, conceito_2,conceito_3,conceito_4,conceito_5,conceito_6,conceito_7,conceito_8,conceito_9,conceito_10,conceito_11,conceito_12};

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        y = 500;

        lista1 = findViewById(R.id.select_conceito1);
        lista2 = findViewById(R.id.select_conceito2);
        lista3 = findViewById(R.id.select_conceito3);
        lista4 = findViewById(R.id.select_conceito4);
        lista5 = findViewById(R.id.select_conceito5);
        lista6 = findViewById(R.id.select_conceito6);
        lista7 = findViewById(R.id.select_conceito7);
        lista8 = findViewById(R.id.select_conceito8);
        lista9 = findViewById(R.id.select_conceito9);
        lista10 = findViewById(R.id.select_conceito10);
        lista11 = findViewById(R.id.select_conceito11);
        lista12 = findViewById(R.id.select_conceito12);

        textToSpeech = new TextToSpeech(activity_selectConceitos.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ler_velocidade();
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak(conceito_13, TextToSpeech.QUEUE_ADD, null);
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

        cursor[0] = lista1;
        cursor[1] = lista2;
        cursor[2] = lista3;
        cursor[3] = lista4;
        cursor[4] = lista5;
        cursor[5] = lista6;
        cursor[6] = lista7;
        cursor[7] = lista8;
        cursor[8] = lista9;
        cursor[9] = lista10;
        cursor[10] = lista11;
        cursor[11] = lista12;
        initTela();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Rlayout = findViewById(R.id.relativeLayoutconceito_geral);
        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth, y) {

            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
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
                if (i == 0) {
                    textToSpeech.speak(conceito_14, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 1) {
                    textToSpeech.speak(conceito_15, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 2) {
                    textToSpeech.speak(conceito_16, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 3) {
                    textToSpeech.speak(conceito_17, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 4) {
                    textToSpeech.speak(conceito_18, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 5) {
                    textToSpeech.speak(conceito_19, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 6) {
                    textToSpeech.speak(conceito_20, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 7) {
                    textToSpeech.speak(conceito_21, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 8) {
                    textToSpeech.speak(conceito_22, textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 9) {
                    textToSpeech.speak(conceito_23, textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 10) {
                    textToSpeech.speak(conceito_24, textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 11) {
                    textToSpeech.speak(conceito_25, textToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onSwipeBottom() {
                if (i == opcao.length - 1) {
                    i = opcao.length - 1;

                } else if (ix == 11 & i <= opcao.length) {
                    yx++;
                    i++;
                    caminhar();
                } else if (i < opcao.length) {
                    i++;
                    ix++;
                }
                cursor[ix].setBackgroundResource(R.drawable.linha_horizontal);
                //para baixo
                if (i == 0) {
                    textToSpeech.speak(conceito_14, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 1) {
                    textToSpeech.speak(conceito_15, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 2) {
                    textToSpeech.speak(conceito_16, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 3) {
                    textToSpeech.speak(conceito_17, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 4) {
                    textToSpeech.speak(conceito_18, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 5) {
                    textToSpeech.speak(conceito_19, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 6) {
                    textToSpeech.speak(conceito_20, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 7) {
                    textToSpeech.speak(conceito_21, textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 8) {
                    textToSpeech.speak(conceito_22, textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 9) {
                    textToSpeech.speak(conceito_23, textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 10) {
                    textToSpeech.speak(conceito_24, textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 11) {
                    textToSpeech.speak(conceito_25, textToSpeech.QUEUE_FLUSH, null);
                }
                if (ix > 0) {
                    cursor[ix - 1].setBackgroundColor(Color.TRANSPARENT);
                }
            }

            @Override
            public void caminhar() {
                lista1.setText(String.valueOf(opcao[l1 + yx]));
                lista2.setText(String.valueOf(opcao[l2 + yx]));
                lista3.setText(String.valueOf(opcao[l3 + yx]));
                lista4.setText(String.valueOf(opcao[l4 + yx]));
                lista5.setText(String.valueOf(opcao[l5 + yx]));
                lista6.setText(String.valueOf(opcao[l6 + yx]));
                lista7.setText(String.valueOf(opcao[l7 + yx]));
                lista8.setText(String.valueOf(opcao[l8 + yx]));
                lista9.setText(String.valueOf(opcao[l9 + yx]));
                lista10.setText(String.valueOf(opcao[l10 + yx]));
                lista11.setText(String.valueOf(opcao[l11 + yx]));
                lista12.setText(String.valueOf(opcao[l12 + yx]));
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
