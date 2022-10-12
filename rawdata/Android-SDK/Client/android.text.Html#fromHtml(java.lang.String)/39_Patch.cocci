@API migration edit:_target.fromHtml(...)->_target.fromHtml(...)@
identifier _VAR2;
expression _target;
expression _DVAR0;
Html Html;
identifier _VAR1;
@@
- Spanned _VAR2 =_target.fromHtml(_CVAR3);
+ int _VAR1 = 0;
+ Spanned _VAR2 = Html.fromHtml(_CVAR3, _VAR1);

