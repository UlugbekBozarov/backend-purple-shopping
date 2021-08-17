package com.example.online_shopping.controller;

import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.request.OrdersRequest;
import com.example.online_shopping.services.OrderServices;
import com.example.online_shopping.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import javax.validation.Valid;

import static com.example.online_shopping.util.Mappings.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(ORDER)
public class OrderController {

    private final OrderServices services;

    public OrderController(OrderServices services) {
        this.services = services;
    }

    @GetMapping()
    public ResponseEntity<?> getOrdersUser(Principal principal) throws NotFound {
        return new ResponseEntity<>(services.getOrdersAccepted(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(ACCEPTED)
    public ResponseEntity<?> getOrderAccepted(Principal principal) throws NotFound {
        return new ResponseEntity<>(services.getOrdersAccepted(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(DELIVERY)
    public ResponseEntity<?> getOrderDelivery(Principal principal) throws NotFound {
        return new ResponseEntity<>(services.getOrdersDelivery(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(ACCEPTED + DELIVERY)
    public ResponseEntity<?> getOrderAcceptedOrDelivery(Principal principal) throws NotFound {
        return new ResponseEntity<>(services.getOrdersAccepted(principal.getName()), HttpStatus.OK);
    }

    @GetMapping(RECEIVE)
    public ResponseEntity<?> getOrderReceive(Principal principal) throws NotFound {
        return new ResponseEntity<>(services.getOrdersReceive(principal.getName()), HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> getOrders(@Valid @RequestBody OrdersRequest orders, Principal principal) {
        return new ResponseEntity<>(services.saveOrders(orders, principal), HttpStatus.OK);
    }

    @GetMapping(STATUS + DELIVERY + "/{order_id}")
    public ResponseEntity<?> updateStatusDelivery(@PathVariable Long order_id) {
        return new ResponseEntity<>(services.updateStatusOrder(order_id), HttpStatus.OK);
    }

    @GetMapping(STATUS + RECEIVE + "/{order_id}")
    public ResponseEntity<?> updateStatusReceive(@PathVariable Long order_id, Principal principal) {
        return new ResponseEntity<>(services.updateStatusOrderReceive(order_id, principal.getName()), HttpStatus.OK);
    }

//    @GetMapping(PLUS + "/{product_id}")
//    public OrderItemsResponse plusCountProduct(@PathVariable Long product_id, Principal principal) {
//        return services.plusCountProduct(product_id, principal.getName());
//    }
//
//    @GetMapping(MINUS + "/{product_id}")
//    public OrderItemsResponse minusCountProduct(@PathVariable Long product_id, Principal principal) {
//        return services.minusCountProduct(product_id, principal.getName());
//    }
}
