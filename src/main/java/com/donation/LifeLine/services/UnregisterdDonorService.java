package com.donation.LifeLine.services;

import com.donation.LifeLine.model.UnregisterdDonor;

import java.util.List;

public interface UnregisterdDonorService {
    UnregisterdDonor saveDonor(UnregisterdDonor donor);
    List<UnregisterdDonor> getAllDonors();
}
