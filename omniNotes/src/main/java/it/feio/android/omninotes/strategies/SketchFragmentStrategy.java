package it.feio.android.omninotes.strategies;

import android.content.pm.ActivityInfo;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.SketchFragment;

public class SketchFragmentStrategy extends MainActivity implements onBackPressedStrategy {
    public void process() {
        // SketchFragment
        Fragment fragment = checkFragmentInstance(R.id.fragment_container, SketchFragment.class);
        if (fragment != null) {
            ((SketchFragment) fragment).save();

            // Removes forced portrait orientation for this fragment
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

            getSupportFragmentManager().popBackStack();
        }
    }
}
