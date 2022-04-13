package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.awt.Desktop.Action;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test11MocksStaticMethods {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private PaymentService paymentServiceMock;

	@Mock
	private RoomService roomServiceMock;

	@Mock
	private BookingDAO bookingDAOMock;

	@Mock
	private MailSender mailSenderMock;

	@Test
	void shouldCalcCorrectPrice() {
		try (MockedStatic<CurrencyConverter> mocked = mockStatic(CurrencyConverter.class)) {
			// given
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
					LocalDate.of(2020, 01, 05), 2, false);
			double exp = 400.0;
			mocked.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

			// when
			double ctual = bookingService.calculatePrice(bookingRequest);
			
			// then
			assertEquals(exp, ctual);
		}
	}
}
