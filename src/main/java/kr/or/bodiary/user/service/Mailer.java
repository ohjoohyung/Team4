package kr.or.bodiary.user.service;

import java.io.StringWriter;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.or.bodiary.user.dto.EmailDto;

@Service
public class Mailer {
   // JavaMailSenderImpl을 class로지정
   private JavaMailSender mailSender;

   /* private Mailer mailer; */

	@Autowired
   private VelocityEngine velocityEngine;

	@Autowired
   public void setMailSender(JavaMailSender mailSender) {
      this.mailSender = mailSender;
   }

   public void setVelocityEngine(VelocityEngine velocityEngine) {
      this.velocityEngine = velocityEngine;
   }

   public void sendMail(EmailDto mail, String key) {
      
      //String userid = principal.getName();
      // SimpleMailMessage message = new SimpleMailMessage();
      MimeMessage message = mailSender.createMimeMessage();
      System.out.println(mailSender.createMimeMessage());
      // JavaMailSender 인터페이스에서
      // createMimeMessage()를 이용해서 주소 받기

      try {
         message.setFrom(new InternetAddress(mail.getMailFrom()));
         message.addRecipient(RecipientType.TO, new InternetAddress(mail.getMailTo()));
         message.setSubject(mail.getMailSubject());
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      try {
         System.out.println("getFrom : " + message.getFrom());
         System.out.println("setSubject : " + message.getSubject());
      } catch (MessagingException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }

      Template template = velocityEngine.getTemplate(mail.getTemplateName(), "UTF-8");

      VelocityContext velocityContext = new VelocityContext();
      velocityContext.put("key", key);   
      //velocityContext.put("userid", userid);

      StringWriter stringWriter = new StringWriter();

      template.merge(velocityContext, stringWriter);

      try {
         message.setText(stringWriter.toString(), "utf-8");
         message.setContent(stringWriter.toString(), "text/html; charset=MS949");
      } catch (MessagingException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      mailSender.send(message);
   }
}