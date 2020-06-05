package com.bioblu.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.bioblu.R;
public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instancia da Acessibilidade
        AccessibilityManager am = (AccessibilityManager)getSystemService(Context.ACCESSIBILITY_SERVICE);

        //Escutando a o Status do talk back
        am.addAccessibilityStateChangeListener(new AccessibilityManager.AccessibilityStateChangeListener(){
            @Override
            public void onAccessibilityStateChanged(boolean b) {
                accessibiltyChanged(b);
            }
        });
        accessibiltyChanged(am.isEnabled());

    }

    //Caso ativado Pede para desabilitar
    void accessibiltyChanged (Boolean enabled){
        if(enabled){
            Toast.makeText(MainActivity.this, "Hold simultaneously both volume buttons to disable the talkback or make a L gesture, that is a swipe down and right to disable the Talkback response or feedback", Toast.LENGTH_LONG).show();
        }else if (!enabled){
            finish();
            Intent intent = new Intent(getApplicationContext(), activity_firstRun.class);
            startActivity(intent);
        }
    }

}