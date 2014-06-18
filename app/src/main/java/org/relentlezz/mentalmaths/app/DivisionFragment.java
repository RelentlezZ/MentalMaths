package org.relentlezz.mentalmaths.app;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DivisionFragment extends Fragment {


    public DivisionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.fragment_division, container, false);

        SharedPreferences highscores = getActivity().getSharedPreferences("highScores", Context.MODE_PRIVATE);
        String[] easyArray = highscores.getString(EndResultActivity.HIGHSCORE_DIVISION_EASY, "x/xZx/0").split("/xZx/");
        String[] hardArray = highscores.getString(EndResultActivity.HIGHSCORE_DIVISION_HARD, "x/xZx/0").split("/xZx/");
        double pointsEasy = Double.parseDouble(easyArray[1]);
        double pointsHard = Double.parseDouble(hardArray[1]);
        TextView easyTextView = (TextView) view.findViewById(R.id.divisionEasyScoreTextView);
        TextView hardTextView = (TextView) view.findViewById(R.id.divisionHardScoreTextView);
        if(pointsEasy != 0) {
            easyTextView.setText(EndResultActivity.df2.format(pointsEasy) + getResources().getString(R.string.points_per_second_by) + easyArray[0]);
        }else{
            easyTextView.setText(getResources().getString(R.string.no_highscore_yet));
        }
        if(pointsHard != 0){
            hardTextView.setText(EndResultActivity.df2.format(pointsHard) + getResources().getString(R.string.points_per_second_by) + hardArray[0]);
        }else{
            hardTextView.setText(getResources().getString(R.string.no_highscore_yet));
        }

        return view;
    }


}
