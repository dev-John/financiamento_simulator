package com.ifsp.simuladorfinanciamentos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ResidenciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residencia);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgResidenciaContainer);
        final RadioButton radio = (RadioButton) findViewById(R.id.rbNovo);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                if(rb.equals(radio)){
                    ((ImageView) findViewById(R.id.imgCasa)).setImageResource(R.drawable.casa_nova);
                }else{
                    ((ImageView) findViewById(R.id.imgCasa)).setImageResource(R.drawable.casa_velha);
                }
            }
        });

        Bundle getBundle = null;
        getBundle = this.getIntent().getExtras();

        ((ImageView) findViewById(R.id.imgCasa)).setImageResource(R.drawable.casa_nova);
        ((TextView) findViewById(R.id.txtNomeUsuario)).setText("Olá " + getBundle.getString("nomeCliente"));
    }

    public void calcular(View view){
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rgResidenciaContainer) ;
        RadioButton rbNovo = (RadioButton) findViewById(R.id.rbNovo);
        TextView nomeCliente = (TextView) findViewById(R.id.txtNomeUsuario);
        EditText valorImovel = (EditText) findViewById(R.id.edtValorImovel);
        EditText valorEntrada = (EditText) findViewById(R.id.edtEntradaImovel);
        EditText qtdParcelas = (EditText) findViewById(R.id.edtQtdParcelasImovel);
        EditText rendaMensal = (EditText) findViewById(R.id.edtRendaMensalImovel);

        if(rGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Selecione uma opção: novo ou usado", Toast.LENGTH_LONG).show();
        }else{
            if(valorImovel.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira o valor do imóvel", Toast.LENGTH_LONG).show();
            }
            else if(valorEntrada.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira o valor da entrada", Toast.LENGTH_LONG).show();
            }
            else if(qtdParcelas.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira a quantidade de parcelas", Toast.LENGTH_LONG).show();
            }
            else if(rendaMensal.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor insira a renda líquida mensal", Toast.LENGTH_LONG).show();
            }
            else{
                int selectedId = rGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);

                Bundle bundle = new Bundle();
                bundle.putString("financiamento", "imóvel");
                bundle.putString("nomeCliente", nomeCliente.getText().toString());

                if(selectedRadioButton.equals(rbNovo)){
                    bundle.putString("categoria", "novo");
                }else{
                    bundle.putString("categoria", "usado");
                }

                bundle.putDouble("valorTotal", Double.parseDouble(valorImovel.getText().toString().trim()));
                bundle.putDouble("valorEntrada", Double.parseDouble(valorEntrada.getText().toString().trim()));
                bundle.putInt("qtdParcelas", Integer.parseInt(qtdParcelas.getText().toString().trim()));
                bundle.putDouble("rendaMensal", Double.parseDouble(rendaMensal.getText().toString().trim()));

                Intent intent = new Intent(ResidenciaActivity.this, ResumoActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }

    }
}
