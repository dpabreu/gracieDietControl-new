package br.com.cds.graciedietcontrol.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.cds.graciedietcontrol.R;
import br.com.cds.graciedietcontrol.model.Motivos;
import br.com.cds.graciedietcontrol.model.Refeicoes;

public class ResultadoRefeicaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_refeicao);
        setTitle("Resultado Refeição");

        TextView textViewResultado = findViewById(R.id.textViewResultado);
        TextView textViewMotivo = findViewById(R.id.textViewMotivo);

        Intent resultado = getIntent();
        Refeicoes refeicaoCriada = (Refeicoes) resultado.getSerializableExtra("refeicao");
        if(refeicaoCriada.getRefeicaoValida()==1){
            textViewResultado.setText("PARABÉNS A SUA REFEIÇÃO ESTÁ DENTRO DAS COMBINAÇÕES DA DIETA GRACIE");
        } else {
            Motivos motivo = (Motivos) resultado.getSerializableExtra("motivo");
            if(motivo != null){
                textViewResultado.setText("SUA REFEIÇÃO NÃO ESTÁ DENTRO DAS COMBINAÇÕES");
                textViewMotivo.setText(motivo.getMotivo().toUpperCase());
            }
        }
    }
}
