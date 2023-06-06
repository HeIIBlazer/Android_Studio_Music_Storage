package com.example.music_storage_vassiljev_divissenko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Integer id;
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
    Button Create;
    Button Update;
    Button Delete;
    int i = 0;
    int rowsNumber = 0;
    int ID=0;
    String t0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);
//        db.execSQL("Delete FROM Album");
////        db.execSQL("DROP TABLE Album");
//        db.execSQL("CREATE TABLE IF NOT EXISTS Album (_id  INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, releaseDate TEXT, artist TEXT)");
//        db.execSQL("INSERT OR IGNORE INTO Album VALUES (1,'All Eyez On Me', '13.02.1998', '2Pac')," +
//                "(2,'Save Rock And Roll', '12.04.2013', 'Fall Out Boy')," +
//                "(3,'HEROES & VILLAINS','02.12.2022','Metro Boomin')," +
//                "(4,'Encore (Deluxe Version)','12.11.2004','Eminem'),"+
//                "(5,'Meteora','25.03.2003','Linkin Park');");
        Cursor cursor = db.rawQuery("SELECT * FROM Album", null);
        rowsNumber = cursor.getCount();

//        System.out.println(rowsNumber);

        id = Integer.parseInt(getIntent().getExtras().get("itemNumber").toString());

        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        while(cursor.moveToNext()) {
            if (cursor.getInt(0) == id) {
                ID = cursor.getInt(0);
                String nameString = cursor.getString(1);
                String yearString = cursor.getString(2);
                String descriptionString = cursor.getString(3);
                name.setText(nameString);
                release.setText(yearString);
                artist.setText(descriptionString);
                break;
            }
        }

        i=ID-1;
        System.out.println(i);


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


    public void onClickNext(View view) {
        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("music.db",
                MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM Album", null);
        rowsNumber = cursor.getCount();
        cursor.close();

//        i = ID;
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
        album_name = query.getString(1);
        release_date= query.getString(2);
        Artist = query.getString(3);

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

        System.out.println(i);


        name = findViewById(R.id.Album_Name);
        release = findViewById(R.id.Release_Date);
        artist = findViewById(R.id.Artist);

        Cursor query = db.rawQuery("SELECT * FROM Album;", null);

        query.moveToPosition(i);
        album_name = query.getString(1);
        release_date= query.getString(2);
        Artist = query.getString(3);

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

        Update = findViewById(R.id.Update);
        Update.setVisibility(View.GONE);

        Delete = findViewById(R.id.Delete);
        Delete.setVisibility(View.GONE);

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

        String row = "INSERT INTO Album(name, releaseDate, artist) Values ('" + t1 + "','" + t2 + "','" + t3 +"')";

        db.execSQL(row);

        name.setEnabled(false);
        release.setEnabled(false);
        artist.setEnabled(false);

        save.setVisibility(View.GONE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.VISIBLE);
        Update.setVisibility(View.VISIBLE);
        Delete.setVisibility(View.VISIBLE);

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

        Create = findViewById(R.id.Create);
        Create.setVisibility(View.GONE);

        Delete = findViewById(R.id.Delete);
        Delete.setVisibility(View.GONE);

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
        Create.setVisibility(View.VISIBLE);
        Delete.setVisibility(View.VISIBLE);

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

        Create = findViewById(R.id.Create);
        Create.setVisibility(View.GONE);

        Update = findViewById(R.id.Update);
        Update.setVisibility(View.GONE);

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
        album_name = query.getString(1);
        release_date= query.getString(2);
        Artist = query.getString(3);

        name.setText("");
        name.append(album_name);
        release.setText("");
        release.append(release_date);
        artist.setText("");
        artist.append(Artist);

        save_delete.setVisibility(View.GONE);
        Create.setVisibility(View.VISIBLE);
        Update.setVisibility(View.VISIBLE);

        query.close();
        db.close();
    }

    public void onClickGo_To(View view){
        switch(view.getId()){
            case R.id.Go_To:
                Intent intent = new Intent(this, Full_List_Activity.class);
                startActivity(intent);
                break;
        }
    }
}