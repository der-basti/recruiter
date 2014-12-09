package de.th.wildau.recruiter.ejb;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA enum type converter.
 *
 * @author s7n
 */
@Converter(autoApply = true)
public class RoleNameConverter implements AttributeConverter<RoleName, String> {

    @Override
    public String convertToDatabaseColumn(final RoleName role) {
        switch (role) {
            case ADMIN:
                return "Administrator";
            case COMPANY:
                return "Unternehmen";
            case USER:
                return "Benutzer";
            default:
                throw new IllegalArgumentException("Unknown value: " + role);
        }
    }

    @Override
    public RoleName convertToEntityAttribute(final String dbData) {
        switch (dbData) {
            case "ADMIN":
                return RoleName.ADMIN;
            case "COMPANY":
                return RoleName.COMPANY;
            case "USER":
                return RoleName.USER;
            default:
                throw new IllegalArgumentException("Unknown value: " + dbData);
        }
    }
}
