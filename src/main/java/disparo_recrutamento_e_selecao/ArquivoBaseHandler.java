package disparo_recrutamento_e_selecao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class ArquivoBaseHandler {
    private static final File arquivoBase = ExtraiMensagensENumerosDeUmArquivo.getCaminhoArquivo();

    public static void removeNumeroDoArquivo(String numeroParaFormatar) {
        List<String> linhas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoBase))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().equals(numeroParaFormatar)) {
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        salvarArquivoAtualizado(linhas);
    }

    private static void salvarArquivoAtualizado(List<String> linhas) {
        try {
            File arquivoTemporario = File.createTempFile("temp", null);
            arquivoTemporario.deleteOnExit();

            try (FileWriter writer = new FileWriter(arquivoTemporario)) {
                for (String linha : linhas) {
                    writer.write(linha + System.lineSeparator());
                }
            }
            Files.move(arquivoTemporario.toPath(), arquivoBase.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
