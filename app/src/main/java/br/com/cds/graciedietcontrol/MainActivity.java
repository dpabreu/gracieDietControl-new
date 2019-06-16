package br.com.cds.graciedietcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.cds.graciedietcontrol.dao.AlimentosDao;
import br.com.cds.graciedietcontrol.dao.GruposDao;
import br.com.cds.graciedietcontrol.dao.MotivosDao;
import br.com.cds.graciedietcontrol.dao.RefeicoesDao;
import br.com.cds.graciedietcontrol.dao.SubGruposDao;
import br.com.cds.graciedietcontrol.model.Alimentos;
import br.com.cds.graciedietcontrol.model.Grupos;
import br.com.cds.graciedietcontrol.model.Motivos;
import br.com.cds.graciedietcontrol.model.Refeicoes;
import br.com.cds.graciedietcontrol.model.SubGrupos;
import br.com.cds.graciedietcontrol.model.TipoRefeicao;
import br.com.cds.graciedietcontrol.ui.activity.ListaAlimentosActivity;
import br.com.cds.graciedietcontrol.ui.activity.ListaGruposActivity;
import br.com.cds.graciedietcontrol.ui.activity.ListaSubGruposActivity;
import br.com.cds.graciedietcontrol.ui.activity.ResultadoRefeicaoActivity;

