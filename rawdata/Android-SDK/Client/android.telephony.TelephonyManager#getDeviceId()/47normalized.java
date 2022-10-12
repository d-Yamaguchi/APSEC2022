public java.lang.String getimei() {
    try {
        android.content.Context _CVAR0 = context;
        java.lang.String _CVAR1 = android.content.Context.TELEPHONY_SERVICE;
        android.telephony.TelephonyManager tm = ((android.telephony.TelephonyManager) (_CVAR0.getSystemService(_CVAR1)));
        android.telephony.TelephonyManager _CVAR2 = tm;
        java.lang.String imei = _CVAR2.getDeviceId();
        java.util.List<java.lang.String> IMEIS = new java.util.ArrayList<java.lang.String>();
        if (checkimei(imei.trim())) {
            IMEIS.add(imei.trim());
        }
        try {
            android.content.Context _CVAR3 = context;
            java.lang.String _CVAR4 = "phone1";
            android.telephony.TelephonyManager telephonyManager1 = ((android.telephony.TelephonyManager) (_CVAR3.getSystemService(_CVAR4)));
            android.telephony.TelephonyManager _CVAR5 = telephonyManager1;
            java.lang.String imeiphone1 = _CVAR5.getDeviceId();
            if ((imeiphone1 != null) && checkimei(imeiphone1)) {
                if (!IMEIS.contains(imeiphone1)) {
                    IMEIS.add(imeiphone1);
                }
            }
        } catch (java.lang.Exception e) {
        }
        try {
            android.content.Context _CVAR6 = context;
            java.lang.String _CVAR7 = "phone2";
            android.telephony.TelephonyManager telephonyManager2 = ((android.telephony.TelephonyManager) (_CVAR6.getSystemService(_CVAR7)));
            android.telephony.TelephonyManager _CVAR8 = telephonyManager2;
            java.lang.String imeiphone2 = _CVAR8.getDeviceId();
            if ((imeiphone2 != null) && checkimei(imeiphone2)) {
                if (!IMEIS.contains(imeiphone2)) {
                    IMEIS.add(imeiphone2);
                }
            }
        } catch (java.lang.Exception e) {
        }
        java.util.List<java.lang.String> imeis = IMEI_initQualcommDoubleSim();
        if ((imeis != null) && (imeis.size() > 0)) {
            for (java.lang.String item : imeis) {
                if (!IMEIS.contains(item)) {
                    IMEIS.add(item);
                }
            }
        }
        imeis = IMEI_initMtkSecondDoubleSim();
        if ((imeis != null) && (imeis.size() > 0)) {
            for (java.lang.String item : imeis) {
                if (!IMEIS.contains(item)) {
                    IMEIS.add(item);
                }
            }
        }
        imeis = IMEI_initMtkDoubleSim();
        if ((imeis != null) && (imeis.size() > 0)) {
            for (java.lang.String item : imeis) {
                if (!IMEIS.contains(item)) {
                    IMEIS.add(item);
                }
            }
        }
        imeis = IMEI_initSpreadDoubleSim();
        if ((imeis != null) && (imeis.size() > 0)) {
            for (java.lang.String item : imeis) {
                if (!IMEIS.contains(item)) {
                    IMEIS.add(item);
                }
            }
        }
        java.lang.StringBuffer IMEI_SB = new java.lang.StringBuffer();
        java.lang.Integer TIMES_TEMP = 1;
        for (java.lang.String item : IMEIS) {
            if (TIMES_TEMP > 1) {
                IMEI_SB.append('\n');
            }
            IMEI_SB.append(item);
            // params.put("IMEI" + TIMES_TEMP, item);
            TIMES_TEMP++;
        }
        java.lang.String imeis_tmp = IMEI_SB.toString().trim();
        if ("".equals(imeis_tmp)) {
            imeis_tmp = "no_imei_1";
        }
        return imeis_tmp;
    } catch (java.lang.Exception e) {
        return "no_imei_2";
    }
}