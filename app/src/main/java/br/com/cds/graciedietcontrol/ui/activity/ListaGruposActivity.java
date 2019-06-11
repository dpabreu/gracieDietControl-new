package br.com.cds.graciedietcontrol.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import br.com.cds.graciedietcontrol.dao.GruposDao;
import br.com.cds.graciedietcontrol.model.Grupos;

public class ListaGruposActivity extends AppCompatActivity {
    private GruposDao dao = new GruposDao(this);
    private ArrayAdapter<Grupos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_grupos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        abreConexao();
        configuraFabNovoGrupo();
    }

    @Override
    protected void onResume() {
        abreConexao();
        super.onResume();
        listarGrupos();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    private void configuraFabNovoGrupo() {
        FloatingActionButton novoGrupo = findViewById(R.id.fab_novo_grupo);

        novoGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaGruposActivity.this,
                        FormularioGruposActivity.class));
            }
        });
    }

    private void abreConexao() {
        dao.open();
    }

    private void listarGrupos() {
        ListView gruposList = findViewById(R.id.activity_lista_grupos_listview);
        final List<Grupos> grupos = dao.getAll();
        adapter = new ArrayAdapter<>( this, android.R.layout.simple_list_item_1, grupos);
        gruposList.setAdapter(adapter);

        gruposList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Grupos grupoEscolhido = grupos.get(position);
                Intent vaiParaFormularioGrupo = new Intent(ListaGruposActivity.this,
                        FormularioGruposActivity.class);
                vaiParaFormularioGrupo.putExtra("grupo", grupoEscolhido);
                startActivity(vaiParaFormularioGrupo);
            }
        });

        registerForContextMenu(gruposList);
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
        Grupos grupoEscolhido = adapter.getItem(menuInfo.position);
        dao.delete(grupoEscolhido);
        listarGrupos();
        return super.onContextItemSelected(item);
    }








}
