package com.enviro.assessment.inter001.sthembisobuthelezi.service.implemenation;

import com.enviro.assessment.inter001.sthembisobuthelezi.enums.HttpStatusEnum;
import com.enviro.assessment.inter001.sthembisobuthelezi.exception.CustomeException;
import com.enviro.assessment.inter001.sthembisobuthelezi.service.CommonService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommonServiceImplementation implements CommonService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment env;


    @Override
    public boolean passwordMatchesConfirmation(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public String sendingEmailForForgotPassword(String emailTo, String firstName, String pin) throws IOException {
        String senderEmail = "sthembisobuthelezi774@gmail.com";
        String appPassword = "owzg byol xxvc onar";

        String recipientEmail = emailTo;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a Session object with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress(senderEmail));

            // Set the recipient's email address
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set the email subject
            message.setSubject("Verification Code");

            // Load the FTL template
            Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            config.setClassForTemplateLoading(this.getClass(), "/templates"); // Assuming your templates are in the "/templates" directory
            Template template = config.getTemplate("email-template-forgot-password.ftl");

            // Create a data model for the template
            Map<String, Object> model = new HashMap<>();
            model.put("firstName", firstName);
            model.put("pin", pin);

            // Merge the template with the data model to generate the HTML content
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            String htmlContent = writer.toString();

            // Set the email content as HTML
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException | TemplateException e) {
            e.printStackTrace();
        }

        return senderEmail;
    }

    private Map<String, String> emailToPinMap = new HashMap<>();

    public String generatePinAndAssociate(String email) {
        // Generate a 5-digit numeric pin
        String pin = RandomStringUtils.randomNumeric(5);

        // Associate the email with the generated pin
        emailToPinMap.put(email, pin);

        return pin;
    }


    @Override
    public String sendingEmailForVerification(String emailTo, String firstName, String pin) throws IOException {
        String senderEmail = "sthembisobuthelezi774@gmail.com";
        String appPassword = "owzg byol xxvc onar"; // If you have two-factor authentication enabled

        // Recipient's email address
        String recipientEmail = emailTo;

        // Set up the properties for the Gmail SMTP server
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Gmail SMTP port for STARTTLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        // Create a Session object with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set the sender's email address
            message.setFrom(new InternetAddress(senderEmail));

            // Set the recipient's email address
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set the email subject
            message.setSubject("Verification Code");

            // Load the FTL template
            Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            config.setClassForTemplateLoading(this.getClass(), "/templates");
            Template template = config.getTemplate("email-template.ftl");

            // Create a data model for the template
            Map<String, Object> model = new HashMap<>();
            model.put("firstName", firstName);
            model.put("pin", pin);

            // Merge the template with the data model to generate the HTML content
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            String htmlContent = writer.toString();

            // Set the email content as HTML
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException | TemplateException e) {
            e.printStackTrace();
        }

        return senderEmail;
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    public String getMessage(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        String respMessage = null;
        try {
            respMessage = messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            respMessage = new MessageFormat(env.getProperty(key)).format(args);
        }
        return respMessage;
    }

    @Override
    public void isValidPassword(String password) {
        StringBuilder errorMessage = new StringBuilder("The password is incorrect. It must contain:");

        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasMinLength = password.length() >= 8;

        if (!hasLowerCase) {
            throw new CustomeException().setDeveloperMessage(HttpStatusEnum.SECOND_NAME_ERROR.getDeveloperMessage())
                    .setResponseCode(500)
                    .setResponseMessage("\n- Password at least one lowercase letter (abcdef..).")
                    .setStatusCode(HttpStatus.NOT_FOUND);
        }
        if (!hasUpperCase) {
            throw new CustomeException().setDeveloperMessage(HttpStatusEnum.SECOND_NAME_ERROR.getDeveloperMessage())
                    .setResponseCode(500)
                    .setResponseMessage("\n- Password at least one uppercase letter (ABCDEF...).")
                    .setStatusCode(HttpStatus.NOT_FOUND);
        }
        if (!hasDigit) {
            throw new CustomeException().setDeveloperMessage(HttpStatusEnum.SECOND_NAME_ERROR.getDeveloperMessage())
                    .setResponseCode(500)
                    .setResponseMessage("\n- Password at least one digit (0,1,2,4,5,6,7,8,9).")
                    .setStatusCode(HttpStatus.NOT_FOUND);
        }
        if (!hasMinLength) {
            throw new CustomeException().setDeveloperMessage(HttpStatusEnum.SECOND_NAME_ERROR.getDeveloperMessage())
                    .setResponseCode(500)
                    .setResponseMessage("\n- Password Minimum length of 8 characters.")
                    .setStatusCode(HttpStatus.NOT_FOUND);
        }

    }

}
