@java.lang.Override
public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
    contentView = inflater.inflate(R.layout.activity_fragment, null);
    typeface = provider.getTypeface();
    words = provider.getWords(verse);
    stefankmitph.hiot.FlowLayout layout = new stefankmitph.hiot.FlowLayout(context, null);
    layout.setLayoutParams(new stefankmitph.hiot.FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.WRAP_CONTENT));
    layout.setPadding(10, 10, 10, 10);
    java.util.List<stefankmitph.model.Word> words = provider.getWords(verse);
    android.view.ViewGroup _CVAR3 = _CVAR0;
    android.content.Context context = _CVAR3.getContext();
    android.view.ViewGroup _CVAR0 = container;
    android.view.ViewGroup _CVAR6 = _CVAR0;
    android.content.Context context = _CVAR6.getContext();
    android.view.ViewGroup _CVAR9 = _CVAR6;
    android.content.Context context = _CVAR9.getContext();
    if (words == null) {
        android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(context);
        linearLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
        linearLayout.setPadding(32, 32, 32, 32);
        linearLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        android.content.Context _CVAR1 = context;
        android.widget.TextView textViewErrorText = new android.widget.TextView(_CVAR1);
        android.widget.TextView _CVAR2 = textViewErrorText;
        android.content.Context _CVAR4 = context;
        int _CVAR5 = android.R.style.TextAppearance_Medium;
        _CVAR2.setTextAppearance(_CVAR4, _CVAR5);
        textViewErrorText.setText(context.getString(R.string.missing_verse_text));
        // textViewErrorLink.setText(context.getString(R.string.missing_verse_link));
        textViewErrorLink.setText(android.text.Html.fromHtml("For more information <a href='http://en.wikipedia.org/wiki/List_of_Bible_verses_not_included_in_modern_translations'>this link</a>"));
        android.content.Context _CVAR7 = context;
        android.widget.TextView textViewErrorLink = new android.widget.TextView(_CVAR7);
        android.widget.TextView _CVAR8 = textViewErrorLink;
        android.content.Context _CVAR10 = context;
        int _CVAR11 = android.R.style.TextAppearance_Medium;
        _CVAR8.setTextAppearance(_CVAR10, _CVAR11);
        textViewErrorLink.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        linearLayout.addView(textViewErrorText);
        linearLayout.addView(textViewErrorLink);
        ((android.view.ViewGroup) (contentView)).addView(linearLayout);
    } else {
        int count = words.size();
        for (int i = 0; i < count; i++) {
            android.widget.LinearLayout linearLayout = getLayout(context, i);
            layout.addView(linearLayout);
        }
        ((android.view.ViewGroup) (contentView)).addView(layout);
    }
    return contentView;// super.onCreateView(inflater, container, savedInstanceState);

}