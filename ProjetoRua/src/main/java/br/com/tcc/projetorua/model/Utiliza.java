package br.com.tcc.projetorua.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Utiliza")
public class Utiliza implements Serializable{
	@Id
    @ManyToOne
    @JoinColumn(name = "beneficiario_id")
    private Beneficiario beneficiario;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "servico_id")
   private Servico servico;
	
	private LocalDate dataUtilizacao;

	
	public LocalDate getDataUtilizacao() {
		return dataUtilizacao;
	}

	public void setDataUtilizacao(LocalDate dataUtilizacao) {
		this.dataUtilizacao = dataUtilizacao;
	}

	public Utiliza(LocalDate dataUtilizacao) {

		this.dataUtilizacao = dataUtilizacao;
	}

	public Utiliza() {

	}

}
