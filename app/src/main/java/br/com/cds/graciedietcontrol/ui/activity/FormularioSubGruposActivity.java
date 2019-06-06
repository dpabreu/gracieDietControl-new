package br.com.cds.graciedietcontrol.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import br.com.cds.graciedietcontrol.R;
import br.com.cds.graciedietcontrol.dao.GruposDao;
import br.com.cds.graciedietcontrol.dao.SubGruposDao;
import br.com.cds.graciedietcontrol.model.Grupos;

public class FormularioSubGruposActivity extends AppCompatActivity {
    private GruposDao gruposDao = new GruposDao(this);
    private SubGruposDao subGruposDao = new SubGruposDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_sub_grupos);
        gruposDao.open();
        subGruposDao.open();
        listarGrupos();
    }


    private void listarGrupos() {
        List<Grupos> grupos = gruposDao.getAll();
        ArrayAdapter<Grupos> adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, grupos);
        Spinner gruposList = findViewById(R.id.activity_formulario_sub_grupos_spinner_grupos);
        gruposList.setAdapter(adapter);
        gruposList.setSelection(-1);
    }
}
