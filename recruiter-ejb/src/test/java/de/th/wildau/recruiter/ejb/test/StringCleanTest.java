package de.th.wildau.recruiter.ejb.test;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class StringCleanTest {

	private static final String dirty = "\u672c &#208; 日本語 - pre<script type=\"text/javascript\">alert('test');</script>"
			+ "John is awesome. <script type=\"text/javascript\"> alert('test');</script>"
			+ "<script type=\"text/javascript\">alert('test');</script> X=Y, Y=\"ZZ\" <b>bold</strong> post";

	public static void main(final String[] args) throws DecoderException {

		System.out.println("dirty: " + dirty);
		System.out.println("##########################");

		final String clean = Jsoup.clean(dirty, Whitelist.none());
		System.out.println("clean: " + clean);
		System.out.println("##########################");

		System.out.println(StringEscapeUtils.escapeEcmaScript(Jsoup.clean(
				dirty, Whitelist.none())));
		System.out.println("##########################");

		// System.out.println(Hex.decodeHex(dirty.toCharArray()));

		System.out.println(StringEscapeUtils
				.unescapeHtml4(("\u00D0 \u672C \u65E5\u672C\u8A9E")));
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
}
