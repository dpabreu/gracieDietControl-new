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

    //Tabela Refeições
    public static final String TABLE_REFEICOES = "refeicoes";
    public static final String COLUMN_ID_REFEICAO = "id_refeicao";
    public static final String COLUMN_TIPO_REFEICAO = "tipo_refeicao";
    public static final String COLUMN_REFEICAO_VALIDA = "refeicao_valida";
    public static final String COLUMN_DATA_REFEICAO =  "data_refeicao";
    public static final String CREATE_TABLE_REFEICOES = "create table "+ TABLE_REFEICOES +
            "(" + COLUMN_ID_REFEICAO + " integer primary key autoincrement, " +
            COLUMN_TIPO_REFEICAO + " text not null, " +
            COLUMN_REFEICAO_VALIDA + " integer not null, " +
            COLUMN_DATA_REFEICAO + " datetime current_timestamp);";

    //Tabela Refeicoes_Alimentos
    public static final String TABLE_REFEICOES_ALIMENTOS = "refeicoes_alimentos";
    public static final String COLUMN_ID_REFEICAO_FK = "id_refeicao";
    public static final String COLUMN_ID_ALIMENTO_FK = "id_alimento";
    public static final String CREATE_TABLE_REFEICOES_ALIMENTOS = "create table "+ TABLE_REFEICOES_ALIMENTOS +
            "(" + COLUMN_ID_REFEICAO_FK + "integer not null, " +
            COLUMN_ID_ALIMENTO_FK + " integer not null, " +
            " primary key(id_refeicao, id_alimento), " +
            " foreign key(id_refeicao) references refeicoes(id_refeicao), " +
            " foreign key(id_alimento) references alimentos(id_alimento));";

    public CustomSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_GRUPOS);
        db.execSQL(CREATE_TABLE_SUB_GRUPOS);
        db.execSQL(CREATE_TABLE_ALIMENTOS);
        db.execSQL(CREATE_TABLE_REFEICOES);
        db.execSQL(CREATE_TABLE_REFEICOES_ALIMENTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRUPOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUB_GRUPOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALIMENTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REFEICOES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REFEICOES_ALIMENTOS);
        onCreate(db);
    }
}
