package com.example.personne;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et_name,et_email,et_update_name,et_update_email;
    Button add,btn_update,btn_cancel;
    RecyclerView recyclerView;
    MyAdapter adapter;
    List<UserData> list = new ArrayList<>();
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        add = (Button) findViewById(R.id.btn_add);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_name.getText().toString();
                email = et_email.getText().toString();
                UserData userData = new UserData();
                userData.setName(name);
                userData.setEmail(email);
                list.add(userData);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "User Add Success...", Toast.LENGTH_SHORT).show();
                et_name.setText("");
                et_email.setText("");
            }
        });
        adapter.setOnItemClickListener(new ItemClickListener() {

            @Override
            public void OnItemClick(int position, UserData userData) {

                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Update User Info");

                // créer une vue à partir du fichier xml de la boite de dialogue destinée à la
                //mise à jour d’un utilisateur

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_update, null, false);
                /*appeler une méthode qu’on définira après pour initialiser les
                champs Name et email de la boite de dialogue avec les informations de
                  l’utilisation sélectionné à la position « position »*/
                InitUpdateDialog(position, view);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
            }
        });
    }
        private void InitUpdateDialog(int position, View view) {

        et_update_name = view.findViewById(R.id.et_update_name);
        et_update_email = view.findViewById(R.id.et_update_email);
        btn_update = view.findViewById(R.id.btn_update_user);
        btn_cancel = view.findViewById(R.id.btn_update_cancel);
        et_update_name.setText(name);
        et_update_email.setText(email);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = et_update_name.getText().toString();
                email = et_update_email.getText().toString();
                UserData userData = new UserData();
                userData.setName(name);
                userData.setEmail(email);
                adapter.UpdateData(position,userData);
                Toast.makeText(MainActivity.this,"User Updated..",Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
             });
}


}

