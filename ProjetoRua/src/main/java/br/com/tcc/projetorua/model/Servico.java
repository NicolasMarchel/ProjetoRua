package br.com.tcc.projetorua.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Servicos")
public class Servico implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@OneToOne
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;
	@OneToOne
	@JoinColumn(name = "recorrencia_id", referencedColumnName = "id")
	private Recorrencia recorrencia;
	@ManyToOne
	@JoinColumn(name = "prestador_id", referencedColumnName = "id")
	private Prestador prestador;
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private Categoria categoria;

	@ManyToMany
	@JoinTable(name = "Utiliza", joinColumns = {
			@JoinColumn(name = "servico_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "beneficiario_id", referencedColumnName = "id") })
	private List<Beneficiario> beneficiarios;

	public Servico(String descricao, Endereco endereco, Categoria categoria, Recorrencia recorrencia) {

		this.descricao = descricao;
		this.endereco = endereco;
		this.categoria = categoria;
		this.recorrencia = recorrencia;
	}

	public Servico() {

	}

	public String getDescricao() {
		return descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Recorrencia getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(Recorrencia recorrencia) {
		this.recorrencia = recorrencia;
	}

	public Prestador getPrestador() {
		return prestador;
	}

	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}

	
}
