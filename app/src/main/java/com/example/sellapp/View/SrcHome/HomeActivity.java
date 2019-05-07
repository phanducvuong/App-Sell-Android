package com.example.sellapp.View.SrcHome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.sellapp.R;
import com.example.sellapp.View.Fragment.CartFragment;
import com.example.sellapp.View.Fragment.HomeFragment;
import com.example.sellapp.View.Fragment.PersonFragment;

public class HomeActivity extends AppCompatActivity {

    private HomeFragment mHomeFragment;
    private CartFragment mCartFragment;
    private PersonFragment mPersonFragment;

    private BottomNavigationView mItemNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        mItemNav = findViewById(R.id.mItemNav);

        mHomeFragment = new HomeFragment();
        mCartFragment = new CartFragment();
        mPersonFragment = new PersonFragment();

        setFragment(mHomeFragment);

        mItemNav.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){
                case R.id.ic_home:{
                    setFragment(mHomeFragment);
                    return true;
                }
                case R.id.ic_cart: {
                    setFragment(mCartFragment);
                    return true;
                }
                case R.id.ic_person: {
                    setFragment(mPersonFragment);
                    return true;
                }
                default: return true;
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mFrame, fragment);
        fragmentTransaction.commit();
    }

    //close app
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

        builder.setTitle(R.string.exit);
        builder.setMessage(R.string.exit_message);

        builder.setPositiveButton("Exit", (dialogInterface, i) -> finishAffinity());

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
