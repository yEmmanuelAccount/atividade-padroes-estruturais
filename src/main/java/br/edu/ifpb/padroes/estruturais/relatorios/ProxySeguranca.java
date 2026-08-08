package br.edu.ifpb.padroes.estruturais.relatorios;

public class ProxySeguranca implements GeradorRelatorio {
    private GeradorRelatorio geradorReal;
    private Usuario usuario;

    public ProxySeguranca(GeradorRelatorio geradorReal, Usuario usuario) {
        this.geradorReal = geradorReal;
        this.usuario = usuario;
    }

    @Override
    public String gerar(String dadosBrutos) {
        if (!usuario.isAutenticado()) {
            System.out.println("[ERRO] Usuário não autenticado tentou gerar relatorio.");
            return null;
        }
        if (!usuario.getPapel().equals("ADMIN") && !usuario.getPapel().equals("OPERADOR")) {
            System.out.println("[ERRO] Usuário sem permissão para gerar relatórios: " + usuario.getNome());
            return null;
        }

        // Se passou nas validações, delega para o objeto real (ou próximo decorator)
        return geradorReal.gerar(dadosBrutos);
    }
}