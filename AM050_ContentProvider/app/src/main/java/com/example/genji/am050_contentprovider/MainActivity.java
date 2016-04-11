package com.example.genji.am050_contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button but = (Button) findViewById(R.id.buttonret);
        final TextView text = (TextView) findViewById(R.id.text);
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] projection = new String[] {
                        MediaStore.Audio.AudioColumns.ALBUM,
                        MediaStore.Audio.AudioColumns.TITLE };
                Uri contentUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
                Cursor cursor = getContentResolver().query(contentUri,
                        projection, null, null, null);
                // Get the index of the columns we need.
                int albumIdx = cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM);
                int titleIdx = cursor
                        .getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE);
                // Create an array to store the result set.
                String[] result = new String[cursor.getCount()];
                /*
                 * Iterate over the Cursor, extracting each album name and song
                 * title.
                 */
                while (cursor.moveToNext()) {
                    // Extract the song title.
                    String title = cursor.getString(titleIdx);
                    // Extract the album name.
                    String album = cursor.getString(albumIdx);
                    result[cursor.getPosition()] = title + " (" + album + ")";
                }
                // Close the Cursor.
                String allSongs = "";
                cursor.close();
                for(String line : result) allSongs += line + "\n";
                text.setText(allSongs);
            }

        });
    }
}