import java.util.ArrayList;

public class Main {
	
	private static boolean oBT = true;	// ordinary Back Tracking
	private static boolean oFC = true;	// ordinary Forward Checking
	
	public static void main(String[] args) {
		
		if (args.length != 4) {
			System.out.println("Usage: main LemmasFilePath rowNum colNum numLemmas");
			return;
		}
		else {
			IO io = new IO(args[0]);
	       	ArrayList<String> lemmas = io.loadLemmas();
	       	int numRow = Integer.parseInt(args[1]);
	       	int numCol = Integer.parseInt(args[2]);
	       	int numLemmas = Integer.parseInt(args[3]);
	       	Domain dom = new Domain(numRow, numCol, lemmas);
	       	long start, end;
	       	int time;
	       	
	       	
	       	//////////////////////////////////////////////////////////////////////
	       	/////////////////////// BACK TRACKING
	       	System.out.println("Back Track:");
	       	CSP cspBT = new CSP(numLemmas, dom);
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
	       	cspBT.formattedPrint(numRow, numCol);
		   	time = (int) (end - start);
		   	System.out.println(time +" ms");
	   		System.out.println();
	   		
	   		
	   		//////////////////////////////////////////////////////////////////////
	   		/////////////////////// FORWARD CHECKING
	   		System.out.println("Forward Checking:");
	   		CSP cspFC = new CSP(numLemmas, dom);
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
		   	cspFC.formattedPrint(numRow, numCol);
		   	time = (int) (end - start);
		   	System.out.println(time +" ms");
	   		System.out.println();
		}
	}	
}