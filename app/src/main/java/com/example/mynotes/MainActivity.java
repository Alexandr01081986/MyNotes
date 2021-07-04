package com.example.mynotes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mynotes.model.ListNote;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNote();
        setContentView(R.layout.activity_main);
        readSettings();
        initView();
        setDefaults();
    }

    private void setDefaults() {
        addFragment(new MyNotesFragment());
    }

    private void initNote() {
        ListNote notes = ListNote.getInstance();
        if (notes.size() == 0) {
            notes.addNote("Notes 1", "portray 1");
            notes.addNote("Notes 2", "portray 2");
            notes.addNote("Notes 3", "portray 3");
            notes.addNote("Notes 4", "portray 4");
            notes.addNote("Notes 5", "portray 5");
        }
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initButtonMain();
        initButtonFavorite();
        initButtonSettings();
        initButtonBack();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                addFragment(new SettingsFragment());
                setWeightFragmentNoteDetails(0f);
                return true;
            case R.id.action_main:
                addFragment(new MyNotesFragment());
                setWeightFragmentNoteDetails(1f);
                return true;
            case R.id.action_favorite:
                addFragment(new FavoriteFragment());
                setWeightFragmentNoteDetails(0f);
                return true;
        }
        return false;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();
        for (int i = countFragments - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }

    private void setWeightFragmentNoteDetails(float weight) {
        FrameLayout noteDetails = findViewById(R.id.nodeDetailsContainer);
        if(noteDetails!=null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT, weight);
            noteDetails.setLayoutParams(params);
        }
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (Settings.isDeleteBeforeAdd) {
            Fragment fragmentToRemove = getVisibleFragment(fragmentManager);
            if (fragmentToRemove != null) {
                fragmentTransaction.remove(fragmentToRemove);
            }
        }

        if (Settings.isAddFragment) {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }

        if (Settings.isBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    private void initButtonSettings() {
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(v -> {
            addFragment(new SettingsFragment());
            setWeightFragmentNoteDetails(0f);
        });

    }

    private void initButtonFavorite() {
        Button buttonFavorite = findViewById(R.id.buttonFavorite);
        buttonFavorite.setOnClickListener(v -> {
            addFragment(new FavoriteFragment());
            setWeightFragmentNoteDetails(0f);
        });

    }

    private void initButtonMain() {
        Button buttonMain = findViewById(R.id.buttonMain);
        buttonMain.setOnClickListener(v -> {
            addFragment(new MyNotesFragment());
            setWeightFragmentNoteDetails(1f);
        });

    }

    private void initButtonBack() {
        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (Settings.isBackAsRemove) {
                Fragment fragment = getVisibleFragment(fragmentManager);
                if (fragment != null) {
                    fragmentManager.beginTransaction().remove(fragment).commit();
                }
            } else {
                fragmentManager.popBackStack();
            }
        });
    }


    private void readSettings() {

        SharedPreferences sharedPref = getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);

        Settings.isBackStack = sharedPref.getBoolean(Settings.IS_BACK_STACK_USED, false);
        Settings.isAddFragment = sharedPref.getBoolean(Settings.IS_ADD_FRAGMENT_USED, true);
        Settings.isBackAsRemove = sharedPref.getBoolean(Settings.IS_BACK_AS_REMOVE_FRAGMENT, true);
        Settings.isDeleteBeforeAdd = sharedPref.getBoolean(Settings.IS_DELETE_FRAGMENT_BEFORE_ADD, false);
    }
}