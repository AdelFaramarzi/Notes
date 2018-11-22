package com.adel.androidbro.note;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteViewEventCallback

 {

    private NoteAdapter noteAdapter;
    private View emptyStatView;
    private FloatingActionButton addNoteFab;
    private View.OnClickListener addButtonOnClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditDialog editDialog =new EditDialog();
            editDialog.setResultCallback(new EditDialog.ResultCallback() {
                @Override
                public void onSave(Note note) {
                    noteAdapter.add(note);
                }
            });
            editDialog.show(getSupportFragmentManager(),null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();


    }


    private void setupViews(){
        android.support.v7.widget.Toolbar  toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.rv_main_notes);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        noteAdapter = new NoteAdapter(this);
        rv.setAdapter(noteAdapter);


        addNoteFab= findViewById(R.id.fab_main_add);
        addNoteFab.setOnClickListener(addButtonOnClickListener);

        Button addNoteBtn = findViewById(R.id.btn_main_add);
        addNoteBtn.setOnClickListener(addButtonOnClickListener);
        emptyStatView = findViewById(R.id.ll_main_emptyState);
    }

     @Override
     public void onDeleteClicked(Note note) {
        noteAdapter.delete(note);


     }

     @Override
     public void onEditClicked(Note note) {
        EditDialog editDialog= EditDialog.newInstance(note);

        editDialog.setCancelable(true);
        editDialog.setResultCallback(new EditDialog.ResultCallback() {
            @Override
            public void onSave(Note note) {
                noteAdapter.update(note);
            }
        });
        editDialog.show(getSupportFragmentManager(),null);
     }

     @Override
     public void onGetItemCountCalled(int count) {
        if(count>0){
            emptyStatView.setVisibility(View.GONE);
        }else{
            emptyStatView.setVisibility(View.VISIBLE);
        }

        if(emptyStatView.getVisibility() == View.VISIBLE){
            addNoteFab.setVisibility(View.GONE);
        }else {
            addNoteFab.setVisibility(View.VISIBLE);
        }
     }


 }
