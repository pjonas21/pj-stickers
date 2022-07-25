import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.Color;
import java.awt.Font;

import javax.imageio.ImageIO;

public class generateSticker {
    
    void createImage(InputStream inputStream, String nomeArquivo) throws Exception{

        //leitura da imagem
        // FileInputStream input = new FileInputStream(new File("assets/filme.jpg"));
        //InputStream input = new URL("https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@.jpg").openStream();
        final BufferedImage imagemOriginal = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparencia e com tamanho novo
        int largura = imagemOriginal.getWidth();
        int altura = imagemOriginal.getHeight();
        int novaAltura = altura + 200;
        //var tercoImagem = largura / 3;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);
        
        // copiar a imagem original pra novo imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(imagemOriginal,0,0,null);

        // configurar a fonte e tamanho
        var fonte = new Font(Font.SANS_SERIF, Font.BOLD, 82);
        graphics.setFont(fonte);
        graphics.setColor(Color.YELLOW);

        // escrever frase embaixo da imagem
        graphics.drawString("TOPZERA", (largura / 2) - 220, novaAltura - 100);
        
        // escrever a nova imagem em um arquivo
        File folder = new File("saida");
        if (!folder.exists()){
            folder.mkdir();
        }
        ImageIO.write(novaImagem,"png",new File("saida/" + nomeArquivo));
    }

}
