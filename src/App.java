import java.net.URI;
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
        String url = "https://alura-imdb-api.herokuapp.com/movies"; // desafio 1 - alterar a url da API
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair os dados de titulo fime, imagem, classificação
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir os dados recebidos da url
        for (Map<String,String> filme : listaDeFilmes) {
            StringBuilder estrela = new StringBuilder("\u001b[97;1m\u001b[44;5;88m");
            int somaEstrelas;

            for (somaEstrelas = 0; somaEstrelas < Double.valueOf(filme.get("imDbRating")).intValue();somaEstrelas++){
                estrela.append("\u2B50");
            }

            System.out.println("\u001b[1mT\u00EDtulo: " + filme.get("title") + "\u001b[m");
            System.out.println("\u001b[1mPoster: \u001b[m" + filme.get("image"));
            System.out.println("\u001b[1mClassificação: " + filme.get("imDbRating") + "\u001b[m");
            System.out.println(estrela + "\u001b[m");
        }
    }
}
