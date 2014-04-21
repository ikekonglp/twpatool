import java.io.File;
import java.util.ArrayList;


public class MWERelabeler {
	public static ArrayList<ConllSentence> readCorpus(File f){
		ArrayList<ConllSentence> res = new ArrayList<ConllSentence>();
		LineReader lr = new LineReader(f);
		ArrayList<String> lineBuffer = new ArrayList<String>();
		while(lr.hasNextLine()){
			String line = lr.readNextLine().trim();
			if(line.equals("")){
				// A sentence is completed
				ConllSentence cs = new ConllSentence();
				cs.setLines(lineBuffer);
				res.add(cs);
				
				lineBuffer = new ArrayList<String>();
				continue;
			}
			lineBuffer.add(line);
		}
		lr.closeAll();
		return res;
	}
	
	public static void printForLabel(ArrayList<ConllSentence> corpus, File output, File indexf){
		LineWriter lw_output = new LineWriter(output);
		LineWriter lw_indexf = new LineWriter(indexf);
		
		for(int i = 0; i < corpus.size(); i++){
			ConllSentence cs = corpus.get(i);
			ArrayList<MWE> mlist = cs.getMWE(i);
			for(MWE mwe : mlist){
				ArrayList<String> printString = mwe.getPrintStrings();
				lw_output.writeln(printString.get(0));
				lw_indexf.writeln(printString.get(1));
			}
			
		}
		lw_output.closeAll();
		lw_indexf.closeAll();
	}
	
	public void mergeWithOriginalFile(File original, File labeled, File output){
		
	}
	
	public static void main(String[] args) {
		printForLabel(readCorpus(new File("/home/lingpenk/GFL/ad3_exp/train")), new File("test1"), new File("test2"));
	
	}

}
