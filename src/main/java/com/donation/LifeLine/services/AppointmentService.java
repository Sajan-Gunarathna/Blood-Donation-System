package com.donation.LifeLine.services;



import com.donation.LifeLine.model.Appointment;
import com.donation.LifeLine.model.UnregisterdDonor;
import com.donation.LifeLine.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);

    }
    public List<Appointment> getAppointmentsForDonor(UnregisterdDonor donor) {
        return appointmentRepository.findByDonorId(donor.getId());
    }

    public List<Appointment> findByUserId(long donorId) {
        return appointmentRepository.findByDonorId(donorId);
    }

}
