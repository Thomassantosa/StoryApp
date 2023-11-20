package com.example.storyappsubmission.view.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.storyappsubmission.data.request.LoginRequest
import com.example.storyappsubmission.data.responses.LoginResult
import com.example.storyappsubmission.databinding.ActivityLoginBinding
import com.example.storyappsubmission.helper.ViewModelFactory
import com.example.storyappsubmission.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[LoginViewModel::class.java]

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (binding.emailEditText.error.isNullOrEmpty() && binding.passwordEditText.error.isNullOrEmpty()) {
                val loginRequest = LoginRequest(email, password)
                loginViewModel.loginUser(loginRequest)

                loginViewModel.loginResponse.observe(this) { loginResponse ->
                    Log.d(TAG, "success response")
                    Toast.makeText(this, loginResponse.message, Toast.LENGTH_SHORT).show()
                    val login = LoginResult(loginResponse.loginResult.name,
                        loginResponse.loginResult.userId, loginResponse.loginResult.token)
                    loginViewModel.saveSession(login)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }

                loginViewModel.errorResponse.observe(this) { errorResponse ->
                    if(errorResponse.message != null){
                        Toast.makeText(this, errorResponse.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

//            viewModel.saveSession(UserModel(email, "sample_token"))
//            AlertDialog.Builder(this).apply {
//                setTitle("Yeah!")
//                setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
//                setPositiveButton("Lanjut") { _, _ ->
//                    val intent = Intent(context, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                    startActivity(intent)
//                    finish()
//                }
//                create()
//                show()
//            }
        }
    }
}