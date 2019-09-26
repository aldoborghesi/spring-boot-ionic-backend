package com.sly.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;



import com.sly.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
    void sendMail(SimpleMailMessage msg);
    
    void sendOrderConfirmationHtmlEmail(Pedido obj);
    
    void sendHtmlEmail(MimeMessage msg);
    
}
