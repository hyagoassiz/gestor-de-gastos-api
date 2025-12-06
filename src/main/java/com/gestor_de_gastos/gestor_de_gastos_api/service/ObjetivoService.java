package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.ObjetivoResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Objetivo;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.ObjetivoRespository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.List;

@Service
public class ObjetivoService {

    private final ObjetivoRespository objetivoRespository;
    private final UsuarioLogadoService usuarioLogadoService;
    private final ContaService contaService;

    public ObjetivoService(ObjetivoRespository objetivoRespository, UsuarioLogadoService usuarioLogadoService,
            ContaService contaService) {
        this.objetivoRespository = objetivoRespository;
        this.usuarioLogadoService = usuarioLogadoService;
        this.contaService = contaService;
    }

    public List<ObjetivoResponseDTO> listarTodosByFiltro(String textoBusca) {
        Long usuarioId = usuarioLogadoService.getUsuarioLogado().getId();

        List<Objetivo> objetivos = objetivoRespository.findByFiltro(usuarioId, textoBusca);

        return objetivos.stream().map(obj -> {

            BigDecimal valorAtual = contaService
                    .buscarSaldoPorContaId(true, obj.getConta().getId())
                    .getSaldo();

            BigDecimal percentual = BigDecimal.ZERO;
            if (obj.getValor().compareTo(BigDecimal.ZERO) > 0) {
                percentual = valorAtual
                        .divide(obj.getValor(), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            }

            return new ObjetivoResponseDTO(
                    obj.getId(),
                    obj.getNome(),
                    obj.getValor(),
                    valorAtual,
                    percentual,
                    obj.getDataConclusao().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate());
        }).toList();
    }

    public Objetivo buscarPorId(Long id) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        return objetivoRespository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Objetivo não encontrada"));
    }

    public Objetivo salvar(Objetivo objetivo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        objetivo.setUsuario(usuario);

        return objetivoRespository.save(objetivo);
    }

    public Objetivo atualizar(Long id, Objetivo objetivo) {
        Objetivo objetivoExistente = buscarPorId(id);

        objetivoExistente.setNome(objetivo.getNome());
        objetivoExistente.setValor(objetivo.getValor());
        objetivoExistente.setDataConclusao(objetivo.getDataConclusao());
        objetivoExistente.setConta(objetivo.getConta());
        objetivoExistente.setObservacao(objetivo.getObservacao());

        return objetivoRespository.save(objetivoExistente);
    }

    public void deletarPorId(Long id) {
        Objetivo objetivoExistente = buscarPorId(id);

        objetivoRespository.deleteById(objetivoExistente.getId());
    }
}
