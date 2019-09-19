import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class CSP_LM {

	private Graph constraintNet;
	private boolean solved;
	
	public CSP_LM(int rowNum, int colNum, ArrayList<Character> domain) {
		solved = false;
		constraintNet = buildLetterMosaicCN(rowNum, colNum, domain);
	}

	public void BT() {
		Node n = getFirstUnassignedNode();
		//	base case
		if (n == null){
			solved = true;
			return;
		}
		//	general case
		ArrayList<Character> d = n.getAttribute("domain");
		for(Character c : d){
			n.setAttribute("value", c);
			if(checkConstraints(n)) BT();
			if(!solved){
				n.removeAttribute("value");
			}
			else return;
		}
	}
	
	public void rBT() {
		Dice dice = new Dice();
		Node n = getFirstUnassignedNode();
		//	base case
		if (n == null){
			solved = true;
			return;
		}
		//	general case
		ArrayList<Character> d = new ArrayList<Character>(n.getAttribute("domain"));
		while(!d.isEmpty()){
			int index = dice.roll(d.size() - 1);
			Character c = d.get(index);
			d.remove(index);
			n.setAttribute("value", c);
			if(checkConstraints(n)) rBT();
			if(!solved){
				n.removeAttribute("value");
			}
			else return;
		}
	}

	
	public void FC() {
		Node n = getFirstUnassignedNode();
		//	base case
		if (n == null){
			solved = true;
			return;
		}
		//	general case
		ArrayList<Character> currdom = new ArrayList<Character>(n.getAttribute("domain"));
		for(Character c : currdom){
			n.setAttribute("value", c);
			if (prune(n, c)) FC();
			if(!solved){
				restore(n, c);
				n.removeAttribute("value");
				}
			else return;
		}
	}
	
	public void rFC() {
		Dice dice = new Dice();
		Node n = getFirstUnassignedNode();
		//	base case
		if (n == null){
			solved = true;
			return;
		}
		//	general case
		ArrayList<Character> currdom = new ArrayList<Character>(n.getAttribute("domain"));
		while(!currdom.isEmpty()){
			int index = dice.roll(currdom.size() - 1);
			Character c = currdom.get(index);
			currdom.remove(index);
			n.setAttribute("value", c);
			if (prune(n, c)) rFC();
			if(!solved){
				restore(n, c);
				n.removeAttribute("value");
				}
			else return;
		}
	}
	
	private boolean prune(Node n, Character c) {
		Iterator<Node> ite = n.getNeighborNodeIterator();
		while(ite.hasNext()){
			Node pn = ite.next();
			if(!pn.hasAttribute("value")){
				ArrayList<Character> domain = new ArrayList<Character>(pn.getAttribute("domain"));
				if(domain.contains(c)){
					domain.remove(c);
					pn.setAttribute("domain", domain);
				}
				if(domain.isEmpty()) return false;
			}
		}
		return true;
	}
	
	private void restore(Node n, Character c) {
		Iterator<Node> ite = n.getNeighborNodeIterator();
		while(ite.hasNext()){
			Node rn = ite.next();
			if(!rn.hasAttribute("value")){
				ArrayList<Character> domain = new ArrayList<Character>(rn.getAttribute("domain"));
				if(!domain.contains(c)){
					domain.add(c);
					rn.setAttribute("domain", domain);
				}
			}
		}
	}

	private boolean checkConstraints(Node n) {
		Iterator<Node> ite = n.getNeighborNodeIterator();
		while(ite.hasNext()){
			Node nn = ite.next();
			Boolean assigned = nn.hasAttribute("value");
			if(assigned && !isCompatible(nn.getAttribute("value"), n.getAttribute("value"))) return false;
		}
		return true;
	}

	private boolean isCompatible(Object o1, Object o2) {
		return !(o1.equals(o2));
	}

	private Node getFirstUnassignedNode() {
		for (Node n : constraintNet){
			if(!n.hasAttribute("value")) return n;
		}
		return null;
	}

	public void printSolution(int numRow, int numCol) {
		if(solved){
			Node n;
			String line = "";
			String border = "+";
			for (int j = 1; j <= numCol; j++){
				border = border + "-";
			}
			border = border + "+";
			System.out.println(border);
			for (int i = 1; i <= numRow; i++){
				for (int j = 1; j <= numCol; j++){
					n = constraintNet.getNode("r" + Integer.toString(i) + "c" + Integer.toString(j));
					line = line + n.getAttribute("value");
				}
				System.out.println("|" + line + "|");
				line = "";
			}
			System.out.println(border);
		}
		else System.out.println("No Solution");
	}
	
	private Graph buildLetterMosaicCN(int rowNum, int colNum, ArrayList<Character> domain) {
		Graph g = new SingleGraph("Letter Mosaic");
		for (int i = 1; i <= rowNum; i++){
			for (int j = 1; j <= colNum; j++){
				String id = "r" + Integer.toString(i) + "c" + Integer.toString(j);
				g.addNode(id);
				g.getNode(id).addAttribute("domain", domain);
				if (j > 1){
					String prevId = "r" + Integer.toString(i) + "c" + Integer.toString(j - 1);
					g.addEdge(id + "-" + prevId, id, prevId);
				}
				if (i > 1){
					String prevId = "r" + Integer.toString(i - 1) + "c" + Integer.toString(j);
					g.addEdge(id + "-" + prevId, id, prevId);
				}
				/*
				if (i > 1 && j > 1){
					String prevId = "r" + Integer.toString(i - 1) + "c" + Integer.toString(j - 1);
					g.addEdge(id + "-" + prevId, id, prevId);
				}
				if (i > 1 && j < colNum){
					String prevId = "r" + Integer.toString(i - 1) + "c" + Integer.toString(j + 1);
					g.addEdge(id + "-" + prevId, id, prevId);
				}
				*/
			}
		}
		return g;
	}
	
	/*
	private void printDomains() {
		for(Node n : constraintNet){
			System.out.println(n.getId() + n.getAttribute("domain").toString());
		}
	}
	*/

}
