package com.shinohara.web.models.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EmployeeValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeValid {
	String message() default "入力された社員番号の情報は既に存在しています。";
	Class<?>[] groups() default{};
	Class<? extends Payload>[] payload() default{};
}
