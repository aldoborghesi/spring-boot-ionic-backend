package com.sly.cursomc.services;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.mail.SimpleMailMessage;

import com.sly.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
    void sendMail(SimpleMailMessage msg);
}
