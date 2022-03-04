/*
 * Author: Peter Arsenault
 * March 3, 2022
 * CS680 Assignment 2
 * Description: Tip calc functionality remains, added a web view activity,
 *  and two intents for dialing a phone number and showing a map.
 *
 */
package com.course.example.assignment02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class TipCalc extends Activity implements OnClickListener {
	// Define request code number to identify a request.
	public static final int requestCode_235 = 235;

	/* Define the user inputs */
	private EditText mealpricefield;
	private EditText dinersAnswer;
	private EditText tipPercentageAnswer;

	private Button button;
	/* Incorporate Assignment 02 Functionality,
	*  Define the additional buttons */
	private Button webBrowser;
	private Button dialer;
	private Button map;

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
		/* Define behavior for Assignment 02 button elements */
		webBrowser = (Button) findViewById(R.id.web);
		dialer = (Button) findViewById(R.id.dial);
		map = (Button) findViewById(R.id.map);

		/* Set a listener for the button being clicked.
		When a click happens, the onClick method fires,
		 Then the program analyzes which button was pressed. */
		button.setOnClickListener(this);
		webBrowser.setOnClickListener(this);
		dialer.setOnClickListener(this);
		map.setOnClickListener(this);
	}

	/* This method runs when the app returns to the Main (TipCalc) activity from another activity,
	*  and displays a TOAST mentioning that fact. */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("request", ""+requestCode);
		Log.i("request", ""+Activity.RESULT_OK);
		Toast.makeText(this, "Back to Tip Calc Activity", Toast.LENGTH_LONG).show();
	}

	// Perform action on click.
	// A different action is performed depending on which Button ID you selected.
	public void onClick(View v) {
		switch (v.getId()){
			/* This switch case performs the original assignment 1 functionality, tip calculating. */
			case R.id.calculate:
				try {
					Log.i(tag, "onClick invoked.");

					// Get the meal price from the UI
					String mealprice = mealpricefield.getText().toString();
					Log.i(tag, "mealprice is $" + mealprice);

					// Get the number of diners from the UI
					String numberOfDiners = dinersAnswer.getText().toString();
					Log.i(tag, "Number of diners: " + numberOfDiners);

					// Get the tip percentage from the UI.
					String tipPercent = tipPercentageAnswer.getText().toString();
					Log.i(tag, "Tip percentage: " + tipPercent);

					// Check to see if the meal price includes a "$"
					if (mealprice.contains("$"))
						mealprice = mealprice.substring(1);

					// Check to see if the tip includes a percent sign, and if so, remove it.
					if(tipPercent.contains("%"))
						tipPercent = tipPercent.substring(0,(tipPercent.length()-1));

					// Set the default tip value of 15%.
					if(tipPercent.isEmpty())
						tipPercent = "15";

					// Parse the user inputs (which came in as strings) into numbers
					float fmp = Float.parseFloat(mealprice);
					int diners = Integer.parseInt(numberOfDiners);
					int tip = Integer.parseInt(tipPercent);

					// Perform calculations using the user-provided values
					double totalTip = fmp * (tip * 0.01);
					double totalMealprice = fmp + totalTip;
					double tipPP = totalTip / diners;
					double totalPP = totalMealprice / diners;

					// Display the answers in the TextView elements.
					Log.i(tag, "onClick complete.");
					totalBillAnswer.setText(String.format("$%.2f", totalMealprice));
					totalTipAnswer.setText(String.format("$%.2f",totalTip));
					totalPerPersonAnswer.setText(String.format("$%.2f",totalPP));
					tipPerPersonAnswer.setText(String.format("$%.2f",tipPP));

				} catch (Exception e) {
					// Throw error messages in the UI if bad input received.
					Log.e(tag, "Failed to Calculate Tip:" + e.getMessage());
					totalBillAnswer.setText("Failed to Calculate Tip");
					totalTipAnswer.setText("Failed to Calculate Tip");
					totalPerPersonAnswer.setText("Failed to Calculate Tip");
					tipPerPersonAnswer.setText("Failed to Calculate Tip");
				}
				break;

				/* This case triggers when the WEB button is pushed.
				   It opens the web lookup view.*/
			case R.id.web:
				Log.i(tag, "onClick invoked for Web Lookup");
				// Create an explicit intent, creating a new activity.
				Intent web = new Intent(this, WebLookup.class);
				/* Use startActivityForResult to provide a "done" result
				from this activity to the Main activity when finished.
				This allows the Main activity to know when
					this second activity has exited. */
				startActivityForResult(web, requestCode_235);
				break;

				/* This case triggers when the DIAL button is pushed.
				*  The ACTION_CALL action requires explicit dangerous permissions in Android!
				*  The ACTION_VIEW and ACTION_DIAL are safer actions. */
			case R.id.dial:
				Uri uri = Uri.parse("tel:7818912000");
				// Create an implicit intent for starting a telephone app or service.
				Intent i2 = new Intent(Intent.ACTION_CALL,uri);
				startActivity(i2);
				break;

				/* This case opens the Map action. */
			case R.id.map:
				Uri uri2 = Uri.parse("geo:0,0?q=175+forest+street+waltham+ma");
				// Create an implicit intent for starting a map app or service.
				Intent i3 = new Intent(Intent.ACTION_VIEW,uri2);
				// Used for older version of Android:
				if (i3.resolveActivity(getPackageManager()) != null) {
					startActivity(i3);
				}
				break;

		}


	}
}
