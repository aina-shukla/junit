package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import org.junit.jupiter.api.*;

class Test06MocksVerifyBehaviour {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setup() {
		this.paymentServiceMock = mock(PaymentService.class);
		this.roomServiceMock = mock(RoomService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.mailSenderMock = mock(MailSender.class);
		this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

	}

	@Test
	void shouldInvokePaymentWhenPrepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, true);

		// when
		bookingService.makeBooking(bookingRequest);

		// then
		verify(paymentServiceMock).pay(bookingRequest, 400);
	}

	@Test
	void shouldNotInvokePaymentWhenNotPrepaid() {
		// given
		BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, false);

		// when
		bookingService.makeBooking(bookingRequest);

		// then
		verify(paymentServiceMock, never()).pay(any(), anyDouble());
	}

}
