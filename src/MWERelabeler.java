import java.io.File;
import java.util.ArrayList;

public class MWERelabeler {
	public static ArrayList<ConllSentence> readCorpus(File f) {
		ArrayList<ConllSentence> res = new ArrayList<ConllSentence>();
		LineReader lr = new LineReader(f);
		ArrayList<String> lineBuffer = new ArrayList<String>();
		while (lr.hasNextLine()) {
			String line = lr.readNextLine().trim();
			if (line.equals("")) {
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

	public static void printForLabel(ArrayList<ConllSentence> corpus,
			File output, File indexf) {
		LineWriter lw_output = new LineWriter(output);
		LineWriter lw_indexf = new LineWriter(indexf);

		for (int i = 0; i < corpus.size(); i++) {
			ConllSentence cs = corpus.get(i);
			ArrayList<MWE> mlist = cs.getMWE(i);
			for (MWE mwe : mlist) {
				ArrayList<String> printString = mwe.getPrintStrings();
				lw_output.writeln(printString.get(0));
				lw_indexf.writeln(printString.get(1));
			}

		}
		lw_output.closeAll();
		lw_indexf.closeAll();
	}

	public static void mergeWithOriginalFile(ArrayList<ConllSentence> corpus,
			ArrayList<ConllSentence> labeled, File indexf, File output) {
		LineReader lr = new LineReader(indexf);
		LineWriter lw = new LineWriter(output);
		int mweind = 0;
		while (lr.hasNextLine()) {
			String indexline = lr.readNextLine();
			if (indexline.equals("")) {
				continue;
			}
			String[] args = indexline.split("\t");
			int sentenceid = Integer.parseInt(args[0]);
			String[] originalpositions = (args[1]).split(" ");
			ConllSentence sen = corpus.get(sentenceid);
			ConllSentence mwe = labeled.get(mweind);

			merge(sen, mwe, originalpositions);
			
			mweind++;
		}
		for(ConllSentence sen : corpus){
			lw.writeln(sen.printString());
		}
		lw.closeAll();
		lr.closeAll();
	}

	private static void merge(ConllSentence sen, ConllSentence mwe,
			String[] originalpositions) {
		ArrayList<Integer> op = new ArrayList<Integer>();
		for(String s: originalpositions){
			op.add(Integer.parseInt(s));
		}
		int orignalMWEHeadPostion = op.get(0);
		int headOfMWE = Integer.parseInt(((sen.lines.get(orignalMWEHeadPostion-1)).split("\\t"))[6]);
		ArrayList<Integer> afterReplace = new ArrayList<Integer>();
		int MWEHeadNowInOriginal = -1;
		int m_index = 0;
		boolean flag = false;
		for(String line : mwe.lines){
			int h = Integer.parseInt((line.split("\\t"))[6]);
			if(h==0 && !flag){
				afterReplace.add(headOfMWE);
				MWEHeadNowInOriginal = op.get(m_index);
				flag = true;
			}else{
				if(h==0){
					afterReplace.add(MWEHeadNowInOriginal);
				}else{
					afterReplace.add(op.get(h-1));
				}
			}
			m_index++;
		}
		// Recover tokens inside the MWE
		for(int i = 0; i < op.size(); i++){
			String as = changeHeadField(sen.lines.get(op.get(i)-1), afterReplace.get(i));
			sen.lines.set(op.get(i)-1, as);
		}
		// Recover tokens outside the MWE
		for(int i = 0; i < sen.lines.size(); i++){
			if(op.contains(i+1)) continue;
			int oh = Integer.parseInt((sen.lines.get(i).split("\\t"))[6]);
			if(oh == orignalMWEHeadPostion){
				String as = changeHeadField(sen.lines.get(i), MWEHeadNowInOriginal);
				sen.lines.set(i, as);
			}
		}
	}
	
	private static String changeHeadField(String line, int nhead){
		String r = "";
		String[] args = line.split("\\t");
		args[6] = "" + nhead;
		for(String s : args){
			r = r + s + "\t";
		}
		r = r.trim();
		return r;
	}

	public static void main(String[] args) {
//		printForLabel(readCorpus(new File("/home/lingpenk/GFL/ad3_exp/test")),
//				new File("/home/lingpenk/GFL/ad3_exp/mwe_test_index"), new File("/home/lingpenk/GFL/ad3_exp/mwe_test"));
//		mergeWithOriginalFile(readCorpus(new File(
//				"/home/lingpenk/GFL/ad3_exp/test")), readCorpus(new File(
//				"/home/lingpenk/GFL/ad3_exp/predict_test_mwe")), new File("/home/lingpenk/GFL/ad3_exp/mwe_test_index"), new File("/home/lingpenk/GFL/ad3_exp/test_modify_mwe"));

//		printForLabel(readCorpus(new File("/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/all.conll")),
//				new File("/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/mwe_all_index"), new File("/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/mwe_all"));

		mergeWithOriginalFile(readCorpus(new File(
				"/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/all.conll")), readCorpus(new File(
				"/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/mwe_all_predict")), new File("/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/mwe_all_index"), new File("/home/lingpenk/GFL/emnlp_exp/corpus/processed/conll/all_mwe_preprocessed.conll"));

		
	}

}
