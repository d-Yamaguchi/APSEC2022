package com.android.settings.divxdrm;
import com.android.settings.R;
public class DivXDRMRegisterFragment extends android.app.Fragment implements android.view.View.OnClickListener {
    private static final java.lang.String TAG = "DivXDRMRegisterFragment";

    android.content.Context mcontext;

    private android.widget.TextView mRegisterTip = null;

    private android.widget.Button mOK = null;

    private android.view.View mView = null;

    public android.drm.DrmManagerClient mDrmManagerClient = null;

    private boolean mNeedReleaseDrmManagerClient = false;

    public DivXDRMRegisterFragment(android.drm.DrmManagerClient Client) {
        if (mDrmManagerClient == null) {
            android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " Constructor -- set mDrmManagerClient to be Client\r\n");
            mDrmManagerClient = Client;
            mNeedReleaseDrmManagerClient = false;
        }
    }

    public void onCreate(android.os.Bundle savedInstanceState) {
        android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " onCreate\r\n");
        super.onCreate(savedInstanceState);
        mcontext = getActivity();
        if (mDrmManagerClient == null) {
            mDrmManagerClient = new android.drm.DrmManagerClient(mcontext);
            android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " onCreate -- mDrmManagerClient\r\n");
            mNeedReleaseDrmManagerClient = true;
        }
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " onCreateView\r\n");
        mView = inflater.inflate(R.layout.drm_divx_register, null);
        if (savedInstanceState == null) {
            android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " onCreateView -- savedInstanceState == null\r\n");
            mRegisterTip = ((android.widget.TextView) (mView.findViewById(R.id.tDrmDivxRegTip)));
            java.lang.String mRegTipPrefixStr = getResources().getString(R.string.drm_divx_register_tip);
            java.lang.String mRegCodeTipStr = getResources().getString(R.string.drm_divx_register_code);
            java.lang.String mRegWebTipStr = getResources().getString(R.string.drm_divx_register_web);
            if (mDrmManagerClient != null) {
                android.drm.DrmInfoRequest drmInfoReq = new android.drm.DrmInfoRequest(android.drm.DrmInfoRequest.TYPE_REGISTRATION_INFO, "video/avi");
                android.drm.DrmInfo mdrmInfo = mDrmManagerClient.acquireDrmInfo(drmInfoReq);
                if ((mdrmInfo != null) && (mdrmInfo.getData() != null)) {
                    byte[] drmRegcode = mdrmInfo.getData();
                    java.lang.String mRegCodeStr = new java.lang.String(drmRegcode);
                    mRegisterTip.setText((((mRegTipPrefixStr + mRegCodeTipStr) + mRegCodeStr) + "\r\n") + mRegWebTipStr);
                } else {
                    mRegisterTip.setText(((mRegTipPrefixStr + mRegCodeTipStr) + " \r\n") + mRegWebTipStr);
                }
            } else {
                mRegisterTip.setText(((mRegTipPrefixStr + mRegCodeTipStr) + " \r\n") + mRegWebTipStr);
            }
            mOK = ((android.widget.Button) (mView.findViewById(R.id.bOK)));
            mOK.setOnClickListener(this);
        } else {
            android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " onCreateView -- savedInstanceState != null\r\n");
        }
        return mView;
    }

    @java.lang.Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @java.lang.Override
    public void onDestroy() {
        android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, "onDestroy -- release mDrmManagerClient!!!");
        if ((mDrmManagerClient != null) && mNeedReleaseDrmManagerClient) {
            Cursor contactsCursor = null;
            contactsCursor.close();
        }
        android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, "onDestroy exit!!!");
        // TODO Auto-generated method stub
        __SmPLUnsupported__(0).onDestroy();
    }

    public void onClick(android.view.View v) {
        switch (v.getId()) {
            case R.id.bOK :
                android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " Click OK Button!!!");
                com.android.settings.divxdrm.DivXDRMSettingFragment ParentFragment = ((com.android.settings.divxdrm.DivXDRMSettingFragment) (getParentFragment()));
                if (ParentFragment == null) {
                    android.util.Log.e(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " divxdrm device -- no parent fragment");
                    getActivity().finish();
                } else {
                    android.util.Log.i(com.android.settings.divxdrm.DivXDRMRegisterFragment.TAG, " ParentFragment.BackToDrmSettingMainFragment()!!!");
                    ParentFragment.BackToDrmSettingMainFragment();
                }
                break;
            default :
                break;
        }
    }
}