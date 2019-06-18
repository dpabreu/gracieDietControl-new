package br.com.cds.graciedietcontrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.com.cds.graciedietcontrol.R;
import br.com.cds.graciedietcontrol.dao.GruposDao;
import br.com.cds.graciedietcontrol.dao.SubGruposDao;
import br.com.cds.graciedietcontrol.model.Grupos;
import br.com.cds.graciedietcontrol.model.SubGrupos;

public class FormularioSubGruposActivity extends AppCompatActivity {
    private GruposDao gruposDao = new GruposDao(this);
    private SubGruposDao subGruposDao = new SubGruposDao(this);
    private EditText campoNome;
    private Spinner campoGrupo;
    private SubGrupos subGrupo = null;
    private List<Grupos> grupos;
    private ArrayAdapter<Grupos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_sub_grupos);
        setTitle("Novo Sub Grupo");
        abreConexao();
        listarGrupos();
        inicializarCampos();
        configuraBotaoSalvar();

        Intent dadosSubGrupo = getIntent();
        subGrupo = (SubGrupos) dadosSubGrupo.getSerializableExtra("subGrupo");
        if(subGrupo != null){
            campoNome.setText(subGrupo.getNome());

            int idx = -1;

            for(Grupos grupo : grupos){
                if(grupo.getIdGrupo()==subGrupo.getIdGrupo()){
                    idx = adapter.getPosition(grupo);
                }
            }

            campoGrupo.setSelection(idx);
        }
    }

    private void abreConexao() {
        gruposDao.open();
        subGruposDao.open();
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_sub_grupo_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeSubGrupo = campoNome.getEditableText().toString();
                if(!nomeSubGrupo.equals("")) {
                    Grupos grupo = (Grupos) campoGrupo.getSelectedItem();
                    long idGrupo = grupo.getIdGrupo();
                    if (subGrupo == null) {
                        subGruposDao.create(nomeSubGrupo,
                                idGrupo);
                    } else {
                        subGruposDao.update(nomeSubGrupo,
                                idGrupo,
                                subGrupo.getIdSubGrupo());
                    }
                    finish();
                } else {
                    Toast.makeText(FormularioSubGruposActivity.this,
                            "Informe o nome do sub-grupo.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializarCampos() {
        campoNome = findViewById(R.id.activity_formulario_sub_grupo_nome);
        campoGrupo = findViewById(R.id.activity_formulario_sub_grupos_spinner_grupos);
    }


    private void listarGrupos() {
        grupos = gruposDao.getAll();
        adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, grupos);
        Spinner gruposList = findViewById(R.id.activity_formulario_sub_grupos_spinner_grupos);
        gruposList.setAdapter(adapter);
        gruposList.setSelection(-1);
    }

    @Override
    protected void onResume() {
        abreConexao();
        super.onResume();
    }

    @Override
    protected void onPause() {
        gruposDao.close();
        subGruposDao.close();
        super.onPause();
    }
}
