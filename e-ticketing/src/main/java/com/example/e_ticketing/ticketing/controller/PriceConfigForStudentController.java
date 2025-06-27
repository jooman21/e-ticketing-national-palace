package com.example.e_ticketing.ticketing.controller;

import com.example.e_ticketing.ticketing.application.dto.PriceConfigForStudentDto;
import com.example.e_ticketing.ticketing.application.service.PriceConfigForStudentService;
import com.example.e_ticketing.ticketing.controller.GlobalResponse.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/priceConfigForStudent")
public class PriceConfigForStudentController {
    private final PriceConfigForStudentService priceConfigForStudentService;

        // 🔹 Create Price Config for Student
        @PostMapping
        public ResponseEntity<GenericResponse> create(@Valid  @RequestBody PriceConfigForStudentDto dto) {
            PriceConfigForStudentDto created = priceConfigForStudentService.createPriceConfigForStudent(dto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new GenericResponse(true, "Student price configuration created successfully.", created));
        }

        // 🔹 Get All Student Price Configs
        @GetMapping
        public ResponseEntity<GenericResponse> getAll() {
            List<PriceConfigForStudentDto> allConfigs = priceConfigForStudentService.getAllPriceConfigs();
            return ResponseEntity.ok(new GenericResponse(true, "All student price configurations retrieved.", allConfigs));
        }

        // 🔹 Get One by ID
        @GetMapping("/{id}")
        public ResponseEntity<GenericResponse> getById( @PathVariable @Valid UUID id) {
            PriceConfigForStudentDto config = priceConfigForStudentService.getPriceConfigForStudentById(id);
            return ResponseEntity.ok(new GenericResponse(true, "Student price configuration found.", config));
        }

        // 🔹 Update Config
        @PutMapping("/{id}")
        public ResponseEntity<GenericResponse> update( @PathVariable UUID id,
                                                       @Valid
                                                      @RequestBody PriceConfigForStudentDto dto) {
            PriceConfigForStudentDto updated = priceConfigForStudentService.updatePriceConfig(id, dto);
            return ResponseEntity.ok(new GenericResponse(true, "Student price configuration updated successfully.", updated));
        }

        // 🔹 Soft Delete (set active=false)
        @DeleteMapping("/{id}")
        public ResponseEntity<GenericResponse> delete( @PathVariable @Valid UUID id) {
            priceConfigForStudentService.deletePriceConfigForStudent(id);
            return ResponseEntity.ok(new GenericResponse(true, "Student price configuration deleted (deactivated).", null));
        }
}
