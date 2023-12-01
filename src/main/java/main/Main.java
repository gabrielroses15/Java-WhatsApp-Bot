package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import disparo_recrutamento_e_selecao.Janela;

public class Main {
	
	private static void EncerrarOutrasAplicacoesAbertas(String nomeExecutavel) {
	    try {
	        String comando = "tasklist /FO CSV /NH";
	        Process process = Runtime.getRuntime().exec(comando);
	        InputStream inputStream = process.getInputStream();
	        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	        BufferedReader reader = new BufferedReader(inputStreamReader);

	        String linha;
	        while ((linha = reader.readLine()) != null) {
	            if (linha.contains(nomeExecutavel)) {
	            	String[] caracteres = linha.split(",");
	            	for (int i = 0; i < caracteres.length ; i++) {
	            	    caracteres[i] = caracteres[i].replace("\"", "");
	            	}
	            	String peso = String.join(", ", caracteres).split(",")[caracteres.length-1].replace(" ", "").replace("K", "");
	            	if (peso.replaceAll("[^.]", "").length() < 2) {
	            		if (Integer.parseInt(peso.replace(".", "")) > 40000) {
	            			Runtime.getRuntime().exec("taskkill /F /PID " + String.join(", ", caracteres).split(",")[1].replace(" ", ""));
	            		}
	            	}
	            }
	        }
	        reader.close();
	        inputStreamReader.close();
	        inputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) {
		EncerrarOutrasAplicacoesAbertas("javaw.exe");
		try {
			Janela janela = new Janela();
			janela.setVisible(true);
		} catch (Exception e) {
			driver.webDriver.closedriver();
			e.printStackTrace();
		}
	}
}
