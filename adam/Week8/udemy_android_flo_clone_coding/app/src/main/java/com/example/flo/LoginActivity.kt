package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    private fun login(){
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        //아이디 정보 가져옴
        val pwd : String = binding.loginPasswordEt.text.toString()
        //패스워드 정보 가져옴

        val songDB = SongDatabase.getInstance(this)!!
        //SongDatabase와 연결
        val user = songDB.userDao().getUser(email, pwd)
        //해당 유저가 존재하는지 확인

        user?.let {
            Log.d("LOGIN_ACT/GET_USER", "userId : ${user.id}, $user")
            //DB에서 해당 유저를 찾았을 경우 실행되는 구문
            saveJwt(user.id)
            startMainAcitvity()
        }
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()


    }

    //userTable에 있는 id값을 jwt토큰으로로 대체해서 사용 > 로그인하기전에 id를 sharedPreference로 저장
    private fun saveJwt(jwt:Int){
        //인자값으로 받은 것을 sharedPreference로 저장
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainAcitvity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}