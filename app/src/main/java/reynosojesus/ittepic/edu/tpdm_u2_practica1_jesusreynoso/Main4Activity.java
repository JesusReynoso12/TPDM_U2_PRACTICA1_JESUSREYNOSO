package reynosojesus.ittepic.edu.tpdm_u2_practica1_jesusreynoso;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main4Activity extends AppCompatActivity {
    EditText id,desc,ubi,fecha,presupuesto;
    Button buscar,actualizar;
    Proyecto[] proyectos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        id = findViewById(R.id.txtID);
        desc = findViewById(R.id.txtDesc1);
        ubi = findViewById(R.id.txtUbi1);
        fecha = findViewById(R.id.txtFecha1);
        presupuesto = findViewById(R.id.txtPresupuesto1);
        buscar = findViewById(R.id.btnBuscar);
        actualizar = findViewById(R.id.btnActualizar);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });
    }

    private void actualizar() {
        try {
            Proyecto p = new Proyecto(this);
            String mensaje;
            boolean respuesta = p.actualizar(new Proyecto(0, desc.getText().toString(), ubi.getText().toString(),
                    new SimpleDateFormat("dd/MM/yyyy").parse(fecha.getText().toString()), Float.parseFloat(presupuesto.getText().toString())));

            if (respuesta) {
                mensaje = "se actualizo correctamente el proyecto ";
            } else {
                mensaje = "Error algo salio mal: " + p.error;
            }
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG);
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void consultar(){
        Proyecto proyecto = new Proyecto(this);
            proyectos = proyecto.consultar("IDPROYECTO",id.getText().toString());
            if(proyectos==null){
                Toast.makeText(this,"ERROR NO EXISTE ESE ID",Toast.LENGTH_LONG).show();
            }else{
                for (int i=0;i<proyectos.length;i++){
                    Proyecto temp= proyectos[i];
                    desc.setText(temp.getDescripcion());
                    ubi.setText(temp.getUbicacion());
                    fecha.setText(temp.getFecha()+"");
                    presupuesto.setText(temp.getPresupuesto()+"");
                }
            }
    }

}
