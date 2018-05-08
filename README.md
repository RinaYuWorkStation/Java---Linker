
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



# for more input, output see moreinput.txt


