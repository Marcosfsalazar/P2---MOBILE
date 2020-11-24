
package com.example.p2_moveis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.p2_moveis.model.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtIdade;
    FloatingActionButton buttonAdd;
    ListView lista;

    private Pessoa pessoa;

    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Object> listaPessoa = new ArrayList<Object>();
    ArrayAdapter<Object> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (EditText)findViewById(R.id.txtName);
        txtIdade = (EditText)findViewById(R.id.txtIdade);
        buttonAdd = (FloatingActionButton) findViewById(R.id.buttonAdd);
        lista = (ListView)findViewById(R.id.lista);

        myAdapter = new ArrayAdapter<Object>(MainActivity.this, android.R.layout.simple_list_item_1,listaPessoa);

        lista.setAdapter(myAdapter);


        iniciaFirebase();

        this.clickButtonListener();

    }

    private void clickButtonListener(){
        this.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pessoa pessoa = getPessoa();
                if(getPessoa() != null){
                    databaseReference.child("pessoa").child(pessoa.getNome()).setValue(pessoa);
                }
            }
        });
    }

    private void iniciaFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private Pessoa getPessoa(){
        this.pessoa = new Pessoa();

        if(this.txtName.getText().toString().isEmpty() == false){
            this.pessoa.setNome(this.txtName.getText().toString());
        }else{
            return null;
        }

        if(this.txtIdade.getText().toString().isEmpty() == false){
            this.pessoa.setIdade(Integer.parseInt(this.txtIdade.getText().toString()));
        }else{
            return null;
        }

        return pessoa;

    }

    private void buscaItens(){
        databaseReference.child("pessoa");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object objeto = snapshot.child("pessoa").getValue(Object.class);
                listaPessoa.add(objeto);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}