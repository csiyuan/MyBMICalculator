package sg.edu.rpc346.id19016011.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalc;
    Button btnReset;
    TextView tvBMI;
    TextView tvDate;
    TextView tvDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editWeight);
        etHeight = findViewById(R.id.editHeight);
        btnCalc = findViewById(R.id.buttoncalc);
        btnReset = findViewById(R.id.buttonreset);
        tvDate = findViewById(R.id.date);
        tvBMI = findViewById(R.id.bmi);
        tvDisplay = findViewById(R.id.display);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText(" ");
                etHeight.setText("");
            }
        });

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double weight = Float.parseFloat(etWeight.getText().toString());
                double height = Float.parseFloat(etHeight.getText().toString());
                double BMI = weight/(height * height);
                String output =String.format("%.3f",BMI);
                tvBMI.setText("Last Calculated BMI: " + output);
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);
                tvDate.setText(getString(R.string.date) + datetime);

                if(BMI < 18.5){
                    tvDisplay.setText("You are underweight");
                }
                else if(BMI >= 18.5 && BMI <=24.9){
                    tvDisplay.setText("Your BMI is average");
                }
                else if(BMI>= 25 && BMI<= 29.9){
                    tvDisplay.setText("You are overweight");
                }
                else{
                    tvDisplay.setText("You are obese");
                }

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("Last Calculated Date: ", datetime);
                prefEdit.putString("Last Calculate BMI: ", String.valueOf(BMI));
                prefEdit.commit();
                tvBMI.setText(BMI + "");
                tvDate.setText(datetime);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        String datetime = tvDate.getText().toString();
        Float BMI = Float.parseFloat(tvBMI.getText().toString());
        String lastOutcome = tvDisplay.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("Last Calculated Date: ", datetime);
        prefEdit.putFloat("Last Calculate BMI: ", BMI);
        prefEdit.putString("Last Outcome", lastOutcome);
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("Date", "");
        float BMI = prefs.getFloat("Last BMI", 0);
        String outcome = prefs.getString("Last outcome", "");
        tvBMI.setText("Last calculated BMI: " + BMI);
        tvDate.setText("Last Calculated Date: " + date);
        tvDisplay.setText("Last outcome: " + outcome);
    }
}