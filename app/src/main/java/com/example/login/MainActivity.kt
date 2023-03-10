package com.example.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.login.preference.App


class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_login : Button = findViewById(R.id.btn_login)
        var edit_id : EditText = findViewById(R.id.edit_id)
        var edit_pw : EditText = findViewById(R.id.edit_pw)
        var btn_register : Button = findViewById(R.id.btn_register)

        // 아이디 저장
        edit_id.setText(App.prefs.myEditText)

        // 로그인 버튼
        btn_login.setOnClickListener {

            //editText로부터 입력된 값을 받아온다
            var id = edit_id.text.toString()
            var pw = edit_pw.text.toString()

            // preference를 이용한 자동? 로그인

            App.prefs.myEditText = edit_id.text.toString()

            // 쉐어드로부터 저장된 id, pw 가져오기
            val sharedPreference = getSharedPreferences("file name", Context.MODE_PRIVATE)
            val savedId = sharedPreference.getString("id", "")
            val savedPw = sharedPreference.getString("pw", "")

            // 유저가 입력한 id, pw값과 쉐어드로 불러온 id, pw값 비교
            if(id == savedId && pw == savedPw){
                // 로그인 성공 다이얼로그 보여주기
                dialog("success")
            }
            else{
                // 로그인 실패 다이얼로그 보여주기
                dialog("fail")
            }
        }

        // 회원가입 버튼
        btn_register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }





    }

    // 로그인 성공/실패 시 다이얼로그를 띄워주는 메소드
    fun dialog(type: String){
        var dialog = AlertDialog.Builder(this)

        if(type.equals("success")){
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        }
        else if(type.equals("fail")){
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        var dialog_listener = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val alertDialog = (dialog as AlertDialog)
                when(which){
                    DialogInterface.BUTTON_POSITIVE -> {
                        when(type) {
                            "success" -> {
                                startActivity(Intent(this@MainActivity,Home::class.java))
                            }
                            else -> { }
                        }
                        Log.d(TAG, "")
                    }
                }
            }
        }

        dialog.setPositiveButton("확인",dialog_listener)
        dialog.show()
    }




}