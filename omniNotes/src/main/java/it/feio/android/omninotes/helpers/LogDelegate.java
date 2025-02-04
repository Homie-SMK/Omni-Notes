/*
 * Copyright (C) 2013-2020 Federico Iosue (federico@iosue.it)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.feio.android.omninotes.helpers;

import static it.feio.android.checklistview.interfaces.Constants.TAG;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_ENABLE_FILE_LOGGING;

import android.content.Context;
import android.util.Log;
import com.bosphere.filelogger.FL;
import com.bosphere.filelogger.FLConfig;
import com.bosphere.filelogger.FLConst;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.OmniNotes;
import it.feio.android.omninotes.exceptions.GenericException;
import it.feio.android.omninotes.utils.StorageHelper;
import java.io.File;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogDelegate {

  private static Boolean fileLoggingEnabled;

  public static void vervoseLog(String message) {
    if (isFileLoggingEnabled()) {
      FL.v(message);
    } else {
      Log.v(TAG, message);
    }
  }

  public static void debugLog(String message) {
    if (isFileLoggingEnabled()) {
      FL.d(message);
    } else {
      Log.d(TAG, message);
    }
  }

  public static void informationLog(String message) {
    if (isFileLoggingEnabled()) {
      FL.i(message);
    } else {
      Log.i(TAG, message);
    }
  }

  public static void warningLog(String message, Throwable e) {
    if (isFileLoggingEnabled()) {
      FL.w(message, e);
    } else {
      Log.w(TAG, message, e);
    }
  }

  public static void warningLog(String message) {
    if (isFileLoggingEnabled()) {
      FL.w(message);
    } else {
      Log.w(TAG, message);
    }
  }

  public static void errorLog(String message, Throwable e) {
    if (isFileLoggingEnabled()) {
      FL.e(message, e);
    } else {
      Log.e(TAG, message, e);
    }
  }

  public static void errorLog(String message) {
    errorLog(message, new GenericException(message));
  }

  private static boolean isFileLoggingEnabled() {
    if (fileLoggingEnabled == null) {
      fileLoggingEnabled = Prefs.getBoolean(PREF_ENABLE_FILE_LOGGING, false);
      if (Boolean.TRUE.equals(fileLoggingEnabled)) {
        Context context = OmniNotes.getAppContext();
        FL.init(new FLConfig.Builder(context)
            .minLevel(FLConst.Level.V)
            .logToFile(true)
            .dir(new File(StorageHelper.getOrCreateExternalStoragePublicDir(), "logs"))
            .retentionPolicy(FLConst.RetentionPolicy.FILE_COUNT)
            .build());
        FL.setEnabled(true);
      }
    }
    return fileLoggingEnabled;
  }
}
