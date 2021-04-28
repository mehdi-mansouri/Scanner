package Package;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LA {
    private int CurrentPoint = -1;
    public int CurrentState = 0;
    static ArrayList<String> TokenName = new ArrayList<String>();
    static ArrayList<Byte> TokenType = new ArrayList<>();

    private String Path;// Address source code
    private String code;// alle code sind hier
    private String token = "";
    private char[] Letter = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
    };
    private char[] Digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private char[] Symble = {'(', ')', '{', '}', ';', '+', '-', '*', '/', '=', '.', '<', '>', '!', '"'};
    private char[] WitheSpace = {'\n', '\t', ' '};


    private String[] Keyword = {"ashar", "sahih", "chap", "agar", "begir"};


    public String Nexttoken() {

        char ch;

        CurrentState = 0;


        while (true) {
            switch (CurrentState) {
                case 0:
                    ch = Nextchar();
                    token = token + ch;
                    if (Case1(ch)) {
                        break;
                    } else {
                        return token + "is not defined in Simple Languege";
                    }
                case 1:
                    if (IsInLetters(Wasnextchar()) || IsInDigits(Wasnextchar())) {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 1;
                    } else {
                        if (IsInKeywords(token)) {
                            TokenName.add(token);
                            TokenType.add(TokenList.Keyword);
                            return "<" + token + ", " + token + " >";

                        } else
                            TokenName.add(token);
                        TokenType.add(TokenList.ID);
                        return "<" + token + ", ID > ";
                    }

                    break;
                case 2:
                    if (IsInDigits(Wasnextchar())) {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 4;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.PLUS);
                        return "<" + token + ", Plus >";
                    }

                    break;
                case 3:
                    if (IsInDigits(Wasnextchar())) {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 4;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.MINUS);
                        return "<" + token + ", Minus >";
                    }
                    break;
                case 4:
                    if (IsInDigits(Wasnextchar())) {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 4;
                    } else if (Wasnextchar() == '.') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 5;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.NUM);
                        return "<" + token + ", IntNummer >";
                    }

                    break;
                case 5:
                    if (IsInDigits(Wasnextchar())) {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 6;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.ERROR);
                        return token + "is not defined in language";

                    }

                    break;
                case 6:
                    if (IsInDigits(Wasnextchar())) {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 6;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.FLOAT);

                        return "<" + token + ",floatNummer>";
                    }


                    break;
                case 7: {
                    TokenName.add(token);
                    TokenType.add(TokenList.OPEN_PAR);

                    return "<" + token + ",linksparentheses >";
                }
                case 8: {
                    TokenName.add(token);
                    TokenType.add(TokenList.CLOSE_PAR);
                    return "<" + token + ",Rechtsparentheses >";
                }
                case 9: {
                    TokenName.add(token);
                    TokenType.add(TokenList.SEMICOLON);
                    return "<" + token + ",Semicolone >";
                }
                case 10: {
                    TokenName.add(token);
                    TokenType.add(TokenList.OPEN_ACC);
                    return "<" + token + ",linksAccolade >";
                }
                case 11: {
                    TokenName.add(token);
                    TokenType.add(TokenList.CLOSE_ACC);
                    return "<" + token + ",rechtsAccolade >";
                }
                case 12:
                    if (Wasnextchar() == '/') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 13;
                    } else if (Wasnextchar() == '*') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 15;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.DIV);
                        return "<" + token + ",Divide";
                    }
                    break;
                case 13:
                    if (Wasnextchar() == '\n') {
                        ch = Nextchar();
                        CurrentState = 14;
                    } else {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 13;
                    }

                    break;
                case 14: {
                    TokenName.add(token);
                    TokenType.add(TokenList.LINECOMMEN);
                    return "<" + token + ",LineComment";
                }
                case 15:
                    if (Wasnextchar() == '*') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 16;
                    } else {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 15;
                    }
                    break;
                case 16:
                    if (Wasnextchar() == '/') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 17;
                    } else if (Wasnextchar() == '*') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 16;
                    } else {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 15;
                    }
                    break;
                case 17: {
                    TokenName.add(token);
                    TokenType.add(TokenList.MULTILINECOMMEN);
                    return "<" + token + " ,multiLineComment";

                }
                case 18:
                    if (Wasnextchar() == '"') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 19;
                    } else {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 18;
                    }

                    break;
                case 19: {
                    TokenName.add(token);
                    TokenType.add(TokenList.STRING);
                    return "<" + token + " , String >";
                }
                case 20: {
                    TokenName.add(token);
                    TokenType.add(TokenList.MULT);
                    return "< " + token + " , multiple >";
                }
                case 21:
                    if (Wasnextchar() == '=') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 22;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.EQUAL);
                        return "< " + token + " , equal";

                    }
                    break;
                case 22: {
                    TokenName.add(token);
                    TokenType.add(TokenList.ZWEIEQUAL);
                    return "< " + token + " , zweiEqual >";
                }
                case 23:
                    if (Wasnextchar() == '=') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 24;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.LESS);
                        return "< " + token + " , less >";
                    }
                    break;
                case 24: {
                    TokenName.add(token);
                    TokenType.add(TokenList.LESSEQUAL);
                    return "< " + token + " ' lessEqual >";

                }
                case 25:
                    if (Wasnextchar() == '=') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 26;
                    } else {
                        TokenName.add(token);
                        TokenType.add(TokenList.MORE);
                        return "< " + token + " , more >";
                    }
                    break;
                case 26: {
                    TokenName.add(token);
                    TokenType.add(TokenList.MOREEQUAL);

                    return "< " + token + " , moreEqual >";
                }
                case 27:
                    if (Wasnextchar() == '=') {
                        ch = Nextchar();
                        token = token + ch;
                        CurrentState = 28;
                    }
                    break;
                case 28: {
                    TokenName.add(token);
                    TokenType.add(TokenList.NOTEQUAL);
                    return "< " + token + " , notEqual >";
                }
                case 29:
                    return "$";
                case 30:
                    token = "";
                    CurrentState = 0;
                    break;
            }
        }

    }

    public boolean Case1(char ch) {
        if (IsInLetters(ch)) {
            CurrentState = 1;
            return true;
        } else if (IsInDigits(ch)) {
            CurrentState = 4;
            return true;
        }else if (IsInWhitespace(ch)) {
            CurrentState = 30;
        return true;
        }
        switch (ch) {
            case '+':
                CurrentState = 2;
                return true;
            case  '-':
                CurrentState = 3;
                return true;
            case  '(':
                CurrentState = 7;
                return true;
            case  ')':
                CurrentState = 8;
                return true;
            case  ';':
                CurrentState = 9;
                return true;
            case  '{':
                CurrentState = 10;
                return true;
            case  '}':
                CurrentState = 11;
                return true;
            case  '/':
                CurrentState = 12;
                return true;
            case '"':
                CurrentState = 18;
                return true;
            case  '*':
                CurrentState = 20;
                return true;
            case  '=':
                CurrentState = 21;
                return true;
            case  '<':
                CurrentState = 23;
                return true;
            case  '>':
                CurrentState = 25;
                return true;
            case '!':
                CurrentState = 27;
                return true;
            case  '$':
                CurrentState = 29;
                return true;
        }
        return false;
    }


    private char Nextchar() {
        char ch;
        CurrentPoint++;
        if (CurrentPoint >= code.length()) {
            return '$';
        }
        ch = code.charAt(CurrentPoint);
        if (!(IsInDigits(ch) || IsInLetters(ch) || IsInSymble(ch) || IsInWhitespace(ch))) {
            System.err.print("character was not found");

            return ' ';
        }

        return ch;
    }

    private char Wasnextchar() {
        char ch;
        int wasnextcharpoint = CurrentPoint + 1;
        if (wasnextcharpoint >= code.length())
            return '$';

        ch = code.charAt(wasnextcharpoint);
        return ch;
    }

    //---------------------------------------------Alphabet
    private boolean IsInLetters(char ch) {
        for (int i = 0; i < Letter.length; i++) {
            if (Letter[i] == ch)
                return true;
        }
        return false;
    }

    private boolean IsInWhitespace(char ch) {
        for (int i = 0; i < WitheSpace.length; i++) {
            if (WitheSpace[i] == ch)
                return true;
        }
        return false;
    }

    private boolean IsInSymble(char ch) {
        for (int i = 0; i < Symble.length; i++) {
            if (Symble[i] == ch)
                return true;
        }

        return false;
    }

    private boolean IsInDigits(char ch) {
        for (int i = 0; i < Digits.length; i++) {
            if (Digits[i] == ch)
                return true;
        }
        return false;
    }

    private boolean IsInKeywords(String word) {
        for (int i = 0; i < Keyword.length; i++) {
            if (word.equals(Keyword[i]))
                return true;

        }
        return false;
    }

    //--------------------------------------------------constructor
    public LA(String path) {
        this.setPath(path);
    }

    //--------------------------------------------------setpath
    /*private void stePath(String path) {

        this.Path=path;
        code="";
        try {
            File source = new File(this.Path);
            Scanner in = new Scanner(source);
            while (in.hasNextLine())
            {
                code=code+"\n"+in.hasNextLine();
            }
            in.close();
        }catch (FileNotFoundException e)
        {
            System.err.print("source File nicht Gefunden");
            e.fillInStackTrace();
        }
    }*/
    private void setPath(String path) {
        this.Path = path;
        this.code = "";

        try {
            File source = new File(this.Path);

            String var10001;
            Scanner in;
            for (in = new Scanner(source); in.hasNextLine(); this.code = var10001 + "\n" + in.nextLine()) {
                var10001 = this.code;
            }

            in.close();
        } catch (FileNotFoundException var4) {
            System.err.println("Source File not Found");
            var4.printStackTrace();
        }

    }
}
