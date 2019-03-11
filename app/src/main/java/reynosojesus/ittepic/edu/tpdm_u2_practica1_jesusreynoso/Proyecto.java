package reynosojesus.ittepic.edu.tpdm_u2_practica1_jesusreynoso;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Proyecto {
    private int idproyecto;
    private String descripcion;
    private String ubicacion;
    private Date fecha;
    private float presupuesto;
    private BasedeDatos base;
    String error;

    public Proyecto(Activity activity){
        base = new BasedeDatos(activity,"PROYECTOS",null,1);
    }

    public Proyecto(int idproyecto, String descripcion, String ubicacion, Date fecha, float presupuesto) {
        this.idproyecto = idproyecto;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.presupuesto = presupuesto;
    }

    public int getIdproyecto() {
        return idproyecto;
    }

    public void setIdproyecto(int idproyecto) {
        this.idproyecto = idproyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }


    public boolean insertar(Proyecto proyecto){
        try{
            SQLiteDatabase Tinsertar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("DESCRIPCION",proyecto.getDescripcion());
            datos.put("UBICACION",proyecto.getUbicacion());
            datos.put("FECHA",proyecto.getFecha()+"");
            datos.put("PRESUPUESTO",proyecto.getPresupuesto());
            long resultado = Tinsertar.insert("PROYECTOS","IDPROYECTO",datos);
            Tinsertar.close();
            if(resultado ==-1) return false;
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public boolean eliminar(String ID) {
        int resultado=0;
        try {
            SQLiteDatabase Teliminar = base.getWritableDatabase();
            String id[] = {ID};
            resultado = Teliminar.delete("PROYECTOS", "IDPROYECTO=?", id);
            Teliminar.close();
        } catch (SQLiteException e) {
            error = e.getMessage();
            return false;
        }
        return resultado>0;
    }

    public boolean actualizar(Proyecto proyecto){
        try {
            SQLiteDatabase Tactualizar = base.getWritableDatabase();
            ContentValues datos = new ContentValues();
            String id[] = {proyecto.getIdproyecto()+""};
            datos.put("DESCRIPCION",proyecto.getDescripcion());
            datos.put("UBICACION",proyecto.getUbicacion());
            datos.put("FECHA",proyecto.getFecha()+"");
            datos.put("PRESUPUESTO",proyecto.getPresupuesto());
            long resultado = Tactualizar.update("PROYECTOS",datos, "IDPROYECTO=?", id);
            Tactualizar.close();
            if (resultado == -1) return false;
        }catch (SQLiteException e){
            error = e.getMessage();
            return false;
        }
        return true;
    }


    public Proyecto[] consultar(){
        SQLiteDatabase Tconsultar = base.getReadableDatabase();
        Proyecto[] result = null;
            Cursor c = Tconsultar.rawQuery("SELECT * FROM PROYECTOS",null);
        if(c.moveToFirst()){
            result = new Proyecto[c.getColumnCount()];
            for (int i=0;c.moveToNext();i++){
                try {
                int id= c.getInt(0);
                String desc= c.getString(1);
                String ubic= c.getString(2);
                Date fecha=new SimpleDateFormat("dd/MM/yyyy").parse(c.getString(3));
                float presupuesto= c.getFloat(4);
                result[i]= new Proyecto(id,desc,ubic,fecha,presupuesto);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Proyecto[] consultar(String columna, String clave){
        Proyecto[] resultado= null;
        try{
            SQLiteDatabase transacciónConsulta = base.getReadableDatabase();
            String SQL = "SELECT * FROM PROYECTOS WHERE IDPROYECTO="+clave;
            if(columna.startsWith("DESCRIPCION")){
                SQL = "SELECT * FROM PROYECTOS WHERE DESCRIPCION='"+clave+"'";
            }
            if(columna.startsWith("UBICACION")){
                SQL = "SELECT * FROM PROYECTOS WHERE UBICACION='"+clave+"'";
            }
            if(columna.startsWith("FECHA")){
                SQL = "SELECT * FROM PROYECTOS WHERE FECHA='"+clave+"'";
            }
            if(columna.startsWith("PRESUPUESTO")){
                SQL = "SELECT * FROM PROYECTOS WHERE PRESUPUESTO>="+clave;
            }
            Cursor c = transacciónConsulta.rawQuery(SQL, null);
            if(c.moveToFirst()){
                resultado = new Proyecto[c.getCount()];
                int pos = 0;
                do{
                    resultado[pos]=new Proyecto(c.getInt(0),c.getString(1),c.getString(2),
                            new SimpleDateFormat("dd/MM/yyyy").parse(c.getString(3)),c.getFloat(4));
                    pos++;
                }while (c.moveToNext());
            }
            transacciónConsulta.close();
        }catch (SQLiteException e){
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
