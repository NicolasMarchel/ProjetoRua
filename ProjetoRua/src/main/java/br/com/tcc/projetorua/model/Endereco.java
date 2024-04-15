package br.com.tcc.projetorua.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Enderecos")
public class Endereco implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cep;
	private String cidade;
	private String bairro;
	private String uf;
	@Column(name = "logradouro", columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
	private String logradouro;
	private String numero;
	@OneToOne(mappedBy = "endereco")
	private Servico servico;
	@OneToOne(mappedBy = "endereco")
	private Prestador prestador;
	
	@OneToOne(mappedBy = "endereco")
	private Beneficiario beneficiario;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Endereco(String cep, String cidade, String bairro, String estado, String logradouro, String numero) {
		this.cep = cep;
		this.cidade = cidade;
		this.bairro = bairro;
		this.uf = estado;
		this.logradouro = logradouro;
		this.numero = numero;
	}
	public Endereco(String cep, String cidade, String bairro, String estado) {
		this.cep = cep;
		this.cidade = cidade;
		this.bairro = bairro;
		this.uf = estado;
		
	}

	public Endereco() {

	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEstado() {
		return uf;
	}

	public void setEstado(String estado) {
		this.uf = estado;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

}
