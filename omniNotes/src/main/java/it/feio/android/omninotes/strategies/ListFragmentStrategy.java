package it.feio.android.omninotes.strategies;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;

import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;

public class ListFragmentStrategy extends MainActivity implements onBackPressedStrategy{
    @Override
    public void process() {
        // ListFragment
        Fragment fragment = checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragment != null) {
            // Before exiting from app the navigation drawer is opened
            boolean isNavdrawerOnExitSetted = Prefs.getBoolean("settings_navdrawer_on_exit", false);
            boolean isDrawerLayoutInExistence = getDrawerLayout() != null;
            boolean isDrawerOpened = getDrawerLayout().isDrawerOpen(GravityCompat.START);
            if (isNavdrawerOnExitSetted && isDrawerLayoutInExistence && !isDrawerOpened) {
                getDrawerLayout().openDrawer(GravityCompat.START);
            } else if (!isNavdrawerOnExitSetted && isDrawerLayoutInExistence && isDrawerOpened) {
                getDrawerLayout().closeDrawer(GravityCompat.START);
            } else {
                if (!((ListFragment) fragment).closeFab()) {
                    setPasswordAcceptedFalse();
                }
            }
        }
    }
}
