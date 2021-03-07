package com.example.smsdrw.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CheckInCheckOut {
	private Integer checkIn;
	private Integer checkOut;
	private Integer numberOfSale;
}
