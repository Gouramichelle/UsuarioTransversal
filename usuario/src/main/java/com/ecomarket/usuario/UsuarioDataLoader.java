package com.ecomarket.usuario;

import com.ecomarket.usuario.model.UsuarioEntity;
import com.ecomarket.usuario.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
import java.util.stream.IntStream;

@Configuration
public class UsuarioDataLoader {

    @Bean
    public CommandLineRunner loadUsuarios(UsuarioRepository usuarioRepository) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Faker faker = new Faker(new Locale("es"));

                IntStream.range(0, 20).forEach(i -> {
                    UsuarioEntity usuario = new UsuarioEntity();
                    usuario.setNombreCompleto(faker.name().fullName());
                    usuario.setRun("1" + faker.number().digits(7) + "-" + faker.number().digit()); // Simulado
                    usuario.setCorreo(faker.internet().emailAddress());
                    usuario.setDireccion(faker.address().fullAddress());
                    usuario.setTelefono("+56 9 " + faker.number().digits(8));
                    usuario.setGiro(faker.company().industry());

                    usuarioRepository.save(usuario);
                });

                System.out.println("Usuarios generados con DataFaker");
            }
        };
    }
}