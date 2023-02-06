package com.bank.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
@Builder
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;


}