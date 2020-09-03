package com.ddhuy4298.chatapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.ddhuy4298.chatapp.R;
import com.ddhuy4298.chatapp.databinding.ActivityMainBinding;
import com.ddhuy4298.chatapp.fragments.BaseFragment;
import com.ddhuy4298.chatapp.fragments.ChatFragment;
import com.ddhuy4298.chatapp.fragments.UserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ChatFragment fmChat = new ChatFragment();
    private UserFragment fmUser = new UserFragment();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private boolean clickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.login_bk_color));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initFragment();

        binding.spaceNav.initWithSaveInstanceState(savedInstanceState);
        setupSpaceNav();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        binding.spaceNav.onSaveInstanceState(outState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setupSpaceNav() {
        binding.spaceNav.addSpaceItem(new SpaceItem("Chat", R.drawable.ic_message));
        binding.spaceNav.addSpaceItem(new SpaceItem("User", R.drawable.ic_user));
        binding.spaceNav.showIconOnly();

        binding.spaceNav.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
//                if (clickable) {
//                    firebaseAuth.signOut();
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                    binding.spaceNav.setEnabled(false);
//                    binding.spaceNav.setClickable(false);
//                }
//                clickable = false;
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex) {
                    case 0:
                        showFragment(fmChat);
                        break;
                    case 1:
                        showFragment(fmUser);
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }

    private void initFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, fmChat);
        transaction.add(R.id.container, fmUser);
        transaction.commit();
        showFragment(fmChat);
    }

    private void showFragment(BaseFragment fmShow) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fmChat);
        transaction.hide(fmUser);
        transaction.show(fmShow);
        transaction.commit();
    }
}