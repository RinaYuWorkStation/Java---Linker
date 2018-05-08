
# Two-pass linker in Java

Assume machine is of 200 words, each of 4 decimal digits: Opcode, an immediate operand, an absolute address, a relative address (relocated) or an external address (resolved). 

The Linker processes the input twice, **First time**, it determines the base address for each module, and the absolute address for each external symbol, storing it in the symbol table. The first module has base address zero, and the next is base address of the last one plus its length. The absolute address for symbol S defined in module M is base address of M plus relative address of S in M. **The second time**, use the base addresses and the symbol tables to generate the actual output by relocating the relative addresses / resolving external references. 

## Definition list: 
(S,R) pairs, S is the symbol and R is the relative address, pass one relocates R to be the absolute address A and stores pair (S, A) in the symbol table

## Use List: 
first word, number of symbols, and then list of symbols and their relative addresses in the module. For example, ‘‘2 f 3 1 4 -1 xyg 0 -1’’. -1 is end of list for each symbol.

Each instruction is of 4 kinds. **I**mmediate, **A**bsolute, **R**elative and **E**xternal 

# How to run 

javac Linker.java
java Linker.java input.txt

Note May 8, 2018: This program I retrieved from 2016 might have a bit problem right now, since I'm in middle of final week I'll look into it later


#  Sample input 1 :
4
1 xy 2
2 z xy
5 R 1004  I 5678  E 2000  R 8002  E 7001
0
1 z
6 R 8001  E 1000  E 1000  E 3000  R 1002  A 1010
0
1 z
2 R 5001  E 4000
1 z 2
2 xy z
3 A 8000  E 1001  E 2000


## Sample output 1:
Symbol Table
xy=2
z=15

Memory Map
0:  1004
1:  5678
2:  2015
3:  8002
4:  7002
5:  8006
6:  1015
7:  1015
8:  3015
9:  1007
10: 1010
11: 5012
12: 4015
13: 8000
14: 1015
15: 2002


## Sample input 2:
4 1
    xy 2
2 z            xy
5 	R 1004  I 5678  
      E 2000  R 
8002  E 7001  0      1 z
6 R 8001  E 1000  E 
1000  E 3000  R 1002  A 1010
0
1
z
2
R
5001
E 4000
1 z 2
2
xy z
3 A 8000
E 1001  E 2000


## Sample output 2:
Symbol Table
xy=2
z=15

Memory Map
0:  1004
1:  5678
2:  2015
3:  8002
4:  7002
5:  8006
6:  1015
7:  1015
8:  3015
9:  1007
10: 1010
11: 5012
12: 4015
13: 8000
14: 1015
15: 2002


## Sample input 3:
6

 3   X11 6  X12 8  X13 9
 2   X22  X31
10   R 1000  E 1001  E 1000  E 1001  E 1000  E 1000  A 1006  R 1007  A 1008  R 1009

 3   X21 0  X22 2  X23 5
 3   X11  X13  X31
 8   E 2001  E 2000  R 2002  E 2000  E 2001  R 2005  E 2001  E 2002

 1   X31 2
 0
 3   A 3000  I 3701  A 3002

 0
 3   X31  X11  X22
 6   R 4000  E 4000  E 4001  R 4003  R 4004  E 4002

 00
 00
 3    R 5000  I 5987  R 5002

 1   X61 2
 1   X31
 6   R 6000  E 6000  A 6002  A 6003  R 6004  I 6005


## Sample output 3:
Symbol Table
X11=6
X12=8
X13=9
X21=10
X22=12
X23=15
X31=20
X61=32

Memory Map
0:  1000
1:  1020
2:  1012
3:  1020
4:  1012
5:  1012
6:  1006
7:  1007
8:  1008
9:  1009
10: 2009
11: 2006
12: 2012
13: 2006
14: 2009
15: 2015
16: 2009
17: 2020
18: 3000
19: 3701
20: 3002
21: 4021
22: 4020
23: 4006
24: 4024
25: 4025
26: 4012
27: 5027
28: 5987
29: 5029
30: 6030
31: 6020
32: 6002
33: 6003
34: 6034
35: 6005

Warning: X12 was defined in module 0 but never used.
Warning: X21 was defined in module 1 but never used.
Warning: X23 was defined in module 1 but never used.
Warning: X61 was defined in module 5 but never used.



