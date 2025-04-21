package jpaprojects.foodorderingsystem.service;

import jpaprojects.foodorderingsystem.dtos.request.CourierNotificationEmailDTO;
import jpaprojects.foodorderingsystem.dtos.request.OrderStatusEmailDTO;
import jpaprojects.foodorderingsystem.dtos.request.PaymentConfirmationEmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailSenderService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }


    public void sendOrderStatusEmail(OrderStatusEmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(emailDTO.getCustomerEmail());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getMessage());
        emailSender.send(message);
    }

    public void sendPaymentConfirmationEmail(PaymentConfirmationEmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(emailDTO.getCustomerEmail());
        message.setSubject("Payment Confirmation");
        message.setText("Dear " + emailDTO.getCustomerName() + ",\n\n"
                + "Your payment of " + emailDTO.getPaidAmount() + " for Order #" + emailDTO.getOrderId() + " has been successfully processed.\n\nThank you for your order.");
        emailSender.send(message);
    }

    public void sendCourierNotificationEmail(CourierNotificationEmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(emailDTO.getCourierEmail());
        message.setSubject("New Delivery Assignment");
        message.setText("Dear " + emailDTO.getCourierName() + ",\n\n"
                + "You have been assigned a new delivery for Order #" + emailDTO.getOrderId() + ".\n\n"
                + "Delivery address: " + emailDTO.getDeliveryAddress());
        emailSender.send(message);
    }
    public void send(SimpleMailMessage message) {
        if (fromEmail != null && !fromEmail.isEmpty()) {
            message.setFrom(fromEmail);
        } else {
            throw new IllegalStateException("Email göndərilməsi üçün 'from' ünvan təyin edilməyib.");
        }
        emailSender.send(message);
    }


}
