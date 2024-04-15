package br.com.tcc.projetorua.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Dificuldades")
public class Dificuldade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private int escala;
	@ManyToOne
	@JoinColumn(name = "beneficiario_id", referencedColumnName = "id")
	private Beneficiario beneficiario;
	public Dificuldade(String nome, int escala) {

		this.nome = nome;
		this.escala = escala;
	}

	public Dificuldade() {

	}

	public String getNome() {
		return nome;
	}

	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}

	public boolean validaNome(String nome) {
		
		return this.nome.equals(nome);
		
	}
	
	
	
	
	
}
