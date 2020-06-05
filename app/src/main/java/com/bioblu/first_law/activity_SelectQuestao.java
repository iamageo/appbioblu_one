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

public class activity_SelectQuestao extends AppCompatActivity {
    public int screenWidth;
    int y;
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 3, l5 = 4;
    private int i = -1;
    private int ix = -1;
    private TextToSpeech textToSpeech;
    private TextView listaQ1, listaQ2, listaQ3, listaQ4, listaQ5;
    private String[] opcao = {"Question 1", "Question 2", "Question 3", "Question 4", "Question 5"};
    TextView[] cursor = new TextView[6];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectquestao);
        Bundle dados = getIntent().getExtras();
        velocidade = dados.getInt("velocidade");

        //Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        y = 100;

        listaQ1 = findViewById(R.id.listaQ1);
        listaQ2 = findViewById(R.id.listaQ2);
        listaQ3 = findViewById(R.id.listaQ3);
        listaQ4 = findViewById(R.id.listaQ4);
        listaQ5 = findViewById(R.id.listaQ5);


        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_SelectQuestao.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("en", "US"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak("You are in the question selection screen. after choosing the desired option with the swipe up or down, double-tap to select the question", TextToSpeech.QUEUE_FLUSH, null);
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
        cursor[3] = listaQ4;
        cursor[4] = listaQ5;

        initTela();

    }

    /**
     * METODOS AQUI
     **/

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_select_questao1law);

        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth, y) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                Intent intent = new Intent(getApplicationContext(), activity_questao.class);
                if (i >= 0) {
                    switch (opcao[i]) {
                        case "Question 1":
                            intent.putExtra("q", "We know that albinism is a recessive genetic anomaly in which the individual has a deficiency in the production of melanin in their skin, If a boy who produces melanin normally, but has a brother who is albine, ( Heterozygous Dominant), marries an albino girl, (Homozygous Recessive), What is the cross between this couple? ");
                            intent.putExtra("r", "Aa,Aa,aa,aa");
                            intent.putExtra("questao", "1");
                            intent.putExtra("cruzamento", "Aa x aa");
                            finish();
                            startActivity(intent);

                            break;
                        case "Question 2":
                            intent.putExtra("q", "Since myopia is considered a recessive disease, determine the crossing of a normal heterozygous couple for myopia.");
                            intent.putExtra("r", "AA, Aa, Aa, aa");
                            intent.putExtra("questao", "2");
                            intent.putExtra("cruzamento", "Aa x Aa");
                            finish();
                            startActivity(intent);
                            break;
                        case "Question 3":
                            intent.putExtra("q", "What is the crossing generated by a couple, that both are heterozygous?");
                            intent.putExtra("r", "AA,Aa,Aa,aa");
                            intent.putExtra("questao", "3");
                            intent.putExtra("cruzamento", "Aa x Aa");
                            finish();
                            startActivity(intent);
                            break;
                        case "Question 4":
                            intent.putExtra("q", "A man who has dark eyes but with a mother with light eyes, married a woman with light eyes whose father has dark-eyed parents. Determine this crossing");
                            intent.putExtra("r", "Aa, Aa, aa, aa");
                            intent.putExtra("questao", "4");
                            intent.putExtra("cruzamento", "Aa x aa");
                            finish();
                            startActivity(intent);
                            break;
                        case "Question 5":
                            intent.putExtra("q", "Achondroplasia is a type of dwarfism in which the head and trunk are normal, but the arms and legs are very short, It is conditioned by a dominant gene that, in homozygosity, causes death before birth. Normal individuals are recessive and the affected ones are heterozygous. What is this crossing? ");
                            intent.putExtra("r", "aa, aa, aa, aa");
                            intent.putExtra("questao", "5");
                            finish();
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

                } else if (ix == 5 & i <= opcao.length) {
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
                listaQ4.setText(String.valueOf(opcao[l4 + yx]));
                listaQ5.setText(String.valueOf(opcao[l5 + yx]));
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
}
