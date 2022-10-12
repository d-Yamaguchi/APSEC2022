package jv.roundemlon.mobiletower;
public class MainActivity extends android.app.Activity {
    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.widget.TextView textGsmCellLocation = ((android.widget.TextView) (findViewById(R.id.gsmcelllocation)));
        android.widget.TextView textCID = ((android.widget.TextView) (findViewById(R.id.cid)));
        android.widget.TextView textLAC = ((android.widget.TextView) (findViewById(R.id.lac)));
        android.widget.TextView textDeviceID = ((android.widget.TextView) (findViewById(R.id.deviceid)));
        android.widget.TextView textNetworkOperator = ((android.widget.TextView) (findViewById(R.id.networkoperator)));
        android.widget.TextView textNetworkOperatorName = ((android.widget.TextView) (findViewById(R.id.networkoperatorname)));
        android.widget.TextView textNetworkType = ((android.widget.TextView) (findViewById(R.id.networktype)));
        android.widget.TextView textNetworkCountryIso = ((android.widget.TextView) (findViewById(R.id.networkcountryiso)));
        // retrieve a reference to an instance of TelephonyManager
        android.telephony.TelephonyManager telephonyManager = ((android.telephony.TelephonyManager) (getSystemService(android.content.Context.TELEPHONY_SERVICE)));
        textDeviceID.setText(getDeviceID(telephonyManager));
        textNetworkOperator.setText("Network Operator(MCC+MNC): " + telephonyManager.getNetworkOperator());
        textNetworkOperatorName.setText("Network Operator Name: " + telephonyManager.getNetworkOperatorName());
        textNetworkType.setText("Network Type: " + getNetworkType(telephonyManager));
        textNetworkCountryIso.setText("Network Country Iso:" + telephonyManager.getNetworkCountryIso());
        // remove comment when removing the nnetwork provided code
        // TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        // try{
        android.telephony.gsm.GsmCellLocation cellLocation = ((android.telephony.gsm.GsmCellLocation) (telephonyManager.getCellLocation()));
        // }catch (Exception e){
        // Log.d("Debud","Error");
        // }
        int cid = cellLocation.getCid();
        int lac = cellLocation.getLac();
        textGsmCellLocation.setText(cellLocation.toString());
        textCID.setText("gsm cell id: " + java.lang.String.valueOf(cid));
        textLAC.setText("gsm location area code: " + java.lang.String.valueOf(lac));
        android.util.Log.d("CellLocation", cellLocation.toString());
        android.util.Log.d("GSM CELL ID", java.lang.String.valueOf(cid));
        android.util.Log.d("GSM Location Code", java.lang.String.valueOf(lac));
    }

    java.lang.String getNetworkType(android.telephony.TelephonyManager phonyManager) {
        int type = phonyManager.getNetworkType();
        switch (type) {
            case android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN :
                return "NETWORK_TYPE_UNKNOWN";
            case android.telephony.TelephonyManager.NETWORK_TYPE_GPRS :
                return "NETWORK_TYPE_GPRS";
            case android.telephony.TelephonyManager.NETWORK_TYPE_EDGE :
                return "NETWORK_TYPE_EDGE";
            case android.telephony.TelephonyManager.NETWORK_TYPE_UMTS :
                return "NETWORK_TYPE_UMTS";
            case android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA :
                return "NETWORK_TYPE_HSDPA";
            case android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA :
                return "NETWORK_TYPE_HSUPA";
            case android.telephony.TelephonyManager.NETWORK_TYPE_HSPA :
                return "NETWORK_TYPE_HSPA";
            case android.telephony.TelephonyManager.NETWORK_TYPE_CDMA :
                return "NETWORK_TYPE_CDMA";
            case android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0 :
                return "NETWORK_TYPE_EVDO_0";
            case android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A :
                return "NETWORK_TYPE_EVDO_0";
                /* Since: API Level 9
                 case TelephonyManager.NETWORK_TYPE_EVDO_B:
                 return "NETWORK_TYPE_EVDO_B";
                 */
            case android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT :
                return "NETWORK_TYPE_1xRTT";
            case android.telephony.TelephonyManager.NETWORK_TYPE_IDEN :
                return "NETWORK_TYPE_IDEN";
                /* Since: API Level 11
                 case TelephonyManager.NETWORK_TYPE_LTE:
                return "NETWORK_TYPE_LTE";
                 */
                /* Since: API Level 11
                 case TelephonyManager.NETWORK_TYPE_EHRPD:
                 return "NETWORK_TYPE_EHRPD";
                 */
            default :
                return "unknown";
        }
    }

    java.lang.String getDeviceID(android.telephony.TelephonyManager phonyManager) {
        java.lang.String id = phonyManager.getImei();
        if (id == null) {
            id = "not available";
        }
        int phoneType = phonyManager.getPhoneType();
        __SmPLUnsupported__(0);
    }
}