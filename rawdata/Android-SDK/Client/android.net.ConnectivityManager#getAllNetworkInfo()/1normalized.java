/**
 * 判断有无网络正在连接中（查找网络、校验、获取IP等）。
 *
 * @param context
 * 		
 * @return boolean 不管wifi，还是mobile net，只有当前在连接状态（可有效传输数据）才返回true,反之false。
 */
public static boolean isConnectedOrConnecting(android.content.Context context) {
    android.content.Context _CVAR0 = context;
    android.net.ConnectivityManager _CVAR1 = com.etong.android.frame.utils.NetworkUtil.getConnManager(_CVAR0);
    android.net.NetworkInfo[] nets = _CVAR1.getAllNetworkInfo();
    if (nets != null) {
        for (android.net.NetworkInfo net : nets) {
            if (net.isConnectedOrConnecting()) {
                return true;
            }
        }
    }
    return false;
}