package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class editnote extends AppCompatActivity {
     String id,title,desc;
     EditText notetitle, notedesc;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    GoogleSignInAccount acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        Intent intent = getIntent();
        title=intent.getExtras().getString("title");
        desc=intent.getExtras().getString("desc");
        id=intent.getExtras().getString("docid");
        acct = GoogleSignIn.getLastSignedInAccount(this);
        notetitle=findViewById(R.id.edittitle);
        notedesc=findViewById(R.id.editdescription);

        notetitle.setText(title);
        notedesc.setText(desc);
    }

    public void savenote(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").document(id).update("title",notetitle.getText().toString());
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").document(id).update("desc",notedesc.getText().toString());
    }
    public void deletenote(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").document(id).delete();
    }

    public void sharenote(View view){

    }
}