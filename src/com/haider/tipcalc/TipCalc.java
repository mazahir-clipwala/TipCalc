package com.haider.tipcalc;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class TipCalc extends ActionBarActivity {
	
	public static final String TAG = "TipCalc";
	
	EditText billAmtET,
			 tipAmtET,
			 finalAmtET;
	
	SeekBar tipSeekBar;
	
	CheckBox friendlyCB,
			 opinionsCB;
	
	RadioGroup foodRG;
	
	RadioButton goodFoodRB,
				okFoodRB,
				averageFoodRB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calc);
		
		billAmtET = (EditText) findViewById (R.id.billAmtET);
		tipAmtET = (EditText) findViewById (R.id.tipAmtET);
		finalAmtET = (EditText) findViewById (R.id.finalAmtET);
		
		tipSeekBar = (SeekBar)findViewById(R.id.tipSeekBar);
		
		friendlyCB = (CheckBox)findViewById (R.id.friendlyCB);
		opinionsCB = (CheckBox)findViewById (R.id.opinionsCB);

		foodRG = (RadioGroup)findViewById (R.id.foodRG);

		goodFoodRB = (RadioButton)findViewById (R.id.goodFoodRB);
		okFoodRB = (RadioButton)findViewById (R.id.okFoodRB);
		averageFoodRB = (RadioButton)findViewById (R.id.averageFoodRB);
		
		billAmtET.addTextChangedListener(new TextWatcher () {
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {	}
			@Override public void afterTextChanged(Editable s) { }
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				calcFinalAmt ();
			}
		});
		
		tipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override public void onStopTrackingTouch(SeekBar seekBar) {	}
			
			@Override public void onStartTrackingTouch(SeekBar seekBar) {   }
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tipAmtET.setText (String.format("%.02f", seekBar.getProgress () *.01));
				calcFinalAmt ();				
			}
		});
		
		friendlyCB.setOnCheckedChangeListener (typeCheckedChangeListener);
		opinionsCB.setOnCheckedChangeListener (typeCheckedChangeListener);
		
		foodRG.setOnCheckedChangeListener (foodCheckedChangeListener);
	}
	
	private android.widget.RadioGroup.OnCheckedChangeListener foodCheckedChangeListener = new android.widget.RadioGroup.OnCheckedChangeListener () {

		@Override
		public void onCheckedChanged (RadioGroup group, int checkedId) {
			Double newTipPercentage = 0.0;
			
			if (true == goodFoodRB.isChecked ()) {
				newTipPercentage = 0.03;
			}
			else if (true == okFoodRB.isChecked ()) {
				newTipPercentage = 0.02;
			}
			else if (true == averageFoodRB.isChecked ()) {
				newTipPercentage = 0.01;
			}
			
			Double currentTipAmt = Double.parseDouble (tipAmtET.getText ().toString ());
			tipAmtET.setText (String.format("%.02f", currentTipAmt + newTipPercentage));
			
			calcFinalAmt ();
		}
	};
	
	private OnCheckedChangeListener typeCheckedChangeListener = new OnCheckedChangeListener () {

		@Override
		public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
			String type = buttonView.getText ().toString ();
			
			Double newTipPercentage = 0.0;
			
			if (("Friendly").equalsIgnoreCase(type)) {
				if (true == isChecked) {
					newTipPercentage = .02;
				}
				else {
					newTipPercentage = -.02;
				}
			}
			else if (("Opinions").equalsIgnoreCase (type)) {
				if (true == isChecked) {
					newTipPercentage = .03;
				}
				else {
					newTipPercentage = -.03;
				}
			}
			
			Double currentTipAmt = Double.parseDouble (tipAmtET.getText ().toString ());
			tipAmtET.setText (String.format("%.02f", currentTipAmt + newTipPercentage));
			
			calcFinalAmt ();
		}
	};
	
	private void calcFinalAmt () {
		Double billAmt = Double.parseDouble (billAmtET.getText ().toString ());
		Double tipAmt = Double.parseDouble (tipAmtET.getText ().toString ());
		
		Double finalAmt = billAmt + (billAmt * tipAmt);
		
		finalAmtET.setText (String.format("%.02f", finalAmt));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calc, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
