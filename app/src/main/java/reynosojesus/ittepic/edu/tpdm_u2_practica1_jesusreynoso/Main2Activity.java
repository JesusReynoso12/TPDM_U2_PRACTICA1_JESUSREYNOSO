package reynosojesus.ittepic.edu.tpdm_u2_practica1_jesusreynoso;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Main2Activity extends AppCompatActivity {
    EditText desc,ubi,fecha,presupuesto;
    Button insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        desc = findViewById(R.id.txtDesc);
        ubi = findViewById(R.id.txtUbi);
        fecha = findViewById(R.id.txtFecha);
        presupuesto = findViewById(R.id.txtPres);
        insertar = findViewById(R.id.btnInsertar);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertar();
            }
        });
    }

    private void insertar() {
        try {
            String mensaje = "";
            Proyecto p = new Proyecto(this);
            boolean respuesta = p.insertar(new Proyecto(0, desc.getText().toString(), ubi.getText().toString(),
                    new SimpleDateFormat("dd/MM/yyyy").parse(fecha.getText().toString()),Float.parseFloat(presupuesto.getText().toString())));
            if (respuesta) {
                mensaje = "Se inserto Correctamente";
            } else {
                mensaje = "Error no se pudo Insertar";
            }
            AlertDialog.Builder alerta =new AlertDialog.Builder(this);
            alerta.setTitle("ATENCION").setMessage(mensaje).setPositiveButton("ok",null).show();
        }catch (Exception e){

        }
    }

}
