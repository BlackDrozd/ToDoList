package com.android.todolist.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.todolist.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> mNotes = new ArrayList<>();
    private OnNoteListener mOnNoteListener;

    public NoteAdapter(OnNoteListener onNoteListener){
        mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteHolder holder, int position) {
        holder.bind(mNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public void setData(List<Note> notes){
        mNotes.clear();
        mNotes.addAll(notes);
        notifyDataSetChanged();
    }

    public List<Note> getData(){
        return mNotes;
    }

    static class NoteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView desc;
        OnNoteListener onNoteListener;

        NoteHolder(View itemView, OnNoteListener onNoteListener){
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            desc = itemView.findViewById(R.id.item_desc);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        void bind(Note note){
            title.setText(note.getTitle());
            desc.setText(note.getText());
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
