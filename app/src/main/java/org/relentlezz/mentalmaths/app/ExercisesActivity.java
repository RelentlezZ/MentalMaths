package org.relentlezz.mentalmaths.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class ExercisesActivity extends Activity {

    //Declare constants
    public final static String EXERCISES_EXTRA = "org.relentlezz.mentalmaths.app.EXERCISES_EXTRA"; //Key pairs for extra messages
    public final static String HIGHSCORE_EXTRA = "org.relentlezz.mentalmaths.app.HIGHSCORE_EXTRA";
    public final static String POINTS_EXTRA = "org.relentlezz.mentalmaths.app.POINTS_EXTRA";
    public final static String TIME_EXTRA = "org.relentlezz.mentalmaths.app.TIME_EXTRA";

    //Declare variables
    private int[] intentParams = new int[5];

    int difficulty, numberRange, rounds, exerciseType, iterations, result, userResult, num1, num2, num3, max, max2, min;
    double points, pointsTimeRatio;
    long time;
    String userResultStr;
    public static Toast toast;

    public static DecimalFormat df = new DecimalFormat("###0");

    Random random = new Random();

    //Declare views
    TextView exerciseTitle;
    TextView exerciseNumber;
    TextView exercise;
    TextView pointsTextView;

    EditText resultEditText;

    Button checkButton;
    Button oneButton;
    Button twoButton;
    Button threeButton;
    Button fourButton;
    Button fiveButton;
    Button sixButton;
    Button sevenButton;
    Button eightButton;
    Button nineButton;
    Button zeroButton;
    Button clearButton;
    ImageButton backButton;

    Chronometer timeChronometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);



        //Initialize variables
        Intent oldIntent = getIntent();                                                          //Get intent from MainActivity
        String [] passedParams = oldIntent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);      //Get EXTRA_MESSAGE

        difficulty = Integer.parseInt(passedParams[0]);
        numberRange = Integer.parseInt(passedParams[1]);
        rounds = Integer.parseInt(passedParams[2]);
        exerciseType = Integer.parseInt(passedParams[3]);
        iterations = 1;
        points = 0;
        pointsTimeRatio = 0;
        difficulty++;


        //Initialize views
        exerciseTitle = (TextView) findViewById(R.id.exerciseTitleTextView);
        exerciseNumber = (TextView) findViewById(R.id.exerciseNumberTextView);
        exercise = (TextView) findViewById(R.id.exerciseTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);

        resultEditText = (EditText) findViewById(R.id.resultEditText);

        checkButton = (Button) findViewById(R.id.checkButton);
        oneButton = (Button) findViewById(R.id.oneButton);
        twoButton = (Button) findViewById(R.id.twoButton);
        threeButton = (Button) findViewById(R.id.threeButton);
        fourButton = (Button) findViewById(R.id.fourButton);
        fiveButton = (Button) findViewById(R.id.fiveButton);
        sixButton = (Button) findViewById(R.id.sixButton);
        sevenButton = (Button) findViewById(R.id.sevenButton);
        eightButton = (Button) findViewById(R.id.eightButton);
        nineButton = (Button) findViewById(R.id.nineButton);
        zeroButton = (Button) findViewById(R.id.zeroButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        backButton = (ImageButton) findViewById(R.id.backButton);

        timeChronometer = (Chronometer) findViewById(R.id.timeChronometer);

        //Set Listeners
        resultEditText.addTextChangedListener(onUserResultChanged);

        checkButton.setOnClickListener(onCheckButtonClicked);
        oneButton.setOnClickListener(onNumbersClicked);
        twoButton.setOnClickListener(onNumbersClicked);
        threeButton.setOnClickListener(onNumbersClicked);
        fourButton.setOnClickListener(onNumbersClicked);
        fiveButton.setOnClickListener(onNumbersClicked);
        sixButton.setOnClickListener(onNumbersClicked);
        sevenButton.setOnClickListener(onNumbersClicked);
        eightButton.setOnClickListener(onNumbersClicked);
        nineButton.setOnClickListener(onNumbersClicked);
        zeroButton.setOnClickListener(onNumbersClicked);
        clearButton.setOnClickListener(onNumbersClicked);
        backButton.setOnClickListener(onNumbersClicked);

        //Set Text in TextViews
        pointsTextView.setText(getResources().getString(R.string.points_text_view) + "  " + df.format(points));

        switch(exerciseType){

            case 1:
                exerciseTitle.setText(getResources().getString(R.string.addition_button));
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.blue));
                startAdditionExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 2:
                exerciseTitle.setText(getResources().getString(R.string.subtraction_button));
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.red));
                startSubtractionExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 3:
                exerciseTitle.setText(getResources().getString(R.string.multiplication_button));
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.yellow));
                startMultiplicationExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 4:
                exerciseTitle.setText(getResources().getString(R.string.division_button));
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.turqoise));
                startDivisionExercises(difficulty, numberRange, rounds, iterations);

                break;
        }
    }


    private void startAdditionExercises(int d, int n, int r, int i){

        iterations++;
        timeChronometer.start();

        if(i == r + 1){

            timeChronometer.stop();
            time = SystemClock.elapsedRealtime() - timeChronometer.getBase();
            viewEndResult(time);

        }else{

            exerciseNumber.setText(getResources().getString(R.string.exercise_number_text_view) + " " + i + "/" + r);
            generateAdditionExercises(d, n);

        }
    }


    private void startSubtractionExercises(int d, int n, int r, int i){

        iterations++;
        timeChronometer.start();

        if(i == r + 1){

            timeChronometer.stop();
            time = SystemClock.elapsedRealtime() - timeChronometer.getBase();
            viewEndResult(time);


        }else{

            exerciseNumber.setText(getResources().getString(R.string.exercise_number_text_view) + " " + i + "/" + r);
            generateSubtractionExercises(d, n);

        }
    }


    private void startMultiplicationExercises(int d, int n, int r, int i){

        iterations++;
        timeChronometer.start();

        if(i == r + 1){

            timeChronometer.stop();
            time = SystemClock.elapsedRealtime() - timeChronometer.getBase();
            viewEndResult(time);

        }else{

            exerciseNumber.setText(getResources().getString(R.string.exercise_number_text_view) + " " + i + "/" + r);
            generateMultiplicationExercises(d, n);
        }
    }


    private void startDivisionExercises(int d, int n, int r, int i){

        iterations++;
        timeChronometer.start();

        if(i == r + 1){

            timeChronometer.stop();
            time = SystemClock.elapsedRealtime() - timeChronometer.getBase();
            viewEndResult(time);

        }else{

            exerciseNumber.setText(getResources().getString(R.string.exercise_number_text_view) + " " + i + "/" + r);
            generateDivisionExercises(d, n);
        }
    }


    private void generateAdditionExercises(int d, int n){

        switch(d){

            case 1:
                max = (int) (Math.round(n * 0.9));
                num1 = random.nextInt(max) + 1;
                num2 = random.nextInt(n - num1) + 1;
                result = num1 + num2;

                exercise.setText(num1 + " + " + num2 + " =");


                break;

            case 2:
                max = (int) (Math.round(n * 0.5));
                max2 = (int) (Math.round(n * 0.3));
                num1 = random.nextInt(max);
                num2 = random.nextInt(max2);
                num3 = random.nextInt(n - (num1 + num2));
                result = num1 + num2 + num3;

                exercise.setText(num1 + " + " + num2 +  " + " + num3 +" =");

                break;
        }
    }


    private void generateSubtractionExercises(int d, int n){

        switch(d){

            case 1:
                min = (int) (Math.round(n * 0.1));
                num1 = random.nextInt(n - min) + min;
                num2 = random.nextInt(num1 - 1) + 1;
                result = num1 - num2;

                exercise.setText(num1 + " - " + num2 + " =");

                break;

            case 2:
                min = (int) (Math.round(n * 0.5));
                num1 = random.nextInt(min) + min;
                num2 = random.nextInt((int) (Math.round(num1 * 0.5)));
                num3 = random.nextInt(num2) + 1;
                result = num1 - num2 - num3;

                exercise.setText(num1 + " - " + num2 + " - " + num3 + " =");

                break;
        }
    }


    private void generateMultiplicationExercises(int d, int n){

        switch(d){

            case 1:
                max = (int) (Math.sqrt((double) n));
                num1 = random.nextInt(max) + 1;
                num2 = random.nextInt(max) + 1;
                result = num1 * num2;

                exercise.setText(num1 + " x " + num2 + " =");

                break;

            case 2:
                max = (int) (Math.sqrt(Math.sqrt((double) n)));
                max2 = (int) (Math.sqrt((double) n));
                num1 = random.nextInt(max2) + 1;
                num2 = random.nextInt(max) + 1;
                num3 = random.nextInt(max) + 1;
                result = num1 * num2 * num3;

                exercise.setText(num1 + " * " + num2 + " * " + num3 + " =");

                break;
        }
    }


    private void generateDivisionExercises(int d, int n){

        switch(d){

            case 1:

                max = (int) (Math.round(0.2 * n));
                num1 = random.nextInt(max) + 1;
                max2 = n / num1;
                num2 = (random.nextInt(max2) + 1) * num1;
                result = num2/ num1;

                exercise.setText(num2 + " / " + num1 + " =");

                break;

            case 2:

                break;
        }
    }


    private void checkResult(int r, int ur){

        if(toast != null){
            toast.cancel();
        }

        if(r == ur){


            toast = Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.right_toast), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -30 );
            toast.show();

            points = points + (60 * (2.5 * (numberRange / 50) * difficulty) / rounds);      //Points calculation
            pointsTextView.setText(getResources().getString(R.string.points_text_view) + "  " + df.format(points));

        }else{


            toast = Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.wrong_toast), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -30);
            toast.show();

        }

        resultEditText.setText("");

        switch(exerciseType){

            case 1:
                startAdditionExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 2:
                startSubtractionExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 3:
                startMultiplicationExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 4:
                startDivisionExercises(difficulty, numberRange, rounds, iterations);

                break;
        }
    }


    private void  viewEndResult(long t){


        intentParams[0] = difficulty;
        intentParams[1] = numberRange;
        intentParams[2] = rounds;
        intentParams[3] = exerciseType;

        pointsTimeRatio = (points / (0.001 * t) );

        Intent intent =  new Intent(ExercisesActivity.this, EndResultActivity.class);
        intent.putExtra(EXERCISES_EXTRA, intentParams);
        intent.putExtra(HIGHSCORE_EXTRA, pointsTimeRatio);
        intent.putExtra(POINTS_EXTRA, points);
        intent.putExtra(TIME_EXTRA, t);

        startActivity(intent);
        finish();

    }


    private void restart(){

        points = 0;
        iterations = 1;
        pointsTextView.setText(getResources().getString(R.string.points_text_view) + " " + df.format(points));
        resultEditText.setText("");
        timeChronometer.stop();
        timeChronometer.setBase(SystemClock.elapsedRealtime());
        timeChronometer.start();

        switch(exerciseType){

            case 1:
                startAdditionExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 2:
                startSubtractionExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 3:
                startMultiplicationExercises(difficulty, numberRange, rounds, iterations);

                break;

            case 4:
                startDivisionExercises(difficulty, numberRange, rounds, iterations);

                break;
        }
    }


    private TextWatcher onUserResultChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            userResultStr = charSequence.toString();                                //ParseInt in onCheckButtonClicked method

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    private View.OnClickListener onCheckButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(resultEditText.getText().toString().equals("")){

                if(toast != null){
                    toast.cancel();
                }
                toast = Toast.makeText(ExercisesActivity.this, getResources().getString(R.string.result_toast_error), Toast.LENGTH_SHORT);
                toast.show();

            }else{

                userResult = Integer.parseInt(userResultStr);                               //ParseInt on clicked button to prevent FC
                checkResult(result, userResult);

            }
        }
    };


    private View.OnClickListener onNumbersClicked = new View.OnClickListener() {            //Listener for number pad
        @Override
        public void onClick(View view) {

            switch(view.getId()){

                case R.id.oneButton:
                    resultEditText.append("1");

                    break;

                case R.id.twoButton:
                    resultEditText.append("2");

                    break;

                case R.id.threeButton:
                    resultEditText.append("3");

                    break;

                case R.id.fourButton:
                    resultEditText.append("4");

                    break;

                case R.id.fiveButton:
                    resultEditText.append("5");

                    break;

                case R.id.sixButton:
                    resultEditText.append("6");

                    break;

                case R.id.sevenButton:
                    resultEditText.append("7");

                    break;

                case R.id.eightButton:
                    resultEditText.append("8");

                    break;

                case R.id.nineButton:
                    resultEditText.append("9");

                    break;

                case R.id.zeroButton:
                    resultEditText.append("0");

                    break;

                case R.id.clearButton:
                    resultEditText.setText("");

                    break;

                case R.id.backButton:
                    String backString = resultEditText.getText().toString();

                    if(backString.length() > 1){

                        backString = backString.substring(0, (backString.length() - 1));
                        resultEditText.setText(backString);

                    }else if(backString.length() <= 1){                                     //Necessary for preventing FC

                        resultEditText.setText("");

                    }
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.excercises, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.action_restart:
                restart();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
