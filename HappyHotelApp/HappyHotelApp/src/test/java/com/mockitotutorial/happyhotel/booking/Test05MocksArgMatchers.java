package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import org.junit.jupiter.api.*;

class Test05MocksArgMatchers {

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
	void shouldNotCompleteBookingWhenPriceIsHigh() {
		// given
		BookingRequest bookingRequest = new BookingRequest("2", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, true);
	//	when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(BusinessException.class);
		when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class);

		// any() -- for objects
		// any#() -- primitve specific

		// when
		org.junit.jupiter.api.function.Executable exec = () -> bookingService.makeBooking(bookingRequest);

		// then
		assertThrows(BusinessException.class, exec);
	}

}
