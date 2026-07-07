package com.alimberdi.unitconverter.controller;

import com.alimberdi.unitconverter.dto.ConvertRequest;
import lombok.RequiredArgsConstructor;
import com.alimberdi.unitconverter.service.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api")
class UnitController {

	private final ConversionService conversionService;

	@PostMapping("/convert")
	public ResponseEntity<Map<String, BigDecimal>> convert(@RequestBody ConvertRequest request) {
		return ResponseEntity
				.ok(Map.of("result", conversionService.convert(request)));
	}

}