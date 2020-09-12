package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class editnote extends AppCompatActivity {
     String id,title,desc,deletetitle,deletedesc;
     EditText notetitle, notedesc;
     TextView suggestiontext;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    GoogleSignInAccount acct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnote);
        suggestiontext=findViewById(R.id.suggestiontext);
        Intent intent = getIntent();
        title=intent.getExtras().getString("title");
        desc=intent.getExtras().getString("desc");
        id=intent.getExtras().getString("docid");
        acct = GoogleSignIn.getLastSignedInAccount(this);
        notetitle=findViewById(R.id.edittitle);
        notedesc=findViewById(R.id.editdescription);

        notetitle.setText(title);
        notedesc.setText(desc);
        if(title.contains("recipe")){
            suggestiontext.setVisibility(View.VISIBLE);
        }
    }
    public void suggestion(View view){
        Intent intent = new Intent(editnote.this,recipiesuggestion.class);
        intent.putExtra("recipe",title);
        startActivity(intent);
    }

    public void savenote(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").document(id).update("title",notetitle.getText().toString());
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").document(id).update("desc",notedesc.getText().toString());
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        backbutton();
    }
    public void deletenote(View view){
        deletetitle=notetitle.getText().toString();
        deletedesc=notedesc.getText().toString();
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").document(id).delete();
        Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
        backbuttonfordelete();
    }

    public void sharenote(View view){
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
    public void backbuttonfordelete(){

      Intent intent = new Intent(this,mainpage.class);
      intent.putExtra("deletetitle",deletetitle);
      intent.putExtra("deletedesc",deletedesc);
      intent.putExtra("check",1);
      startActivity(intent);

    }
}