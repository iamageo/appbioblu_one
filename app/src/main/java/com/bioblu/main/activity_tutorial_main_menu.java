package com.bioblu.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bioblu.R;
import com.bioblu.controllers.OuvinteTelaTutorial;

import java.util.Locale;

public class activity_tutorial_main_menu extends AppCompatActivity {

    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 3, l5 = 4;
    private int i = -1;
    private int ix = -1;
    private TextView lista1, lista2, lista3, lista4, lista5;
    private String[] opcao;
    TextView[] cursor = new TextView[5];
    private TextToSpeech textToSpeech; /** Sintetiza a fala do texto para reprodução imediata ou para criar um arquivo de som **/
    public int screenWidth;
    public int screenHeight;
    public int velocidade;
    public ImageView imageViewTutorial;

    String tutorial_1;
    String tutorial_2;
    String tutorial_3;
    String tutorial_4;
    String tutorial_5;
    String tutorial_6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_main_menu);
        Bundle dados = getIntent().getExtras();
        velocidade = dados.getInt("velocidade");

        tutorial_1 = getString(R.string.tutorial_1);
        tutorial_2 = getString(R.string.tutorial_2);
        tutorial_3 = getString(R.string.tutorial_3);
        tutorial_4 = getString(R.string.tutorial_4);
        tutorial_5 = getString(R.string.tutorial_5);
        tutorial_6 = getString(R.string.tutorial_6);

        opcao = new String[]{tutorial_2,
                tutorial_3,
                tutorial_4,
                tutorial_5,
                tutorial_6};


        lista1 = findViewById(R.id.textView_option1);
        lista2 = findViewById(R.id.textView_option2);
        lista3 = findViewById(R.id.textView_option3);
        lista4 = findViewById(R.id.textView_option4);
        lista5 = findViewById(R.id.textView_option5);

        imageViewTutorial = findViewById(R.id.imageViewTutorial);

        textToSpeech = new TextToSpeech(activity_tutorial_main_menu.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.setPitch(1);
                    textToSpeech.speak(tutorial_1, textToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        /** Pega o Tamanho da tela do Celular Para a Classe OuvinteDeToque**/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        cursor[0] = lista1;
        cursor[1] = lista2;
        cursor[2] = lista3;
        cursor[3] = lista4;
        cursor[4] = lista5;

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        initTela();
    }

    /** METODOS AQUI **/
    @SuppressLint("ClickableViewAccessibility")
    private void initTela(){
        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_main_tutorial);
        Rlayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {


            @Override
            public void LGesture() {
                //verificar se fez duas vezes
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
                finish();
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
                lista1.setText(String.valueOf(opcao[l1 + yx]));
                lista2.setText(String.valueOf(opcao[l2 + yx]));
                lista3.setText(String.valueOf(opcao[l3 + yx]));
                lista4.setText(String.valueOf(opcao[l4 + yx]));
                lista5.setText(String.valueOf(opcao[l5 + yx]));
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