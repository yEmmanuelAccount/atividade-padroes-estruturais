package br.edu.ifpb.padroes.estruturais.relatorios;

import java.util.HashMap;
import java.util.Map;

public class CacheDecorator extends GeradorRelatorioDecorator {
    // Map estático para simular o cache global da aplicação
    private static Map<String, String> cache = new HashMap<>();
    private String tipo;
    private String nomeUsuario;

    public CacheDecorator(GeradorRelatorio wrapper, String tipo, String nomeUsuario) {
        super(wrapper);
        this.tipo = tipo;
        this.nomeUsuario = nomeUsuario;
    }

    @Override
    public String gerar(String dadosBrutos) {
        String chaveCache = tipo + ":" + dadosBrutos;

        if (cache.containsKey(chaveCache)) {
            System.out.println("[LOG] Retornando resultado do cache para " + nomeUsuario);
            return cache.get(chaveCache);
        }

        // Se não está no cache, manda gerar
        String resultado = super.gerar(dadosBrutos);

        // Guarda no cache se a geração foi bem sucedida (ex: não foi barrada pelo proxy)
        if (resultado != null) {
            cache.put(chaveCache, resultado);
        }
        return resultado;
    }
}