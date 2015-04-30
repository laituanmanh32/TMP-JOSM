import java.io.File;
import java.io.IOException;
import java.util.List;


public class Main {

	public static void main(String[] args) throws IOException {
		POFileHandler pohdl  = new POFileHandler();
		List<String> a = pohdl.getMsgid(new File("josm_josm-vi.po"));

		for(int i = 0 ; i < a.size() ; i++){
			System.out.println(a.get(i));
		}
	}

}
