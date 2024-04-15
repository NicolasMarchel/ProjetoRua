package br.com.tcc.projetorua.model;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;

@Entity
@Table(name = "Avatares")
public class Avatar implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private byte[] foto;

	/////////////////////////////////////////
	@OneToMany(mappedBy = "avatar")
	private List<Beneficiario> beneficiarios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	/////////////////////////////////////////
	public Avatar() {

	}

	public String getFoto() {
		try {
			String fotoBase64 = Base64.getEncoder().encodeToString(foto);
			return fotoBase64;
		} catch (Exception e) {
			return null;
		}
	}

	public String getExtensao() {
		try {
			ImageInfo imageInfo = Imaging.getImageInfo(foto);
			if (imageInfo != null && imageInfo.getFormat() != null) {
				return imageInfo.getFormat().getName();
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
}
