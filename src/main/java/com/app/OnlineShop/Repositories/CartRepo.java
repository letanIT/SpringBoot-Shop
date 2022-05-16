package com.app.OnlineShop.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.app.OnlineShop.models.Cart;
@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{

	@Query("SELECT e FROM Cart e WHERE e.prod_id = ?1")
	Cart findByProdId(long prod_id);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Cart e WHERE e.prod_id = ?1")
	void deleteByProductId(long prod_id);
}
