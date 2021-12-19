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
                "<p>Complete signing up by clicking <span class=\"il\">\"Verify My account\"</span><b>. This link is only valid for the next 24 hours.</b></p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"padding-bottom:30px\">\n"+
                "<a href=\"" + link +"\" style=\"padding:10px;width:300px;display:block;text-decoration:none;border:1px solid #0eb05f;text-align:center;font-weight:bold;font-size:14px;font-family:'Open Sans',sans-serif;color:#ffffff;background:#0eb05f;border-radius:5px; line-height:17px;margin:0 auto\" target=\"_blank\">\n"+
                "Verify My <span class=\"il\">Account</span>\n"+
                "</a>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"font-family:'Open Sans',sans-serif;font-size:13px;padding:0px 10px 0px 10px;text-align:left\">\n"+
                "<p>If you need additional assistance, or you did not make this <span class=\"il\">change</span>, please contact \n"+
                "<a href=\"mailto:help@ironmuscle.com\" style=\"color:#0eb05f;text-decoration:underline;font-weight:bold\" target=\"_blank\">help@ironmuscle.com</a>.</p>\n"+
                "<p>Cheers,<br>The IronMuscle Team</p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody></table>\n"+
                "</td>\n"+
                "</tr>\n"+
                "</tbody>\n"+
                "</table>";
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
                "<p>We have received your request to reset your <span class=\"il\">password</span>. Please click the link below to complete the reset. <b>This password reset link is only valid for the next 24 hours.</b></p>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"padding-bottom:30px\">\n"+
                "<a href=\"" + link +"\" style=\"padding:10px;width:300px;display:block;text-decoration:none;border:1px solid #0eb05f;text-align:center;font-weight:bold;font-size:14px;font-family:'Open Sans',sans-serif;color:#ffffff;background:#0eb05f;border-radius:5px; line-height:17px;margin:0 auto\" target=\"_blank\">\n"+
                "Reset My <span class=\"il\">Password</span>\n"+
                "</a>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"font-family:'Open Sans',sans-serif;font-size:13px;padding:0px 10px 0px 10px;text-align:left\">\n"+
                "<p>If you need additional assistance, or you did not make this <span class=\"il\">change</span>, please contact \n"+
                "<a href=\"mailto:help@ironmuscle.com\" style=\"color:#0eb05f;text-decoration:underline;font-weight:bold\" target=\"_blank\">help@ironmuscle.com</a>.</p>\n"+
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
                "<a href=\"" + link +"\" style=\"padding:10px;width:300px;display:block;text-decoration:none;border:1px solid #0eb05f;text-align:center;font-weight:bold;font-size:14px;font-family:'Open Sans',sans-serif;color:#ffffff;background:#0eb05f;border-radius:5px; line-height:17px;margin:0 auto\" target=\"_blank\">\n"+
                "Create My <span class=\"il\">Account</span>\n"+
                "</a>\n"+
                "</td>\n"+
                "</tr>\n"+
                "<tr>\n"+
                "<td style=\"font-family:'Open Sans',sans-serif;font-size:13px;padding:0px 10px 0px 10px;text-align:left\">\n"+
                "<p>If you need additional assistance, or you did not make this <span class=\"il\">change</span>, please contact \n"+
                "<a href=\"mailto:help@ironmuscle.com\" style=\"color:#0eb05f;text-decoration:underline;font-weight:bold\" target=\"_blank\">help@ironmuscle.com</a>.</p>\n"+
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
