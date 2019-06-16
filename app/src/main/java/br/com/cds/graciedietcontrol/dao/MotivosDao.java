package br.com.cds.graciedietcontrol.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.model.Motivos;
import br.com.cds.graciedietcontrol.sqlhelper.CustomSQLiteOpenHelper;

public class MotivosDao {

    private SQLiteDatabase dataBase;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID_MOTIVO,
            CustomSQLiteOpenHelper.COLUMN_COD_MOTIVO,
            CustomSQLiteOpenHelper.COLUMN_MOTIVO};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;


    public MotivosDao(Context ctx){
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(ctx);
    }

    public void open(){
        dataBase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteOpenHelper.close();
    }

    public List<Motivos> getAll(){
        List<Motivos> motivos = new ArrayList<>();
        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_MOTIVOS, columns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Motivos motivo = new Motivos();
            motivo.setIdMotivo(cursor.getLong(0));
            motivo.setCodMotivo(cursor.getString(1));
            motivo.setMotivo(cursor.getString(2));
            motivos.add(motivo);

            cursor.moveToNext();
        }
        cursor.close();

        return motivos;
    }

    public Motivos getMotivoByCodMotivo(String codMotivo){
        Motivos motivo = new Motivos();

        String sqlQuery = "select id_motivo, cod_motivo, motivo from motivos where cod_motivo = ?";
        Cursor cursor = dataBase.rawQuery(sqlQuery, new String[] {codMotivo});
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            motivo.setIdMotivo(cursor.getLong(0));
            motivo.setCodMotivo(cursor.getString(1));
            motivo.setMotivo(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        return motivo;
    }
}
