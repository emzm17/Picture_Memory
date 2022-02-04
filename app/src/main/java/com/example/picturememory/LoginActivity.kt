package com.example.picturememory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.picturememory.models.User
import com.example.picturememory.dao.UserDao
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN:Int=123
   lateinit var auth:FirebaseAuth
    companion object{
        const val TAG="LoginActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
          googleSignInClient= GoogleSignIn.getClient(this,gso)
        auth= FirebaseAuth.getInstance()
        signInButton.setOnClickListener {
             signIn()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentuser=auth.currentUser
        updateUI(currentuser)
    }
    private fun signIn(){
        val signIntent=googleSignInClient.signInIntent
        startActivityForResult(signIntent,RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {

                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInButton.visibility=View.GONE
        txt1.visibility=View.GONE
        txt2.visibility=View.GONE
        imgLogo.visibility=View.GONE
        progressBar.visibility=View.VISIBLE
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                   Log.d(TAG,"FIREBASE: ${user!!.displayName}")
                    updateUI(user)
                 } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {
         if(user==null){
             signInButton.visibility=View.VISIBLE
             txt1.visibility=View.VISIBLE
             txt2.visibility=View.VISIBLE
             imgLogo.visibility=View.VISIBLE
             progressBar.visibility=View.GONE
         }
        else{
            val newUser=User(user.uid,user.displayName,user.photoUrl.toString())
             val userDao= UserDao()
             userDao.addUsers(newUser)
             startActivity(Intent(this,FrontPage::class.java))
             finish()
         }
    }



}