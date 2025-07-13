package com.example.e_ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class eTicketingApplication {

	public static void main(String[] args) {
		SpringApplication.run(eTicketingApplication.class, args);
		System.out.println("  Application Started successfully!     \n" +
				" .----------------------------------.       \n" +
				" '----------------------------------'       \n" +
				"    [    ]  [    ]  [    ]  [    ]          \n" +
				"     ||||    ||||    ||||    ||||           \n" +
				"     ||||    ||||    ||||    ||||           \n" +
				"     ||||    ||||    ||||    ||||           \n" +
				"     ||||    ||||    ||||    ||||           \n" +
				"     ||||    ||||    ||||    ||||           \n" +
				"     ||||    ||||    ||||    ||||           \n" +
				"    [    ]  [    ]  [    ]  [    ]          \n" +
				" .----------------------------------.       \n" +
				" '----------------------------------'       ");
	}

}