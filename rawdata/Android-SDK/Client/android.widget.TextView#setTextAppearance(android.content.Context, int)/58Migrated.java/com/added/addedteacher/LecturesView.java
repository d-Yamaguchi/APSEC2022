package com.added.addedteacher;
import android.support.annotation.Nullable;
import com.added.addedteacher.database.MyDatabase;
import model.LectureTopicModel;
public class LecturesView extends android.app.Fragment implements android.widget.TabHost.TabContentFactory {
    com.added.addedteacher.database.MyDatabase db;

    android.content.Context context;

    int[] count;

    java.util.ArrayList<model.LectureTopicModel> topics;

    int ch_id;

    int total_lectures;

    private java.lang.String activity;

    @android.support.annotation.Nullable
    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View v = inflater.inflate(R.layout.view_lectures, container, false);
        return v;
    }

    /**
     * Called when the activity is first created.
     */
    @java.lang.Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();
        android.os.Bundle bundle = this.getArguments();
        ch_id = bundle.getInt("ch_id");
        db = com.added.addedteacher.database.MyDatabase.getDatabaseInstance(context);
        topics = db.getTopicInLecture(ch_id);
        total_lectures = bundle.getInt("total_lectures");
        final android.widget.TabHost tabHost = ((android.widget.TabHost) (getView().findViewById(R.id.tabHost)));
        tabHost.setup();
        android.content.res.Resources res = getResources();
        android.content.res.Configuration cfg = res.getConfiguration();
        boolean hor = cfg.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE;
        count = new int[total_lectures];
        for (int i = 0; i < total_lectures; i++) {
            int k = 0;
            for (int j = 0; j < LectureTopicModel.lecture_topic_content.size(); j++) {
                if (LectureTopicModel.lecture_topic_content.get(j).lect_no == (i + 1)) {
                    k++;
                }
            }
            count[i] = k;
        }
        if (hor) {
            android.widget.TabWidget tw = tabHost.getTabWidget();
            tw.setOrientation(android.widget.LinearLayout.VERTICAL);
        }
        for (int i = 0; i < total_lectures; i++) {
            tabHost.addTab(tabHost.newTabSpec("" + i).setIndicator(createIndicatorView(tabHost, "Lecture " + (i + 1))).setContent(this));
        }
    }

    private android.view.View createIndicatorView(android.widget.TabHost tabHost, java.lang.CharSequence label) {
        android.view.LayoutInflater inflater = ((android.view.LayoutInflater) (context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE)));
        android.view.View tabIndicator = // tab widget is the parent
        inflater.inflate(R.layout.tab_indicator, tabHost.getTabWidget(), false);// no inflate params

        final android.widget.TextView tv = ((android.widget.TextView) (tabIndicator.findViewById(R.id.title)));
        tv.setText(label);
        return tabIndicator;
    }

    @java.lang.Override
    public android.view.View createTabContent(java.lang.String tag) {
        int c = java.lang.Integer.parseInt(tag);
        android.widget.ScrollView parentScrollView = new android.widget.ScrollView(context);
        android.widget.LinearLayout.LayoutParams parentScrollViewLayoutParams = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        parentScrollView.setLayoutParams(parentScrollViewLayoutParams);
        android.widget.LinearLayout parentLinearLayout1 = new android.widget.LinearLayout(context);
        android.widget.LinearLayout.LayoutParams parentLayout1Params = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
        parentLinearLayout1.setLayoutParams(parentLayout1Params);
        parentLinearLayout1.setOrientation(android.widget.LinearLayout.VERTICAL);
        java.lang.String topic;
        java.lang.String objective;
        java.lang.String introduction;
        java.lang.String questions;
        java.lang.String textassignment;
        /* for(int i=0;i<count[c];i++)
        {
         */
        for (int j = 0; j < LectureTopicModel.lecture_topic_content.size(); j++) {
            if (LectureTopicModel.lecture_topic_content.get(j).lect_no == (c + 1)) {
                topic = LectureTopicModel.lecture_topic_content.get(j).topic;
                objective = LectureTopicModel.lecture_topic_content.get(j).objective;
                introduction = LectureTopicModel.lecture_topic_content.get(j).intro;
                questions = LectureTopicModel.lecture_topic_content.get(j).quest;
                activity = LectureTopicModel.lecture_topic_content.get(j).activity;
                textassignment = LectureTopicModel.lecture_topic_content.get(j).assignment;
                android.widget.LinearLayout parentLinearLayout = getInsight(topic, objective, introduction, questions, textassignment);
                parentLinearLayout1.addView(parentLinearLayout);
            }
        }
        /* } */
        parentScrollView.addView(parentLinearLayout1);
        return parentScrollView;
    }

    private android.widget.LinearLayout getInsight(java.lang.String topic, java.lang.String objective, java.lang.String introduction, java.lang.String questions, java.lang.String textassignment) {
        // TODO Auto-generated method stub
        android.widget.LinearLayout parentLinearLayout = new android.widget.LinearLayout(context);
        android.widget.LinearLayout.LayoutParams parentLayoutParams = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
        parentLinearLayout.setLayoutParams(parentLayoutParams);
        parentLinearLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
        android.widget.TextView topicTextView = new android.widget.TextView(context);
        TextView textView = new android.widget.TextView(context);
        textView.setTextAppearance(ch_id);
        topicTextView.setTextColor(android.graphics.Color.parseColor("#691A99"));
        topicTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        topicTextView.setText(topic);
        topicTextView.setTextSize(23);
        android.widget.LinearLayout.LayoutParams topicLayoutParams = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
        topicLayoutParams.setMargins(0, 3, 0, 5);
        android.widget.TextView objectiveTextView = new android.widget.TextView(context);
        objectiveTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        objectiveTextView.setTextColor(android.graphics.Color.parseColor("#8E24AA"));
        objectiveTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        objectiveTextView.setText("Objective");
        android.widget.TextView objectiveContentTextView = new android.widget.TextView(context);
        objectiveContentTextView.setText(objective);
        objectiveContentTextView.setTextColor(android.graphics.Color.parseColor("#000000"));
        objectiveContentTextView.setLayoutParams(topicLayoutParams);
        android.widget.TextView introductionTextView = new android.widget.TextView(context);
        introductionTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        introductionTextView.setText("Introduction");
        introductionTextView.setTextColor(android.graphics.Color.parseColor("#8E24AA"));
        introductionTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        android.widget.TextView introContentTextView = new android.widget.TextView(context);
        introContentTextView.setText(introduction);
        introContentTextView.setTextColor(android.graphics.Color.parseColor("#000000"));
        introContentTextView.setLayoutParams(topicLayoutParams);
        android.widget.TextView questionsTextView = new android.widget.TextView(context);
        questionsTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        questionsTextView.setText("Questions");
        questionsTextView.setTextColor(android.graphics.Color.parseColor("#8E24AA"));
        questionsTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        android.widget.TextView questionsContentTextView = new android.widget.TextView(context);
        questionsContentTextView.setText(questions);
        questionsContentTextView.setTextColor(android.graphics.Color.parseColor("#000000"));
        questionsContentTextView.setLayoutParams(topicLayoutParams);
        android.widget.TextView activityTextView = new android.widget.TextView(context);
        activityTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        activityTextView.setText("Activity");
        activityTextView.setTextColor(android.graphics.Color.parseColor("#8E24AA"));
        activityTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        android.widget.TextView activityContentTextView = new android.widget.TextView(context);
        activityContentTextView.setText(activity);
        activityContentTextView.setTextColor(android.graphics.Color.parseColor("#000000"));
        activityContentTextView.setLayoutParams(topicLayoutParams);
        android.widget.TextView testAssignmentTextView = new android.widget.TextView(context);
        testAssignmentTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        testAssignmentTextView.setText("Test/Assignment");
        testAssignmentTextView.setTextColor(android.graphics.Color.parseColor("#8E24AA"));
        testAssignmentTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        android.widget.TextView testAssignmentContentTextView = new android.widget.TextView(context);
        testAssignmentContentTextView.setText(textassignment);
        testAssignmentContentTextView.setTextColor(android.graphics.Color.parseColor("#000000"));
        testAssignmentContentTextView.setLayoutParams(topicLayoutParams);
        android.view.View v = new android.view.View(context);
        android.widget.LinearLayout.LayoutParams viewLayoutParams = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 3);
        v.setLayoutParams(viewLayoutParams);
        v.setBackgroundColor(android.graphics.Color.parseColor("#000000"));
        /* TextView durationTextView=new TextView(context);
        durationTextView.setTextAppearance(context, android.R.style.TextAppearance_Medium);
        durationTextView.setText("Duration");
        TextView durationContentTextView=new TextView(context);
        durationContentTextView.setText(duration);
        durationContentTextView.setLayoutParams(topicLayoutParams);
         */
        parentLinearLayout.addView(topicTextView);
        parentLinearLayout.addView(objectiveTextView);
        parentLinearLayout.addView(objectiveContentTextView);
        parentLinearLayout.addView(introductionTextView);
        parentLinearLayout.addView(introContentTextView);
        parentLinearLayout.addView(questionsTextView);
        parentLinearLayout.addView(questionsContentTextView);
        parentLinearLayout.addView(activityTextView);
        parentLinearLayout.addView(activityContentTextView);
        parentLinearLayout.addView(testAssignmentTextView);
        parentLinearLayout.addView(testAssignmentContentTextView);
        parentLinearLayout.addView(v);
        /* parentLinearLayout.addView(durationTextView);
        parentLinearLayout.addView(durationContentTextView);
         */
        return parentLinearLayout;
    }
}