package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;

public class notemake extends AppCompatActivity {
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    EditText title,description;
    GoogleSignInAccount acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notemake);
        title=findViewById(R.id.title);
        description=findViewById(R.id.description);
        acct = GoogleSignIn.getLastSignedInAccount(this);

    }
    public void save(View view){

        note note = new note(title.getText().toString(),description.getText().toString());

        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").add(note);
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        backbutton();
    }
    public void backtohomepage(View view){
        backbutton();
    }
    public void backbutton(){
        Intent intent=new Intent(this,mainpage.class);
        startActivity(intent);
        finish();
    }
}