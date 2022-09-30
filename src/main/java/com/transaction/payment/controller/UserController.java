package com.transaction.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.payment.controller.operation.PaymentOperations;
import com.transaction.payment.controller.userOperation.UserOperation;
import com.transaction.payment.model.User;
import com.transaction.payment.repo.UserDao;

@RestController
public class UserController {
	@Autowired
	UserDao userDao;

	@Autowired
	PaymentOperations paymentOperation;

	@Autowired
	User newUser;

	@Autowired
	UserOperation userOperation;

	@RequestMapping("send")
	public boolean paymentTransfer(@RequestParam("userId") String userId, @RequestParam("userPin") int userPin,
			@RequestParam("receiverId") String receiverId, @RequestParam("amount") double transferAmount) {

		boolean paymentStatus = paymentOperation.transferAmount(userId, userPin, receiverId, transferAmount);

		return paymentStatus;

	}

	@RequestMapping("signUp")
	public boolean userSignUp(@RequestParam("userName") String userName, @RequestParam("userPin") int userPin) {

		boolean userAddStatus = userOperation.addUser(userName, userPin);

		return userAddStatus;

	}

	@RequestMapping("addAmount")
	public boolean userAddAmount(@RequestParam("receiverId") String receiverId,
			@RequestParam("amount") double newDepositeAmount) {
		try {
			User receiverEntity = userDao.getReferenceById(receiverId);
			receiverEntity.setAmount(newDepositeAmount);
			userDao.save(receiverEntity);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
