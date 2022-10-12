package stefankmitph.hiot;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import stefankmitph.model.DatabaseManager;
import stefankmitph.model.PosParser;
import stefankmitph.model.Word;
public class VerseFragment extends android.support.v4.app.Fragment {
    private java.lang.String book;

    private int chapter;

    private int verse;

    private java.util.List<stefankmitph.model.Word> words;

    private android.graphics.Typeface typeface;

    private stefankmitph.hiot.ActivityObjectProvider provider;

    private android.view.View contentView;

    @java.lang.Override
    public void onActivityCreated(android.os.Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static stefankmitph.hiot.VerseFragment newInstance(java.lang.String book, int chapter, int verse) {
        stefankmitph.hiot.VerseFragment fragment = new stefankmitph.hiot.VerseFragment();
        fragment.setRetainInstance(true);
        android.os.Bundle args = new android.os.Bundle();
        args.putString("book", book);
        args.putInt("chapter", chapter);
        args.putInt("verse", verse);
        fragment.setArguments(args);
        return fragment;
    }

    @java.lang.Override
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        try {
            provider = ((stefankmitph.hiot.ActivityObjectProvider) (activity));
        } catch (java.lang.ClassCastException e) {
            throw new java.lang.RuntimeException("it ain't a Provider");
        }
    }

    @java.lang.Override
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        book = getArguments().getString("book");
        chapter = getArguments().getInt("chapter");
        verse = getArguments().getInt("verse");
    }

    @java.lang.Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            android.support.v4.app.FragmentActivity activity = getActivity();
            if (activity != null) {
                ((stefankmitph.hiot.MainActivity) (activity)).setActionBarTitle(java.lang.String.format("%s %d:%d", book, chapter, verse));
            }
        }
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.activity_fragment, null);
        typeface = provider.getTypeface();
        words = provider.getWords(verse);
        stefankmitph.hiot.FlowLayout layout = new stefankmitph.hiot.FlowLayout(context, null);
        layout.setLayoutParams(new stefankmitph.hiot.FlowLayout.LayoutParams(FlowLayout.LayoutParams.MATCH_PARENT, FlowLayout.LayoutParams.WRAP_CONTENT));
        layout.setPadding(10, 10, 10, 10);
        java.util.List<stefankmitph.model.Word> words = provider.getWords(verse);
        android.content.Context context = container.getContext();
        android.content.Context context = container.getContext();
        android.content.Context context = container.getContext();
        if (words == null) {
            android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(context);
            linearLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
            linearLayout.setPadding(32, 32, 32, 32);
            linearLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            android.widget.TextView textViewErrorText = new android.widget.TextView(context);
            TextView textView = new android.widget.TextView(context);
            textView.setTextAppearance(chapter);
            textViewErrorText.setText(context.getString(R.string.missing_verse_text));
            // textViewErrorLink.setText(context.getString(R.string.missing_verse_link));
            textViewErrorLink.setText(android.text.Html.fromHtml("For more information <a href='http://en.wikipedia.org/wiki/List_of_Bible_verses_not_included_in_modern_translations'>this link</a>"));
            android.widget.TextView textViewErrorLink = new android.widget.TextView(context);
            textViewErrorLink.setTextAppearance(context, android.R.style.TextAppearance_Medium);
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

    private android.widget.LinearLayout getLayout(android.content.Context context, int index) {
        android.os.Bundle prefs = provider.getPreferences();
        android.widget.LinearLayout linearLayout = new android.widget.LinearLayout(context);
        linearLayout.setPadding(10, 20, 30, 40);
        linearLayout.setOrientation(android.widget.LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
        final android.widget.TextView textViewStrongs = new android.widget.TextView(context);
        textViewStrongs.setTag("textViewStrongs" + index);
        textViewStrongs.setTextAppearance(context, android.R.style.TextAppearance_Small);
        textViewStrongs.setGravity(android.view.Gravity.RIGHT);
        textViewStrongs.setTextColor(android.graphics.Color.rgb(77, 179, 179));
        textViewStrongs.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                java.lang.String text = textViewStrongs.getText().toString();
                java.lang.String[] parts = text.split("\\&");
                stefankmitph.model.DatabaseManager manager = stefankmitph.model.DatabaseManager.getInstance();
                java.lang.String strongs = manager.getStrongs(parts);
                android.widget.Toast toast = android.widget.Toast.makeText(v.getContext(), strongs, android.widget.Toast.LENGTH_LONG);
                toast.show();
            }
        });
        java.lang.String fontSizeWord = prefs.getString("font_size_word", "2");
        int fontResId = 0;
        switch (fontSizeWord) {
            case "0" :
                // small
                fontResId = android.R.style.TextAppearance_Small;
                break;
            case "1" :
                // medium
                fontResId = android.R.style.TextAppearance_Medium;
                break;
            case "2" :
                // medium
                fontResId = android.R.style.TextAppearance_Large;
                break;
        }
        android.widget.TextView textViewWord = new android.widget.TextView(context);
        textViewWord.setTextAppearance(context, fontResId);
        textViewWord.setTypeface(typeface);
        textViewWord.setTextColor(android.graphics.Color.rgb(61, 76, 83));
        textViewWord.setTag("textViewWord" + index);
        android.widget.TextView textViewConcordance = new android.widget.TextView(context);
        textViewConcordance.setTextAppearance(context, android.R.style.TextAppearance_Holo_Small);
        textViewConcordance.setGravity(android.view.Gravity.RIGHT);
        textViewConcordance.setTextColor(android.graphics.Color.rgb(230, 74, 69));
        textViewConcordance.setTag("textViewConcordance" + index);
        android.widget.TextView textViewTransliteration = new android.widget.TextView(context);
        textViewTransliteration.setTextAppearance(context, android.R.style.TextAppearance_Small);
        textViewTransliteration.setTextColor(android.graphics.Color.rgb(77, 179, 179));
        textViewTransliteration.setGravity(android.view.Gravity.RIGHT);
        textViewTransliteration.setTag("textViewTransliteration" + index);
        /* textViewTransliteration.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        String result = PosParser.get((String) ((TextView) v).getTag());

        Toast toast = Toast.makeText(v.getContext(), result, Toast.LENGTH_LONG);
        toast.show();
        }
        });
         */
        android.widget.TextView textViewLemma = new android.widget.TextView(context);
        textViewLemma.setTextAppearance(context, android.R.style.TextAppearance_Small);
        textViewLemma.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        textViewLemma.setGravity(android.view.Gravity.RIGHT);
        textViewLemma.setTag("textViewLemma" + index);
        textViewStrongs.setText(words.get(index).getStrongs());
        textViewWord.setText(words.get(index).getWord());
        textViewTransliteration.setText(words.get(index).getTranslit());
        textViewConcordance.setText(words.get(index).getConcordance());
        textViewLemma.setText(words.get(index).getLemma());
        boolean showStrongs = prefs.getBoolean("show_strongs", false);
        if (showStrongs)
            linearLayout.addView(textViewStrongs);

        linearLayout.addView(textViewWord);
        boolean showConcordance = prefs.getBoolean("show_concordance", false);
        if (showConcordance)
            linearLayout.addView(textViewConcordance);

        boolean showFunctional = prefs.getBoolean("show_transliteration", false);
        if (showFunctional)
            linearLayout.addView(textViewTransliteration);

        boolean showLemma = prefs.getBoolean("show_lemma", false);
        if (showLemma)
            linearLayout.addView(textViewLemma);

        return linearLayout;
    }
}