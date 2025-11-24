package com.donation.LifeLine.services;

import com.donation.LifeLine.model.Report;
import com.donation.LifeLine.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<Report> listAll() { return reportRepository.findAll(); }
    public Optional<Report> getById(Long id) { return reportRepository.findById(id); }
    public Report create(Report r) { return reportRepository.save(r); }
    public Report update(Long id, Report updated) {
        Report existing = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found"));
        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        return reportRepository.save(existing);
    }
    public void delete(Long id) { reportRepository.deleteById(id); }
}


