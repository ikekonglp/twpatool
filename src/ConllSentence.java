import java.util.ArrayList;


public class ConllSentence {
	public ArrayList<String> lines = new ArrayList<String>();
	public void setLines(ArrayList<String> lines){
		this.lines = lines;
	}
	public ArrayList<MWE> getMWE() {
		return getMWE(0);
	}
	public ArrayList<MWE> getMWE(int i) {
		for(String line : lines){
			
		}
		return null;
	}
	
	public int getIndex(String s){
		return Integer.parseInt(getFieldAt(s, 0));
	}
	
	public static String getLabel(String s){
		return getFieldAt(s, 7);
	}
	
	public static int getHead(String s){
		return Integer.parseInt(getFieldAt(s, 6));
	}
	
	public static String getFieldAt(String s, int i){
		return (s.split("\\t"))[i];
	}
	
	public static String getPOS(String s){
		return getFieldAt(s, 4);
	}
	
	public String getBC4(String s){
		return getFieldAt(s, 10);
	}
	
	public String getBC6(String s){
		return getFieldAt(s, 11);
	}
	
	public String getBCAll(String s){
		return getFieldAt(s, 12);
	}
}
