package com.bioblu.second_law;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteTelaTutorial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class activity_tutorial_polialelia extends AppCompatActivity {
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
    private String primeirafala = "Essa é a tela de tutorial do módulo da polialelia" + '\n' + "aqui você aprendera como utilizar os mecanismos de resolução utilizados no aplicativo para questões referentes a polialelia" + '\n' +
            " Para começar iremos efetuar o seguinte cruzamento" + '\n' + "qual o resultado entre o cruzamento de um coelho selvagem com recessividade himalaia, com um coelho chinchila com recessividade albino " + '\n' +
            " para iniciar informaremos os genes parentais..." + '\n' + " agora deslize para cima no lado esquerdo da tela";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_polialelia);
        Bundle dados = getIntent().getExtras();
        velocidade = dados.getInt("velocidade");

        //instância dos textview para todas gerações
        textViewFilho1 = findViewById(R.id.textViewFilho1_tutorial1law);
        textViewFilho2 = findViewById(R.id.textViewFilho2_tutorial1law);
        textViewFilho3 = findViewById(R.id.textViewFilho3_tutorial1law);
        textViewFilho4 = findViewById(R.id.textViewFilho4_tutorial1law);

        textViewP1 = findViewById(R.id.textViewF11_tutorial1law);
        textViewP12 = findViewById(R.id.textViewF12_tutorial1law);

        imageViewP1 = findViewById(R.id.imageViewF11_tutorial1law);
        imageViewP12 = findViewById(R.id.imageViewF12_tutorial1law);


        //instância dos imageview da geração f2
        filho1 = findViewById(R.id.law1_filho1);
        filho2 = findViewById(R.id.law1_filho2);
        filho3 = findViewById(R.id.law1_filho3);
        filho4 = findViewById(R.id.law1_filho4);

        imageViewTutorial = findViewById(R.id.imageViewTutorial);


        textToSpeech = new TextToSpeech(activity_tutorial_polialelia.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
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
        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_tutorial_polialelia);
        Rlayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void onSwipeTopE() {
                if (x == 1 || x == 5 || x == 7) {
                    textToSpeech.speak("Gene Selvagem" + '\n' + "dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "C";
                } else if (x == 2 || x == 10 || x == 11 || x == 3 || x == 6 || x == 9 || x == 4 || x == 8 || x == 12){
                    textToSpeech.speak("Toque inválido, o gene informado está incorreto", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x > 12){
                    textToSpeech.speak("você finalizou a questão de tutorial, para retornar ao menu basta fazer um L ao contrário em qualquer parte da tela.", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onSwipeBottomE() {
                if(x == 2 || x == 10 || x == 11) {
                    textToSpeech.speak("Gene himalaia" + '\n' + "dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "Ch";
                } else if (x == 1 || x == 5 || x == 7 || x == 3 || x == 6 || x == 9 || x == 4 || x == 8 || x == 12) {
                    textToSpeech.speak("Toque inválido, o gene informado está incorreto", TextToSpeech.QUEUE_FLUSH, null);
                }else if (x > 12){
                    textToSpeech.speak("você finalizou a questão de tutorial, para retornar ao menu basta fazer um L ao contrário em qualquer parte da tela.", TextToSpeech.QUEUE_FLUSH, null);
                }


            }

            @Override
            public void onSwipeTopD() {
                if(x == 3 || x == 6 || x == 9) {
                    textToSpeech.speak("Gene chinchila" + '\n' + "dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "CCh";
                } else if (x == 1 || x == 5 || x == 7 || x == 2 || x == 10 || x == 11 || x == 4 || x == 8 || x == 12) {
                    textToSpeech.speak("Toque inválido, o gene informado está incorreto", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x > 12){
                    textToSpeech.speak("você finalizou a questão de tutorial, para retornar ao menu basta fazer um L ao contrário em qualquer parte da tela.", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onSwipeBottomD() {
                if(x == 4 || x == 8 || x == 12) {
                    textToSpeech.speak("Gene albino" + '\n' + "dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolha = "Ca";
                } else if (x == 1 || x == 5 || x == 7 || x == 2 || x == 10 || x == 11 || x == 3 || x == 6 || x == 9) {
                    textToSpeech.speak("Toque inválido, o gene informado está incorreto", TextToSpeech.QUEUE_FLUSH, null);
                } else if (x > 12){
                    textToSpeech.speak("você finalizou a questão de tutorial, para retornar ao menu basta fazer um L ao contrário em qualquer parte da tela.", TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void doubleTap() {
                if (x >= 1 && escolha != null) {
                    if (x == 1) {
                        genep1 = escolha;
                        textToSpeech.speak("Informe mais um gene parental deslizando para baixo no lado esquerdo da tela", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img2);
                    } else if (x == 2) {
                        genep2 = escolha;
                        if (genep1.equals("C") && genep2.equals("Ch")) {
                            imageViewP1.setImageResource(R.drawable.selvagem);
                            textViewP1.setText("C Ch");
                            textToSpeech.speak("O primeiro alelo gerou um coelho selvagem com recessividade himalaia", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("muito bem, agora deslize para cima do lado direito da tela", TextToSpeech.QUEUE_ADD, null);
                            imageViewTutorial.setImageResource(R.drawable.img3);
                        }
                    } else if (x == 3) {
                        genep3 = escolha;
                        textToSpeech.speak("você está indo bem" + '\n' + "informe mais um gene parental deslizando para baixo do lado direito da tela", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 4) {
                        genep4 = escolha;
                        if (genep3.equals("CCh") && genep4.equals("Ca")) {
                            imageViewP12.setImageResource(R.drawable.chinchila);
                            textViewP12.setText("CCh Ca");
                            textToSpeech.speak("O segundo alelo gerou um coelho chinchila com recessividade albino", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("ótimo, agora você irá informar os resultados da geração f2 " + '\n' + " para isso deslize para cima do lado esquerdo da tela", TextToSpeech.QUEUE_ADD, null);
                            imageViewTutorial.setImageResource(R.drawable.img2);
                        }
                    } else if (x == 5) {
                        genef1_1 = escolha;
                        textToSpeech.speak("Informe o segundo gene do lado direito superior da tela", TextToSpeech.QUEUE_ADD, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 6) {
                        genef1_2 = escolha;
                        if (genef1_1.equals("C") && genef1_2.equals("CCh")) {
                            imageViewTutorial.setImageResource(R.drawable.img2);
                            filho1.setImageResource(R.drawable.selvagem);
                            textViewFilho1.setText("C CCh");
                            textToSpeech.speak("O primeiro filho é um coelho selvagem com recessividade chinchila", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("muito bem " + '\n' + "informe o outro gene da geração F2 deslizando para cima do lado esquerdo da tela", TextToSpeech.QUEUE_ADD, null);
                        }
                    } else if (x == 7) {
                        genef1_3 = escolha;
                        textToSpeech.speak("perfeito " + '\n' + " informe o quarto gene da geração f2 no lado direito inferior da tela", TextToSpeech.QUEUE_ADD, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 8) {
                        genef1_4 = escolha;
                        if (genef1_3.equals("C") && genef1_4.equals("Ca")) {
                            filho2.setImageResource(R.drawable.selvagem);
                            textViewFilho2.setText("C Ca");
                            textToSpeech.speak("O segundo filho é um coelho selvagem com recessividade albino", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("muito bem, deslize para cima no lado direito superior da tela", TextToSpeech.QUEUE_ADD, null);
                            imageViewTutorial.setImageResource(R.drawable.img3);
                        } // aqui
                    } else if (x == 9) {
                        c1 = escolha;
                        textToSpeech.speak("muito bem, beslize para baixo do lado esquerdo da tela ", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img2);
                    } else if (x == 10) {
                        d1 = escolha;
                        if (c1.equals("CCh") && d1.equals("Ch")) {
                            filho3.setImageResource(R.drawable.chinchila);
                            textViewFilho3.setText("CCh Ch");
                            textToSpeech.speak("O terceiro filho é um coelho chinchila com recessividade himalaia", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("Deslize mais suma vez para baixo do lado esquerdo da tela", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 11) {
                        c2 = escolha;
                        textToSpeech.speak("Ótimo " + '\n' + " agora deslize para baixo do lado direito da tela", TextToSpeech.QUEUE_FLUSH, null);
                        imageViewTutorial.setImageResource(R.drawable.img3);
                    } else if (x == 12) {
                        d2 = escolha;
                        if (c2.equals("Ch") && d2.equals("Ca")) {
                            filho4.setImageResource(R.drawable.himalaia);
                            textViewFilho4.setText("Ch Ca");
                            imageViewTutorial.setImageResource(R.drawable.img2);
                            textToSpeech.speak("O quarto filho é um coelho himalaia com recessividade albina", TextToSpeech.QUEUE_ADD, null);
                            textToSpeech.speak("perfeito, você finalizou a questão de tutorial, para retornar ao menu basta fazer um L ao contrário em qualquer parte da tela.", TextToSpeech.QUEUE_FLUSH, null);
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
                textToSpeech.speak("Você solicitou a ajuda: toda vez que precisar de ajuda basta segurar na tela por dois segundos", TextToSpeech.QUEUE_FLUSH, null);
            }
            /*ir para o meu */

            @Override
            public void LGesture() {
                Intent intent = new Intent(getApplicationContext(), activity_menu_polialelia.class);
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
