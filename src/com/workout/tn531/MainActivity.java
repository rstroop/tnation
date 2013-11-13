package com.workout.tn531;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Date;

public class MainActivity extends Activity {
	
	EditText benchText, pressText, squatText, deadText;
	Button buttonSave,buttonWorkout,buttonDate,doneButton;
	DatePicker startDate,todaysDate;
	Spinner day1,day2,day3,day4,day5,day6,day7;
	TextView dayweekText,workoutText,warmupText,workOne,workTwo,workThree,assistOne,assistTwo;
	Date start,today;
	CheckBox checkBoxWarm,checkBox1,checkBox2,checkBox3,checkBox5,checkBox6,checkBox7,checkBox8,checkBox9;
	
	private String array_spinner[];
	
	CountDownTimer timer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        setDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	case R.id.settings_max:
				setMaxes();
                return true;
        	case R.id.settings_date:
                setDate();
                return true;
        	case R.id.settings_clear:
                ClearPreferences();
                return true;
        	case R.id.settings_workout:
        		setWorkouts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void setWorkouts(){
    	setContentView(R.layout.activity_workouts);
    	
    	array_spinner=new String[8];
    	array_spinner[0]="Bench Press";
    	array_spinner[1]="Overhead Press";
    	array_spinner[2]="Squat";
    	array_spinner[3]="Deadlift";
    	array_spinner[4]="Swimming";
    	array_spinner[5]="Biking";
    	array_spinner[6]="Running";
    	array_spinner[7]="Rest";
    	ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, array_spinner);
    	
    	day1 = (Spinner) findViewById(R.id.spinner1);
    	day1.setAdapter(adapter);
    	day2 = (Spinner) findViewById(R.id.spinner2);
    	day2.setAdapter(adapter);
    	day3 = (Spinner) findViewById(R.id.spinner3);
    	day3.setAdapter(adapter);
    	day4 = (Spinner) findViewById(R.id.spinner4);
    	day4.setAdapter(adapter);
    	day5 = (Spinner) findViewById(R.id.spinner5);
    	day5.setAdapter(adapter);
    	day6 = (Spinner) findViewById(R.id.spinner6);
    	day6.setAdapter(adapter);
    	day7 = (Spinner) findViewById(R.id.spinner7);
    	day7.setAdapter(adapter);
    	
    	buttonWorkout = (Button)findViewById(R.id.saveworkout);
        
        buttonWorkout.setOnClickListener(buttonWorkoutOnClickListener);
    	
