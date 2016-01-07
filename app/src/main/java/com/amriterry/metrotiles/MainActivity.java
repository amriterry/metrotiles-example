package com.amriterry.metrotiles;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    private int tilesWidth;
    private int tilesCount;
    private int nextTile;
    private long slideTimer = 3000;

    private LinearLayout tilesContainer;
    private TileCarouselHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
        initializeTilesWidth();
        startCarousel();
    }

    /**
     *  Initializes view and add event listeners.
     */
    private void initializeView() {
        handler = new TileCarouselHandler();
        tilesContainer = (LinearLayout) findViewById(R.id.tiles_container);

        tilesContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TILES", "Clicked");
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
            }
        });

        tilesCount = tilesContainer.getChildCount();
    }

    /**
     *  Starts the carousel sliding.
     */
    private void startCarousel() {
        //  First thread creates an infinite loop in which the UI is to be changed.
        new Thread(new Runnable(){

            public void run(){
                while(true){
                    try {
                        //  This thread sleeps for 'slideTimer' ms and after 'slideTimer' ms it again loops.
                        Thread.sleep(slideTimer);

                        //  Handler object uses a runnable instance to make changes to UI.
                        handler.post(new Runnable(){

                            public void run(){
                                slideTile();
                            }

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    /**
     * Initializes tiles width for each tile in tiles container.
     */
    private void initializeTilesWidth() {
        // This is called each time UI is to be drawn to the screen, such as in case of orientation change.
        tilesContainer.post(new Runnable() {

            public void run() {
                tilesWidth = tilesContainer.getWidth();

                for (int i = 0; i < tilesContainer.getChildCount(); i++) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tilesContainer.getChildAt(i).getLayoutParams();
                    params.width = tilesWidth;
                }
            }

        });
    }

    /**
     *  Slides a tile to next tile.
     */
    private void slideTile(){
        //  This initializes tiles container for transition.
        TransitionManager.beginDelayedTransition(tilesContainer);

        //  Main Code snippet to make the tiles slide.
        tilesContainer.setPadding(nextTile * (-tilesWidth),0,0,0);
        nextTile = (nextTile+1) % tilesCount;
    }

    /**
     * Handler class updates the UI, in this instance the tiles container.
     */
    private class TileCarouselHandler extends Handler{}
}
