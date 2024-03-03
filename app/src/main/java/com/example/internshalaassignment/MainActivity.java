package com.example.internshalaassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
    SharedPreferences pref;
    NavController navController;
    androidx.navigation.fragment.NavHostFragment navHostFragment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize tool bar in Activity because it is common for all fragments
        toolbar = findViewById(R.id.materialToolbar);
        pref = getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        navController = navHostFragment.getNavController();


        if (pref.getBoolean(getString(R.string.isLoggedIn),false)){

           navController.navigate(R.id.action_signInFragment_to_mainFragment);

        }

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId()==R.id.mainFragment || navDestination.getId()==R.id.signInFragment){
                    //Show logout menu item on toolbar by updating toolbar
                    updateToolbar();
                }else{
                    // Remove logout menu item from tool bar
                    toolbar.getMenu().clear();
                }
            }
        });







    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(getString(R.string.isLoggedIn),false);
        //Default as a value will signify that the user has logged out hence the user data is erased from shared preference
        editor.putString(getString(R.string.current_user_emailId),"Default");
        editor.putString(getString(R.string.current_user_displayName),"Default");
        editor.apply();
        toolbar.getMenu().clear();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut() .addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, getString(R.string.logged_out_message), Toast.LENGTH_SHORT).show();
            }
        });

        navController.navigate(R.id.action_mainFragment_to_signInFragment);





    }

    public void updateToolbar(){
        toolbar.getMenu().clear();
        String title = pref.getString(getString(R.string.current_user_displayName),getString(R.string.app_name));
        if(title.equals("Default")){
            //User logged out and shared preference's user display name was set to default
            title = getString(R.string.app_name);
        }
        toolbar.setTitle(title);
        if(pref.getBoolean(getString(R.string.isLoggedIn),false)){
            //User logged in
            toolbar.inflateMenu(R.menu.main_topbar_menu);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    //Since there is only 1 menu item so we don't need for any if else statement on item's id

                    logout();


                    return false;
                }
            });
        }
    }


}