package br.com.cds.graciedietcontrol.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.model.Alimentos;
import br.com.cds.graciedietcontrol.model.Motivos;
import br.com.cds.graciedietcontrol.model.Refeicoes;
import br.com.cds.graciedietcontrol.sqlhelper.CustomSQLiteOpenHelper;

public class RefeicoesDao {

    private SQLiteDatabase dataBase;
    private String[] columns = {CustomSQLiteOpenHelper.COLUMN_ID_REFEICAO,
            CustomSQLiteOpenHelper.COLUMN_TIPO_REFEICAO,
            CustomSQLiteOpenHelper.COLUMN_REFEICAO_VALIDA};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;

    public RefeicoesDao(Context ctx){
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(ctx);
    }

    public void open(){
        dataBase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteOpenHelper.close();
    }

    public Refeicoes create(String tipoRefeicao, Integer refeicaoValida,
                            List<Alimentos> alimentos, Motivos motivo){
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_TIPO_REFEICAO, tipoRefeicao);
        values.put(CustomSQLiteOpenHelper.COLUMN_REFEICAO_VALIDA, refeicaoValida);

        long insertId = dataBase.insert(CustomSQLiteOpenHelper.TABLE_REFEICOES, null, values);

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_REFEICOES, columns,
                CustomSQLiteOpenHelper.COLUMN_ID_REFEICAO +" = " + insertId, null,
                null,null,null);
        cursor.moveToFirst();

        Refeicoes newRefeicao = new Refeicoes();
        newRefeicao.setIdRefeicao(cursor.getLong(0));
        newRefeicao.setTipoRefeicao(cursor.getString(1));
        newRefeicao.setRefeicaoValida(cursor.getInt(2));
        cursor.close();

        for (Alimentos alimento : alimentos) {
            ContentValues valuesAliementos = new ContentValues();
            valuesAliementos.put(CustomSQLiteOpenHelper.COLUMN_ID_REFEICAO_FK, newRefeicao.getIdRefeicao());
            valuesAliementos.put(CustomSQLiteOpenHelper.COLUMN_ID_ALIMENTO_FK, alimento.getIdAlimento());

            dataBase.insert(CustomSQLiteOpenHelper.TABLE_REFEICOES_ALIMENTOS,null,
                    valuesAliementos);
        }
        newRefeicao.setAlimentos(alimentos);

        if(newRefeicao.getRefeicaoValida()==0){
            ContentValues valuesRefeicoesMotivos = new ContentValues();
            valuesRefeicoesMotivos.put(CustomSQLiteOpenHelper.COLUMN_ID_MOTIVO_FK, motivo.getIdMotivo());
            valuesRefeicoesMotivos.put(CustomSQLiteOpenHelper.COLUMN_ID_REFEICAO_MOT_FK, newRefeicao.getIdRefeicao());

            dataBase.insert(CustomSQLiteOpenHelper.CREATE_TABLE_MOTIVOS_REFEICOES,null,
                    valuesRefeicoesMotivos);

        }
        return newRefeicao;
    }

    public List<Refeicoes> getAll(){
        List<Refeicoes> refeicoes = new ArrayList<>();

        Cursor cursor = dataBase.query(CustomSQLiteOpenHelper.TABLE_REFEICOES, columns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Refeicoes refeicao = new Refeicoes();
            refeicao.setIdRefeicao(cursor.getLong(0));
            refeicao.setTipoRefeicao(cursor.getString(1));
            refeicao.setRefeicaoValida(cursor.getInt(2));

            refeicoes.add(refeicao);

            cursor.moveToNext();
        }
        cursor.close();
        return refeicoes;
    }

}
