package com.alimberdi.unitconverter.dto;

import com.alimberdi.unitconverter.enums.Unit;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ConvertRequest(
		@NotNull(message = "Value cannot be empty")
		@DecimalMax(value = "100000000000.0", message = "Value is too large")
		BigDecimal value,

		Unit from,
		Unit to
) {}