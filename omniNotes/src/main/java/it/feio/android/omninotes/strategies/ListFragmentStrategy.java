package it.feio.android.omninotes.strategies;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;

import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.factory.FragmentStrategy;

public class ListFragmentStrategy extends FragmentStrategy implements onBackPressedStrategy{
    private MainActivity mMainActivity;
    @Override
    public void process() {
        // ListFragment
        Fragment fragment = mMainActivity.checkFragmentInstance(R.id.fragment_container, ListFragment.class);
        if (fragment != null) {
            // Before exiting from app the navigation drawer is opened
            boolean isNavdrawerOnExitSetted = Prefs.getBoolean("settings_navdrawer_on_exit", false);
            boolean isDrawerLayoutInExistence = mMainActivity.getDrawerLayout() != null;
            boolean isDrawerOpened = mMainActivity.getDrawerLayout().isDrawerOpen(GravityCompat.START);
            if (isNavdrawerOnExitSetted && isDrawerLayoutInExistence && !isDrawerOpened) {
                mMainActivity.getDrawerLayout().openDrawer(GravityCompat.START);
            } else if (!isNavdrawerOnExitSetted && isDrawerLayoutInExistence && isDrawerOpened) {
                mMainActivity.getDrawerLayout().closeDrawer(GravityCompat.START);
            } else {
                if (!((ListFragment) fragment).closeFab()) {
                    mMainActivity.setPasswordAcceptedFalse();
                }
            }
        }
    }

    @Override
    public onBackPressedStrategy createStrategy() {
        return new ListFragmentStrategy();
    }

    private MainActivity getMainActivity() {
        if(mMainActivity == null){
            mMainActivity = new MainActivity();
        }
        return mMainActivity;
    }
}
