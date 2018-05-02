package com.example.breysi.biblio_virtual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    TextView tx;
    public ArrayList<String> listaLibrosPrestados = new ArrayList<>();
    Usuario u;

    //--------------------------------------------------------
    ImageButton btn_libros_prestados;
    ImageButton btn_anadir_libros;
    ImageButton btn_cuenta;
    ImageButton btn_configuracion;
    Usuario user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tx = (TextView) findViewById(R.id.textView);
        btn_libros_prestados = (ImageButton) findViewById(R.id.btn_libros_prestados);
        btn_anadir_libros = (ImageButton) findViewById(R.id.btn_buscar_anadir_libros);
        btn_cuenta = (ImageButton) findViewById(R.id.btn_cuenta);
        btn_configuracion = (ImageButton) findViewById(R.id.btn_configuracion);

        btn_libros_prestados.setOnClickListener(this);
        btn_anadir_libros.setOnClickListener(this);
        btn_cuenta.setOnClickListener(this);
        btn_configuracion.setOnClickListener(this);

        // String nombre= getIntent().getStringExtra("nombre"); // SOLO POR EL NOMBRE
        user = (Usuario) getIntent().getSerializableExtra("usuarioo");
        String nom_user = user.getDni();
        tx.setText(user.getNombre());
        // tx.setText(nombre); //  SOLO POR EL NOMBRE

        Toast.makeText(Perfil.this, user.getCurso(), Toast.LENGTH_SHORT).show();
        Toast.makeText(Perfil.this, user.getNombre(), Toast.LENGTH_SHORT).show();
/*
     Intent i = getIntent();
     listUsuario = i.getStringArrayListExtra("lista");*/

        // autentificar_usuario(nombre);  SOLO POR EL NOMBRE


    }


    public void librosPrestados() {
        //----------LIBRO------------------------

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        final Query q_librosPrestados = myRef.child("libros_prestados").orderByChild("id_usuario").equalTo(user.getDni());

        Toast.makeText(Perfil.this, user.getDni(), Toast.LENGTH_LONG).show();
        q_librosPrestados.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Libro tokenLibro = new Libro();
                    //String nombre_usuario = "", apellido_usuario = "";
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        tokenLibro.setAutor((String) item.child("autor").getValue());
                        tokenLibro.setTitulo((String) item.child("titulo").getValue());
                        tokenLibro.setEditorial((String) item.child("editorial").getValue());
                      //  listaLibrosPrestados.add(tokenLibro);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        Intent intent1;
        Intent intent2;
        switch (view.getId()) {
            case R.id.btn_libros_prestados:
                intent = new Intent(Perfil.this, LibrosPrestados.class);
                startActivity(intent);
                break;
            case R.id.btn_buscar_anadir_libros:
                intent1 = new Intent(Perfil.this, BuscarLibros.class);
                startActivity(intent1);
                break;
            case R.id.btn_cuenta:
                intent2 = new Intent(Perfil.this, CuentaUsuario.class);
                startActivity(intent2);
                break;

            default:
                Toast.makeText(Perfil.this, "Error", Toast.LENGTH_LONG).show();
        }
    }
}



//-----------------
   /* public void autentificar_usuario(final String auth_email) { SOLO POR EL NOMBRE
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

      //  final Query q_email = myRef.child("usuario").orderByChild("nombre").equalTo(auth_email); SOLO POR EL NOMBRE

    //    final Query q_pass = myRef.child("usuario").orderByChild("clave").equalTo(auth_password);


        //consulta
         final Usuario tokenUser = new Usuario();

        q_email.addListenerForSingleValueEvent(new ValueEventListener() { // SOLO POR EL NOMBRE
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean claveCorrecta = false;
                if (dataSnapshot.exists()) {
                     //tokenUser =  new Usuario(); ;
                    //String nombre_usuario = "", apellido_usuario = "";
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        tokenUser.setApellido((String) item.child("apellido").getValue());
                        tokenUser.setClave((String) item.child("clave").getValue());
                        tokenUser.setCurso((String) item.child("curso").getValue());
                        tokenUser.setDni((String)item.child("dni").getValue());
                        tokenUser.setEmail((String)item.child("email").getValue());
                        // tokenUser.setFechaNacimiento((Date) item.child("fechaNacimiento").getValue());
                        tokenUser.setNombre((String)item.child("nombre").getValue());
                        tokenUser.setPoblacion((String)item.child("poblacion").getValue());
                        tokenUser.setProvincia((String)item.child("provincia").getValue());
                        tokenUser.setTelefono((String)item.child("telefono").getValue());
                    }
                    Toast.makeText(Perfil.this, "HOLAAAAAA", Toast.LENGTH_LONG).show();

                    String apellido = tokenUser.getApellido();
                        Toast.makeText(Perfil.this, apellido, Toast.LENGTH_LONG).show();
                    //q_pass.getRef().getDatabase();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */