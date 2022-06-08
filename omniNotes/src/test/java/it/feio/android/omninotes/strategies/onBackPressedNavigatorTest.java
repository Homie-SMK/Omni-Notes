package it.feio.android.omninotes.strategies;

import android.os.Build;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import it.feio.android.omninotes.DetailFragment;
import it.feio.android.omninotes.ListFragment;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.SketchFragment;
import it.feio.android.omninotes.factory.FragmentStrategy;

@RunWith(MockitoJUnitRunner.class)
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

    @Test
    public void processTest() {
        mNavigator = new onBackPressedNavigator(DetailFragment.class);
        DetailFragmentStrategy detailFragmentStrategy = mock(DetailFragmentStrategy.class);
        doNothing().when(detailFragmentStrategy).process();
        mNavigator.process();
        verify(detailFragmentStrategy, times(1)).process();

        mNavigator = new onBackPressedNavigator(ListFragment.class);
        ListFragmentStrategy listFragmentStrategy = mock(ListFragmentStrategy.class);
        doNothing().when(listFragmentStrategy).process();
        mNavigator.process();
        verify(listFragmentStrategy, times(1)).process();

        mNavigator = new onBackPressedNavigator(SketchFragment.class);
        SketchFragmentStrategy sketchFragmentStrategy = mock(SketchFragmentStrategy.class);
        doNothing().when(sketchFragmentStrategy).process();
        mNavigator.process();
        verify(sketchFragmentStrategy, times(1)).process();
    }
}
