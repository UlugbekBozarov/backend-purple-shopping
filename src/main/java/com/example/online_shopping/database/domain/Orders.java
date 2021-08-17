package com.example.online_shopping.database.domain;

import com.example.online_shopping.database.domain.enumeration.ORDER_STATUS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne
    @JoinColumn(name = "address_id")
    private UserAddress address;

    @OneToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToOne
    @JoinColumn(name = "contact_id")
    private UserContact contact;

    @OneToOne
    @JoinColumn(name = "vaucher_id")
    private Vaucher vaucher;

    @OneToMany()
    @JoinColumn(name = "order_id")
    private List<OrderItems> orderItems;

    @NotNull
    private Date time_of_ordening;

    private Date receive_time;

    @NotNull
    private ORDER_STATUS status;

    private double grant_total;

    public Orders(Users user, UserAddress address, Schedule schedule, UserContact contact, Vaucher vaucher, List<OrderItems> orderItems) {
        this.user = user;
        this.address = address;
        this.schedule = schedule;
        this.contact = contact;
        this.vaucher = vaucher;
        this.orderItems = orderItems;
        this.time_of_ordening = new Date();
        this.status = ORDER_STATUS.ACCEPTED;
        this.grant_total = getGrantTotal(orderItems);
    }

    public void setStatus(ORDER_STATUS status) {
        if (status == ORDER_STATUS.RECEIVE) {
            this.receive_time = new Date();
        }
        this.status = status;
    }

    private static double getGrantTotal(List<OrderItems> orderItems) {
        double total = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            total += orderItems.get(i).getTotal_price();
        }
        return total;
    }
}
