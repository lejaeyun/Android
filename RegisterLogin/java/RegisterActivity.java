package kr.ac.pusan.cs.android.dietapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText register_id, register_pw, pw_check, name, age, gender, height, weight;
    private Button btn_idcheck, btn_register;
    private AlertDialog dialog;
    private boolean validate=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_id=findViewById(R.id.register_id);
        register_pw=findViewById(R.id.register_pw);
        pw_check=findViewById(R.id.pw_check);
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);

        btn_idcheck=findViewById(R.id.btn_idcheck);
        btn_register=findViewById(R.id.btn_register);

        btn_idcheck.setOnClickListener(new View.OnClickListener() { // id 중복체크
            @Override
            public void onClick(View v) {
                String userID=register_id.getText().toString();

                if(validate) return;
                if(userID.equals("")) { // id가 빈 칸일 경우
                    AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener=new Response.Listener<String>() { // volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                register_id.setEnabled(false);
                                validate=true;
                                btn_idcheck.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest=new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() { // 회원가입
            @Override
            public void onClick(View v) {
                // editText에 입력된 값을 가져온다
                String userID=register_id.getText().toString();
                final String userPassword=register_pw.getText().toString();
                String userName=name.getText().toString();
                int userAge=Integer.parseInt(age.getText().toString());
                String userGender=gender.getText().toString();
                int userHeight=Integer.parseInt(height.getText().toString());
                int userWeight=Integer.parseInt(weight.getText().toString());
                final String checkPassword=pw_check.getText().toString();

                Response.Listener<String> responseListener=new Response.Listener<String>() { // volley
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success=jsonObject.getBoolean("success");
                            if(userPassword.equals(checkPassword)) {
                                if (success) { // 회원가입 성공
                                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                            else{ // 회원가입 실패
                                Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest=new RegisterRequest(userID, userPassword, userName,
                        userAge, userGender, userHeight, userWeight, responseListener);
                RequestQueue queue=Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
