package com.ecomarket.usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nombre_usuario", nullable= false)
    private String nombreCompleto;

    @Column(name="run_usuario", nullable= false, unique = true, length = 13)
    private String run;

    @Column(name="correo_usuario", unique = true, nullable = false)
    private String correo;

    @Column (name= "direccion_usuario", length = 500)
    private String direccion;

    @Column(name="telefono_usuario", length = 30)
    private String telefono;

    @Column(name="giro")
    private String giro;


}
