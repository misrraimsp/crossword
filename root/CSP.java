import java.util.ArrayList;
import java.util.Iterator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;


public class CSP {

	private Graph constraintNet;
	private boolean solved;
	
	public CSP(int numLemmas, Domain domain) {
		solved = false;
		constraintNet = buildCN(numLemmas, domain);
	}


	public void BT() {
		Node n = getFirstUnassignedNode();
		//	base case
		if (n == null){
			solved = true;
			return;
		}
		//	general case
		Domain d = n.getAttribute("domain");
		for(Word w : d.getValues()){
			n.setAttribute("value", w);
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
		Domain d = new Domain(n.getAttribute("domain"));
		while(!d.isEmpty()){
			int index = dice.roll(d.getSize() - 1);
			Word w = d.getValue(index);
			d.remove(index);
			n.setAttribute("value", w);
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
		Graph pruned;
		Domain currdom = new Domain(n.getAttribute("domain"));
		for(Word w : currdom.getValues()){
			n.setAttribute("value", w);
			pruned = prune(n, w);
			if (pruned != null) FC();
			if(!solved){
				restore(pruned);
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
		Graph pruned;
		Domain currdom = new Domain(n.getAttribute("domain"));
		while(!currdom.isEmpty()){
			int index = dice.roll(currdom.getSize() - 1);
			Word w = currdom.getValue(index);
			currdom.remove(index);
			n.setAttribute("value", w);
			pruned = prune(n, w);
			if (pruned != null) rFC();
			if(!solved){
				restore(pruned);
				n.removeAttribute("value");
				}
			else return;
		}
	}
	
	private Graph prune(Node n, Word w) {
		Graph g = new SingleGraph("aux graph");
		Iterator<Node> ite = n.getNeighborNodeIterator();
		while(ite.hasNext()){
			Node pn = ite.next();
			boolean assigned = pn.hasAttribute("value");
			if(!assigned){
				g.addNode(pn.getId());
				Domain oldDomain = pn.getAttribute("domain");
				Domain pruned = new Domain();
				Domain newDomain = new Domain();
				for (int i = 0; i < oldDomain.getSize(); i++){
					Word word = oldDomain.getValue(i);
					if(w.isCompatible(word)) newDomain.addValue(word);
					else pruned.addValue(word);
				}
				if(newDomain.isEmpty()) return null;
				pn.setAttribute("domain", newDomain);
				g.getNode(pn.getId()).addAttribute("domain", pruned);
			}
		}
		return g;
	}
	
	private void restore(Graph g) {
		if (g == null) return;
		for(Node n : g){
			Domain d = n.getAttribute("domain");
			String id = n.getId();
			Node rn = constraintNet.getNode(id);
			Domain newDomain = new Domain(rn.getAttribute("domain"));
			for(Word word : d.getValues()){
				newDomain.addValue(word);
			}
			rn.setAttribute("domain", newDomain);
		}
	}

	
	private boolean checkConstraints(Node n) {
		Word w = n.getAttribute("value");
		Iterator<Node> ite = n.getNeighborNodeIterator();
		while(ite.hasNext()){
			Node nn = ite.next();
			Boolean assigned = nn.hasAttribute("value");
			if(assigned && !w.isCompatible(nn.getAttribute("value"))) return false;
		}
		return true;
	}



	private Node getFirstUnassignedNode() {
		for (Node n : constraintNet){
			if(!n.hasAttribute("value")) return n;
		}
		return null;
	}

	
	private Graph buildCN(int numLemmas, Domain domain) {
		Graph g = new SingleGraph("Crossword Design");
		for (int i = 1; i <= numLemmas; i++){
			String id = Integer.toString(i);
			g.addNode(id);
			g.getNode(id).addAttribute("domain", domain);
			for (Node n : g){
				if(!id.equals(n.getId())){
					String cId = id + "-" + n.getId(); //	constraint id
					g.addEdge(cId, g.getNode(id), n);
				}
			}
		}
		return g;
	}
	
	
	public void simplePrint(){
		if(solved){
			for(Node n : constraintNet){
				Word value = n.getAttribute("value");
				String s =	Integer.toString(value.getStartRow()) + Integer.toString(value.getStartCol()) + " ";
				if (value.getDir() == 0) s = s + "V: ";
				else s = s + "H: ";
				s = s + value.getStr();
				System.out.println(s);
			}
		}
		else System.out.println("No Solution");
	}
	
	public void formattedPrint(int row, int col){
		if(!solved){
			System.out.println("No Solution");
			return;
		}
		ArrayList<Cell> allCoreCells = new ArrayList<Cell>();
		for(Node n : constraintNet){
			Word w = n.getAttribute("value");
			allCoreCells.addAll(w.getCoreCells());
		}
		String border = "+";
		for (int j = 1; j <= col; j++){
			border = border + "-";
		}
		border = border + "+";
		System.out.println(border);
		String line = "";
		Character l = null;
		for(int i = 1; i <= row; i++){
			line = "";
			for(int j = 1; j <= col; j++){
				l = null;
				for(Cell c : allCoreCells){
					if(i == c.getRow() && j == c.getCol()) l = c.getLetter();
				}
				if(l != null) line = line + l;
				else line = line + "·";
			}
			System.out.println("|" + line + "|");
		}
		System.out.println(border);
	}

}
