package org.celso.main;

import com.google.gson.Gson;
import org.celso.model.Filme;
import org.celso.model.Itens;
import org.celso.utils.Manipulador;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Main {

    static Filme filme = new Filme();
    private static Scanner menu;


    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        Manipulador manipulador = new Manipulador();
        Properties properties = manipulador.getProp();

        // Criando um menu
        menu(properties);

    }

    private static void menu(Properties properties) throws IOException, InterruptedException {
        menu = new Scanner (System.in);
        int opcao = 0;
        limparConsole();
        do {
            System.out.print("\n\n");
            System.out.print(colorize("##--------------Menu-------------##\n\n", CYAN_TEXT(), BOLD()));
            System.out.print("|---------------------------------|\n");
            System.out.print("| Opção 1 - Top Filmes            |\n");
            System.out.print("| Opção 2 - Top Series            |\n");
            System.out.print("| Opção 3 - Filmes mais populares |\n");
            System.out.print("| Opção 4 - Series mais populares |\n");
            System.out.print("| Opção 5 - Sair                  |\n");
            System.out.print("|---------------------------------|\n\n");
            System.out.print("Digite uma opção: ");

            opcao = menu.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("\n\n");
                    limparConsole();
                    System.out.println(colorize("************Top Filmes************\n\n",BOLD()));
                    setFilmes(properties.getProperty("url.topfilmes"));
                    break;

                case 2:
                    System.out.print("\n\n");
                    limparConsole();
                    System.out.println(colorize("************Top Series************\n\n",BOLD()));
                    setFilmes(properties.getProperty("url.topseries"));
                    break;

                case 3:
                    System.out.print("\n\n");
                    limparConsole();
                    System.out.println(colorize("************Filmes mais populares************\n\n",BOLD()));
                    setFilmes(properties.getProperty("url.mostpopularfilmes"));
                    break;

                case 4:
                    System.out.print("\n\n");
                    limparConsole();
                    System.out.println(colorize("************Series mais populares************\n\n",BOLD()));
                    setFilmes(properties.getProperty("url.mostpopularseries"));
                    break;

                case 5:
                    System.out.print("\nAté logo!");
                    menu.close();
                    break;

                default:
                    System.out.print("\nOpção Inválida!");
                    break;

            }
        }while (opcao < 5);
    }

    private static void setFilmes(String url) throws IOException, InterruptedException {
        filme = consumirApi(url);
        showLista(filme.getItems());
    }

    private static void showLista(ArrayList<Itens> items) throws IOException {
        int cont = 1;
        for (Itens i: items) {
            int round = Math.round(i.getImDbRating());
            if(round <=5){
                System.out.println(colorize(cont+"- "+i.getTitle(),RED_TEXT(), BOLD() ));
            }else{
                System.out.println(colorize(cont+"- "+i.getTitle(),GREEN_TEXT(), BOLD() ));
            }
            cont++;
            if(round <= 1){
                System.out.println("⭐☆☆☆☆");
            } else if (round == 2) {
                System.out.println("⭐⭐☆☆☆");
            }else if (round == 3) {
                System.out.println("⭐⭐⭐☆☆");
            }else if (round == 4) {
                System.out.println("⭐⭐⭐⭐☆");
            }else if (round >= 5) {
                System.out.println("⭐⭐⭐⭐⭐");
            }
            System.out.println("Ano de Lançamento: "+i.getYear());
            System.out.println("Poster: "+i.getImage());
            System.out.println(" ");

        }
        waitingEnter();
    }



    private static Filme consumirApi(String url) throws IOException, InterruptedException {
        Gson gson = new Gson();
        // create a client
        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
                        URI.create(url))
                .header("accept", "application/json")
                .build();

        // use the client to send the request
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // the response:
        Filme list = gson.fromJson(response.body(), Filme.class);

        return list;
    }

    public static void limparConsole() throws IOException, InterruptedException {
        //Limpa a tela no windows, no linux e no MacOS
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            Runtime.getRuntime().exec("clear");
    }

    public static void waitingEnter() throws IOException {
        System.out.print("Enter para voltar");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}