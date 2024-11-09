package com.example.quicknotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView notesRecyclerView;
    private FloatingActionButton addNoteButton;
    private Spinner categoryFilter;
    private NoteAdapter noteAdapter;
    private List<Note> allNotes;
    private List<Note> filteredNotes;
    private DBHelper dbHelper;

    private static final int ADD_NOTE_REQUEST_CODE = 1;  // Request code for the add note activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        addNoteButton = findViewById(R.id.addNoteButton);
        categoryFilter = findViewById(R.id.categoryFilter);

        dbHelper = new DBHelper(this);

        // Fetch all notes from the database
        allNotes = dbHelper.getAllNotes();
        filteredNotes = new ArrayList<>(allNotes);

        noteAdapter = new NoteAdapter(filteredNotes, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                // Handle note click event (you can add functionality here)
            }
        });

        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(noteAdapter);

        // FAB to add a new note
        addNoteButton.setOnClickListener(v -> openAddNoteScreen());

        // Category filter dropdown
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("All", "Work", "Personal", "Ideas"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(adapter);
        categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterNotes(categoryFilter.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void openAddNoteScreen() {
        Intent intent = new Intent(HomeActivity.this, NoteCreationActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
    }

    private void filterNotes(String category) {
        filteredNotes.clear();
        if (category.equals("All")) {
            filteredNotes.addAll(allNotes);
        } else {
            for (Note note : allNotes) {
                if (note.getCategory().equals(category)) {
                    filteredNotes.add(note);
                }
            }
        }
        noteAdapter.notifyDataSetChanged();
    }

    // Handle the result when NoteCreationActivity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from the add note activity and it's OK
        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Refresh the list of notes after adding a new note
            allNotes.clear();
            allNotes.addAll(dbHelper.getAllNotes());
            filterNotes(categoryFilter.getSelectedItem().toString());
        }
    }
}
