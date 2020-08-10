package com.bioblu.second_law;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteTelaTutorial;
import com.bioblu.first_law.activity_erro;
import com.bioblu.first_law.activity_search_question;
import com.bioblu.first_law.activity_solve_and_send_question;
import com.bioblu.main.main_menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class activity_search_question_2law extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView textView_questao;
    private String [] questao, genes, email;
    int i, teste;
    public int screenWidth;
    private int yx = 0, l1 = 0;
    private int ii = -1;
    private int ix = -1;
    TextView[] cursor = new TextView[1];
    private String id_dispositivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_question_2law);

        //recupera o id para enviar junto com a resposta
        id_dispositivo = Settings.Secure.getString(
                getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        String x = id_dispositivo.trim();

        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_search_question_2law.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.setSpeechRate(1);
                    textToSpeech.speak("Você está na tela de busca da segunda lei de mendel, para buscar questões basta deslizar para cima e para baixo na tela"+'\n'
                            +"após ouvir o comando da questão, dê dois toques para selecionar", textToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;


        textView_questao = findViewById(R.id.textView_questao_search);

        RequestQueue queue = Volley.newRequestQueue(this);

        final String url = "http://200.239.66.35/bioblu/consulta2law.php?codigo=" + x + "";

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        teste = response.length();
                        questao = new String[teste];
                        genes = new String[teste];
                        email = new String[teste];

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);

                                questao[i] = new String (jsonObject.getString("comando_questao").getBytes("ISO-8859-1"), "UTF-8");
                                genes[i] = jsonObject.getString("genes_questao");
                                email[i] = jsonObject.getString("email_usuario");
                            }

                        } catch (JSONException e) {
                            Toast.makeText(activity_search_question_2law.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent i = new Intent(getApplicationContext(), activity_erro.class);
                        startActivity(i);
                        finish();
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);

        cursor[0] = textView_questao;

        initTela();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayout_search_question_2law);

        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
            }

            @Override
            public void doubleTap() {
                Intent intent = new Intent(getApplicationContext(), activity_solve_and_send_question_2law.class);
                if (ii >= 0) {
                    intent.putExtra("questao", questao[ii]);
                    intent.putExtra("letra", genes[ii]);
                    intent.putExtra("email", email[ii]);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onSwipeTop() {
                if (ix == 0 & ii > 0) {
                    yx--;
                    ii--;

                } else if (ii > 0) {
                    ii--;
                    ix--;
                } else {
                    ii = 0;
                    ix = 0;
                }

                textToSpeech.speak("Questão " + ii + ":" + questao[ii], TextToSpeech.QUEUE_FLUSH, null);
                textView_questao.setText(questao[ii]);
            }

            @Override
            public void onSwipeBottom() {
                if (ii == questao.length - 1) {
                    ii = questao.length - 1;

                } else if (ix == questao.length & ii <= questao.length) {
                    yx++;
                    ii++;

                } else if (ii < questao.length) {
                    ii++;
                    ix++;
                }
                textView_questao.setText(questao[ii]);
                textToSpeech.speak("Questão " + ii + ":" + questao[ii], TextToSpeech.QUEUE_FLUSH, null);

            }

            @Override
            public void caminhar() {
                textView_questao.setText(String.valueOf(questao[l1 + yx]));
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
        textToSpeech.stop();
        if(textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

}
