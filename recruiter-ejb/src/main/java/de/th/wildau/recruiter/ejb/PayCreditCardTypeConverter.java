package de.th.wildau.recruiter.ejb;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA enum type converter.
 *
 * @author s7n
 */
@Converter(autoApply = true)
public class PayCreditCardTypeConverter implements AttributeConverter<PayCreditCardType, String> {

    @Override
    public String convertToDatabaseColumn(final PayCreditCardType cc) {
        switch (cc) {
            case VISA:
                return "VISA";
            case MASTER_CARD:
                return "Master Card";
            case AMERICAN_EXPRESS:
                return "American Express";
            default:
                throw new IllegalArgumentException("Unknown value: " + cc);
        }
    }

    @Override
    public PayCreditCardType convertToEntityAttribute(final String dbData) {
        switch (dbData) {
            case "VISA":
                return PayCreditCardType.VISA;
            case "Master Card":
                return PayCreditCardType.MASTER_CARD;
            case "American Express":
                return PayCreditCardType.AMERICAN_EXPRESS;
            default:
                throw new IllegalArgumentException("Unknown value: " + dbData);
        }
    }
}
