package it.feio.android.omninotes.strategies;

import androidx.fragment.app.Fragment;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.MainActivity;
import it.feio.android.omninotes.R;
import it.feio.android.omninotes.SketchFragment;

public class onBackPressedNavigator {
    private onBackPressedStrategy mStrategy;

    public onBackPressedNavigator(Object instanceClass) {
        if(instanceClass == DetailFragment.class) {
            this.mStrategy = new DetailFragmentStrategy();
        } else if (instanceClass == SketchFragment.class) {
            this.mStrategy = new SketchFragmentStrategy();
        } else if (instanceClass == ListFragment.class) {
            this.mStrategy = new ListFragmentStrategy();
        }
    }

    public void process() {
        if(mStrategy == null){
            return;
        } else {
            mStrategy.process();
        }
    }
}
