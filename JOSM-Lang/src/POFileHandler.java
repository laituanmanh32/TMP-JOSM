import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class POFileHandler {
	/**
	 * Fetch all line belong to msgid.
	 *
	 * @param poFile
	 * @return list of msgid line.
	 * @throws IOException
	 */
	public List<String> getMsgid(File poFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(poFile));
		List<String> msgidList = new ArrayList<String>();

		boolean isMsgidContent = false;
		String next_line = null;
		while (true) {
			if (!br.ready())
				break;
			next_line = br.readLine();

			if (next_line.startsWith("msgid")) {
				isMsgidContent = true;
			}

			if (next_line == null || next_line.contains("msgstr")
					|| next_line.startsWith("#")) {
				isMsgidContent = false;
			}

			if (isMsgidContent)
				msgidList.add(next_line);

		}
		System.out.println(next_line);


		br.close();
		return msgidList;
	}
}
