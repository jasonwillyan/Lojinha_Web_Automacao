package modulos.produtos;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import paginas.LoginPage;

import java.time.Duration;

@DisplayName("Testes web do módulo de produtos")
public class ProdutosTest
{

    private WebDriver navegador;

    @BeforeEach
    public void beforeEach()
    {
        //Abrir o navegador
        System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver94\\chromedriver.exe");
        this.navegador = new ChromeDriver();

        //Maximizar a tela
        this.navegador.manage().window().maximize();

        //Vou definir um tempo de espera padrão de 5 secundos
        this.navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        //Navegar até a página www.exemplo.com
        this.navegador.get("http://165.227.93.41/lojinha-web/v2/");
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor igual a zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualAZero()
    {
        String menssagemApresentada = new LoginPage(navegador)
                .informarUsuario("admin")
                .informarASenha("admin")
                .submeterFomularioDeLogin()
                .acessarFormularioDeAdicaoDeNovoProduto()
                .informarNomeDoProduto("Iphone X")
                .informarValorProduto("000")
                .informarCoresDoProduto("Preto")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", menssagemApresentada);
    }

    @Test
    @DisplayName("Não é permitido registrar um produto com valor acima de 7000.00")
    public void testNaoEPermitidoRegistrarProdutoComValorAcimaDeSeteMil()
    {
        String menssagemApresentada = new LoginPage(navegador)
                .informarUsuario("admin")
                .informarASenha("admin")
                .submeterFomularioDeLogin()
                .acessarFormularioDeAdicaoDeNovoProduto()
                .informarNomeDoProduto("Macbook Pro")
                .informarValorProduto("700001")
                .informarCoresDoProduto("Preto, Branco")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", menssagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produto que estejam no limite de R$ 0.01")
    public void testEPermitidoRegistrarProdutoComValorUmCentavo()
    {
        String menssagemApresentada = new LoginPage(navegador)
                .informarUsuario("admin")
                .informarASenha("admin")
                .submeterFomularioDeLogin()
                .acessarFormularioDeAdicaoDeNovoProduto()
                .informarNomeDoProduto("Playstation 5")
                .informarValorProduto("001")
                .informarCoresDoProduto("Preto, Branco")
                .submeterFormularioDeEdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", menssagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produto que estejam no limite de R$ 7000.00")
    public void testEPermitidoRegistrarProdutoComValorSeteMilReais()
    {
        String menssagemApresentada = new LoginPage(navegador)
                .informarUsuario("admin")
                .informarASenha("admin")
                .submeterFomularioDeLogin()
                .acessarFormularioDeAdicaoDeNovoProduto()
                .informarNomeDoProduto("Playstation 4")
                .informarValorProduto("700000")
                .informarCoresDoProduto("Preto, Branco")
                .submeterFormularioDeEdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", menssagemApresentada);
    }

    @AfterEach
    public void afterEach()
    {
        //Vou fechar p navegador
        navegador.quit();
    }
}
