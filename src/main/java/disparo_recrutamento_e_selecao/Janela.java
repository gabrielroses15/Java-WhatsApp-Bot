package disparo_recrutamento_e_selecao;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import driver.webDriver;
import driver.webDriver.ScriptExecutionListener;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Janela extends JFrame implements ScriptExecutionListener {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static boolean iniciado = false;
	private boolean jaIniciadoUmaVez = false;

	public String mensagem;
	public List<String> numerosParaFormatar;
	private JButton btnIniciar;
	private JButton btnMudaBase;

	public Janela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 774, 257);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnIniciar = new JButton("Escolha a base");
		btnIniciar.setBounds(274, 163, 217, 23);
		btnIniciar.setVisible(false);

		btnIniciar.addActionListener(e -> {
			if (iniciado == false) {
				iniciado = true;
				btnIniciar.setText("Iniciando...");
				if (mensagem == null) {
					iniciado = false;
					JOptionPane.showMessageDialog(null, "Por favor, certifique-se de selecionar corretamente a base.",
							"Base não selecionada ou não reconhecida.", JOptionPane.ERROR_MESSAGE);
					btnIniciar.setText("Escolha a base");
					btnMudaBase.setVisible(true);
				} else if (mensagem == "") {
					iniciado = false;
					mensagem = null;
					JOptionPane.showMessageDialog(null,
							"A mensagem não foi reconhecida, por favor, certifique-se de formatar a base corretamente.",
							"Mensagem não reconhecida.", JOptionPane.ERROR_MESSAGE);
					btnIniciar.setText("Escolha a base");
					btnMudaBase.setVisible(true);
				} else {
					btnIniciar.setText("Enviando");
					webDriver.startWebDriver(numerosParaFormatar, mensagem);
					btnIniciar.setText("Enviando");
					iniciado = false;
					if (jaIniciadoUmaVez) {
						btnIniciar.setText("Enviar outra base");
					}
					jaIniciadoUmaVez = true;
				}
			}
		});

		contentPane.add(btnIniciar);

		btnMudaBase = new JButton("Escolher base");
		btnMudaBase.setBounds(274, 129, 217, 23);

		btnMudaBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (iniciado == false) {
					try {
						new ExtraiMensagensENumerosDeUmArquivo();
						ExtraiMensagensENumerosDeUmArquivo mensagemNumeros = ExtraiMensagensENumerosDeUmArquivo
								.selecionarArquivo(contentPane);
						mensagem = mensagemNumeros.getMensagem(mensagemNumeros);
						numerosParaFormatar = mensagemNumeros.getNumerosParaFormatar();
						if (mensagem != null && mensagem != "") {
							btnIniciar.setText("Iniciar");
							if (jaIniciadoUmaVez) {
								btnIniciar.setText("Enviar outra base");
							}
							btnMudaBase.setText("Mudar Base");
							btnIniciar.setVisible(true);
							btnMudaBase.setVisible(false);
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Por favor aguarde a base ser enviada.",
							"Aguarde a base anterior ser completamente enviada.", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		contentPane.add(btnMudaBase);
		webDriver.setScriptExecutionListener(this);
	}

	public void onScriptExecuted(String numeroFormatado) {
	    ArquivoBaseHandler.removeNumeroDoArquivo(numeroFormatado);
	}
	
	public void onScriptCompleted() {
		btnMudaBase.setVisible(true);
        btnIniciar.setVisible(false);
        btnIniciar.setText("Escolha uma nova base");
    }
}
