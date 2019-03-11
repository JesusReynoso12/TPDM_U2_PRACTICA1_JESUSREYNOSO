package reynosojesus.ittepic.edu.tpdm_u2_practica1_jesusreynoso;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    ListView listaConsulta;
    RadioButton id,descripcion;
    Button botonconsultar;
    EditText parametro;
    Proyecto[] proyectos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listaConsulta= findViewById(R.id.listaproyectos);
        parametro = findViewById(R.id.parametro);
        id = findViewById(R.id.buscarid);
        descripcion = findViewById(R.id.buscarDescripcion);
        botonconsultar = findViewById(R.id.btnConsultar);
        botonconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });
        listaConsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eliminar();
            }
        });
    }



    public void eliminar(){
        if(id.isChecked()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setTitle("Atencion");
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String mensaje="";
                    Proyecto proyecto = new Proyecto(Main3Activity.this);
                    boolean resultado = proyecto.eliminar(parametro.getText().toString());
                    if (resultado) {
                        mensaje="Se elimino Correctamente";
                    }else {
                        mensaje="Error al Elimnar";
                    }
                    Toast.makeText(Main3Activity.this,mensaje,Toast.LENGTH_LONG).show();
                }
            });
            alerta.show();


        }
    }

    public void consultar(){
        Proyecto proyecto = new Proyecto(this);
        String [] descripcionUbicacion=null;
        if(id.isChecked()){
            proyectos = proyecto.consultar("IDPROYECTO",parametro.getText().toString());
            if(proyectos==null){
                descripcionUbicacion = new String[1];
                descripcionUbicacion[0]="No Hay Proyectos";
            }else{
                descripcionUbicacion = new String[proyectos.length];
                for (int i=0;i<proyectos.length;i++){
                    Proyecto temp= proyectos[i];
                    descripcionUbicacion[i]=temp.getDescripcion()+"\n"+temp.getUbicacion();
                }
            }
        }else if(descripcion.isChecked()){
            proyectos = proyecto.consultar("DESCRIPCION",parametro.getText().toString());
            if(proyectos==null){
                descripcionUbicacion = new String[1];
                descripcionUbicacion[0]="No Hay Proyectos";
            }else{
                descripcionUbicacion = new String[proyectos.length];
                for (int i=0;i<proyectos.length;i++){
                    Proyecto temp= proyectos[i];
                    descripcionUbicacion[i]=temp.getDescripcion()+"\n"+temp.getUbicacion();
                }
            }
        }else{
          Toast.makeText(this,"Selecciona Una Opcion de Busqueda",Toast.LENGTH_LONG).show();

        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Main3Activity.this,android.R.layout.simple_list_item_1,descripcionUbicacion);
        listaConsulta.setAdapter(adaptador);
    }

}
