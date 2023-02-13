package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.poly.entity.Order;

public interface OrderDAO extends JpaRepository<Order, Long>{
	@Query("SELECT o FROM Order	o WHERE o.account.username=?1")
	List<Order> findByUsername(String username);

	void deleteById(Integer id);
	
	@Query(value = 
			"select  top 1 Orders.Username from\n" + 
			"Orders join OrderDetails on Orders.Id=OrderDetails.OrderId\n" + 
			"where Orders.Username = ?1 and  OrderDetails.ProductId= ?2", 
			nativeQuery = true)
	String check(String username, Integer id);
	
	//duy
	@Query(value="select Accounts.Fullname, Accounts.Username\n"
            + "from Accounts join Orders on Accounts.Username=Orders.Username\n"
            + "group by Accounts.Fullname, Accounts.Username", nativeQuery = true)
    List<Object[]> getCilents();
    
    @Query(value="select Products.Name, Orders.CreateDate, SUM(OrderDetails.Price * OrderDetails.Quantity)\n"
            + "from Accounts join Orders on Accounts.Username=Orders.Username\n"
            + "join OrderDetails on OrderDetails.OrderId=Orders.Id\n"
            + "join Products on Products.Id=OrderDetails.ProductId\n"
            + "where Accounts.Username like ?1 and (Orders.CreateDate between FORMAT(DATEADD(MONTH,-1,GETDATE()),'yyyy-MM-dd') and FORMAT(GETDATE(),'yyyy-MM-dd'))\n"
            + "group by Products.Name, Orders.CreateDate", nativeQuery=true)
     List<Object[]> getCilentByKeyWord(String key);
     
     @Transactional
     @Modifying 
     @Query(value="update Orders set OrderStatus = N'Đã giao' where Id = ?1", nativeQuery=true)
     void update_order(Long id);
}
