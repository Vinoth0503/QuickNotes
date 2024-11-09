package com.example.quicknotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteCreationActivity extends AppCompatActivity {
    private EditText noteTitleInput;
    private EditText noteContentInput;
    private Spinner categorySelection;
    private Button saveNoteButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        noteTitleInput = findViewById(R.id.noteTitleInput);
        noteContentInput = findViewById(R.id.noteContentInput);
        categorySelection = findViewById(R.id.categorySelection);
        saveNoteButton = findViewById(R.id.saveNoteButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Populate the category selection dropdown
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Arrays.asList("Work", "Personal", "Ideas"));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySelection.setAdapter(categoryAdapter);

        // Cancel button action
        cancelButton.setOnClickListener(v -> finish());

        // Save button action
        saveNoteButton.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = noteTitleInput.getText().toString().trim();
        String content = noteContentInput.getText().toString().trim();
        String category = categorySelection.getSelectedItem().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the note (you might save it to a database or send it back to HomeActivity)
        // For now, you could use an Intent to return the data to HomeActivity

        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("category", category);
        setResult(RESULT_OK, intent);
        finish();
    }
}

