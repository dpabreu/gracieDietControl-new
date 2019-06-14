package br.com.cds.graciedietcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.dao.AlimentosDao;
import br.com.cds.graciedietcontrol.model.Alimentos;
import br.com.cds.graciedietcontrol.model.TipoRefeicao;
import br.com.cds.graciedietcontrol.ui.activity.ListaAlimentosActivity;
import br.com.cds.graciedietcontrol.ui.activity.ListaGruposActivity;
import br.com.cds.graciedietcontrol.ui.activity.ListaSubGruposActivity;

public class MainActivity extends AppCompatActivity {
    private AlimentosDao alimentosDao = new AlimentosDao(this);
    private List<Alimentos> alimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alimentosDao.open();
        configuraTipoRefeicao();
        configuraAlimentos();

        alimentos = new ArrayList<>();

        Button botaoAdd = findViewById(R.id.activity_main_alimento_adicionar);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinnerAlimentos = findViewById(R.id.activity_main_spinner_alimentos);
                Alimentos alimento = (Alimentos) spinnerAlimentos.getSelectedItem();
                alimentos.add(alimento);
                ArrayAdapter adapaterAlimento = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, alimentos);
                ListView alimentosList = findViewById(R.id.activity_main_alimentos_listview);
                alimentosList.setAdapter(adapterAlimento);

            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void configuraAlimentos() {
        List<Alimentos> alimentos = alimentosDao.getAll();
        ArrayAdapter<Alimentos> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, alimentos);
        Spinner listViewAlimentos = findViewById(R.id.activity_main_spinner_alimentos);
        listViewAlimentos.setAdapter(adapter);
    }

    private void configuraTipoRefeicao() {
        List<String> tipoRefeicaoList = new ArrayList<>();
        for(TipoRefeicao tipoRefeicao: TipoRefeicao.values()){
            switch (tipoRefeicao){
                case CAFE:
                    tipoRefeicaoList.add("Café da Manhã");
                    break;
                case ALMOCO:
                    tipoRefeicaoList.add("Almoço");
                    break;
                case JANTA:
                    tipoRefeicaoList.add("Jantar");
                    break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, tipoRefeicaoList);
        Spinner listViewTipoRefeicao = findViewById(R.id.activity_main_spinner_tipo_refeicao);
        listViewTipoRefeicao.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_grupos) {
            startActivity(new Intent(MainActivity.this, ListaGruposActivity.class));
            return true;
        }

        if (id == R.id.action_sub_grupos) {
            startActivity(new Intent(MainActivity.this, ListaSubGruposActivity.class));
            return true;
        }

        if (id == R.id.action_alimentos) {
            startActivity(new Intent(MainActivity.this, ListaAlimentosActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
