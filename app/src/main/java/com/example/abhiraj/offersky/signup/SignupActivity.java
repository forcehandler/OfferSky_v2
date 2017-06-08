package com.example.abhiraj.offersky.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.abhiraj.offersky.BaseActivity;
import com.example.abhiraj.offersky.Constants;
import com.example.abhiraj.offersky.R;
import com.example.abhiraj.offersky.model.User;
import com.example.abhiraj.offersky.ui.MainActivity;
import com.example.abhiraj.offersky.utils.FirebaseUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import org.joda.time.LocalDate;

public class SignupActivity extends BaseActivity implements View.OnClickListener, VerificationListener, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = SignupActivity.class.getSimpleName();

    private FirebaseAuth mAuth;

    //listener to authentication state changes
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button mCreateUserButton;
    private Button mVerifyUserButton;
    private EditText mOtpEditText;
    private EditText mNameEditText;
    private EditText mPhoneEditText;
    private EditText mAgeEditText;
    private EditText mBloodEditText;
    private RadioButton maleRB;
    private RadioButton femaleRB;

    private TextInputLayout mNameInputLayout;
    private TextInputLayout mAgeInputLayout;
    private TextInputLayout mPhoneInputLayout;

    //listener for OTP callbacks
    private Verification mVerification;

    // Sign in button
    private SignInButton mSignInButton;

    // Signing options google
    private GoogleSignInOptions gso;

    // google api client
    private GoogleApiClient mGoogleApiClient;

    private User mUser = new User();

    // authentication medium
    private int authentication_route = -1;
    // 0 for anpnymous and 1 for google sign in

    // Signin constant to check activity result
    private static final int RC_SIGN_IN = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        createAuthStateListener();
        mVerifyUserButton = (Button) findViewById(R.id.verifyOtpButton);
        mCreateUserButton = (Button) findViewById(R.id.createUserButton);
        mOtpEditText = (EditText) findViewById(R.id.otpEditText);
        mNameEditText = (EditText) findViewById(R.id.nameEditText);
        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        mAgeEditText = (EditText) findViewById(R.id.ageEditText);
        mBloodEditText = (EditText) findViewById(R.id.bloodEditText);
        maleRB = (RadioButton) findViewById(R.id.maleRadioButton);
        femaleRB = (RadioButton) findViewById(R.id.femaleRadioButton);



        mCreateUserButton.setOnClickListener(this);
        mVerifyUserButton.setOnClickListener(this);

        // Initializing google sign in button
        mSignInButton  =(SignInButton) findViewById(R.id.google_sign_in_button);
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);
        mSignInButton.setOnClickListener(this);

        // Google sign in
        handleGoogleSignIn();
    }

    @Override
    public void onStart()
    {
        Log.d(TAG, "in onStart");
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onDestroy()
    {
        mVerification = null;
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onPause()
    {
        //mVerification = null;
        super.onPause();
    }

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void createAuthStateListener()
    {
        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //updateUserCredentials();

                    Log.d(TAG, "updateUserCredentials()");

                    //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user != null) {
                        Log.d(TAG, "user is not null");


                        if (authentication_route == 0) {
                            Log.d(TAG, "OTP login");

                            FirebaseUtils.addUser(mUser);

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mUser.getName())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                        }

                        else if (authentication_route == 1) {
                            Log.d(TAG, "google login");
                            String name="";
                            String email="";
                            String gender;

                           UserInfo profile = user.getProviderData().get(0);
                                // UID specific to the provider
                                //String uid = profile.getUid();

                                // Name, email address, and profile photo Url
                                name = profile.getDisplayName();
                                email = profile.getEmail();
                                Log.d(TAG, profile.getEmail()+ " email");
                                Log.d(TAG, profile.getPhotoUrl()+ " photo url");
                                Log.d(TAG, profile.getProviderId()+ " provider id");

                            mUser.setName(name);
                            mUser.setEmail(email);

                            Log.d(TAG, FirebaseAuth.getInstance().getCurrentUser().getUid() + " firebase user id");
                            FirebaseUtils.addUser(mUser);
                        }
                    }



                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]
    }



    @Override
    public void onClick(View v) {
        Log.d(TAG, "in onClick");
        if (v == mCreateUserButton) {
            createNewUser();

        }
        else if (v == mVerifyUserButton) {
            if(mOtpEditText.getText().toString().trim().equals("")) {
                Log.d(TAG, mOtpEditText.getText().toString().equals(null) + " " + mOtpEditText.getText().toString());
                mOtpEditText.setError("Please enter your Otp");
            }
            else
            {
                if(mVerification != null) {
                    mVerification.verify(mOtpEditText.getText().toString());
                }
            }
        }
        else if(v == mSignInButton)
        {
            googleSignIn();
        }
    }

    private boolean isValidPhone(String phone) {
        boolean isGoodPhone =
                (phone != null && android.util.Patterns.PHONE.matcher(phone).matches());
        if (!isGoodPhone) {
            mPhoneEditText.setError("Please enter a valid email address");
            return false;
        }
        return isGoodPhone;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("Please enter your name");
            return false;
        }
        return true;
    }

    // Create Account with Email and Password

    private void createAnonymousUser() {
        Log.d(TAG, "createAccount");

        String name = mNameEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String age = mAgeEditText.getText().toString();
        String blood = mBloodEditText.getText().toString();
        // Check gender
        String gender;
        if(maleRB.isChecked())
        {
            gender = "M";
        }
        else if(femaleRB.isChecked())
        {
            gender = "F";
        }
        // TODO: Set error if any of the fields remains empty through android design
        // Support library. requires extra code.
        else
        {
            // Set error if radio button is not checked
            gender = "Indeterminate";
        }

        //TODO: fetch uid from server, currently setting it up as the current time
        LocalDate now = new LocalDate();

        mUser.setName(name);
        mUser.setAge(age);
        mUser.setGender(gender);
        mUser.setPhone(phone);


        // TODO: login via firebase
        final String email = phone + Constants.Signup.USER_DEFAULT_EMAIL_DOMAIN;
        final String password = Constants.Signup.USER_DEFAULT_PASSWORD;

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
                            /*Toast.makeText(SignupActivity.this, "Authentication Failed",
                                    Toast.LENGTH_SHORT).show();*/

                            // If the user is unable to authenticate then sign in the user in the
                            // background using his phone number and default password
                            signIn(email, password);

                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(SignupActivity.this, "Sign in failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        //hideProgressDialog();

                    }
                });
    }


    private void createNewUser() {
        Log.d(TAG, "in createNewUser");
        final String name = mNameEditText.getText().toString().trim();
        final String phone = mPhoneEditText.getText().toString().trim();

        boolean validPhone = isValidPhone(phone);
        boolean validName = isValidName(name);
        if (!validPhone || !validName){
            return;
        }

        //TODO: Implement OTP properly, currently only for testing purposes
        mVerification = SendOtpVerification.createSmsVerification(this, phone, this, "91", true);
        mVerification.initiate(); //sending otp on given number

        //TODO: storing user values when the values are sane, in later version
        //TODO: store values in preferences after OTP is matched

    }




    //Methods for OTP from Msg91
    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "got on initiated callback from otp");
        Toast.makeText(this, "sent the otp", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Log.d(TAG, "got on failed initialization callback from otp");
        Toast.makeText(this, "initiation failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onVerified(String response) {
        Log.d(TAG, "got on verification successful callback from otp");
        Toast.makeText(this, "verification successful", Toast.LENGTH_LONG).show();
        authentication_route = 0;
        createAnonymousUser();
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.d(TAG, "got on verification failed callback from otp");
        Toast.makeText(this, "verification failed", Toast.LENGTH_LONG).show();

    }



    // [ START GOOGLE SIGN IN ]
    private void handleGoogleSignIn()
    {

        Log.d(TAG, "handleGoogleSignIn()");
        // Initializing google sign in option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    // Create sign in intent
    private void googleSignIn()
    {

        Log.d(TAG, "googleSignIn()");
        // Creating signin dialog intent
        Intent signInIntent = Auth.GoogleSignInApi
                .getSignInIntent(mGoogleApiClient);


        showProgressDialog();
        // Starting signin activity with the signin intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    // To handle result from signin dialog
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        Log.d(TAG, "onActivityResult( of google sign in");

        super.onActivityResult(requestCode, resultCode, data);

        // Check request code
        if(requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);

            // function to handle sign in
            handleSignInResult(result);

        }

    }

    // [START handleSignInResult]
    // Function is called after recieving signin intent from dialog
    private void handleSignInResult(GoogleSignInResult result) {

        Log.d(TAG, "handleSignInResult google sign in");

        // If the login is successful
        if(result.isSuccess())
        {
            // Grab the google account!
            GoogleSignInAccount account = result.getSignInAccount();

            authentication_route = 1;

            // Authenticate with firebase
            firebaseAuthWithGoogle(account);


            // We can grab image url from GoogleSignInAccount object to display
            // user's image <code> String url = account.getPhotoUrl().toString() </code>
        }

        else // If login fails
        {
            Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show();
        }
    }
    // [END handleSignInResult]

    // [START firebaseAuthWithGoogle]
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle " + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        else{

                            hideProgressDialog();
                        }

                        // ...
                    }
                });
    }
    // [END firebaseAuthWithGoogle]

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    // [ END GOOGLE SIGN IN ]

    private void updateUserCredentials() {

        Log.d(TAG, "updateUserCredentials()");
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(TAG, "user is not null");
            String uid = user.getUid();

            if (authentication_route == 0) {
                Log.d(TAG, "OTP login");
                mRef.child("users").child(uid).setValue(mUser);

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(mUser.getName())
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            }
                        });
            }

            else if (authentication_route == 1) {
                Log.d(TAG, "google login in function");
                String name="";
                String email="";
                String gender;

                for (UserInfo profile : user.getProviderData()) {
                    // UID specific to the provider
                    //String uid = profile.getUid();

                    // Name, email address, and profile photo Url
                    name = profile.getDisplayName();
                    email = profile.getEmail();
                }
                mUser.setName(name);
                mUser.setEmail(email);
                mRef.child("users").child(uid).setValue(mUser);
            }
        }
    }
}
