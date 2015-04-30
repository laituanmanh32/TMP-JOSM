import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POFileHandler {
	public List<String> getMsgid(File poFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(poFile));
		List<String> msgidList = new ArrayList<String>();

		while (true) {
			if (!br.ready())
				break;
			String next_line = br.readLine();
			if (next_line == null
				|| next_line.contains("msgstr")
				|| next_line.startsWith("#")) {
				continue;
			}

			msgidList.add(next_line);

		}

		br.close();
		return msgidList;

	}
}
