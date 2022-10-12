@API migration edit:_target.getLocation(...)->_target.toString(...)@
identifier _VAR3;
expression _target;
Integer Integer;
identifier _VAR2;
identifier _VAR1;
identifier floorBlocks;
@@
- Location _VAR3 =_target.getLocation();
+ HashSet floorBlocks = new java.util.HashSet<org.bukkit.block.Block>();
+ HashSet _VAR1 = floorBlocks;
+ int _VAR2 = _VAR1.size();
+ Location _VAR3 = Integer.toString(_VAR2);

