package br.com.cds.graciedietcontrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.cds.graciedietcontrol.R;
import br.com.cds.graciedietcontrol.dao.AlimentosDao;
import br.com.cds.graciedietcontrol.model.Alimentos;

public class ListaAlimentosActivity extends AppCompatActivity {

    private AlimentosDao alimentosDao = new AlimentosDao(this);
    private ArrayAdapter<Alimentos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alimentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        abreConexao();
        listarAlimentos();
        configuraNovoAlimento();
    }

    private void configuraNovoAlimento() {
        FloatingActionButton novoAlimento = findViewById(R.id.fab_novo_alimento);
        novoAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaAlimentosActivity.this,
                            FormularioAlimentosActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        abreConexao();
        super.onResume();
        listarAlimentos();
    }

    @Override
    protected void onPause() {
        alimentosDao.close();
        super.onPause();
    }

    private void listarAlimentos() {
        final List<Alimentos> alimentos = alimentosDao.getAll();
        adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, alimentos);
        ListView alimentosList = findViewById(R.id.activity_lista_alimentos_listview);
        alimentosList.setAdapter(adapter);
        alimentosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Alimentos alimentoSelicionado = alimentos.get(position);
                Intent vaiParaFormularioAlimentos = new Intent(ListaAlimentosActivity.this,
                        FormularioAlimentosActivity.class);
                vaiParaFormularioAlimentos.putExtra("alimento", alimentoSelicionado);
                startActivity(vaiParaFormularioAlimentos);
            }
        });

        registerForContextMenu(alimentosList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Remover");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Alimentos alimentoEscolhido = adapter.getItem(menuInfo.position);
        alimentosDao.delete(alimentoEscolhido);
        listarAlimentos();
        return super.onContextItemSelected(item);
    }

    private void abreConexao() {
        alimentosDao.open();
    }

}
