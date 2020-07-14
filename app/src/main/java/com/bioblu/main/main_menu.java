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
import com.bioblu.controllers.OuvinteTelaTutorial;
import com.bioblu.first_law.activity_menu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static com.bioblu.main.activity_voiceRate.FILE_NAME;

public class main_menu extends AppCompatActivity {

    public int screenWidth;
    public int y;
    private TextToSpeech TTS;
    private int yx = 0, l1 = 0, l2 = 1, l3 = 3, l4 =5;
    private int i = -1;
    private int ix = -1;
    private int fx = -1;

    String main_menu_1;
    String main_menu_2;
    String main_menu_3;
    String main_menu_4;
    String main_menu_5;
    String main_menu_6;
    String main_menu_7;

    private TextView lista1, lista2, lista3, lista4;
    private String[] opcao;
    TextView[] cursor = new TextView[6];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        main_menu_1 = getString(R.string.main_menu_1);
        main_menu_2 = getString(R.string.main_menu_2);
        main_menu_3 = getString(R.string.main_menu_3);
        main_menu_4 = getString(R.string.main_menu_4);
        main_menu_5 = getString(R.string.main_menu_5);
        main_menu_6 = getString(R.string.main_menu_6);
        main_menu_7 = getString(R.string.main_menu_7);

        opcao = new String[] {main_menu_1, main_menu_2, main_menu_3, main_menu_4};

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //if (proximitySensor == null) {
          //  Toast.makeText(this,"Proximity sensor is not avalible !", Toast.LENGTH_LONG).show();
            //finish();
        //}

        /* tratamento de erro da api de fala */
        TTS = new TextToSpeech(main_menu.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ler_velocidade();
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(Locale.getDefault());
                    TTS.setSpeechRate(velocidade);
                    TTS.setPitch(1);
                    TTS.speak(main_menu_5, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

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
        lista1 = findViewById(R.id.listaQ1);
        lista2 = findViewById(R.id.listaQ2);
        lista3 = findViewById(R.id.listaQ4);
        lista4 = findViewById(R.id.listaQ5);
        cursor[0] = lista1;
        cursor[1] = lista2;
        cursor[2] = lista3;
        cursor[3] = lista4;
        initTela();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayoutMenu);

        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void doubleTap() {
                sair = false;
                if (i >= 0) {
                    switch (opcao[i]) {
                        case "TRAINING SCREEN":
                        case "AMBIENTAÇÃO": {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), activity_tutorial_main_menu.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            break;
                        }
                        case "MENDEL'S 1ST LAW":
                        case "PRIMEIRA LEI DE MENDEL": {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            break;
                        }
                        case "CONFIGURATION":
                        case "CONFIGURAÇÃO": {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), activity_voiceRate.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            break;
                        }
                        case "REVIEW CONCEPTS":
                        case "REVER CONCEITOS": {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), activity_selectConceitos.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            break;
                        }


                    }
                }
            }

            @Override
            public void onSwipeTop() {
                sair = false;
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
                sair = false;
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
            }

            @Override
            public void caminhar() {
                lista1.setText(String.valueOf(opcao[l1 + yx]));
                lista2.setText(String.valueOf(opcao[l2 + yx]));
                lista3.setText(String.valueOf(opcao[l3 + yx]));
                lista4.setText(String.valueOf(opcao[l4 + yx]));
            }


            public void LGesture() {
                TTS.speak(main_menu_6, TextToSpeech.QUEUE_FLUSH, null);
                sair = true;

            }

            public void onLongPressTutorial(){
                if(sair){
                    finish();
                }
            }

            public void wrong(){
                TTS.speak(main_menu_7, TextToSpeech.QUEUE_FLUSH, null);
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
