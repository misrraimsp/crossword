import java.util.ArrayList;

public class Main_LM {
	
	private static boolean oBT = true;	// ordinary Back Tracking
	private static boolean oFC = true;	// ordinary Forward Checking
	
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Usage: main rowNum colNum strDomain");
			return;
		}
        else {
        	int numRow = Integer.parseInt(args[0]);
        	int numCol = Integer.parseInt(args[1]);
            ArrayList<Character> domain = new ArrayList<Character>();
            for (int i = 0; i < args[2].length(); i++){
                domain.add(args[2].charAt(i));
            }
            long start, end;
	       	int time;
            
            
            //////////////////////////////////////////////////////////////////////
            /////////////////////// BACK TRACKING
	       	System.out.println("Back Track:");
	       	CSP_LM cspBT = new CSP_LM(numRow, numCol, domain);
	       	// Ordinary BT
	       	if(oBT){
	       		start = System.currentTimeMillis();
	       		cspBT.BT();
	       		end = System.currentTimeMillis();
	       	}
	       	// Random BT
	       	else {
	       		start = System.currentTimeMillis();
	       		cspBT.rBT();
	       		end = System.currentTimeMillis();
	       	}
	       	cspBT.printSolution(numRow, numCol);
		   	time = (int) (end - start);
		   	System.out.println(time +" ms");
	   		System.out.println();
    		
	   		
	   		//////////////////////////////////////////////////////////////////////
	   		/////////////////////// FORWARD CHECKING
	   		System.out.println("Forward Checking:");
	   		CSP_LM cspFC = new CSP_LM(numRow, numCol, domain);
	   		// Ordinary BT
	   		if(oFC){
	   			start = System.currentTimeMillis();
	   			cspFC.FC();
	   			end = System.currentTimeMillis();
	   		}
	   		// Random BT
	   		else {
	   			start = System.currentTimeMillis();
	   			cspFC.rFC();
	   			end = System.currentTimeMillis();
	   		}
	   		cspFC.printSolution(numRow, numCol);
	   		time = (int) (end - start);
	   		System.out.println(time +" ms");
	   		System.out.println();
        }	
	}
}