package br.edu.ifpb.padroes.estruturais.relatorios;

public abstract class GeradorRelatorioDecorator implements GeradorRelatorio {
    protected GeradorRelatorio wrapper;

    public GeradorRelatorioDecorator(GeradorRelatorio wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String gerar(String dadosBrutos) {
        return wrapper.gerar(dadosBrutos);
    }
}