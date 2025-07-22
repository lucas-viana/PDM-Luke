package br.edu.ifsuldeminas.mch.constraintlayouts.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static final String PREFS_NAME = "luke_diario_prefs";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_RESPONSIBLE_EMAIL = "responsible_email";
    private static final String KEY_FIRST_TIME = "first_time";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    private static final String KEY_HIGH_CONTRAST_ENABLED = "high_contrast_enabled";
    
    private SharedPreferences preferences;
    
    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    public void setUserName(String userName) {
        preferences.edit().putString(KEY_USER_NAME, userName).apply();
    }
    
    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, "Usuário");
    }
    
    public void setFirstTime(boolean firstTime) {
        preferences.edit().putBoolean(KEY_FIRST_TIME, firstTime).apply();
    }
    
    public boolean isFirstTime() {
        return preferences.getBoolean(KEY_FIRST_TIME, true);
    }
    
    public void setNotificationsEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }
    
    public boolean getNotificationsEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }
    
    public boolean areNotificationsEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }
    
    public void setHighContrastEnabled(boolean enabled) {
        preferences.edit().putBoolean(KEY_HIGH_CONTRAST_ENABLED, enabled).apply();
    }
    
    public boolean getHighContrastEnabled() {
        return preferences.getBoolean(KEY_HIGH_CONTRAST_ENABLED, false);
    }
    
    public void salvarUsuario(String userId, String email, String emailResponsavel) {
        preferences.edit()
                .putString(KEY_USER_ID, userId)
                .putString(KEY_USER_EMAIL, email)
                .putString(KEY_RESPONSIBLE_EMAIL, emailResponsavel)
                .apply();
    }
    
    public String getUserId() {
        return preferences.getString(KEY_USER_ID, null);
    }
    
    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, null);
    }

    public String getResponsibleEmail() {
        return preferences.getString(KEY_RESPONSIBLE_EMAIL, null);
    }

    public void setResponsibleEmail(String email) {
        preferences.edit().putString(KEY_RESPONSIBLE_EMAIL, email).apply();
    }

    public boolean isNotificationsEnabled() {
        return preferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }

    public boolean isHighContrastEnabled() {
        return preferences.getBoolean(KEY_HIGH_CONTRAST_ENABLED, false);
    }
}
