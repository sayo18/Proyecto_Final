package com.example.breysi.biblio_virtual;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by breysi on 28/04/2018.
 */

public class LibrosPrestados  extends AppCompatActivity {
    ListView listaVista;
    TextView titulo,autor,fechaEntrega;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libros_prestados);

    }

/* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listalibrosprestados);

        Usuario user = (Usuario) getIntent().getSerializableExtra("usuarioo");
        // tx.setText(user.getNombre());

    }*/
}
