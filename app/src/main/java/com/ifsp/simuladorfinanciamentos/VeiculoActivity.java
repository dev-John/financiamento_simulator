package com.ifsp.simuladorfinanciamentos;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class VeiculoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculo);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgVeiculoContainer);
        final RadioButton radio = (RadioButton) findViewById(R.id.rbVeiculoNovo);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if(rb.equals(radio)){
                    ((ImageView) findViewById(R.id.imgCarro)).setImageResource(R.drawable.carro_novo);
                }else{
                    ((ImageView) findViewById(R.id.imgCarro)).setImageResource(R.drawable.carro_velho);
                }
            }
        });

        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();

        ((ImageView) findViewById(R.id.imgCarro)).setImageResource(R.drawable.carro_novo);
        ((TextView) findViewById(R.id.txtNomeUsuario)).setText("Olá " + getBundle.getString("nomeCliente"));
    }

    public void calcular(View view){
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rgVeiculoContainer) ;
        RadioButton rbNovo = (RadioButton) findViewById(R.id.rbVeiculoNovo);
        TextView nomeCliente = (TextView) findViewById(R.id.txtNomeUsuario);
        EditText edtValorVeiculo = (EditText) findViewById(R.id.edtValorVeiculo);
        EditText edtValorEntrada = (EditText) findViewById(R.id.edtValorEntrada);
        EditText edtQtdParcelas = (EditText) findViewById(R.id.edtQtdParcelas);
        EditText edtRendaLiquida = (EditText) findViewById(R.id.edtRendaLiquida);

        if(rGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Selecione uma opção: novo ou usado", Toast.LENGTH_LONG).show();
        }else{
            if(edtValorVeiculo.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira o valor do veículo", Toast.LENGTH_LONG).show();
            }
            else if(edtValorEntrada.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira o valor da entrada", Toast.LENGTH_LONG).show();
            }
            else if(edtQtdParcelas.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira a quantidade de parcelas", Toast.LENGTH_LONG).show();
            }
            else if(edtRendaLiquida.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira a renda líquida mensal", Toast.LENGTH_LONG).show();
            }
            else{
                int selectedId = rGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);

                Bundle bundle = new Bundle();
                bundle.putString("financiamento", "veiculo");
                bundle.putString("nomeCliente", nomeCliente.getText().toString());

                if(selectedRadioButton.equals(rbNovo)){
                    bundle.putString("categoria", "novo");
                }else{
                    bundle.putString("categoria", "usado");
                }

                bundle.putDouble("valorTotal", Double.parseDouble(edtValorVeiculo.getText().toString().trim()));
                bundle.putDouble("valorEntrada", Double.parseDouble(edtValorEntrada.getText().toString().trim()));
                bundle.putInt("qtdParcelas", Integer.parseInt(edtQtdParcelas.getText().toString().trim()));
                bundle.putDouble("rendaMensal", Double.parseDouble(edtRendaLiquida.getText().toString().trim()));

                Intent intent = new Intent(VeiculoActivity.this, ResumoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

    }
}
