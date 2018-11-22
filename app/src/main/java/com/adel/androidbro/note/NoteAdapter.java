package com.adel.androidbro.note;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes = new ArrayList<>();
    private NoteViewEventCallback eventCallback;

    public NoteAdapter(NoteViewEventCallback eventCallback) {
        this.eventCallback = eventCallback;
        Note note = new Note();
        note.setTitle("What is Lorem Ipsum?");
        note.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
        note.setDate(System.currentTimeMillis());
        notes.add(note);
    }

    public void addNotes(List<Note> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bindNote(notes.get(position));
    }

    @Override
    public int getItemCount() {
        int count = notes.size();
        eventCallback.onGetItemCountCalled(count);
        return count;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private View deleteBtn;
        private View editBtn;
        private TextView titleTv;
        private TextView descriptionTv;
        private TextView dateTv;

        public NoteViewHolder(View itemView) {
            super(itemView);

            deleteBtn = itemView.findViewById(R.id.iv_note_delete);
            editBtn = itemView.findViewById(R.id.iv_note_edit);
            titleTv = itemView.findViewById(R.id.tv_note_title);
            descriptionTv = itemView.findViewById(R.id.tv_note_desc);
            dateTv = itemView.findViewById(R.id.tv_note_date);
        }

        public void bindNote(final Note note) {
            titleTv.setText(note.getTitle());

            descriptionTv.setText(note.getDescription());

            dateTv.setText(note.getDate());

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventCallback.onDeleteClicked(note);
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventCallback.onEditClicked(note);
                }
            });
        }
    }

    public void add(Note note) {
        notes.add(note);
        notifyItemInserted(notes.size() - 1);
    }

    public void delete(Note note) {
        int index = notes.indexOf(note);
        notes.remove(index);
        notifyItemRemoved(index);
    }

    public void update(Note note) {
        int index = notes.indexOf(note);
        notes.set(index, note);
        notifyItemChanged(index);
    }

    public interface NoteViewEventCallback {
        void onDeleteClicked(Note note);

        void onEditClicked(Note note);

        void onGetItemCountCalled(int count);
    }
}



