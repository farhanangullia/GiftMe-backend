package util.email;

import ejb.session.stateless.PromotionControllerLocal;
import entity.Customer;
import entity.Delivery;
import entity.Promotion;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailManager 
{
    private final String emailServerName = "mailauth.comp.nus.edu.sg";     
    private final String mailer = "JavaMailer";
    private String smtpAuthUser;
    private String smtpAuthPassword;
    
    @EJB
    private PromotionControllerLocal promotionControllerLocal;
    
    
    
    public EmailManager()
    {
    }

    
    
    public EmailManager(String smtpAuthUser, String smtpAuthPassword)
    {
        this.smtpAuthUser = smtpAuthUser;
        this.smtpAuthPassword = smtpAuthPassword;
    }
    
    
    
    public Boolean emailDelivery(String fromEmailAddress, String toEmailAddress, Delivery delivery)
    {
     //   String emailBody = "Hello World! I am sent from a Java Web Application with JavaMail!";
        System.out.println("IN HERE");
        String firstName = delivery.getTransaction().getCustomer().getFirstName();
        String lastName = delivery.getTransaction().getCustomer().getLastName();
        String deliveryCode = delivery.getDeliveryCode();
        String customerName = firstName + " " + lastName;
        
        System.out.println("LOL HERE");
        
        String customerAddress = delivery.getCustomerAddress();
        String shopAddress = delivery.getShopAddress();
        String deliveryStatus = delivery.getDeliveryStatus();
        String timeToArrival = delivery.getTimeToArrival();
        String distance = delivery.getDistanceAway();
        BigDecimal amount = delivery.getTransaction().getTotalAmount();
      
        BigDecimal discount = delivery.getTransaction().getDiscount();
        BigDecimal amt = amount.add(discount);
        BigDecimal delFee = delivery.getTransaction().getDeliveryFee();
        amt = amt.subtract(delFee);
        
          String promotionDiscount = delivery.getTransaction().getDiscount().toString();
        String subTotal = amt.toString();
         String finalAmount = delivery.getTransaction().getTotalAmount().toString();
         
        String transactionDate = delivery.getTransaction().getTransactionDateTime().toString();
       
        System.out.println("HERE LA");
        
        String deliveryFee = delivery.getTransaction().getDeliveryFee().toString();
        if(deliveryFee.equals("0.00"))
            deliveryFee = "FREE";
        else
            deliveryFee = "S$"+deliveryFee;
        
        
        System.out.println("UM HERE");
        try 
        {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");            
            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
          //  session.setDebug(true);            
            Message msg = new MimeMessage(session);
                                    
            if (msg != null)
            {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress, false));
            
                msg.setSubject("All good—here’s the delivery details for your gift order " + deliveryCode);
               // msg.setText(emailBody);
              
               msg.setContent("<div style=\"color:#212121;font-family:&quot;Helvetica Neue&quot;,&quot;Helvetica&quot;,&quot;Arial&quot;,sans-serif;font-weight:normal;margin:0;padding:0;text-align:center;line-height:1.3;font-size:14px;line-height:19px\">\n" +
"  <table class=\"m_4953874785919959836body\" style=\"border-collapse:collapse;height:100%;width:100%;min-width:600px;table-layout:fixed;background-color:#eee;color:#212121;font-family:&quot;Helvetica Neue&quot;,&quot;Helvetica&quot;,&quot;Arial&quot;,sans-serif;font-weight:normal;margin:0;padding:0;text-align:center;line-height:1.3;font-size:14px;line-height:19px;border-spacing:0!important\">\n" +
"    <tbody><tr>\n" +
"      <td class=\"m_4953874785919959836center\" align=\"center\" valign=\"top\" style=\"vertical-align:top;text-align:center\">\n" +
"\n" +
"        <table class=\"m_4953874785919959836header m_4953874785919959836container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;height:80px;margin-top:20px;border-spacing:0!important\">\n" +
"          <tbody><tr>\n" +
"            <td style=\"vertical-align:top\">\n" +
"              <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"                    <table class=\"m_4953874785919959836twelve m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:580px;border-spacing:0!important\">\n" +
"                      <tbody><tr>\n" +
"                        <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                          <img alt=\"giftme\" class=\"m_4953874785919959836center CToWUd\" src=\"http://i67.tinypic.com/107kgtx.png\" style=\"margin:0 auto;margin-top:15px;margin-bottom:4px;width:34%;max-width:250px;height:auto;float:none;clear:none;display:inline-block\">\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                    </tbody></table>\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody></table>\n" +
"\n" +
"        <table class=\"m_4953874785919959836customer-completed-order m_4953874785919959836container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top\">\n" +
"\n" +
"      <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"            <table class=\"m_4953874785919959836twelve m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:580px;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_4953874785919959836hero\" style=\"vertical-align:top;padding:0px 0px 10px;text-align:center;padding:0px 40px\">\n" +
"                  <h2 class=\"m_4953874785919959836no-margin-top\" style=\"font-weight:normal;word-break:normal;line-height:normal;font-size:20px;margin-top:0\">\n" +
"                   Hi " + firstName + ", we are fulfilling your delivery!\n" +
"                    \n" +
"                  </h2>\n" +
"                </td>\n" +
"                <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"        <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"          <tbody><tr>\n" +
"            <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"              <table class=\"m_4953874785919959836ten m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:480px;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td class=\"m_4953874785919959836twelve m_4953874785919959836sub-columns m_4953874785919959836panel m_4953874785919959836fulfilled-items-header\" style=\"vertical-align:top;background:#f2f2f2;border:1px solid #d9d9d9;min-width:0px;padding:0px 0px 10px;background-color:#ffffff;background-color:#f5f5f5;color:#000000;text-align:left;padding-left:10px;font-size:16px;padding-right:10px;width:100%;padding:10px!important\">\n" +
"                    <p style=\"margin:0 0 5px 0\">\n" +
"                    <strong>Delivery Code: " +  deliveryCode + "</strong>\n" +
"                    </p>\n" +
"                  </td>\n" +
"                  <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"                </tr>\n" +
"                      <tr>\n" +
"                      </tr>\n" +
"                      <tr>\n" +
"                        <td class=\"m_4953874785919959836twelve m_4953874785919959836sub-columns m_4953874785919959836panel m_4953874785919959836product-detail\" style=\"vertical-align:top;background:#f2f2f2;border:1px solid #d9d9d9;min-width:0px;padding:0px 0px 10px;background-color:#ffffff;padding-right:10px;width:100%;padding:10px!important\">\n" +
"                          <table class=\"m_4953874785919959836ten m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:480px;margin:0px;table-layout:fixed;border-spacing:0!important\">\n" +
"                            <tbody><tr>\n" +
"                              <td class=\"m_4953874785919959836five m_4953874785919959836sub-columns m_4953874785919959836align-left\" style=\"vertical-align:top;text-align:left;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:41.666666%\">\n" +
"                                <p style=\"margin:0 0 5px 0\"><strong>Store Address</strong></p>\n" +
"                                  <p style=\"margin:0 0 5px 0\">\n" +
"                                     <span class=\"m_4953874785919959836product-price\" style=\"text-align:left;color:#a1a1a1\">"+shopAddress+ "</span>\n" +
"                                  </p>\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836one m_4953874785919959836column\" style=\"vertical-align:top;padding:0px 0px 10px;width:8.333333%\">\n" +
"                                &nbsp;\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836five m_4953874785919959836sub-columns m_4953874785919959836align-left\" style=\"vertical-align:top;text-align:right;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:41.666666%\">\n" +
"                                <p style=\"margin:0 0 5px 0\"><strong>Your Address</strong></p>\n" +
"                                  <p style=\"margin:0 0 5px 0\">\n" +
"                                     <span class=\"m_4953874785919959836product-price\" style=\"text-align:left;color:#a1a1a1\">"+customerAddress+ "</span>\n" +
"                                  </p>\n" +
"                              </td>\n" +
"                            </tr>\n" +
"                          </tbody></table>\n" +
"                        </td>\n" +
"                      </tr>\n" +
"                      <tr>\n" +
"                        <td class=\"m_4953874785919959836twelve m_4953874785919959836sub-columns m_4953874785919959836panel m_4953874785919959836product-detail\" style=\"vertical-align:top;background:#f2f2f2;border:1px solid #d9d9d9;min-width:0px;padding:0px 0px 10px;background-color:#ffffff;padding-right:10px;width:100%;padding:10px!important\">\n" +
"                          <table class=\"m_4953874785919959836ten m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:480px;margin:0px;table-layout:fixed;border-spacing:0!important\">\n" +
"                            <tbody><tr>\n" +
"                              <td class=\"m_4953874785919959836five m_4953874785919959836sub-columns m_4953874785919959836align-left\" style=\"vertical-align:top;text-align:left;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:41.666666%\">\n" +
"                                <p style=\"margin:0 0 5px 0\"><strong>Transaction Date</strong></p>\n" +
"                                  <p style=\"margin:0 0 5px 0\">\n" +
"                                     <span class=\"m_4953874785919959836product-price\" style=\"text-align:left;color:#a1a1a1\">"+ transactionDate + "</span>\n" +
"                                  </p>\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836one m_4953874785919959836column\" style=\"vertical-align:top;padding:0px 0px 10px;width:8.333333%\">\n" +
"                                &nbsp;\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836five m_4953874785919959836sub-columns m_4953874785919959836align-left\" style=\"vertical-align:top;text-align:right;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:41.666666%\">\n" +
"                                <p style=\"margin:0 0 5px 0\"><strong>Delivery Status</strong></p>\n" +
"                                  <p style=\"margin:0 0 5px 0\">\n" +
"                                     <span class=\"m_4953874785919959836product-price\" style=\"text-align:left;color:#a1a1a1\">"+ deliveryStatus + "</span>\n" +
"                                  </p>\n" +
"                              </td>\n" +
"                            </tr>\n" +
"                          </tbody></table>\n" +
"                        </td>\n" +
"                      </tr>\n" +
"                      <tr>\n" +
"                        <td class=\"m_4953874785919959836twelve m_4953874785919959836sub-columns m_4953874785919959836panel m_4953874785919959836product-detail\" style=\"vertical-align:top;background:#f2f2f2;border:1px solid #d9d9d9;min-width:0px;padding:0px 0px 10px;background-color:#ffffff;padding-right:10px;width:100%;padding:10px!important\">\n" +
"                          <table class=\"m_4953874785919959836ten m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:480px;margin:0px;table-layout:fixed;border-spacing:0!important\">\n" +
"                            <tbody><tr>\n" +
"                              <td class=\"m_4953874785919959836five m_4953874785919959836sub-columns m_4953874785919959836align-left\" style=\"vertical-align:top;text-align:left;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:41.666666%\">\n" +
"                                <p style=\"margin:0 0 5px 0\"><strong>Time to Arrival</strong></p>\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836one m_4953874785919959836column\" style=\"vertical-align:top;padding:0px 0px 10px;width:8.333333%\">\n" +
"                                &nbsp;\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836two m_4953874785919959836sub-column\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:16.666666%\">\n" +
"                                <p class=\"m_4953874785919959836quantity\" style=\"margin:0 0 5px 0;text-align:left;color:#a1a1a1;white-space:nowrap\">"+distance+"</p>\n" +
"                              </td>\n" +
"                              <td class=\"m_4953874785919959836five m_4953874785919959836sub-columns m_4953874785919959836align-left\" style=\"vertical-align:top;text-align:right;min-width:0px;padding:0px 0px 10px;padding-right:10px;width:41.666666%\">\n" +
"                                <p style=\"margin:0 0 5px 0\"><strong>"+timeToArrival+"</strong></p>\n" +
"                              </td>\n" +
"                            </tr>\n" +
"                          </tbody></table>\n" +
"                        </td>\n" +
"                      </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody></table>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"      <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"            <table class=\"m_4953874785919959836ten m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:480px;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_4953874785919959836twelve m_4953874785919959836sub-columns m_4953874785919959836panel m_4953874785919959836order-subtotals\" style=\"vertical-align:top;background:#f2f2f2;border:1px solid #d9d9d9;min-width:0px;padding:0px 0px 10px;background-color:#ffffff;padding-right:10px;border:none;width:100%;padding:10px!important\">\n" +
"                  <table class=\"m_4953874785919959836ten m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:480px;border-spacing:0!important\">\n" +
"                      <tbody><tr>\n" +
"                        <td class=\"m_4953874785919959836three m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:25%\">\n" +
"                          &nbsp;\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836seven m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:58.333333%\">\n" +
"                          <p style=\"margin:0 0 5px 0\">\n" +
"                            <strong>\n" +
"                              Subtotal\n" +
"                            </strong>\n" +
"                            \n" +
"                          </p>\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836two m_4953874785919959836sub-column\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:16.666666%\">\n" +
"                          <p class=\"m_4953874785919959836amount\" style=\"margin:0 0 5px 0;text-align:right;white-space:nowrap\">S$"+subTotal+"</p>\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                    \n" +
"                      <tr>\n" +
"                        <td class=\"m_4953874785919959836three m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:25%\">\n" +
"                          &nbsp;\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836seven m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:58.333333%\">\n" +
"                          <p style=\"margin:0 0 5px 0\">\n" +
"                            <strong>\n" +
"                              Delivery Fee\n" +
"                            </strong>\n" +
"                            \n" +
"                          </p>\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836two m_4953874785919959836sub-column\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:16.666666%\">\n" +
"                          <p class=\"m_4953874785919959836amount\" style=\"margin:0 0 5px 0;text-align:right;white-space:nowrap\">"+deliveryFee+"</p>\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                      <tr>\n" +
"                        <td class=\"m_4953874785919959836three m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:25%\">\n" +
"                          &nbsp;\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836seven m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:58.333333%\">\n" +
"                          <p style=\"margin:0 0 5px 0\">\n" +
"                          <strong>\n" +
"                            Promotion applied\n" +
"                          </strong>\n" +
"                          </p>\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836two m_4953874785919959836sub-column\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:16.666666%\">\n" +
"                          <p class=\"m_4953874785919959836amount\" style=\"margin:0 0 5px 0;text-align:right;white-space:nowrap\">\n" +
"                            -S$"+promotionDiscount+"\n" +
"                          </p>\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                    <tr>\n" +
"                      <td class=\"m_4953874785919959836twelve m_4953874785919959836sub-columns\" colspan=\"3\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:100%\">&nbsp;</td>\n" +
"                      <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding:0!important\"></td>\n" +
"                    </tr>\n" +
"                    <tr>\n" +
"                      <td class=\"m_4953874785919959836three m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:25%\">\n" +
"                        &nbsp;\n" +
"                      </td>\n" +
"                      <td class=\"m_4953874785919959836seven m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:58.333333%\">\n" +
"                          <p style=\"margin:0 0 5px 0\">\n" +
"                            <strong>\n" +
"                              FINAL TOTAL\n" +
"                            </strong>\n" +
"                            \n" +
"                          </p>\n" +
"                      </td>\n" +
"                      <td class=\"m_4953874785919959836two m_4953874785919959836sub-column\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:16.666666%\">\n" +
"                        <p class=\"m_4953874785919959836amount\" style=\"margin:0 0 5px 0;text-align:right;white-space:nowrap\">\n" +
"                          S$"+finalAmount+"\n" +
"                        </p>\n" +
"                      </td>\n" +
"                      <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding:0!important\"></td>\n" +
"                    </tr>\n" +
"                      <tr>\n" +
"    <td class=\"m_4953874785919959836three m_4953874785919959836sub-columns\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:25%\">&nbsp;</td>\n" +
"    <td class=\"m_4953874785919959836seven m_4953874785919959836sub-columns\" colspan=\"2\" style=\"vertical-align:top;min-width:0px;padding:0px 0px 10px;padding-top:0px;padding-bottom:0px;margin-top:0px;margin-bottom:0px;text-align:left;padding-right:10px;width:58.333333%\">\n" +
"      <em><small style=\"font-size:10px\">Please note: the final price does not include external fees that may be charged by your bank. For any enquiries, please contact your bank directly.</small></em>\n" +
"    </td>\n" +
"  </tr>\n" +
"\n" +
"                  </tbody></table>\n" +
"                  \n" +
"                </td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"          <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0!important\"></td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"        <p class=\"m_4953874785919959836survey-link-title\" style=\"margin:0 0 5px 0;font-family:Arial;font-size:20px;font-weight:bold;margin-top:70px\">\n" +
"          Need help?\n" +
"        </p>\n" +
"        <a class=\"m_4953874785919959836survey-link\" style=\"color:#444444;text-decoration:none;background-color:#2bd665;display:inline-block;height:44px;border-radius:5px;margin-top:20px\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=en&amp;q=http://www.surveygizmo.com/s3/3959124/Tell-us-about-your-last-Food-order-How-did-we-do&amp;source=gmail&amp;ust=1523812061597000&amp;usg=AFQjCNHCRQHl_e-8iIY398UIjNdsuFyfjg\">\n" +
"          <p class=\"m_4953874785919959836survey-link-text\" style=\"margin:0 0 5px 0;margin:0px;font-family:Arial;font-size:16px;color:#fff;padding:13px 24px\">\n" +
"            Your order "+deliveryCode+"\n" +
"          </p>\n" +
"</a>  "+
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"\n" +
"<table class=\"m_4953874785919959836contact-us m_4953874785919959836container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;background:#fbf6ed;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top\">\n" +
"      <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"            <table class=\"m_4953874785919959836twelve m_4953874785919959836columns m_4953874785919959836header\" style=\"border-collapse:collapse;margin:0 auto;width:580px;font-size:12px;color:#444444;font-size:18px;font-weight:bold;margin-top:10px;margin-bottom:0px;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                   Simply contact us at\n" +
"                </td>\n" +
"                <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"      <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"            <td class=\"m_4953874785919959836wrapper\" style=\"vertical-align:top;padding:10px 20px 0px 0px\">\n" +
"              <table class=\"m_4953874785919959836two m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:80px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                    &nbsp;\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"\n" +
"          <td class=\"m_4953874785919959836wrapper\" style=\"vertical-align:top;padding:10px 20px 0px 0px\">\n" +
"            <table class=\"m_4953874785919959836four m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:180px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_4953874785919959836left-text-pad m_4953874785919959836icon\" style=\"vertical-align:top;padding:0px 0px 10px;height:40px;vertical-align:middle;padding-bottom:0px;padding-left:20px\">\n" +
"                  <img src=\"http://i65.tinypic.com/2regtib.png\" alt=\"Support email big\" style=\"max-width:44px;max-height:35px;clear:none;float:none;display:inline-block\" class=\"CToWUd\">\n" +
"                </td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td class=\"m_4953874785919959836left-text-pad\" style=\"vertical-align:top;padding:0px 0px 10px;padding-left:20px\">\n" +
"                  <b> Email </b><br>\n" +
"                  <a href=\"mailto:mail.giftme@gmail.com\" style=\"color:#4a90e2;text-decoration:none\" target=\"_blank\">mail.giftme@<span class=\"il\">gmail</span>.com</a>\n" +
"                </td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"\n" +
"            <td class=\"m_4953874785919959836wrapper\" style=\"vertical-align:top;padding:10px 20px 0px 0px\">\n" +
"            <table class=\"m_4953874785919959836four m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:180px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_4953874785919959836right-text-pad m_4953874785919959836icon\" style=\"vertical-align:top;padding:0px 0px 10px;height:40px;vertical-align:middle;padding-bottom:0px;padding-right:20px\">\n" +
"                  <img src=\"http://i66.tinypic.com/nuk9x.jpg\" alt=\"Support operating hour big\" style=\"max-width:44px;max-height:35px;clear:none;float:none;display:inline-block\" class=\"CToWUd\">\n" +
"                </td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td class=\"m_4953874785919959836right-text-pad\" style=\"vertical-align:top;padding:0px 0px 10px;padding-right:20px\">\n" +
"                  <b>Operating Hours</b><br>\n" +
"                  <span class=\"aBn\" data-term=\"goog_1112765182\" tabindex=\"0\"><span class=\"aQJ\">9am - 10pm</span></span>, every day\n" +
"                </td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"            <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"              <table class=\"m_4953874785919959836two m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:80px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                    &nbsp;\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"\n" +
"        <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;padding:0!important\"></td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"      <br>\n" +
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"\n" +
"\n" +
"        <table class=\"m_4953874785919959836social m_4953874785919959836container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;background:#ffffff;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top\">\n" +
"      <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"            &nbsp;\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"      <hr style=\"color:#ffffff;background-color:#ffffff;border:1px dashed #a1a1a1;margin:0 auto\">\n" +
"      <br>\n" +
"\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"\n" +
"\n" +
"        <table class=\"m_4953874785919959836footer m_4953874785919959836container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;margin:0 auto 10px;border-spacing:0!important\">\n" +
"          <tbody><tr>\n" +
"            <td style=\"vertical-align:top;font-size:12px;color:#8d8d8d;padding-bottom:10px\">\n" +
"              <table class=\"m_4953874785919959836row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td class=\"m_4953874785919959836wrapper m_4953874785919959836last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;font-size:12px;color:#8d8d8d;padding-bottom:10px;padding-right:0px\">\n" +
"                    <table class=\"m_4953874785919959836twelve m_4953874785919959836columns\" style=\"border-collapse:collapse;margin:0 auto;width:580px;border-spacing:0!important\">\n" +
"                      <tbody><tr>\n" +
"                        <td class=\"m_4953874785919959836center\" style=\"vertical-align:top;text-align:center;font-size:12px;color:#8d8d8d;padding-bottom:10px;padding:0px 0px 10px\">\n" +
"                          \n" +
"                          © 2018 <span class=\"il\">GiftMe</span>. All rights reserved.\n" +
"                            <br>\n" +
"                          You are receiving this email because you subscribed to our mailing list.\n" +
"                        </td>\n" +
"                        <td class=\"m_4953874785919959836expander\" style=\"vertical-align:top;width:0px;font-size:12px;color:#8d8d8d;padding-bottom:10px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                    </tbody></table>\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody></table>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody></table>\n" +
"</div></div></div>", "text/html");
                msg.setHeader("X-Mailer", mailer);
                
                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);
                
                Transport.send(msg);
                
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            
            return false;
        }
    }
    
    
    
    
    public Boolean emailNewCustomer(String fromEmailAddress, String toEmailAddress, Customer customer, Promotion defaultPromotion)
    {
        String emailBody = "Hello World! I am sent from a Java Web Application with JavaMail!";
        
     
   
        
        
        try 
        { 
            
               String firstName = customer.getFirstName();
               String lastName = customer.getLastName();
        String customerEmail = customer.getEmail();
        String dateCreated = new Date().toString();
        System.out.println("WOO");
       
      //  List<Promotion> promotions = promotionControllerLocal.retrieveAllPromotions();
       // Promotion defaultPromotion = promotions.get(0);
              //Promotion defaultPromotion = promotionControllerLocal.retrievePromotionById(1L);
              System.out.println("YEP");
            String promoCode = defaultPromotion.getPromoCode();
            String discount = defaultPromotion.getDiscount().toString();
            
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");            
            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
              
            Message msg = new MimeMessage(session);
                                    
            if (msg != null)
            {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress, false));
                msg.setSubject("Dear "+ firstName  + " " + lastName + ", thanks for signing up with giftme!");
               // msg.setText(emailBody);
               msg.setContent("<div style=\"color:#212121;font-family:&quot;Helvetica Neue&quot;,&quot;Helvetica&quot;,&quot;Arial&quot;,sans-serif;font-weight:normal;margin:0;padding:0;text-align:center;line-height:1.3;font-size:14px;line-height:19px\">\n" +
"  <table class=\"m_-6059880834036033641body\" style=\"border-collapse:collapse;height:100%;width:100%;min-width:600px;table-layout:fixed;background-color:#eee;color:#212121;font-family:&quot;Helvetica Neue&quot;,&quot;Helvetica&quot;,&quot;Arial&quot;,sans-serif;font-weight:normal;margin:0;padding:0;text-align:center;line-height:1.3;font-size:14px;line-height:19px;border-spacing:0!important\">\n" +
"    <tbody><tr>\n" +
"      <td class=\"m_-6059880834036033641center\" align=\"center\" valign=\"top\" style=\"vertical-align:top;text-align:center\">\n" +
"\n" +
"        <table class=\"m_-6059880834036033641header m_-6059880834036033641container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;height:80px;margin-top:20px;border-spacing:0!important\">\n" +
"          <tbody><tr>\n" +
"            <td style=\"vertical-align:top\">\n" +
"              <table class=\"m_-6059880834036033641row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td class=\"m_-6059880834036033641wrapper m_-6059880834036033641last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"                    <table class=\"m_-6059880834036033641twelve m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:580px;border-spacing:0!important\">\n" +
"                      <tbody><tr>\n" +
"                        <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                          <img alt=\"giftme\" class=\"m_-6059880834036033641center CToWUd\" src=\"http://i67.tinypic.com/107kgtx.png\" style=\"margin:0 auto;margin-top:15px;margin-bottom:4px;width:34%;max-width:250px;height:auto;float:none;clear:none;display:inline-block\">\n" +
"                        </td>\n" +
"                        <td class=\"m_-6059880834036033641expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                    </tbody></table>\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody></table>\n" +
"\n" +
"        <table class=\"m_-6059880834036033641order-fulfillment-late m_-6059880834036033641container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top;color:#8c8c8b;padding-top:11px\">\n" +
"      <table class=\"m_-6059880834036033641row m_-6059880834036033641panel m_-6059880834036033641twelve m_-6059880834036033641columns\" style=\"border-collapse:collapse;background:#f2f2f2;border:1px solid #d9d9d9;padding:0px;width:100%;margin:0 auto;width:580px;background-color:#f8f7f4;border-spacing:0!important;padding:10px!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_-6059880834036033641panel m_-6059880834036033641sub-grid\" style=\"vertical-align:top;background:#f2f2f2;border:1px solid #d9d9d9;color:#8c8c8b;padding:0px 0px 10px;background-color:#f8f7f4;padding:10px!important\">\n" +
"            <table style=\"border-collapse:collapse;width:100%;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_-6059880834036033641six-sub-columns\" style=\"vertical-align:top;color:#8c8c8b;padding:0px 0px 10px;padding:0px\">\n" +
"                  <p align=\"left\" style=\"margin:0 0 5px 0;margin:0px\">\n" +
"                   " + customerEmail +
"                  </p>\n" +
"                </td>\n" +
"                <td class=\"m_-6059880834036033641six-sub-columns\" style=\"vertical-align:top;color:#8c8c8b;padding:0px 0px 10px;padding:0px\">\n" +
"                  <p align=\"right\" style=\"margin:0 0 5px 0;margin:0px\">\n" +
"                  Account Created: " + dateCreated +
"                  </p>\n" +
"                </td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"      <br>\n" +
"      <table class=\"m_-6059880834036033641eight m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:380px;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td style=\"vertical-align:top;color:#8c8c8b;padding:0px 0px 10px\">\n" +
"            <table style=\"border-collapse:collapse;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td><h2 style=\"font-weight:normal;word-break:normal;line-height:normal;font-size:20px;color:#000\">\n" +
"                  <strong>\n" +
"                    Dear <span class=\"m_-6059880834036033641highlight\" style=\"color:#E7B0D0;font-weight:bold\">"+firstName+"</span>, you have made your life easier by signing up with us. \n" +
"                  </strong>\n" +
"                </h2>\n" +
"              </td></tr>\n" +
"              <tr><td>\n" +
"                It is our duty to bring you last minute gifts when you need it. Before you start on your gifting journey with us, we would like to thank you for your support. \n" +
"              </td></tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"      <br>\n" +
"        <p style=\"margin:0 0 5px 0\">\n" +
"         To start off, here's a promotional code for you to use when you order with giftme. \n" +
"        </p>\n" +
"      <br>\n" +
"      <table class=\"m_-6059880834036033641eight m_-6059880834036033641columns m_-6059880834036033641perforated-box\" style=\"border-collapse:collapse;background-color:#ffffff;border:1px dashed #a1a1a1;margin:0 auto;width:380px;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top;color:#8c8c8b;padding:0px 0px 10px;padding:5px\">\n" +
"      <table style=\"border-collapse:collapse;border-spacing:0!important;margin:auto\">\n" +
"        <tbody><tr>\n" +
"          <td style=\"vertical-align:top;color:#8c8c8b;padding:0px 0px 10px;padding:0px\">\n" +
"            <h2 style=\"font-weight:normal;word-break:normal;line-height:normal;font-size:20px;color:#000\">\n" +
"              <strong>\n" +
"               " + promoCode +
"              </strong>\n" +
"            </h2>\n" +
"            <p style=\"margin:0 0 5px 0\">\n" +
"          S$" + discount +" Off Total Bill\n" +
"            </p>\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"<br>\n" +
"\n" +
"\n" +
"      <br>\n" +
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"\n" +
"<table class=\"m_-6059880834036033641contact-us m_-6059880834036033641container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;background:#fbf6ed;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top\">\n" +
"      <table class=\"m_-6059880834036033641row\" style=\"border-collapse:collapse;padding:0px;width:100%;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_-6059880834036033641wrapper m_-6059880834036033641last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"            <table class=\"m_-6059880834036033641twelve m_-6059880834036033641columns m_-6059880834036033641header\" style=\"border-collapse:collapse;margin:0 auto;width:580px;font-size:12px;color:#444444;font-size:18px;font-weight:bold;margin-top:10px;margin-bottom:0px;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                  Need help? Simply contact us at\n" +
"                </td>\n" +
"                <td class=\"m_-6059880834036033641expander\" style=\"vertical-align:top;width:0px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"      <table class=\"m_-6059880834036033641row\" style=\"border-collapse:collapse;padding:0px;width:100%;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"            <td class=\"m_-6059880834036033641wrapper\" style=\"vertical-align:top;padding:10px 20px 0px 0px\">\n" +
"              <table class=\"m_-6059880834036033641two m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:80px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                    &nbsp;\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"\n" +
"          <td class=\"m_-6059880834036033641wrapper\" style=\"vertical-align:top;padding:10px 20px 0px 0px\">\n" +
"            <table class=\"m_-6059880834036033641four m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:180px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_-6059880834036033641left-text-pad m_-6059880834036033641icon\" style=\"vertical-align:top;padding:0px 0px 10px;height:40px;vertical-align:middle;padding-bottom:0px;padding-left:20px\">\n" +
"                  <img src=\"http://i66.tinypic.com/nuk9x.jpg\" alt=\"Support email big\" style=\"max-width:44px;max-height:35px;clear:none;float:none;display:inline-block\" class=\"CToWUd\">\n" +
"                </td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td class=\"m_-6059880834036033641left-text-pad\" style=\"vertical-align:top;padding:0px 0px 10px;padding-left:20px\">\n" +
"                  <b> Email </b><br>\n" +
"                  <a href=\"mailto:mail.giftme@gmail.com\" style=\"color:#4a90e2;text-decoration:none\" target=\"_blank\">mail.giftme@gmail.com</a>\n" +
"                </td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"\n" +
"            <td class=\"m_-6059880834036033641wrapper\" style=\"vertical-align:top;padding:10px 20px 0px 0px\">\n" +
"            <table class=\"m_-6059880834036033641four m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:180px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"              <tbody><tr>\n" +
"                <td class=\"m_-6059880834036033641right-text-pad m_-6059880834036033641icon\" style=\"vertical-align:top;padding:0px 0px 10px;height:40px;vertical-align:middle;padding-bottom:0px;padding-right:20px\">\n" +
"                  <img src=\"http://i65.tinypic.com/2regtib.jpg\" alt=\"Support operating hour big\" style=\"max-width:44px;max-height:35px;clear:none;float:none;display:inline-block\" class=\"CToWUd\">\n" +
"                </td>\n" +
"              </tr>\n" +
"              <tr>\n" +
"                <td class=\"m_-6059880834036033641right-text-pad\" style=\"vertical-align:top;padding:0px 0px 10px;padding-right:20px\">\n" +
"                  <b>Operating Hours</b><br>\n" +
"                  <span class=\"aBn\" data-term=\"goog_1922951321\" tabindex=\"0\"><span class=\"aQJ\">9am - 10pm</span></span>, every day\n" +
"                </td>\n" +
"              </tr>\n" +
"            </tbody></table>\n" +
"          </td>\n" +
"            <td class=\"m_-6059880834036033641wrapper m_-6059880834036033641last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"              <table class=\"m_-6059880834036033641two m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:80px;font-size:12px;color:#444444;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td style=\"vertical-align:top;padding:0px 0px 10px\">\n" +
"                    &nbsp;\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"\n" +
"        <td class=\"m_-6059880834036033641expander\" style=\"vertical-align:top;width:0px;padding:0!important\"></td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"      <br>\n" +
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"\n" +
"\n" +
"        <table class=\"m_-6059880834036033641social m_-6059880834036033641container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;background:#ffffff;border-spacing:0!important\">\n" +
"  <tbody><tr>\n" +
"    <td style=\"vertical-align:top\">\n" +
"      <table class=\"m_-6059880834036033641row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"        <tbody><tr>\n" +
"          <td class=\"m_-6059880834036033641wrapper m_-6059880834036033641last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;padding-right:0px\">\n" +
"            &nbsp;\n" +
"          </td>\n" +
"        </tr>\n" +
"      </tbody></table>\n" +
"\n" +
"      <hr style=\"color:#ffffff;background-color:#ffffff;border:1px dashed #a1a1a1;margin:0 auto\">\n" +
"      <br>\n" +
"\n" +
"   \n" +
"    </td>\n" +
"  </tr>\n" +
"</tbody></table>\n" +
"\n" +
"\n" +
"        <table class=\"m_-6059880834036033641footer m_-6059880834036033641container\" style=\"border-collapse:collapse;width:580px;margin:0 auto;text-align:inherit;background-color:#fff;margin:0 auto 10px;border-spacing:0!important\">\n" +
"          <tbody><tr>\n" +
"            <td style=\"vertical-align:top;font-size:12px;color:#8d8d8d;padding-bottom:10px\">\n" +
"              <table class=\"m_-6059880834036033641row\" style=\"border-collapse:collapse;padding:0px;width:100%;border-spacing:0!important\">\n" +
"                <tbody><tr>\n" +
"                  <td class=\"m_-6059880834036033641wrapper m_-6059880834036033641last\" style=\"vertical-align:top;padding:10px 20px 0px 0px;font-size:12px;color:#8d8d8d;padding-bottom:10px;padding-right:0px\">\n" +
"                    <table class=\"m_-6059880834036033641twelve m_-6059880834036033641columns\" style=\"border-collapse:collapse;margin:0 auto;width:580px;border-spacing:0!important\">\n" +
"                      <tbody><tr>\n" +
"                        <td class=\"m_-6059880834036033641center\" style=\"vertical-align:top;text-align:center;font-size:12px;color:#8d8d8d;padding-bottom:10px;padding:0px 0px 10px\">\n" +
"                          \n" +
"                          © 2018 <span class=\"il\">giftme</span>. All rights reserved.\n" +
"        \n" +
"                        \n" +
"                            <br>\n" +
"                          You are receiving this email because you subscribed to our mailing list.\n" +
"                        </td>\n" +
"                        <td class=\"m_-6059880834036033641expander\" style=\"vertical-align:top;width:0px;font-size:12px;color:#8d8d8d;padding-bottom:10px;padding:0px 0px 10px;padding:0!important\"></td>\n" +
"                      </tr>\n" +
"                    </tbody></table>\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody></table>\n" +
"\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody></table>\n" +
"\n" +
"</div></div></div><div id=\":sn\" class=\"ii gt \" style=\"display:none\"><div id=\":so\" class=\"a3s aXjCH undefined\"></div></div><div class=\"hi\"></div></div><div class=\"ajx\"></div></div><div class=\"gA gt acV\"><div class=\"gB xu\"><table id=\":s2\" cellpadding=\"0\" role=\"presentation\" class=\"cf gz ac0\"><tbody><tr><td><div class=\"cKWzSc mD\" idlink=\"\" tabindex=\"0\" role=\"button\"><img class=\"mL\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/cleardot(1).gif\" alt=\"\"> <span class=\"mG\">Reply</span></div></td><td></td><td><div class=\"XymfBd mD\" idlink=\"\" tabindex=\"0\" role=\"button\"><img class=\"mI\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/cleardot(1).gif\" alt=\"\"> <span class=\"mG\">Forward</span></div></td><td></td><td class=\"io\"><div class=\"adA\"></div></td></tr></tbody></table><div class=\"ip iq\"><div id=\":ru\"><table class=\"cf wS\" role=\"presentation\"><tbody><tr><td class=\"amq\"><img id=\":0_0\" name=\":0\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/no_photo.png\" jid=\"farhanangullia@gmail.com\" data-hovercard-id=\"farhanangullia@gmail.com\" class=\"ajn bofPge\"></td><td class=\"amr\"><div class=\"nr wR\"><div class=\"amn\">Click here to <span id=\":rn\" role=\"link\" tabindex=\"0\" class=\"ams bkH\">Reply</span> or <span id=\":rp\" role=\"link\" tabindex=\"0\" class=\"ams bkG\">Forward</span></div></div><input type=\"text\" class=\"amp\"></td></tr></tbody></table></div></div></div></div></div></div></div></div></div></div><div class=\"nH\"></div><div class=\"nH\"></div></div></div><div class=\"nH\"><div class=\"l2 ov\" style=\"padding-bottom: 440px;\"><div id=\":se\"></div><div id=\":si\" class=\"aeV\"><div><div class=\"md mj\"><div><span dir=\"ltr\">3.29 GB</span> (21%) of <span dir=\"ltr\">15 GB</span> used</div><div class=\"aeW\"><a target=\"_blank\" href=\"https://www.google.com/settings/u/1/storage?hl=en\" class=\"l8\">Manage</a></div></div></div></div><div class=\"aeU\"><div id=\":sh\"><div><div class=\"ma\"><a href=\"https://www.google.com/intl/en/policies/terms/\" target=\"_blank\" class=\"l9\">Terms</a> - <a href=\"https://www.google.com/intl/en/policies/privacy/\" target=\"_blank\" class=\"l9\">Privacy</a> </div></div></div></div><div id=\":sf\" class=\"ae3\"><div><div class=\"l6\"><div>Last account activity: 2 hours ago</div><span id=\":sp\" class=\"l8 ou\" tabindex=\"0\" role=\"link\">Details</span></div></div></div><div style=\"clear: both;\"></div></div></div></div></td><td class=\"Bu yM\"><div class=\"Bt\" style=\"width: 0px;\"></div><div class=\"nH\" style=\"width: 0px;\"><div class=\"no\"><div class=\"nH nn\" style=\"width: 0px;\"><div style=\"height: 64ex;\"></div></div></div><div class=\"dJ\"></div></div></td><td class=\"Bu y3\"><div class=\"Bt\" style=\"width: 859px;\"></div><div class=\"nH bno adC\" role=\"complementary\" style=\"top: 0px; right: 30px; position: absolute;\"><div class=\"nH\"><div class=\"nH Pj\"></div></div></div><div class=\"y4\" style=\"visibility: hidden;\"></div></td></tr></table></div></div></div></div><div id=\":3\" class=\"aeG\" style=\"display: none;\"><div class=\"l2 ov\"><div id=\":2r\"></div><div id=\":2v\" class=\"aeV\"><div><div class=\"md mj\"><div><span dir=\"ltr\">3.29 GB</span> (21%) of <span dir=\"ltr\">15 GB</span> used</div><div class=\"aeW\"><a target=\"_blank\" href=\"https://www.google.com/settings/u/1/storage?hl=en\" class=\"l8\">Manage</a></div></div></div></div><div class=\"aeU\"><div id=\":2u\"><div><div class=\"ma\"><a href=\"https://www.google.com/intl/en/policies/terms/\" target=\"_blank\" class=\"l9\">Terms</a> - <a href=\"https://www.google.com/intl/en/policies/privacy/\" target=\"_blank\" class=\"l9\">Privacy</a> </div></div></div></div><div id=\":2s\" class=\"ae3\"><div><div class=\"l6\"><div>Last account activity: 2 hours ago</div><span id=\":2z\" class=\"l8 ou\" tabindex=\"0\" role=\"link\">Details</span></div></div></div><div style=\"clear: both;\"></div></div></div></div></div></div></div></div></div></div><div class=\"nH bnl nn\" style=\"max-width: 0px; min-width: 0px; width: 0px; height: 780px;\"><div class=\"bvq\" style=\"display: none;\"><div id=\":j1.l\" style=\"display: none;\"><div class=\"bt1 bt3\"><div class=\"bt5 bt6 bz0\"></div><div class=\"bt5 bt7 bz0\"></div><div class=\"bt5 QHQUud bz0\"></div></div></div><div id=\":j1.s\" class=\"J-KU-Jg J-KU-Jg-Zj\" aria-label=\"Add-ons Panel\"></div></div><div class=\"bvu J-KU-Jz\" style=\"display: none;\"></div></div></div><div class=\"dJ\"></div></div></div></div></div><div class=\"vY nq\"><div class=\"vX UC\" style=\"display: none;\"><div class=\"J-J5-Ji\"><div class=\"UD\"></div><div class=\"vh\"><div class=\"J-J5-Ji\"><div class=\"vZ cNDVKe wTsMFb\"><span class=\"v1\">Oops… the system encountered a problem (#001)</span> - Retrying in 1s…</div></div><div class=\"J-J5-Ji\"><div class=\"v0\"><span id=\":ta\" class=\"v2\" tabindex=\"0\" role=\"link\">Retry now</span> &nbsp;<a target=\"_blank\" href=\"https://support.google.com/mail?hl=en&amp;p=oops1&amp;authuser=1\" class=\"v2\">Learn more</a></div></div></div><div class=\"UB\"></div></div></div></div><div class=\"azu Ta J-M\" style=\"display: none; left: 0px; top: 0px;\"></div><div class=\"azu Ta J-M\" style=\"display: none; left: 0px; top: 0px;\"></div><div class=\"azu Ta J-M\" style=\"display: none; left: 0px; top: 0px;\"></div><iframe src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/saved_resource(3).html\" aria-hidden=\"true\" style=\"display: none !important;\"></iframe><div></div><iframe src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/saved_resource(4).html\" aria-hidden=\"true\" style=\"display: none !important;\"></iframe><iframe id=\"apiproxye854ea7a730ac57a3a860a308b4b318deecba6520.2536439133\" name=\"apiproxye854ea7a730ac57a3a860a308b4b318deecba6520.2536439133\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/proxy.html\" tabindex=\"-1\" aria-hidden=\"true\" style=\"width: 1px; height: 1px; position: absolute; top: -100px; display: none;\"></iframe><iframe name=\"oauth2relay321771835\" id=\"oauth2relay321771835\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/postmessageRelay.html\" tabindex=\"-1\" aria-hidden=\"true\" style=\"width: 1px; height: 1px; position: absolute; top: -100px;\"></iframe><div class=\"dw np\"><div><div class=\"nH\" style=\"width: 1920px;\"><div class=\"nH\" style=\"height: 888px;\"><div class=\"no\" style=\"float: right;\"><div class=\"nH nn\" style=\"width: 8px;\"></div><div class=\"nH aJl nn\" style=\"width: 6px; height: 888px; display: none;\"><div class=\"aJn\"><div id=\":ja\"><div class=\"nH aAl\" style=\"height: 1px;\"><iframe src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/frame.html\" frameborder=\"0\" scrolling=\"no\" class=\"a7A\" width=\"1\" height=\"1\"></iframe></div></div><div id=\":j7\" class=\"aQV aJo\"></div><div id=\":j8\" class=\"aQV aJm\"></div><div id=\":j9\" class=\"aQV aQX\"></div></div></div><div class=\"nH nn\" style=\"width: 20px;\"></div></div><div class=\"dJ\"></div></div></div></div></div><div class=\"a07\" style=\"display: none;\"><div class=\"T-P a06\"><div class=\"T-P-Jz-UR\" id=\"talk_flyout\" style=\"width: 263px; height: 605px;\"><iframe id=\"wblh0.9116857289776352-1\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/frame2(1).html\" frameborder=\"0\" tabindex=\"0\" class=\"a1i\" style=\"background-color: transparent; width: 100%; height: 100%; border: 0px; overflow-y: hidden; position: absolute;\"></iframe></div><div class=\"T-P-hFsbo-UR T-P-hFsbo\"><div class=\"T-P-atD\"></div><div class=\"T-P-atC\"></div></div></div></div><script type=\"text/javascript\" src=\"./S$2.50 has been added to your rewards wallet 💓 - farhanangullia@gmail.com - Gmail2_files/host-js\"></script><div class=\"aSs\" style=\"visibility: hidden;\"><div class=\"aSt\"></div></div><div class=\"J-M agd aYO\" style=\"display: none;\"></div><div class=\"J-M agd aYO\" style=\"display: none;\"></div><div class=\"J-M aX0 aYO\" style=\"display: none;\"></div><div class=\"J-M agd aYO\" style=\"display: none;\"></div><div class=\"J-M aX0 aYO\" style=\"display: none;\"></div><div class=\"T-ays\" role=\"tooltip\" aria-live=\"polite\" style=\"display: none;\"><div class=\"T-ays-W095bf\">Add to Calendar</div><div class=\"T-ays-hFsbo\"><div class=\"T-ays-atD\"></div><div class=\"T-ays-atC\"></div></div></div>", "text/html");
                msg.setHeader("X-Mailer", mailer);
                
                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);
                
                Transport.send(msg);
                
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            
            return false;
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
      public Boolean email2(String fromEmailAddress, String toEmailAddress)
    {
        String emailBody = "Hello World! I am sent from a Java Web Application with JavaMail!";
        
        try 
        {
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.host", emailServerName);
            props.put("mail.smtp.port", "25");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.debug", "true");            
            javax.mail.Authenticator auth = new SMTPAuthenticator(smtpAuthUser, smtpAuthPassword);
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);            
            Message msg = new MimeMessage(session);
                                    
            if (msg != null)
            {
                msg.setFrom(InternetAddress.parse(fromEmailAddress, false)[0]);
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress, false));
                msg.setSubject("Greetings from JavaMail");
               // msg.setText(emailBody);
               msg.setContent("<h1>LOL</h1>", "text/html");
                msg.setHeader("X-Mailer", mailer);
                
                Date timeStamp = new Date();
                msg.setSentDate(timeStamp);
                
                Transport.send(msg);
                
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            
            return false;
        }
    }
    
    
}
