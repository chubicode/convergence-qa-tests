package otp

import javax.mail.*
import java.util.Properties
import java.util.regex.Matcher
import java.util.regex.Pattern

class OTPHelper {

	static String fetchOTP() {

		String host = "imap.gmail.com"
		String username = "apisupport@cicod.com"
		String password = "gruf zgla wfct subf "

		Properties props = new Properties()
		props.put("mail.store.protocol", "imaps")

		Session session = Session.getInstance(props, null)

		Store store = session.getStore("imaps")
		store.connect(host, username, password)

		Folder inbox = store.getFolder("INBOX")
		inbox.open(Folder.READ_ONLY)

		Message[] messages = inbox.getMessages()

		// Get latest email
		Message latestMessage = messages[messages.length - 1]

		String content = latestMessage.getContent().toString()

		println("EMAIL CONTENT:")
		println(content)

		// Extract 6-digit OTP
		Pattern pattern = Pattern.compile("\\b\\d{6}\\b")
		Matcher matcher = pattern.matcher(content)

		String otp = ""

		if (matcher.find()) {
			otp = matcher.group()
		}

		println("OTP FOUND: " + otp)

		inbox.close(false)
		store.close()

		return otp
	}
}