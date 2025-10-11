package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.DashboardResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final TransacaoRepository transacaoRepository;

    private final UsuarioLogadoService usuarioLogadoService;


    public DashboardService(TransacaoRepository transacaoRepository, UsuarioLogadoService usuarioLogadoService) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioLogadoService = usuarioLogadoService;
    }

    public DashboardResponseDTO getDashboard() {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();

        BigDecimal totalEntradas = transacaoRepository.getTotalEntradas(usuarioId);
        BigDecimal totalSaidas = transacaoRepository.getTotalSaidas(usuarioId);
        BigDecimal totalAReceber = transacaoRepository.getTotalAReceber(usuarioId);
        BigDecimal totalAPagar = transacaoRepository.getTotalAPagar(usuarioId);

        if (totalEntradas == null) totalEntradas = BigDecimal.ZERO;
        if (totalSaidas == null) totalSaidas = BigDecimal.ZERO;
        if (totalAReceber == null) totalAReceber = BigDecimal.ZERO;
        if (totalAPagar == null) totalAPagar = BigDecimal.ZERO;

        BigDecimal saldo = totalEntradas.subtract(totalSaidas);
        
        return new DashboardResponseDTO(
                totalEntradas,
                totalSaidas,
                saldo,
                totalAReceber,
                totalAPagar
        );
    }
}
