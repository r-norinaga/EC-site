package katachi.spring.exercise.domain.user.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class Item {
	 private int id;
	 private String name;
	 private int price;
	 private LocalDateTime registrationDateTime;
	 private LocalDateTime updateDateTime;
	 private int deletion;
	 private int stock;
	 private String description;
	 private String image;
}
