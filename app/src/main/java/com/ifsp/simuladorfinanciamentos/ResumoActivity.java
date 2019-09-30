package com.ifsp.simuladorfinanciamentos;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ResumoActivity extends AppCompatActivity {

    protected static final int INICIO = 1;
    protected static final int VEICULO = 2;
    protected static final int IMOVEL = 3;
    protected static final int SOBRE = 4;
    protected static final int SAIR = 5;
    public String financiamento;
    public String categoria; //novo ou usado
    public Double valorTotal;
    public Double valorEntrada;
    public int qtdParcelas;
    public Double rendaMensal;
    public Double valTelaAnt;
    public Double valorParcelas;
    public Button simularNovamente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        simularNovamente = (Button) findViewById(R.id.btnSimularNovamente);
        simularNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RespostaCarroActivity.this,"Você será redirecionado para pagina inicial",Toast.LENGTH_SHORT).show();
                startActivity(new Intent((ResumoActivity.this), MainActivity.class));
            }
        });

        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();

        ((TextView) findViewById(R.id.txtNomUsu)).setText(getBundle.getString("nomeCliente") + " veja seu resumo.");
        financiamento = getBundle.getString("financiamento");
        categoria = getBundle.getString("categoria");
        valorTotal = getBundle.getDouble("valorTotal");
        valorEntrada = getBundle.getDouble("valorEntrada");
        qtdParcelas = getBundle.getInt("qtdParcelas");
        rendaMensal = getBundle.getDouble("rendaMensal");

        valTelaAnt = getBundle.getDouble("valorTotal");

        setImage();
        calcularJuros();
        apresentarValores();
    }

    public void setImage(){
        if(financiamento.equals("veiculo")){
            if(categoria.equals("novo")){
                ((ImageView) findViewById(R.id.imgResumo)).setImageResource(R.drawable.carro_novo);
            }else{
                ((ImageView) findViewById(R.id.imgResumo)).setImageResource(R.drawable.carro_velho);
            }
        }else{
            if(categoria.equals("novo")){
                ((ImageView) findViewById(R.id.imgResumo)).setImageResource(R.drawable.casa_nova);
            }else{
                ((ImageView) findViewById(R.id.imgResumo)).setImageResource(R.drawable.casa_velha);
            }
        }

    }

    public void calcularJuros(){
        Double val = valorTotal - valorEntrada;

        if(financiamento.equals("veiculo")){ //TRATATIVAS DE JUROS PARA VEÍCULO
            if(rendaMensal <= 3500){
                val = val + (val*0.06);
            }else if(3500 < rendaMensal && rendaMensal <= 5000){
                val = val + (val*0.05);
            }else{
                val = val + (val*0.04);
            }

            if(categoria.equals("novo")){
                val = val + ((val * 0.01) + (val * 0.04));
            }

        }else{ //TRATATIVAS DE JUROS PARA IMÓVEL
            if(rendaMensal <= 3500){
                val = val + (val*0.03);
            }else if(3500 < rendaMensal && rendaMensal <= 5000){
                val = val + (val*0.025);
            }else{
                val = val + (val*0.020);
            }

            if(categoria.equals("novo")){
                val = (val + (val * 0.05));
            }else{
                val = (val + (val * 0.02));
            }
        }

        valorParcelas = val/qtdParcelas;
        valorTotal = val + valorEntrada;
    }

    public void returnTelaInicial(){
        startActivity(new Intent((ResumoActivity.this), MainActivity.class));
    }

    public void apresentarValores(){
        TextView txtValor = (TextView) findViewById(R.id.txtValor);
        TextView txtEntrada = (TextView) findViewById(R.id.txtEntrada);
        TextView qtdParcelas = (TextView) findViewById(R.id.qtdParcelas);
        TextView txtRenda = (TextView) findViewById(R.id.txtRenda);
        TextView txtFinanciamentoDe = (TextView) findViewById(R.id.txtFinanciamentoDe);
        TextView txtMsgExtra = (TextView) findViewById(R.id.txtMsgExtra);

        TextView txtValorTotal = (TextView) findViewById(R.id.txtValorTotal);
        TextView txtValorParcelas = (TextView) findViewById(R.id.txtValorParcelas);

        txtValor.setText("R$ " + valTelaAnt.toString());
        txtEntrada.setText("R$ " + valorEntrada.toString());
        qtdParcelas.setText(String.valueOf(this.qtdParcelas));
        txtRenda.setText("R$ " + String.valueOf(rendaMensal));
        txtFinanciamentoDe.setText("Financiamento de " + financiamento);
        txtValorTotal.setText("R$ " + String.valueOf(valorTotal));
        txtValorParcelas.setText("R$ " + String.valueOf(valorParcelas));

        if(valorParcelas > (rendaMensal*0.3)){
            txtMsgExtra.setText("As parcelas superam 30% da renda, o financiamento não pode ser realizado.");
            txtMsgExtra.setTextColor(Color.RED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //criando opções para o menu
        menu.add(0, INICIO, 0, "Início");
        menu.add(0, VEICULO, 1, "Simular Veículo");
        menu.add(0, IMOVEL, 2, "Simular Imóvel");
        menu.add(0, SOBRE, 3, "Sobre");
        menu.add(0, SAIR, 4, "Sair");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int i = item.getItemId();

        switch (i){
            case INICIO:
                startActivity(new Intent((ResumoActivity.this), MainActivity.class));
                break;
            case VEICULO:
                startActivity(new Intent((ResumoActivity.this), VeiculoActivity.class));
                break;
            case IMOVEL:
                startActivity(new Intent((ResumoActivity.this), ResidenciaActivity.class));
                break;
            case SOBRE:
                startActivity(new Intent((ResumoActivity.this), SobreActivity.class));
                break;
            case SAIR:
                System.exit(1);
                break;
        }

        return false;
    }
}
