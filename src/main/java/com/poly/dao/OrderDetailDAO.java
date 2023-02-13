package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long>{

	//duy
	//@Query("SELECT od FROM OrderDetail od WHERE od.order.id = ?1")
		List<OrderDetail> findByOrderId(Long id);
		
		@Query(value="select p.id, p.Name, od.Price, od.Quantity,  SUM(od.price * od.quantity) as  'Gia' from Products as p join OrderDetails as od on p.Id = od.ProductId\n"
	            + "group by p.id, p.Name, od.Price, od.Quantity", nativeQuery = true)
	    List<Object[]> getStatsByProducts();
	 
	    @Query(value="SELECT Products.Name, Orders.CreateDate, SUM(OrderDetails.Price * OrderDetails.Quantity) from  Products join OrderDetails on Products.id=OrderDetails.ProductId\n"
	            + "join Orders on Orders.Id = OrderDetails.OrderId\n"
	            + "group by Products.Name, Orders.CreateDate\n"
	            + "order by Orders.CreateDate ASC", nativeQuery = true)
	    List<Object[]> getMonthStats();
	    
	    @Query(value="SELECT Products.Name, Orders.CreateDate, SUM(OrderDetails.Price * OrderDetails.Quantity) from  Products join OrderDetails on Products.id=OrderDetails.ProductId\n"
	            + "join Orders on Orders.Id = OrderDetails.OrderId\n"
	            + "where Orders.CreateDate between ?1 and ?2\n"
	            + "group by Products.Name, Orders.CreateDate\n"
	            + "order by Orders.CreateDate ASC", nativeQuery = true)
	    List<Object[]> getMonthStatsCheck(String fromDate, String toDate);
}
