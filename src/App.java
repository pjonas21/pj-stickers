import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // acessar url da plataforma de filmes
        //String url = "https://api.mocki.io/v2/549a5d8b/Top250Movies";
        //String url = "https://alura-imdb-api.herokuapp.com/movies"; // desafio 1 - alterar a url da API
        String url = "https://api.mocki.io/v2/549a5d8b/Top250TVs";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair os dados de titulo fime, imagem, classificação
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        System.out.println(listaDeFilmes.size());

        // bloco que chama a classe para gerar as figurinhas
        var gerador = new generateSticker();
        
        // exibir e manipular os dados recebidos da url
        for (Map<String,String> filme : listaDeFilmes) {

            String[] urlImageArr = filme.get("image").split("@._");
            String urlImagem = urlImageArr.length > 1 ? urlImageArr[0] + "@.jpg" : urlImageArr[0];

            //String urlImagem = filme.get("image").replaceAll("\\._V1_[a-zA-Z]+[0-9]+_CR([0-9]+(,[0-9]+)+)_AL_", "");
            String tituloFilme = filme.get("title").replaceAll("\\p{Punct}", "");
            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = tituloFilme + ".png";

            // bloco que gera as figurinhas
            gerador.createImage(inputStream, nomeArquivo);
            
            // linha que mostra qual filme será gerada a imagem
            System.out.println("\u001b[1mT\u00EDtulo: " + tituloFilme + "\u001b[m");

            // bloco de código para exibir os dados no terminal com formatação

            StringBuilder estrela = new StringBuilder("\u001b[1m");
            int somaEstrelas;

            for (somaEstrelas = 0; somaEstrelas < Double.valueOf(filme.get("imDbRating")).intValue();somaEstrelas++){
                estrela.append("\u2B50");
            }

            System.out.println("\u001b[1mPoster: \u001b[m" + filme.get("image"));
            System.out.println("\u001b[1mClassificação: " + filme.get("imDbRating") + "\u001b[m");
            System.out.println(estrela + "\u001b[m");
        }
    }
}
