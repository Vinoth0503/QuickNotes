package com.example.quicknotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes;
    private OnNoteClickListener onNoteClickListener;

    // Interface for handling click events
    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    // Constructor
    public NoteAdapter(List<Note> notes, OnNoteClickListener onNoteClickListener) {
        this.notes = notes != null ? notes : new ArrayList<>();  // Prevent null list issues
        this.onNoteClickListener = onNoteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Get the current note
        Note note = notes.get(position);

        // Bind the data to the views
        holder.noteTitle.setText(note.getTitle());
        holder.noteDescription.setText(note.getDescription());

        // Set click listener to handle note click
        holder.itemView.setOnClickListener(v -> onNoteClickListener.onNoteClick(note));
    }

    @Override
    public int getItemCount() {
        return notes.size();  // Return the size of the notes list
    }

    // ViewHolder class to hold reference to the views
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle, noteDescription;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);  // Ensure these IDs are correct
            noteDescription = itemView.findViewById(R.id.noteDescription);
        }
    }
}
