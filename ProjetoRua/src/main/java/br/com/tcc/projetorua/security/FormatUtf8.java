package br.com.tcc.projetorua.security;

import java.io.UnsupportedEncodingException;

public class FormatUtf8 {

	
	public String getUtf8(String texto) {

		String Utf8 = "";

		try {

			if (texto != null) {
				Utf8 = new String(texto.getBytes("ISO-8859-1"), "UTF-8");
			}

		} catch (UnsupportedEncodingException e) {

			
			Utf8 = "";
		} catch (Exception e) {
			Utf8 = "";
		}

		return Utf8;
	}
	
	
}
