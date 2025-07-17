package com.ecomarket.usuario;


import com.ecomarket.usuario.assembler.UsuarioModelAssembler;
import com.ecomarket.usuario.controller.UsuarioController;
import com.ecomarket.usuario.exception.BadRequestException;
import com.ecomarket.usuario.model.UsuarioEntity;
import com.ecomarket.usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    UsuarioService usuarioService;

    @MockitoBean
    UsuarioModelAssembler assembler;

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
        usuario.setGiro("Retail");
    }

    @Test
    void shouldReturnAllUsuarios_HAL() throws Exception {
        when(usuarioService.findAllUsuario()).thenReturn(List.of(usuario));
        when(assembler.toModel(any())).thenReturn(EntityModel.of(usuario));

        mvc.perform(get("/usuario").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.usuarioEntityList[0].id").value(1));
    }

    @Test
    void shouldReturnUsuarioById() throws Exception {
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(assembler.toModel(usuario)).thenReturn(EntityModel.of(usuario));

        mvc.perform(get("/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404IfUsuarioNotFound() throws Exception {
        when(usuarioService.findById(99)).thenThrow(new BadRequestException("No existe"));

        mvc.perform(get("/usuario/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateUsuario() throws Exception {
        when(usuarioService.save(any())).thenReturn(usuario);
        when(assembler.toModel(usuario)).thenReturn(EntityModel.of(usuario));

        mvc.perform(post("/usuario")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/usuario/1"));
    }

    @Test
    void shouldUpdateUsuario() throws Exception {
        when(usuarioService.update(eq(1), any())).thenReturn(usuario);
        when(assembler.toModel(usuario)).thenReturn(EntityModel.of(usuario));

        mvc.perform(put("/usuario/1")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1);

        mvc.perform(delete("/usuario/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService).delete(1);
    }
}