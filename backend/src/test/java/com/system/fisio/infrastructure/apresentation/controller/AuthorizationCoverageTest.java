package com.system.fisio.infrastructure.apresentation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Garante que nenhum endpoint novo fique acidentalmente sem @PreAuthorize.
 *
 * Antes do RBAC, só 2 dos ~20 endpoints do sistema tinham qualquer checagem de
 * permissão (o resto ficava aberto para qualquer usuário autenticado por simples
 * omissão). Este teste falha o build se isso voltar a acontecer silenciosamente.
 * Endpoints intencionalmente públicos (login) ficam na allowlist abaixo.
 */
class AuthorizationCoverageTest {

    private static final Set<String> ALLOWLIST_SEM_PREAUTHORIZE = Set.of(
            "com.system.fisio.infrastructure.apresentation.controller.AuthController"
    );

    private static final List<Class<? extends Annotation>> MAPEAMENTOS_HTTP = List.of(
            GetMapping.class, PostMapping.class, PutMapping.class, PatchMapping.class, DeleteMapping.class, RequestMapping.class
    );

    @Test
    void todoEndpointDeveTerPreAuthorizeOuEstarNaAllowlist() throws Exception {
        List<String> violacoes = new ArrayList<>();

        for (Class<?> controller : listarControllers()) {
            if (ALLOWLIST_SEM_PREAUTHORIZE.contains(controller.getName())) {
                continue;
            }
            for (Method method : controller.getDeclaredMethods()) {
                boolean isEndpoint = MAPEAMENTOS_HTTP.stream().anyMatch(method::isAnnotationPresent);
                if (!isEndpoint) {
                    continue;
                }
                if (!method.isAnnotationPresent(PreAuthorize.class)) {
                    violacoes.add(controller.getSimpleName() + "#" + method.getName());
                }
            }
        }

        assertTrue(violacoes.isEmpty(),
                "Endpoints sem @PreAuthorize (adicione a anotação ou inclua na allowlist se for intencionalmente público): "
                        + violacoes);
    }

    private List<Class<?>> listarControllers() throws Exception {
        String packageName = "com.system.fisio.infrastructure.apresentation.controller";
        String path = packageName.replace('.', '/');
        List<Class<?>> classes = new ArrayList<>();

        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
        while (resources.hasMoreElements()) {
            File directory = new File(resources.nextElement().getFile());
            if (!directory.isDirectory()) {
                continue;
            }
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
            if (files == null) {
                continue;
            }
            for (File file : files) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
