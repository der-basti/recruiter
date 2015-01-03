package de.th.wildau.recruiter.ejb.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class HashTest {

	@Test
	public void hashPw4Db() {
		gen("passwd123", "7MkZdGswgzvk1cDocG4v",
				"fsbv1UooTJ3hPChjo5HgY9T5caG6TG4xY7Qb10k2T8E=");

	}

	private void gen(final String pw, final String salt, final String result) {
		Assert.assertEquals(hashNEW(pw + salt), result);
	}

	private String hashBaseSha(final String value) {
		return Base64.getEncoder().encodeToString(hashSha(value).getBytes());
	}

	private String hashNEW(final String pw) {
		try {
			final byte[] hash = MessageDigest.getInstance("SHA-256").digest(
					pw.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	private String hashSha(final String value) {
		return Hashing.sha256().hashString(value, Charsets.UTF_8).toString();
	}

}