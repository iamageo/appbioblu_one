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
    private TextToSpeech textToSpeech; /** Sintetiza a fala do texto para reprodução imediata ou para criar um arquivo de som **/
    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 3, l5 = 4, l6 = 5, l7 = 6, l8 = 7 , l9 = 8, l10 = 9, l11 = 10, l12 = 11;
    private int i = -1;
    private int ix = -1;
    private TextView lista1, lista2, lista3, lista4, lista5, lista6, lista7, lista8, lista9, lista10, lista11, lista12;
    private String[] opcao = {"Gene", "Genotype", "Phenotype", "Heterozygote", "Homozygous", "Dominant", "Recessive", "Codominance","Allele","Gametes","Dihybridisme","Monohybridisme"};
    TextView[] cursor = new TextView[12];
    public int velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_conceitos);
        /** Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque **/
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

        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_selectConceitos.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    ler_velocidade();
                    textToSpeech.setLanguage(new Locale("en", "US"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak("You are in the concept screen to navigate through them just swipe up or down to return to the menu, make an reverse  L anywhere on the screen.", TextToSpeech.QUEUE_ADD, null);
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

    /** METODOS AQUI **/

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
                //textToSpeech.speak(opcao[i], textToSpeech.QUEUE_FLUSH, null);
                if (i == 0) {
                    textToSpeech.speak("Gene. Are specific sequences of nucleotides in DNA that constitute the fundamental unit of heredity.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 1) {
                    textToSpeech.speak("Genotype. Group of genes of the individual.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 2) {
                    textToSpeech.speak("Phenotype. Group of physical and physiological characteristics of an individual that results of the expression of the model and action of the environment.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 3) {
                    textToSpeech.speak("Heterozygote. When an individual has two different alleles of the same gene", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 4) {
                    textToSpeech.speak("Homozygous. When an individual has two equal alleles of the same gene.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 5) {
                    textToSpeech.speak("Dominant. The allele is expressed in a homozygous condition and in a heterozygous condition.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 6) {
                    textToSpeech.speak("Recessive. The allele is only expressed in homozygosis. for that reason it has no phenotypic effect when in heterozygosis.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 7) {
                    textToSpeech.speak("Codominance. Situation in which the expression of the phenotypes of both alleles is observed when presented in heterozygosis.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 8) {
                    textToSpeech.speak("Allele. Alternative form of a gene that affects the same characteristic differently.", textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 9) {
                    textToSpeech.speak("Gametes. Are cells involved in sexual reproduction Heredity: transmission of resources from parents to their descendants.", textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 10) {
                    textToSpeech.speak("Dihybridisme. This term refers to Mendel's second law, where differences in one characteristic are inherited regardless of differences in another characteristic.", textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 11) {
                    textToSpeech.speak("Monohybridisme. This term refers to Mendel's first law, and applies to hybrid individuals, considering only one characteristic.", textToSpeech.QUEUE_FLUSH, null);
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
                    textToSpeech.speak("Gene. Are specific sequences of nucleotides in DNA that constitute the fundamental unit of heredity.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 1) {
                    textToSpeech.speak("Genotype. Group of genes of the individual.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 2) {
                    textToSpeech.speak("Phenotype. Group of physical and physiological characteristics of an individual that results of the expression of the model and action of the environment.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 3) {
                    textToSpeech.speak("Heterozygote. When an individual has two different alleles of the same gene", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 4) {
                    textToSpeech.speak("Homozygous. When an individual has two equal alleles of the same gene.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 5) {
                    textToSpeech.speak("Dominant. The allele is expressed in a homozygous condition and in a heterozygous condition.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 6) {
                    textToSpeech.speak("Recessive. The allele is only expressed in homozygosis. for that reason it has no phenotypic effect when in heterozygosis.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 7) {
                    textToSpeech.speak("Codominance. Situation in which the expression of the phenotypes of both alleles is observed when presented in heterozygosis.", textToSpeech.QUEUE_FLUSH, null);
                } else if (i == 8) {
                    textToSpeech.speak("Allele. Alternative form of a gene that affects the same characteristic differently.", textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 9) {
                    textToSpeech.speak("Gametes. Are cells involved in sexual reproduction Heredity: transmission of resources from parents to their descendants.", textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 10) {
                    textToSpeech.speak("Dihybridisme. This term refers to Mendel's second law, where differences in one characteristic are inherited regardless of differences in another characteristic.", textToSpeech.QUEUE_FLUSH, null);
                }else if (i == 11) {
                    textToSpeech.speak("Monohybridisme. This term refers to Mendel's first law, and applies to hybrid individuals, considering only one characteristic.", textToSpeech.QUEUE_FLUSH, null);
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
