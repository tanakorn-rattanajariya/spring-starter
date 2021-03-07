package com.example.smsdrw.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@ToString
@Inheritance(strategy =InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
	@Id
	@JsonProperty( value = "username", access = JsonProperty.Access.WRITE_ONLY)
	private String username;
	@JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@JsonProperty( value = "ref", access = JsonProperty.Access.WRITE_ONLY)
	private String ref;
	@JsonProperty( value = "refTimestamp", access = JsonProperty.Access.WRITE_ONLY)
	private Date refTimestamp;
}
