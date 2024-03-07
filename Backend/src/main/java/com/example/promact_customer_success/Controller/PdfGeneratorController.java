package com.example.promact_customer_success.Controller;
import com.example.promact_customer_success.Entity.*;
import com.example.promact_customer_success.Repository.*;
import com.lowagie.text.*;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@RestController
@RequestMapping("/export")
public class PdfGeneratorController {

    @Autowired
    private AuditHistoryRepository auditHistoryRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EscalationMatrixRepository  escalationMatrixRepository;

    @Autowired
    private PhaseMilestoneRepository phaseMilestoneRepository;

    @Autowired
    private ProjectBudgetRepository projectBudgetRepository;
    @Autowired
    private RiskProfilingRepository riskProfilingRepository;
    @Autowired
    private SprintDetailRepository sprintDetailRepository;

    @Autowired
    private  StakeholderRepository stakeholderRepository;

    @Autowired
    private  VersionHistoryRepository versionHistoryRepository;

    @GetMapping("/pdf")
    public void exportProjectsToPdf(HttpServletResponse response) throws IOException, DocumentException {
        List<Project> projects = (List<Project>) projectRepository.findAll();
        List<AuditHistory> auditHistories = (List<AuditHistory>) auditHistoryRepository.findAll();
        List<EscalationMatrix> escalationMatrices = (List<EscalationMatrix>) escalationMatrixRepository.findAll();
        List<PhaseMilestone> phaseMilestones = (List<PhaseMilestone>) phaseMilestoneRepository.findAll();
        List<ProjectBudget> projectBudgets = (List<ProjectBudget>) projectBudgetRepository.findAll();
        List<RiskProfiling> riskProfilings = (List<RiskProfiling>) riskProfilingRepository.findAll();
        List<SprintDetail> sprintDetails = (List<SprintDetail>) sprintDetailRepository.findAll();
        List<Stakeholder> stakeholders = (List<Stakeholder>) stakeholderRepository.findAll();
        List<VersionHistory> versionHistories = (List<VersionHistory>) versionHistoryRepository.findAll();


        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Document document = new Document(PageSize.A4)) {

            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Create HTML table for projects
            StringBuilder htmlTable = new StringBuilder();
            htmlTable.append("<h1 style=\"margin: 1px;\">Projects</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>Project ID</th><th>Project Name</th><th>Description</th></tr>");
            for (Project project : projects) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(project.getId()).append("</td>");
                htmlTable.append("<td>").append(project.getName()).append("</td>");
                htmlTable.append("<td>").append(project.getDescription().substring(0,100)).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            // Create HTML table for audit histories
            htmlTable.append("<h1 style=\"margin: 1px;\">Projects</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Date of Audit</th><th>Reviewed By</th><th>Status</th><th>Reviewed Section</th><th>Comment Queries</th><th>Action Item</th></tr>");
            for (AuditHistory auditHistory : auditHistories) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(auditHistory.getId()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getDateOfAudit()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getReviewedBy()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getStatus()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getReviewedSection()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getCommentQueries()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getActionItem()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            // Create HTML table for escalation matrices
            htmlTable.append("<h1 style=\"margin: 1px;\">Escalation Matrices</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Escalation Level</th><th>Name</th><th>Role</th><th>Type</th></tr>");
            for (EscalationMatrix escalationMatrix : escalationMatrices) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(escalationMatrix.getId()).append("</td>");
                htmlTable.append("<td>").append(escalationMatrix.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(escalationMatrix.getEscalationLevel()).append("</td>");
                htmlTable.append("<td>").append(escalationMatrix.getName()).append("</td>");
                htmlTable.append("<td>").append(escalationMatrix.getRole()).append("</td>");
                htmlTable.append("<td>").append(escalationMatrix.getType()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            // Create HTML table for phase milestones
            htmlTable.append("<h1 style=\"margin: 1px;\">Phase Milestones</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Title</th><th>Start Date</th><th>Completion Date</th><th>Approval Date</th><th>Status</th><th>Revised Completion Date</th><th>Comments</th></tr>");
            for (PhaseMilestone phaseMilestone : phaseMilestones) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(phaseMilestone.getId()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getTitle()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getStartDate()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getCompletionDate()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getApprovalDate()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getStatus()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getRevisedCompletionDate()).append("</td>");
                htmlTable.append("<td>").append(phaseMilestone.getComments()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            //risk profilings
            htmlTable.append("<h1 style=\"margin: 1px;\">Risk Profilings</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Risk Type</th><th>Description</th><th>Severity</th><th>Impact</th><th>Remedial Steps</th><th>Status</th><th>Closure Date</th></tr>");
            for (RiskProfiling riskProfiling : riskProfilings) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(riskProfiling.getId()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getRiskType()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getDescription()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getSeverity()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getImpact()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getRemedialSteps()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getStatus()).append("</td>");
                htmlTable.append("<td>").append(riskProfiling.getClosureDate()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            // Create HTML table for audit histories
            htmlTable.append("<h1 style=\"margin: 1px;\">Audit Histories</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Date of Audit</th><th>Reviewed By</th><th>Status</th><th>Reviewed Section</th><th>Comment Queries</th><th>Action Item</th></tr>");
            for (AuditHistory auditHistory : auditHistories) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(auditHistory.getId()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getDateOfAudit()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getReviewedBy()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getStatus()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getReviewedSection()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getCommentQueries()).append("</td>");
                htmlTable.append("<td>").append(auditHistory.getActionItem()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            // Create HTML table for sprint details
            htmlTable.append("<h1 style=\"margin: 1px;\">Sprint Details</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Sprint Number</th><th>Start Date</th><th>End Date</th><th>Status</th><th>Comments</th></tr>");
            for (SprintDetail sprintDetail : sprintDetails) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(sprintDetail.getId()).append("</td>");
                htmlTable.append("<td>").append(sprintDetail.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(sprintDetail.getSprintNumber()).append("</td>");
                htmlTable.append("<td>").append(sprintDetail.getStartDate()).append("</td>");
                htmlTable.append("<td>").append(sprintDetail.getEndDate()).append("</td>");
                htmlTable.append("<td>").append(sprintDetail.getStatus()).append("</td>");
                htmlTable.append("<td>").append(sprintDetail.getComments()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            // Create HTML table for stakeholders

            htmlTable.append("<h1 style=\"margin: 1px;\">Stakeholders</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Title</th><th>Name</th><th>Contact</th></tr>");
            for (Stakeholder stakeholder : stakeholders) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(stakeholder.getId()).append("</td>");
                htmlTable.append("<td>").append(stakeholder.getProject().getId()).append("</td>");
                htmlTable.append("<td>").append(stakeholder.getTitle()).append("</td>");
                htmlTable.append("<td>").append(stakeholder.getName()).append("</td>");
                htmlTable.append("<td>").append(stakeholder.getContact()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            //version history
            htmlTable.append("<h1 style=\"margin: 1px;\">Stakeholders</h1>");
            htmlTable.append("<table border=\"1\">");
            htmlTable.append("<tr><th>ID</th><th>Project ID</th><th>Version</th><th>Approved By</th><th>Change Reason</th><th>Change Type</th><th>Approval Date</th></tr>");
            for (VersionHistory versionHistory : versionHistories) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(versionHistory.getId()).append("</td>");
                htmlTable.append("<td>").append(versionHistory.getProject()).append("</td>");
                htmlTable.append("<td>").append(versionHistory.getVersion()).append("</td>");
                htmlTable.append("<td>").append(versionHistory.getApprovedBy()).append("</td>");
                htmlTable.append("<td>").append(versionHistory.getChangeReason()).append("</td>");
                htmlTable.append("<td>").append(versionHistory.getChangeType()).append("</td>");
                htmlTable.append("<td>").append(versionHistory.getApprovalDate()).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");
            // Parse HTML table content to PDF
            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(htmlTable.toString()));

            document.close();

            // Set response content type and header
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=projects.pdf");

            // Write PDF content to response output stream
            response.getOutputStream().write(byteArrayOutputStream.toByteArray());
        }
    }
}
