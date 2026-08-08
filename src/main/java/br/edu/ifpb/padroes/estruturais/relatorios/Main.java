package br.edu.ifpb.padroes.estruturais.relatorios;

public class Main {
    public static void main(String[] args) {

        Usuario admin = new Usuario("Diogo", true, "ADMIN");
        Usuario visitante = new Usuario("Emmanuel", true, "VISITANTE");
        Usuario naoLogado = new Usuario("Carla", false, "OPERADOR");

        System.out.println("--- Cenário 01: admin, com cache e log ---");
        // Montando o fluxo: Real <- Log <- Cache <- Proxy
            // Dessa forma, o fluxo atua como um "filtro":
            // Primeiro o Proxy barra usuários inválidos
            // e antes do Log ser acionado a chamada passa pelo Cache
        GeradorRelatorio basePdf1 = new GeradorRelatorioPDF();
        GeradorRelatorio logPdf1 = new LogDecorator(basePdf1, admin.getNome(), "PDF");
        GeradorRelatorio cachePdf1 = new CacheDecorator(logPdf1, "PDF", admin.getNome());
        GeradorRelatorio pipelineCenario1 = new ProxySeguranca(cachePdf1, admin);
        System.out.println(pipelineCenario1.gerar("vendas-julho"));

        System.out.println("\n--- Cenário 02: mesma consulta (deve vir do cache) ---");
        // Reutilizando a pipeline montada no Cenário 1
        System.out.println(pipelineCenario1.gerar("vendas-julho"));

        System.out.println("\n--- Cenário 03: admin, sem cache, com log, outro tipo | Sem usar o CacheDecorator ---");
        // Sem usar o CacheDecorator!
        GeradorRelatorio baseExcel = new GeradorRelatorioExcel();
        GeradorRelatorio logExcel = new LogDecorator(baseExcel, admin.getNome(), "EXCEL");
        GeradorRelatorio pipelineCenario3 = new ProxySeguranca(logExcel, admin);
        System.out.println(pipelineCenario3.gerar("vendas-agosto"));

        System.out.println("\n--- Cenário 04: visitante (sem permissão) | Sem usar LogDecorator ---");
        // Sem usar o LogDecorator!
        GeradorRelatorio basePdf2 = new GeradorRelatorioPDF();
        GeradorRelatorio cachePdf2 = new CacheDecorator(basePdf2, "PDF", visitante.getNome());
        GeradorRelatorio pipelineCenario4 = new ProxySeguranca(cachePdf2, visitante);
        System.out.println(pipelineCenario4.gerar("vendas-julho"));

        System.out.println("\n--- Cenário 05: usuário nao autenticado | Proxy barrando logo no início ---");
        // Tentativa completa, mas o Proxy deve barrar logo de cara
        GeradorRelatorio basePdf3 = new GeradorRelatorioPDF();
        GeradorRelatorio logPdf3 = new LogDecorator(basePdf3, naoLogado.getNome(), "PDF");
        GeradorRelatorio cachePdf3 = new CacheDecorator(logPdf3, "PDF", naoLogado.getNome());
        GeradorRelatorio pipelineCenario5 = new ProxySeguranca(cachePdf3, naoLogado);
        System.out.println(pipelineCenario5.gerar("vendas-julho"));
    }
}