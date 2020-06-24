package com.bioblu.first_law;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.bioblu.controllers.OuvinteDeToque;
import com.bioblu.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bioblu.main.activity_voiceRate.FILE_NAME;

public class activity_solve_and_send_question extends AppCompatActivity {
    /*criando variáveis para ImageView */
    private TextView textViewFilho1, textViewFilho2, textViewFilho3, textViewFilho4, textView4;
    public ImageView filho1, filho2, filho3, filho4;
    public int screenWidth;
    public int screenHeight;
    private String d, d1, c1, d2, c2, d3, c3, d4, c4,g1 ,g2 ,g3 ,g4;
    private String res;
    private String resposta[] = new String[4];
    int y;
    private TextToSpeech textToSpeech;
    public float velocidade;
    private String questao, letra, email;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    private String id_dispositivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao);
        Bundle dados = getIntent().getExtras();

        //recupera o id para enviar junto com a resposta
        id_dispositivo = Settings.Secure.getString(
                getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        textViewFilho1 = findViewById(R.id.tetxViewF1_questao1law);
        textViewFilho2 = findViewById(R.id.tetxViewF2_questao1law);
        textViewFilho3 = findViewById(R.id.tetxViewF3_questao1law);
        textViewFilho4 = findViewById(R.id.tetxViewF4_questao1law);
        textView4 = findViewById(R.id.textView_questao1law);

        filho1 = findViewById(R.id.imageViewF1_questao1law);
        filho2 = findViewById(R.id.imageViewF2_questao1law);
        filho3 = findViewById(R.id.imageViewF3_questao1law);
        filho4 = findViewById(R.id.imageViewF4_questao1law);

        /* tratamento de erro da api de fala */
        textToSpeech = new TextToSpeech(activity_solve_and_send_question.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ler_velocidade();
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("en", "US"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.speak(questao, TextToSpeech.QUEUE_FLUSH, null);
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
        questao = dados.getString("questao");
        letra = dados.getString("letra");
        email = dados.getString("email");
        textView4.setText(questao);

        initTela();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {

        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_solve_and_send_question);

        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth, y) {

            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), activity_menu.class);
                startActivity(intent);
            }

            @Override
            public void onLongPressQuestao() {
                enviarResposta("http://200.239.66.35/bioblu/inserir.php");
            }

            @Override
            public void doubleTap(){
                textToSpeech.speak("\n" + "Gene confirmed " + escolhafala, TextToSpeech.QUEUE_FLUSH, null);
                if (x >= 1 && escolha != null) {
                    if (x == 1) {
                        c1 = escolha;
                    } else if (x == 2) {
                        d1 = escolha;
                        g1 = c1 + d1;
                        if (c1.equals(c1.toUpperCase()) && d1.equals(d1.toUpperCase())) {
                            filho1.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho1.setText(g1);
                            textToSpeech.speak("\n" + " Dominate Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c1.equals(c1.toLowerCase()) && d1.equals(d1.toUpperCase()) || c1.equals(c1.toUpperCase()) && d1.equals(d1.toLowerCase())) {
                            filho1.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho1.setText(g1);
                            textToSpeech.speak("\n" + " Dominate Heterozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c1.equals(c1.toLowerCase()) && d1.equals(d1.toLowerCase())) {
                            filho1.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho1.setText(g1);
                            textToSpeech.speak("\n" + " Recessive Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            re++;
                        }
                    } else if (x == 3) {
                        c2 = escolha;
                    } else if (x == 4) {
                        d2 = escolha;
                        g2 = c2 + d2;
                        if (c2.equals(c2.toUpperCase()) && d2.equals(d2.toUpperCase())) {
                            filho2.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho2.setText(g2);
                            textToSpeech.speak("\n" + " Dominate Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c2.equals(c2.toLowerCase()) && d2.equals(d2.toUpperCase()) || c2.equals(c2.toUpperCase()) && d2.equals(d2.toLowerCase())) {
                            filho2.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho2.setText(g2);
                            textToSpeech.speak("\n" + " Dominate Heterozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c2.equals(c2.toLowerCase()) && d2.equals(d2.toLowerCase())) {
                            filho2.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho2.setText(g2);
                            re++;
                            textToSpeech.speak("\n" + " Recessive Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 5) {
                        c3 = escolha;
                    } else if (x == 6) {
                        d3 = escolha;
                        g3 = c3 + d3;
                        if (c3.equals(c3.toUpperCase()) && d3.equals(d3.toUpperCase())) {
                            filho3.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho3.setText(g3);
                            textToSpeech.speak("\n" + "Dominate Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;
                        } else if (c3.equals(c3.toLowerCase()) && d3.equals(d3.toUpperCase()) || c3.equals(c3.toUpperCase()) && d3.equals(d3.toLowerCase())) {
                            filho3.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho3.setText(g3);
                            textToSpeech.speak("\n" +  "Dominate Heterozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            he++;
                        } else if (c3.equals(c3.toLowerCase()) && d3.equals(d3.toLowerCase())) {
                            filho3.setImageResource(R.drawable.quadrado_preenchido);
                            textViewFilho3.setText(g3);
                            re++;
                            textToSpeech.speak("\n" + "Recessive Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 7) {
                        c4 = escolha;
                    } else if (x == 8) {
                        d4 = escolha;
                        g4 = c4 + d4;
                        if (c4.equals(c4.toUpperCase()) && d4.equals(d4.toUpperCase())) {
                            filho4.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho4.setText(g4);
                            textToSpeech.speak("\n" + " Dominate Homozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            ho++;

                            d = c1 + d1 + "," + c2 + d2 + "," + c3 + d3 + "," + c4 + d4;
                            d = d.trim();

                        } else if (c4.equals(c4.toLowerCase()) && d4.equals(d4.toUpperCase()) || c4.equals(c4.toUpperCase()) && d4.equals(d4.toLowerCase())) {
                            filho4.setImageResource(R.drawable.quadrado_normal);
                            textViewFilho4.setText(g4);
                            textToSpeech.speak("\n" + " Dominate Heterozygous Gene", TextToSpeech.QUEUE_FLUSH, null);
                            he++;

                            d = c1 + d1 + "," + c2 + d2 + "," + c3 + d3 + "," + c4 + d4;
                            d = d.trim();

                        } else if (c4.equals(c4.toLowerCase()) && d4.equals(d4.toLowerCase())) {
                                filho4.setImageResource(R.drawable.quadrado_preenchido);
                                textViewFilho4.setText(g4);
                                re++;
                                textToSpeech.speak("\n" + " Recessive Homozygous Gene", TextToSpeech.QUEUE_ADD, null);

                                d = c1 + d1 + "," + c2 + d2 + "," + c3 + d3 + "," + c4 + d4;
                                d = d.trim();
                        }
                    }
                }else {
                    x--;
                }
            }


            @Override
            public void onSwipeTopE() {
                if(x <= 8) {
                    textToSpeech.speak("Gene " + letra +  " Dominant, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    /** Cruzamento feito pelo User**/
                    escolha = letra.toUpperCase();
                    escolhafala = letra + " Dominant";
                } else {
                    escolhafala = "";
                    textToSpeech.speak("To send a reply click and hold on the screen, to return to the menu do an L in reverse", TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onSwipeBottomE() {
                if(x <= 8) {
                    textToSpeech.speak("Gene " + letra +  " Dominant, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    /** Cruzamento feito pelo User**/
                    escolha = letra.toUpperCase();
                    escolhafala = letra + " Dominant";
                }else  {
                    escolhafala = "";
                    textToSpeech.speak("To send a reply click and hold on the screen, to return to the menu do an L in reverse", TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onSwipeTopD() {
                if(x <= 8) {
                    textToSpeech.speak("Gene " + letra + " \n" + " recessive, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    /** Informando o Cruzamento a ser feito**/
                    escolha = letra.toLowerCase();
                    escolhafala = letra + " recessive";
                } else  {
                    escolhafala = "";
                    textToSpeech.speak("To send a reply click and hold on the screen, to return to the menu do an L in reverse", TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            @Override
            public void onSwipeBottomD () {
                if (x <= 8) {
                    textToSpeech.speak("Gene " + letra + " recessive, double tap to confirm", TextToSpeech.QUEUE_FLUSH, null);
                    /** Informando o Cruzamento a ser feito**/
                    escolha = letra.toLowerCase();
                    escolhafala = letra + " rescessive";
                } else  {
                    escolhafala = "";
                    textToSpeech.speak("To send a reply click and hold on the screen, to return to the menu do an L in reverse", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }


    private void enviarResposta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textToSpeech.speak("The solution was sent successfully!", TextToSpeech.QUEUE_FLUSH, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textToSpeech.speak("\n" + "There was an error submitting the solution, please try again!", TextToSpeech.QUEUE_FLUSH, null);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String questao_sub = questao;
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_dispositivo", id_dispositivo);
                parametros.put("n_lei", "1ª Lei");
                parametros.put("solucao", d);
                parametros.put("comando", questao_sub);
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
}
