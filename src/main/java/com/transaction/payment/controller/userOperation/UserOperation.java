package com.transaction.payment.controller.userOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.payment.model.User;
import com.transaction.payment.repo.UserDao;

@Service
public class UserOperation {
	
	User newUser;
	@Autowired
	UserDao userDao;

	public UserOperation( User newUser) {
		this.newUser = newUser;
	}
	public UserOperation() {
	}


	public boolean addUser(String userName, int userPin) {
		try {
			newUser.setAmount(0.0);
			newUser.setName(userName);
			newUser.setPin(userPin);

			setUserId(userName);
			
			userDao.save(newUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void setUserId(String userName) {
		long userIdSuffix = findUserCount(userName);
		newUser.setUid(userName + userIdSuffix + "@upi");
		
	}

	private long findUserCount(String userName) {
		try {
			long userNameDuplicateCount = userDao.findAll().stream().map(user -> user.getName())
					.filter(n -> n.equals(userName)).count();
			return userNameDuplicateCount;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
}
