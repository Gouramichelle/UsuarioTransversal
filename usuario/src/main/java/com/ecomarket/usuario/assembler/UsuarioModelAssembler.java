package com.ecomarket.usuario.assembler;

import com.ecomarket.usuario.controller.UsuarioController;
import com.ecomarket.usuario.model.UsuarioEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioModelAssembler
        implements RepresentationModelAssembler<UsuarioEntity, EntityModel<UsuarioEntity>> {

    @Override
    public EntityModel<UsuarioEntity> toModel(UsuarioEntity usuario) {
        return EntityModel.of(
                usuario,
                linkTo(methodOn(UsuarioController.class)
                        .getUsuarioById(usuario.getId())).withSelfRel(),
                linkTo(methodOn(UsuarioController.class)
                        .getAllUsuarios()).withRel("usuarios")
        );
    }
}
