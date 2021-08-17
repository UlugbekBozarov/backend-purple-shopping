package com.example.online_shopping.services;

import com.example.online_shopping.database.domain.*;
import com.example.online_shopping.database.domain.enumeration.ORDER_STATUS;
import com.example.online_shopping.database.repository.*;
import com.example.online_shopping.erroe.NotFound;
import com.example.online_shopping.erroe.message.ErrorMessage;
import com.example.online_shopping.request.OrdersRequest;
import com.example.online_shopping.response.order.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServices {

    private UsersRepository usersRepository;

    private UserAddressRepo addressRepo;

    private ScheduleRepo scheduleRepo;

    private UserContactRepo contactRepo;

    private VaucherRepo vaucherRepo;

    private OrdersRepo ordersRepo;

    private OrderItemsRepo itemsRepo;

    private ProductsRepo productsRepo;

    @Autowired
    public OrderServices(UsersRepository usersRepository, UserAddressRepo addressRepo, ScheduleRepo scheduleRepo, UserContactRepo contactRepo, VaucherRepo vaucherRepo, OrdersRepo ordersRepo, OrderItemsRepo itemsRepo, ProductsRepo productsRepo) {
        this.usersRepository = usersRepository;
        this.addressRepo = addressRepo;
        this.scheduleRepo = scheduleRepo;
        this.contactRepo = contactRepo;
        this.vaucherRepo = vaucherRepo;
        this.ordersRepo = ordersRepo;
        this.itemsRepo = itemsRepo;
        this.productsRepo = productsRepo;
    }


    public List<Orders> getOrdersAccepted(String username) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(username);
            List<Orders> orders;
            if (user.getRole().equals("ADMIN")) {
                orders = ordersRepo.findAllByStatusAccepted() ;
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).getUser().setAddresses(null);
                    orders.get(i).getUser().setCards(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            } else {
                orders = ordersRepo.findAllByUserAndStatus(user);
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).setUser(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            }

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order not found");
            throw new NotFound("Get Order", -2, error);
        }
    }

    public List<Orders> getOrdersDelivery(String username) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(username);
            List<Orders> orders;
            if (user.getRole().equals("ADMIN")) {
                orders = ordersRepo.findAllByStatusDelivery() ;
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).getUser().setAddresses(null);
                    orders.get(i).getUser().setCards(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            } else {
                orders = ordersRepo.findAllByUserAndStatus(user);
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).setUser(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            }

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order not found");
            throw new NotFound("Get Order", -2, error);
        }
    }

    public List<Orders> getOrdersAcceptedOrDelivery(String username) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(username);
            List<Orders> orders;
            if (user.getRole().equals("ADMIN")) {
                orders = ordersRepo.findAllByStatusAccepted() ;
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).getUser().setAddresses(null);
                    orders.get(i).getUser().setCards(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            } else {
                orders = ordersRepo.findAllByUserAndStatus(user);
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).setUser(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            }

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order not found");
            throw new NotFound("Get Order", -2, error);
        }
    }

    public List<Orders> getOrdersReceive(String username) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(username);
            List<Orders> orders;
            if (user.getRole().equals("ADMIN")) {
                orders = ordersRepo.findAllByStatusReceive() ;
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).getUser().setAddresses(null);
                    orders.get(i).getUser().setCards(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            } else {
                orders = ordersRepo.findAllByUserAndStatus(user);
                for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).setUser(null);
                    for (int j = 0; j < orders.get(i).getOrderItems().size(); j++) {
                        orders.get(i).getOrderItems().get(j).getProducts().setImage_source("");
                    }
                }
                return orders;
            }

        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order not found");
            throw new NotFound("Get Order", -2, error);
        }
    }


    public OrderResponse saveOrders(OrdersRequest ordersReq, Principal principal) throws NotFound {

        if (ordersReq.getItems().size() == 0) {
            ErrorMessage error = new ErrorMessage(406, "You did not select any product.");
            throw new NotFound("Save Order", -2, error);
        }

        try {
            Users user = usersRepository.findByUsername(principal.getName());

            UserAddress address = addressRepo.findById(ordersReq.getAddress_id()).get();

            Schedule schedule = scheduleRepo.findById(ordersReq.getSchedule()).get();

            UserContact contact = contactRepo.findById((long) ordersReq.getContact_id()).get();

            Vaucher vaucher = null;
            if (ordersReq.getVaucher_code() != null) {
                vaucher = vaucherRepo.findByVaucher_code(ordersReq.getVaucher_code());
            }

            List<OrderItems> items = new ArrayList<>();

            for (int i = 0; i < ordersReq.getItems().size(); i++) {
                items.add(new OrderItems(productsRepo.findById(ordersReq.getItems().get(i).getProduct_id()).get(), ordersReq.getItems().get(i).getQuantity()));
            }

            itemsRepo.saveAll(items);

            Orders order = new Orders(user, address, schedule, contact, vaucher, items);
            ordersRepo.save(order);

            OrderResponse response = new OrderResponse(order.getOrder_id(), order.getTime_of_ordening(), order.getGrant_total(), order.getOrderItems().size(), order.getSchedule().getSchedule_time(), order.getAddress().getAddress(), order.getVaucher() != null ? (true) : (false));

            log.info("Save Order: {}", order.toString());

            return response;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order has not been accepted!");
            throw new NotFound("Save Order", -2, error);
        }

    }


    public List<Orders> updateStatusOrder(Long order_id) throws NotFound {
        try {
            Orders order = ordersRepo.findById(order_id).get();
            order.setStatus(ORDER_STATUS.DELIVERY);
            ordersRepo.save(order);

            log.info("Update Order Status = DELIVERY", order.toString());

            return ordersRepo.findAllByStatusAccepted();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order has not been updated!");
            throw new NotFound("Update User Order", -2, error);
        }
    }

    public List<Orders> updateStatusOrderReceive(Long order_id, String username) throws NotFound {
        try {
            Users user = usersRepository.findByUsername(username);
            Orders order = ordersRepo.findByOrder_idAndUser(order_id, user.getUser_id());
            order.setStatus(ORDER_STATUS.RECEIVE);
            ordersRepo.save(order);
            log.info("Update Order Status = RECEIVE", order.toString());
            return ordersRepo.findAllByUserAndStatus(user);
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(406, "The order has not been updated!");
            throw new NotFound("Update User Order", -2, error);
        }
    }



