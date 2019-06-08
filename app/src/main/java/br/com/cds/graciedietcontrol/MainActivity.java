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

import br.com.cds.graciedietcontrol.ui.activity.ListaAlimentosActivity;
import br.com.cds.graciedietcontrol.ui.activity.ListaGruposActivity;
import br.com.cds.graciedietcontrol.ui.activity.ListaSubGruposActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
