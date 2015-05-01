import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class POFileHandler {
	/**
	 * Fetch all line belong to msgid line.
	 *
	 * @param poFile
	 * @return list of msgid line.
	 * @throws IOException
	 */
	public List<String> getMsgid(File poFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(poFile));
		List<List<String>> msgidList = new LinkedList<List<String>>();
		List<String> msgid = new ArrayList<String>();

		String newline = br.readLine();
		while (true) {
			if (!br.ready())
				break;
			if (newline.startsWith("msgid")) {
				msgid = new ArrayList<String>();
				msgid.add(newline);
				for (;;) {
					newline = br.readLine();
					if (newline.startsWith("msgstr")
							|| newline.startsWith("msgid")
							|| newline == null)
						break;
					msgid.add(newline);
				}
				msgidList.add(msgid);
			} else {
				newline = br.readLine();
			}
		}

		System.out.println(msgidList.get(1));
		br.close();
		return msgid;
	}

	/**
	 * Fetch all line belong to msgstr line
	 *
	 * @param poFile
	 * @return
	 * @throws IOException
	 */
	public List<List<String>> getMsgstr(File poFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(poFile));
		List<List<String>> msgstrList = new LinkedList<List<String>>();
		List<String> msgstr;

		String newline = br.readLine();
		while (true) {
			if (!br.ready())
				break;
			if (newline.startsWith("msgstr")) {
				msgstr = new ArrayList<String>();
				msgstr.add(newline);
				for (;;) {
					newline = br.readLine();
					if (newline.startsWith("msgstr")
							|| newline.startsWith("msgid")
							|| newline == null)
						break;
					msgstr.add(newline);
				}
				msgstrList.add(msgstr);
			} else {
				newline = br.readLine();
			}
		}

		br.close();
		return msgstrList;
	}
}
