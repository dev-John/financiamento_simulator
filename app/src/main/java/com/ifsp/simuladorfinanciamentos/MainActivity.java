package com.ifsp.simuladorfinanciamentos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void continuar(View view){
        RadioGroup rGroup = (RadioGroup) findViewById(R.id.rgImovelVeiculo) ;
        RadioButton rbImovel = (RadioButton) findViewById(R.id.rbImovel);
        EditText edtNomeCliente = (EditText) findViewById(R.id.edtNomeCliente);
        Bundle bundle = new Bundle();
        bundle.putString("nomeCliente", edtNomeCliente.getText().toString());

        if(rGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getApplicationContext(), "Selecione uma opção de financiamento", Toast.LENGTH_LONG).show();
        }else{
            if(edtNomeCliente.getText().toString().trim().isEmpty()){
                Toast.makeText(getApplicationContext(), "Por favor, digite seu nome", Toast.LENGTH_LONG).show();
            }else{
                int selectedId = rGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
                //Toast.makeText(getApplicationContext(), selectedRadioButton.getText().toString() + " selecionado", Toast.LENGTH_LONG).show();
                if(selectedRadioButton.equals(rbImovel)){
                    Intent intent = new Intent(MainActivity.this, ResidenciaActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "É IMOVEL", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, VeiculoActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }



    }
}
