package com.example.garen.modernartui;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    static private final String URL = "http://www.moma.org";
    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seek_bar_color);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            Random rnd = new Random();
            ArgbEvaluator colorEvaluator = new ArgbEvaluator();

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                float fraction = (float) progress / 100;

                UpdateColor(R.id.image1, R.color.start1, R.color.end1, colorEvaluator, fraction);
                UpdateColor(R.id.image2, R.color.start2, R.color.end2, colorEvaluator, fraction);
                UpdateColor(R.id.image3, R.color.start3, R.color.end3, colorEvaluator, fraction);
                UpdateColor(R.id.image4, R.color.start4, R.color.end4, colorEvaluator, fraction);
                UpdateColor(R.id.image5, R.color.start5, R.color.end5, colorEvaluator, fraction);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void UpdateColor(int imageId, int startColorId, int endColorId, ArgbEvaluator colorEvaluator, float fraction)
    {
        ImageView image = findViewById(imageId);
        image.setBackgroundColor((Integer) colorEvaluator.evaluate(fraction,
                getResources().getColor(startColorId),
                getResources().getColor(endColorId)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mDialog = AlertDialogFragment.newInstance();
            // Show AlertDialogFragment
            mDialog.show(getFragmentManager(), "Alert");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Start a Browser Activity to view specified URL
    private void startBrowser() {
        // Create intent for viewing a URL
        Uri page = Uri.parse(URL);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, page);

        // DONE - Start the chooser Activity, using the chooser intent
        startActivity(webIntent);

    }


    // Class that creates the AlertDialog
    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance() {
            return new AlertDialogFragment();
        }

        // Build AlertDialog using AlertDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Inspired by the works of Piet Mondrian.")

                    // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                    .setNegativeButton("Not Now",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                }
                            })

                    .setPositiveButton("Visit MOMA Website",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    ((MainActivity) getActivity()).startBrowser();
                                }
                            }).create();
        }
    }
}
