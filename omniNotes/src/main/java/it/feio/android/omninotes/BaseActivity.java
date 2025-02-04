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
package it.feio.android.omninotes;

import static it.feio.android.omninotes.utils.ConstantsBase.INTENT_UPDATE_DASHCLOCK;
import static it.feio.android.omninotes.utils.ConstantsBase.PREF_NAVIGATION;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewConfiguration;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.pixplicity.easyprefs.library.Prefs;
import it.feio.android.omninotes.helpers.LanguageHelper;
import it.feio.android.omninotes.helpers.LogDelegate;
import it.feio.android.omninotes.models.Note;
import it.feio.android.omninotes.models.PasswordValidator;
import it.feio.android.omninotes.utils.Navigation;
import it.feio.android.omninotes.utils.PasswordHelper;
import it.feio.android.omninotes.widget.ListWidgetProvider;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

  protected static final int TRANSITION_VERTICAL = 0;
  protected static final int TRANSITION_HORIZONTAL = 1;

  protected String navigation;
  protected String navigationTmp; // used for widget navigation


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_list, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    Context context = LanguageHelper.updateLanguage(newBase, null);
    super.attachBaseContext(context);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Forces menu overflow icon
    try {
      ViewConfiguration config = ViewConfiguration.get(this.getApplicationContext());
      Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
      if (menuKeyField != null) {
        menuKeyField.setAccessible(true);
        menuKeyField.setBoolean(config, false);
      }
    } catch (Exception e) {
      LogDelegate.warningLog("Just a little issue in physical menu button management", e);
    }
    super.onCreate(savedInstanceState);
  }


  @Override
  protected void onResume() {
    super.onResume();
    String navNotes = getResources().getStringArray(R.array.navigation_list_codes)[0];
    navigation = Prefs.getString(PREF_NAVIGATION, navNotes);
    LogDelegate.debugLog(Prefs.getAll().toString());
  }


  protected void showToast(CharSequence text, int duration) {
    if (Prefs.getBoolean("settings_enable_info", true)) {
      Toast.makeText(getApplicationContext(), text, duration).show();
    }
  }


  /**
   * Method to validate security password to protect a list of notes. When "Request password on
   * access" in switched on this check not required all the times. It uses an interface callback.
   */
   public void requestPassword(final Activity mActivity, List<Note> notes, final PasswordValidator mPasswordValidator) {
	    if (Prefs.getBoolean("settings_password_access", false)) {
	      mPasswordValidator(mPasswordValidator);
	      return;
	    }
	    boolean askForPassword = false;
	    for (Note note : notes) {
	      if (note.isLocked()) {
	        askForPassword = true;
	        break;
	      }
	    }
	    if (askForPassword) {
	      PasswordHelper.requestPassword(mActivity, mPasswordValidator);
	    } else {
	    	mPasswordValidator(mPasswordValidator);
        }
   }
   private void mPasswordValidator(final PasswordValidator mPasswordValidator) {
     mPasswordValidator.onPasswordValidated(PasswordValidator.Result.SUCCEED);
   }
   public boolean updateNavigation(String nav) {
     boolean isNavEqualsTmp = nav.equals(navigationTmp);
     boolean isnavEqualsNavi = Navigation.getNavigationText().equals(nav);
     if (isNavEqualsTmp || (navigationTmp == null && isnavEqualsNavi)) {
       return false;
     }
     Prefs.edit().putString(PREF_NAVIGATION, nav).apply();
     navigation = nav;
     navigationTmp = null;
     return true;
  }


  /**
   * Retrieves resource by name
   */
  private String getStringResourceByName(String aString) {
    String packageName = getApplicationContext().getPackageName();
    int resId = getResources().getIdentifier(aString, "string", packageName);
    return getString(resId);
  }


  /**
   * Notifies App Widgets about data changes so they can update theirselves
   */
  public static void notifyAppWidgets(Context context) {
    // Home widgets
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, ListWidgetProvider.class));
    LogDelegate.debugLog("Notifies AppWidget data changed for widgets " + Arrays.toString(ids));
    appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list);
    // Dashclock
    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(INTENT_UPDATE_DASHCLOCK));
  }


  @SuppressLint("InlinedApi")
  protected void animateTransition(FragmentTransaction transaction, int direction) {
    switch (direction) {
      case TRANSITION_HORIZONTAL:
      transaction.setCustomAnimations(R.anim.fade_in_support, R.anim.fade_out_support, R.anim.fade_in_support, R.anim.fade_out_support);
    break;
      case TRANSITION_VERTICAL:
      transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_pop, R.anim.anim_out_pop);break;
    }
  }


  protected void setActionBarTitle(String title) {
    // Creating a spannable to support custom fonts on ActionBar
    int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "ID", "android");
    android.widget.TextView actionBarTitleView = getWindow().findViewById(actionBarTitle);
    Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");

    boolean notEmptyActionBarTitleView = actionBarTitleView != null;
    boolean notEmptySupportActionBar = getSupportActionBar() != null;

    if (notEmptyActionBarTitleView) {
      actionBarTitleView.setTypeface(font);
    }

    if (notEmptySupportActionBar) {
      getSupportActionBar().setTitle(title);
    }
  }


  public String getNavigationTmp() {
    return navigationTmp;
  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return keyCode == KeyEvent.KEYCODE_MENU || super.onKeyDown(keyCode, event);
  }
}
