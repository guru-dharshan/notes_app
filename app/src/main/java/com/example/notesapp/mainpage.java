package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class mainpage extends AppCompatActivity {
    TextView username;
    ImageView usephoto;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acct;
    RecyclerView recyclerView;
    EditText searchtext;
    public static List<note> notearray;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        recyclerView=findViewById(R.id.noterecycle);
        searchtext=findViewById(R.id.searchtext);

        username=findViewById(R.id.username);
        usephoto=findViewById(R.id.userphoto);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

          username.setText("hi "+personName+"!");
            Glide.with(this)
                    .load(String.valueOf(username))
                    .override(100, 100)
                    .error(R.drawable.ic_launcher_background)
                    .into(usephoto);


        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").addSnapshotListener(this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                notearray = new ArrayList<>();

                for (QueryDocumentSnapshot documentSnapshot : value) {
                    note note = documentSnapshot.toObject(note.class);
                    note.setDocumentId(documentSnapshot.getId());
                    notearray.add(note);
                }
                noteadapter noteadapter = new noteadapter(mainpage.this,notearray);
                recyclerView.setAdapter(noteadapter);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(mainpage.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);

            }

        });
    }


    public void create(View view){
         Intent intent = new Intent(this,notemake.class);
        startActivity(intent);
       /** firestore.collection("Note Book").document(acct.getId()).collection("childnotes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    note note = documentSnapshot.toObject(note.class);
                    note.setDocumentId(documentSnapshot.getId());
                    notearray.add(note);
                }
                noteadapter noteadapter = new noteadapter(mainpage.this,notearray);
                recyclerView.setAdapter(noteadapter);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(mainpage.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);
            }
        });**/

    }

    public void search(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").whereEqualTo("title",searchtext.getText().toString())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                notearray = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    note note = documentSnapshot.toObject(note.class);
                    note.setDocumentId(documentSnapshot.getId());
                    notearray.add(note);
                }
                noteadapter noteadapter = new noteadapter(mainpage.this,notearray);
                recyclerView.setAdapter(noteadapter);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(mainpage.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);

            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mainpage.this, "no notes found!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void showall(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                notearray = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    note note = documentSnapshot.toObject(note.class);
                    note.setDocumentId(documentSnapshot.getId());
                    notearray.add(note);
                }
                noteadapter noteadapter = new noteadapter(mainpage.this,notearray);
                recyclerView.setAdapter(noteadapter);
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(mainpage.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(horizontalLayoutManagaer);
            }
        });
    }

    public void showassc(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").orderBy("title", Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        notearray = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            note note = documentSnapshot.toObject(note.class);
                            note.setDocumentId(documentSnapshot.getId());
                            notearray.add(note);
                        }
                        noteadapter noteadapter = new noteadapter(mainpage.this,notearray);
                        recyclerView.setAdapter(noteadapter);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(mainpage.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(horizontalLayoutManagaer);

                    }

                });
    }
    public void showdesc(View view){
        firestore.collection("Note Book").document(acct.getId()).collection("childnotes").orderBy("title", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        notearray = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            note note = documentSnapshot.toObject(note.class);
                            note.setDocumentId(documentSnapshot.getId());
                            notearray.add(note);
                        }
                        noteadapter noteadapter = new noteadapter(mainpage.this,notearray);
                        recyclerView.setAdapter(noteadapter);
                        LinearLayoutManager horizontalLayoutManagaer
                                = new LinearLayoutManager(mainpage.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(horizontalLayoutManagaer);

                    }

                });
    }

    public void setting(View view){
        Intent intent=new Intent(this,setting.class);
        startActivity(intent);
        finish();
    }
}