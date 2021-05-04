package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class account_setting extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "account_setting";
    private FirebaseAuth mAuth;
    private SignInButton btn_google;
    private Button account_setting_logout_button, complete_account_setting_button;
    private int RC_SIGN_IN = 1;
    private ImageButton imageButton;
    private TextView EditText_email, EditText_password;
    private static int RQST_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this ,R.color.themeColor));

        EditText_email = findViewById(R.id.EditText_email);
        EditText_password = findViewById(R.id.EditText_password);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        imageButton = (ImageButton) findViewById(R.id.account_setting_back_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        account_setting_logout_button = findViewById(R.id.account_setting_logout_button);
        account_setting_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mAuth.signOut();
                            Toast.makeText(getApplicationContext(),"Logout successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(account_setting.this, "You are Logged out", Toast.LENGTH_SHORT).show();

                EditText_email.setText("None");
                EditText_password.setText("None");
                btn_google.setVisibility(View.VISIBLE);
                account_setting_logout_button.setVisibility(View.INVISIBLE);
            }
        });

        complete_account_setting_button = findViewById(R.id.complete_account_setting_button);
        complete_account_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(account_setting.this, profile_screen.class);

                intent.putExtra("email", EditText_email.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    private void signIn(){
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(account_setting.this,"Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch(ApiException e){
            Toast.makeText(account_setting.this,"Sign In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);

        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(account_setting.this,"Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user;
                    if(mAuth.getCurrentUser()== null) {
                        updateUI(null);
                    }else{
                        user = mAuth.getCurrentUser();
                        updateUI(user);
                    }

                }
                else{
                    Toast.makeText(account_setting.this,"Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }
    private void updateUI(FirebaseUser fUser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if(account != null){
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();

            btn_google.setVisibility(View.INVISIBLE);
            account_setting_logout_button.setVisibility(View.VISIBLE);
            EditText_email.setText(personEmail);
            EditText_password.setText("**********");

            Toast.makeText(account_setting.this,personEmail, Toast.LENGTH_SHORT).show();
        }
        else{
            btn_google.setVisibility(View.VISIBLE);
            account_setting_logout_button.setVisibility(View.INVISIBLE);
        }
    }
}
