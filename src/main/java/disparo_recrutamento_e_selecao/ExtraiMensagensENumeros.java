package disparo_recrutamento_e_selecao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraiMensagensENumeros {

    private String msgResult = "";
    private List<String> nuns = new ArrayList<>();

    public ExtraiMensagensENumeros() {
    }
    
    public String getMsgResult() {
        return msgResult;
    }

    public List<String> getNumerosParaFormatar() {
        return nuns;
    }

    public ExtraiMensagensENumeros extrairMensagem(String fileContent) {
        Pattern pattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(fileContent);
        ExtraiMensagensENumeros extrairMensagensENumeros = new ExtraiMensagensENumeros();

        int lastIndex = -1;
        boolean foundMatch = false;

        while (matcher.find()) {
        	extrairMensagensENumeros.msgResult = matcher.group(1);
            extrairMensagensENumeros.msgResult = extrairMensagensENumeros.msgResult.replace("\n", "\\n");
            lastIndex = matcher.end();
            foundMatch = true;
        }

        if (foundMatch && lastIndex != -1) {
            String restOfText = fileContent.substring(lastIndex);
            String[] lines = restOfText.split("\n");
            for (String restLine : lines) {
                extrairMensagensENumeros.nuns.add(restLine);
            }
        }
        return extrairMensagensENumeros;
    }
}