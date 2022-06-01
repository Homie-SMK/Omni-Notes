package it.feio.android.omninotes.strategies;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.factory.FragmentStrategy;

public class DetailFragmentStrategy extends FragmentStrategy implements onBackPressedStrategy{
    private MainActivity mMainActivity;

    @Override
    public void process() {
        // DetailFragment
        Fragment fragment = mMainActivity.checkFragmentInstance(R.id.fragment_container, DetailFragment.class);
        if (fragment != null) {
            ((DetailFragment) fragment).goBack = true;
            ((DetailFragment) fragment).saveAndExit((DetailFragment) fragment);
        }
    }

    @Override
    public FragmentStrategy createStrategy() {
        return new DetailFragmentStrategy();
    }

    private MainActivity getMainActivity() {
        if(mMainActivity == null){
            mMainActivity = new MainActivity();
        }
        return mMainActivity;
    }
}
