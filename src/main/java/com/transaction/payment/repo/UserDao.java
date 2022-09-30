package com.transaction.payment.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.transaction.payment.model.User;

@Repository
public interface UserDao extends JpaRepository<User,String>{

	

}
