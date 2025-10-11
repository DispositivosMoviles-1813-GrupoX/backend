package pe.edu.upc.center.vitalia.payment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PaymentMethod(String paymentMethodId, String type) {

    public PaymentMethod() {
        this(null, null);
    }

    public PaymentMethod {
        if (paymentMethodId == null || paymentMethodId.isBlank()) {
            throw new IllegalArgumentException("Payment Method ID cannot be null or blank");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank");
        }
    }
}