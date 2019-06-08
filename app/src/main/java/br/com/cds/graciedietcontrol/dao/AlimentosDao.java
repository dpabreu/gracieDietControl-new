package br.com.cds.graciedietcontrol.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.model.Alimentos;
import br.com.cds.graciedietcontrol.sqlhelper.CustomSQLiteOpenHelper;

public class AlimentosDao {

    private SQLiteDatabase dataBase;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID_ALIMENTO,
            CustomSQLiteOpenHelper.COLUMN_NOME_ALIMENTO,
            CustomSQLiteOpenHelper.COLUMN_ID_SUB_GRUPO_FK};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;

    public AlimentosDao(Context context){
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    public void open(){
        dataBase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteOpenHelper.close();
    }

    public Alimentos create(String nomeAlimento, long idSubGrupoFk){
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_NOME_ALIMENTO, nomeAlimento);
        values.put(CustomSQLiteOpenHelper.COLUMN_ID_SUB_GRUPO_FK, idSubGrupoFk);

        long insertId = dataBase.insert(CustomSQLiteOpenHelper.TABLE_ALIMENTOS, null, values);

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_ALIMENTOS, columns,
                CustomSQLiteOpenHelper.COLUMN_ID_ALIMENTO +" = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();

        Alimentos newAlimento = new Alimentos();
        newAlimento.setIdAlimento(cursor.getLong(0));
        newAlimento.setNome(cursor.getString(1));
        newAlimento.setIdSubGrupo(cursor.getLong(2));

        cursor.close();

        return newAlimento;
    }

    public void delete(Alimentos alimento){
        long id = alimento.getIdAlimento();
        dataBase.delete(CustomSQLiteOpenHelper.TABLE_ALIMENTOS,
                CustomSQLiteOpenHelper.COLUMN_ID_ALIMENTO +"="+ id,
                null);
    }

    public List<Alimentos> getAll() {
        List<Alimentos> alimentos = new ArrayList<>();

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_ALIMENTOS, columns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Alimentos alimento = new Alimentos();
            alimento.setIdAlimento(cursor.getLong(0));
            alimento.setNome(cursor.getString(1));
            alimento.setIdSubGrupo(cursor.getLong(2));

            alimentos.add(alimento);

            cursor.moveToNext();
        }
        cursor.close();
        return alimentos;
    }
}
