package com.bioblu.first_law;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bioblu.controllers.OuvinteTelaTutorial;
import com.bioblu.R;

import java.util.Locale;

public class activity_tutorial_1law extends AppCompatActivity {
    private TextToSpeech textToSpeech;
    /**
     * Sintetiza a fala do texto para reprodução imediata ou para criar um arquivo de som
     **/
    private TextView textViewFilho1, textViewFilho2, textViewFilho3, textViewFilho4, textViewP1, textViewP12, textViewF1, textViewF12;
    private String d1, c1, d2, c2, d3, c3, d4, c4, genep1, genep2, genep3, genep4, genef1_1, genef1_2, genef1_3, genef1_4;
    public ImageView filho1, filho2, filho3, filho4, imageViewP1, imageViewP12, imageViewF1, imageViewF12;
    public int screenWidth;
    public int screenHeight;
    public int velocidade;
    public ImageView imageViewTutorial;

    /**
     * String com informações de como usar o app
     **/
    private String primeirafala = "This is the tutorial screen of the first law of Mendel, here you will learn how to use the resolution mechanisms used in the application for the first law" +
            "             To start, we will make the following crossing what is the result of crossing pure yellow seed peas with pure green seed peas" +
            "             to start we will inform the parental genes. now swipe up or down on the left side of the screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_1law);
        Bundle dados = getIntent().getExtras();
        velocidade = dados.getInt("velocidade");

        //instância dos textview para todas gerações
        textViewFilho1 = findViewById(R.id.textViewFilho1_tutorial1law);
        textViewFilho2 = findViewById(R.id.textViewFilho2_tutorial1law);
        textViewFilho3 = findViewById(R.id.textViewFilho3_tutorial1law);
        textViewFilho4 = findViewById(R.id.textViewFilho4_tutorial1law);

        textViewP1 = findViewById(R.id.textViewParental_tutorial1law);
        textViewP12 = findViewById(R.id.textViewParental2_tutorial1law);
        textViewF1 = findViewById(R.id.textViewF11_tutorial1law);
        textViewF12 = findViewById(R.id.textViewF12_tutorial1law);

        //instância dos imageview da geração parental e f1
        imageViewP1 = findViewById(R.id.imageViewParental_tutorial1law);
        imageViewP12 = findViewById(R.id.imageViewParental2_tutorial1law);
        imageViewF1 = findViewById(R.id.imageViewF11_tutorial1law);
        imageViewF12 = findViewById(R.id.imageViewF12_tutorial1law);

        //instância dos imageview da geração f2
        filho1 = findViewById(R.id.law1_filho1);
        filho2 = findViewById(R.id.law1_filho2);
        filho3 = findViewById(R.id.law1_filho3);
        filho4 = findViewById(R.id.law1_filho4);

        imageViewTutorial = findViewById(R.id.imageViewTutorial);


