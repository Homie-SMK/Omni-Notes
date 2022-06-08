package it.feio.android.omninotes.strategies;

import android.os.Build;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.SketchFragment;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OmniNotes.class, Build.VERSION.class})
@PowerMockIgnore("jdk.internal.reflect.*")

public class onBackPressedNavigatorTest {
    onBackPressedNavigator mNavigator;

    @Test
    public void getStrategyTest() {
        mNavigator = new onBackPressedNavigator(DetailFragment.class);
        assertSame(mNavigator.getStrategy().getClass(), DetailFragmentStrategy.class);
        mNavigator = new onBackPressedNavigator(ListFragment.class);
        assertSame(mNavigator.getStrategy().getClass(), ListFragmentStrategy.class);
        mNavigator = new onBackPressedNavigator(SketchFragment.class);
        assertSame(mNavigator.getStrategy().getClass(), SketchFragmentStrategy.class);
    }
}
