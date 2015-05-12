import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class POFileHandler {
	/**
	 * Fetch all line belong to msgid line.
	 *
	 * @param poFile
	 * @return list of msgid line.
	 * @throws IOException
	 */
	public static LinkedList<List<String>> getMsgid(File poFile)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(poFile));
		LinkedList<List<String>> msgidList = new LinkedList<List<String>>();
		List<String> msgid = new ArrayList<String>();

		String newline = br.readLine();
		while (br.ready()) {
			if (newline.startsWith("msgid")) {
				msgid = new ArrayList<String>();
				msgid.add(newline);
				for (;;) {
					newline = br.readLine();
					if (newline.startsWith("msgstr")
							|| newline.startsWith("msgid") || newline == null)
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
		return msgidList;
	}

	/**
	 * Fetch all line belong to msgstr line
	 *
	 * @param poFile
	 * @return
	 * @throws IOException
	 */
	public static LinkedList<List<String>> getMsgstr(File poFile)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(poFile));
		LinkedList<List<String>> msgstrList = new LinkedList<List<String>>();
		List<String> msgstr;

		String newline = br.readLine();
		while (br.ready()) {
			if (newline.startsWith("msgstr")) {
				msgstr = new ArrayList<String>();
				msgstr.add(newline);
				for (;;) {
					newline = br.readLine();
					if (newline.startsWith("msgid_plural")) {
						String temp = msgstr.get(0).replace("msgstr",
								"msgstr[0]");
						msgstr.remove(0);
						msgstr.add(0, temp);
						newline = newline.replace("msgid_plural", "msgstr[1]");
						newline.trim();
					}
					if (newline.startsWith("msgstr")
							|| newline.startsWith("msgid") || newline == null)
						break;
					msgstr.add(newline);
				}
				msgstrList.add(msgstr);
			} else {
				newline = br.readLine();

			}
		}

		msgstr = new ArrayList<String>();
		msgstr.add(newline);
		msgstrList.add(msgstr);



		System.out.println(msgstrList.get(msgstrList.size()-1));
		br.close();
		return msgstrList;
	}

	public static void mergePOFile(File orgPOFile, File msgstrPOFile,
			File desFile) throws IOException {
		BufferedReader orgFile = new BufferedReader(new FileReader(orgPOFile));
		PrintWriter pw = new PrintWriter(desFile);

		// Build msgstr list
		Queue<List<String>> msgstrList = getMsgstr(msgstrPOFile);

		// Begin merge data

		while (orgFile.ready()) {
			String newline = orgFile.readLine();
			if (newline.startsWith("msgstr")) {
				List<String> msgstr = msgstrList.poll();
				for(String line : msgstr){
					pw.println(line);
				}
//				pw.println(msgstr);
				pw.flush();

			} else {
				pw.println(newline);
				pw.flush();
			}
		}

		orgFile.close();
		pw.close();

		System.out.println("Done!");
	}

	public static void writeListToFile(List<List<String>> lst, File outFile)
			throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(outFile);

		for (List<String> memLst : lst) {
			for (String line : memLst) {
				pw.println(line);
				pw.flush();
			}
		}
		pw.close();
	}

	public static void repairMsgstrFile(File msgidPOFile, File msgstrPOFile)
			throws IOException {
		BufferedReader msgidFile = new BufferedReader(new FileReader(
				msgidPOFile));
		BufferedReader msgstrFile = new BufferedReader(new FileReader(
				msgstrPOFile));

		PrintWriter repairedFile = new PrintWriter(new File("repaied" + msgstrPOFile.getName()));

		while (msgidFile.ready() && msgstrFile.ready()) {
			String msgid = msgidFile.readLine();
			String msgstr = msgstrFile.readLine();

			if (msgid.startsWith("msgid") && !msgid.startsWith("msgid_plural")
					&& !msgstr.startsWith("msgstr")) {
				if(msgstr.endsWith("msgstr")){
					msgstr = msgstr.substring(0, msgstr.indexOf("msgstr"));
					msgstr = msgstr.trim();
				}
				msgstr = "msgstr ".concat(msgstr);
			}
			repairedFile.println(msgstr);
		}

		msgidFile.close();
		msgstrFile.close();
		repairedFile.close();
		System.out.println("done");
	}


}
