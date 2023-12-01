package disparo_recrutamento_e_selecao;

import javax.swing.JPanel;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ExtraiMensagensENumerosDeUmArquivo {
    private String mensagem;
    private List<String> numerosParaFormatar;
    private static File caminhoArquivo;

    public ExtraiMensagensENumerosDeUmArquivo() {
    }

    public String getMensagem(ExtraiMensagensENumerosDeUmArquivo selecionaArquivo) {
        return mensagem;
    }

    public List<String> getNumerosParaFormatar() {
        return numerosParaFormatar;
    }

    public static ExtraiMensagensENumerosDeUmArquivo selecionarArquivo(JPanel contentPane) throws IOException {
        ExtraiMensagensENumerosDeUmArquivo selecionaArquivo = new ExtraiMensagensENumerosDeUmArquivo();
        try {
            JFileChooser fileChooser = new JFileChooser();
            String userHome = System.getProperty("user.home");
            String desktopPath = userHome + File.separator + "Desktop";
            File desktopDirectory = new File(desktopPath);
            fileChooser.setCurrentDirectory(desktopDirectory);
            
            int returnValue = fileChooser.showOpenDialog(contentPane);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (selectedFile.toString().endsWith(".txt")){
                	caminhoArquivo = selectedFile;
                }
                String fileContent = lerConteudoArquivo(selectedFile);
                ExtraiMensagensENumeros extrator = new ExtraiMensagensENumeros();
                ExtraiMensagensENumeros conteudoExtraido = extrator.extrairMensagem(fileContent);
                selecionaArquivo.mensagem = conteudoExtraido.getMsgResult();
                selecionaArquivo.numerosParaFormatar = conteudoExtraido.getNumerosParaFormatar();
            }
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
        return selecionaArquivo;
    }
    
    public static File getCaminhoArquivo() {
    	return caminhoArquivo;
    }

    private static String lerConteudoArquivo(File file) throws IOException {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }
        return fileContent.toString();
    }
}