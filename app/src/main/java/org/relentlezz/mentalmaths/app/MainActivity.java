package org.relentlezz.mentalmaths.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends Activity {

    //Declare constants
    public final static String EXTRA_MESSAGE = "org.relentlezz.mentalmaths.app.EXTRA_MESSAGE";          //Key pair for extra message

    public final static String ENTERED_DIFFICULTY = "org.relentlezz.mentalmaths.app.ENTERED_DIFFICULTY";     //Key pairs for shared prefs
    public final static String ENTERED_NUMBER_RANGE = "org.relentlezz.mentalmaths.app.ENTERED_NUMBER_RANGE";
    public final static String ENTERED_ROUNDS = "org.relentlezz.mentalmaths.app.ENTERED_ROUNDS";

    //Declare variables
    private String[] intentParams = new String[4]; //Array for passing extra message
    String difficulty, numberRange, rounds, exerciseType;
    Toast toast;
    boolean doubleBack;

    private SharedPreferences enteredParams;

    //Declare views
    Spinner numberRangeSpinner;
    Spinner roundsSpinner;
    Button additionButton;
    Button subtractionButton;
    Button multiplicationButton;
    Button divisionButton;
    RadioGroup radioGroup;
    RadioButton easyRadio;
    RadioButton hardRadio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize variables
        enteredParams = getSharedPreferences("settings", MODE_PRIVATE);

        difficulty = enteredParams.getString(ENTERED_DIFFICULTY, "0");
        numberRange = enteredParams.getString(ENTERED_NUMBER_RANGE, "100");
        rounds = enteredParams.getString(ENTERED_ROUNDS, "10");

        intentParams[0] = difficulty;
        intentParams[1] = numberRange;
        intentParams[2] = rounds;

        //Initialize the views
        easyRadio = (RadioButton) findViewById(R.id.easyRadio);
        hardRadio = (RadioButton) findViewById(R.id.hardRadio);

        if(difficulty.equals("0")){
            easyRadio.setChecked(true);
        }else{
            hardRadio.setChecked(true);
        }

        radioGroup = (RadioGroup) findViewById(R.id.radioWrapper);
        radioGroup.setOnCheckedChangeListener(onDifficultyRadioChanged);

        numberRangeSpinner = (Spinner) findViewById(R.id.numberRangeSpinner);

            switch (Integer.parseInt(numberRange)){

                case 50:
                    numberRangeSpinner.setSelection(0);
                    break;

                case 100:
                    numberRangeSpinner.setSelection(1);
                    break;

                case 150:
                    numberRangeSpinner.setSelection(2);
                    break;

                case 200:
                    numberRangeSpinner.setSelection(3);
                    break;

                case 250:
                    numberRangeSpinner.setSelection(4);
                    break;

                case 300:
                    numberRangeSpinner.setSelection(5);
                    break;

            }

            numberRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    numberRange = numberRangeSpinner.getSelectedItem().toString();
                    intentParams[1] = numberRange;                                           //Set number range based on user input
                    updatePreferences();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        roundsSpinner = (Spinner) findViewById(R.id.roundsSpinner);

            switch (Integer.parseInt(rounds)){             //Standard selection of roundsSpinner

                case 5:
                    roundsSpinner.setSelection(0);
                    break;

                case 10:
                    roundsSpinner.setSelection(1);
                    break;

                case 15:
                    roundsSpinner.setSelection(2);
                    break;

                case 20:
                    roundsSpinner.setSelection(3);
                    break;

                case 25:
                    roundsSpinner.setSelection(4);
                    break;

                case 30:
                    roundsSpinner.setSelection(5);
                    break;

            }

            roundsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    rounds = roundsSpinner.getSelectedItem().toString();
                    intentParams[2] = rounds;                                           //Set rounds based on user input
                    updatePreferences();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });                                //SpinnerListener

        additionButton = (Button) findViewById(R.id.additionButton);
        subtractionButton = (Button) findViewById(R.id.subtractionButton);
        multiplicationButton = (Button) findViewById(R.id.multiplicationButton);
        divisionButton = (Button) findViewById(R.id.divisionButton);

            additionButton.setOnClickListener(onButtonsClicked);                //Set ButtonListeners
            subtractionButton.setOnClickListener(onButtonsClicked);
            multiplicationButton.setOnClickListener(onButtonsClicked);
            divisionButton.setOnClickListener(onButtonsClicked);

    }


    private void updatePreferences() {

        SharedPreferences.Editor editor = enteredParams.edit();

        editor.putString(ENTERED_DIFFICULTY, difficulty);
        editor.putString(ENTERED_NUMBER_RANGE, numberRange);
        editor.putString(ENTERED_ROUNDS, rounds);

        editor.apply();

    }

    private RadioGroup.OnCheckedChangeListener onDifficultyRadioChanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch(i){
                case R.id.easyRadio:
                    difficulty = "0";
                    break;
                case R.id.hardRadio:
                    difficulty = "1";
                    break;
            }
            intentParams[0] = difficulty;
            updatePreferences();
        }
    };

    private View.OnClickListener onButtonsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, ExercisesActivity.class);

            if(numberRange.equals("")){

                if(toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(MainActivity.this, R.string.number_range_toast_error, Toast.LENGTH_SHORT);
                toast.show();

            }else if(Integer.parseInt(numberRange) < 50){

                if(toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.too_easy_toast), Toast.LENGTH_SHORT);
                toast.show();

            }else {

                updatePreferences();

                switch (view.getId()) {

                    case R.id.additionButton:                               //Choosing exerciseType based on user input
                        //Start ExercisesActivity with intent + extra
                        exerciseType = "1";
                        intentParams[3] = exerciseType;
                        intent.putExtra(EXTRA_MESSAGE, intentParams);
                        startActivity(intent);

                        break;

                    case R.id.subtractionButton:
                        exerciseType = "2";
                        intentParams[3] = exerciseType;
                        intent.putExtra(EXTRA_MESSAGE, intentParams);
                        startActivity(intent);

                        break;

                    case R.id.multiplicationButton:
                        if(Integer.parseInt(numberRange) < 500 && difficulty.equals("1")){

                            Toast.makeText(MainActivity.this, getResources().getString(R.string.multiplication_toast), Toast.LENGTH_SHORT).show();
                            easyRadio.setChecked(true);
                            difficulty = "0";

                        }
                        exerciseType = "3";
                        intentParams[3] = exerciseType;
                        intent.putExtra(EXTRA_MESSAGE, intentParams);
                        startActivity(intent);

                        break;

                    case R.id.divisionButton:
                        if(difficulty.equals("1")){

                            Toast.makeText(MainActivity.this, getResources().getString(R.string.division_not_available_toast), Toast.LENGTH_SHORT).show();
                            easyRadio.setChecked(true);
                            difficulty = "0";

                        }

                        exerciseType = "4";
                        intentParams[3] = exerciseType;
                        intent.putExtra(EXTRA_MESSAGE, intentParams);
                        startActivity(intent);

                        break;
                }
            }
        }
    };

}