package ru.kroshchenko.ruven.service;

import ru.kroshchenko.ruven.utils.ClassNameUtil;
import org.apache.commons.mail.*;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kroshchenko
 *         2016.01.20
 */
public class MailService {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    private static MailService ourInstance;
    private ExecutorService executorService;
    private Properties properties;

    public static MailService getInstance() {
        if (ourInstance != null) {
            return ourInstance;
        } else {
            throw new IllegalStateException("Mail service not initialized");
        }
    }

    private MailService(Properties properties) {
        this.properties = properties;
        executorService = Executors.newFixedThreadPool(5);
    }

    public void send(String from, String fromName, String to, String subject, String message) {
        executorService.submit(new SendEmailTask(from, fromName, to, subject, message));
    }

    public static void init(Properties properties) {
        ourInstance = new MailService(properties);
    }


    private class SendEmailTask implements Runnable {
        private String from;
        private String fromName;
        private String to;
        private String sbj;
        private String msg;

        SendEmailTask(String from, String fromName, String to, String sbj, String msg) {
            this.msg = msg;
            this.sbj = sbj;
            this.to = to;
            this.from = from;
            this.fromName = fromName;
        }

        @Override
        public void run() {
            try {
                final String PROPERTY_AUTH = "EMAIL.AUTH";
                final String PROPERTY_SMTP_HOST = "EMAIL.SMTP-HOST";
                final String PROPERTY_USERNAME = "EMAIL.USERNAME";
                final String PROPERTY_PASSWORD = "EMAIL.PASSWORD";
                final String PROPERTY_SSL = "EMAIL.SSL";
                final String PROPERTY_SMTP_PORT = "EMAIL.SMTP_PORT";

                Email email = new SimpleEmail();
                email.setCharset(EmailConstants.UTF_8);
                email.setHostName(properties.getProperty(PROPERTY_SMTP_HOST));
                boolean auth = Boolean.parseBoolean(properties.getProperty(PROPERTY_AUTH));
                if (auth) {
                    String userName = properties.getProperty(PROPERTY_USERNAME);
                    String password = (properties.getProperty(PROPERTY_PASSWORD) == null) ? "" : properties.getProperty(PROPERTY_PASSWORD);
                    if (userName == null) {
                        logger.info("Can not send email to " + to + ". Because smtp username didn't set.");
                    } else {
                        email.setAuthenticator(new DefaultAuthenticator(
                                userName, password));
                    }
                }
                boolean ssl = Boolean.parseBoolean(properties.getProperty(PROPERTY_SSL));
                email.setSSLOnConnect(ssl);
                final String port = (ssl) ? "465" : "587";
                email.setSmtpPort(Integer.parseInt(properties.getProperty(PROPERTY_SMTP_PORT, port)));
                email.setFrom(from, fromName);
                email.setSubject(sbj);
                email.setMsg(msg);
                email.addTo(to);
                email.send();
            } catch (EmailException e) {
                logger.info("********");
                logger.info(e.getMessage());
                logger.info("********");
            }
        }
    }
}
