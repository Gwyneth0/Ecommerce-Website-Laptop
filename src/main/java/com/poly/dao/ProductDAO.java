package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Product;

public interface ProductDAO extends JpaRepository<Product, Integer>{

	@Query("SELECT p FROM Product p WHERE p.category.id = ?1")
	List<Product> findByCategoryId(Integer cid);
	
	@Query("SELECT p FROM Product p WHERE p.brand.id = ?1")
	List<Product> findByBrandId(Integer bid);
//	@Query(value = "SELECT TOP 1 column FROM table ORDER BY NEWID()")
//	List<Product> findByBrandId(Integer bid);
	
	@Query("SELECT p FROM Product p WHERE p.category.id=?1 order by p.price desc")
	Page<Product> PageCategoryId(Integer cid, String sort, Pageable pageable);
	
	//day
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	List<Product> PageCategoryId(Integer cid);
	
	
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
	Page<Product> PageBrandId(Integer bid, Pageable pageable);
	
	@Query(value="select Products.* from \n" + 
			"Categories join Products on Categories.Id=Products.CategoryId join\n" + 
			"Brands on Brands.Id=Products.BrandId where Categories.Id=?1 and Brands.Id=?2", nativeQuery=true)
	List<Product> loadProductByBrandIdAndCateId(Integer cid, Integer bid);
	
	@Query(value="select Products.* from Products join Categories on Categories.Id = Products.CategoryId\n" + 
			"where Categories.Id=?1 and (Products.Price between ?2 and ?3)", nativeQuery=true)
	List<Product> findByMinandMax(Integer bid, Double min, Double max);
	
	@Query(value="SELECT * FROM Products where Name like ?1", nativeQuery =true)
	Page<Product> findByKeywords(String key, Pageable pageable);
	/////////////////////////// ban chay
	@Query(value="select Top 8 p.* from Products as p inner join \n" + 
			"OrderDetails as od on  p.Id = od.ProductId \n" + 
			"GROUP by  p.Id, p.Name, p.Image, p.Price, p.CreateDate, p.Available,\n" + 
			"p.CategoryId, p.BrandId, p.Discription, p.Quantity, p.isDeleted\n" + 
			"\n" + 
			"ORDER BY COUNT(p.Id) DESC", nativeQuery=true)
	List<Product> findBySellExpensive();
	
	@Query("SELECT o FROM Product o WHERE o.category.id = ?1 and (o.price BETWEEN ?2 AND ?3) ")
	Page<Product> findByPrice(Integer cate_id ,Double minPrice, Double maxPrice, Pageable pageable);
	//duy
	@Query("SELECT p FROM Product p WHERE p.id NOT IN (SELECT d.product.id FROM Discount d)")
	List<Product> getProductNotInDiscount();
	
	@Query(value="SELECT c.name, COUNT(p.id) FROM Products as p JOIN Categories as c ON c.Id = p.CategoryId\n"
            + "GROUP BY c.Name", nativeQuery =true)
    List<Object[]> getStatsByCategories();
    
    @Query(value="select Products.Name, OrderDetails.Quantity\n"
            + "from Products join OrderDetails on Products.Id=OrderDetails.ProductId\n"
            + "group by Products.Name, OrderDetails.Quantity", nativeQuery=true)
    List<Object[]> getStatsByQuntity();
}
