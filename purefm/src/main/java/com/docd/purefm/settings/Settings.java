package com.docd.purefm.settings;

import java.util.HashSet;
import java.util.Set;

import com.docd.purefm.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.widget.Toast;

public final class Settings {
    
    private static final String KEY_BOOKMARKS = "purefm.settings.keys.bookmarks";
    private Settings() {}
    
    public static final int APPEARANCE_LIST = 0;
    public static final int APPEARANCE_GRID = 1;
    
    public static int appearance;
    public static boolean showSize;
    public static boolean showHidden;
    public static boolean showPermissions;
    public static boolean showPreviews;
    public static boolean showLastModified;
    public static boolean useCommandLine;
    public static boolean su;
    
    public static void init(Context context, Resources res) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        appearance = Integer.parseInt(p.getString(res.getString(R.string.key_preference_appearance), Integer.toString(APPEARANCE_LIST)));
        showHidden = p.getBoolean(res.getString(R.string.key_preference_show_hidden), false);
        showSize = p.getBoolean(res.getString(R.string.key_preference_show_size), true);
        showPermissions = p.getBoolean(res.getString(R.string.key_preference_show_permissions), true);
        showPreviews = p.getBoolean(res.getString(R.string.key_preference_show_preview), true);
        showLastModified = p.getBoolean(res.getString(R.string.key_preference_show_modified), true);
        useCommandLine = p.getBoolean(res.getString(R.string.key_preference_use_commandline), false);
        su = p.getBoolean(res.getString(R.string.key_preference_allow_root), false);
    }
    
    public static void setUseCommandLine(Context context, boolean use) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor e = p.edit();
        e.putBoolean(context.getString(R.string.key_preference_use_commandline), use);
        e.commit();
        useCommandLine = use;
    }
    
    public static void setAllowRoot(Context context, boolean use) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor e = p.edit();
        e.putBoolean(context.getString(R.string.key_preference_allow_root), use);
        e.commit();
        su = use;
    }
    
    public static void saveAppearance(Context context, int newAppearance) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor e = p.edit();
        e.putString(context.getString(R.string.key_preference_appearance), Integer.toString(newAppearance));
        e.commit();
        appearance = newAppearance;
    }
    
    public static String getHomeDirectory(Context context) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        return p.getString(context.getString(R.string.key_preference_home_directory),
                android.os.Environment.getExternalStorageDirectory().getAbsolutePath());
    }
    
    public static Set<String> getBookmarks(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getStringSet(KEY_BOOKMARKS, new HashSet<String>());
    }
    
    public static void saveBookmarks(Context context, Set<String> bookmarks) {
        final SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        final Editor e = p.edit();
        e.putStringSet(KEY_BOOKMARKS, bookmarks);
        e.commit();
    }
    
    public static void addBookmark(Context context, String path) {
        final Set<String> bookmarks = getBookmarks(context);
        if (bookmarks.contains(path)) {
            Toast.makeText(context, R.string.bookmark_exists, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!bookmarks.add(path)) {
            Toast.makeText(context, R.string.bookmark_not_added, Toast.LENGTH_SHORT).show();
            return;
        }
        saveBookmarks(context, bookmarks);
        Toast.makeText(context, R.string.bookmark_added, Toast.LENGTH_SHORT).show();
    }
}