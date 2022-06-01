package it.feio.android.omninotes.helpers;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.SketchFragment;
import it.feio.android.omninotes.strategies.onBackPressedNavigator;
import it.feio.android.omninotes.MainActivity;

public class onBackPressedManager {
    private MainActivity mMainActivity;
    private Fragment mFragment;

    public void process(int id) {
        MainActivity mainActivity = getMainActivity();
        onBackPressedNavigator navigator;
        mFragment = mainActivity.checkFragmentInstance(id, DetailFragment.class);
        if (mFragment != null) {
            navigator = new onBackPressedNavigator(DetailFragment.class);
            navigator.process();
        }
        mFragment = mainActivity.checkFragmentInstance(id, SketchFragment.class);
        if (mFragment != null) {
            navigator = new onBackPressedNavigator(SketchFragment.class);
            navigator.process();
        }
        mFragment = mainActivity.checkFragmentInstance(id, ListFragment.class);
        if (mFragment != null) {
            navigator = new onBackPressedNavigator(ListFragment.class);
            navigator.process();
        }
    }

    private MainActivity getMainActivity() {
        if(mMainActivity == null){
            mMainActivity = new MainActivity();
        }
        return mMainActivity;
    }
}
