package com.example.promact_customer_success.Service;

import com.example.promact_customer_success.Entity.AuditHistory;
import com.example.promact_customer_success.Entity.Project;
import com.example.promact_customer_success.Entity.Stakeholder;
import com.example.promact_customer_success.Repository.ProjectRepository;
import com.example.promact_customer_success.Repository.StakeholderRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private StakeholderRepository stakeholderRepository;

    @Autowired
    private ProjectRepository projectRepository;
    String projectName;
    public void sendAuditHistoryNotification(AuditHistory auditHistory,String type) {
        // Get project ID from the audit history
        Integer projectId = auditHistory.getProject().getId();
        Optional<Project> optionalProject= projectRepository.findById(projectId);
        if(optionalProject.isPresent()){
            Project project=optionalProject.get();
            projectName=project.getName();
        }
        // Find stakeholders associated with the project
        List<Stakeholder> stakeholders =(List<Stakeholder>) stakeholderRepository.findByProjectId(projectId);
        System.out.println("stakeholders");
        if (stakeholders.isEmpty()) {
            System.out.println("empty stackholders");
        }

        // Prepare email content
        String subject = "Audit History "+type+" Notification";
        String greeting = "<p> Please note that audit has been " + type + " and here is the audit summary:</p>";
//        String message = "<table style='border-collapse: collapse;'>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Project Name:</strong> " + projectName + "</td></tr>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Reviewed Section:</strong> " + auditHistory.getReviewedSection() + "</td></tr>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Reviewed By:</strong> " + auditHistory.getReviewedBy() + "</td></tr>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Status:</strong> " + auditHistory.getStatus() + "</td></tr>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Comments:</strong> " + auditHistory.getCommentQueries() + "</td></tr>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Action Item:</strong> " + auditHistory.getActionItem() + "</td></tr>"
//                + "<tr><td colspan='2' style='padding: 5px;'><strong>Date of Audit:</strong> " + auditHistory.getDateOfAudit() + "</td></tr>"
//                + "</table><br><br>"
//                + "<p>Thanks and Regards,<br>Promact Infotech Pvt Ltd</p>";
        String message = "<table style='border-collapse: collapse;'>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Project Name:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + projectName + "</td></tr>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Reviewed Section:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + auditHistory.getReviewedSection() + "</td></tr>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Reviewed By:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + auditHistory.getReviewedBy() + "</td></tr>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Status:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + auditHistory.getStatus() + "</td></tr>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Comments:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + auditHistory.getCommentQueries() + "</td></tr>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Action Item:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + auditHistory.getActionItem() + "</td></tr>";
        message += "<tr><th colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>Date of Audit:</td>";
        message += "<td colspan='2' style='padding: 5px; border: 1px solid black; border-collapse: collapse;'>" + auditHistory.getDateOfAudit() + "</td></tr>";
        message += "</table><br><br>";
        message += "<p>Thanks and Regards,<br>Promact Infotech Pvt Ltd</p>";

        // Send email to each stakeholder
        for (Stakeholder stakeholder : stakeholders) {
            sendEmail(stakeholder.getName(),stakeholder.getTitle(), stakeholder.getContact(), subject,greeting, message);
        }
    }

        private void sendEmail(String name,String title, String to, String subject,String greeting, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            try {
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText("<html><body><p>Hello " + title + " " + name + "</p>" + greeting + text + "</body></html>", true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            emailSender.send(message);
    }
}
