package br.com.cds.graciedietcontrol.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.model.SubGrupos;
import br.com.cds.graciedietcontrol.sqlhelper.CustomSQLiteOpenHelper;

public class SubGruposDao {

    private SQLiteDatabase dataBase;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID_SUB_GRUPO,
            CustomSQLiteOpenHelper.COLUMN_NOME_SUB_GRUPO,
            CustomSQLiteOpenHelper.COLUMN_ID_GRUPO_FK};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;

    public SubGruposDao(Context context){
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    public void open(){
        dataBase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteOpenHelper.close();
    }

    public SubGrupos create(String nomeSubGrupo, long idGrupoFk){
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_NOME_SUB_GRUPO, nomeSubGrupo);
        values.put(CustomSQLiteOpenHelper.COLUMN_ID_GRUPO_FK, idGrupoFk);

        long insertId = dataBase.insert(CustomSQLiteOpenHelper.TABLE_SUB_GRUPOS, null, values);

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_SUB_GRUPOS, columns,
                CustomSQLiteOpenHelper.COLUMN_ID_SUB_GRUPO +" = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();

        SubGrupos newSubGrupo = new SubGrupos();
        newSubGrupo.setIdSubGrupo(cursor.getLong(0));
        newSubGrupo.setNome(cursor.getString(1));
        newSubGrupo.setIdGrupo(cursor.getLong(2));

        cursor.close();

        return newSubGrupo;
    }

    public void delete(SubGrupos subGrupo){
        long id = subGrupo.getIdSubGrupo();
        dataBase.delete(CustomSQLiteOpenHelper.TABLE_SUB_GRUPOS,
                CustomSQLiteOpenHelper.COLUMN_ID_SUB_GRUPO +"="+ id,
                null);
    }

    public List<SubGrupos> getAll(){
        List<SubGrupos> subGrupos = new ArrayList<>();

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_SUB_GRUPOS, columns, null,
                null,null, null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            SubGrupos subGrupo = new SubGrupos();
            subGrupo.setIdSubGrupo(cursor.getLong(0));
            subGrupo.setNome(cursor.getString(1));
            subGrupo.setIdGrupo(cursor.getLong(2));
            subGrupos.add(subGrupo);

            cursor.moveToNext();
        }
        cursor.close();
        return subGrupos;
    }

    public List<SubGrupos> getById(long id){
        List<SubGrupos> subGrupos = new ArrayList<>();

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_SUB_GRUPOS, columns,
                CustomSQLiteOpenHelper.COLUMN_ID_SUB_GRUPO + "=" + id,null,null,
                null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            SubGrupos subGrupo = new SubGrupos();
            subGrupo.setIdSubGrupo(cursor.getLong(0));
            subGrupo.setNome(cursor.getString(1));
            subGrupo.setIdGrupo(cursor.getLong(2));
            subGrupos.add(subGrupo);

            cursor.moveToNext();
        }
        cursor.close();
        return subGrupos;
    }

}
