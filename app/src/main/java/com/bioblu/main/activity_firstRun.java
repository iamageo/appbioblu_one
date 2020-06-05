package com.bioblu.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bioblu.R;

public class activity_firstRun extends AppCompatActivity {
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        //variável para salvar o estado do app (execuções)
        sharedPreferences = getSharedPreferences("fistRun", MODE_PRIVATE);
    }

    /*método para gerenciar qual tela vai abrir decorrente a execução em que está
     * se execução = true (abre tela de introdução)
     * se execução = false (não está mais na primeira) abre o menu
     * */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sharedPreferences.getBoolean("firstRun", true)) {
            finish();
            Intent intent = new Intent(getApplicationContext(), activity_voiceRate.class);
            startActivity(intent);
            sharedPreferences.edit().putBoolean("firstRun", false).apply();
        } else {
            finish();
            Intent intent = new Intent(getApplicationContext(), main_menu.class);
            startActivity(intent);
        }
    }
}

