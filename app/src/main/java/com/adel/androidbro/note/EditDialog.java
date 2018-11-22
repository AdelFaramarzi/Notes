package com.adel.androidbro.note;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDialog extends android.support.v4.app.DialogFragment {
    private static final String EXTRA_KEY_NOTE = "note";
    private EditText titleEt;
    private EditText descriptionEt;
    private ResultCallback resultCallback;
    private Note note;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(EXTRA_KEY_NOTE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_note, null);
        Button saveButton = view.findViewById(R.id.btn_edit);
        titleEt = view.findViewById(R.id.et_edit_title);
        descriptionEt = view.findViewById(R.id.et_edit_description);
        if (note != null) {
            titleEt.setText(note.getTitle());
            descriptionEt.setText(note.getDescription());
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleEt.length() > 0 && descriptionEt.length() > 0) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setTitle(titleEt.getText().toString());
                    note.setDescription(descriptionEt.getText().toString());
                    note.setDate(System.currentTimeMillis());
                    resultCallback.onSave(note);
                    dismiss();
                } else {
                    if (titleEt.length() == 0) {
                        titleEt.setError("Title cannot be empty");
                    } else {
                        descriptionEt.setError("Description cannot be empty");
                    }
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public static EditDialog newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_KEY_NOTE, note);
        EditDialog editDialog = new EditDialog();
        editDialog.setArguments(args);
        return editDialog;
    }

    public void setResultCallback(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    public interface ResultCallback {
        void onSave(Note note);
    }
}
