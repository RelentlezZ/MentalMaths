package org.relentlezz.mentalmaths.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class EndResultActivity extends Activity {

    //Declare constants
    public final static String HIGHSCORE_ADDITION_EASY = "org.relentlezz.mentalmaths.app.HIGHSCORE_ADDITION_EASY";      //Key pairs for SharedPreferences
    public final static String HIGHSCORE_ADDITION_HARD = "org.relentlezz.mentalmaths.app.HIGHSCORE_ADDITION_HARD";
    public final static String HIGHSCORE_SUBTRACTION_EASY = "org.relentlezz.mentalmaths.app.HIGHSCORE_SUBTRACTION_EASY";
    public final static String HIGHSCORE_SUBTRACTION_HARD = "org.relentlezz.mentalmaths.app.HIGHSCORE_SUBTRACTION_HARD";
    public final static String HIGHSCORE_MULTIPLICATION_EASY = "org.relentlezz.mentalmaths.app.HIGHSCORE_MULTIPLICATION_EASY";
    public final static String HIGHSCORE_MULTIPLICATION_HARD = "org.relentlezz.mentalmaths.app.HIGHSCORE_MULTIPLICATION_HARD";
    public final static String HIGHSCORE_DIVISION_EASY = "org.relentlezz.mentalmaths.app.HIGHSCORE_DIVISION_HARD";
    public final static String HIGHSCORE_DIVISION_HARD = "org.relentlezz.mentalmaths.app.HIGHSCORE_DIVISION_HARD";


    //Declare variables
    int[] passedParams;
    int difficulty, numberRange, rounds, exerciseType;
    double points, time, pointsTimeRatio, maxPoints, currentHighScore;
    String name, currentScoreHolder;

    public static DecimalFormat df2 = new DecimalFormat("####0.00");
    private SharedPreferences highScores;


    //Declare views
    TextView pointsReachedTextView;
    TextView modeTextView;
    TextView difficultyTextView;
    TextView numberRangeTextView;
    TextView playedRoundsTextView;
    TextView timeTextView;
    TextView pointsTimeRatioTextView;
    TextView highScoreTextView;
    TextView currentHighScoreTextView;

    Button saveHighScoreButton;
    Button playAgainButton;
    Button highScoreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_result);

        ExercisesActivity.toast.cancel();

        //Initialize variables
        Intent oldIntent = getIntent();                     //Get Intent from ExercisesActivity
        highScores = EndResultActivity.this.getSharedPreferences("highScores", MODE_PRIVATE);   //Setup SharedPreferences

        passedParams = oldIntent.getIntArrayExtra(ExercisesActivity.EXERCISES_EXTRA);
        difficulty = passedParams[0];
        numberRange = passedParams[1];
        rounds = passedParams[2];
        exerciseType = passedParams[3];

        pointsTimeRatio = oldIntent.getDoubleExtra(ExercisesActivity.HIGHSCORE_EXTRA, 0);
        points = oldIntent.getDoubleExtra(ExercisesActivity.POINTS_EXTRA, 0);
        time = oldIntent.getLongExtra(ExercisesActivity.TIME_EXTRA, 0) * 0.001;

        currentScoreHolder = getHighscore(difficulty, exerciseType)[0];
        currentHighScore = Double.parseDouble(getHighscore(difficulty, exerciseType)[1]);

        maxPoints = rounds * (60 * (2.5 * (numberRange / 50) * difficulty) / rounds);

        //Initialize views
        pointsReachedTextView = (TextView) findViewById(R.id.pointsReachedTextView);
        modeTextView = (TextView) findViewById(R.id.modeTextView);
        difficultyTextView = (TextView) findViewById(R.id.difficultyResultTextView);
        numberRangeTextView = (TextView) findViewById(R.id.numberRangeResultTextView);
        playedRoundsTextView = (TextView) findViewById(R.id.playedRoundsTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        pointsTimeRatioTextView = (TextView) findViewById(R.id.pointsTimeRatioTextView);
        highScoreTextView = (TextView) findViewById(R.id.highscoreTextView);
        currentHighScoreTextView = (TextView) findViewById(R.id.currentHighscoreTextView);

        saveHighScoreButton = (Button) findViewById(R.id.saveHighscoreButton);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        highScoreButton = (Button) findViewById(R.id.highscoreButton);

        //Set OnClickListeners
        saveHighScoreButton.setOnClickListener(onHighscoreSaveClicked);
        playAgainButton.setOnClickListener(onPlayAgainClicked);
        highScoreButton.setOnClickListener(onHighScoreButtonClicked);

        //Set Text in TextViews
        pointsReachedTextView.setText(getResources().getString(R.string.reached_points_1_text_view) + ExercisesActivity.df.format(points) + getResources().getString(R.string.reached_points_2_text_view) + ExercisesActivity.df.format(maxPoints) + getResources().getString(R.string.reached_points_3_text_view));
        timeTextView.setText(getResources().getString(R.string.time_needed) + ExercisesActivity.df.format(time) + getResources().getString(R.string.seconds));
        pointsTimeRatioTextView.setText(getResources().getString(R.string.points_per_second) + df2.format(pointsTimeRatio));
        numberRangeTextView.setText(getResources().getString(R.string.number_range_2_text_view) + numberRange);
        playedRoundsTextView.setText(getResources().getString(R.string.rounds_2_text_view) + rounds);

        if(pointsTimeRatio > currentHighScore){

            saveHighScoreButton.setVisibility(View.VISIBLE);
            highScoreTextView.setText(getResources().getString(R.string.new_highscore));
            currentHighScoreTextView.setText(currentHighScore == 0 ? getResources().getString(R.string.no_highscore_yet) : getResources().getString(R.string.previous_high_score) + df2.format(currentHighScore) + getResources().getString(R.string.points_per_second_by) + currentScoreHolder);

        }else{

            saveHighScoreButton.setVisibility(View.INVISIBLE);
            highScoreTextView.setText(getResources().getString(R.string.can_do_better));
            currentHighScoreTextView.setText(currentHighScore == 0 ? getResources().getString(R.string.no_highscore_yet) : getResources().getString(R.string.current_high_score) + df2.format(currentHighScore) + getResources().getString(R.string.points_per_second_by) + currentScoreHolder);

        }
        switch(exerciseType){

            case 1:
                modeTextView.setText(getResources().getString(R.string.played_mode_text_view) + getResources().getString(R.string.addition_button));
                break;

            case 2:
                modeTextView.setText(getResources().getString(R.string.played_mode_text_view) + getResources().getString(R.string.subtraction_button));
                break;

            case 3:
                modeTextView.setText(getResources().getString(R.string.played_mode_text_view) + getResources().getString(R.string.multiplication_button));
                break;

            case 4:
                modeTextView.setText(getResources().getString(R.string.played_mode_text_view) + getResources().getString(R.string.division_button));
                break;
        }
        switch(difficulty){

            case 1:
                difficultyTextView.setText(getResources().getString(R.string.difficulty_2_text_view) + getResources().getString(R.string.easy_text_view));
                break;

            case 2:
                difficultyTextView.setText(getResources().getString(R.string.difficulty_2_text_view) + getResources().getString(R.string.hard_text_view));
                break;
        }
    }


    private void startMainActivity(){

        Intent MainActivityIntent = new Intent(EndResultActivity.this, MainActivity.class);
        startActivity(MainActivityIntent);
        finish();
    }


    //Read from SharedPreferences
    private String[] getHighscore(int d, int et){

        String prefScore;

        switch(et){

            case 1:
                prefScore = highScores.getString(d == 1?HIGHSCORE_ADDITION_EASY:HIGHSCORE_ADDITION_HARD, "x/xZx/0");
                break;

            case 2:
                prefScore = highScores.getString(d == 1?HIGHSCORE_SUBTRACTION_EASY:HIGHSCORE_SUBTRACTION_HARD, "x/xZx/0");
                break;

            case 3:
                prefScore = highScores.getString(d == 1?HIGHSCORE_MULTIPLICATION_EASY:HIGHSCORE_MULTIPLICATION_HARD, "x/xZx/0");
                break;

            case 4:
            prefScore = highScores.getString(d == 1?HIGHSCORE_DIVISION_EASY:HIGHSCORE_DIVISION_HARD, "x/xZx/0");
            break;

            default:
                prefScore = "x/xZx/0";
        }

        return  prefScore.split("/xZx/");
    }


    //Write to SharedPreferences
    private void setHighscore(String n, double newHighscore, int d){

        String savedHighscore = n + "/xZx/" + newHighscore;

        SharedPreferences.Editor editor = highScores.edit();

        switch(exerciseType){

            case 1:
                editor.putString(d == 1?HIGHSCORE_ADDITION_EASY:HIGHSCORE_ADDITION_HARD, savedHighscore);
                break;

            case 2:
                editor.putString(d == 1?HIGHSCORE_SUBTRACTION_EASY:HIGHSCORE_SUBTRACTION_HARD, savedHighscore);
                break;

            case 3:
                editor.putString(d == 1?HIGHSCORE_MULTIPLICATION_EASY:HIGHSCORE_MULTIPLICATION_HARD, savedHighscore);
                break;

            case 4:
                editor.putString(d == 1? HIGHSCORE_DIVISION_EASY:HIGHSCORE_DIVISION_HARD, savedHighscore);
                break;
        }

        editor.commit();
    }


    private View.OnClickListener onHighscoreSaveClicked = new View.OnClickListener() {

        public void onClick(final View view) {

            final AlertDialog.Builder saveDialog = new AlertDialog.Builder(EndResultActivity.this);

            saveDialog.setTitle(getResources().getString(R.string.save_highscore_button));
            saveDialog.setMessage(getResources().getString(R.string.enter_name));

            final EditText nameInput = new EditText(EndResultActivity.this);
            nameInput.setHint(getResources().getString(R.string.name_hint));
            nameInput.setInputType(InputType.TYPE_CLASS_TEXT);
            saveDialog.setView(nameInput);
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            saveDialog.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {

                    if(nameInput.getText().toString().length() > 0){

                        name = nameInput.getText().toString();
                        setHighscore(name, pointsTimeRatio, difficulty);
                        Toast.makeText(EndResultActivity.this, getResources().getString(R.string.saved), Toast.LENGTH_LONG).show();
                        saveHighScoreButton.setVisibility(View.INVISIBLE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    }else{

                        Toast.makeText(EndResultActivity.this, getResources().getString(R.string.enter_name_first_toast), Toast.LENGTH_LONG).show();

                    }

                    }
                });

            saveDialog.setNegativeButton(getResources().getString(R.string.dont_save), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {

                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    }
                });

            saveDialog.show();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    };


    private View.OnClickListener onHighScoreButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent highScoreIntent = new Intent(EndResultActivity.this, HighScoreActivity.class);
            startActivity(highScoreIntent);

        }
    };


    private View.OnClickListener onPlayAgainClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(pointsTimeRatio < currentHighScore){
                Toast.makeText(EndResultActivity.this, getResources().getString(R.string.need_that_toast), Toast.LENGTH_LONG).show();
            }
            startMainActivity();

        }
    };


    /*@Override
    public void onBackPressed(){                        //Not necessary anymore

       startMainActivity();

    } */

}
