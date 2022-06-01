package it.feio.android.omninotes.factory;

import it.feio.android.omninotes.strategies.DetailFragmentStrategy;
import it.feio.android.omninotes.strategies.onBackPressedStrategy;

abstract public class FragmentStrategy {
    abstract public onBackPressedStrategy createStrategy();
}
