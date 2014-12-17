package de.th.wildau.recruiter.ejb.test;

import java.util.Formatter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class StringCleanTest {

	private static final String dirty = "<script type=\"text/javascript\">alert('test');</script>"
			+ "John is awesome. <script type=\"text/javascript\"> alert('test');</script>"
			+ "<script type=\"text/javascript\">alert('test');</script> X=Y, Y=\"ZZ\"";

	// "0x0041"
	// "日本語"
	// \u65e5\u672c\u8a9e
	private static final String unicode = "日本語";

	public static String escapeUnicode(String input) {
		StringBuilder b = new StringBuilder(input.length());
		Formatter f = new Formatter(b);
		for (char c : input.toCharArray()) {
			if (c < 128) {
				b.append(c);
			} else {
				f.format("\\u%04x", (int) c);
			}
		}
		f.close();
		return b.toString();
	}

	private static String escapeNonAscii(String str) {
		StringBuilder retStr = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			int cp = Character.codePointAt(str, i);
			int charCount = Character.charCount(cp);
			if (charCount > 1) {
				i += charCount - 1;
				if (i >= str.length()) {
					throw new IllegalArgumentException("truncated unexpectedly");
				}
			}

			if (cp < 128) {
				retStr.appendCodePoint(cp);
			} else {
				retStr.append(String.format("\\u%x", cp));
			}
		}
		return retStr.toString();
	}

	@SuppressWarnings("unused")
	private static void prepare(final String userId) {
		// https://duckduckgo.com/?q=java+check+string+against+sql+injection&ia=qa
		// https://stackoverflow.com/questions/2447717/preparedstatement-alternative-within-jpa

		// Connection con = new Object();
		// String selectStatement = "SELECT * FROM User WHERE userId = ? ";
		// PreparedStatement prepStmt = con.prepareStatement(selectStatement);
		// prepStmt.setString(1, userId);
		// ResultSet rs = prepStmt.executeQuery();
	}

	public static void main(String[] args) {

		String clean = Jsoup.clean(dirty, Whitelist.none());

		System.out.println("dirty: " + dirty);
		System.out.println("##########################");

		System.out.println("clean: " + clean);
		System.out.println("##########################");

		System.out.println(unicode);
		System.out.println(escapeNonAscii(unicode));
		System.out.println(escapeUnicode(unicode));
		System.out.println("##########################");

		String escaptedStrJapanese = StringEscapeUtils.escapeJava("日本語");
		System.out.println(escaptedStrJapanese);
		System.out.println(StringEscapeUtils.escapeHtml4(dirty));

	}
}
