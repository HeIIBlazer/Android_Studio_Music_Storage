package com.example.music_storage_vassiljev_divissenko;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText release;
    EditText artist;
    String album_name;
    String release_date;
    String Artist;
    Button next;
    Button previous;
    Button save;
    Button save_update;
    Button save_delete;
    int i = 0;
    int rowsNumber = 0;

    String t0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);
        db.execSQL("Delete FROM Album");
        db.execSQL("CREATE TABLE IF NOT EXISTS Album (name TEXT, releaseDate TEXT, artist TEXT)");
        db.execSQL("INSERT OR IGNORE INTO Album VALUES ('All Eyez On Me', '13.02.1998', '2Pac')," +
                "('Save Rock And Roll', '12.04.2013', 'Fall Out Boy')," +
                "('HEROES & VILLAINS','02.12.2022','Metro Boomin')," +
                "('Encore (Deluxe Version)','12.11.2004','Eminem'),"+
                "('Meteora','25.03.2003','Linkin Park');");
        Cursor cursor = db.rawQuery("SELECT * FROM Album", null);
        rowsNumber = cursor.getCount();


        System.out.println(rowsNumber);

        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        save = findViewById(R.id.Save);
        save.setVisibility(View.GONE);

        save_delete = findViewById(R.id.Save_Delete);
        save_delete.setVisibility(View.GONE);

        save_update = findViewById(R.id.save_Update);
        save_update.setVisibility(View.GONE);

        name.setEnabled(false);
        release.setEnabled(false);
        artist.setEnabled(false);

        System.out.println(rowsNumber);

        cursor.close();
        db.close();
    }


    public void onClickNext(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Album", null);
        rowsNumber = cursor.getCount();
        cursor.close();

        if (i == rowsNumber-1){
            i = 0;
        }else{
            i++;
        }

        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        Cursor query = db.rawQuery("SELECT * FROM Album;", null);

        query.moveToPosition(i);
        album_name = query.getString(0);
        release_date= query.getString(1);
        Artist = query.getString(2);

        System.out.println(album_name);

        name.setText("");
        name.append(album_name);
        release.setText("");
        release.append(release_date);
        artist.setText("");
        artist.append(Artist);

        query.close();
        db.close();
    }

    public void onClickPrevious(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM Album", null);
        rowsNumber = cursor.getCount();
        cursor.close();

        if (i == 0) {
            rowsNumber=rowsNumber-1;
            i=rowsNumber;
            rowsNumber= rowsNumber+1;

        }else {
            i--;
        }


        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        Cursor query = db.rawQuery("SELECT * FROM Album;", null);

        query.moveToPosition(i);
        album_name = query.getString(0);
        release_date= query.getString(1);
        Artist = query.getString(2);

        System.out.println(album_name);

        name.setText("");
        name.append(album_name);
        release.setText("");
        release.append(release_date);
        artist.setText("");
        artist.append(Artist);

        query.close();
        db.close();
    }

    public void onClickCreate(View view) {

        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        save = findViewById(R.id.Save);
        save.setVisibility(View.VISIBLE);

        next = findViewById(R.id.Next);
        next.setVisibility(View.GONE);

        previous = findViewById(R.id.Previous);
        previous.setVisibility(View.GONE);

        save_update.setVisibility(View.GONE);
        save_delete.setVisibility(View.GONE);

        name.setEnabled(true);
        release.setEnabled(true);
        artist.setEnabled(true);

        name.setText("");
        release.setText("");
        artist.setText("");

    }

    public void onClickSave(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);

        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        String t1 = name.getText().toString();
        String t2 = release.getText().toString();
        String t3 = artist.getText().toString();

        String row = "INSERT INTO Album Values ('" + t1 + "','" + t2 + "','" + t3 +"')";

        db.execSQL(row);

        name.setEnabled(false);
        release.setEnabled(false);
        artist.setEnabled(false);

        save.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);

        db.close();
    }

    public void onClickUpdate(View view){
        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        t0 = name.getText().toString();

        save_update = findViewById(R.id.save_Update);
        save_update.setVisibility(View.VISIBLE);

        next = findViewById(R.id.Next);
        next.setVisibility(View.GONE);

        previous = findViewById(R.id.Previous);
        previous.setVisibility(View.GONE);

        save.setVisibility(View.GONE);
        save_delete.setVisibility(View.GONE);

        name.setEnabled(true);
        release.setEnabled(true);
        artist.setEnabled(true);
    }

    public void onClickSave_Update(View View){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);

        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        String t1 = name.getText().toString();
        String t2 = release.getText().toString();
        String t3 = artist.getText().toString();

        String row = "UPDATE Album SET name ='"+t1+"', releaseDate = '"+t2+"',artist = '"+t3+"' WHERE name = '"+t0+"'";


        db.execSQL(row);

        name.setEnabled(false);
        release.setEnabled(false);
        artist.setEnabled(false);

        save_update.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);

        db.close();
    }

    public void onClickDelete(View view){

        t0 = name.getText().toString();

        save_delete = findViewById(R.id.Save_Delete);
        save_delete.setVisibility(View.VISIBLE);

        next = findViewById(R.id.Next);
        next.setVisibility(View.GONE);

        previous = findViewById(R.id.Previous);
        previous.setVisibility(View.GONE);

        save.setVisibility(View.GONE);
        save_update.setVisibility(View.GONE);

    }

    public void onClickDelete_Save(View view){
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);
        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);


        String row = "DELETE FROM Album WHERE name = '"+t0+"'";

        db.execSQL(row);

        save.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        Cursor query = db.rawQuery("SELECT * FROM Album;", null);

        query.moveToPosition(i);
        album_name = query.getString(0);
        release_date= query.getString(1);
        Artist = query.getString(2);

        name.setText("");
        name.append(album_name);
        release.setText("");
        release.append(release_date);
        artist.setText("");
        artist.append(Artist);

        save_delete.setVisibility(View.GONE);

        query.close();
        db.close();
    }
}