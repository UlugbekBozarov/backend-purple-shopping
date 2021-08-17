package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.*;
import com.example.online_shopping.database.repository.*;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.user.CardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
public class CardServices {

    private UsersRepository usersRepository;

    private UserCardRepo cardRepo;

    @Autowired
    public CardServices(UsersRepository usersRepository, UserCardRepo cardRepo) {
        this.usersRepository = usersRepository;
        this.cardRepo = cardRepo;
    }

    public Users addCard(CardRequest addCard, Principal principal) throws NotFound {

        try {
            Users user = usersRepository.findByUsername(principal.getName());
            System.out.println("Card Number: ");
            System.out.println(addCard.getCard_number().substring(0, 4));
            System.out.println(addCard.getCard_number().substring(4, 8));
            System.out.println(addCard.getCard_number().substring(8, 12));
            System.out.println(addCard.getCard_number().substring(12, 16));
            UserCard card = new UserCard(
                    user.getUser_id(),
                    Integer.parseInt(addCard.getCard_number().substring(0, 4)),
                    Integer.parseInt(addCard.getCard_number().substring(4, 8)),
                    Integer.parseInt(addCard.getCard_number().substring(8, 12)),
                    Integer.parseInt(addCard.getCard_number().substring(12, 16)),
                    addCard.getCard_date()
            );
            cardRepo.save(card);
            log.info("Create Card: {}", card.toString());
            return user;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The card has not been saved!");
            throw new NotFound("Save Card", -2, error);
        }
    }

    public Users deleteCard(Long card_id, Principal principal) throws NotFound {
        ErrorMessage error;

        try {
            Users user = usersRepository.findByUsername(principal.getName());
            UserCard card = cardRepo.findById(card_id).get();
            if (card.getUser_id() == user.getUser_id()) {
                cardRepo.deleteById(card_id);
                log.info("Delete Card: {}", card.toString());
            } else {
                error = new ErrorMessage(406, "Bunday Card topilmadi");
                throw new NotFound("Delete Card", -2, error);
            }

            return user;
        } catch (Exception e) {
            error = new ErrorMessage(406, "The card has not been deleted!");
            throw new NotFound("Delete Card", -2, error);
        }
    }

}
