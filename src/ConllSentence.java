import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class ConllSentence {
	public ArrayList<String> lines = new ArrayList<String>();
	public void setLines(ArrayList<String> lines){
		this.lines = lines;
	}
	public ArrayList<MWE> getMWE() {
		return getMWE(0);
	}
	
	public ArrayList<MWE> getMWE(int i) {
		ArrayList<Integer> headlist = getMWEHeadList();
		ArrayList<MWE> mwes = new ArrayList<MWE>();
		for(int head : headlist){
			MWE mwe = getMWEAt(head);
			mwe.sentenceID = i;
			mwes.add(mwe);
		}
		return mwes;
	}
	// Given the head of the MWE, assemble its team!
	private MWE getMWEAt(int head){
		MWE mwe = new MWE();
		// Add the first token
		addFromLine(mwe, lines.get(head-1));
		for(String line : lines){
			if(getLabel(line).equals("MWE") && getHead(line) == head){
				addFromLine(mwe, line);
			}
		}
		return mwe;
	}
	
	// add to the MWE structure from a line in conll
	private void addFromLine(MWE mwe, String line){
		mwe.tokens.add(getWord(line));
		mwe.originalPositions.add(getIndex(line));
		mwe.headPositions.add(getHead(line));
		mwe.postags.add(getPOS(line));
		mwe.bc4strings.add(getBC4(line));
		mwe.bc6strings.add(getBC6(line));
		mwe.bcallstrings.add(getBCAll(line));
	}
	
	private ArrayList<Integer> getMWEHeadList(){
		HashSet<Integer> headset = new HashSet<Integer>();
		for(String line : lines){
			String label = getLabel(line);
			if(label.equals("MWE")){
				// Here is a MWE arc, find its parent
				int head = getHead(line);
				headset.add(head);
			}
		}
		ArrayList<Integer> headlist = new ArrayList<Integer>(headset);
		Collections.sort(headlist);
		return headlist;
	}
	
	private String getWord(String s){
		return getFieldAt(s, 1);
	}
	
	private int getIndex(String s){
		return Integer.parseInt(getFieldAt(s, 0));
	}
	
	private static String getLabel(String s){
		return getFieldAt(s, 7);
	}
	
	private static int getHead(String s){
		return Integer.parseInt(getFieldAt(s, 6));
	}
	
	private static String getFieldAt(String s, int i){
		return (s.split("\\t"))[i];
	}
	
	private static String getPOS(String s){
		return getFieldAt(s, 4);
	}
	
	private String getBC4(String s){
		return getFieldAt(s, 10);
	}
	
	private String getBC6(String s){
		return getFieldAt(s, 11);
	}
	
	private String getBCAll(String s){
		return getFieldAt(s, 12);
	}
}
