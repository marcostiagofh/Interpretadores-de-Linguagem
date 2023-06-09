/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpretador;

import commands.CommandBlock;

/**
 *
 * @author Marcos
 */

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java mlab [MiniLab File]");
            return;
        }

        try (LexicalAnalysis la = new LexicalAnalysis(args[0])) {
            SyntaticalAnalysis sa = new SyntaticalAnalysis(la);
            CommandBlock cmd = sa.start();
            cmd.execute();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }  
        System.out.printf("Acabou o programa\n");
    }

    private static boolean checkType(TokenType type) {
        return !(type == TokenType.END_OF_FILE ||
                 type == TokenType.INVALID_TOKEN ||
                 type == TokenType.UNEXPECTED_EOF);
    }
}


