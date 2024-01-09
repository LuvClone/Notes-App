package com.example.finalnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ListAdapter listAdap;

    DBHelper dbHelper;

    private ArrayList<Note> notes;

    FloatingActionButton addNoteBtn;
    ListView listView;
    ImageButton menuBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notes = new ArrayList<>();

        dbHelper = new DBHelper(this);
        addNoteBtn=findViewById(R.id.AddNoteBtn);
        listView=findViewById(R.id.ListView);
        menuBtn=findViewById(R.id.MenuBtn);
        listAdap = new ListAdapter();
        listView.setAdapter(listAdap);

        addNoteBtn.setOnClickListener((v)-> {
            Intent intent = new Intent(HomeActivity.this, NoteDetailsActivity.class);
            intent.putExtra("email",getIntent().getStringExtra("email"));
            startActivity(intent);
        });

        menuBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
            Toast.makeText(HomeActivity.this, "Log out complete", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
        FetchData();
    }

    void showMenu(){

    }


    protected void onResume(){
        super.onResume();
        FetchData();
    }

    public class ListAdapter extends BaseAdapter{


        public ListAdapter(){

        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Note getItem(int position) {
            return notes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.recycler_note_item,null);
            TextView NoteTitle = view.findViewById(R.id.NoteTitleMini);
            TextView NoteContent = view.findViewById(R.id.NoteContentMini);
            Note note = notes.get(position);
            NoteTitle.setText(note.getNoteTitle());
            NoteContent.setText(note.getNoteContent());
            NoteContent.setOnClickListener(
                    v -> {
                        Intent intent = new Intent(HomeActivity.this, NoteDetailsActivity.class);
                        intent.putExtra("NoteTitle", note.getNoteTitle());
                        intent.putExtra("NoteContent", note.getNoteContent());
                        intent.putExtra("email", note.getEmail());
                        intent.putExtra("ID", note.getIdNote());
                        startActivity(intent);
                    }
            );

            ImageButton DeleteBtn = view.findViewById(R.id.DeleteBtn);
            DeleteBtn.setOnClickListener(v -> {
                dbHelper.DeleteNote(note);
                FetchData();
            });


            return view;
        }


    }


    public void FetchData(){
        ArrayList<Note> notess = dbHelper.GetAllNote(getIntent().getStringExtra("email"));
        notes = notess;
        listAdap.notifyDataSetChanged();
    }


}