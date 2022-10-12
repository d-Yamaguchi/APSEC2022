@API migration edit:_target.getFace(...)->_target.getRelative(...)@
identifier holdingBlock;
expression _target;
expression _DVAR0;
identifier _VAR11;
identifier _VAR4;
identifier _VAR0;
Block buttonBlock;
identifier _VAR13;
identifier _VAR6;
identifier _VAR2;
identifier opposite;
WorldUtils WorldUtils;
identifier _VAR8;
@@
- BlockFace holdingBlock =_target.getFace(_CVAR1);
+ BlockFace holdingBlock = _CVAR0.getRelative(_CVAR1);

