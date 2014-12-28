package de.th.wildau.recruiter.ejb.service;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class MailService implements Serializable {

	private static final long serialVersionUID = 7345551127477024503L;

	protected static final String LINE_BREAK = System
			.getProperty("line.separator");

	private final Logger log = LoggerFactory.getLogger(MailService.class);

	@Resource(name = "java:jboss/mail/recruiterMail")
	private Session session;

	/**
	 * Send a text mail.
	 * 
	 * @param addresses
	 * @param topic
	 * @param textMessage
	 */
	@PermitAll
	public void send(final String topic, final String textMessage,
			final String addressTo) {
		try {
			send(topic, textMessage, InternetAddress.parse(addressTo));
		} catch (final AddressException e) {
			this.log.error("Cannot convert email address", e);
		}
	}

	/**
	 * Send mail with the standalone*.xml mail configuration.
	 * 
	 * @param topic
	 * @param textMessage
	 * @param addresses
	 */
	private void send(final String topic, final String textMessage,
			final InternetAddress... addresses) {
		final MimeMessage m = new MimeMessage(this.session);
		try {
			this.session.setDebug(true);
			final Properties p = this.session.getProperties();
			p.put("mail.smtp.auth", "true");
			p.put("mail.smtp.starttls.enable", "true");
			p.put("mail.smtp.tls", "true");

			// p.put("javax.net.debug", "ssl");
			// p.put("mail.transport.protocol", "smtps");
			// // p.put("mail.smtp.socketFactory.class",
			// "javax.net.ssl.SSLSocketFactory");
			// MailSSLSocketFactory sf = new MailSSLSocketFactory();
			// sf.setTrustAllHosts(true);
			// p.put("mail.smtp.ssl.enable", "true");
			// p.put("mail.smtp.ssl.socketFactory", sf);
			// p.put("mail.smtp.ssl.checkserveridentity", "true");
			// XXX th-wildau doesn't support smtp ssl transport
			// (AUTH_LOGIN_PLAIN)
			// http://www.tutorialspoint.com/javamail_api/javamail_api_smtp_servers.htm

			m.setHeader("X-Mailer", "recruiter-mail");
			m.setFrom("recruiter@th-wildau.de");
			m.setRecipients(Message.RecipientType.TO, addresses);
			m.setSentDate(new java.util.Date());
			m.setSubject(topic);
			m.setContent(textMessage, "text/plain;charset=utf-8");
			m.saveChanges();

			Transport.send(m);

			// SMTPSSLTransport t = (SMTPSSLTransport)
			// this.session.getTransport("smtps");
			// t.connect("smtp.th-wildau.de", "USER", "PASSWORD");
			// m.saveChanges();
			// t.sendMessage(m, m.getAllRecipients());
			// t.close();
		} catch (final MessagingException e) {
			this.log.error("Cannot send mail", e);
		}
	}
}
