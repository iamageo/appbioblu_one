package com.bioblu.second_law;
import com.bioblu.R;
import com.bioblu.controllers.OuvinteDeToque;
import com.bioblu.main.main_menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import static com.bioblu.main.activity_voiceRate.FILE_NAME;

public class activity_cruzamento_coelho extends AppCompatActivity {
    /*criando variáveis para ImageView */
    private TextView textViewFilho1, textViewFilho2, textViewFilho3, textViewFilho4, textView1, textView2;
    private ImageView meuImageView1, meuImageView2, filho1, filho2, filho3, filho4;
    public int screenWidth, screenHeight;
    public String Selvagem  = "C" ;
    public String Chinchila  = "Cch" ;
    public String himalaia  = "Ch" ;
    public String albino  = "Ca" ;
    private String d, c, x1, x2, x3, x4, d1, c1, d2, c2, d3, c3, d4, c4,z1,z2,z3,z4,a1, a2, b1, b2,xx1,xx2;
    int y;
    private TextToSpeech textToSpeech;
    public float velocidade;
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;
    public String[] dados_recuperados = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cruzamento_coelho);
        Bundle dados = getIntent().getExtras();

        dados_recuperados[0] = dados.getString("genes_escolhido1");
        dados_recuperados[1] = dados.getString("genes_escolhido2");
        dados_recuperados[2] = dados.getString("genes_escolhido3");
        dados_recuperados[3] = dados.getString("genes_escolhido4");

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
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("pt", "BR"));
                    textToSpeech.setSpeechRate(velocidade);
                    textToSpeech.setPitch(1);
                    textToSpeech.speak("na tela anterios você informou os alelos parentais, agora realize esse cruzamento!", TextToSpeech.QUEUE_FLUSH, null);
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


        initTela();


    }
    @SuppressLint("ClickableViewAccessibility")
    private void initTela() {

        RelativeLayout Rlayout = findViewById(R.id.relativeLayout_cruzamento_coelho);

        Rlayout.setOnTouchListener(new OuvinteDeToque(this, screenWidth,y) {
            @Override
            public void LGesture() {
                finish();
                Intent intent = new Intent(getApplicationContext(), main_menu.class);
                startActivity(intent);
            }


            @Override
            public void doubleTap(){
                textToSpeech.speak("Confirmado Gene "+escolhafala, TextToSpeech.QUEUE_FLUSH, null);
                if (x >= 1 && escolha != null) {
                    if (x == 1) {
                        a1 = escolha;
                    } else if (x == 2) {
                        a2 = escolha;
                        xx1 = a1 + a2;
                        if ((a1 == Selvagem) || (a2 == Selvagem)) {
                            meuImageView1.getDrawable();
                            meuImageView1.setImageResource(R.drawable.selvagem);
                            textView1.setText(xx1);
                            textToSpeech.speak("Selvagem ", TextToSpeech.QUEUE_FLUSH, null);
                        } else if (((a1 == Chinchila) || (a2 == Chinchila))) {
                            meuImageView1.setImageResource(R.drawable.chinchila);
                            textView1.setText(xx1);
                            textToSpeech.speak("Chinchila", TextToSpeech.QUEUE_FLUSH, null);
                        } else if ((a1 == himalaia) && (a2 == himalaia)) {
                            meuImageView1.setImageResource(R.drawable.himalaia);
                            textView1.setText(xx1);
                            textToSpeech.speak("Himalaia", TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            meuImageView1.setImageResource(R.drawable.albino);
                            textView1.setText(xx1);
                            textToSpeech.speak("Albinho", TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else if (x == 3) {
                        b1 = escolha;
                    } else if (x == 4) {
                        b2 = escolha;
                        xx2 = b1 + b2;
                        if ((b1 == Selvagem) || (b2 == Selvagem)) {
                            meuImageView2.setImageResource(R.drawable.selvagem);
                            textView2.setText(xx2);
                            textToSpeech.speak("Selvagem ", TextToSpeech.QUEUE_FLUSH, null);
                        } else if (((b1 == Chinchila) || (b2 == Chinchila))) {
                            meuImageView2.setImageResource(R.drawable.chinchila);
                            textView2.setText(xx2);
                            textToSpeech.speak("Chinchila", TextToSpeech.QUEUE_FLUSH, null);
                        } else if ((b1 == himalaia) || (b2 == himalaia)) {
                            meuImageView2.setImageResource(R.drawable.himalaia);
                            textView2.setText(xx2);
                            textToSpeech.speak("Himalaia", TextToSpeech.QUEUE_FLUSH, null);
                        } else {
                            meuImageView2.setImageResource(R.drawable.albino);
                            textView2.setText(xx2);
                            textToSpeech.speak("Albinho", TextToSpeech.QUEUE_FLUSH, null);
                        }
                        x1 = a1 + b1;
                        x2 = a1 + b2;
                        x3 = a2 + b1;
                        x4 = a2 + b2;
                        c = x1 + "," + x2 + "," + x3 + "," + x4;
                        c = c.trim();

                    } else if (x == 5) {
                        c1 = escolha;
                    } else if (x == 6) {
                        d1 = escolha;
                        z1 = c1 + d1;
                        if( z1.equals(x1)) {
                            if ((c1 == Selvagem) || (d1 == Selvagem)) {
                                filho1.setImageResource(R.drawable.selvagem);
                                textViewFilho1.setText(z1);
                                se++;
                                textToSpeech.speak("Selvagem ", TextToSpeech.QUEUE_FLUSH, null);
                            } else if (((c1 == Chinchila) || (d1 == Chinchila))) {
                                filho1.setImageResource(R.drawable.chinchila);
                                textViewFilho1.setText(z1);
                                ch++;
                                textToSpeech.speak("Chinchila", TextToSpeech.QUEUE_FLUSH, null);
                            } else if ((c1 == himalaia) || (d1 == himalaia)) {
                                filho1.setImageResource(R.drawable.himalaia);
                                textViewFilho1.setText(z1);
                                hi++;
                                textToSpeech.speak("Himalaia", TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                filho1.setImageResource(R.drawable.albino);
                                textViewFilho1.setText(z1);
                                textToSpeech.speak("Albinho", TextToSpeech.QUEUE_FLUSH, null);
                                al++;
                            }
                        }else{
                            x = x - 2;
                            textToSpeech.speak("O Gene Informado Homozigoto Dominate está errado, tente novamente.", TextToSpeech.QUEUE_FLUSH, null);
                            }

                    } else if (x == 7) {
                        c2 = escolha;
                    } else if (x == 8) {
                        d2 = escolha;
                        z2 = c2 + d2;
                        if (z2.equals(x2)) {
                            if ((c2 == Selvagem) || (d2 == Selvagem)) {
                                filho2.setImageResource(R.drawable.selvagem);
                                textViewFilho2.setText(z2);
                                se++;
                                textToSpeech.speak("Selvagem ", TextToSpeech.QUEUE_FLUSH, null);
                            } else if (((c2 == Chinchila) || (d2 == Chinchila))) {
                                filho2.setImageResource(R.drawable.chinchila);
                                textViewFilho2.setText(z2);
                                ch++;
                                textToSpeech.speak("Chinchila", TextToSpeech.QUEUE_FLUSH, null);
                            } else if ((c2 == himalaia) || (d2 == himalaia)) {
                                filho2.setImageResource(R.drawable.himalaia);
                                textViewFilho2.setText(z2);
                                hi++;
                                textToSpeech.speak("Himalaia", TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                filho2.setImageResource(R.drawable.albino);
                                textViewFilho2.setText(z2);
                                textToSpeech.speak("Albinho", TextToSpeech.QUEUE_FLUSH, null);
                                al++;
                            }
                        }else{
                            x = x - 2;
                            textToSpeech.speak("O Gene Informado Homozigoto Dominate está errado, tente novamente.", TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else if (x == 9) {
                        c3 = escolha;
                    } else if (x == 10) {
                        d3 = escolha;
                        z3 = c3 + d3;
                        if (z3.equals(x3)) {
                            if ((c3 == Selvagem) || (d3 == Selvagem)) {
                                filho3.setImageResource(R.drawable.selvagem);
                                textViewFilho3.setText(z3);
                                se++;
                                textToSpeech.speak("Selvagem ", TextToSpeech.QUEUE_FLUSH, null);
                            } else if (((c3 == Chinchila) || (d3 == Chinchila))) {
                                filho3.setImageResource(R.drawable.chinchila);
                                textViewFilho3.setText(z3);
                                ch++;
                                textToSpeech.speak("Chinchila", TextToSpeech.QUEUE_FLUSH, null);
                            } else if ((c3 == himalaia) || (d3 == himalaia)) {
                                filho3.setImageResource(R.drawable.himalaia);
                                textViewFilho3.setText(z3);
                                hi++;
                                textToSpeech.speak("Himalaia", TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                filho3.setImageResource(R.drawable.albino);
                                textViewFilho3.setText(z3);
                                textToSpeech.speak("Albinho", TextToSpeech.QUEUE_FLUSH, null);
                                al++;
                            }
                        }else{
                            x = x - 2;
                            textToSpeech.speak("O Gene Informado Homozigoto Dominate está errado, tente novamente.", TextToSpeech.QUEUE_FLUSH, null);
                        }

                    } else if (x == 11) {
                        c4 = escolha;
                    } else if (x == 12) {
                        d4 = escolha;
                        z4 = c4 + d4;
                        if (z4.equals(x4)) {
                            if ((c4 == Selvagem) || (d4 == Selvagem)) {
                                filho4.setImageResource(R.drawable.selvagem);
                                textViewFilho4.setText(z4);
                                se++;
                                textToSpeech.speak("Selvagem ", TextToSpeech.QUEUE_FLUSH, null);
                            } else if (((c4 == Chinchila) || (d4 == Chinchila))) {
                                filho4.setImageResource(R.drawable.chinchila);
                                textViewFilho4.setText(z4);
                                ch++;
                                textToSpeech.speak("Chinchila", TextToSpeech.QUEUE_FLUSH, null);
                            } else if ((c4 == himalaia) || (d4 == himalaia)) {
                                filho4.setImageResource(R.drawable.himalaia);
                                textViewFilho4.setText(z4);
                                hi++;
                                textToSpeech.speak("Himalaia", TextToSpeech.QUEUE_FLUSH, null);
                            } else {
                                filho4.setImageResource(R.drawable.albino);
                                textViewFilho4.setText(z4);
                                textToSpeech.speak("Albinho", TextToSpeech.QUEUE_FLUSH, null);
                                al++;
                            }
                        }else{
                            x = x - 2;
                            textToSpeech.speak("O Gene Informado Homozigoto Dominate está errado, tente novamente.", TextToSpeech.QUEUE_FLUSH, null);
                        }

                        d = c1 + d1 + "," + c2 + d2 + "," + c3 + d3 + "," + c4 + d4;
                        d = d.trim();
                        se = (se / 4) * 100;
                        ch = (ch / 4) * 100;
                        hi = (hi / 4) * 100;
                        al = (al / 4) * 100;

                        selv = (int) se;
                        chic = (int) ch;
                        hima = (int) hi;
                        albi = (int) al;

                        if (selv > 0 & chic > 0 & hima  > 0 & albi > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram , " + selv + " Porcento Selvagem ," + chic + " Porcento Chinchila ," + hima + "Porcento Himalaia ," + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (selv > 0 & chic > 0 & hima  > 0 ) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram , " + selv + " Porcento Selvagem ," + chic + " Porcento Chinchila ," + hima + "Porcento Himalaia", TextToSpeech.QUEUE_ADD, null);

                        }else if (selv > 0 & chic > 0 & albi  > 0 ) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram , " + selv + " Porcento Selvagem ," + chic + " Porcento Chinchila ," + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (selv > 0 & hima > 0 & albi  > 0 ) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram , " + selv + " Porcento Selvagem ," + hima + "Porcento Himalaia" + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (chic > 0 & hima > 0 & albi  > 0 ) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram , " + chic + " Porcento Chinchila ," + hima + "Porcento Himalaia" + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (selv > 0 & chic > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram,"+ selv + " Porcento Selvagem e" + chic + " Porcento Chinchila ", TextToSpeech.QUEUE_ADD, null);

                        } else if (selv > 0 & hima > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram,"+ selv + " Porcento Selvagem e " + hima + "Porcento Himalaia", TextToSpeech.QUEUE_ADD, null);

                        } else if (selv > 0 & albi> 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + selv + " Porcento Selvagem e " + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (chic > 0 & hima> 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + chic + " Porcento Chinchila e , " + hima + "Porcento Himalaia", TextToSpeech.QUEUE_ADD, null);

                        }else if (chic > 0 & albi> 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + chic + " Porcento Chinchila e ,"+ albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (hima > 0 & albi> 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + hima + "Porcento Himalaia e," + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);

                        }else if (selv > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + selv + " Porcento Selvagem .", TextToSpeech.QUEUE_ADD, null);

                        } else if (chic > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + chic + " Porcento Chinchila.", TextToSpeech.QUEUE_ADD, null);

                        } else if (hima > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram," + hima + "Porcento Himalaia.", TextToSpeech.QUEUE_ADD, null);

                        }else if (albi > 0) {
                            textToSpeech.speak(" Os Genes gerados através do Cruzamentos foram,"  + albi + "Porcento Albino.", TextToSpeech.QUEUE_ADD, null);
                        }
                    }
                }else {
                    x--;
                }

             }
            //{"Selvagem", "Chinchila", "Himalaia", "Albino"};
            @Override
            public void onSwipeTopE() {
                textToSpeech.speak("Gene, "+ dados_recuperados[0], TextToSpeech.QUEUE_FLUSH, null);
                escolhafala = dados_recuperados[0];
                switch (escolhafala) {
                    case "Selvagem":
                        escolha = "C";
                        break;
                    case "Chinchila":
                        escolha = "Cch";
                        break;
                    case "Himalaia":
                        escolha = "Ch";
                        break;
                    default:
                        escolha = "Ca";
                        break;
                }
                }

            @Override
            public void onSwipeBottomE() {
                textToSpeech.speak("Gene, "+dados_recuperados[1], TextToSpeech.QUEUE_FLUSH, null);
                escolhafala = dados_recuperados[1];
                switch (escolhafala) {
                    case "Selvagem":
                        escolha = "C";
                        break;
                    case "Chinchila":
                        escolha = "Cch";
                        break;
                    case "Himalaia":
                        escolha = "Ch";
                        break;
                    default:
                        escolha = "Ca";
                        break;
                }

            }

            @Override
            public void onSwipeTopD() {
                textToSpeech.speak("Gene, "+dados_recuperados[2], TextToSpeech.QUEUE_FLUSH, null);
                escolhafala = dados_recuperados[2];
                switch (escolhafala) {
                    case "Selvagem":
                        escolha = "C";
                        break;
                    case "Chinchila":
                        escolha = "Cch";
                        break;
                    case "Himalaia":
                        escolha = "Ch";
                        break;
                    default:
                        escolha = "Ca";
                        break;
                }

            }
            @Override
            public void onSwipeBottomD() {
                textToSpeech.speak("Gene, "+dados_recuperados[3], TextToSpeech.QUEUE_FLUSH, null);
                escolhafala = dados_recuperados[3];
                switch (escolhafala) {
                    case "Selvagem":
                        escolha = "C";
                        break;
                    case "Chinchila":
                        escolha = "Cch";
                        break;
                    case "Himalaia":
                        escolha = "Ch";
                        break;
                    default:
                        escolha = "Ca";
                        break;
                }

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

