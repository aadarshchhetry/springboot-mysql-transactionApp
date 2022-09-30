package com.transaction.payment.controller.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.payment.model.User;
import com.transaction.payment.repo.UserDao;

@Service
public class PaymentOperations {
	
	@Autowired
	UserDao userDao;

//	public PaymentOperations(UserDao userDao) {
//		this.userDao = userDao;
//	}

	public boolean transferAmount(String userId, int enteredPin, String receiverId, double transferedAmount) {
		User senderEntity = this.userDao.getReferenceById(userId);//adarsh0@upi
		User receiverEntity = this.userDao.getReferenceById(receiverId);
		if (isBalanceAvailable(senderEntity, transferedAmount)) {
			if (senderEntity.getPin() == enteredPin) {

				receiverEntity.setAmount(receiverEntity.getAmount() + transferedAmount);
				senderEntity.setAmount(senderEntity.getAmount() - transferedAmount);
				try {
					this.userDao.save(receiverEntity);
					this.userDao.save(senderEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}

	boolean isBalanceAvailable(User senderEntity, double transferedAmount) {
		try {
			if (senderEntity.getAmount() >= transferedAmount) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
}
