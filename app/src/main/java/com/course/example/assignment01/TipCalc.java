/*
 * Enter dinner bill, number of diners, and a tip is calculated (or, leave blank and 15% tip is calculated).
 * Program outputs the bill total, tip total, and amounts per person. 
 * This example uses a listener implemented by the Activity class.
 * Another alternative is using an anonymous class.
 */
package com.course.example.assignment01;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class TipCalc extends Activity implements OnClickListener {

	/* Define the user inputs */
	private EditText mealpricefield;
	private EditText dinersAnswer;
	private EditText tipPercentageAnswer;

	private Button button;

	/* Define the outputs to the TextView fields */
	private TextView totalBillAnswer;
	private TextView totalPerPersonAnswer;
	private TextView totalTipAnswer;
	private TextView tipPerPersonAnswer;

	private static final String tag = "Tips";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		/* Set view name as main from res/layout */
		setContentView(R.layout.main);

		/* Set the contents of the EditText elements to the user input variables created above. */
		mealpricefield = (EditText) findViewById(R.id.mealprice);
		dinersAnswer = (EditText) findViewById(R.id.dinersAnswer);
		tipPercentageAnswer = (EditText) findViewById(R.id.tipPercentageAnswer);

		/* Set the contents of the TextView elements to the output variables created above. */
		totalBillAnswer = (TextView) findViewById(R.id.totalBillAnswer);
		totalPerPersonAnswer = (TextView) findViewById(R.id.totalPerPersonAnswer);
		totalTipAnswer = (TextView) findViewById(R.id.totalTipAnswer);
		tipPerPersonAnswer = (TextView) findViewById(R.id.tipPerPersonAnswer);

		button = (Button) findViewById(R.id.calculate);

		button.setOnClickListener(this);
	}

	// Perform action on click
	public void onClick(View v) {
		try {

			Log.i(tag, "onClick invoked.");

			// grab the meal price from the UI
			String mealprice = mealpricefield.getText().toString();
			Log.i(tag, "mealprice is $" + mealprice);

			String numberOfDiners = dinersAnswer.getText().toString();
			Log.i(tag, "Number of diners: " + numberOfDiners);

			String tipPercent = tipPercentageAnswer.getText().toString();
			Log.i(tag, "Tip percentage: " + tipPercent);

			// check to see if the meal price includes a "$"
			if (mealprice.contains("$"))
					mealprice = mealprice.substring(1);

			if(tipPercent.contains("%"))
				tipPercent = tipPercent.substring(0,(tipPercent.length()-1));

			if(tipPercent.isEmpty())
				tipPercent = "15";

			float fmp = Float.parseFloat(mealprice);
			int diners = Integer.parseInt(numberOfDiners);
			int tip = Integer.parseInt(tipPercent);

			double totalTip = fmp * (tip * 0.01);
			double totalMealprice = fmp + totalTip;
			double tipPP = totalTip / diners;
			double totalPP = totalMealprice / diners;

			// display the answers
			Log.i(tag, "onClick complete.");
			totalBillAnswer.setText(String.format("$%.2f", totalMealprice));
			totalTipAnswer.setText(String.format("$%.2f",totalTip));
			totalPerPersonAnswer.setText(String.format("$%.2f",totalPP));
			tipPerPersonAnswer.setText(String.format("$%.2f",tipPP));

		} catch (Exception e) {
			Log.e(tag, "Failed to Calculate Tip:" + e.getMessage());
			totalBillAnswer.setText("Failed to Calculate Tip");
			totalTipAnswer.setText("Failed to Calculate Tip");
			totalPerPersonAnswer.setText("Failed to Calculate Tip");
			tipPerPersonAnswer.setText("Failed to Calculate Tip");
		}

	}
}
