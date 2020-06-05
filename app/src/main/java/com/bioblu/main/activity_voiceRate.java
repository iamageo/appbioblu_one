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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import android.provider.Settings.Secure;

public class activity_voiceRate extends AppCompatActivity {

    public int screenWidth;
    private TextToSpeech TTS;
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2;
    private int i = -1;
    private int ix = -1;
    private int fx = -1;
    private TextView lista1, lista2, lista3, textView_imei;
    private String[] opcao = {"Voice speed 1", "Voice speed 2", "Voice speed 3"};
    TextView[] cursor = new TextView[6];
    public static String FILE_NAME = "voiceRate.txt";
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    private String android_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_rate);

         android_id = Secure.getString(
                getApplicationContext().getContentResolver(), Secure.ANDROID_ID);

        /* tratamento de erro da api de fala */
        TTS = new TextToSpeech(activity_voiceRate.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    TTS.setLanguage(new Locale("en", "US"));
                    TTS.setSpeechRate(velocidade);
                    TTS.setPitch(1);
                    TTS.speak("This is the Voice Speed Choice Menu. In it you will have 3 options to choose, You can navigate through the slide up or down after hearing the chosen option, make a double-tap to proceed. ", TextToSpeech.QUEUE_FLUSH, null);
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


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        lista1 = findViewById(R.id.listaQ1);
        lista2 = findViewById(R.id.listaQ2);
        lista3 = findViewById(R.id.listaQ3);
        textView_imei = findViewById(R.id.textView_imei);

        textView_imei.setText("DEVICE ID: "+ android_id);

        cursor[0] = lista1;
        cursor[1] = lista2;
        cursor[2] = lista3;

        initTela();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayout_voiceRate);

        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(this, screenWidth) {
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
                        case "Voice speed 1": {
                            salvar_velocidade("1");
                            Intent intent = new Intent(getApplicationContext(), main_menu.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        case "Voice speed 2": {
                            salvar_velocidade("2");
                            Intent intent = new Intent(getApplicationContext(), main_menu.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            finish();
                            break;
                        }
                        case "Voice speed 3": {
                            salvar_velocidade("4");
                            Intent intent = new Intent(getApplicationContext(), main_menu.class);
                            intent.putExtra("velocidade", velocidade);
                            startActivity(intent);
                            finish();
                            break;
                        }
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
                if(opcao[i].equals("Voice speed 1")){
                    velocidade = 1;
                    TTS.setSpeechRate(velocidade);
                }else if(opcao[i].equals("Voice speed 2")){
                    velocidade = 3;
                    TTS.setSpeechRate(velocidade);
                }else {
                    velocidade = 4;
                    TTS.setSpeechRate(velocidade);
                }
                TTS.speak(opcao[i], TTS.QUEUE_FLUSH, null);
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

                if(opcao[i].equals("Voice speed 1")){
                    velocidade = 1;
                    TTS.setSpeechRate(velocidade);
                }else if(opcao[i].equals("Voice speed 2")){
                    velocidade = 3;
                    TTS.setSpeechRate(velocidade);
                }else {
                    velocidade = 4;
                    TTS.setSpeechRate(velocidade);
                }
                TTS.speak(opcao[i], TTS.QUEUE_FLUSH, null);

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

    /*método para salvar a velocidade de voz definida pelo usuário*/
    public void salvar_velocidade(String text) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null ){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(proximitySensorListener);
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
}
