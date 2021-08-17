package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.UserAddress;
import com.example.online_shopping.database.domain.Users;
import com.example.online_shopping.database.repository.*;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.user.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class AddressServices {

    private UsersRepository usersRepository;

    private UserAddressRepo addressRepo;

    @Autowired
    public AddressServices(UsersRepository usersRepository, UserAddressRepo addressRepo) {
        this.usersRepository = usersRepository;
        this.addressRepo = addressRepo;
    }

    public Users addAddress(Address addAddress, Principal principal) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserAddress address = new UserAddress(user.getUser_id(), addAddress.getTitle(), addAddress.getAddress());
            addressRepo.save(address);
            log.info("Create Address: {}", address.toString());
            return user;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The address has not been saved!");
            throw new NotFound("Save Address", -2, error);
        }
    }


    public Users editAddress(Long address_id, Address editAddress, Principal principal) throws NotFound {
        ErrorMessage error;

        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserAddress address = addressRepo.findById(address_id).get();
            if (address.getUser_id() == user.getUser_id()) {
                address.setAddress(editAddress.getAddress());
                address.setTitle(editAddress.getTitle());
                addressRepo.save(address);
                log.info("Edit Address: {}", address.toString());
            } else {
                error = new ErrorMessage(406, "Bunday Address topilmadi");
                throw new NotFound("Edit Address", -2, error);
            }

            return user;
        } catch (Exception e) {
            error = new ErrorMessage(406, "The address has not been edited!");
            throw new NotFound("Edit Address", -2, error);
        }
    }


    public Users deleteAddress(Long address_id, Principal principal) throws NotFound {
        ErrorMessage error;

        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserAddress address = addressRepo.findById(address_id).get();
            if (address.getUser_id() == user.getUser_id()) {
                addressRepo.deleteById(address_id);
                log.info("Delete Address: {}", address.toString());
            } else {
                error = new ErrorMessage(406, "Bunday Address topilmadi");
                throw new NotFound("Delete Address", -2, error);
            }

            return user;
        } catch (Exception e) {
            error = new ErrorMessage(406, "The address has not been deleted!");
            throw new NotFound("Delete Address", -2, error);
        }
    }
}
