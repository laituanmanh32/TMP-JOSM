import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		POFileHandler.repairMsgstrFile(new File("msgid.po"), new File("msgstr.po"));
		POFileHandler.mergePOFile(new File("josm_josm-vi.po"), new File("repaiedmsgstr.po"), new File("vi.po"));
	}

}
