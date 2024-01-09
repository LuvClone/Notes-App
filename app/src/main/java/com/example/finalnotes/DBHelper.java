package com.example.finalnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(email TEXT primary key, password TEXT)");
        MyDB.execSQL("create Table notes(IdNote INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, describtion TEXT, email TEXT not null references users (email))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);

        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkemail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        if(cursor.getCount()>0)
                return true;
            else
                return false;
    }

    public Boolean checkpassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email = ? and password = ?", new String[] {email, password});
        if(cursor.getCount()>0)
            return true;
        else return false;
    }

    public ArrayList<Note> GetAllNote(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ArrayList<Note> Notes = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("select * from Notes where email = ?", new String[] {email});
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                int IdNote = cursor.getInt(cursor.getColumnIndexOrThrow("IdNote"));
                String NoteTittle = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String NoteContent = cursor.getString(cursor.getColumnIndexOrThrow("describtion"));

                Note note = new Note();
                note.setIdNote(IdNote);
                note.setNoteTitle(NoteTittle);
                note.setNoteContent(NoteContent);

                Notes.add(note);

            }

        }
        cursor.close();
        MyDB.close();
        return Notes;
    }

    public long AddNote( Note note){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", note.getEmail());
        contentValues.put("title", note.getNoteTitle());
        contentValues.put("describtion", note.getNoteContent());

        long idNote = MyDB.insert("notes", null, contentValues);
        MyDB.close();
        return idNote;
    }

    public void DeleteNote(Note note){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Log.d("cdmm", String.valueOf(note.getIdNote()));
        String where = "IdNote" + "=?";
        String[] arg = {String.valueOf(note.getIdNote())};
        MyDB.delete("notes", where, arg);
        MyDB.close();
    }

    public void EditNote(Note note){
        Log.d("kasdasd", "" + note.getIdNote());
        Log.d("kasdasd", "" + note.getNoteContent());
        Log.d("kasdasd", "" + note.getNoteTitle());
        Log.d("kasdasd", "" + note.getEmail());
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", note.getNoteTitle());
        contentValues.put("describtion", note.getNoteContent());
        Log.d("kasdasd", "" + note.getIdNote());
        Log.d("kasdasd", "" + note.getNoteContent());
        Log.d("kasdasd", "" + note.getNoteTitle());
        Log.d("kasdasd", "" + note.getEmail());



        String where = "IdNote" + "= ?";
        String[] arg = {String.valueOf(note.getIdNote())};
        MyDB.update("notes", contentValues, where, arg);
        MyDB.close();
    }

}
