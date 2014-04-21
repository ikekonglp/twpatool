import java.util.ArrayList;

public class MWE {
	public int sentenceID = 0;
	public ArrayList<String> tokens = new ArrayList<String>();
	public ArrayList<Integer> originalPositions = new ArrayList<Integer>();
	public ArrayList<Integer> headPositions = new ArrayList<Integer>();
	public ArrayList<String> postags = new ArrayList<String>();
	public ArrayList<String> bc4strings = new ArrayList<String>();
	public ArrayList<String> bc6strings = new ArrayList<String>();
	public ArrayList<String> bcallstrings = new ArrayList<String>();

	public ArrayList<String> getPrintStrings() {
		// The first element returns the index file and the second element
		// returns the content
		ArrayList<String> buffer = new ArrayList<String>();
		String index = "" + sentenceID + "\t";
		String content = "";
		for (int i = 0; i < tokens.size(); i++) {
			index = index + originalPositions.get(i) + " ";
			content = content + (i + 1) + "\t" + tokens.get(i) + "\t_\t"
					+ postags.get(i) + "\t" + postags.get(i) + "\t"
					+ "_\t0\t_\t_\t_\t" + bc4strings.get(i) + "\t"
					+ bc6strings.get(i) + "\t" + bcallstrings.get(i) + "\t1" + "\n";
			
		}
		index = index.trim();
		buffer.add(index);
		buffer.add(content);
		return buffer;
	}

}
