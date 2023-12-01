package driver;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import disparo_recrutamento_e_selecao.FormatadorDeNumeros;
import disparo_recrutamento_e_selecao.Janela;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import org.openqa.selenium.JavascriptExecutor;

public class webDriver {
	private static Janela scriptExecutionListener;
	
	public static void setScriptExecutionListener(Janela janela) {
        scriptExecutionListener = janela;
    }
	
	private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String CHROME_DRIVER_PATH = "C:\\chromium\\chromedriver.exe";
    private static WebDriver driver;
    public static int intervalo = 5;
    private static boolean primeiraIniciacao = false;
    private static boolean segundaIniciacao = false;
    
    public static String startWebDriver(List<String> numerosParaFormatar, String msg) {
    	if (primeiraIniciacao) {
    		segundaIniciacao = true;
    	}else {
    		primeiraIniciacao = true;
    	}
    	
    	if (segundaIniciacao == false) {
	    	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
	        driver = new ChromeDriver();
	        String scriptMiniBit = copiaAPI(driver);
	        abreWhatsapp();
	        injetaScript(scriptMiniBit, driver);
	        executorService.execute(() -> {
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        });
    	}
        for (int i = 0; i < numerosParaFormatar.size(); i++) {
        	String numeroParaFormatar = numerosParaFormatar.get(i);
        	String numeroFormatado = FormatadorDeNumeros.formatadorDeNumeros(numeroParaFormatar);
        	if (numeroFormatado.charAt(0) == '5') {
        		injetaScript("YOUR CODE", driver);
        		executorService.execute(() -> {
                    try {
                        Thread.sleep(5000);
                        if (scriptExecutionListener != null) {
                            try {
                                driver.getTitle();
                                scriptExecutionListener.onScriptExecuted(numeroParaFormatar);
                            } catch (Exception f) {
                                JOptionPane.showMessageDialog(null, "Google fechado.",
                                        "O google foi fechado, por favor escolha a base novamente..", JOptionPane.ERROR_MESSAGE);
                            } finally {
                                scriptExecutionListener.onScriptCompleted();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                    	scriptExecutionListener.onScriptCompleted();
                    }
                });
        	}else {
        		if (numeroFormatado == "Nenhum número foi digitado"){
        			scriptExecutionListener.onScriptExecuted(numeroParaFormatar);
        		}
        	}
        }
        if (numerosParaFormatar.size() == 0) {
        	scriptExecutionListener.onScriptCompleted();
        }
        
        return "finalizado";
    }
    
    public static void closedriver() {
	   driver.close();
    }
    
    public static String copiaAPI(WebDriver driver) {
    	try {
    	driver.get("YOUR API");
        WebElement texto = driver.findElement(By.cssSelector("body > pre"));
        String script = texto.getText();
        return script;
    	} catch (Exception e) {
    		return "";
    	}
    }
    
    public interface ScriptExecutionListener {
        void onScriptExecuted(String numeroFormatado);
        void onScriptCompleted();
    }
    
    public static void abreWhatsapp() {
        try {
            int tentativas = 0;
            driver.get("https://web.whatsapp.com/");
            WebDriverWait wait = new WebDriverWait(driver, 60);

            boolean qrCodePresent = true;
            boolean conversas = false;
            while (qrCodePresent) {
                try {
                    WebElement QR = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#app > div > div > div.landing-window > div.landing-main > div > div > div._3AjBo > ol > li:nth-child(2) > strong:nth-child(2) > span")));
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#app > div > div > div.landing-window > div.landing-main > div > div > div._3AjBo > ol > li:nth-child(2) > strong:nth-child(2) > span")));
                    tentativas = 0;
                    qrCodePresent = false;
                } catch (Exception e) {
                    tentativas += 1;
                    if (tentativas == 10) {
                        break;
                    }
                    Thread.sleep(1000);
                }
            }

            while (conversas == false) {
                try {
                    WebElement QR = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#side > div._3gYev > div > button > div")));
                    conversas = true;
                } catch (Exception e) {
                    tentativas += 1;
                    if (tentativas == 10) {
                        break;
                    }
                    Thread.sleep(1000);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void injetaScript(String script, WebDriver driver) {
    	JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
    	jsExecutor.executeScript(script);
    }
    
    public static boolean verificaBrowserAberto() {
    	try {
    		if (driver == null) {
	            return false;
	        }else {
	        	driver.getCurrentUrl();
	        	return true;
	        }
    	} catch (Exception e) {
    		return false;
    	}
    }
}