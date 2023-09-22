package redes;

public class Teste {

    // CLASSE PARA TESTES

    public static void main(String[] args){


        // Teste de manipulação de string

        String palavra = "#Login        /#"; // Exemplo de comando emitido pelo cliente
        String nova = palavra.replace(" ", ""); // Onde tiver espaços em branco será substituído por nada (literalmente)
        nova = palavra.substring(palavra.indexOf("#") + 1, palavra.indexOf("/#")); // retira '#' e '/#'
        System.out.println(nova);
        nova = nova.trim(); // Retira possíveis espaços em branco antes ou depois da string desejada
        System.out.println(nova.length()); // Tamanho da string

    }

}
