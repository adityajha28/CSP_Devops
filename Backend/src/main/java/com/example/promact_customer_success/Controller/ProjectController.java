    package com.example.promact_customer_success.Controller;

    import com.example.promact_customer_success.Entity.Project;
    import com.example.promact_customer_success.Repository.ProjectRepository;
    import com.lowagie.text.Document;
    import com.lowagie.text.*;
    import com.lowagie.text.FontFactory;
    import com.lowagie.text.PageSize;
    import com.lowagie.text.pdf.PdfWriter;
    import jakarta.servlet.http.HttpServletResponse;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/project")
    public class ProjectController {

        @Autowired
        private ProjectRepository projectRepository;

        @GetMapping
        public Iterable<Project> findAllProjects() {
            return projectRepository.findAll();
        }

        @PostMapping
        public Project addProject(@RequestBody Project project) {
            return projectRepository.save(project);
        }

        @GetMapping("/export/pdf/{id}")
        public void exportProjectsToPdf(HttpServletResponse response,@PathVariable Integer id ) throws IOException {
            Optional<Project> optionalProject = projectRepository.findById(id);
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                     Document document = new Document(PageSize.A4)) {
                    PdfWriter.getInstance(document, byteArrayOutputStream);
                    document.open();

                    Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
                    fontTitle.setSize(20);
                    Paragraph paragraph1 = new Paragraph("Project Details", fontTitle);
                    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
                    document.add(paragraph1);
                    document.add(new Paragraph("Project Name: " + project.getName() + "\n Description: " + project.getDescription()+"\n"));
                    document.close();
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=projects.pdf");
                    response.getOutputStream().write(byteArrayOutputStream.toByteArray());
                }
            }
        }
    }