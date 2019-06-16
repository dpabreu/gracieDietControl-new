package br.com.cds.graciedietcontrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.cds.graciedietcontrol.R;
import br.com.cds.graciedietcontrol.dao.GruposDao;
import br.com.cds.graciedietcontrol.model.Grupos;

public class FormularioGruposActivity extends AppCompatActivity {

    private GruposDao dao = new GruposDao(this);
    private EditText campoNome;
    private Grupos grupo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_grupos);
        setTitle("Novo Grupo");
        dao.open();
        inicializaCamposGrupos();
        configuraBotaoSalvar();

        Intent dadosGrupo = getIntent();
        grupo = (Grupos) dadosGrupo.getSerializableExtra("grupo");
        if(grupo != null){
            campoNome.setText(grupo.getNome());
        }
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_grupo_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeGrupo = campoNome.getEditableText().toString();
                if(grupo==null) {
                    dao.create(nomeGrupo);
                } else {
                    dao.update(nomeGrupo, grupo.getIdGrupo());
                }
                finish();
            }
        });
    }

    private void inicializaCamposGrupos() {
        campoNome = findViewById(R.id.activity_formulario_grupo_nome);
    }

    @Override
    protected void onResume() {
        dao.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dao.close();
    }
}
