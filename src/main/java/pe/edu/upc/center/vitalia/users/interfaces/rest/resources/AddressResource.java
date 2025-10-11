package pe.edu.upc.center.vitalia.users.interfaces.rest.resources;

public record AddressResource(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {}
