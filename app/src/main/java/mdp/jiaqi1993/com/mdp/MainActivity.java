package mdp.jiaqi1993.com.mdp;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //Setup Drawer Here
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;



    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private String tag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);



        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);



        mFragmentManager = getSupportFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame, new BluetoothChatFragment(),"BTFrag");
        mFragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = BluetoothChatFragment.class;
                tag="BTFrag";
                if(mFragmentManager.findFragmentByTag(tag) != null) {
                    //if the fragment exists, show it.
                    mFragmentManager.beginTransaction().show(mFragmentManager.findFragmentByTag(tag)).commit();
                } else {
                    //if the fragment does not exist, add it to fragment manager.
                    mFragmentManager.beginTransaction().add(R.id.frame, new RobotCtrlFrag(), tag).commit();
                }
                if(mFragmentManager.findFragmentByTag("RCFrag") != null){
                    //if the other fragment is visible, hide it.
                    mFragmentManager.beginTransaction().hide(mFragmentManager.findFragmentByTag("RCFrag")).commit();
                }
                break;
            case R.id.nav_second_fragment:
                fragmentClass =RobotCtrlFrag.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = RobotCtrlFrag.class;
                tag="RCFrag";
                if(mFragmentManager.findFragmentByTag(tag) != null) {
                    //if the fragment exists, show it.
                    mFragmentManager.beginTransaction().show(mFragmentManager.findFragmentByTag(tag)).commit();
                } else {
                    //if the fragment does not exist, add it to fragment manager.
                    mFragmentManager.beginTransaction().add(R.id.frame, new RobotCtrlFrag(), tag).commit();
                }
                if(mFragmentManager.findFragmentByTag("BTFrag") != null){
                    //if the other fragment is visible, hide it.
                    mFragmentManager.beginTransaction().hide(mFragmentManager.findFragmentByTag("BTFrag")).commit();
                }
                break;
            default:
                fragmentClass = RobotCtrlFrag.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment

        mFragmentManager.beginTransaction().show(mFragmentManager.findFragmentByTag(tag)).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }




}
