package br.edu.ifpb.padroes.estruturais.relatorios;

public class LogDecorator extends GeradorRelatorioDecorator {
    private String nomeUsuario;
    private String tipo;

    public LogDecorator(GeradorRelatorio wrapper, String nomeUsuario, String tipo) {
        super(wrapper);
        this.nomeUsuario = nomeUsuario;
        this.tipo = tipo;
    }

    @Override
    public String gerar(String dadosBrutos) {
        System.out.println("[LOG] Usuário " + nomeUsuario + " solicitou relatorio tipo " + tipo);

        String resultado = super.gerar(dadosBrutos);

        if (resultado != null) {
            System.out.println("[LOG] Relatório gerado com sucesso para " + nomeUsuario);
        }
        return resultado;
    }
}