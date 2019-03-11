package reynosojesus.ittepic.edu.tpdm_u2_practica1_jesusreynoso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView Lista;
    Proyecto[] proyectos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Lista = findViewById(R.id.ListaProyectos);
    }


    @Override
    protected void onStart() {
        Proyecto proyecto = new Proyecto(this);
        proyectos = proyecto.consultar();
        String [] descripcionUbicacion=null;
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
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,descripcionUbicacion);
        Lista.setAdapter(adaptador);
        super.onStart();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Consultar:
                Intent inserta= new Intent(this,Main3Activity.class);
                startActivity(inserta);
                break;
            case R.id.Actualizar:
                Intent elimina= new Intent(this,Main4Activity.class);
                startActivity(elimina);
                break;
            case R.id.Insertar:
                Intent actualiza= new Intent(this,Main2Activity.class);
                startActivity(actualiza);
                break;
        }



        return super.onOptionsItemSelected(item);
    }
}
