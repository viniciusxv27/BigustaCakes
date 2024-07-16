/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dados;

/**
 *
 * @author gueta
 */
public class Produto {
    private int codigo;
    private String nome;
    private String descricao;
    private double precoCusto;
    private double precoVenda;

    public Produto(String nome, String descricao, double precoVenda, double precoCusto) {
        
        this.nome = nome;
        this.descricao = descricao;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
    }

    
    
    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(double precoCusto) {
        this.precoCusto = precoCusto;
    }
    @Override
    public String toString(){
        String registro;
        registro = "Nome: "+this.nome;
        registro += "\nDescrição: "+this.descricao;
        registro += "\nPreço de venda: "+this.precoVenda;
        registro += "\nPreço de custo: "+this.precoCusto;
        return registro;
        
    }

    public int getCodigo() {
        return codigo;
    }
    
    
    
}
