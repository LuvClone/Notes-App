package com.example.finalnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NoteDetailsActivity extends AppCompatActivity {

    DBHelper myDB;
    EditText titleEditText, contentEdittext;

    ImageButton saveNoteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new DBHelper(this);
        setContentView(R.layout.activity_note_details);

        titleEditText = findViewById(R.id.NoteTittle);
        contentEdittext = findViewById(R.id.ContentText);
        saveNoteBtn = findViewById(R.id.DoneBtn);



        if(getIntent().hasExtra("NoteTitle")){
            Log.d("kljjjllj","abc");
            titleEditText.setText(getIntent().getStringExtra("NoteTitle"));
            contentEdittext.setText(getIntent().getStringExtra("NoteContent"));
            saveNoteBtn.setOnClickListener(v -> {
                DBHelper myDB = new DBHelper(this);
                String noteTitle = titleEditText.getText().toString();
                String noteContent = contentEdittext.getText().toString();

                if(noteTitle == null || noteTitle.isEmpty()){
                    titleEditText.setError("Title is required");
                    return;
                }

                Note note = new Note();

                note.setIdNote(getIntent().getIntExtra("ID", 0));
                note.setNoteContent(noteContent);
                note.setNoteTitle(noteTitle);
                note.setEmail(getIntent().getStringExtra("email"));
                myDB.EditNote(note);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intent = new Intent(NoteDetailsActivity.this, Notification.class);
                intent.putExtra("taskName",note.getNoteTitle());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteDetailsActivity.this, note.getIdNote(),intent,PendingIntent.FLAG_IMMUTABLE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,0,pendingIntent);

                Toast.makeText(NoteDetailsActivity.this, "Save complete", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
        else{
            saveNoteBtn.setOnClickListener((v -> SaveNote()));
        }
    }

    void SaveNote(){
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEdittext.getText().toString();

        if(noteTitle == null || noteTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }
        Note note = new Note();
        note.setNoteContent(contentEdittext.getText().toString());
        note.setNoteTitle(titleEditText.getText().toString());
        note.setEmail(getIntent().getStringExtra("email"));
        Log.d("acc",note.getNoteContent());
        Log.d("acc",note.getNoteTitle());
        Log.d("acc",note.getEmail());
        long noteId = myDB.AddNote(note);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(NoteDetailsActivity.this, Notification.class);
        intent.putExtra("taskName",note.getNoteTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NoteDetailsActivity.this, Integer.parseInt(String.valueOf(noteId))+124,intent,PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+1000,pendingIntent);
        Toast.makeText(NoteDetailsActivity.this, "Save complete", Toast.LENGTH_SHORT).show();
        finish();

    }

}