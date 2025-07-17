package com.ecomarket.usuario;

import com.ecomarket.usuario.exception.BadRequestException;
import com.ecomarket.usuario.exception.ResourceNotFoundException;
import com.ecomarket.usuario.model.UsuarioEntity;
import com.ecomarket.usuario.repository.UsuarioRepository;
import com.ecomarket.usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    private UsuarioEntity usuario;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioEntity();
        usuario.setId(1);
        usuario.setNombreCompleto("Juan Pérez");
        usuario.setRun("12345678-9");
        usuario.setCorreo("juan@example.com");
        usuario.setDireccion("Calle Falsa 123");
        usuario.setTelefono("912345678");
        usuario.setGiro("Venta minorista");
    }

    @Test
    void findAllUsuarios_ok() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioEntity> resultado = usuarioService.findAllUsuario();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreCompleto()).isEqualTo("Juan Pérez");
    }

    @Test
    void findUsuarioById_existente() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        UsuarioEntity resultado = usuarioService.findById(1);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombreCompleto());
    }

    @Test
    void findUsuarioById_noExiste() {
        when(usuarioRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> usuarioService.findById(2));
    }
    @Test
    void saveUsuario_ok() {
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioEntity guardado = usuarioService.save(usuario);

        assertNotNull(guardado);
        assertEquals("Juan Pérez", guardado.getNombreCompleto());
    }

    @Test
    void deleteUsuario_ok() {
        doNothing().when(usuarioRepository).deleteById(1);

        usuarioService.delete(1);

        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void updateUsuario_existente() {
        UsuarioEntity cambios = new UsuarioEntity();
        cambios.setNombreCompleto("Juan Actualizado");
        cambios.setRun("87654321-0");
        cambios.setCorreo("nuevo@example.com");
        cambios.setDireccion("Calle Nueva 456");
        cambios.setTelefono("987654321");
        cambios.setGiro("Servicios");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UsuarioEntity actualizado = usuarioService.update(1, cambios);

        assertEquals("Juan Actualizado", actualizado.getNombreCompleto());
        assertEquals("nuevo@example.com", actualizado.getCorreo());
    }

    @Test
    void updateUsuario_noExiste() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> usuarioService.update(99, usuario));
    }
}