//    public OrdersResponse getOrder(String username) throws NotFound {
//        try {
//            Users user = usersRepository.findByUsername(username);
//            List<Orders> orders = orderRepo.findAllByUserId(user.getUser_id());
//
//            return new OrdersResponse(orders, totalAmount(orders));
//        } catch (Exception e) {
//            ErrorMessage error = new ErrorMessage(404, "No information was found in the database.");
//            throw new NotFound("Find All - Order", -1, error);
//        }
//    }





    /*public Orders getOrderById(Long order_id, String token) throws SystemError {
        try {
            return orderRepo.findById(order_id).get();
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(99, "Could not retrieve orders data from the database!");
            throw new SystemError("get", -1, error);
        }
    }*/


//    public OrderItemsResponse plusCountProduct(Long product_id, String username) throws NotFound {
//
//        try {
//
//            Users user = usersRepository.findByUsername(username);
//            Products product = productsRepo.findById(product_id).get();
//            int quantity = 1;
//            try {
//                Orders order = orderRepo.findByUserIdAndProductId(user.getUser_id(), product_id);
//                order.setQuantity(order.getQuantity() + 1);
//                orderRepo.save(order);
//                quantity = order.getQuantity();
//            } catch (Exception e) {
//                Orders order1 = new Orders(user, product, 1);
//                orderRepo.save(order1);
//                quantity = order1.getQuantity();
//            }
//
//            List<Orders> list = orderRepo.findAllByUserId(user.getUser_id());
//
//            OrderItemsResponse response = new OrderItemsResponse(product_id, quantity, list.size(), totalAmount(list));
//            return response;
//        } catch (Exception e) {
//            ErrorMessage error = new ErrorMessage(404, "There is no such product.");
//            throw new NotFound("Plus - Order", -1, error);
//        }
//
//    }


