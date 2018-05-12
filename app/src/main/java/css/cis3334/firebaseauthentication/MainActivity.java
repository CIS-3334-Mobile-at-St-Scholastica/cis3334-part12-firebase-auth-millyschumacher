package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * MainActivity()
 *
 * @author Amelia Schumacher
 *
 * This class sets up the Firebase tasks for creating an account and sign-on
 */
public class MainActivity extends AppCompatActivity {

    //All variables are declared
    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;
    private FirebaseAuth mAuth;

    /**
     * onCreate()
     *
     * Launch the application and set up sign in options with create account option
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The variables are linked to the xml file string values
        mAuth=FirebaseAuth.getInstance();
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);

        //A listener to detect if the login button is clicked
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the user's entered email and password for them to sign in
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        //The listener detects if the user clicks on the create login button
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get the user's entered email and password to set up the new login
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        //A listener to detect if the user wants to log in using Google credentials
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleSignIn();
            }
        });

        //A listener to sign the user out
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signOut();
            }
        });


    }

    /**
     * createAccount()
     *
     * This method creates new accounts with an email and password for login
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        //use a listener to create an authentication with the current user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Signed In");
                        } else {
                            // If sign in fails, display a message to the user.
                            textViewStatus.setText("Signed Out");
                        }
                    }
                });

    }

    /**
     * signIn()
     *
     * This method requires users to exist before they can log in and uses an existing user-password combo
     * @param email
     * @param password
     */
    private void signIn(String email, String password){
        //Use a listener to sign the current user in with their credentials
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            textViewStatus.setText("Signed In");
                        } else {
                            // If sign in fails, display a message to the user.
                            textViewStatus.setText("Signed Out");
                        }
                }
                });

    }

    /**
     * signOut()
     *
     * The method to sign a user out of the application
     */
    private void signOut () {
        mAuth.signOut();
        textViewStatus.setText("Signed Out");

    }

    /**
     * googleSignIn()
     *
     * A method to sign in using google if a user chose that option
     */
    private void googleSignIn() {

    }

    /**
     * onStart()
     *
     * This method executes after onCreate()
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null) {
            // User is signed in
            textViewStatus.setText("Signed In");
        } else {
            // User is signed out
            textViewStatus.setText("Signed Out");
        }
    }

}
