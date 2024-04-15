package br.com.tcc.projetorua.model;

import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class Localizacao implements Comparable<Localizacao> {

	private static final String API_KEY = "AIzaSyD-6KD2ZuOEVkPSzGTeodWcbQX994UMB3M";
	private static final String API_URL = "https://maps.googleapis.com/maps/api/distancematrix/xml";
	private String origem;
	private String destino;
	private double distancia;
	private int minutos;
	private String googleMapsURL;
	private Servico servico;
	
	
	
	
	public void setServico(Servico servico) {
		this.servico = servico;
	}


	public Servico getServico() {
        return this.servico;
    }
	@Override
    public int compareTo(Localizacao outraLocalizacao) {
        return Double.compare(this.distancia, outraLocalizacao.getDistancia());
    }

	public void setOrigem(String origemXml) {
		this.origem = origemXml;

	}

	public void setDestino(String destinoXml) {
		this.destino = destinoXml;

	}

	public void setDistancia(double distanciaKm) {
		this.distancia = distanciaKm;

	}

	public void setMinutos(int duracaoMinutos) {
		this.minutos = duracaoMinutos;

	}

	public int getMinutos() {
		return minutos;
	}

	public String getOrigem() {
		return origem;
	}

	public String getDestino() {
		return destino;
	}

	public double getDistancia() {
		return distancia;
	}

	public static Localizacao obterLocalizacao(String origem, String destino, int distanciaMaxima) {

		try {

			String url = API_URL + "?origins=" + origem + "&destinations=" + destino + "&mode=driving"
					+ "&language=pt-BR" + "&sensor=false" + "&key=" + API_KEY;

			URL requestUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() == 200) {
				InputStream responseStream = connection.getInputStream();
				String responseString = IOUtils.toString(responseStream, "UTF-8");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new InputSource(new StringReader(responseString)));
				XPath xpath = XPathFactory.newInstance().newXPath();
				String origemXml = new String(xpath.evaluate("//origin_address", document));
				String destinoXml = new String(xpath.evaluate("//destination_address", document));
				String distanciaMetros = xpath.evaluate("//distance/value", document);
				double distanciaKm = Double.parseDouble(distanciaMetros) / 1000.0;
				int duracaoMinutos = Integer.parseInt(xpath.evaluate("//duration/value", document)) / 60;

				String googleMapsURL = "https://maps.google.com/maps?saddr=" + origem + "&daddr=" + destino
						+ "&output=embed";

				Localizacao localizacao = new Localizacao();
				localizacao.setOrigem(origemXml);
				localizacao.setDestino(destinoXml);
				localizacao.setDistancia(distanciaKm);
				localizacao.setMinutos(duracaoMinutos);
				localizacao.setGoogleMapsURL(googleMapsURL);
				if (distanciaKm <= distanciaMaxima) {
					return localizacao;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setGoogleMapsURL(String url) {
		this.googleMapsURL = url;
	}

	public String getGoogleMapsURL() {
		return googleMapsURL;
	}

	public String obterDescricaoDistancia() {
		String unidadeDistancia = (distancia < 1) ? "m" : "km";
		return String.format("%.2f %s", distancia, unidadeDistancia);
	}
	public String obterDescricaoTempo() {
	    String unidadeTempo = (minutos < 60) ? "minuto" : "hora";
	    int tempo = (minutos < 60) ? 0 : minutos / 60;
	    int minutosRestantes = minutos % 60;

	    String descricaoTempo = "";

	    if (tempo > 0) {
	        descricaoTempo += String.format("%d %s", tempo, unidadeTempo);
	        if (tempo != 1) {
	            descricaoTempo += "s";
	        }
	    }

	    if (minutosRestantes > 0&&tempo> 0) {
	    	String unidadeMinutos = (minutosRestantes != 1) ? "s" : "";
	        descricaoTempo += String.format(" e %d minuto%s", minutosRestantes, unidadeMinutos);
	    	
	    }
	    else if(minutosRestantes > 0&&tempo==0) {
	    	
	    	String unidadeMinutos = (minutosRestantes != 1) ? "s" : "";
	        descricaoTempo += String.format(" %d minuto%s", minutosRestantes, unidadeMinutos);
	    }
	    
	    
	   // if (minutosRestantes > 0) {
	  //      String unidadeMinutos = (minutosRestantes != 1) ? "s" : "";
	  //      descricaoTempo += String.format(" %d minuto%s", minutosRestantes, unidadeMinutos);
	  //  }

	    return descricaoTempo.trim(); // Remova espaços em branco adicionais, se houver
	}

}
