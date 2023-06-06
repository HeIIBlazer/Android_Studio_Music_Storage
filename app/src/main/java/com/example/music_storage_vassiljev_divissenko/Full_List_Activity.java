package com.example.music_storage_vassiljev_divissenko;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;



public class Full_List_Activity extends AppCompatActivity {

    ListView all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_list);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM Album", null);

        all = findViewById(R.id.list);

//        while(cursor.moveToNext()){
//            String name = cursor.getString(0);
//            String date = cursor.getString(1);
//            String artist = cursor.getString(2);
//            all.append("Name: " + name + "\nRelease Date: "+ date + "\nArtist: " + artist+ "\n\n");
//
//        }

        String[] headers = new String[]{
                "_id",
                "name",
                "releaseDate",
                "artist"
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.list, cursor, headers, new int[]{R.id.idText, R.id.name, R.id.releaseDate, R.id.artist}, 0);
        all.setAdapter(adapter);

        all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                TextView textView = (TextView) view.findViewById(R.id.idText);
                intent.putExtra("itemNumber", textView.getText());
                startActivity(intent);
            }
        });

    }



//    public void onClickMainPage(View view){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }

}
