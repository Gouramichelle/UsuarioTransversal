package com.ecomarket.usuario.service;

import com.ecomarket.usuario.exception.BadRequestException;
import com.ecomarket.usuario.model.UsuarioEntity;
import com.ecomarket.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioEntity> findAllUsuario()
    {return usuarioRepository.findAll();}

    public UsuarioEntity findById(Integer id)
    {return usuarioRepository.findById(id)
            .orElseThrow(() -> new BadRequestException("No existe producto con el ID: " + id));}


    public UsuarioEntity save(UsuarioEntity usuario)
    {return usuarioRepository.save(usuario);}

    public void delete(Integer id){usuarioRepository.deleteById(id);}

    public UsuarioEntity update(Integer id, UsuarioEntity usuario){
        Optional<UsuarioEntity> existente= usuarioRepository.findById(id);
        if (existente.isPresent()){
            UsuarioEntity usuarioactualizado = existente.get();
            usuarioactualizado.setNombreCompleto(usuario.getNombreCompleto());
            usuarioactualizado.setRun(usuario.getRun());
            usuarioactualizado.setCorreo(usuario.getCorreo());
            usuarioactualizado.setDireccion(usuario.getDireccion());
            usuarioactualizado.setTelefono(usuario.getTelefono());
            usuarioactualizado.setGiro(usuario.getGiro());

            return usuarioRepository.save(usuarioactualizado);
        }
        else {
            throw new BadRequestException("Usuario no encontrado");}

    }
}
