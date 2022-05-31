package it.feio.android.omninotes.strategies;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;

public class DetailFragmentStrategy extends MainActivity implements onBackPressedStrategy{
    @Override
    public void process() {
        // DetailFragment
        Fragment fragment = checkFragmentInstance(R.id.fragment_container, DetailFragment.class);
        if (fragment != null) {
            ((DetailFragment) fragment).goBack = true;
            ((DetailFragment) fragment).saveAndExit((DetailFragment) fragment);
        }
    }
}
