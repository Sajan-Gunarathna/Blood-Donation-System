package com.donation.LifeLine.services;

import com.donation.LifeLine.model.DTO.DonorFrequencyDTO;
import com.donation.LifeLine.model.DTO.DonationHistoryDTO;
import com.donation.LifeLine.model.DonationHistory;
import com.donation.LifeLine.repository.DonationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class DonationHistoryService {

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    public DonationHistory create(DonationHistoryDTO dto) {
        DonationHistory dh = new DonationHistory();
        dh.setDonorName(dto.getDonorName());
        dh.setDonorNIC(dto.getDonorNIC());
        dh.setDonationDate(dto.getDonationDate());
        dh.setDonationLocation(dto.getDonationLocation());
        dh.setHemoglobinLevel(dto.getHemoglobinLevel());
        dh.setMedicalOfficer(dto.getMedicalOfficer());
        dh.setNotes(dto.getNotes());
        return donationHistoryRepository.save(dh);
    }

    public Optional<DonationHistory> getById(Long id) {
        return donationHistoryRepository.findById(id);
    }

    public List<DonationHistory> getByDonorNIC(String donorNIC) {
        if (donorNIC == null) return donationHistoryRepository.findAll();
        return donationHistoryRepository.findByDonorNIC(donorNIC);
    }

    @Transactional
    public Optional<DonationHistory> update(Long id, DonationHistoryDTO dto) {
        return donationHistoryRepository.findById(id).map(dh -> {
            if (dto.getDonorName() != null) dh.setDonorName(dto.getDonorName());
            if (dto.getDonorNIC() != null) dh.setDonorNIC(dto.getDonorNIC());
            if (dto.getDonationDate() != null) dh.setDonationDate(dto.getDonationDate());
            if (dto.getDonationLocation() != null) dh.setDonationLocation(dto.getDonationLocation());
            if (dto.getHemoglobinLevel() != null) dh.setHemoglobinLevel(dto.getHemoglobinLevel());
            if (dto.getMedicalOfficer() != null) dh.setMedicalOfficer(dto.getMedicalOfficer());
            if (dto.getNotes() != null) dh.setNotes(dto.getNotes());
            return donationHistoryRepository.save(dh);
        });
    }

    public boolean delete(Long id) {
        if (!donationHistoryRepository.existsById(id)) return false;
        donationHistoryRepository.deleteById(id);
        return true;
    }


    public List<DonorFrequencyDTO> donorFrequencyReport(LocalDate from, LocalDate to, Long minDonations) {
        if (to == null) to = LocalDate.now();
        if (from == null) from = to.minusYears(1);

        List<Object[]> rows = donationHistoryRepository.countDonationsBetween(from, to);
        List<DonorFrequencyDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            String nic = (String) row[0];
            String name = (String) row[1];
            Long count = ((Number) row[2]).longValue();
            if (minDonations != null && count < minDonations) continue;
            result.add(new DonorFrequencyDTO(nic, name, count));
        }

        result.sort(Comparator.comparing(DonorFrequencyDTO::getDonationCount).reversed());
        return result;
    }
}