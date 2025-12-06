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

import java.util.List;

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

                Usuario usuario = Usuario.builder()
                                .nome(dto.getNome())
                                .email(dto.getEmail())
                                .senha(passwordEncoder.encode(dto.getSenha()))
                                .build();

                Usuario salvo = usuarioRepository.save(usuario);

                String observacaoPadrao = "Categoria gerada automaticamente pelo sistema";

                Categoria transferenciaSaida = Categoria.builder()
                                .nome("Transferência entre Contas")
                                .tipoMovimentacao(TipoMovimentacao.SAIDA)
                                .ativo(true)
                                .padrao(true)
                                .usuario(salvo)
                                .observacao(observacaoPadrao)
                                .build();

                Categoria transferenciaEntrada = Categoria.builder()
                                .nome("Transferência entre Contas")
                                .tipoMovimentacao(TipoMovimentacao.ENTRADA)
                                .ativo(true)
                                .padrao(true)
                                .usuario(salvo)
                                .observacao(observacaoPadrao)
                                .build();

                Categoria ajusteSaldoEntrada = Categoria.builder()
                                .nome("Ajuste de Saldo")
                                .tipoMovimentacao(TipoMovimentacao.ENTRADA)
                                .ativo(true)
                                .padrao(true)
                                .usuario(salvo)
                                .observacao(observacaoPadrao)
                                .build();

                Categoria ajusteSaldoSaida = Categoria.builder()
                                .nome("Ajuste de Saldo")
                                .tipoMovimentacao(TipoMovimentacao.SAIDA)
                                .ativo(true)
                                .padrao(true)
                                .usuario(salvo)
                                .observacao(observacaoPadrao)
                                .build();

                categoriaRepository.saveAll(
                                List.of(
                                                transferenciaSaida,
                                                transferenciaEntrada,
                                                ajusteSaldoEntrada,
                                                ajusteSaldoSaida));

                return new UsuarioResponseDTO(salvo.getId(), salvo.getNome(), salvo.getEmail());
        }

}
