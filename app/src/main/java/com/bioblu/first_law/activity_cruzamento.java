package com.bioblu.first_law;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
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

public class activity_cruzamento extends AppCompatActivity {
    /*criando variáveis para ImageView */
    private TextView textViewFilho1, textViewFilho2, textViewFilho3, textViewFilho4, textView1, textView2;
    private ImageView meuImageView1, meuImageView2, filho1, filho2, filho3, filho4;
    public int screenWidth, screenHeight;
    public String dominate,letra,recessivo ;
    public String homozigotoD = dominate+dominate;
    public String hetorozigotoD = dominate+recessivo;
    public String homozigotoR = recessivo+recessivo;
    private String d, c, x1, x2, x3, x4, d1, c1, d2, c2, d3, c3, d4, c4,z1,z2,z3,z4;
    char a1, a2, b1, b2;
    int y;
    private TextToSpeech textToSpeech;
    public float velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruzamento);
        Bundle dados = getIntent().getExtras();
        ler_velocidade();

        textViewFilho1 = findViewById(R.id.textViewF1_cruzamento1law);
        textViewFilho2 = findViewById(R.id.textViewF2_cruzamento1law);
        textViewFilho3 = findViewById(R.id.textViewF3_cruzamento1law);
        textViewFilho4 = findViewById(R.id.textViewF4_cruzamento1law);

        textView1 = findViewById(R.id.textViewP1_cruzamento1law);
        textView2 = findViewById(R.id.textViewP2_cruzamento1law);

        meuImageView1 = findViewById(R.id.imageViewParental1_cruzamento1law);
        meuImageView2 = findViewById(R.id.ImageViewParental2_cruzamento1law);

        filho1 = findViewById(R.id.imageViewF1_cruzamento1law);
        filho2 = findViewById(R.id.imageViewF2_cruzamento1law);
        filho3 = findViewById(R.id.imageViewF3_cruzamento1law);
        filho4 = findViewById(R.id.imageViewF4_cruzamento1law);

        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_cruzamento.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("en", "US"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.setPitch(1);
                    textToSpeech.speak("Now you are in the crossing screen. Make 4 taps to inform the Application which crossing you will make." +
                            "After that, swipe 8 times to make the crossing you informed above.", TextToSpeech.QUEUE_FLUSH, null);
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

        /** Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque**/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        y = 12;

        dominate = dados.getString("dominate");
        recessivo = dados.getString("recessivo");
        letra = dados.getString("dominate");
        homozigotoD = dominate + dominate;
        hetorozigotoD = dominate + recessivo;
        homozigotoR = recessivo + recessivo;
        initTela();


    }
    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {

        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_cruzamento1law);

        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth,y) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                textToSpeech.speak("Confirmado Gene "+escolhafala, TextToSpeech.QUEUE_FLUSH, null);
                if (x >= 1 && escolha != null) {
                    if (x == 1) {
                        a1 = escolha.charAt(0);
                    } else if (x == 2) {
                        a2 = escolha.charAt(0);
                        if ((a1 == dominate.charAt(0)) && (a2 == dominate.charAt(0))) {
                            meuImageView1.getDrawable();
                            meuImageView1.setImageResource(R.drawable.quadrado_normal);
                            textView1.setText(homozigotoD);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                        } else if (((a1 == recessivo.charAt(0)) && (a2 == dominate.charAt(0))) || ((a1 == dominate.charAt(0)) && (a2 == recessivo.charAt(0)))) {
                            meuImageView1.setImageResource(R.drawable.quadrado_normal);
                            textView1.setText(hetorozigotoD);
                            textToSpeech.speak("Gene " + letra + " Heterozygous Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                        } else if ((a1 == recessivo.charAt(0)) && (a2 == recessivo.charAt(0))) {
                            meuImageView1.setImageResource(R.drawable.quadrado_preenchido);
                            textView1.setText(homozigotoR);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Recessive Selected", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 3) {
                        b1 = escolha.charAt(0);
                    } else if (x == 4) {
                        b2 = escolha.charAt(0);
                        if ((b1 == dominate.charAt(0)) && (b2 == dominate.charAt(0))) {
                            meuImageView2.setImageResource(R.drawable.quadrado_normal);
                            textView2.setText(homozigotoD);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                        } else if (((b1 == recessivo.charAt(0)) && (b2 == dominate.charAt(0))) || ((b1 == dominate.charAt(0)) && (b2 == recessivo.charAt(0)))) {
                            meuImageView2.setImageResource(R.drawable.quadrado_normal);
                            textView2.setText(hetorozigotoD);
                            textToSpeech.speak("Gene " + letra + " Heterozygous Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                        } else if ((b1 == recessivo.charAt(0)) && (b2 == recessivo.charAt(0))) {
                            meuImageView2.setImageResource(R.drawable.quadrado_preenchido);
                            textView2.setText(homozigotoR);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Recessive Selected", TextToSpeech.QUEUE_FLUSH, null);
                        }
                        /** Checando Se a letra maiuscula está na frente no X1**/
                        if (Character.isUpperCase(a1)) {
                            x1 = Character.toString(a1) + b1;
                        } else if (Character.isUpperCase(b1)) {
                            x1 = Character.toString(b1) + a1;
                        } else {
                            x1 = Character.toString(a1) + b1;
                        }

                        /** Checando Se a letra maiuscula está na frente no X2**/
                        if (Character.isUpperCase(a1)) {
                            x2 = Character.toString(a1) + b2;
                        } else if (Character.isUpperCase(b2)) {
                            x2 = Character.toString(b2) + a1;
                        } else {
                            x2 = Character.toString(a1) + b2;
                        }
                        /** Checando Se a letra maiuscula está na frente no X3**/
                        if (Character.isUpperCase(a2)) {
                            x3 = Character.toString(a2) + b1;
                        } else if (Character.isUpperCase(b1)) {
                            x3 = Character.toString(b1) + a2;
                        } else {
                            x3 = Character.toString(a2) + b1;
                        }
                        /** Checando Se a letra maiuscula está na frente no X4**/
                        if (Character.isUpperCase(a2)) {
                            x4 = Character.toString(a2) + b2;
                        } else if (Character.isUpperCase(b2)) {
                            x4 = Character.toString(b2) + a2;
                        } else {
                            x4 = Character.toString(a2) + b2;
                        }
                        c = x1 + "," + x2 + "," + x3 + "," + x4;
                        c = c.trim();

                        /** Cruzamento feito pelo User**/

                    } else if (x == 5) {
                        c1 = escolha;
                    } else if (x == 6) {
                        d1 = escolha;
                        z1 = c1 + d1;
                        if (c1.equals(dominate) && d1.equals(dominate)) {
                            filho1.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho1.setText(homozigotoD);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c1.equals(recessivo) && d1.equals(dominate) || c1.equals(dominate) && d1.equals(recessivo)) {
                            filho1.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho1.setText(hetorozigotoD);
                            textToSpeech.speak("Gene " + letra + " Heterozygous Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c1.equals(recessivo) && d1.equals(recessivo)) {
                            filho1.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho1.setText(homozigotoR);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Recessive Selected", TextToSpeech.QUEUE_FLUSH, null);
                            re++;
                        }
                    } else if (x == 7) {
                        c2 = escolha;
                    } else if (x == 8) {
                        d2 = escolha;
                        z2 = c2 + d2;
                        if (c2.equals(dominate) && d2.equals(dominate)) {
                            filho2.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho2.setText(homozigotoD);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c2.equals(recessivo) && d2.equals(dominate) || c2.equals(dominate) && d2.equals(recessivo)) {
                            filho2.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho2.setText(hetorozigotoD);
                            textToSpeech.speak("Gene " + letra + " Heterozygous Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c2.equals(recessivo) && d2.equals(recessivo)) {
                            filho2.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho2.setText(homozigotoR);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Recessive Selected", TextToSpeech.QUEUE_FLUSH, null);
                            re++;
                        }
                    } else if (x == 9) {
                        c3 = escolha;
                    } else if (x == 10) {
                        d3 = escolha;
                        z3 = c3 + d3;
                        if (c3.equals(dominate) && d3.equals(dominate)) {
                            filho3.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho3.setText(homozigotoD);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c3.equals(recessivo) && d3.equals(dominate) || c3.equals(dominate) && d3.equals(recessivo)) {
                            filho3.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho3.setText(hetorozigotoD);
                            textToSpeech.speak("Gene " + letra + " Heterozygous Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            textToSpeech.speak("Você acertou!!! Continue.", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c3.equals(recessivo) && d3.equals(recessivo)) {
                            filho3.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho3.setText(homozigotoR);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Recessive Selected", TextToSpeech.QUEUE_FLUSH, null);
                            re++;
                        }
                    } else if (x == 11) {
                        c4 = escolha;
                    } else if (x == 12) {
                        d4 = escolha;
                        z4 = c4 + d4;
                        if (c4.equals(dominate) && d4.equals(dominate)) {
                            filho4.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho4.setText(homozigotoD);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c4.equals(recessivo) && d4.equals(dominate) || c4.equals(dominate) && d4.equals(recessivo)) {
                            filho4.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho4.setText(hetorozigotoD);
                            textToSpeech.speak("Gene " + letra + " Heterozygous Dominant Selected", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c4.equals(recessivo) && d4.equals(recessivo)) {
                            filho4.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho4.setText(homozigotoR);
                            textToSpeech.speak("Gene " + letra + " Homozygous  Recessive Selected", TextToSpeech.QUEUE_FLUSH, null);
                            re++;
                        }
                        d = c1 + d1 + "," + c2 + d2 + "," + c3 + d3 + "," + c4 + d4;
                        d = d.trim();
                        ho = (ho / 4) * 100;
                        he = (he / 4) * 100;
                        re = (re / 4) * 100;
                        homoD = (int) ho;
                        heteD = (int) he;
                        reces = (int) re;

                        if (homoD > 0 & heteD > 0 & reces > 0) {
                            textToSpeech.speak("The Genes generated through the Crossings were," + homoD + "Percent Homozygote Dominant and," + heteD + "Percent Heterozygote Dominant, and" + reces + "Percent Homozygote Recessive.", TextToSpeech.QUEUE_ADD, null);

                        } else if (homoD > 0 & heteD > 0) {
                            textToSpeech.speak("The Genes generated through the Crossings were," + homoD + "Percent Homozygous Dominant and," + heteD + "Percent Heterozygous Dominant", TextToSpeech.QUEUE_ADD, null);

                        } else if (homoD > 0 & reces > 0) {
                            textToSpeech.speak("The Genes generated through the Crossings were," + homoD + "Percent Homozygous Dominant and" + reces + "Percent Homozygote Recessive", TextToSpeech.QUEUE_ADD, null);

                        } else if (heteD > 0 & reces > 0) {
                            textToSpeech.speak("The Genes generated through the Crossings were," + heteD + "Heterozygous Percent Dominant and," + reces + "Recessive Homozygous Percent", TextToSpeech.QUEUE_ADD, null);

                        } else if (homoD > 0) {
                            textToSpeech.speak("The Genes generated through Crossings were," + homoD + "Percent Homozygous Dominant.", TextToSpeech.QUEUE_ADD, null);

                        } else if (heteD > 0) {
                            textToSpeech.speak("The Genes generated through Crossings were," + heteD + "Heterozygous Percent Dominant.", TextToSpeech.QUEUE_ADD, null);

                        } else if (reces > 0) {
                            textToSpeech.speak("The Genes generated through the Crossings were," + reces + "Percent Homozygous Recessive.", TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                }else {
                    x--;
                }
            }

            @Override
            public void onSwipeTopE() {
                textToSpeech.speak("Gene, "+ dominate +" Dominate", TextToSpeech.QUEUE_FLUSH, null);
                /** Informando o Cruzamento a ser feito**/
                escolha = dominate;
                escolhafala = "A Dominant";
            }

            @Override
            public void onSwipeBottomE() {
                textToSpeech.speak("Gene, "+dominate+" Dominate", TextToSpeech.QUEUE_FLUSH, null);
                /** Informando o Cruzamento a ser feito**/
                escolha = dominate;
                escolhafala = "A Dominant";
            }

            @Override
            public void onSwipeTopD() {
                textToSpeech.speak("Gene, "+dominate+" Recessivo", TextToSpeech.QUEUE_FLUSH, null);
                /** Informando o Cruzamento a ser feito**/
                escolha = recessivo;
                escolhafala = "a Recessive";
            }
            @Override
            public void onSwipeBottomD() {
                textToSpeech.speak("Gene, "+dominate+" Recessivo", TextToSpeech.QUEUE_FLUSH, null);
                /** Informando o Cruzamento a ser feito**/
                escolha = recessivo;
                escolhafala = "a Recessive";
            }

        });
    }
    @Override
    protected void onDestroy() {
        if(textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(proximitySensorListener);
        textToSpeech.stop();
        if(textToSpeech != null) {
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

