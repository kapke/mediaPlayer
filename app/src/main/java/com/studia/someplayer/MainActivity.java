package com.studia.someplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URI;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView text = (TextView)this.findViewById(R.id.textView);
        AudioProvider audioProvider = new FilesystemAudioProvider();
        audioProvider.setAudioExtensions(new String[]{"wav", "mp3"});
        List<URI> foundFiles = audioProvider.getFiles();
        for(URI file : foundFiles) {
            text.append(file.toString()+"\n");
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
