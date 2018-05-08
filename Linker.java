
// jiaqi yu, jy1604

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Linker {
	public static void main(String[] args) throws FileNotFoundException {
//		
		String [] AIER = {"A","E", "I","R"};
		ArrayList<Integer> moduleNumInstr = new ArrayList<Integer>();//how many instructions in a module
		ArrayList<Integer> modulebaseAddr = new ArrayList<Integer>();//base addresses of modules
		
		ArrayList<String> symbolTable = new ArrayList<String>();
		ArrayList<Integer> symbolCount = new ArrayList<Integer>();
		
		ArrayList<Integer> symbolDef = new ArrayList<Integer>();
		File inputFile= new File(args[0]);
		
		
		int instrSum=0;
		int instrGlobalTotal=0;
		
		Scanner scanner1 = new Scanner(inputFile);
		int totalModules=Integer.parseInt(scanner1.next());
		
		for(int currentModule=0;currentModule<totalModules;currentModule++){
		boolean lastLineofModule=false;
		boolean isUseList=false;
		boolean isDefinitionLine=true;
		
		while(lastLineofModule==false){

			StringBuilder currentline=new StringBuilder();
			int thisLineLength=Integer.parseInt(scanner1.next());
			currentline.append(thisLineLength);
			

			if(isUseList){
				isDefinitionLine=false;
				isUseList=false;
				int progress=0;
				while(progress<thisLineLength){
					
					if (scanner1.hasNext()){
					currentline.append(" ");
					String tmp=scanner1.next();
					if(tmp.equals("-1")){
						currentline.append(tmp);
						progress++;
					}
					else{
						currentline.append(tmp);
					}
				}
			}}
			else if(thisLineLength!=0){
				for(int q=0;q<2*thisLineLength;q++){
					currentline.append(" ");
					currentline.append(scanner1.next());
				}
			}

			
			  String primitiveline=currentline.toString();
				String [] separated= primitiveline.split("\\s+");//separate words in each line

				for(int i=0;i<separated.length;i++){
					for (int j=0;j<AIER.length;j++){
						if(AIER[j].equals(separated[i])) {
							lastLineofModule=true;
							instrSum+=1;
							instrGlobalTotal+=1;
							}
						}		
		}
				
				int ptr = 0;   

                if (isDefinitionLine) {
                    while (ptr < separated.length) {
                        if ((separated[ptr].length() > 0) && (Character.isLetter(separated[ptr].charAt(0)))) {

                            if (ptr + 1 > separated.length) {
                                System.out.println("***Error: There is an invalid variable name or value!***");
                                break;
                            }
                          
                            
                            else {
                            	
                               symbolTable.add(separated[ptr]);
                              
                               symbolCount.add(Integer.parseInt(separated[ptr+1])+instrGlobalTotal);
                              symbolDef.add(currentModule);
                              
                            }

                            ptr++;
                        } else {
                            ptr++;
                        }
                    }
              isUseList = isDefinitionLine;   }

				if(lastLineofModule==true) {
					
					moduleNumInstr.add(instrSum);//after this line, get the instruction of this module
					instrSum=0;//reset the number of instruction in this module
					}//start of a new module
			
		}	
	}    scanner1.close();
				
				
			
			int sum=0;
			modulebaseAddr.add(0);
			
			for(int x=1;x<moduleNumInstr.size();x++){
				sum=0;
				for(int y=0;y<x;y++){
					sum+=moduleNumInstr.get(y);
					
				}modulebaseAddr.add(sum);
			}
			

			for(int m=0;m<symbolTable.size();m++){
			if(symbolCount.get(m)>=modulebaseAddr.get(symbolDef.get(m))+moduleNumInstr.get(symbolDef.get(m))){	
				symbolCount.set(m, modulebaseAddr.get(symbolDef.get(m)));
				System.out.println("Error: the address of definition " +symbolTable.get(m)+" exceeds the size of the module");
			
			}
			}
			
			
			System.out.println("Symbol Table");
			for(int x=0;x<symbolTable.size();x++){
				System.out.print(symbolTable.get(x));
				System.out.print("=");
				System.out.print(symbolCount.get(x));
				System.out.println();
				
				for(int o=x+1;o<symbolTable.size();o++){
					if(symbolTable.get(o).equals(symbolTable.get(x))){
						System.out.println("Error: This variable is multiply defined; first value used.");
						symbolTable.remove(o);
						symbolCount.remove(o);
				}
				}
				
			}
			
			
			

	System.out.println("Memory Map");
	
	Scanner scanner2 = new Scanner(inputFile);
	
		int totalModules2=Integer.parseInt(scanner2.next());
		
        ArrayList<String> external = new ArrayList<String>();
        ArrayList<String> usedSymbol = new ArrayList<String>();
        int countOutput=0;
        
		for(int currentModule=0;currentModule<totalModules2;currentModule++){
			boolean lastLineofModule=false;
			boolean isUseList=true;
		
			  int jump = Integer.parseInt(scanner2.next());
	            for (int i =1; i <= 2*jump; i ++){
	                scanner2.next();
	            }
	            
	           while(!lastLineofModule){
	        	   	boolean inTable=false;
	                   StringBuilder currentline = new StringBuilder();   

	                   int thisLineLength = Integer.parseInt(scanner2.next());  
	                   currentline.append(thisLineLength);

	                   if(isUseList){
	       				int progress=0;
	       				while(progress<thisLineLength){
	       					
	       					if (scanner2.hasNext()){
	       					currentline.append(" ");
	       					String tmp=scanner2.next();
	       					if(tmp.equals("-1")){
	       						currentline.append(tmp);
	       						progress++;
	       					}
	       					else{
	       						currentline.append(tmp);
	       					}
	       				}
	       			}}
	       			else if(thisLineLength!=0){
	       				for(int q=0;q<2*thisLineLength;q++){
	       					currentline.append(" ");
	       					currentline.append(scanner2.next());
	       				}
	       			}
	                   String primitiveline=currentline.toString();
	   				String [] separated2= primitiveline.split(" ");//separate words in each line
	   		
	   				for(int i=0;i<separated2.length;i++){
						
						for(int j=0;j<symbolTable.size();j++){
						if(isUseList){
							
							if(Character.isLetter(separated2[i].charAt(0))){
								String symbol=separated2[i];
								i+=1;
								while(separated2[i].equals("-1")==false){
									external.add(symbol);
									usedSymbol.add(symbol);
									external.add(separated2[i]);
									i++;			
							}

							}
							
							}
   				
						}
//						
//						
						for(int a=0;a<external.size();a++){
							String temp=external.get(a);
							if(!Character.isLetter(temp.charAt(0))){
							for(int b=a+1;b<external.size();b++){
								if(external.get(b).equals(temp)){
									System.out.println("Error:multiple symbols are listed as used in the same instruction, ignore all but the first usage given");
								external.remove(b);
								external.remove(b-1);
								}
								
							}
						}
						}
						
//					
						
	   				if("A".equals(separated2[i])) {
						lastLineofModule=true;
						 int size = Integer.parseInt(separated2[i+1]) % 1000;
					 if(size<=200){
						System.out.println(countOutput+": "+separated2[i+1]);
						}
					 else{System.out.print(countOutput+": "+((Integer.parseInt(separated2[i+1])/1000)*1000));
					
						 System.out.println(" Error: Absolute address exceeds the size of the machine, use zero");
					 }
						countOutput++;
					}
					
					if("E".equals(separated2[i])) {
					
						lastLineofModule=true;
						for(int z=0;z<external.size();z++){
						
							if(!Character.isLetter(external.get(z).charAt(0)) && Integer.parseInt(external.get(z))>(1+(separated2.length-1)/2) ){
								System.out.println("Error: use of " +external.get(z-1)+"  is beyond the size of the module, ignore");
							}
						
							else if(external.get(z).equals(((i-1)/2)+"")){
							
							
								if(!symbolTable.contains(external.get(z-1))){
//									
									System.out.println(countOutput+": "+(Integer.parseInt(separated2[i+1])/1000)*1000 +" Error: "+ external.get(z-1)+" is not defined, zero used");
									}
								
								else if(symbolCount.get(symbolTable.indexOf(external.get(z-1)))>200){
								
							System.out.println(separated2[i+1]+"exceeds the size of the machine, use zero");
								System.out.println(countOutput+": "+(Integer.parseInt(separated2[i+1])/1000)*1000);
								countOutput++;
								}
								
							
								
								
							else{
//								
//								
									System.out.println(countOutput+": "+((Integer.parseInt(separated2[i+1])/1000)*1000+symbolCount.get(symbolTable.indexOf(external.get(z-1)))));
										external.remove(z);
						external.remove(z-1);
						countOutput++;
					}
									
					}}
					}
					if("I".equals(separated2[i])) {
					
						lastLineofModule=true;
						
					System.out.println(countOutput+": "+separated2[i+1]);
					countOutput++;}
				
					if("R".equals(separated2[i])) {
						int moduleSize=Integer.parseInt(separated2[0]);
						lastLineofModule=true;
						int size=(Integer.parseInt(separated2[i+1]))%1000;
						if(size>moduleSize){
							int result=Integer.parseInt(separated2[i+1])-size;
							System.out.println(countOutput+": "+result+"Error: relative address exceeds size of module");
							countOutput++;
						}
						else{

						System.out.println(countOutput+": "+(Integer.parseInt(separated2[i+1])+modulebaseAddr.get(currentModule)));
						countOutput++;}

					}

		
		}
	   				isUseList=false;	}
		
		
		
		
		
	}scanner2.close();
	
	for(int a=0;a<symbolTable.size();a++){
		
			if(!usedSymbol.contains(symbolTable.get(a))){
				System.out.println("Warning: "+symbolTable.get(a)+" symbol is defined in module "+ (symbolDef.get(a)+1)+" but never used");
			}
		
	}
	
	
}
}
