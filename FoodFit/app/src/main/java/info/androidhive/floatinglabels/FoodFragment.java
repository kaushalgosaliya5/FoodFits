package info.androidhive.floatinglabels;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {

    private Button food, steps;
    String[][] avg = new String[7][2];
    TableLayout tl;
    TableRow tr;


    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        String[] spaceProbeHeaders={"Name","Value"};

        int[] calorie = new int[]{136, 81, 89, 52, 47, 17, 57};
        double[] fat = new double[]{9.6, 2.12, 0.33, 0.17, 0.12, 0.24, 0.86};
        double[] carbs = new double[]{7.2, 12.74, 22.84, 13.81, 11.75, 3.2, 13.16};
        double[] fiber = new double[]{19.2, 11.12, 4.36, 1.04, 3.76, 6.08, 2.68};
        double[] protein = new double[]{4.8, 2.78, 1.09, 0.26, 0.94, 1.52, 0.67};
        double[] vita = new double[]{4.8, 85, 1, 1, 4, 101, 1};
        int[] vitc = new int[]{8, 5, 14, 8, 89, 26, 59};
        int[] calcium = new int[]{12, 2, 0, 1, 4, 5, 1};
        int[] iron = new int[]{16, 4, 1, 1, 1, 6, 0};


        int i;
        double fat_avg = 0.0, carbs_avg = 0.0, fiber_avg = 0.0, pro_avg = 0.0, vita_avg = 0.0, cal_avg = 0, vitc_avg = 0, calc_avg = 0, iron_avg = 0;

        for (i = 0; i < 7; i++) {
            cal_avg = cal_avg + calorie[i];
            fat_avg = fat_avg + fat[i];
            carbs_avg = carbs_avg + carbs[i];
            fiber_avg = fiber_avg + fiber[i];
            pro_avg = pro_avg + protein[i];
            vita_avg = vita_avg + vita[i];
            vitc_avg = vitc_avg + vitc[i];
            calc_avg = calc_avg + calcium[i];
            iron_avg = iron_avg + iron[i];
        }

        cal_avg = calc_avg / 7;
        fat_avg = fat_avg / 7;
        carbs_avg = carbs_avg / 7;
        fiber_avg = fiber_avg / 7;
        pro_avg = pro_avg / 7;
        vita_avg = vita_avg / 7;
        vitc_avg = vitc_avg / 7;
        calc_avg = calc_avg / 7;
        iron_avg = iron_avg / 7;

        final String[][] avg = {
                {"Avg.Calorie Intake" ,String.format("%.2f",cal_avg)},
                {"Avg.Fat Intake",String.format("%.2f",fat_avg)},
                {"Avg.Carb Intake",String.format("%.2f",carbs_avg)},
                {"Avg.Fibre Intake",String.format("%.2f",fiber_avg)},
                {"Avg.protein Intake",String.format("%.2f",pro_avg)},
                {"Avg.Vita A Intake",String.format("%.2f",vita_avg)},
                {"Avg.Vita C Intake",String.format("%.2f",vitc_avg)},
                {"Avg.Calcium Intake",String.format("%.2f",calc_avg)},
                {"Avg.Iron Intake",String.format("%.2f",iron_avg)}
        };

        final TableView<String[]> tableView = (TableView<String[]>) view.findViewById(R.id.tableView);
        //SET PROP
        tableView.setHeaderBackgroundColor(Color.parseColor("#ff944d"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(),spaceProbeHeaders));
        tableView.setColumnCount(2);

        tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(),avg));

//        food = (Button) view.findViewById(R.id.food_btn);
//
//
//        food.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });
        return view;
    }


}
