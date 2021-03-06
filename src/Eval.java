import java.io.File;


public class Eval {
	public static void eval(File g, File s){
		LineReader gr = new LineReader(g);
		LineReader sr = new LineReader(s);
		int linenum = 0;
		double total = 0;
		double correct = 0;
		while (gr.hasNextLine()){
			linenum++;
			String gl = gr.readNextLine().trim();
			String sl = sr.readNextLine().trim();
			if(gl.equals("")) continue;
			String[] ga = gl.split("\\t");
			int gsel = Integer.parseInt(ga[ga.length-1]);
			if (ga[7].equals("NMOD")) continue;
			if(gsel == 1){
				total++;
				//System.out.println(ga[6]);
				
				String[] sa = sl.split("\\t");
//				if(ga[13].equals("0")){
//					//System.out.println("no parse for selected token at line "  + linenum);
//				}
				if(ga[6].equals(sa[6])){
					correct++;
				}
			}
		}
		sr.closeAll();
		gr.closeAll();
		System.out.println(correct + " " + total + " " + correct/total);
	}
	
	public static void eval_byset(File g, File s){
		LineReader gr = new LineReader(g);
		LineReader sr = new LineReader(s);
		int linenum = 0;
		double total = 0;
		double correct = 0;
		while (gr.hasNextLine()){
			linenum++;
			String gl = gr.readNextLine().trim();
			String sl = sr.readNextLine().trim();
			if(gl.equals("")) continue;
			String[] ga = gl.split("\\t");
			int gsel = Integer.parseInt(ga[ga.length-1]);
			// if (ga[7].equals("NMOD")) continue;
			if(gsel == 1){
				total++;
				//System.out.println(ga[6]);
				
				String[] sa = sl.split("\\t");
//				if(ga[13].equals("0")){
//					//System.out.println("no parse for selected token at line "  + linenum);
//				}
				if(ga[6].equals(sa[6])){
					correct++;
				}
			}
		}
		sr.closeAll();
		gr.closeAll();
		System.out.println(correct + " " + total + " " + correct/total);
	}
	
	
	public static void main(String[] args){
		// String working_dir = "/home/lingpenk/GFL/ad3_exp_foster/";
		String working_dir = "/home/lingpenk/GFL/ad3_exp/";
		File fg = new File(working_dir+ "test");
		//File fs = new File(working_dir + "prediction");
		File fs = new File(working_dir + "prediction_withptb");
		//File fs = new File(working_dir + "ptb_single_predict_test");
		
		
		//565.0 740.0 0.7635135135135135

		//File fg = new File(args[0]);
		//File fs = new File(args[1]);
				
		eval(fg,fs);
	}
}
