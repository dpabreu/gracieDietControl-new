package br.com.cds.graciedietcontrol.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText campoNome;
    private Spinner campoGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_sub_grupos);
        setTitle("Novo Sub Grupo");
        gruposDao.open();
        subGruposDao.open();
        listarGrupos();
        inicializarCampos();
        configuraBotaoSalvar();
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_sub_grupo_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeSubGrupo = campoNome.getEditableText().toString();
                Grupos grupo = (Grupos) campoGrupo.getSelectedItem();
                long idGrupo = grupo.getIdGrupo();
                subGruposDao.create(nomeSubGrupo,
                                    idGrupo);
                finish();
            }
        });
    }

    private void inicializarCampos() {
        campoNome = findViewById(R.id.activity_formulario_sub_grupo_nome);
        campoGrupo = findViewById(R.id.activity_formulario_sub_grupos_spinner_grupos);
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
