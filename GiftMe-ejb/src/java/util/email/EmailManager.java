package util.email;

import entity.Delivery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;
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
        
          String promotionDiscount = delivery.getTransaction().getDiscount().toString();
        String subTotal = amt.toString();
         String finalAmount = delivery.getTransaction().getTotalAmount().toString();
         
        String transactionDate = delivery.getTransaction().getTransactionDateTime().toString();
       
        System.out.println("HERE LA");
        
        String deliveryFee = delivery.getTransaction().getDeliveryFee().toString();
        if(deliveryFee.equals("0"))
            deliveryFee = "FREE";
        
        
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
"                          <img alt=\"giftme\" class=\"m_4953874785919959836center CToWUd\" src=\"http://i68.tinypic.com/2hs13rr.jpg\" style=\"margin:0 auto;margin-top:15px;margin-bottom:4px;width:34%;max-width:250px;height:auto;float:none;clear:none;display:inline-block\">\n" +
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
"                          <p class=\"m_4953874785919959836amount\" style=\"margin:0 0 5px 0;text-align:right;white-space:nowrap\">S$"+deliveryFee+"</p>\n" +
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
