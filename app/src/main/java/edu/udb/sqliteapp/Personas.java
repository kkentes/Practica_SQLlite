package edu.udb.sqliteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Personas extends AppCompatActivity {

    private EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas);

        et1=(EditText)findViewById(R.id.et1);
        et2=(EditText)findViewById(R.id.et2);
        et3=(EditText)findViewById(R.id.et3);
    }

    public void alta2(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        String cod = et1.getText().toString();
        String nom = et2.getText().toString();
        String ed = et3.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("codigo", cod);
        registro.put("nombres", nom);
        registro.put("edad", ed);

        try {
            bd.insertOrThrow("personas", null, registro);
            bd.close();
            et1.setText("");
            et2.setText("");
            et3.setText("");
            Toast.makeText(this, "Se cargaron los datos de la persona",Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
            Toast.makeText(this, "ERROR!! No se cargaron los datos de la persona" + e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    public void consultaporcodigo2(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = et1.getText().toString();
        Cursor fila = bd.rawQuery("select nombre,edad from personas where codigo=" + cod, null);
        if (fila.moveToFirst()) {
            et2.setText(fila.getString(0));
            et3.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe una persona con dicho código",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void consultapordescripcion2(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nom = et2.getText().toString();
        Cursor fila = bd.rawQuery("select codigo,nombre from personas where nombres='" + nom +"'", null);
        if (fila.moveToFirst()) {
            et1.setText(fila.getString(0));
            et3.setText(fila.getString(1));
        } else
            Toast.makeText(this, "No existe una persona con ese nombre",
                    Toast.LENGTH_SHORT).show();
        bd.close();
    }

    public void bajaporcodigo2(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod= et1.getText().toString();
        int cant = bd.delete("personas", "codigo=" + cod, null);
        bd.close();
        et1.setText("");
        et2.setText("");
        et3.setText("");
        if (cant == 1)
            Toast.makeText(this, "Se borró la persona con dicho código",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No existe una persona con dicho código",
                    Toast.LENGTH_SHORT).show();
    }

    public void modificacion2(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String cod = et1.getText().toString();
        String nom = et2.getText().toString();
        String ed = et3.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("codigo", cod);
        registro.put("nombre", nom);
        registro.put("edad", ed);
        int cant = bd.update("personas", registro, "codigo=" + cod, null);
        bd.close();
        if (cant == 1)
            Toast.makeText(this, "se modificaron los datos", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "no existe una persona con el código ingresado",
                    Toast.LENGTH_SHORT).show();
    }

    public void finalizarActividad(View v){
        finish();
    }
}