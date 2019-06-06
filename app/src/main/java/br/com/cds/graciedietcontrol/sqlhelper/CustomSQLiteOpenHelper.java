package br.com.cds.graciedietcontrol.sqlhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "graciediet.db";
    private static final int DATABASE_VERSION = 1;

    //Tabela Grupos
    public static final String TABLE_GRUPOS = "grupos";
    public static final String COLUMN_ID = "id_grupo";
    public static final String COLUMN_NOME = "nome";
    public static final String CREATE_TABLE_GRUPOS = "create table "+ TABLE_GRUPOS +
            "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NOME + " text not null);";

    //Tabela SubGrupos
    public static final String TABLE_SUB_GRUPOS = "sub_grupos";
    public static final String COLUMN_ID_SUB_GRUPO = "id_sub_grupo";
    public static final String COLUMN_NOME_SUB_GRUPO = "nome";
    public static final String COLUMN_ID_GRUPO_FK = "id_grupo";
    public static final String CREATE_TABLE_SUB_GRUPOS = "create table "+ TABLE_SUB_GRUPOS +
            "(" + COLUMN_ID_SUB_GRUPO + " integer primary key autoincrement, " +
            COLUMN_NOME_SUB_GRUPO + " text not null, "+
            COLUMN_ID_GRUPO_FK + " integer not null, "+
            " foreign key(id_grupo) references grupos(id_grupo));";

    //Tabela Alimentos
    public static final String TABLE_ALIMENTOS = "alimentos";
    public static final String COLUMN_ID_ALIMENTO = "id_alimento";
    public static final String COLUMN_NOME_ALIMENTO = "nome";
    public static final String COLUMN_ID_SUB_GRUPO_FK = "id_sub_grupo";
    public static final String CREATE_TABLE_ALIMENTOS = "create table "+ TABLE_ALIMENTOS +
            "(" + COLUMN_ID_ALIMENTO + " integer primary key autoincrement, " +
            COLUMN_NOME_ALIMENTO + " text not null, " +
            COLUMN_ID_SUB_GRUPO_FK + " integer not null, " +
            " foreign key(id_sub_grupo) references sub_grupos(id_sub_grupo));";

    public CustomSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GRUPOS);
        db.execSQL(CREATE_TABLE_SUB_GRUPOS);
        db.execSQL(CREATE_TABLE_ALIMENTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRUPOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUB_GRUPOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALIMENTOS);
        onCreate(db);
    }
}
