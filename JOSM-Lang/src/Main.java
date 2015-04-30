import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class Main {

	public static void main(String[] args) throws IOException {
		POFileHandler pohdl  = new POFileHandler();
		List<String> a = pohdl.getMsgid(new File("josm_josm-vi.po"));

		writeListToFile(a, "msgid.po");
	}

	public static void writeListToFile(List lst, String filename) throws IOException{
		File outFile = new File(filename);
		outFile.createNewFile();

		PrintWriter pw = new PrintWriter(outFile);

		for(int i = 0 ; i < lst.size(); i++){
			pw.println(lst.get(i));
			pw.flush();
		}

	}

}
