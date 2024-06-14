package com.springbootapi.springbootapi.models.DTO;

/**
 * @author William Toloto
 */


public record CustomerRequestDTO( String name, String docId, String email, String cityId,
									String postalCode, String houseNumber, String reference,
									String phone) {

}
