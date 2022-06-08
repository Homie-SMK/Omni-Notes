package it.feio.android.omninotes.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import android.os.Environment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import it.feio.android.omninotes.BaseUnitTest;

public class FileHelperTest extends BaseUnitTest {
    @Test
    public void getFilePrefixTest() {
        String testFileName = "test.exe";
        String expected = "test";
        assertEquals(FileHelper.getFilePrefix(testFileName), expected);
    }

    @Test
    public void getFileExtensionTest() {
        String testFileName = "test.exe";
        String expected = ".exe";
        assertEquals(expected, FileHelper.getFileExtension(testFileName));
        testFileName = "";
        expected = "";
        assertEquals(expected, FileHelper.getFileExtension("null"));
    }
}
