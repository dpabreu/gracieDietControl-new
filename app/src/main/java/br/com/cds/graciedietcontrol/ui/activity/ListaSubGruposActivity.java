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
import br.com.cds.graciedietcontrol.dao.SubGruposDao;
import br.com.cds.graciedietcontrol.model.SubGrupos;

public class ListaSubGruposActivity extends AppCompatActivity {
    private SubGruposDao dao = new SubGruposDao(this);
    private ArrayAdapter<SubGrupos> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sub_grupos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        abreConexao();
        listarSubGrupos();
        configuraNovoSubGrupo();
    }

    private void configuraNovoSubGrupo() {
        FloatingActionButton fabNovoSubGrupo = findViewById(R.id.fab_novo_sub_grupo);
        fabNovoSubGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaSubGruposActivity.this,
                        FormularioSubGruposActivity.class));
            }
        });
    }

    private void abreConexao(){
        dao.open();
    }

    @Override
    protected void onResume() {
        abreConexao();
        super.onResume();
        listarSubGrupos();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    private void listarSubGrupos(){
        final List<SubGrupos> subGrupos = dao.getAll();
        adapter = new ArrayAdapter<>( this,
                android.R.layout.simple_list_item_1, subGrupos);
        ListView subGruposList = findViewById(R.id.activity_lista_sub_grupos_listview);
        subGruposList.setAdapter(adapter);
        subGruposList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubGrupos subGrupoSelecionado = subGrupos.get(position);
                Intent vaiParaFormularioSubGrupo = new Intent(ListaSubGruposActivity.this,
                        FormularioSubGruposActivity.class);
                vaiParaFormularioSubGrupo.putExtra("subGrupo", subGrupoSelecionado);
                startActivity(vaiParaFormularioSubGrupo);
            }
        });

        registerForContextMenu(subGruposList);
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
        SubGrupos subGrupoEscolhido = adapter.getItem(menuInfo.position);
        dao.delete(subGrupoEscolhido);
        listarSubGrupos();

        return super.onContextItemSelected(item);
    }
}
