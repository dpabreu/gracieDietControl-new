package br.com.cds.graciedietcontrol.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.model.Grupos;
import br.com.cds.graciedietcontrol.sqlhelper.CustomSQLiteOpenHelper;

public class GruposDao {
    private SQLiteDatabase dataBase;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID, CustomSQLiteOpenHelper.COLUMN_NOME};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;

    public GruposDao(Context context){
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    public void open(){
        dataBase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteOpenHelper.close();
    }

    public Grupos create(String nomeGrupo){
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_NOME, nomeGrupo);

        long insertId = dataBase.insert(CustomSQLiteOpenHelper.TABLE_GRUPOS, null, values);

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_GRUPOS, columns,
                CustomSQLiteOpenHelper.COLUMN_ID +" = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();

        Grupos newGrupo = new Grupos();
        newGrupo.setIdGrupo(cursor.getLong(0));
        newGrupo.setNome(cursor.getString(1));

        cursor.close();

        return newGrupo;
    }

    public Grupos update(String nomeGrupo, long idGrupo){
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_NOME, nomeGrupo);

        int updateId = dataBase.update(CustomSQLiteOpenHelper.TABLE_GRUPOS, values,
                CustomSQLiteOpenHelper.COLUMN_ID + "=" + Long.toString(idGrupo),
                null);

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_GRUPOS, columns,
                CustomSQLiteOpenHelper.COLUMN_ID + "=" + updateId, null,
                null, null, null);

        cursor.moveToFirst();

        Grupos grupoEdit = new Grupos();
        grupoEdit.setIdGrupo(cursor.getLong(0));
        grupoEdit.setNome(cursor.getString(1));

        cursor.close();
        return grupoEdit;
    }

    public void delete(Grupos grupo){
        long id = grupo.getIdGrupo();
        dataBase.delete(CustomSQLiteOpenHelper.TABLE_GRUPOS, CustomSQLiteOpenHelper.COLUMN_ID +"="+ id,
                null);
    }

    public List<Grupos> getAll(){
        List<Grupos> grupos = new ArrayList<>();

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_GRUPOS, columns, null,
                null,null, null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            Grupos grupo = new Grupos();
            grupo.setIdGrupo(cursor.getLong(0));
            grupo.setNome(cursor.getString(1));
            grupos.add(grupo);

            cursor.moveToNext();
        }
        cursor.close();
        return grupos;
    }

    public List<Grupos> getGrupoById(long id){
        List<Grupos> grupos = new ArrayList<>();

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_GRUPOS, columns,
                CustomSQLiteOpenHelper.COLUMN_ID + "=" + id,null,
                null, null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){

            Grupos grupo = new Grupos();
            grupo.setIdGrupo(cursor.getLong(0));
            grupo.setNome(cursor.getString(1));
            grupos.add(grupo);

            cursor.moveToNext();
        }
        cursor.close();
        return grupos;
    }
}
