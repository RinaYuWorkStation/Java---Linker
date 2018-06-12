
# Two-pass linker in Java

Assume machine is of 200 words, each of 4 decimal digits: Opcode, an immediate operand, an absolute address, a relative address (relocated) or an external address (resolved). 

The Linker processes the input twice, **First time**, it determines the base address for each module, and the absolute address for each external symbol, storing it in the symbol table. The first module has base address zero, and the next is base address of the last one plus its length. The absolute address for symbol S defined in module M is base address of M plus relative address of S in M. **The second time**, use the base addresses and the symbol tables to generate the actual output by relocating the relative addresses / resolving external references. 

## Definition list: 
(S,R) pairs, S is the symbol and R is the relative address, pass one relocates R to be the absolute address A and stores pair (S, A) in the symbol table

## Use List: 
first word, number of symbols, and then list of symbols and their relative addresses in the module. For example, ‘‘2 f 3 1 4 -1 xyg 0 -1’’. -1 is end of list for each symbol.

Each instruction is of 4 kinds. **I**mmediate, **A**bsolute, **R**elative and **E**xternal 

# How to run 
hit run in eclipse
or

javac Linker.java
java Linker.java 

## sample input:
```
4
1 xy 2
2 z 2 -1 xy 4 -1 
5 R 1004 I 5678 E 2000 R 8002 E 7001
0
1 z 1 2 3 -1 
6 R 8001 E 1000 E 1000 E 3000 R 1002 A 1010
0
1 z 1 -1
2 R 5001 E 4000 
1 z 2
2 xy 2 -1 z 1 -1
3 A 8000 E 1001 E 2000
```

## sample output:
```
Symbol Table
xy=2
z=15
Memory Map
0: 1004
1: 5678
2: 2015
3: 8002
4: 7002
5: 8006
6: 1015
7: 1015
8: 3015
9: 1007
10: 1010
11: 5012
12: 4015
13: 8000
14: 1015
15: 2002
```



