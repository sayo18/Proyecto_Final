package com.example.breysi.biblio_virtual;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by breysi on 28/04/2018.
 */

public class BuscarLibros  extends AppCompatActivity  implements View.OnClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    EditText tv_buscar;
    ImageButton btn_buscar;
    RecyclerView mResultList;

    Button btn_busqueda_avanzada;
    ListView listview_libros;
    ImageView imageViewlibro;
    public static ArrayList<Libro> listaLi = new ArrayList<Libro>();

    ArrayAdapter<String> adapter;

    ValueEventListener valueListener;

    final Query q_libro = myRef.child("libro").orderByChild("titulo");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_anadir_libros);


        tv_buscar = (EditText) findViewById(R.id.tv_buscar);
        btn_busqueda_avanzada = (Button) findViewById(R.id.btn_busqueda_avanzada);
        listview_libros = (ListView) findViewById(R.id.listview_libros);
        imageViewlibro = (ImageView) findViewById(R.id.imageView_libro);
        btn_buscar = (ImageButton) findViewById(R.id.btn_buscar);

        btn_busqueda_avanzada.setOnClickListener(this);
        btn_buscar.setOnClickListener(this);

       // adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaLi.get(3).get);


        //final Query q_libro = myRef.child("libro").orderByChild("titulo");



        q_libro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {


                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        Libro libro = new Libro();
                        libro.setTitulo((String) item.child("titulo").getValue());
                        libro.setAutor((String) item.child("autor").getValue());
                        libro.setPortada(item.child("portada").getValue(String.class));
                        Toast.makeText(BuscarLibros.this,libro.getPortada(),Toast.LENGTH_SHORT).show();

                        listaLi.add(libro);
                    }
                    Lista ();
                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


      //      myRef.child("libro").addListenerForSingleValueEvent((ValueEventListener) q_libro);
        }


    private void buscarLibro (String textBuscar) { //ERROR!
        Query q = myRef.child("libro").orderByChild("titulo").equalTo(textBuscar);
        q.addValueEventListener((ValueEventListener) q_libro);




    }

    private void Lista () {

        listview_libros.setAdapter(null); //vacio la lista

        for (final Libro libro : listaLi) {
            String titulo = libro.getTitulo();
            String autor = libro.getAutor();
            String portada = libro.getPortada();


            //MI ADAPTADOR (CLASS)
            ListAdapter adapter = new AdaptadordeListas(
                    BuscarLibros.this,listaLi
            );
            listview_libros.setAdapter(adapter);

            listview_libros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                    final int pos = (position);

                    AlertDialog.Builder alert = new AlertDialog.Builder(BuscarLibros.this);
                  /*  alert.setMessage(R.string.borrar);
                    alert.setPositiveButton(R.string.si,
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //TODO
                                    //db.deleteVehicle(vehicles.get(pos));
                                    borrarElemento(listaV.get(pos));
                                    Toast.makeText(MainActivity.this,"Registre esborrat",Toast.LENGTH_SHORT).show();
                                }
                            });
                    alert.setNegativeButton(R.string.no,new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(MainActivity.this,"Registre no esborrat",Toast.LENGTH_SHORT).show();
                        }});
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
                    alert2.setMessage(R.string.modificar);
                    alert2.setPositiveButton(R.string.si,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //db.modificarVehicle(vehicles.get(pos));
                                    Intent intent1;
                                    //TODO
                                    intent1 = new Intent(MainActivity.this, Modificar.class);
                                    intent1.putExtra("posicion", pos);
                                    startActivity(intent1);

                                    Toast.makeText(MainActivity.this,"Registro modificado", Toast.LENGTH_SHORT).show();
                                }
                            });
                    alert2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "Registro no modificado", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog alertDialog2 = alert2.create();
                    alertDialog2.show();*/
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_buscar:
                String textBuscar = tv_buscar.getText().toString();
                buscarLibro(textBuscar);
                break;
            case R.id.btn_busqueda_avanzada:
                break;

        }
    }
}
