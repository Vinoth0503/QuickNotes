package com.example.quicknotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class NoteCreationActivity extends AppCompatActivity {
    private EditText noteTitleInput, noteContentInput;
    private Spinner categorySelection;
    private Button saveNoteButton, cancelButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        noteTitleInput = findViewById(R.id.noteTitleInput);
        noteContentInput = findViewById(R.id.noteContentInput);
        categorySelection = findViewById(R.id.categorySelection);
        saveNoteButton = findViewById(R.id.saveNoteButton);
        cancelButton = findViewById(R.id.cancelButton);

        dbHelper = new DBHelper(this);

        // Set up category selection spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("Work", "Personal", "Ideas"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySelection.setAdapter(adapter);

        saveNoteButton.setOnClickListener(v -> saveNote());
        cancelButton.setOnClickListener(v -> finish());
    }

    private void saveNote() {
        String title = noteTitleInput.getText().toString().trim();
        String content = noteContentInput.getText().toString().trim();
        String category = categorySelection.getSelectedItem().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the note to the database
        dbHelper.insertNote(title, content, category);

        // Return result to HomeActivity to indicate the data has changed
        setResult(RESULT_OK);
        Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();

        // Go back to HomeActivity
        finish();
    }
}
