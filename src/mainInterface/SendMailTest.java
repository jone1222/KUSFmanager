package mainInterface;

import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTest {
 
    public SendMailTest() {
    };
    public SendMailTest(String mailaddr, String reserve_data) {
         
        Properties p = System.getProperties();
        p.put("mail.smtp.starttls.enable", "true");     // gmail�� ������ true ����
        p.put("mail.smtp.host", "smtp.gmail.com");      // smtp ���� �ּ�
        p.put("mail.smtp.auth","true");                 // gmail�� ������ true ����
        p.put("mail.smtp.port", "587");                 // gmail ��Ʈ
           
        Authenticator auth = new MyAuthentication();
         
        //session ���� ��  MimeMessage����
        Session session = Session.getDefaultInstance(p, auth);
        MimeMessage msg = new MimeMessage(session);
         
        try{
            //���������ð�
            msg.setSentDate(new Date());
             
            InternetAddress from = new InternetAddress() ;
             
             
            from = new InternetAddress("kusmartfactory<kusmartfactory@gmail.com>");
             
            // �̸��� �߽���
            msg.setFrom(from);
             
             
            // �̸��� ������
            InternetAddress to = new InternetAddress(mailaddr);
            msg.setRecipient(Message.RecipientType.TO, to);
             
            // �̸��� ����
            msg.setSubject("�Ǳ����б� ����Ʈ���丮 ������ �Ϸ�Ǿ����ϴ�", "UTF-8");
             
            // �̸��� ����
            msg.setText(reserve_data, "UTF-8");
             
            // �̸��� ���
            msg.setHeader("content-Type", "text/html");
             
            //���Ϻ�����
            javax.mail.Transport.send(msg);
             
        }catch (AddressException addr_e) {
            addr_e.printStackTrace();
        }catch (MessagingException msg_e) {
            msg_e.printStackTrace();
        }
    }
 
}
 
 
class MyAuthentication extends Authenticator {
      
    PasswordAuthentication pa;
    
 
    public MyAuthentication(){
         
        String id = "kusmartfactory@gmail.com";       // ���� ID
        String pw = "kusf1234";          				// ���� ��й�ȣ
 
        // ID�� ��й�ȣ�� �Է��Ѵ�.
        pa = new PasswordAuthentication(id, pw);
      
    }
 
    // �ý��ۿ��� ����ϴ� ��������
    public PasswordAuthentication getPasswordAuthentication() {
        return pa;
    }
}
