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
    private List<Note> allNotes; // List to hold all notes
    private List<Note> filteredNotes; // List to hold filtered notes based on category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the views
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        addNoteButton = findViewById(R.id.addNoteButton);
        categoryFilter = findViewById(R.id.categoryFilter);

        // Initialize the lists (this would normally come from a database or shared preferences)
        allNotes = new ArrayList<>();
        filteredNotes = new ArrayList<>(allNotes);  // Set filtered notes initially to all notes

        // Initialize the NoteAdapter and set it to RecyclerView
        noteAdapter = new NoteAdapter(filteredNotes, new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(Note note) {
                // Handle note click event (e.g., open note details)
            }
        });
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesRecyclerView.setAdapter(noteAdapter);

        // Set up FAB to open Note Creation screen
        addNoteButton.setOnClickListener(v -> openAddNoteScreen());

        // Set up category filter (for filtering notes based on categories)
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
        // Open NoteCreationActivity when FAB is clicked
        Intent intent = new Intent(HomeActivity.this, NoteCreationActivity.class);
        startActivity(intent);
    }

    private void filterNotes(String category) {
        filteredNotes.clear();  // Clear previous filtered list

        // Filter notes based on the selected category
        if (category.equals("All")) {
            filteredNotes.addAll(allNotes);  // Add all notes if "All" is selected
        } else {
            for (Note note : allNotes) {
                if (note.getCategory().equals(category)) {
                    filteredNotes.add(note);  // Add notes that match the selected category
                }
            }
        }

        // Notify adapter that data has changed, so the RecyclerView can update
        if (noteAdapter != null) {
            noteAdapter.notifyDataSetChanged();
        }
    }
}