public class MainActivity extends AppCompatActivity {
    private AlimentosDao alimentosDao = new AlimentosDao(this);
    private List<Alimentos> alimentosListView;
    private ArrayAdapter<Alimentos> alimentosListViewAdapter;
    private ListView listView;
    private RefeicoesDao refeicoesDao = new RefeicoesDao(this);
    private GruposDao gruposDao = new GruposDao(this);
    private SubGruposDao subGruposDao = new SubGruposDao(this);
    private MotivosDao motivosDao = new MotivosDao(this);
    private Motivos motivo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        abreConexao();
        configuraTipoRefeicao();
        configuraAlimentos();
        configuraBotaoAddAlimento();
        configuraBotaoProcessar();
    }

    private void configuraBotaoProcessar() {
        FloatingActionButton fab = findViewById(R.id.fab_activity_main_botao_processar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(alimentosListView.size() == 0){
                   Toast.makeText(MainActivity.this, "Adicione ao menos um alimento à lista",
                           Toast.LENGTH_SHORT).show();
               } else{
                   Spinner tipoRefeicaoSpinner = findViewById(R.id.activity_main_spinner_tipo_refeicao);
                   Integer refeicaoValida = validaRefeicao(alimentosListView);
                   Refeicoes refeicaoCriada =  refeicoesDao.create(tipoRefeicaoSpinner.getSelectedItem().toString(),
                           refeicaoValida,alimentosListView, motivo);
                   Intent vaiParaResultadoRefeicao = new Intent(MainActivity.this,
                           ResultadoRefeicaoActivity.class);
                   vaiParaResultadoRefeicao.putExtra("refeicao", refeicaoCriada);

                   if(motivo != null){
                       vaiParaResultadoRefeicao.putExtra("motivo", motivo);
                   }
                   startActivity(vaiParaResultadoRefeicao);
               }
            }
        });
    }

    private void abreConexao() {
        alimentosDao.open();
        refeicoesDao.open();
        gruposDao.open();
        subGruposDao.open();
        motivosDao.open();
    }

    private Integer validaRefeicao(List<Alimentos> alimentos){
        Grupos grupoAnterior = null;
        for (Alimentos alimentoCorrente : alimentos) {
            SubGrupos subGrupoCorrente = subGruposDao.getSubGruposById(alimentoCorrente.getIdSubGrupo());
            Grupos grupoCorrente = gruposDao.getGrupoById(subGrupoCorrente.getIdGrupo());

            if(grupoAnterior != null){
               if(grupoAnterior.getNome().equals("Grupo A") && grupoCorrente.getNome().equals("Grupo C") ||
                  grupoAnterior.getNome().equals("Grupo C") && grupoCorrente.getNome().equals("Grupo A")){
                    motivo = motivosDao.getMotivoByCodMotivo("001");
                    return 0;
               }

               if(grupoAnterior.getNome().equals("Grupo B") && (grupoCorrente.getNome().equals("Grupo B"))){
                   motivo = motivosDao.getMotivoByCodMotivo("002");
                   return 0;
               }

               if(grupoAnterior.getNome().equals("Grupo C") && grupoCorrente.getNome().equals("Grupo B") ||
                  grupoAnterior.getNome().equals("Grupo B") && grupoCorrente.getNome().equals("Grupo C")) {
                   motivo = motivosDao.getMotivoByCodMotivo("003");
                   return 0;
               }

               if(grupoCorrente.getNome().equals("Grupo D") && (alimentos.size() > 1)){
                   motivo = motivosDao.getMotivoByCodMotivo("004");
                   return 0;
               }

               if(grupoAnterior.getNome().equals("Grupo E") &&
                       (!subGrupoCorrente.getNome().equals("Frutas e Alimentos Doces") &&
                        !subGrupoCorrente.getNome().equals("Queijos Frescos e Cremosos"))){

                   motivo = motivosDao.getMotivoByCodMotivo("005");
                   return 0;

               }
               if(grupoAnterior.getNome().equals("Grupo F") &&
                       (!grupoCorrente.getNome().equals("Grupo B") &&
                        !grupoCorrente.getNome().equals("Grupo E") &&
                        !subGrupoCorrente.getNome().equals("Queijos Frescos e Cremosos") &&
                        !alimentoCorrente.getNome().equals("Manteiga"))){

                   motivo = motivosDao.getMotivoByCodMotivo("006");
                   return 0;

                }
            }
            grupoAnterior = grupoCorrente;
        }

        return 1;
    }


    private void configuraBotaoAddAlimento() {
        alimentosListView = new ArrayList<>();
        alimentosListViewAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, alimentosListView);
        listView = findViewById(R.id.activity_main_alimentos_listview);

        Button botaoAdd = findViewById(R.id.activity_main_alimento_adicionar);
        botaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinnerAlimentos = findViewById(R.id.activity_main_spinner_alimentos);
                Alimentos alimento = (Alimentos) spinnerAlimentos.getSelectedItem();
                if(!alimentosListView.contains(alimento)) {
                    alimentosListView.add(alimento);
                    listView.setAdapter(alimentosListViewAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Alimento já adicionado.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerForContextMenu(listView);
    }

    private void configuraAlimentos() {
        List<Alimentos> alimentosCombo = alimentosDao.getAll();
        ArrayAdapter<Alimentos> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, alimentosCombo);
        Spinner spinnerAlimentos = findViewById(R.id.activity_main_spinner_alimentos);
        spinnerAlimentos.setAdapter(adapter);
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
        Spinner tipoRefeicaoSpinner = findViewById(R.id.activity_main_spinner_tipo_refeicao);
        tipoRefeicaoSpinner.setAdapter(adapter);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("Remover");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Alimentos alimentoEscolhido = alimentosListViewAdapter.getItem(menuInfo.position);

        alimentosListView.remove(alimentoEscolhido);
        listView.setAdapter(alimentosListViewAdapter);
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        abreConexao();
        super.onResume();
        alimentosListView.clear();
        listView.setAdapter(new ArrayAdapter<Alimentos>(this,
                android.R.layout.simple_expandable_list_item_1));
        Spinner spinnerTipoRefeicao = findViewById(R.id.activity_main_spinner_tipo_refeicao);
        spinnerTipoRefeicao.setSelection(0);
        Spinner spinnerAlimento = findViewById(R.id.activity_main_spinner_alimentos);
        spinnerAlimento.setSelection(0);

    }

    @Override
    protected void onPause() {
        fechaConexao();
        super.onPause();
    }

    private void fechaConexao() {
        alimentosDao.close();
        refeicoesDao.close();
        gruposDao.close();
        subGruposDao.close();
        motivosDao.close();
    }
}
