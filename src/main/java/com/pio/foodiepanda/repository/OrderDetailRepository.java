package com.pio.foodiepanda.repository;

import com.pio.foodiepanda.dto.OrderDetailDTO;
import com.pio.foodiepanda.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrders_OrderId(Long orderId);

//    @Query("SELECT new com.pio.foodiepanda.dto.OrderDetailDTO(od.orders.orderId, o.status, o.totalAmount, o.customer.customerID, o.deliveryAddress.addressId, o.restaurant.restaurantId, od.menuItems.menuItemId, od.quantity, od.price) " +
//            "FROM OrderDetail od JOIN od.orders o WHERE od.orders.orderId = :orderId")
//    List<OrderDetailDTO> f(@Param("orderId") Long orderId);
}
