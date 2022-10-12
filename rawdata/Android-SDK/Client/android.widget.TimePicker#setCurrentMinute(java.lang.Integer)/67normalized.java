public final java.lang.Object m12905a(android.view.ViewGroup viewGroup, final int i) {
    android.view.View view = this.f12926b[i];
    if (view == null) {
        android.view.View view2;
        if (((java.lang.Integer) (com.facebook.uicontrib.datetimepicker.DateTimePickerPagerAdapter.f12925a.get(i))).intValue() == 1) {
            view2 = ((android.widget.FrameLayout) (android.view.LayoutInflater.from(viewGroup.getContext()).inflate(2130907429, viewGroup, false)));
            this.f12929e = ((android.widget.TimePicker) (view2.findViewById(2131567904)));
            java.util.Calendar _CVAR1 = this.f12932h;
            int _CVAR2 = 12;
            int _CVAR3 = _CVAR1.get(_CVAR2);
            android.widget.TimePicker _CVAR0 = this.f12929e;
            java.lang.Integer _CVAR4 = java.lang.Integer.valueOf(_CVAR3);
            _CVAR0.setCurrentMinute(_CVAR4);
            this.f12929e.setCurrentHour(java.lang.Integer.valueOf(this.f12932h.get(10)));
            this.f12929e.setOnTimeChangedListener(new android.widget.TimePicker.OnTimeChangedListener(this) {
                /* synthetic */
                final com.facebook.uicontrib.datetimepicker.DateTimePickerPagerAdapter f12922b;

                public void onTimeChanged(android.widget.TimePicker timePicker, int i, int i2) {
                    this.f12922b.f12932h.set(11, i);
                    this.f12922b.f12932h.set(12, i2);
                    this.f12922b.m12906a(i, this.f12922b.f12927c.format(this.f12922b.f12932h.getTime()));
                }
            });
        } else {
            android.widget.FrameLayout frameLayout = ((android.widget.FrameLayout) (android.view.LayoutInflater.from(viewGroup.getContext()).inflate(2130903843, viewGroup, false)));
            this.f12930f = ((android.widget.DatePicker) (frameLayout.findViewById(2131560887)));
            this.f12930f.init(this.f12932h.get(1), this.f12932h.get(2), this.f12932h.get(5), new android.widget.DatePicker.OnDateChangedListener(this) {
                /* synthetic */
                final com.facebook.uicontrib.datetimepicker.DateTimePickerPagerAdapter f12924b;

                public void onDateChanged(android.widget.DatePicker datePicker, int i, int i2, int i3) {
                    this.f12924b.f12932h.set(i, i2, i3);
                    this.f12924b.m12906a(i, this.f12924b.f12928d.format(this.f12924b.f12932h.getTime()));
                }
            });
        }
        view = view2;
        this.f12926b[i] = view;
    }
    viewGroup.addView(view);
    return view;
}