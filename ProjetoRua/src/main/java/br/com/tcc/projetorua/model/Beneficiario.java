package br.com.tcc.projetorua.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Beneficiarios")
public class Beneficiario implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String nome;
	private int tempoNasRuas;
	private String etnia;
	
	private String genero;
	private LocalDate dataNascimento;
	protected String hashSenha;
	protected Long cpf;
	//private byte[] foto;
	private String historia;
	private LocalDate dataPublicacao = LocalDate.now();

	///////////////////////////
	@ManyToOne
	@JoinColumn(name = "avatar_id", referencedColumnName = "id")
	private Avatar avatar;

	///////////////////////////////
	
	
	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	@OneToOne
	@JoinColumn(name = "endereco_id", referencedColumnName = "id")
	private Endereco endereco;
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Dificuldade> getDificuldades() {
		return dificuldades;
	}

	public void setDificuldades(List<Dificuldade> dificuldades) {
		this.dificuldades = dificuldades;
	}

	@OneToMany(mappedBy = "beneficiario")
	private List<Dificuldade> dificuldades;
	
	
	@ManyToMany(mappedBy = "beneficiarios")
	private List<Servico> servicos;

	//public String getFoto() {
	//	try {
	//		String fotoBase64 = Base64.getEncoder().encodeToString(foto);
	//		return fotoBase64;
	//	} catch (Exception e) {
	//		return null;
	//	}
	//}

	//public String getExtensao() {
	//	try {
	//		ImageInfo imageInfo = Imaging.getImageInfo(foto);
	//		if (imageInfo != null && imageInfo.getFormat() != null) {
	//			return imageInfo.getFormat().getName();
	//		}
	//	} catch (Exception e) {
	//		return null;
	//	}
	//	return null;
	//}

//	public void setFoto(byte[] foto) {
//		this.foto = foto;
//	}

	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}
	public Date getDatePublicacao() {
		
		 Date date = Date.from(dataPublicacao.atStartOfDay(ZoneId.systemDefault()).toInstant());
		 
			return date;
		}
	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getHistoria() {

		String historiaUtf8 = "";

		try {

			if (historia != null) {
				historiaUtf8 = new String(historia.getBytes("ISO-8859-1"), "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {

			
			historiaUtf8 = "";
		} catch (Exception e) {
			historiaUtf8 = "";
		}

		return historiaUtf8;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}

	public Beneficiario(String nome, Long cpf, String hashSenha) {
		this.nome = nome;
		this.cpf = cpf;
		this.hashSenha = hashSenha;
	}

	public Beneficiario() {

	}

	public String getNome() {
		return nome;
	}

	public String getPrimeiroNome() {
		
		
		if (nome != null && !nome.isEmpty()) {
           
            int indiceEspaco = nome.indexOf(' ');

          
            if (indiceEspaco != -1) {
                return nome.substring(0, indiceEspaco);
            } else {
             
                return nome;
            }
        } else {
           
            return "";
        }
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getHashSenha() {
		return hashSenha;
	}

	public void setHashSenha(String hashSenha) {
		this.hashSenha = hashSenha;
	}
	public int getTempoNasRuas() {
		return tempoNasRuas;
	}

	public void setTempoNasRuas(int tempoNasRuas) {
		this.tempoNasRuas = tempoNasRuas;
	}

	public String getEtnia() {
		return etnia;
	}

	public void setEtnia(String etnia) {
		this.etnia = etnia;
	}

	

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public LocalDate getDataNascimento() {
		
		
		return dataNascimento;
	}
public Date getDateNascimento() {
		
	 Date date = Date.from(dataNascimento.atStartOfDay(ZoneId.systemDefault()).toInstant());
	 
		return date;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	public int getIdade() {
        if (dataNascimento != null) {
            LocalDate dataAtual = LocalDate.now();
            return Period.between(dataNascimento, dataAtual).getYears();
        }
        return 0; 
    }
	
	
	
	

}
