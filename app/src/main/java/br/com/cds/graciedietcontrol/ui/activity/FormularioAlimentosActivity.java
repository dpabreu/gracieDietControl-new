package br.com.cds.graciedietcontrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.com.cds.graciedietcontrol.R;
import br.com.cds.graciedietcontrol.dao.AlimentosDao;
import br.com.cds.graciedietcontrol.dao.GruposDao;
import br.com.cds.graciedietcontrol.dao.SubGruposDao;
import br.com.cds.graciedietcontrol.model.Alimentos;
import br.com.cds.graciedietcontrol.model.Grupos;
import br.com.cds.graciedietcontrol.model.SubGrupos;

public class FormularioAlimentosActivity extends AppCompatActivity {

    private AlimentosDao alimentosDao = new AlimentosDao(this);
    private SubGruposDao subGruposDao = new SubGruposDao(this);
    private GruposDao gruposDao = new GruposDao(this);
    private TextView campoNome;
    private Spinner campoSubGrupo;
    private Spinner campoGrupo;
    private Alimentos alimento = null;
    private List<SubGrupos> subGrupos;
    private ArrayAdapter<SubGrupos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_alimentos);
        setTitle("Novo Alimento");
        abreConexao();
        listarSubGrupos();
        inicializaCampos();
        configuraBotaoSalvar();

        Intent dadosAlimento = getIntent();
        alimento = (Alimentos) dadosAlimento.getSerializableExtra("alimento");
        if(alimento != null){
            campoNome.setText(alimento.getNome());

            int idx = -1;
            for(SubGrupos subGrupo : subGrupos){
                if(subGrupo.getIdSubGrupo()==alimento.getIdSubGrupo()){
                    idx = adapter.getPosition(subGrupo);
                }
            }

            campoSubGrupo.setSelection(idx);
        }
    }

    private void abreConexao() {
        alimentosDao.open();
        subGruposDao.open();
        gruposDao.open();
    }

    private void listarSubGrupos() {
        subGrupos = subGruposDao.getAll();
        adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, subGrupos);
        Spinner subGruposList = findViewById(R.id.activity_formulario_alimento_spinner_subGrupos);
        subGruposList.setAdapter(adapter);
    }

    private void listarGruposById(long id){
        List<Grupos> grupos = gruposDao.getAll();
        ArrayAdapter<Grupos> adapterGrupo = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, grupos);
        campoGrupo.setAdapter(adapterGrupo);

        int idx = -1;
        for(Grupos grupo : grupos){
            if(grupo.getIdGrupo() == id){
                idx = adapterGrupo.getPosition(grupo);
            }
        }
        campoGrupo.setSelection(idx);
    }

//    private void listarSubGruposById(long id){
//        List<SubGrupos> subGrupos = subGruposDao.getById(id);
//        ArrayAdapter<SubGrupos> adapter = new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1, subGrupos);
//        campoSubGrupo.setAdapter(adapter);
//
////        listarGruposById(subGrupos.get(0).getIdGrupo());
//    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_alimento_nome);
        campoSubGrupo = findViewById(R.id.activity_formulario_alimento_spinner_subGrupos);
        campoGrupo = findViewById(R.id.activity_formulario_alimento_spinner_grupos);

        campoSubGrupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SubGrupos subGrupos = (SubGrupos) campoSubGrupo.getSelectedItem();
                listarGruposById(subGrupos.getIdGrupo());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        campoGrupo.setEnabled(false);
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_alimento_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAlimento = campoNome.getEditableText().toString();
                SubGrupos subGrupo = (SubGrupos) campoSubGrupo.getSelectedItem();
                long idSubGrupo = subGrupo.getIdSubGrupo();
                if(alimento!=null) {
                    alimentosDao.update(nomeAlimento,
                            idSubGrupo,
                            alimento.getIdAlimento());
                } else {
                    alimentosDao.create(nomeAlimento,
                            idSubGrupo);
                }
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        abreConexao();
        super.onResume();
    }

    @Override
    protected void onPause() {
        alimentosDao.close();
        subGruposDao.close();
        gruposDao.close();
        super.onPause();
    }
}
