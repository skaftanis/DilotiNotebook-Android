package com.smyc.dilotinotebook;

// BUG #1: αλλαγή πόντων λήξης χωρίς restart

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class GameEnv extends ActionBarActivity {

	//DbAdapter db = new DbAdapter(this);
	
	//ads section
	//private AdView adView;
	//private final String unitid ="ca-app-pub-9464597938461177/8881937835";
	
	
	public String team1Name; //public vars with default value
	public String team2Name;
	
	private int total1;
	private int total2;
	
	private int prtotal1;
	private int prtotal2;
	
	private boolean undoClicked=true;
	
//	private boolean oneChecked=false;
//	private boolean twoChecked=false;;
	
	//private static String namebg;
	private static int end;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_env);
		Intent intent = getIntent();
	
		try {
			end=Integer.parseInt(getFirstLine("endnum.txt"));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

//save background choice (NOT WORKING)
		
	//	try {
	//		namebg=getFirstLine("bgdefault.txt");
	//	} catch (IOException e) {
	//		e.printStackTrace();
	//	}
		
	//	ScrollView scroll = (ScrollView) findViewById (R.id.Scroll);
	//	if (namebg == "gwf") {
	//		if (Build.VERSION.SDK_INT >= 16) {
	//			Resources res = getResources();
	//			Drawable drawable = res.getDrawable(R.drawable.greenbg); 
	//		    scroll.setBackground(drawable);
	//		}
	//		else {
	//			Resources res = getResources();
	//			Drawable drawable = res.getDrawable(R.drawable.greenbg); 
	//		    scroll.setBackgroundDrawable(drawable);	
	//		}	
	//	}
	//	else if (namebg == "gwc") {
			//scroll.setBackgroundResource(R.drawable.bg);
	//	}
		
		setNewGame();
		if ( MainActivity.mega == 1)
			InsertTeams();
		
		final TextView score1 = (TextView) findViewById (R.id.team1Score);
		final TextView score2 = (TextView) findViewById (R.id.team2Score);

		score1.setText(Integer.toString(MainActivity.total1));
		//score1.setText(namebg);
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
		
		  //ads staff
	//	  LinearLayout rootLayout = (LinearLayout) findViewById(R.id.rootView);
	//	  adView = new AdView(this);
	//	  adView.setAdSize(AdSize.SMART_BANNER);
	//	  adView.setAdUnitId(unitid);
	//	  rootLayout.addView(adView,0);
	//	  AdRequest adRequest = new AdRequest.Builder().build();
	//	  adView.loadAd(adRequest);
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
				round.setText("Ãýñïò " + MainActivity.mega);
				if ( MainActivity.total1 >= end || MainActivity.total2 >= end ) {
					EndGameBox();	
				}
			}
			undoClicked=true; 

		}
		if (id == R.id.about) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Ó÷åôéêÜ ìå ôï Diloti NoteBook");
			builder.setMessage("Ôï Diloti Notebook åßíáé Ýíá app ôï ïðïßï óáò âïçèÜåé óôçí åýêïëç êáé ãñÞãïñç êáôáìÝôñçóç ôùí ðüíôùí óôï ãíùóôü åëëçíéêü ðáé÷íßäé äçëùôÞ. "
					+ "Äçìéïõñãüò: Óðýñïò ÊáöôÜíçò ãéá ShowMeYourCode");
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
			}
		})
		
		.show(); 
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
				WriteToFile("endnum.txt", input );	
				try {
					end=Integer.parseInt(getFirstLine("endnum.txt"));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		})
		
		.show(); 
		try {
			end=Integer.parseInt(getFirstLine("endnum.txt"));
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
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
                 //  if (points2.matches("")) {
                	   text2.setText(Integer.toString(7-Integer.parseInt(points1)));
                  // }               	                   
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
	  
	 
	
	private void CheckBox () {
		CheckBox check1= (CheckBox) findViewById(R.id.filla1check);
    	CheckBox check2= (CheckBox) findViewById(R.id.filla2check);
    	  if (check1.isChecked()) {
       	   TextView test = (TextView) findViewById(R.id.round);
       	   test.setText("XAXAA");
          }
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
			builder.setPositiveButton("New Game", new NewGameOnClickListener());
			builder.setNegativeButton("Exit", new ExitOnClickListener());
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
		  	round.setText("Round "+ MainActivity.mega) ;
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
		        
		        //Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
		    }
		    catch(IOException e)
		    {
		         e.printStackTrace();
		         //importError = e.getMessage();
		        // iError();
		    }
		   }  
		
	private  String getFirstLine (String name) throws IOException {
			File sdCard = Environment.getExternalStorageDirectory();
			String Link = null;
			name=sdCard.getAbsolutePath()+"/"+"DilotiNotebook"+"/"+name;
			
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(name));
				String line;
				while ((line = br.readLine()) != null) {
				   Link=line;
				   break;
				}
				br.close();
					
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return Link;
	}
	

}