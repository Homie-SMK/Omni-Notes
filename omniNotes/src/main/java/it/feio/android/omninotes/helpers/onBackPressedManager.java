package it.feio.android.omninotes.helpers;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.SketchFragment;
import it.feio.android.omninotes.strategies.onBackPressedNavigator;
import it.feio.android.omninotes.MainActivity;

public class onBackPressedManager {
    private MainActivity mMainActivity;

    public void process(int id) {
        MainActivity mainActivity = getMainActivity();
        onBackPressedNavigator navigator;
        Fragment fragment = mainActivity.checkFragmentInstance(id, DetailFragment.class);
        if (fragment != null) {
            navigator = new onBackPressedNavigator(DetailFragment.class);
            navigator.process();
        }
        fragment = mainActivity.checkFragmentInstance(id, SketchFragment.class);
        if (fragment != null) {
            navigator = new onBackPressedNavigator(SketchFragment.class);
            navigator.process();
        }
        fragment = mainActivity.checkFragmentInstance(id, ListFragment.class);
        if (fragment != null) {
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
