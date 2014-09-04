package com.smyc.dilotinotebook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class GameEnv extends ActionBarActivity {
	

	public String team1Name; //public vars with default value
	public String team2Name;
	
	private int total1;
	private int total2;
	
	private int prtotal1;
	private int prtotal2;
	
	private boolean undoClicked=true;
	private static int end;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_env);
		Intent intent = getIntent();
		  
		//ads staff 
		 AdView adView = (AdView) findViewById(R.id.adView);
		 AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("EAD625570A28B498CEA7ECF661A068D4").build();
		 adView.loadAd(adRequest);
		
		end=61; //default value for end
	
		setNewGame();
		if ( MainActivity.mega == 1)
			InsertTeams();
		
		final TextView score1 = (TextView) findViewById (R.id.team1Score);
		final TextView score2 = (TextView) findViewById (R.id.team2Score);

		score1.setText(Integer.toString(MainActivity.total1));
		score2.setText(Integer.toString(MainActivity.total2));
		
		
		final TextView round = (TextView) findViewById (R.id.round);
		round.setText("Γύρος " + MainActivity.mega);
		
		EditText arx = (EditText)findViewById(R.id.xeres1);
		arx.setText("0");
		
		EditText arx2 = (EditText)findViewById(R.id.xeres2);
		arx2.setText("0");
	
		AutoComplete();
		
		 Button button= (Button) findViewById(R.id.button1);
		     //check if something is empty
			 if ( !TextUtils.isEmpty(score1.getText()) )
				 button.setEnabled(false);
			 else 
				 button.setEnabled(true);			 
		 
		  button.setOnClickListener(new View.OnClickListener() {
		      @Override
		      public void onClick(View v) {
		    	  buttonClicked(v);
		      }

			private void buttonClicked(View v) {
				
				undoClicked=false;
				
				EditText text1 = (EditText)findViewById(R.id.points1);
				String points1 = text1.getText().toString();
				
				EditText text3 = (EditText)findViewById(R.id.points2);
				String points2 = text3.getText().toString();
				
				
				EditText text = (EditText)findViewById(R.id.xeres1);
        		String xeres1 = text.getText().toString();
        		if (TextUtils.isEmpty(xeres1))
        			xeres1="0";
        		
        		total1+=Integer.parseInt(xeres1)*10+Integer.parseInt(points1);
        		
        		EditText text2 = (EditText)findViewById(R.id.xeres2);
        		String xeres2 = text2.getText().toString();
        		if (TextUtils.isEmpty(xeres2))
        			xeres2="0";
        		
        		total2+=Integer.parseInt(xeres2)*10+Integer.parseInt(points2);
				
        		CheckBox check1 = (CheckBox) findViewById(R.id.filla1check);
        		CheckBox check2 = (CheckBox) findViewById(R.id.filla2check);

        		if ( isChecked(check1)) 
        			total1+=4;
        		if ( isChecked(check2))
        			total2+=4;
        		
        		
        		if ( isChecked(check1) && isChecked(check2)) {
        			total1-=4;
        			total2-=4;
        		}
        		
        		MainActivity.total1+=total1;
        		MainActivity.total2+=total2;
        		
        		MainActivity.mega++;
        		round.setText("Γύρος " + MainActivity.mega);

        		
        		score1.setText(Integer.toString(MainActivity.total1));
        		score2.setText(Integer.toString(MainActivity.total2));
        		
        		text1.setText("");
        		text3.setText("");
        		text.setText("0");
        		text2.setText("0");
        		ScrollView scroll = (ScrollView) findViewById (R.id.Scroll);
        		scroll.fullScroll(ScrollView.FOCUS_UP);

        		
        		prtotal1=total1;
        		prtotal2=total2;
        		
        		total1=0;
        		total2=0;
        		
        		
        		if ( MainActivity.total1 >= end || MainActivity.total2 >= end ) {
        			 EndGameBox();	
        		}

			}
		  });
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_env, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.bg) {
			final CharSequence photos[] = new CharSequence[] {"Green with flowers", "Green with Cards", "Green with Symbols"};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Pick a Picture");
			builder.setItems(photos, new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        if ( photos[which] == "Green with flowers" ) {
		        		ScrollView scroll = (ScrollView) findViewById (R.id.Scroll);
		        		scroll.setBackgroundResource(R.drawable.greenbg);
		        		CreateFolder();
		        		WriteToFile("bgdefault.txt", "gwf" );
			        }
			        if ( photos[which] == "Green with Cards" ) {
			        	ScrollView scroll = (ScrollView) findViewById (R.id.Scroll);
			        	scroll.setBackgroundResource(R.drawable.bg);
			        	CreateFolder();
		        		WriteToFile("bgdefault.txt", "gwc" );
			        }
			        if ( photos[which] == "Green with Symbols") {
			        	ScrollView scroll = (ScrollView) findViewById (R.id.Scroll);
		        		scroll.setBackgroundResource(R.drawable.gwog_background);
		        		CreateFolder();
		        		WriteToFile("bgdefault.txt", "gws" );
			        }
			    }
			});
			builder.show();
		}
		if ( id == R.id.end) {
			insertBox();
		}
		if ( id == R.id.undo) {
						
			if (!undoClicked) {
				MainActivity.total1-=prtotal1;
				MainActivity.total2-=prtotal2;
				//total1-=total1;
				//total2-=total2;
				MainActivity.mega--;

				TextView score1 = (TextView) findViewById (R.id.team1Score);
				TextView score2 = (TextView) findViewById (R.id.team2Score);
				score1.setText(Integer.toString(MainActivity.total1));
				score2.setText(Integer.toString(MainActivity.total2));
				EditText text1 = (EditText)findViewById(R.id.points1);			
				EditText text3 = (EditText)findViewById(R.id.points2);			
				EditText text2 = (EditText)findViewById(R.id.xeres2);			
				EditText text = (EditText)findViewById(R.id.xeres1);
				text1.setText("");
				text3.setText("");
				text.setText("0");
				text2.setText("0");
				ScrollView scroll = (ScrollView) findViewById (R.id.Scroll);
				scroll.fullScroll(ScrollView.FOCUS_UP);
				TextView round = (TextView) findViewById (R.id.round);
				round.setText("Γύρος " + MainActivity.mega);
				if ( MainActivity.total1 >= end || MainActivity.total2 >= end ) {
					EndGameBox();	
				}
			}
			undoClicked=true; 

		}
		if (id == R.id.about) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Σχετικά με το Diloti NoteBook");
			builder.setMessage("Το Diloti Notebook είναι ένα app το οποίο σας βοηθάει στην εύκολη και γρήγορη καταμέτρηση των πόντων στο γνωστό ελληνικό παιχνίδι δηλωτή. "
					+ "Δημιουργός: Σπύρος Καφτάνης για ShowMeYourCode \n Version 1.0 (Builded 4/9/2014)");
			builder.setPositiveButton("OK", null);
			AlertDialog dialog = builder.show();

			// Must call show() prior to fetching text view
			TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
			messageView.setGravity(Gravity.CENTER);
		}
		return super.onOptionsItemSelected(item);
	}
	

	
	private void InsertTeams(){
		final EditText txtUrl = new EditText(this);
		txtUrl.setHint("πχ. Χαρτοπαίχτες");
		new AlertDialog.Builder(this)
		.setTitle("Εισάγεται το όνομα της ομάδας")
		.setMessage("Όνομα πρώτης ομάδας")
		.setView(txtUrl)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String input = txtUrl.getText().toString();
				team1Name=input;
				TextView team1 = (TextView) findViewById (R.id.team1);
				team1.setText(input);
				if (input.matches(""))
					team1.setText("Ομάδα 1");
				Team2();		
			}
		})
		
		.show(); 
		
	}
	
	private void Team2 () {
		final EditText txtUrl = new EditText(this);
		txtUrl.setHint("πχ. Χαρτοπαίχτες");
		
		new AlertDialog.Builder(this)
		.setTitle("Εισάγετε το όνομα της ομάδας")
		.setMessage("Όνομα δεύτερης ομάδας")
		.setView(txtUrl)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String input = txtUrl.getText().toString();
				team2Name=input;
				TextView team2 = (TextView) findViewById (R.id.team2);
				team2.setText(input);
				if (input.matches(""))
					team2.setText("Ομάδα 2");
				insertBox();

			}
		})
		
		.show(); 
		//insertBox is for choosing the points to end
	}
	
	private void insertBox () {
		final EditText txtUrl = new EditText(this);
		txtUrl.setInputType(InputType.TYPE_CLASS_NUMBER);
		txtUrl.setHint("Τώρα είναι  " + end);
		new AlertDialog.Builder(this)
		.setMessage("Εισάγετε τον αριθμό των πόντων που τελειώνει το παιχνίδι")
		.setView(txtUrl)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String input = txtUrl.getText().toString();
				if (input.matches(""))
					end=end; //no effect
				else {
					TextView score1 = (TextView) findViewById (R.id.team1Score);
				    TextView score2 = (TextView) findViewById (R.id.team2Score);
				    String points1 = (String) score1.getText();
				    String points2 = (String) score2.getText();
					if ( Integer.parseInt(input)< Integer.parseInt(points1) || Integer.parseInt(input) < Integer.parseInt(points2)) {
						Toast.makeText(getApplicationContext(), "Μη κλέβεις!", Toast.LENGTH_LONG).show();
						insertBox();
					} else
						end=Integer.parseInt(input);
				}
			}
		})
		
		.show(); 
	
	}
	private void AutoComplete(){
	    final EditText text = (EditText)findViewById(R.id.points1);

	    
	    
		text.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable s){
        		EditText text2 = (EditText)findViewById(R.id.points2);
        		String points2 = text2.getText().toString();
        		EditText text = (EditText)findViewById(R.id.points1);
        		String points1 = text.getText().toString();
        	
        		Button button= (Button) findViewById(R.id.button1);
                if(!text.getText().toString().equals("")){
                   if ( Integer.parseInt(points1) > 7) {
                	   text.setText("7");
                	   text2.setText("0");
                   }
                   if ( Integer.parseInt(points1) < 0) {
                	   text.setText("0");
                	   text2.setText("7");
                   }
                   points1 = text.getText().toString(); //refresh values
                   points2 = text2.getText().toString();
                   text2.setText(Integer.toString(7-Integer.parseInt(points1)));
              	                   
                   button.setEnabled(true);  
   
                                       	
               }
                else 
                	button.setEnabled(false);
               
	

                }

            public void beforeTextChanged(CharSequence s,int start,int count, int after){	
            } 
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
});
				
}
	
	  public boolean isChecked(View v) {
		    if (((CheckBox) v).isChecked()) 
		    	   return true;	       	   		  
		    else 
		    	  return false;		    	  	 
	  }
	  
	
	  public void newGameFunc (View view) {
	    	Intent intent = new Intent (this, GameEnv.class);
	    	startActivity(intent);
	    }
	
	  private void EndGameBox () {
		
			Builder builder = new AlertDialog.Builder(this);
			if ( MainActivity.total1 > MainActivity.total2) {
				TextView team1 = (TextView) findViewById (R.id.team1);
				builder.setMessage(team1.getText() + " is the winner!");
			}
			else if (MainActivity.total1 < MainActivity.total2) {
				TextView team2 = (TextView) findViewById (R.id.team2);
				builder.setMessage(team2.getText() + " is the winner!");
			}
			else 
				builder.setMessage("There is no winner! Tie");
			builder.setCancelable(false);
			builder.setPositiveButton("Νέο Παιχνίδι", new NewGameOnClickListener());
			builder.setNegativeButton("Έξοδος", new ExitOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
	  }
	  
	  private final class NewGameOnClickListener implements DialogInterface.OnClickListener {
			public void onClick(DialogInterface dialog, int which) {
				//Save()
				setNewGame();			
			}
		}
	  private final class ExitOnClickListener implements DialogInterface.OnClickListener {
			public void onClick(DialogInterface dialog, int which) {
				//Save()		
				finish();
			}
		}
	  
	  private void setNewGame () {
		  	MainActivity.mega=1;
		  	TextView round = (TextView) findViewById (R.id.round);
		  	round.setText("Γύρος "+ MainActivity.mega) ;
			TextView score1 = (TextView) findViewById (R.id.team1Score);
			TextView score2 = (TextView) findViewById (R.id.team2Score);
			score1.setText("0");
			score2.setText("0");
			EditText arx = (EditText)findViewById(R.id.xeres1);
			EditText arx2 = (EditText)findViewById(R.id.xeres2);
			arx.setText("0");								
			arx2.setText("0");
			MainActivity.total1=0;
			MainActivity.total2=0;
			total1=0;
			total2=0;
	  }
	  
	  public static void CreateFolder () {
			File folder = new File("mnt/sdcard/DilotiNotebook");
			if (!folder.exists()) {
			     folder.mkdir();
			}
		}
	  
	  private  void WriteToFile(String sFileName, String first){
		    try
		    {
		        File root = new File(Environment.getExternalStorageDirectory(), "DilotiNotebook");
		        if (!root.exists()) {
		            root.mkdirs();
		        }
		        File gpxfile = new File(root, sFileName);
		        FileWriter writer = new FileWriter(gpxfile);
		        first+="\r\n";
		        writer.append(first);
		        writer.flush();
		        writer.close();		        		       
		    }
		    catch(IOException e)
		    {
		         e.printStackTrace();		      
		    }
		   }  
		

}