package com.example.flo.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.flo.ui.signup.SignUpActivity
import com.example.flo.data.entities.User
import com.example.flo.data.remote.auth.AuthService
import com.example.flo.data.remote.auth.Result
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginSignInBtn.setOnClickListener {
            login()
        }

    }

    //validation check
    private fun login() {
        if (binding.loginIdEt.text.toString()
                .isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email =
            binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd = binding.loginPasswordEt.text.toString()
        val name = binding.loginIdEt.text.toString()

//        val songDB = SongDatabase.getInstance(this)!!
//        val user = songDB.userDao().getUser(email, pwd)
//
//
//        // 해당 user를 찾았을 경우 let 안의 내용 실행됨(user가 null이 아닌 경우)
//        user?.let{
//            Log.d("LOGIN_ACT/GET_USER", "userId: ${user.id}, $user")
//            //saveJWT(user.id)
//            startMainActivity()
//        }


        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(User(email, pwd, name))

        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()


    }

    private fun saveJWT(jwt: Int) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun saveJWT2(jwt: String) {
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginSuccess(code: Int, result: Result) {
        when (code) {
            1000 -> {
                saveJWT2(result.jwt)
                startMainActivity()
            }

        }

    }

    override fun onLoginFailure() {
        //실패처리
        Log.d("LOGIN_ACTIVITY", "Failure")

    }


}