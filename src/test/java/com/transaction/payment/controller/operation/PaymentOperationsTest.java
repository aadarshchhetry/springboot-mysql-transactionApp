package com.transaction.payment.controller.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.transaction.payment.model.User;
import com.transaction.payment.repo.UserDao;

@ExtendWith(MockitoExtension.class)
class PaymentOperationsTest {
	
	@InjectMocks
	PaymentOperations paymentOperations;

	@Mock
	UserDao userDao;

	@Test
	void testTransfer() {
		User entityS = new User("adarsh0@upi", "adarsh", 123, 2000.0);
		User entityR = new User("adarsh1@upi", "adarsh", 123, 2000.0);

		when(userDao.getReferenceById(entityS.getUid())).thenReturn(entityS);
		when(userDao.getReferenceById(entityR.getUid())).thenReturn(entityR);

		double amount = 500.0;
		boolean result = paymentOperations.transferAmount("adarsh0@upi", 123, "adarsh1@upi", amount);
		assertEquals(true, result);

	}

	@ParameterizedTest
	@ValueSource(doubles = { 100.0, 500.0, 2000.0, 3000.0, 5000.0 })
	void availableBalanceCheck(double amount) {
		User entityS = new User("adarsh@upi", "adarsh", 123, 5000.0);//3000
		assertEquals(true, paymentOperations.isBalanceAvailable(entityS, amount));

	}

}
