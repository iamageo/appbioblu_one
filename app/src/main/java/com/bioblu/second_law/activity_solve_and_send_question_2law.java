package com.bioblu.second_law;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteTelaTutorial;

import static com.bioblu.main.activity_voiceRate.FILE_NAME;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class activity_solve_and_send_question_2law extends AppCompatActivity {
    private TextView textView1, textView2, textView3, textView4, textView5,
            textView6, textView7, textView8, textView9, textView10, textView11,
            textView12, textView13, textView14, textView15, textView16;
    private TextView  textViewF2_1, textViewF2_2, textViewF2_3, textViewF2_4, textViewP1, textViewP2, TextViewParental1, TextViewParental2;
    private ImageView imageViewF2_1, imageViewF2_2, imageViewF2_3, imageViewF2_4, imageViewP1, imageViewP2;
    public int screenWidth;

    private String [] genes = new String[14];
    private String gene;
    private String questao_recebida;
    private TextView textView_questao;
    private String resposta_formatada;
    private String email;

    private String aux0 = "", aux01 = "";

    private String [] nomes = {"LINHA 1, COLUNA 1", "LINHA 1, COLUNA 2", "LINHA 1, COLUNA 3", "LINHA 1, COLUNA 4",
            "LINHA 2, COLUNA 1", "LINHA 2, COLUNA 2", "LINHA 2, COLUNA 3", "LINHA 2, COLUNA 4",
            "LINHA 3, COLUNA 1", "LINHA 3, COLUNA 2", "LINHA 3, COLUNA 3", "LINHA 3, COLUNA 4",
            "LINHA 4, COLUNA 1", "LINHA 4, COLUNA 2", "LINHA 4, COLUNA 3", "LINHA 4, COLUNA 4"};
    private String [] classificacao = new String[16];

    private String [] genesParental = new String[8];
    private String [] geneP = new String[4];
    private String [] resultadoGene = new String[8];
    private String [] resultado = new String[4];
    private String [] valor_matriz = new String[32];
    private String [] lista_genes = new String[16];
    private TextToSpeech textToSpeech;
    String gene_selecionado;
    String [] gene_selecionado1 = new String[4];
    public int velocidade;

    private int yx = 0, l1 = 0, l2 = 1, l3 = 2, l4 = 3, l5 = 4, l6 = 5, l7 = 6, l8 = 7, l9 = 8,
            l10 = 9, l11 = 10, l12 = 11, l13 = 12, l14 = 13, l15 = 14;
    private int i = -1;
    private int ix = -1;
    private int fx = -1;
    TextView[] cursor =  new TextView[16];

    private String [][] matriz_resultado = new String[4][4];
    private String [][] matriz_final = new String[4][4];

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    private String id_dispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_and_send_question_2law);
        Bundle dados = getIntent().getExtras();

        questao_recebida = dados.getString("questao");
        email = dados.getString("email");
        gene = dados.getString("letra");
        genes = gene.split(", ");

        aux0 = genes[0] + genes[1] + genes[2] + genes[3];
        aux01 =  genes[4] + genes[5] + genes[6] + genes[7];

        inicializaComponentes();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_solve_and_send_question_2law.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ler_velocidade();
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("pt", "BR"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak(questao_recebida+'\n'+"com base nos dados da questão, informe os genes da geração f1", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        //recupera o id para enviar junto com a resposta
        id_dispositivo = Settings.Secure.getString(
                getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);


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

        //questão e alelos parentais recebidos
        TextViewParental1.setText(aux0);
        TextViewParental2.setText(aux01);

        textView_questao.setText(questao_recebida);

        initTela();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayout_solve_and_send2);
        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu_2law.class);
                startActivity(intent);
            }

            @Override
            public void onSwipeTopE() {
                if (gene_x >= 0 && gene_x < 28) {
                    escolhaGeneP = genes[0].toUpperCase();
                    textToSpeech.speak("Gene " + genes[0] + " dominante dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolhafala = genes[0].toUpperCase();
                }  else if (gene_x > 27) {
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
                    textToSpeech.speak(nomes[i] + classificacao[i], TextToSpeech.QUEUE_FLUSH, null);
                }

            }

            @Override
            public void onSwipeBottomE() {
                if (gene_x >= 0 && gene_x < 28) {
                    escolhaGeneP = genes[2].toUpperCase();
                    textToSpeech.speak("Gene " + genes[2] + " dominante dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolhafala = genes[2].toUpperCase();
                } else if (gene_x > 27) {
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
                    textToSpeech.speak(nomes[i] + classificacao[i], TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onSwipeTopD() {
                if (gene_x >= 0 && gene_x < 28) {
                    escolhaGeneP = genes[0].toLowerCase();
                    textToSpeech.speak("Gene " +  genes[0] + " recessivo dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolhafala =  genes[0].toLowerCase();
                } else if (gene_x > 27) {
                    if (i == lista_genes.length - 1) {
                        i = lista_genes.length - 1;

                    } else if (ix == 15 & i <= lista_genes.length) {
                        yx++;
                        i++;
                        caminhar();
                    } else if (i < lista_genes.length) {
                        i++;
                        ix++;
                    }
                    cursor[ix].setBackgroundResource(R.drawable.linha_horizontal);
                    textToSpeech.speak(nomes[i] + classificacao[i], TextToSpeech.QUEUE_FLUSH, null);
                    if (ix > 0) {
                        cursor[ix - 1].setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }

            @Override
            public void onSwipeBottomD() {
                if (gene_x >= 0 && gene_x < 28) {
                    escolhaGeneP = genes[2].toLowerCase();
                    textToSpeech.speak("Gene " +  genes[2] + " recessivo dê dois toques para confirmar", TextToSpeech.QUEUE_FLUSH, null);
                    escolhafala =  genes[2].toLowerCase();
                } else if (gene_x > 27) {
                    if (i == lista_genes.length - 1) {
                        i = lista_genes.length - 1;

                    } else if (ix == 15 & i <= lista_genes.length) {
                        yx++;
                        i++;
                        caminhar();
                    } else if (i < lista_genes.length) {
                        i++;
                        ix++;
                    }
                    cursor[ix].setBackgroundResource(R.drawable.linha_horizontal);

                    textToSpeech.speak(nomes[i] + classificacao[i], TextToSpeech.QUEUE_FLUSH, null);

                    if (ix > 0) {
                        cursor[ix - 1].setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }


            @Override
            public void doubleTap() {
                if (escolhaGeneP != null) {
                    textToSpeech.speak("Confirmado Gene " + escolhafala, TextToSpeech.QUEUE_FLUSH, null);
                    if (gene_x == 0) {
                        geneP[0] = escolhaGeneP;
                        textToSpeech.speak("Informe o segundo gene da geração f1 ", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 1) {
                        geneP[1] = escolhaGeneP;

                        if (geneP[0].equals(geneP[0].toUpperCase()) && geneP[1].equals(geneP[1].toUpperCase())) {
                            imageViewP1.setImageResource(R.drawable.quadrado_normal);
                            textViewP1.setText(geneP[0] + geneP[1]);
                        } else if (geneP[0].equals(geneP[0].toUpperCase()) && geneP[1].equals(geneP[1].toLowerCase())) {
                            imageViewP1.setImageResource(R.drawable.quadrado_normal);
                            textViewP1.setText(geneP[0] + geneP[1]);
                        } else if (geneP[0].equals(geneP[0].toLowerCase()) && geneP[1].equals(geneP[1].toUpperCase())) {
                            imageViewP1.setImageResource(R.drawable.quadrado_normal);
                            textViewP1.setText(geneP[0] + geneP[1]);
                        } else {
                            imageViewP1.setImageResource(R.drawable.quadrado_preenchido);
                            textViewP1.setText(geneP[0] + geneP[1]);
                        }


                        textToSpeech.speak("Informe o terceiro gene da geração f1 ", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 2) {
                        geneP[2] = escolhaGeneP;

                        textToSpeech.speak("Informe o quarto gene da geração f1 ", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 3) {
                        geneP[3] = escolhaGeneP;

                        if (geneP[2].equals(geneP[2].toUpperCase()) && geneP[3].equals(geneP[3].toUpperCase())) {
                            imageViewP2.setImageResource(R.drawable.quadrado_normal);
                            textViewP2.setText(geneP[2] + geneP[3]);
                        } else if (geneP[2].equals(geneP[2].toUpperCase()) && geneP[3].equals(geneP[3].toLowerCase())) {
                            imageViewP2.setImageResource(R.drawable.quadrado_normal);
                            textViewP2.setText(geneP[2] + geneP[3]);
                        } else if (geneP[2].equals(geneP[2].toLowerCase()) && geneP[3].equals(geneP[3].toUpperCase())) {
                            imageViewP2.setImageResource(R.drawable.quadrado_normal);
                            textViewP2.setText(geneP[2] + geneP[3]);
                        } else {
                            imageViewP2.setImageResource(R.drawable.quadrado_preenchido);
                            textViewP2.setText(geneP[2] + geneP[3]);
                        }

                        textToSpeech.speak("Muito bem, informe o primeiro gene da geração F2 ", TextToSpeech.QUEUE_ADD, null);
                    }

                    if (gene_x == 4) {
                        resultadoGene[0] = escolhaGeneP;
                        escolhaGeneP = null;
                        textToSpeech.speak("Informe o segundo gene da geração f2 ", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 5) {
                        resultadoGene[1] = escolhaGeneP;
                        escolhaGeneP = null;
                        resultado[0] = resultadoGene[0] + resultadoGene[1];
                        textViewF2_1.setText(resultado[0]);
                        if (resultadoGene[0].equals(resultadoGene[0].toUpperCase()) && resultadoGene[1].equals(resultadoGene[1].toUpperCase())) {
                            gene_selecionado1[0] = "Homozigoto Dominante";
                            imageViewF2_1.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[0].equals(resultadoGene[0].toUpperCase()) && resultadoGene[1].equals(resultadoGene[1].toLowerCase())) {
                            gene_selecionado1[0] = "Heterozigoto";
                            imageViewF2_1.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[0].equals(resultadoGene[0].toLowerCase()) && resultadoGene[1].equals(resultadoGene[1].toUpperCase())) {
                            gene_selecionado1[0] = "Heterozigoto";
                            imageViewF2_1.setImageResource(R.drawable.quadrado_normal);
                        } else {
                            gene_selecionado1[0] = "Homozigoto recessivo";
                            imageViewF2_1.setImageResource(R.drawable.quadrado_preenchido);
                        }
                        textToSpeech.speak("Informe o terceiro gene da geração f2 ", TextToSpeech.QUEUE_ADD, null);

                    } else if (gene_x == 6) {

                        resultadoGene[2] = escolhaGeneP;
                        escolhaGeneP = null;
                        textToSpeech.speak("Informe o quarto gene da geração f2 ", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 7) {
                        resultadoGene[3] = escolhaGeneP;
                        escolhaGeneP = null;
                        resultado[1] = resultadoGene[2] + resultadoGene[3];
                        textViewF2_2.setText(resultado[1]);
                        if (resultadoGene[2].equals(resultadoGene[2].toUpperCase()) && resultadoGene[3].equals(resultadoGene[3].toUpperCase())) {
                            gene_selecionado1[1] = "Homozigoto Dominante";
                            imageViewF2_2.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[2].equals(resultadoGene[2].toUpperCase()) && resultadoGene[3].equals(resultadoGene[3].toLowerCase())) {
                            gene_selecionado1[1] = "Heterozigoto";
                            imageViewF2_2.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[2].equals(resultadoGene[2].toLowerCase()) && resultadoGene[3].equals(resultadoGene[3].toUpperCase())) {
                            gene_selecionado1[1] = "Heterozigoto";
                            imageViewF2_2.setImageResource(R.drawable.quadrado_normal);
                        } else {
                            gene_selecionado1[1] = "Homozigoto recessivo";
                            imageViewF2_2.setImageResource(R.drawable.quadrado_preenchido);
                        }
                        textToSpeech.speak("Informe o quinto gene da geração f2 ", TextToSpeech.QUEUE_ADD, null);

                    } else if (gene_x == 8) {

                        resultadoGene[4] = escolhaGeneP;
                        escolhaGeneP = null;
                        textToSpeech.speak("Informe o sexto gene da geração f2 ", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 9) {
                        resultadoGene[5] = escolhaGeneP;
                        escolhaGeneP = null;
                        resultado[2] = resultadoGene[4] + resultadoGene[5];
                        textViewF2_3.setText(resultado[2]);
                        if (resultadoGene[4].equals(resultadoGene[4].toUpperCase()) && resultadoGene[5].equals(resultadoGene[5].toUpperCase())) {
                            gene_selecionado1[2] = "Homozigoto Dominante";
                            imageViewF2_3.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[4].equals(resultadoGene[4].toUpperCase()) && resultadoGene[5].equals(resultadoGene[5].toLowerCase())) {
                            gene_selecionado1[2] = "Heterozigoto";
                            imageViewF2_3.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[4].equals(resultadoGene[4].toLowerCase()) && resultadoGene[5].equals(resultadoGene[5].toUpperCase())) {
                            gene_selecionado1[2] = "Heterozigoto";
                            imageViewF2_3.setImageResource(R.drawable.quadrado_normal);
                        } else {
                            gene_selecionado1[2] = "Homozigoto recessivo";
                            imageViewF2_3.setImageResource(R.drawable.quadrado_preenchido);
                        }
                        textToSpeech.speak("Informe o sétimo gene da geração f2", TextToSpeech.QUEUE_ADD, null);

                    } else if (gene_x == 10) {

                        resultadoGene[6] = escolhaGeneP;
                        escolhaGeneP = null;
                        textToSpeech.speak("Informe o último gene da geração f2", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 11) {
                        resultadoGene[7] = escolhaGeneP;
                        escolhaGeneP = null;
                        resultado[3] = resultadoGene[6] + resultadoGene[7];
                        textViewF2_4.setText(resultado[3]);
                        if (resultadoGene[6].equals(resultadoGene[6].toUpperCase()) && resultadoGene[7].equals(resultadoGene[7].toUpperCase())) {
                            gene_selecionado1[3] = "Homozigoto Dominante";
                            imageViewF2_4.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[6].equals(resultadoGene[6].toUpperCase()) && resultadoGene[7].equals(resultadoGene[7].toLowerCase())) {
                            gene_selecionado1[3] = "Heterozigoto";
                            imageViewF2_4.setImageResource(R.drawable.quadrado_normal);
                        } else if (resultadoGene[6].equals(resultadoGene[6].toLowerCase()) && resultadoGene[7].equals(resultadoGene[7].toUpperCase())) {
                            gene_selecionado1[3] = "Heterozigoto";
                            imageViewF2_4.setImageResource(R.drawable.quadrado_normal);
                        } else {
                            gene_selecionado1[3] = "Homozigoto recessivo";
                            imageViewF2_4.setImageResource(R.drawable.quadrado_preenchido);
                        }

                        /* tratar matriz resultado */
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                matriz_final[i][j] = resultado[i] + resultado[j];
                            }
                        }

                        textToSpeech.speak("Perfeito, você vai popular o quadro de Punnett de acordo com os resultados da geração F2" + '\n' + "lembrando, o quadro deve ser preenchido com os genes da geração f2, vamos nessa, informe o primeiro gene", TextToSpeech.QUEUE_ADD, null);
                        // colocar os genes gerados aqui
                        /** Quadro de Punnet aqui **/
                    } else if (gene_x == 12) { /* linha 0, coluna 0*/
                        valor_matriz[0] = escolhaGeneP;
                        textToSpeech.speak("perfeito, informe o segundo gene", TextToSpeech.QUEUE_ADD, null);
                    } else  if (gene_x == 13) {
                        valor_matriz[1] = escolhaGeneP;
                        matriz_resultado[0][0] = valor_matriz[0] + valor_matriz[1];
                        textToSpeech.speak("perfeito, informe o terceiro gene", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 14) { /* linha 0, coluna 1*/
                        valor_matriz[2] = escolhaGeneP;
                        textToSpeech.speak("perfeito, informe o quarto gene", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 15) {
                        valor_matriz[3] = escolhaGeneP;
                        lista_genes[0] = valor_matriz[0] + valor_matriz[1] + valor_matriz[2] + valor_matriz[3];
                        matriz_resultado[0][1] = valor_matriz[2] + valor_matriz[3];
                        textView1.setText(lista_genes[0]); // final primeiro alelo

                        textToSpeech.speak("muito bem, agora informe o primeiro gene do segundo alelo", TextToSpeech.QUEUE_ADD, null);

                    } else if (gene_x == 16) { /* linha 0, coluna 1*/
                        valor_matriz[4] = escolhaGeneP;
                        textToSpeech.speak("ótimo, agora informe o segundo gene do segundo alelo", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 17) {
                        textToSpeech.speak("ótimo, agora informe o terceiro gene do segundo alelo", TextToSpeech.QUEUE_ADD, null);
                        valor_matriz[5] = escolhaGeneP;
                        escolhaGeneP = null;
                        matriz_resultado[0][2] = valor_matriz[4] + valor_matriz[5];


                    } else if (gene_x == 18) { /* linha 0, coluna 2*/
                        textToSpeech.speak("ótimo, agora informe o quarto gene do segundo alelo", TextToSpeech.QUEUE_ADD, null);
                        valor_matriz[6] = escolhaGeneP;
                        escolhaGeneP = null;
                    } else if (gene_x == 19) {
                        textToSpeech.speak("muito bem, agora informe o primeiro gene do terceiro alelo.", TextToSpeech.QUEUE_ADD, null);
                        valor_matriz[7] = escolhaGeneP;
                        lista_genes[1] = valor_matriz[4] + valor_matriz[5] + valor_matriz[6] + valor_matriz[7];
                        matriz_resultado[0][3] = valor_matriz[6] + valor_matriz[7];
                        textView2.setText(lista_genes[1]);

                   }  else if (gene_x == 20) { /* linha 0, coluna 3*/
                        valor_matriz[8] = escolhaGeneP;
                        textToSpeech.speak("perfeito, informe o segundo gene do terceiro alelo", TextToSpeech.QUEUE_ADD, null);
                    } else  if (gene_x == 21) {
                        valor_matriz[9] = escolhaGeneP;
                        matriz_resultado[1][0] = valor_matriz[8] + valor_matriz[9];
                        textToSpeech.speak("perfeito, informe o terceiro gene do terceiro alelo", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 22) { /* linha 0, coluna 1*/
                        valor_matriz[10] = escolhaGeneP;
                        textToSpeech.speak("perfeito, informe o quarto gene do terceiro alelo", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 23) {
                        valor_matriz[11] = escolhaGeneP;
                        lista_genes[2] = valor_matriz[8] + valor_matriz[9] + valor_matriz[10] + valor_matriz[11];
                        matriz_resultado[1][1] = valor_matriz[2] + valor_matriz[3];
                        textView3.setText(lista_genes[2]); // final primeiro alelo

                        textToSpeech.speak("muito bem, agora informe o primeiro gene do quarto alelo", TextToSpeech.QUEUE_ADD, null);

                    }else if (gene_x == 24) { /* linha 0, coluna 4*/
                        valor_matriz[12] = escolhaGeneP;
                        textToSpeech.speak("ótimo, agora informe o segundo gene do quarto alelo", TextToSpeech.QUEUE_ADD, null);
                    } else if (gene_x == 25) {
                        textToSpeech.speak("ótimo, agora informe o terceiro gene do quarto alelo", TextToSpeech.QUEUE_ADD, null);
                        valor_matriz[13] = escolhaGeneP;
                        escolhaGeneP = null;
                        matriz_resultado[1][2] = valor_matriz[12] + valor_matriz[13];


                    } else if (gene_x == 26) { /* linha 0, coluna 2*/
                        textToSpeech.speak("ótimo, agora informe o quarto gene do quarto alelo", TextToSpeech.QUEUE_ADD, null);
                        valor_matriz[14] = escolhaGeneP;
                        escolhaGeneP = null;
                    } else if (gene_x == 27) {
                        valor_matriz[15] = escolhaGeneP;
                        lista_genes[3] = valor_matriz[12] + valor_matriz[13] + valor_matriz[14] + valor_matriz[15];
                        matriz_resultado[1][3] = valor_matriz[14] + valor_matriz[15];
                        textView4.setText(lista_genes[3]);

                        textToSpeech.speak("perfeito, como o processo de preenchimento é muito demorado evimos que aprendeu, iremos conpletar o quadro para você" + '\n' + "para isso basta tocar duas vezes na tela", TextToSpeech.QUEUE_ADD, null);

                    } else if (gene_x == 28) {

                        textView5.setText(matriz_final[1][0]);
                        textView6.setText(matriz_final[1][1]);
                        textView7.setText(matriz_final[1][2]);
                        textView8.setText(matriz_final[1][3]);
                        textView9.setText(matriz_final[2][0]);
                        textView10.setText(matriz_final[2][1]);
                        textView11.setText(matriz_final[2][2]);
                        textView12.setText(matriz_final[2][3]);
                        textView13.setText(matriz_final[3][0]);
                        textView14.setText(matriz_final[3][1]);
                        textView15.setText(matriz_final[3][2]);
                        textView16.setText(matriz_final[3][3]);

                        lista_genes[4] = matriz_final[1][0];
                        lista_genes[5] = matriz_final[1][1];
                        lista_genes[6] = matriz_final[1][2];
                        lista_genes[7] = matriz_final[1][3];
                        lista_genes[8] = matriz_final[2][0];
                        lista_genes[9] = matriz_final[2][1];
                        lista_genes[10] = matriz_final[2][2];
                        lista_genes[11] = matriz_final[2][3];
                        lista_genes[12] = matriz_final[3][0];
                        lista_genes[13] = matriz_final[3][1];
                        lista_genes[14] = matriz_final[3][2];
                        lista_genes[15] = matriz_final[3][3];

                        resposta_formatada = lista_genes[0] + ", " + lista_genes[1] + ", " + lista_genes[2] + ", " + lista_genes[3] + "\n" +
                                lista_genes[4] + ", " + lista_genes[4] + ", " + lista_genes[6] + ", " + lista_genes[7] + "\n" +
                                lista_genes[8] + ", " + lista_genes[9] + ", " + lista_genes[10] + ", " + lista_genes[11] + "\n" +
                                lista_genes[12] + ", " + lista_genes[13] + ", " + lista_genes[14] + ", " + lista_genes[15];

                        classificacao[0] = gene_selecionado1[0] + " e " + gene_selecionado1[0];
                        classificacao[1] = gene_selecionado1[0] + " e " + gene_selecionado1[1];
                        classificacao[2] = gene_selecionado1[0] + " e " + gene_selecionado1[2];
                        classificacao[3] = gene_selecionado1[0] + " e " + gene_selecionado1[3];
                        classificacao[4] = gene_selecionado1[1] + " e " + gene_selecionado1[0];
                        classificacao[5] = gene_selecionado1[1] + " e " + gene_selecionado1[1];
                        classificacao[6] = gene_selecionado1[1] + " e " + gene_selecionado1[2];
                        classificacao[7] = gene_selecionado1[1] + " e " + gene_selecionado1[3];
                        classificacao[8] = gene_selecionado1[2] + " e " + gene_selecionado1[0];
                        classificacao[9] = gene_selecionado1[2] + " e " + gene_selecionado1[1];
                        classificacao[10] = gene_selecionado1[2] + " e " + gene_selecionado1[2];
                        classificacao[11] = gene_selecionado1[2] + " e " + gene_selecionado1[3];
                        classificacao[12] = gene_selecionado1[3] + " e " + gene_selecionado1[0];
                        classificacao[13] = gene_selecionado1[3] + " e " + gene_selecionado1[1];
                        classificacao[14] = gene_selecionado1[3] + " e " + gene_selecionado1[2];
                        classificacao[15] = gene_selecionado1[3] + " e " + gene_selecionado1[3];

                        textToSpeech.speak("Para submeter a sua resposta clique e segure na tela por 2 segundos" + '\n' + "para escutar o resultado obtido basta deslizar para cima ou para baixo para navegar entre os itens "+'\n'+ "caso queira retornar ao menu faça um L ao contrário", TextToSpeech.QUEUE_ADD, null);

                    }
                }
            }

            @Override
            public void caminhar() {
                textView1.setText(String.valueOf(lista_genes[l1 + yx]));
                textView2.setText(String.valueOf(lista_genes[l2 + yx]));
                textView3.setText(String.valueOf(lista_genes[l3 + yx]));
                textView4.setText(String.valueOf(lista_genes[l4 + yx]));
                textView5.setText(String.valueOf(lista_genes[l5 + yx]));
                textView6.setText(String.valueOf(lista_genes[l6 + yx]));
                textView7.setText(String.valueOf(lista_genes[l7 + yx]));
                textView8.setText(String.valueOf(lista_genes[l8 + yx]));
                textView9.setText(String.valueOf(lista_genes[l9 + yx]));
                textView10.setText(String.valueOf(lista_genes[l10 + yx]));
                textView11.setText(String.valueOf(lista_genes[l11 + yx]));
                textView12.setText(String.valueOf(lista_genes[l12 + yx]));
                textView13.setText(String.valueOf(lista_genes[l13 + yx]));
                textView14.setText(String.valueOf(lista_genes[l14 + yx]));
                textView15.setText(String.valueOf(lista_genes[l15 + yx]));
            }

            @Override
            public void onLongPressTutorial() {
                enviarResposta("http://200.239.66.35/bioblu/inserir.php");
            }
        });
    }


    private void inicializaComponentes() {
        //instancia pais
        TextViewParental1 = findViewById(R.id.textView_pai);
        TextViewParental2 = findViewById(R.id.textView_mae);

        //instância dos elementos F1
        textViewP1 = findViewById(R.id.textViewP1);
        textViewP2 = findViewById(R.id.textViewP2);
        imageViewP1 = findViewById(R.id.imageViewP1);
        imageViewP2 = findViewById(R.id.imageViewP2);

        //INSTANCIA QUESTÃO
        textView_questao = findViewById(R.id.textView_questao2law);

        //instância dos elementos f2
        textViewF2_1 = findViewById(R.id.textViewF2_1);
        textViewF2_2 = findViewById(R.id.textViewF2_2);
        textViewF2_3 = findViewById(R.id.textViewF2_3);
        textViewF2_4 = findViewById(R.id.textViewF2_4);
        imageViewF2_1 = findViewById(R.id.imageViewF2_1);
        imageViewF2_2 = findViewById(R.id.imageViewF2_2);
        imageViewF2_3 = findViewById(R.id.imageViewF2_3);
        imageViewF2_4 = findViewById(R.id.imageViewF2_4);

        //instancia de elementos do quadro de punnet
        cursor[0] = textView1 = findViewById(R.id.filho1_2law);
        cursor[1] = textView2 = findViewById(R.id.filho2_2law);
        cursor[2] = textView3 = findViewById(R.id.filho3_2law);
        cursor[3] = textView4 = findViewById(R.id.filho4_2law);
        cursor[4] = textView5 = findViewById(R.id.filho5_2law);
        cursor[5] = textView6 = findViewById(R.id.filho6_2law);
        cursor[6] = textView7 = findViewById(R.id.filho7_2law);
        cursor[7] = textView8 = findViewById(R.id.filho8_2law);
        cursor[8] = textView9 = findViewById(R.id.filho9_2law);
        cursor[9] = textView10 = findViewById(R.id.filho10_2law);
        cursor[10] = textView11 = findViewById(R.id.filho11_2law);
        cursor[11] = textView12 = findViewById(R.id.filho12_2law);
        cursor[12] = textView13 = findViewById(R.id.filho13_2law);
        cursor[13] = textView14 = findViewById(R.id.filho14_2law);
        cursor[14] = textView15 = findViewById(R.id.filho15_2law);
        cursor[15] = textView16 = findViewById(R.id.filho16_2law);
    }

    private void enviarResposta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textToSpeech.speak("A solução foi enviada com sucesso!", TextToSpeech.QUEUE_FLUSH, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textToSpeech.speak("Houve um erro ao submeter a solução, por favor, tente novamente!", TextToSpeech.QUEUE_FLUSH, null);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String questao_tratada = questao_recebida;
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_dispositivo", id_dispositivo);
                parametros.put("n_lei", "2ª Lei");
                parametros.put("comando", questao_tratada);
                parametros.put("solucao", resposta_formatada);
                parametros.put("email_cadastro_questao", email);

                return  parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
