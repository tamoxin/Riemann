package com.example.marco.riemann;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView answer;
    EditText functionET, fromET, toET, rectanglesET;
    Spinner spinner;
    Button solveBtn;
    double from, to, base, height;
    int rectanglesNo;
    String function;
    int valuesFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link variables with UI
        answer = (TextView) findViewById(R.id.answer);
        fromET = (EditText) findViewById(R.id.from);
        toET = (EditText) findViewById(R.id.to);
        rectanglesET = (EditText) findViewById(R.id.rectaglesNo);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.position, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        solveBtn = (Button) findViewById(R.id.solveBtn);
        functionET = (EditText) findViewById(R.id.algFunction);
    }

    public void setValues() {
        rectanglesNo = Integer.parseInt(rectanglesET.getText().toString());
        function = functionET.getText().toString();
        to = Double.parseDouble(toET.getText().toString());
        from = Double.parseDouble(fromET.getText().toString());
        base = (this.to - this.from)/this.rectanglesNo;
        height = 0;
    }

    public void solve() {
        double[] coefficients;
        int[] pows;
        double area = 0;
        double x;
        FunctionReader reader = new FunctionReader(function);

        pows = reader.getPows();
        coefficients = reader.splitIntoCoefficients();

        if (valuesFlag == 0)
            x = from;
        else if (valuesFlag == 1) {
            x = from + base;
            to += base;
        } else
            x = from + base / 2;

        while (x < to) {
            for (int pos = 0; pos < pows.length; pos++) {
                height += coefficients[pos] * (Math.pow(x, pows[pos]));
            }
            x += base;
        }
        if ((Double) height == Double.POSITIVE_INFINITY) {
            throwError(2);
            return;
        }
        area += base * height;

        answer.setText(""+area);
    }

    /**
     * Shows a Toast with an error message.
     *
     * @param errorId the error identifier
     */
    public void throwError(int errorId) {
        String error;

        switch (errorId) {
            case 1:
                error = getString(R.string.flip_range_error);
                String aux = toET.getText().toString();
                toET.setText(fromET.getText().toString());
                fromET.setText(aux);
                break;
            case 2:
                error = getString(R.string.integer_power_error);
                break;
            case 3:
                error = getString(R.string.non_polynomial_error);
                break;
            case 4:
                error = getString(R.string.all_fields_required_error);
                break;
            case 5:
                error = getString(R.string.too_many_rectangles_error);
                rectanglesET.setText("1000");
                break;
            default:
                error = getString(R.string.default_error);
        }

        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    public boolean areFieldsEmpty() {
        return functionET.getText().toString().equals("") || fromET.getText().toString().equals("")
                || toET.getText().toString().equals("") || rectanglesET.getText().toString().equals("");
    }

    public void onClick(View view) {

        //Check for errors
        if(areFieldsEmpty()) {
            throwError(4);
            return;
        }

        if(this.from > this.to)
            throwError(1);

        if(Integer.parseInt(rectanglesET.getText().toString()) > 1000)
            throwError(5);
        ////////////////////////

        setValues();
        solve();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected. You can retrieve the position of the selected item using
        //parent.getPositionForView(view)
        valuesFlag = parent.getPositionForView(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
