package main;

import Command.CommandBlock;
import main.Lexeme;
import main.Lexical;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java msi [Mini Shell Script File]");
            return;
        }

        try (Lexical la = new Lexical(args[0])) {
            Syntatical sa = new Syntatical(la);
            CommandBlock cmd = sa.start();
            cmd.execute();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }  
        System.out.printf("Acabou o programa\n");
        
        /*try (Lexical l = new Lexical(args[0])) {
            Lexeme lex;
            while (checkType((lex = l.nextToken()).type)) {
                System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
            }

            switch (lex.type) {
                case INVALID_TOKEN:
                    System.out.printf("%02d: Lexema inválido [%s]\n", l.line(), lex.token);
                    break;
                case UNEXPECTED_EOF:
                    System.out.printf("%02d: Fim de arquivo inesperado\n", l.line());
                    break;
                default:
                    System.out.printf("(\"%s\", %s)\n", lex.token, lex.type);
                    break;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }*/
    }

    private static boolean checkType(TokenType type) {
        return !(type == TokenType.END_OF_FILE ||
                 type == TokenType.INVALID_TOKEN ||
                 type == TokenType.UNEXPECTED_EOF);
    }
}

