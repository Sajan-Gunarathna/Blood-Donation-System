package com.donation.LifeLine.services;

import com.donation.LifeLine.model.BloodRequest;
import com.donation.LifeLine.repository.BloodRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BloodRequestService {

    @Autowired
    private BloodRequestRepository bloodRequestRepository;

    public BloodRequest createRequest(BloodRequest request) {
        request.setRequestDate(OffsetDateTime.now());
        request.setStatus("pending");
        return bloodRequestRepository.save(request);
    }

    public List<BloodRequest> getAllRequests() {
        return bloodRequestRepository.findAll();
    }

    public Optional<BloodRequest> getRequestById(Long id) {
        return bloodRequestRepository.findById(id);
    }

    public BloodRequest updateRequest(Long id, BloodRequest updatedRequest) {
        return bloodRequestRepository.findById(id)
                .map(existingRequest -> {
                    existingRequest.setHospitalName(updatedRequest.getHospitalName());
                    existingRequest.setRequesterName(updatedRequest.getRequesterName());
                    existingRequest.setContact(updatedRequest.getContact());
                    existingRequest.setBloodType(updatedRequest.getBloodType());
                    existingRequest.setQuantity(updatedRequest.getQuantity());
                    existingRequest.setUrgency(updatedRequest.getUrgency());
                    existingRequest.setPatientDetails(updatedRequest.getPatientDetails());
                    existingRequest.setNeededBy(updatedRequest.getNeededBy());
                    return bloodRequestRepository.save(existingRequest);
                })
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }

    public void deleteRequest(Long id) {
        bloodRequestRepository.deleteById(id);
    }

    public List<BloodRequest> searchRequests(String searchTerm) {
        return bloodRequestRepository.searchRequests(searchTerm);
    }

    public BloodRequest updateRequestStatus(Long id, String status) {
        return bloodRequestRepository.findById(id)
                .map(request -> {
                    request.setStatus(status);
                    return bloodRequestRepository.save(request);
                })
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id));
    }
}