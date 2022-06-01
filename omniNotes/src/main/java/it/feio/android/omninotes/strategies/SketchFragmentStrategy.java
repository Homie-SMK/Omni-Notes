package it.feio.android.omninotes.strategies;

import android.content.pm.ActivityInfo;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.SketchFragment;
import it.feio.android.omninotes.factory.FragmentStrategy;

public class SketchFragmentStrategy extends FragmentStrategy implements onBackPressedStrategy {
    private MainActivity mMainActivity;

    public void process() {
        // SketchFragment
        Fragment fragment = mMainActivity.checkFragmentInstance(R.id.fragment_container, SketchFragment.class);
        if (fragment != null) {
            ((SketchFragment) fragment).save();

            // Removes forced portrait orientation for this fragment
            mMainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

            mMainActivity.getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public onBackPressedStrategy createOnBackPressedStrategy() {
        return new ListFragmentStrategy();
    }

    private MainActivity getMainActivity() {
        if(mMainActivity == null){
            mMainActivity = new MainActivity();
        }
        return mMainActivity;
    }
}
