package disparo_recrutamento_e_selecao;

public class FormatadorDeNumeros {
	
	public static String formatadorDeNumeros(String numero) {
		numero = numero.replace("+", "");
		numero = numero.replace(" ", "");
		numero = numero.replace("-", "");
    	if (numero.length() == 0) {
    		return "Nenhum número foi digitado";
		}
		
		if (numero.length() < 10 && numero.length() != 0) {
			return "Número pequeno de mais, por favor, reformule-o.";
		}
		
		if (numero.length() >= 10) {
			return numberFormatter(numero);
		}
		
		return "a";
    }
	
	private static String numberFormatter(String numero) {
		switch(numero.length()){
		case 10:
			if (numero.contains("55")) {
				return "Número incorreto";
			} else {
				String numbers = "";
				char[] digitos = numero.toCharArray();
				for (int i = 2; i < digitos.length; i++) {
					numbers = numbers + digitos[i];
				}
				numero = "55" + digitos[0] + digitos[1] + "9" + numbers;
				return numero;
			}
		case 11:
			if (numero.contains("55")) {
				return "Número incorreto";
			} else {
				numero = "55" + numero;
				return numero;
			}
		case 12:
			char[] digitos = numero.toCharArray();
			if (numero.startsWith("55")) {
				if (!numero.startsWith("5511")) {
					return numero;
				}
			}
			String numbers = "";
			for (int i = 4; i < digitos.length; i++) {
				numbers = numbers + digitos[i];
			}
			numbers = "9" + numbers;
			numbers = digitos[2] + "" + digitos[3] + numbers;
			numero = digitos[0] + "" + digitos[1] + numbers;
			return numero;
		case 13:
			return numero;
		default:
			return "Número incorreto, por favor reformule-o.";
		}
	}
}
