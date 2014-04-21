import java.io.File;
import java.util.ArrayList;


public class MWERelabeler {
	public ArrayList<ConllSentence> readCorpus(File f){
		ArrayList<ConllSentence> res = new ArrayList<ConllSentence>();
		LineReader lr = new LineReader(f);
		ArrayList<String> lineBuffer = new ArrayList<String>();
		while(lr.hasNextLine()){
			String line = lr.readNextLine().trim();
			if(lr.equals("")){
				// A sentence is completed
				ConllSentence cs = new ConllSentence();
				cs.setLines(lineBuffer);
				lineBuffer = new ArrayList<String>();
			}
			lineBuffer.add(line);
		}
		lr.closeAll();
		return res;
	}
	
	public void printForLabel(File output, File indexf){
		
	}
	
	public void mergeWithOriginalFile(File original, File labeled, File output){
		
	}
	
	public static void main(String[] args) {

	}

}
