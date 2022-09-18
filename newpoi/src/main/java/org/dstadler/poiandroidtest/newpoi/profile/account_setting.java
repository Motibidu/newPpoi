package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.dstadler.poiandroidtest.newpoi.R;


public class account_setting extends AppCompatActivity {

    private ImageButton backBtn;
    private Button signOutBtn, cmpltBtn;
    private TextView emailCntnt, pswdCntnt;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private SignInButton signInBtn;
    private int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting);
        mAuth = FirebaseAuth.getInstance();

        //뒤로가기 버튼
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //이메일내용
        emailCntnt = findViewById(R.id.emailCntnt);
        //비밀번호내용
        pswdCntnt = findViewById(R.id.pswdCntnt);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        //로그인 버튼
        signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //로그아웃 버튼
        signOutBtn = findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener() {
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
//                Toast.makeText(account_setting.this, "You are Logged out", Toast.LENGTH_SHORT).show();

                //로그아웃 상태일 때 이메일 내용을 None으로 설정
                emailCntnt.setText("None");

                //로그아웃 상태일 때 이메일 내용을 None으로 설정
                pswdCntnt.setText("None");

                //로그아웃 상태일 때 로그인 버튼이 보임
                signInBtn.setVisibility(View.VISIBLE);
                //로그아웃 상태일 때 로그아웃 버튼이 보이지 않음
                signOutBtn.setVisibility(View.INVISIBLE);
            }
        });

        //완료 버튼, 프로필화면으로 넘어감
        cmpltBtn = findViewById(R.id.cmpltBtn);
        cmpltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(account_setting.this, profile_screen.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }
    //UI업데이트
    private void updateUI(FirebaseUser fUser){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        //로그인 상태일 때
        if(isSignedIn()){
            String personEmail = account.getEmail();

            //로그인 버튼을 보이지 않음
            signInBtn.setVisibility(View.INVISIBLE);
            //로그아웃 버틍르 보임
            signOutBtn.setVisibility(View.VISIBLE);
            //이메일내용을 구글이메일로 설정함
            emailCntnt.setText(personEmail);
            //비밀번호내용을 "*********"으로 설정함
            pswdCntnt.setText("**********");
//            Toast.makeText(account_setting.this,personEmail, Toast.LENGTH_SHORT).show();
        }
        //로그아웃 상태일 때
        else{
            //로그인 버튼을 보임
            signInBtn.setVisibility(View.VISIBLE);
            //로그아웃 버튼을 보이지 않음
            signOutBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void signIn(){
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try{
                    Toast.makeText(account_setting.this,"Signed In Successfully", Toast.LENGTH_SHORT).show();
                    GoogleSignInAccount acc = task.getResult(ApiException.class);

                    FirebaseGoogleAuth(acc);
                }
                catch(ApiException e){
                    Toast.makeText(account_setting.this,"Sign In Failed", Toast.LENGTH_SHORT).show();
//            FirebaseGoogleAuth(null);

                }
//                handleSignInResult(task);
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                int statusCode = result.getStatus().getStatusCode();
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
//            FirebaseGoogleAuth(null);

        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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

    //로그인 check
    private boolean isSignedIn() {
        return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;
    }
}
