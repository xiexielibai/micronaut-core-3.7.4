package io.micronaut.validation.validator.constraints.custom;

import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import jakarta.inject.Singleton;

@Factory
class CustomConstraintsValidatorFactory {
    @Singleton
    ConstraintValidator<AlwaysInvalidConstraint, Object> alwaysInvalidConstraintValidator() {
        return (value, annotationMetadata, context) -> false;
    }

    @Singleton
    ConstraintValidator<CustomMessageConstraint, Object> customMessageConstraintValidator() {
        return (value, annotationMetadata, context) -> {
            context.messageTemplate("custom invalid");
            return false;
        };
    }
}
