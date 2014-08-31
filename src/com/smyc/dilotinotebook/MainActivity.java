package com.smyc.dilotinotebook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.smyc.dilotinotebook.R;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	
	public static int mega=1;
	public static int total1;
	public static int total2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        CreateFolder();
    	if (isFirstLaunch()) {
    		WriteToFile("bgdefault.txt", "gwf" );
    		WriteToFile("endnum.txt", "61" );
    	}
    		
    	return true;
    }


    
    
    public void newGameFunc (View view) {
    	MainActivity.total1=0;
		MainActivity.total2=0;
    	Intent intent = new Intent (this, GameEnv.class);
    	startActivity(intent);
    }
    
    private boolean isFirstLaunch() {
		File directory = new File("mnt/sdcard/DilotiNotebook");
		File[] files = directory.listFiles();
		if (files.length == 0)
			return true;
		else
			return false;
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
	        
	        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	    }
	    catch(IOException e)
	    {
	         e.printStackTrace();
	         //importError = e.getMessage();
	        // iError();
	    }
	   } 
    private  void CreateFolder () {
		File folder = new File("mnt/sdcard/DilotiNotebook");
		if (!folder.exists()) {
		     folder.mkdir();
		}
	}
    
}