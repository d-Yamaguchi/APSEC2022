@android.annotation.SuppressLint("NewApi")
@java.lang.Override
java.util.ArrayList<com.nemustech.study.sysinfo.InfoItem> getItems() {
    if (null == com.nemustech.study.sysinfo.DrmInfoProvider.sDrmItems) {
        com.nemustech.study.sysinfo.DrmInfoProvider.sDrmItems = new java.util.ArrayList<com.nemustech.study.sysinfo.InfoItem>();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            com.nemustech.study.sysinfo.DrmInfoProvider.sDrmItems.add(new com.nemustech.study.sysinfo.InfoItem(getString(R.string.item_drm), getString(R.string.sdk_version_required, android.os.Build.VERSION_CODES.HONEYCOMB)));
        } else {
            java.lang.String[] engines = dmc.getAvailableDrmEngines();
            if ((null == engines) || (0 == engines.length)) {
                com.nemustech.study.sysinfo.DrmInfoProvider.sDrmItems.add(new com.nemustech.study.sysinfo.InfoItem(getString(R.string.item_drm), getString(R.string.drm_none)));
            } else {
                java.lang.StringBuffer sb = new java.lang.StringBuffer();
                sb.append("- ").append(engines[0]);
                for (int idx = 1; idx < engines.length; ++idx) {
                    sb.append("\n- ").append(engines[idx]);
                }
                com.nemustech.study.sysinfo.DrmInfoProvider.sDrmItems.add(new com.nemustech.study.sysinfo.InfoItem(getString(R.string.item_drm), sb.toString()));
            }
            com.nemustech.study.sysinfo.DrmInfoProvider _CVAR0 = mContext;
            android.drm.DrmManagerClient dmc = new android.drm.DrmManagerClient(_CVAR0);
            if (android.os.Build.VERSION_CODES.JELLY_BEAN <= android.os.Build.VERSION.SDK_INT) {
                android.drm.DrmManagerClient _CVAR1 = dmc;
                _CVAR1.release();
            }
        }
    }
    return com.nemustech.study.sysinfo.DrmInfoProvider.sDrmItems;
}