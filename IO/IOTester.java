import java.io.*;
import java.util.*;

public class IOTester {

	static String type;
	static String entry;
	static Dictionary<String, String> dict;

	public static void main(String[] args) throws Exception {
		FileReader fr = new FileReader("sampleDict");

		int i;
		String strm = "";
		while ((i = fr.read()) != -1) {
			strm = strm + (char) i;
		}

		i = 0;
		dict = new Hashtable<String, String>();
		while (i<strm.length()) {
			i = lexer(strm, i);

			if (type.length() > 0) {
				System.out.print("(" + type + ") " + entry + "\n");

				if (!type.contains("comment")) {
					hasher(entry);
				}
			}
		}

		Enumeration k = dict.keys();
		Enumeration e = dict.elements();
		for (; k.hasMoreElements();) {
			System.out.println(k.nextElement() + " " + e.nextElement());
		}
	}

	public static void hasher(String str) {
		int i = 0;
		String key = "";
		while (true) {
			char c = str.charAt(i++);
			key = key + c;

			if (c == ' ' || c == '\n' || c == '\t') {
				break;
			}
		}

		String val = "";
		for (int ind=i; ind<str.length(); ind++) {
			if (str.charAt(ind) == ' ' || str.charAt(ind) == '\t') {
				// do nothing
			}
			else {
				val = val + str.charAt(i++);
			}
		}

		dict.put(key, val);
	}

	public static int lexer
	(
	 	String strm, 
		int is
	) {
		int i = is;
		type = "";
		entry = "";
		if (strm.charAt(i) == '/' && strm.charAt(i+1) == '/') {
			type = "comment";
			i = is+2;
			while (strm.charAt(i) != '\n') {
				entry = entry + strm.charAt(i++);
			}
		}
		else if (strm.charAt(i) == '/' && strm.charAt(i+1) == '*') {
			type = "comment";
			i = is+2;
			while (true) {
				if (strm.charAt(i) == '*' && strm.charAt(i+1) == '/') {
					break;
				}
				entry = entry + strm.charAt(i++);
			}
		}
		else if (Character.isLetter(strm.charAt(i))) {
			type = "entry";
			while (strm.charAt(i) != ';') {
				entry = entry + strm.charAt(i++);
			}
		}
		else {
			i++;
		}

		return i;
	}
}
