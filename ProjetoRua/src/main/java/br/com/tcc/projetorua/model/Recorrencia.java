package br.com.tcc.projetorua.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Recorrencias")
public class Recorrencia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dataInicio;
	private LocalDate dataFinal;
	private String frequencia;
	private LocalTime horarioInicio;
	private LocalTime horarioTermino;
	private Boolean segunda;
	private Boolean terca;
	private Boolean quarta;
	private Boolean quinta;
	private Boolean sexta;
	private Boolean sabado;
	private Boolean domingo;
	
	
	public Boolean getSegunda() {
		return segunda;
	}

	public void setSegunda(Boolean segunda) {
		this.segunda = segunda;
	}

	public Boolean getTerca() {
		return terca;
	}

	public void setTerca(Boolean terca) {
		this.terca = terca;
	}

	public Boolean getQuarta() {
		return quarta;
	}

	public void setQuarta(Boolean quarta) {
		this.quarta = quarta;
	}

	public Boolean getQuinta() {
		return quinta;
	}

	public void setQuinta(Boolean quinta) {
		this.quinta = quinta;
	}

	public Boolean getSexta() {
		return sexta;
	}

	public void setSexta(Boolean sexta) {
		this.sexta = sexta;
	}

	public Boolean getSabado() {
		return sabado;
	}

	public void setSabado(Boolean sabado) {
		this.sabado = sabado;
	}

	public Boolean getDomingo() {
		return domingo;
	}

	public void setDomingo(Boolean domingo) {
		this.domingo = domingo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(mappedBy = "recorrencia")
	private Servico servicoDeAssistencia;

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public LocalTime getHorarioInicio() {
		return horarioInicio;
	}

	public void setHorarioInicio(LocalTime horarioInicio) {
		this.horarioInicio = horarioInicio;
	}

	public LocalTime getHorarioTermino() {
		return horarioTermino;
	}

	public void setHorarioTermino(LocalTime horarioTermino) {
		this.horarioTermino = horarioTermino;
	}

	public Recorrencia(LocalDate dataInicio, LocalDate dataFinal, String frequencia, LocalTime horarioInicio,
			LocalTime horarioTermino) {

		this.dataInicio = dataInicio;
		this.dataFinal = dataFinal;
		this.frequencia = frequencia;
		this.horarioInicio = horarioInicio;
		this.horarioTermino = horarioTermino;
	}

	public Recorrencia() {

	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public Date getDateInicio() {
		
		 Date date = Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
		 
			return date;
		}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public Date getDateFinal() {
		
		 Date date = Date.from(dataFinal.atStartOfDay(ZoneId.systemDefault()).toInstant());
		 
			return date;
		}
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	

}
