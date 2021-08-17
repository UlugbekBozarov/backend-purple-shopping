package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.Vaucher;
import com.example.online_shopping.database.domain.enumeration.VAUCHER;
import com.example.online_shopping.database.repository.VaucherRepo;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VaucherServices {

    private VaucherRepo vaucherRepo;

    public VaucherServices(VaucherRepo vaucherRepo) {
        this.vaucherRepo = vaucherRepo;
    }

    public Boolean verifyVaucher(String vaucher_code) throws NotFound {
        ErrorMessage error = null;
        try {
            Vaucher vaucher = vaucherRepo.findByVaucher_code(vaucher_code);
            if (vaucher.getStatus() == VAUCHER.NEW) {
                return true;
            } else if (vaucher.getStatus() == VAUCHER.USED) {
                error = new ErrorMessage(-301, "This voucher code was used.");
                throw new NotFound("Verify Vaucher", -1, error);
            } else {
                error = new ErrorMessage(-302, "Invalid vaucher code");
                throw new NotFound("Verify Vaucher", -1, error);
            }
        } catch (Exception e) {
            error = new ErrorMessage(-112, "This vaucher code is invalid");
            throw new NotFound("Verify Vaucher", 1, error);
        }
    }
}
