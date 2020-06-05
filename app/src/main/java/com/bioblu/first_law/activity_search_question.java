package com.bioblu.first_law;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteTelaTutorial;
import com.bioblu.main.main_menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class activity_search_question extends AppCompatActivity {

    public float velocidade;
    private TextToSpeech textToSpeech;
    private TextView textView_questao;
    public int teste;
    private String [] questao ;
    private String [] letra ;
    int i;
    private int screenWidth;
    private int yx = 0, l1 = 0;
    private int ii = -1;
    private int ix = -1;
    TextView[] cursor = new TextView[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_question);

        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_search_question.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("en", "US"));
                    textToSpeech.setSpeechRate(1);
                    textToSpeech.speak("Você está na tela de busca da primeira lei de mendel, para navegar entre as opções basta dezlizar o dedo na tela"+'\n'
                            +"caso você tenha um código de questão copiado em seu aparelho, dê dois toques para colar"+'\n'+"para resolver a questão clique e segure na tela", textToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        textView_questao = findViewById(R.id.textView_questao_search);



        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://10.219.1.159/api/consulta.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                teste = response.length();
                questao = new String[teste];
                letra = new String[teste];

                try {
                    for (int i = 0; i < response.length(); i++) {
                    jsonObject = response.getJSONObject(i);

                        questao[i] = jsonObject.getString("comando");
                        letra[i] = jsonObject.getString("letra");
                    }

                    } catch (JSONException e) {
                        Toast.makeText(activity_search_question.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity_search_question.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(activity_search_question.this);
        requestQueue.add(jsonArrayRequest);



        cursor[0] = textView_questao;

       initTela();
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {
        RelativeLayout Relativelayout = findViewById(R.id.relativeLayout_search_question_2law);

        Relativelayout.setOnTouchListener(new OuvinteTelaTutorial(getApplicationContext(), screenWidth) {

            @Override
            public void doubleTap() {
                Intent intent = new Intent(getApplicationContext(), activity_solve_and_send_question.class);
                if (ii >= 0) {
                   intent.putExtra("questao", questao[ii]);
                   intent.putExtra("letra", letra[ii]);
                   startActivity(intent);
                   finish();
                }
            }


            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
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

                textToSpeech.speak(questao[ii], TextToSpeech.QUEUE_FLUSH, null);
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
                textToSpeech.speak(questao[ii], TextToSpeech.QUEUE_FLUSH, null);

            }
            @Override
            public void caminhar() {
                textView_questao.setText(String.valueOf(questao[l1 + yx]));
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
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }


}
