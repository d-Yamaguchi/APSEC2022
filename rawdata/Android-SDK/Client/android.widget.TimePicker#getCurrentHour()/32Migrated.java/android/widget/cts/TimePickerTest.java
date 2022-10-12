/* Copyright (C) 2008 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package android.widget.cts;
import android.widget.TimePicker;
import android.test.ActivityInstrumentationTestCase2;
/**
 * Test {@link TimePicker}.
 */
public class TimePickerTest extends android.test.ActivityInstrumentationTestCase2<android.widget.cts.CtsActivity> {
    private android.widget.TimePicker mTimePicker;

    private android.app.Activity mActivity;

    private android.content.Context mContext;

    private android.app.Instrumentation mInstrumentation;

    public TimePickerTest() {
        super("com.android.cts.widget", android.widget.cts.CtsActivity.class);
    }

    @java.lang.Override
    protected void setUp() throws java.lang.Exception {
        super.setUp();
        mInstrumentation = getInstrumentation();
        mContext = mInstrumentation.getTargetContext();
        mActivity = getActivity();
    }

    public void testConstructors() {
        android.util.AttributeSet attrs = mContext.getResources().getLayout(com.android.cts.widget.R.layout.timepicker);
        assertNotNull(attrs);
        new android.widget.TimePicker(mContext);
        try {
            new android.widget.TimePicker(null);
            fail("did not throw NullPointerException when param context is null.");
        } catch (java.lang.NullPointerException e) {
            // expected
        }
        new android.widget.TimePicker(mContext, attrs);
        try {
            new android.widget.TimePicker(null, attrs);
            fail("did not throw NullPointerException when param context is null.");
        } catch (java.lang.NullPointerException e) {
            // expected
        }
        new android.widget.TimePicker(mContext, null);
        new android.widget.TimePicker(mContext, attrs, 0);
        try {
            new android.widget.TimePicker(null, attrs, 0);
            fail("did not throw NullPointerException when param context is null.");
        } catch (java.lang.NullPointerException e) {
            // expected
        }
        new android.widget.TimePicker(mContext, null, 0);
        new android.widget.TimePicker(mContext, attrs, 0);
        new android.widget.TimePicker(mContext, attrs, java.lang.Integer.MIN_VALUE);
    }

    public void testSetEnabled() {
        mTimePicker = new android.widget.TimePicker(mContext);
        assertTrue(mTimePicker.isEnabled());
        mTimePicker.setEnabled(false);
        assertFalse(mTimePicker.isEnabled());
        mTimePicker.setEnabled(true);
        assertTrue(mTimePicker.isEnabled());
    }

    public void testSetOnTimeChangedListener() {
        int initialHour = 13;
        int initialMinute = 50;
        mTimePicker = new android.widget.TimePicker(mContext);
        android.widget.cts.TimePickerTest.MockOnTimeChangeListener listener = new android.widget.cts.TimePickerTest.MockOnTimeChangeListener();
        mTimePicker.setOnTimeChangedListener(listener);
        mTimePicker.setCurrentHour(java.lang.Integer.valueOf(initialHour));
        mTimePicker.setCurrentMinute(java.lang.Integer.valueOf(initialMinute));
        assertEquals(initialHour, listener.getNotifiedHourOfDay());
        assertEquals(initialMinute, listener.getNotifiedMinute());
        // set the same hour as current
        listener.reset();
        mTimePicker.setCurrentHour(java.lang.Integer.valueOf(initialHour));
        assertFalse(listener.hasCalledOnTimeChanged());
        mTimePicker.setCurrentHour(java.lang.Integer.valueOf(initialHour + 1));
        assertTrue(listener.hasCalledOnTimeChanged());
        assertEquals(initialHour + 1, listener.getNotifiedHourOfDay());
        assertEquals(initialMinute, listener.getNotifiedMinute());
        assertSame(mTimePicker, listener.getNotifiedView());
        // set the same minute as current
        listener.reset();
        mTimePicker.setCurrentMinute(initialMinute);
        assertFalse(listener.hasCalledOnTimeChanged());
        listener.reset();
        mTimePicker.setCurrentMinute(initialMinute + 1);
        assertTrue(listener.hasCalledOnTimeChanged());
        assertEquals(initialHour + 1, listener.getNotifiedHourOfDay());
        assertEquals(initialMinute + 1, listener.getNotifiedMinute());
        assertSame(mTimePicker, listener.getNotifiedView());
        // change time picker mode
        listener.reset();
        mTimePicker.setIs24HourView(!mTimePicker.is24HourView());
        assertFalse(listener.hasCalledOnTimeChanged());
    }

