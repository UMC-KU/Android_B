package com.example.flo.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.flo.data.entities.User
import com.example.flo.data.remote.auth.AuthService
import com.example.flo.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
            finish()
        }

    }

    private fun getUser(): User {
        val email = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd = binding.signUpPasswordEt.text.toString()
        val name = binding.signUpNameEt.text.toString()
        return User(email, pwd, name)
    }

    //validation
//    private fun signUp(){
//        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val user = userDB.userDao().getUsers()
//        Log.d("SIGNUPACT", user.toString())
//    }

    private fun signUp(){
        if (binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpNameEt.text.toString().isEmpty()){
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }


        val authService = AuthService()
        authService.setSignUpView(this)
        authService.signUp(getUser())

    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {

    }


}