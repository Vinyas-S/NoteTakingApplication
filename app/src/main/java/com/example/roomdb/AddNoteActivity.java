package com.example.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.roomdb.notedb.Note;
import com.example.roomdb.notedb.NoteDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;

public class AddNoteActivity extends AppCompatActivity {

    private TextInputEditText etTitle,etContent;
    private  boolean update;
    private Button button;

    private NoteDatabase noteDatabase;
    private Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle=findViewById(R.id.et_title);
        etContent=findViewById(R.id.et_content);
        button=findViewById(R.id.but_save);

        noteDatabase=NoteDatabase.getInstance(AddNoteActivity.this);

        if((note = (Note) getIntent().getSerializableExtra("note")) !=null){
            getSupportActionBar().setTitle("Update Note");
            update=true;
            button.setText("Update");
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(update){
                    note.setContent(etContent.getText().toString());
                    note.setTitle(etTitle.getText().toString());
                    noteDatabase.getNoteDao().updateNote(note);
                    setResult(note,2);

                }
                else{
                    note=new Note(etContent.getText().toString(),
                            etTitle.getText().toString());

                    new InsertTask(AddNoteActivity.this,note).execute();
                }
            }
        });
    }

    private void setResult(Note note,int flag){
        setResult(flag,new Intent().putExtra("note",note));
        finish();

    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {
        private WeakReference<AddNoteActivity> activityWeakReference;
        private Note note;

        InsertTask(AddNoteActivity context,Note note){
            activityWeakReference=new WeakReference<>(context);
            this.note= note;

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            long j=activityWeakReference.get().noteDatabase.getNoteDao().insertNote(note);
            note.setNote_id(j);

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                activityWeakReference.get().setResult(note,1);
                activityWeakReference.get().finish();
            }

        }
    }
}
