package cl.luckio.dibuje;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends BaseActivity {

    private static final String TAG = Login.class.getSimpleName();

    private Button btLogin;
    private Button btCreate;
    private EditText etUser;
    private EditText etPass;
    private LinearLayout llActivityMain;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btLogin = (Button) findViewById(R.id.btLogin);
        btCreate = (Button) findViewById(R.id.btCreate);
        llActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mAuth = FirebaseAuth.getInstance();
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.edPass);

        btLogin.setOnClickListener(login);
        btCreate.setOnClickListener(create);

        mAuthListener = fbAuth;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private View.OnClickListener create = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideKeyboard(view);
        }
    };

    private FirebaseAuth.AuthStateListener fbAuth = new FirebaseAuth.AuthStateListener(){
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "onAuthStateChanged: singed_in: " + user.getUid());
            } else {
                Snackbar.make(llActivityMain, R.string.sbNoSession, Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etUser.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etUser.setError("Required.");
            valid = false;
        } else {
            etUser.setError(null);
        }

        String password = etPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPass.setError("Required.");
            valid = false;
        } else {
            etPass.setError(null);
        }

        return valid;
    }
}