//    public OrderItemsResponse minusCountProduct(Long product_id, String username) throws NotFound {
//
//        int quantity = 0;
//
//        try {
//
//            Users user = usersRepository.findByUsername(username);
//            Orders order = orderRepo.findByUserIdAndProductId(user.getUser_id(), product_id);
//
//            if (order.getQuantity() > 1) {
//                order.setQuantity(order.getQuantity() - 1);
//                orderRepo.save(order);
//                quantity = order.getQuantity();
//
//            } else {
//                quantity = 0;
//                try {
//                    orderRepo.deleteById(order.getOrder_id());
//                } catch (Exception e) {
//                }
//            }
//
//            List<Orders> list = orderRepo.findAllByUserId(user.getUser_id());
//
//            OrderItemsResponse response = new OrderItemsResponse(product_id, quantity, list.size(), totalAmount(list));
//            return response;
//        } catch (Exception e) {
//            ErrorMessage error = new ErrorMessage(404, "There is no such product.");
//            throw new NotFound("Minus - Order", -1, error);
//        }
//
//    }


    /*public OrderItemsResponse saveOrder(OrderRequest order, String username) throws SystemError {

        try {

            Users user = usersRepository.findByUsername(username);
            Products product = productsRepo.findById(order.getProduct_id()).get();

            Orders orders = new Orders(user, product, order.getQuantity());
            orderRepo.save(orders);

            log.info("Save Order {}", orders.toString());

            OrderItemsResponse response = new OrderItemsResponse(orderRepo.findAllByUserId(user.getUser_id()).size(), totalAmount(orderRepo.findAllByUserId(user.getUser_id())));
            return response;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(108, "There is no such product.");
            throw new SystemError("save", -1, error);
        }

    }


    public OrderItemsResponse updateOrderQuantity(String username, Long product_id, Integer quantity) throws SystemError {

        try {

            Users user = usersRepository.findByUsername(username);
            Orders orders = orderRepo.findByUserIdAndProductId(user.getUser_id(), product_id);

            orders.setQuantity(quantity);

            orderRepo.save(orders);

            log.info("Update Order Quantity {}", orders.toString());

            OrderItemsResponse response = new OrderItemsResponse(orderRepo.findAllByUserId(user.getUser_id()).size(), totalAmount(orderRepo.findAllByUserId(user.getUser_id())));
            return response;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(103, "The orders has not been modified");
            throw new SystemError("update", -1, error);
        }
    }


    public OrderItemsResponse deleteOrder(String username, Long product_id) throws SystemError  {

        try {
            Users user = usersRepository.findByUsername(username);

            Orders orders = orderRepo.findByUserIdAndProductId(user.getUser_id(), product_id);

            orderRepo.deleteById(orders.getOrder_id());

            log.info("Delete Order {}", orders.toString());

            OrderItemsResponse response = new OrderItemsResponse(orderRepo.findAllByUserId(user.getUser_id()).size(), totalAmount(orderRepo.findAllByUserId(user.getUser_id())));
            return response;
        } catch (Exception e) {
            ErrorMessage error = new ErrorMessage(100, "The orders has not been deleted!");
            throw new SystemError("delete", -1, error);
        }
    }*/


    /*public String checkOrder(String username, Long product_id) throws SystemError {

        try {
            Users user = usersRepository.findByUsername(username);
            Orders order = orderRepo.findByUserIdAndProductId(user.getUser_id(), product_id);
            if (order != null) {
                return "200";
            } else return "-1";
        } catch (Exception e) {
            return "-1";
        }
    }*/


//    private double totalAmount(List<Orders> orders) {
//        double total_price = 0;
//        for (int i = 0; i < orders.size(); i++) {
//            total_price += orders.get(i).getTotal_price();
//        }
//        return total_price;
//    }


}
