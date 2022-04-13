package com.mockitotutorial.happyhotel.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class Test09MocksAnnotations {

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
	void shouldThrowExc_WhenInputOK() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, false);
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

		// when
		org.junit.jupiter.api.function.Executable exec = () -> bookingService.makeBooking(bookingRequest);

		// then
		assertThrows(BusinessException.class, exec);

	}

	@Test
	void shouldNotThrowExc_WhenInputOK() {
		// given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01), LocalDate.of(2020, 01, 05),
				2, false);
		doNothing().when(mailSenderMock).sendBookingConfirmation(any());

		// when
		bookingService.makeBooking(bookingRequest);

		// then
		// no exception
	}

}
