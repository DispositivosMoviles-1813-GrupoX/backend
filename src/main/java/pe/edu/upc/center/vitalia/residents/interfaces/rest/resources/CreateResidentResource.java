package pe.edu.upc.center.vitalia.residents.interfaces.rest.resources;

import java.util.Date;

public record CreateResidentResource(
        String dni,
        String firstName,
        String lastName,
        String city,
        String state,
        String country,
        String street,
        String zipCode,
        Date birthDate,
        String gender,
        Long receiptId
) {}
