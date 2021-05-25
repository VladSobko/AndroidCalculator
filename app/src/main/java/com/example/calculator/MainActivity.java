package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView resultField; // текстове поле для виводу інф
    EditText numberField;   // поле для ввода числа
    TextView operationField;    // текстовое поле для виводу знака
    Double operand = null;  // операція
    String lastOperation = "="; // остання операція
    String memory = ""; //память


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получаем все поля по id из activity_main.xml
        resultField =(TextView) findViewById(R.id.resultField);
        numberField = (EditText) findViewById(R.id.numberField);
        operationField = (TextView) findViewById(R.id.operationField);
    }

    // збереження стану
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onMCClick(View view){
        memory = "";
        Toast toast = Toast.makeText(getApplicationContext(),
                "Пам'ять очищено",
                Toast.LENGTH_SHORT);
        toast.show();
    }
    public void onMSClick(View view){
        memory = resultField.getText().toString();
        if (memory !=""){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "\"" +memory + "\" збережено у пам'ять.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onMRClick(View view){
        if (memory !="") {
            numberField.append(memory);

            Toast toast = Toast.makeText(getApplicationContext(),
                    "\"" +memory + "\" виведено із пам`яті.",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "У пам`яті нічого не записано!",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onCClick(View view){
        numberField.setText("");
    }
    // обробка кліку на число
    public void onNumberClick(View view){

        Button button = (Button)view;
        numberField.append(button.getText());

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }
    // обробка кліку на операцію
    public void onOperationClick(View view){

        Button button = (Button)view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        // если введенно что-нибудь
        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {
        // якщо це перша операція
        if(operand ==null){
            operand = number;
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
            }
            switch(lastOperation){
                case "=":
                    operand =number;
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                    }
                    else{
                        operand /=number;
                    }
                    break;
                case "*":
                    operand *=number;
                    break;
                case "+":
                    operand +=number;
                    break;
                case "-":
                    operand -=number;
                    break;
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }
}