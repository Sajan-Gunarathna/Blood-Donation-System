package com.donation.LifeLine.controllers;

import com.donation.LifeLine.model.Report;
import com.donation.LifeLine.services.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
@CrossOrigin(origins = {"http://localhost:8080", "http://127.0.0.1:8080"})
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public List<Report> getAllReports() {
        return reportService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        return reportService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Report createReport(@RequestBody Report report) {
        return reportService.create(report);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(
            @PathVariable Long id,
            @RequestBody Report report) {
        try {
            Report updated = reportService.update(id, report);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable Long id) {
        reportService.delete(id);
    }
}