        LoadWorkout();
    	
    }
    
    public void setDate(){
    	setContentView(R.layout.activity_main);
        
        startDate = (DatePicker)findViewById(R.id.datePickerStart);
        todaysDate= (DatePicker)findViewById(R.id.datePickerToday);
        buttonDate = (Button)findViewById(R.id.date);
        
        buttonDate.setOnClickListener(buttonDateOnClickListener);
    	
    	LoadDate();
    }
    
    public void setMaxes(){
    	setContentView(R.layout.activity_maxes);
    	
        benchText = (EditText)findViewById(R.id.benchtext);
        pressText = (EditText)findViewById(R.id.presstext);
        squatText = (EditText)findViewById(R.id.squattext);
        deadText = (EditText)findViewById(R.id.deadtext);
        buttonSave = (Button)findViewById(R.id.savemax);
       
        buttonSave.setOnClickListener(buttonSaveOnClickListener);
       
        LoadPreferences();
    }
    
    Button.OnClickListener buttonDateOnClickListener = new Button.OnClickListener(){

    	@Override
    	public void onClick(View arg0) {	
    		SaveDate(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
    		
    		start = new Date(startDate.getYear(),startDate.getMonth(),startDate.getDayOfMonth());
    		today = new Date(todaysDate.getYear(),todaysDate.getMonth(),todaysDate.getDayOfMonth());
    		
    		getWorkout();
    	}
   
    };
    
    Button.OnClickListener buttonWorkoutOnClickListener = new Button.OnClickListener(){

    	@Override
    	public void onClick(View arg0) {	
    		SaveWorkout(day1.getSelectedItemPosition(),day2.getSelectedItemPosition(),day3.getSelectedItemPosition(),day4.getSelectedItemPosition(),day5.getSelectedItemPosition(),day6.getSelectedItemPosition(),day7.getSelectedItemPosition());
    	}
   
    };
    
    Button.OnClickListener doneOnClickListener = new Button.OnClickListener(){

    	@Override
    	public void onClick(View arg0) {	
    		setDate();
    	}
   
    };    
    
    Button.OnClickListener buttonSaveOnClickListener = new Button.OnClickListener(){

    	@Override
    	public void onClick(View arg0) {
    		SavePreferences("MEM1", benchText.getText().toString());
    		SavePreferences("MEM2", pressText.getText().toString());
    		SavePreferences("MEM3", squatText.getText().toString());
    		SavePreferences("MEM4", deadText.getText().toString());
    		LoadPreferences();
    	}
   
    };
    
    CheckBox.OnCheckedChangeListener threeMinListener = new CheckBox.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if(isChecked){
				countDown(3);
			}
		}
    	
    };
    
    
    CheckBox.OnCheckedChangeListener fourMinListener = new CheckBox.OnCheckedChangeListener(){
    	
		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if(isChecked){
				countDown(4);
			}
		}
    	
    };
    
    CheckBox.OnCheckedChangeListener fiveMinListener = new CheckBox.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
			if(isChecked){
				countDown(5);
			}
		}
    	
    };
    
    DialogInterface.OnClickListener closer= new DialogInterface.OnClickListener()
    {
           public void onClick(DialogInterface dialog, int id) {
        	   timer.cancel();
           }
    };
    
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	        	SharedPreferences.Editor editor = sharedPreferences.edit();
	        	editor.clear();
	        	editor.commit();
	        	setDate();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};
	
	DialogInterface.OnClickListener maxesListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	        	String weight = sharedPreferences.getString("MEM1", "100");
	        	SavePreferences("MEM1", String.valueOf(Integer.parseInt(weight) + 5));
	        	weight = sharedPreferences.getString("MEM2", "100");
	        	SavePreferences("MEM2", String.valueOf(Integer.parseInt(weight) + 5));
	        	weight = sharedPreferences.getString("MEM3", "100");
	        	SavePreferences("MEM3", String.valueOf(Integer.parseInt(weight) + 10));
	        	weight = sharedPreferences.getString("MEM4", "100");
	        	SavePreferences("MEM4", String.valueOf(Integer.parseInt(weight) + 10));
	        	setMaxes();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};
    
    public void countDown(int minutes){
    	final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
    	alertDialog.setTitle(minutes+" Minutes of Rest");  
	    alertDialog.setMessage("Seconds Left: "+minutes*60);
	    alertDialog.setButton("Close", closer);
	    alertDialog.setCancelable(false);
	    
	    
	    
	    alertDialog.show();
        
        int seconds = minutes*60;

        timer = new CountDownTimer(seconds*1000, 1000) {
	        
            @Override
            public void onTick(long millisUntilFinished) {
               alertDialog.setMessage("Seconds Left: "+ (millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
            	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(2000);
            	alertDialog.hide();
            }
            
        };
        
        timer.start();
    }
    
    
     
    private void SavePreferences(String key, String value){
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	editor.putString(key, value);
    	editor.commit();
    	setDate();
    }
    
    private void SaveDate(int year, int month, int day){
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	editor.putInt("DAY", day);
    	editor.putInt("MONTH", month);
    	editor.putInt("YEAR", year);
    	editor.commit();
    }
    
    private void SaveWorkout(int one, int two, int three, int four, int five, int six, int seven){
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	SharedPreferences.Editor editor = sharedPreferences.edit();
    	editor.putInt("ONE", one);
    	editor.putInt("TWO", two);
    	editor.putInt("THREE", three);
    	editor.putInt("FOUR", four);
    	editor.putInt("FIVE", five);
    	editor.putInt("SIX", six);
    	editor.putInt("SEVEN", seven);
    	editor.commit();
    	setDate();
    }
    
    private void LoadWorkout(){
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	int one = sharedPreferences.getInt("ONE", 7);
    	int two = sharedPreferences.getInt("TWO", 7);
    	int three = sharedPreferences.getInt("THREE", 7);
    	int four = sharedPreferences.getInt("FOUR", 7);
    	int five = sharedPreferences.getInt("FIVE", 7);
    	int six = sharedPreferences.getInt("SIX", 7);
    	int seven = sharedPreferences.getInt("SEVEN", 7);
    	day1.setSelection(one);
    	day2.setSelection(two);
    	day3.setSelection(three);
    	day4.setSelection(four);
    	day5.setSelection(five);
    	day6.setSelection(six);
    	day7.setSelection(seven);
    }
    
    public int lookupWorkout(int day){
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	switch(day){
    	case 1:
    		return sharedPreferences.getInt("ONE", 7);
    	case 2:
    		return sharedPreferences.getInt("TWO", 7);
    	case 3:
    		return sharedPreferences.getInt("THREE", 7);
    	case 4:
    		return sharedPreferences.getInt("FOUR", 7);
    	case 5:
    		return sharedPreferences.getInt("FIVE", 7);
    	case 6:
    		return sharedPreferences.getInt("SIX", 7);
    	case 7:
    		return sharedPreferences.getInt("SEVEN", 7);
    	default:
    		return 7;
    	}
    }
    
    private void LoadDate(){
    	SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	int day = sharedPreferences.getInt("DAY", 1);
    	int month = sharedPreferences.getInt("MONTH", 1);
    	int year = sharedPreferences.getInt("YEAR", 2012);
    	startDate.updateDate(year,month,day);
    }
    	  
	private void LoadPreferences(){
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		String strSavedMem1 = sharedPreferences.getString("MEM1", "100");
		String strSavedMem2 = sharedPreferences.getString("MEM2", "100");
		String strSavedMem3 = sharedPreferences.getString("MEM3", "100");
		String strSavedMem4 = sharedPreferences.getString("MEM4", "100");
		benchText.setText(strSavedMem1);
		pressText.setText(strSavedMem2);
		squatText.setText(strSavedMem3);
		deadText.setText(strSavedMem4);
	}
	
	private void ClearPreferences(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
		    .setNegativeButton("No", dialogClickListener).show();
	}
	
	public int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public void getWorkout(){
		setContentView(R.layout.activity_today);
		
		doneButton = (Button)findViewById(R.id.done);
        
        doneButton.setOnClickListener(doneOnClickListener);
		
		dayweekText = (TextView)findViewById(R.id.dayweek);
		workoutText = (TextView)findViewById(R.id.textworkout);
		warmupText = (TextView)findViewById(R.id.warmup);
		workOne = (TextView)findViewById(R.id.Work1);
		workTwo = (TextView)findViewById(R.id.Work2);
		workThree = (TextView)findViewById(R.id.Work3);
		assistOne = (TextView)findViewById(R.id.Work4);
		assistTwo = (TextView)findViewById(R.id.Work5);
		
		checkBoxWarm = (CheckBox)findViewById(R.id.checkBoxwarm);
		checkBoxWarm.setOnCheckedChangeListener(threeMinListener);
		checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
		checkBox1.setOnCheckedChangeListener(fourMinListener);
		checkBox2 = (CheckBox) findViewById(R.id.checkBox2);
		checkBox2.setOnCheckedChangeListener(fiveMinListener);
		checkBox3 = (CheckBox) findViewById(R.id.checkBox3);
		checkBox3.setOnCheckedChangeListener(threeMinListener);
		checkBox5 = (CheckBox) findViewById(R.id.checkBox5);
		checkBox5.setOnCheckedChangeListener(threeMinListener);
		checkBox6 = (CheckBox) findViewById(R.id.checkBox6);
		checkBox6.setOnCheckedChangeListener(threeMinListener);
		checkBox7 = (CheckBox) findViewById(R.id.checkBox7);
		checkBox7.setOnCheckedChangeListener(threeMinListener);
		checkBox8 = (CheckBox) findViewById(R.id.checkBox8);
		checkBox8.setOnCheckedChangeListener(threeMinListener);
		checkBox9 = (CheckBox) findViewById(R.id.checkBox9);
		checkBox9.setOnCheckedChangeListener(threeMinListener);
		
		
		int day = daysBetween(start, today);
		day++;
		int week = 1;
		if(day<1){
			day = 1;
			SaveDate(today.getYear(),today.getMonth(),today.getDate());
		}
		if(day>28){
			day = 1;
			SaveDate(today.getYear(),today.getMonth(),today.getDate());
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("You have completed 4 weeks of 5-3-1. Your new start date has been set to today. Would you like to update your maxes?").setPositiveButton("Yes", maxesListener)
			    .setNegativeButton("No", maxesListener).show();
		}
		if(day>7){
			week = ((day-1)/7) + 1;
			day = day - (7*(week-1));
		}
		
		int first_set = 8;
		int second_set = 8;
		int third_set = 8;
		double first_percent = 60;
		double second_percent = 70;
		double third_percent = 80;
		
		switch(week){
		case 1 :
			first_set = 5;
			second_set = 5;
			third_set = 5;
			first_percent = 65;
			second_percent = 75;
			third_percent = 85;
			break;
		case 2 :
			first_set = 3;
			second_set = 3;
			third_set = 3;
			first_percent = 65;
			second_percent = 75;
			third_percent = 85;
			break;
		case 3 :
			first_set = 5;
			second_set = 3;
			third_set = 1;
			first_percent = 65;
			second_percent = 75;
			third_percent = 85;
			break;
		default :
			first_set = 5;
			second_set = 5;
			third_set = 5;
			first_percent = 40;
			second_percent = 50;
			third_percent = 60;
			break;
		}
		
		
		
		int work = lookupWorkout(day);
		Integer weight;
		
		dayweekText.setText("Day " + day + " Week " + week);
		
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		
		switch(work){
		case 0 :
			String benchMax = sharedPreferences.getString("MEM1", "100");
			weight = Integer.parseInt(benchMax);
			workoutText.setText("Bench Press today!\n");
			
			if(week==4){
				warmupText.setText("Warmup (x3):\nPush-ups");
			}
			else{
				warmupText.setText("Warmup (x5):\n"+(weight*.4)+", "+(weight*.5)+", "+(weight*.6));				
			}

			workOne.setText((weight*first_percent/100)+" x "+first_set);
			workTwo.setText((weight*second_percent/100)+" x "+second_set);
			workThree.setText((weight*third_percent/100)+" x "+third_set);
			
			assistOne.setText("Dumbbell chest press (15x)");
			assistTwo.setText("Dumbbell row (10x)");

			break;
		case 1 :
			String pressMax = sharedPreferences.getString("MEM2", "100");
			weight = Integer.parseInt(pressMax);
			workoutText.setText("Overhead Press today!\n");
			
			if(week==4){
				warmupText.setText("Warmup:\nJump-Rope 5 minutes");
			}
			else{
				warmupText.setText("Warmup (x5):\n"+(weight*.4)+", "+(weight*.5)+", "+(weight*.6));				
			}
			
			workOne.setText((weight*first_percent/100)+" x "+first_set);
			workTwo.setText((weight*second_percent/100)+" x "+second_set);
			workThree.setText((weight*third_percent/100)+" x "+third_set);
			
			assistOne.setText("Dips (15x)");
			assistTwo.setText("Chin-ups (10x)");
			
			break;
		case 2 :
			String squatMax = sharedPreferences.getString("MEM3", "100");
			weight = Integer.parseInt(squatMax);
			workoutText.setText("Squats today!");
			
			if(week==4){
				warmupText.setText("Warmup:\nStretching");
			}
			else{
				warmupText.setText("Warmup (x5):\n"+(weight*.4)+", "+(weight*.5)+", "+(weight*.6));				
			}
			
			workOne.setText((weight*first_percent/100)+" x "+first_set);
			workTwo.setText((weight*second_percent/100)+" x "+second_set);
			workThree.setText((weight*third_percent/100)+" x "+third_set);
			
			assistOne.setText("Leg Press (15x)");
			assistTwo.setText("Leg Curl (10x)");
			
			break;
		case 3 :
			String deadMax = sharedPreferences.getString("MEM4", "100");
			weight = Integer.parseInt(deadMax);
			workoutText.setText("Deadlift today!");
			
			if(week==4){
				warmupText.setText("Warmup:\nBox Jumps");
			}
			else{
				warmupText.setText("Warmup (x5):\n"+(weight*.4)+", "+(weight*.5)+", "+(weight*.6));				
			}
			
			workOne.setText((weight*first_percent/100)+" x "+first_set);
			workTwo.setText((weight*second_percent/100)+" x "+second_set);
			workThree.setText((weight*third_percent/100)+" x "+third_set);
			
			assistOne.setText("Good Mornings (12x)");
			assistTwo.setText("Hanging Leg Raise (15x)");

			break;
		case 4 :
			workoutText.setText("Swimming today!");
			
			warmupText.setText("Warmup:\n50m");
			
			workOne.setText("300m");
			workTwo.setText("500m");
			workThree.setText("800m");
			
			assistOne.setText("Legs Only");
			assistTwo.setText("Focus on technique");
			break;
		case 5 :
			workoutText.setText("Biking today!");
			
			warmupText.setText("Warmup:\n5 minutes");
			
			workOne.setText("Flats 10 min");
			workTwo.setText("Hills 20 min");
			workThree.setText("Sprints 10 min");
			
			assistOne.setText("Cooldown 5 min");
			assistTwo.setText("Stretch");
			break;
		case 6 :
			workoutText.setText("Running today!");
			
			warmupText.setText("Warmup:\nJumping Jacks");
			
			workOne.setText("5 X 100m");
			workTwo.setText("1 Mile");
			workThree.setText("10 x 200m");
			
			assistOne.setText("Hill Sprints");
			assistTwo.setText("Stretching");
			break;
		default :
			workoutText.setText("Today is a day of Rest...");	
		}
		
	}
    
}
