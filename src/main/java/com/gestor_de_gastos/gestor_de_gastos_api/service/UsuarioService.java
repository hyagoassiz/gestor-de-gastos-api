package com.gestor_de_gastos.gestor_de_gastos_api.service;

import com.gestor_de_gastos.gestor_de_gastos_api.dto.LoginRequestDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.LoginResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.UsuarioRequestDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.dto.UsuarioResponseDTO;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Categoria;
import com.gestor_de_gastos.gestor_de_gastos_api.entity.Usuario;
import com.gestor_de_gastos.gestor_de_gastos_api.enums.TipoMovimentacao;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.CategoriaRepository;
import com.gestor_de_gastos.gestor_de_gastos_api.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public LoginResponseDTO autenticar(LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponseDTO(token);
    }

    public UsuarioResponseDTO registrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario salvo = usuarioRepository.save(usuario);

        Categoria transferenciaSaida = new Categoria();
        transferenciaSaida.setNome("Transferência entre Contas");
        transferenciaSaida.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        transferenciaSaida.setAtivo(true);
        transferenciaSaida.setPadrao(true);
        transferenciaSaida.setUsuario(salvo);
        transferenciaSaida.setObservacao("Categoria gerada automaticamente pelo sistema");
        categoriaRepository.save(transferenciaSaida);

        Categoria transferenciaEntrada = new Categoria();
        transferenciaEntrada.setNome("Transferência entre Contas");
        transferenciaEntrada.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        transferenciaEntrada.setAtivo(true);
        transferenciaEntrada.setPadrao(true);
        transferenciaEntrada.setUsuario(salvo);
        transferenciaEntrada.setObservacao("Categoria gerada automaticamente pelo sistema");
        categoriaRepository.save(transferenciaEntrada);

        Categoria ajusteSaldoEntrada = new Categoria();
        ajusteSaldoEntrada.setNome("Ajuste de Saldo");
        ajusteSaldoEntrada.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        ajusteSaldoEntrada.setAtivo(true);
        ajusteSaldoEntrada.setPadrao(true);
        ajusteSaldoEntrada.setUsuario(salvo);
        ajusteSaldoEntrada.setObservacao(
                "Categoria gerada automaticamente pelo sistema");
        categoriaRepository.save(ajusteSaldoEntrada);

        Categoria ajusteSaldoSaida = new Categoria();
        ajusteSaldoSaida.setNome("Ajuste de Saldo");
        ajusteSaldoSaida.setTipoMovimentacao(TipoMovimentacao.SAIDA);
        ajusteSaldoSaida.setAtivo(true);
        ajusteSaldoSaida.setPadrao(true);
        ajusteSaldoSaida.setUsuario(salvo);
        ajusteSaldoSaida.setObservacao(
                "Categoria gerada automaticamente pelo sistema");
        categoriaRepository.save(ajusteSaldoSaida);

        return new UsuarioResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail());
    }
}