    public void testAccessCurrentHour() {
        mTimePicker = new android.widget.TimePicker(mContext);
        // AM/PM mode
        mTimePicker.setIs24HourView(false);
        mTimePicker.setCurrentHour(0);
        assertEquals(java.lang.Integer.valueOf(0), mTimePicker.getHour());
        mTimePicker.setCurrentHour(12);
        assertEquals(java.lang.Integer.valueOf(12), mTimePicker.getHour());
        mTimePicker.setCurrentHour(13);
        assertEquals(java.lang.Integer.valueOf(13), mTimePicker.getHour());
        mTimePicker.setCurrentHour(23);
        assertEquals(java.lang.Integer.valueOf(23), mTimePicker.getHour());
        // for 24 hour mode
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(0);
        assertEquals(java.lang.Integer.valueOf(0), mTimePicker.getHour());
        mTimePicker.setCurrentHour(13);
        assertEquals(java.lang.Integer.valueOf(13), mTimePicker.getHour());
        mTimePicker.setCurrentHour(23);
        assertEquals(java.lang.Integer.valueOf(23), mTimePicker.getHour());
    }

    public void testAccessIs24HourView() {
        mTimePicker = new android.widget.TimePicker(mContext);
        assertFalse(mTimePicker.is24HourView());
        mTimePicker.setIs24HourView(true);
        assertTrue(mTimePicker.is24HourView());
        mTimePicker.setIs24HourView(false);
        assertFalse(mTimePicker.is24HourView());
    }

    public void testAccessCurrentMinute() {
        mTimePicker = new android.widget.TimePicker(mContext);
        mTimePicker.setCurrentMinute(0);
        assertEquals(java.lang.Integer.valueOf(0), mTimePicker.getCurrentMinute());
        mTimePicker.setCurrentMinute(12);
        assertEquals(java.lang.Integer.valueOf(12), mTimePicker.getCurrentMinute());
        mTimePicker.setCurrentMinute(33);
        assertEquals(java.lang.Integer.valueOf(33), mTimePicker.getCurrentMinute());
        mTimePicker.setCurrentMinute(59);
        assertEquals(java.lang.Integer.valueOf(59), mTimePicker.getCurrentMinute());
    }

    public void testGetBaseline() {
        mTimePicker = new android.widget.TimePicker(mContext);
        assertEquals(-1, mTimePicker.getBaseline());
    }

    public void testOnSaveInstanceStateAndOnRestoreInstanceState() {
        android.widget.cts.TimePickerTest.MyTimePicker source = new android.widget.cts.TimePickerTest.MyTimePicker(mContext);
        android.widget.cts.TimePickerTest.MyTimePicker dest = new android.widget.cts.TimePickerTest.MyTimePicker(mContext);
        int expectHour = (dest.getCurrentHour() + 10) % 24;
        int expectMinute = (dest.getCurrentMinute() + 10) % 60;
        source.setCurrentHour(expectHour);
        source.setCurrentMinute(expectMinute);
        android.os.Parcelable p = source.onSaveInstanceState();
        dest.onRestoreInstanceState(p);
        assertEquals(java.lang.Integer.valueOf(expectHour), dest.getCurrentHour());
        assertEquals(java.lang.Integer.valueOf(expectMinute), dest.getCurrentMinute());
    }

    private class MockOnTimeChangeListener implements android.widget.TimePicker.OnTimeChangedListener {
        private android.widget.TimePicker mNotifiedView;

        private boolean mHasCalledOnTimeChanged;

        private int mNotifiedHourOfDay;

        private int mNotifiedMinute;

        public boolean hasCalledOnTimeChanged() {
            return mHasCalledOnTimeChanged;
        }

        public android.widget.TimePicker getNotifiedView() {
            return mNotifiedView;
        }

        public int getNotifiedHourOfDay() {
            return mNotifiedHourOfDay;
        }

        public int getNotifiedMinute() {
            return mNotifiedMinute;
        }

        public void reset() {
            mNotifiedView = null;
            mNotifiedHourOfDay = 0;
            mNotifiedMinute = 0;
            mHasCalledOnTimeChanged = false;
        }

        public void onTimeChanged(android.widget.TimePicker view, int hourOfDay, int minute) {
            mNotifiedView = view;
            mNotifiedHourOfDay = hourOfDay;
            mNotifiedMinute = minute;
            mHasCalledOnTimeChanged = true;
        }
    }

    private class MyTimePicker extends android.widget.TimePicker {
        public MyTimePicker(android.content.Context context) {
            super(context);
        }

        @java.lang.Override
        protected void onRestoreInstanceState(android.os.Parcelable state) {
            super.onRestoreInstanceState(state);
        }

        @java.lang.Override
        protected android.os.Parcelable onSaveInstanceState() {
            return super.onSaveInstanceState();
        }
    }
}