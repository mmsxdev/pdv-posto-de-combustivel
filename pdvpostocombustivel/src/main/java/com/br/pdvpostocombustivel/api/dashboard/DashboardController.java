package com.br.pdvpostocombustivel.api.dashboard;

import com.br.pdvpostocombustivel.api.dashboard.dto.DashboardMetricasResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/metricas")
    public ResponseEntity<DashboardMetricasResponse> getMetricas() {
        return ResponseEntity.ok(dashboardService.getMetricasDoDia());
    }

    @GetMapping("/estoque-critico")
    public ResponseEntity<Long> getContagemEstoqueCritico() {
        return ResponseEntity.ok(dashboardService.getContagemEstoqueCritico());
    }

}