        textToSpeech = new TextToSpeech(activity_tutorial_1law.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("en", "US"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.setPitch(1);
                    textToSpeech.speak(primeirafala, textToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        /** Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque**/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        initTela();
    }

    /**
     * METODOS AQUI
     **/
    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_tutorial1law);
        Rlayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void onSwipeTopE() {
                if (x == 1) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 2) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 3 || x == 4) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 5) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 6) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 7) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 8) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 9) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 10) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 11) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 12) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 13) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 14) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 15) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 16) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x > 16) {
                    textToSpeech.speak("Make an reverse  “L” to go to the menu", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onSwipeBottomE() {
                if (x == 1) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 2) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 3 || x == 4) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 5) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 6) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 7) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 8) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 9) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 10) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 11) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 12) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 13) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 14) {
                    textToSpeech.speak("Dominant V gene, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "V";
                } else if (x == 15) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 16) {
                    textToSpeech.speak("You must swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x > 16) {
                    textToSpeech.speak("Make an reverse  “L” to go to the menu", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onSwipeTopD() {
                if (x == 1 || x == 2) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 3) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 4) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 5) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 6) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 7) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 8) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 9) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 10) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 11) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 12) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 13) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 14) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 15) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 16) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x > 16) {
                    textToSpeech.speak("Make an reverse  “L” to go to the menu", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onSwipeBottomD() {
                if (x == 1 || x == 2) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 3) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 4) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 5) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 6) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 7) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 8) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 9) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 10) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 11) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 12) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 13) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 14) {
                    textToSpeech.speak("You must swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x == 15) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x == 16) {
                    textToSpeech.speak("Gene v recessive. double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "v";
                } else if (x > 16) {
                    textToSpeech.speak("Make an reverse  “L” to go to the menu", TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void doubleTap() { //imformar genes VV (amarela) x vv (verde)
                if (x >= 1 && escolha != null) {
                    if (x == 1) {
                        imageViewTutorial.setImageResource(R.drawable.img2);
                        genep1 = escolha;
                        textToSpeech.speak("Enter one more parental gene by swiping up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                    } else if (x == 2) {
                        genep2 = escolha;
                        imageViewTutorial.setImageResource(R.drawable.img2);
                        if (genep1.equals("V") && genep2.equals("V")) {
                            imageViewP1.setImageResource(R.drawable.quadrado_normal);
                            textViewP1.setText("VV");
                            textToSpeech.speak("Dominant Homozygous Gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("You entered the first pair. now swipe up or down on the right side of the screen", TextToSpeech.QUEUE_ADD, null);
                            imageViewTutorial.setImageResource(R.drawable.img2);
                        }
                    } else if (x == 3) {
                        genep3 = escolha;
                        textToSpeech.speak("you're doing well. enter another parental gene by swiping up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 4) {
                        genep4 = escolha;
                        imageViewTutorial.setImageResource(R.drawable.img3);
                        if (genep3.equals("v") && genep4.equals("v")) {
                            imageViewP12.setImageResource(R.drawable.quadrado_preenchido);
                            textViewP12.setText("vv");
                            textToSpeech.speak("Recessive Homozygous Gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("great, now you will report the results of the f1 generation. to do this swipe up or down on the left side of the screen", TextToSpeech.QUEUE_ADD, null);
                            imageViewTutorial.setImageResource(R.drawable.img2);
                        }
                    } else if (x == 5) {
                        genef1_1 = escolha;
                        textToSpeech.speak("Enter the second gene on the right side of the screen", TextToSpeech.QUEUE_ADD, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 6) {
                        genef1_2 = escolha;
                        if (genef1_1.equals("V") && genef1_2.equals("v")) {
                            imageViewTutorial.setImageResource(R.drawable.img3);
                            imageViewF1.setImageResource(R.drawable.quadrado_normal);
                            textViewF1.setText("Vv");
                            textToSpeech.speak("Dominant heterozygous gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("very well. inform the other F1 generation gene by swiping up or down on the right side of the screen", TextToSpeech.QUEUE_ADD, null);
                        }
                    } else if (x == 7) {
                        genef1_3 = escolha;
                        textToSpeech.speak("perfect. enter the second gene of generation f1 on the right side of the screen", TextToSpeech.QUEUE_ADD, null);
                    } else if (x == 8) {
                        genef1_4 = escolha;
                        if (genef1_3.equals("V") && genef1_4.equals("v")) {
                            imageViewF12.setImageResource(R.drawable.quadrado_normal);
                            textViewF12.setText("Vv");
                            textToSpeech.speak("Dominant heterozygous gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("very well, you informed the genes of the parental generation f1. now inform the result of the generation F2. to start slide on the left side of the screen", TextToSpeech.QUEUE_ADD, null);
                            imageViewTutorial.setImageResource(R.drawable.img2);
                        }
                    } else if (x == 9) {
                        c1 = escolha;
                        textToSpeech.speak("Swipe up or down on the left side of the screen ", TextToSpeech.QUEUE_FLUSH, null);
                    } else if (x == 10) {
                        d1 = escolha;
                        if (c1.equals("V") && d1.equals("V")) {
                            filho1.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho1.setText("VV");
                            textToSpeech.speak("Dominant Homozygous Gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("Swipe up or down one more time on the left side of the screen ", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 11) {
                        imageViewTutorial.setImageResource(R.drawable.img3);
                        c2 = escolha;
                        textToSpeech.speak("Great!, Now swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);

                    } else if (x == 12) {
                        d2 = escolha;
                        if (c2.equals("v") && d2.equals("V") || c2.equals("V") && d2.equals("v")) {
                            filho2.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho2.setText("Vv");
                            imageViewTutorial.setImageResource(R.drawable.img2);
                            textToSpeech.speak("Dominant heterozygous gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("Perfect!, Now swipe up or down on the left side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 13) {
                        c3 = escolha;
                        textToSpeech.speak("you're doing well. Swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 14) {
                        d3 = escolha;
                        if (c3.equals("v") && d3.equals("V") || c3.equals("V") && d3.equals("v")) {
                            filho3.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho3.setText("Vv");
                            textToSpeech.speak("Dominant heterozygous gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("Swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else if (x == 15) {
                        c4 = escolha;
                        textToSpeech.speak("Very Good! Swipe up or down on the right side of the screen", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 16) {
                        d4 = escolha;
                        if (c4.equals("v") && d4.equals("v")) {
                            filho4.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho4.setText("vv");
                            textToSpeech.speak("Recessive Homozygous Gene", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("You have completed the question and finished the setting successfully, to proceed make an reverse  “L” on any part of the screen", TextToSpeech.QUEUE_FLUSH, null);
                            x = 17;
                        }
                    }
                } else {
                    System.out.println("print");
                    x = x - 1;
                }
            }

            /*dica da questão*/
            @Override
            public void onLongPressTutorial() {
                textToSpeech.speak("You asked for help: every time you need help, just press the screen for two seconds", TextToSpeech.QUEUE_FLUSH, null);
            }
            /*ir para o meu */

            @Override
            public void LGesture() {
                Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                startActivity(intent);
                finish();
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
        textToSpeech.stop();
        super.onPause();
    }

}
