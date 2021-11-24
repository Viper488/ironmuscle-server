package com.muscle.email.service.impl;

import com.muscle.email.service.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender {
    private final static String FAILED_TO_SEND_EMAIL = "Failed to send an email";
    private final static String EMAIL_TITLE = "Confirm your email";
    private final static String EMAIL_FROM = "noreply@iron.muscles.com";
    private final static String ENCODING = "utf-8";

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, ENCODING);
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(EMAIL_TITLE);
            helper.setFrom(EMAIL_FROM);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }

    public static String buildUserEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public static String buildPasswordEmail(String name, String link) {
        return "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"  style=\"border-collapse:collapse;width:100%;min-width:100%;height:auto\">\n"+
                "<tbody>\n"+
                "<tr>\n"+
                "<td width=\"100%\" valign=\"top\" bgcolor=\"#ffffff\" style=\"padding-top:20px\">\n"+
                "<table width=\"580\" class=\"m_-5530667538910658334deviceWidth\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" bgcolor=\"#ffffff\" style=\"border-collapse:collapse;margin:0 auto\">\n"+
                "<tbody>\n"+
                "<tr>\n"+
                "<td style=\"font-size:13px;color:#282828;font-weight:normal;text-align:left;font-family:'Open Sans',sans-serif;line-height:24px;vertical-align:top;padding:15px 8px 10px 8px\" bgcolor=\"#ffffff\">\n"+
                "<h1 style=\"text-align:center;font-weight:600;margin:30px 0 50px 0\"><span class=\"il\">PASSWORD</span> RESET REQUEST</h1>\n"+
                "<p>Dear "+ name +",</p>\n"+
                "<p>We have received your request to reset your <span class=\"il\">password</span>. Please click the link below to complete the reset. <b>This password reset is only valid for the next 24 hours.</b></p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"padding-bottom:30px\">\n"+
                "<a href=\"" + link +"\" style=\"padding:10px;width:300px;display:block;text-decoration:none;border:1px solid #ff6c37;text-align:center;font-weight:bold;font-size:14px;font-family:'Open Sans',sans-serif;color:#ffffff;background:#ff6c37;border-radius:5px; line-height:17px;margin:0 auto\" target=\"_blank\">\n"+
                "Reset My <span class=\"il\">Password</span>\n"+
                "</a>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"font-family:'Open Sans',sans-serif;font-size:13px;padding:0px 10px 0px 10px;text-align:left\">\n"+
                "<p>If you need additional assistance, or you did not make this <span class=\"il\">change</span>, please contact \n"+
                "<a href=\"mailto:help@ironmuscle.com\" style=\"color:#ff6c37;text-decoration:underline;font-weight:bold\" target=\"_blank\">help@ironmuscle.com</a>.</p>\n"+
                "<p>Cheers,<br>The IronMuscle Team</p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody></table>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody>\n"+
                "</table>";
    }

    public static String buildUserPasswordEmail(String name, String link) {
        return "<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"  style=\"border-collapse:collapse;width:100%;min-width:100%;height:auto\">\n"+
                "<tbody>\n"+
                "<tr>\n"+
                "<td width=\"100%\" valign=\"top\" bgcolor=\"#ffffff\" style=\"padding-top:20px\">\n"+
                "<table width=\"580\" class=\"m_-5530667538910658334deviceWidth\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\" bgcolor=\"#ffffff\" style=\"border-collapse:collapse;margin:0 auto\">\n"+
                "<tbody>\n"+
                "<tr>\n"+
                "<td style=\"font-size:13px;color:#282828;font-weight:normal;text-align:left;font-family:'Open Sans',sans-serif;line-height:24px;vertical-align:top;padding:15px 8px 10px 8px\" bgcolor=\"#ffffff\">\n"+
                "<h1 style=\"text-align:center;font-weight:600;margin:30px 0 50px 0\"><span class=\"il\">PASSWORD</span> CREATE ACCOUNT REQUEST</h1>\n"+
                "<p>Dear "+ name +",</p>\n"+
                "<p>We are happy to welcome you in <span class=\"il\">Iron Muscle</span> family. Please click the link below to complete the creation of your account. <b>This link is only valid for the next 24 hours.</b></p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"padding-bottom:30px\">\n"+
                "<a href=\"" + link +"\" style=\"padding:10px;width:300px;display:block;text-decoration:none;border:1px solid #ff6c37;text-align:center;font-weight:bold;font-size:14px;font-family:'Open Sans',sans-serif;color:#ffffff;background:#ff6c37;border-radius:5px; line-height:17px;margin:0 auto\" target=\"_blank\">\n"+
                "Create My <span class=\"il\">Account</span>\n"+
                "</a>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"font-family:'Open Sans',sans-serif;font-size:13px;padding:0px 10px 0px 10px;text-align:left\">\n"+
                "<p>If you need additional assistance, or you did not make this <span class=\"il\">change</span>, please contact \n"+
                "<a href=\"mailto:help@ironmuscle.com\" style=\"color:#ff6c37;text-decoration:underline;font-weight:bold\" target=\"_blank\">help@ironmuscle.com</a>.</p>\n"+
                "<p>Cheers,<br>The IronMuscle Team</p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody></table>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody>\n"+
                "</table>";
    }
}
