package com.bioblu.controllers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class OuvinteTelaTutorial implements View.OnTouchListener {
    private final GestureDetector gestureDetector;
    public String escolhaGeneP, escolhafala;
    public int gene_x = 0;
    int Width;
    public String escolha;
    public float re = 0;
    public float ho = 0;
    public float he = 0;
    public int heteD = 0;
    public int homoD = 0;
    public int reces = 0;
    public int x = 1;
    boolean control = false;
    public boolean sair = false;

    public OuvinteTelaTutorial(Context context, int width) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        /** Recebe o tamanho da tela de Cruzamento**/
        Width = width;

    }

    public void caminhar() {
    }

    public void doubleTap() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }

    public void onSwipeTopE() {
    }

    public void onSwipeBottomE() {
    }

    public void onSwipeTopD() {
    }

    public void onSwipeBottomD() {
    }

    public void LGesture() {

    }

    public void onLongPressTutorial() {

    }

    public void wrong() {
    }

    public boolean onTouch(View v, MotionEvent event) {

        return gestureDetector.onTouchEvent(event);

    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {


        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        private static final int SWIPE_MIN_DISTANCE = 120;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        private static final int MIN_SWIPE_DISTANCE_X = 100;
        private static final int MAX_SWIPE_DISTANCE_X = 1000;
        private static final int MIN_SWIPE_DISTANCE_Y = 100;
        private static final int MAX_SWIPE_DISTANCE_Y = 1000;

        @Override
        public void onLongPress(MotionEvent e) {
            onLongPressTutorial();

        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            doubleTap();
            x++;
            gene_x++;
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float distanceY = e2.getY() - e1.getY();
            float deltaX = e1.getX() - e2.getX();
            float deltaXAbs = Math.abs(deltaX);
            float deltaY = e1.getY() - e2.getY();
            float deltaYAbs = Math.abs(deltaY);


            if (e1.getX() < Width / 2) {
                if ((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
                    if (deltaY > 0) {
                        onSwipeTopE();
                        onSwipeTop();
                    } else {
                        onSwipeBottomE();
                        onSwipeBottom();
                        control = true;
                    }
                    if (control && (deltaXAbs >= 300) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X)) {
                        if (!(deltaX < 0)) {
                            LGesture();
                            control = false;
                        }
                    }else if ((deltaXAbs > 100 ) && (deltaXAbs < 300) ){
                        wrong();
                    }
                    return false;
                }else {
                    wrong();
                }

                return false;

            } else {
                if ((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
                    if (deltaY > 0) {
                        onSwipeTopD();
                        onSwipeTop();
                    } else {
                        onSwipeBottomD();
                        onSwipeBottom();
                        control = true;
                    }
                    if (control && (deltaXAbs >= 300) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X)) {
                        if (!(deltaX < 0)) {
                            LGesture();
                            control = false;
                        }
                    }else if ((deltaXAbs > 100 ) && (deltaXAbs < 300) ){
                        wrong();
                    }
                    return false;
                }else {
                    wrong();
                }
                return false;
            }
                //fim on fling
        }
    }
}