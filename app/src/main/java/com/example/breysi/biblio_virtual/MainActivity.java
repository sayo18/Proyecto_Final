package com.example.breysi.biblio_virtual;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_ingresar;
    AutoCompleteTextView id_usuario;
    EditText id_pass;
    FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener AuthListener;
    private ProgressDialog mProgress;


    private String mCustomToken;
    private static final String TAG = "CustomAuthActivity";
    private TokenBroadcastReceiver mTokenReceiver;

    public ArrayList<Usuario> listUsuario = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_usuario = (AutoCompleteTextView) findViewById(R.id.id_usuario);
        id_pass = (EditText) findViewById(R.id.id_pass);
        btn_ingresar = (Button) findViewById(R.id.btn_ingresar);

        mAuth = FirebaseAuth.getInstance();
        btn_ingresar.setOnClickListener(this);

        mProgress = new ProgressDialog(this);

        mTokenReceiver = new TokenBroadcastReceiver() {
            @Override
            public void onNewToken(String token) {
                Log.d(TAG, "onNewToken:" + token);
                setCustomToken(token);
            }
        };
    }

    @Override
    public void onClick(View view) {

        String usuario, pass;
        usuario = id_usuario.getText().toString();
        pass = id_pass.getText().toString();

        if (usuario.equals("") || pass.equals("")) {
            Toast.makeText(this, "Vacio ", Toast.LENGTH_LONG).show();
            return;
        } else {
            this.onStart();
            mProgress.setMessage("Ingresando, por favor espere...");
            mProgress.show();
            autentificar_usuario(usuario, pass);


        }


    }

    @Override
    protected void onStart() {// verifica q el usuario haya accedido
        super.onStart();
        //FirebaseUser user = mAuth.getCurrentUser();
        //updateUI(user);
    }


    String emailUsuario;
    String claveUsuario;

    public void autentificar_usuario(final String auth_email, String auth_password) {
        startSignIn();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        final Query q_email = myRef.child("usuario").orderByChild("email").equalTo(auth_email);

        final Query q_pass = myRef.child("usuario").orderByChild("clave").equalTo(auth_password);

        emailUsuario = auth_email;
        claveUsuario = auth_password;
        //consulta


        q_email.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean claveCorrecta = false;

                if (dataSnapshot.exists()) {
                    Usuario tokenUser =  new Usuario(); ;

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
                        //password

                        if(tokenUser.getClave().equals(claveUsuario)){

                            claveCorrecta = true;

                        }

                        listUsuario.add(tokenUser);
                    }
                    //q_pass.getRef().getDatabase();
                    if (claveCorrecta){
                        mProgress.dismiss();
                        Intent intent;
                        intent = new Intent(MainActivity.this, Perfil.class);
                        //intent.putExtra("nombre", tokenUser.getNombre());
                        intent.putExtra("usuarioo", tokenUser);

                        Toast.makeText(MainActivity.this, "Clave correcto", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Clave incorrecto", Toast.LENGTH_LONG).show();
                    }


                } else {

                    Toast.makeText(MainActivity.this, "NO LISTA", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void limpiarcampos() {


    }

    private void startSignIn() {
        //!!!!!!!!!!!!LOGIN!!!!!!!!!!!!!!!!!!!!!!
        String auth_email = "k@gmail.com";
        //  Log.e("email", auth_email);
        String auth_password = "123456";
        //Log.e("pass", auth_password);

        mAuth.signInWithEmailAndPassword(auth_email, auth_password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                    Toast.makeText(MainActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();

                }

                // ...
            }
        });

    }

    private void setCustomToken(String token) {
        mCustomToken = token;

        String status;
        if (mCustomToken != null) {
            status = "Token:" + mCustomToken;
        } else {
            status = "Token: null";
        }

        // Enable/disable sign-in button and show the token
        findViewById(R.id.btn_ingresar).setEnabled((mCustomToken != null));
        ((AutoCompleteTextView) findViewById(R.id.id_usuario)).setText(status);
    }

}
