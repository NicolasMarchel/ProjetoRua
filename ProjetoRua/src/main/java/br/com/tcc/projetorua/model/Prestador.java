package br.com.tcc.projetorua.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Prestadores")
public class Prestador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String nomeInstituicao;
	private String descricao;
	protected String hashSenha;
	protected Long cnpj;
	@OneToMany(mappedBy = "prestador")
	private List<Servico> servicos;

	@OneToOne
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;

	public List<Servico> getServicosDeAssistencia() {
		return servicos;
	}

	public void setServicosDeAssistencia(List<Servico> servicosDeAssistencia) {
		this.servicos = servicosDeAssistencia;
	}

	public Prestador(String nomeInstituicao, String descricao, String hashSenha, Long cnpj) {

		this.nomeInstituicao = nomeInstituicao;
		this.descricao = descricao;
		this.hashSenha = hashSenha;
		this.cnpj = cnpj;
		
	}

	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Prestador() {

	}

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nomeInstituicao;
	}

	public void setNome(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
