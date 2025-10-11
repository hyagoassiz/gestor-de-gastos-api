package com.gestor_de_gastos.gestor_de_gastos_api.controller;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.DashboardResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.DespesaPorCategoriaDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.TransacaoMensalResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.service.DashboardService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/totais")
    public DashboardResponseDTO getDashboard() {
        return dashboardService.getDashboard();
    }

    @GetMapping("/mensal")
    public List<TransacaoMensalResponseDTO> getTransacoesMensais() {
        return dashboardService.getTransacoesMensais();
    }

    @GetMapping("/despesas-por-categoria")
    public List<DespesaPorCategoriaDTO> getDespesasPorCategoria() {
        return dashboardService.getDespesasPorCategoria();
    }
}