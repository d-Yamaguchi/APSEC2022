private void populateUI(com.udacity.sandwichclub.model.Sandwich sandwich) {
    java.lang.String mainName = sandwich.getMainName();
    java.lang.String description = sandwich.getDescription();
    java.lang.String placeOfOrigin = sandwich.getPlaceOfOrigin();
    java.util.List<java.lang.String> ingredients = sandwich.getIngredients();
    java.util.List<java.lang.String> alsoKnownAs = sandwich.getAlsoKnownAs();
    void _CVAR0 = R.id.name_tv;
    android.widget.TextView name_tv = findViewById(_CVAR0);
    java.lang.String _CVAR2 = "</font><br>";
    java.lang.String name_tv_Styled = ("<br><font color='#b2b2b2'>" + mainName) + _CVAR2;
    java.lang.String _CVAR3 = name_tv_Styled;
    android.widget.TextView _CVAR1 = name_tv;
    android.text.Spanned _CVAR4 = android.text.Html.fromHtml(_CVAR3);
    _CVAR1.append(_CVAR4);
    void _CVAR5 = R.id.description_tv;
    android.widget.TextView description_tv = findViewById(_CVAR5);
    java.lang.String _CVAR7 = "</font><br>";
    java.lang.String description_tv_Styled = ("<br><font color='#b2b2b2'>" + description) + _CVAR7;
    java.lang.String _CVAR8 = description_tv_Styled;
    android.widget.TextView _CVAR6 = description_tv;
    android.text.Spanned _CVAR9 = android.text.Html.fromHtml(_CVAR8);
    _CVAR6.append(_CVAR9);
    void _CVAR10 = R.id.placeOfOrigin_tv;
    android.widget.TextView placeOfOrigin_tv = findViewById(_CVAR10);
    java.lang.String _CVAR12 = "</font><br>";
    java.lang.String placeOfOrigin_tv_Styled = ("<br><font color='#b2b2b2'>" + placeOfOrigin) + _CVAR12;
    java.lang.String _CVAR13 = placeOfOrigin_tv_Styled;
    android.widget.TextView _CVAR11 = placeOfOrigin_tv;
    android.text.Spanned _CVAR14 = android.text.Html.fromHtml(_CVAR13);
    _CVAR11.append(_CVAR14);
    java.lang.String alsoKnownAsString = "";
    int listLength = alsoKnownAs.size();
    int i = 0;
    for (java.lang.String item : alsoKnownAs) {
        if (i != (listLength - 1)) {
            alsoKnownAsString += item + ", ";
            i++;
        } else {
            alsoKnownAsString += item;
        }
    }
    void _CVAR15 = R.id.alsoKnownAs_tv;
    android.widget.TextView alsoKnownAs_tv = findViewById(_CVAR15);
    java.lang.String _CVAR17 = "</font><br>";
    java.lang.String alsoKnownAsStringStyled = ("<br><font color='#b2b2b2'>" + alsoKnownAsString) + _CVAR17;
    java.lang.String _CVAR18 = alsoKnownAsStringStyled;
    android.widget.TextView _CVAR16 = alsoKnownAs_tv;
    android.text.Spanned _CVAR19 = android.text.Html.fromHtml(_CVAR18);
    _CVAR16.append(_CVAR19);
    java.lang.String ingredientsString = "";
    int ingListLength = ingredients.size();
    int j = 0;
    for (java.lang.String ingred : ingredients) {
        if (j != (ingListLength - 1)) {
            ingredientsString += ingred + ", ";
            j++;
        } else {
            ingredientsString += ingred;
        }
    }
    void _CVAR20 = R.id.ingredients_tv;
    android.widget.TextView Ingredients_tv = findViewById(_CVAR20);
    java.lang.String _CVAR22 = "</font><br>";
    java.lang.String Ingredients_tv_Styled = ("<br><font color='#b2b2b2'>" + ingredientsString) + _CVAR22;
    java.lang.String _CVAR23 = Ingredients_tv_Styled;
    android.widget.TextView _CVAR21 = Ingredients_tv;
    android.text.Spanned _CVAR24 = android.text.Html.fromHtml(_CVAR23);
    _CVAR21.append(_CVAR24);
}