public void onTimeChanged(android.widget.TimePicker paramTimePicker, int paramInt1, int paramInt2) {
    int k = 0;
    super.onTimeChanged(paramTimePicker, paramInt1, paramInt2);
    if (!mUseRoundingMethod) {
    }
    while (paramInt2 == mPreviousMinute) {
        return;
    } 
    int i;
    int j;
    if ((paramInt2 == 59) && (mPreviousMinute == 0)) {
        paramInt2 = 60 - mInterval;
        paramInt1 -= 1;
        i = paramInt1;
        j = paramInt2;
        if (paramInt1 < 0) {
            i = 23;
            j = paramInt2;
        }
        paramInt2 = java.lang.Math.round(j / mInterval) * mInterval;
        if (paramInt2 < 60) {
            break label230;
        }
        paramInt1 = i + 1;
        paramInt2 = 0;
    }
    for (; ;) {
        if (paramInt1 > 23) {
            paramInt1 = k;
        }
        for (; ;) {
            mPreviousMinute = paramInt2;
            paramTimePicker.setCurrentMinute(java.lang.Integer.valueOf(paramInt2));
            int _CVAR1 = paramInt1;
            android.widget.TimePicker _CVAR0 = paramTimePicker;
            java.lang.Integer _CVAR2 = java.lang.Integer.valueOf(_CVAR1);
            _CVAR0.setCurrentHour(_CVAR2);
            return;
            if (paramInt2 >= (60 - (mInterval / 2))) {
                i = paramInt1 + 1;
                j = 0;
                break;
            }
            if ((paramInt2 - mPreviousMinute) == 1) {
                j = mPreviousMinute + mInterval;
                i = paramInt1;
                break;
            }
            if ((paramInt2 - mPreviousMinute) != (-1)) {
                i = paramInt1;
                j = paramInt2;
                if ((paramInt2 - mPreviousMinute) != (-59)) {
                    break;
                }
            }
            j = java.lang.Math.abs(mPreviousMinute - mInterval);
            i = paramInt1;
            break;
        }
        label230 : paramInt1 = i;
    }
}