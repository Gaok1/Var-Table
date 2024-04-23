import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.Data;

public class Interpreter {
    final static String tokenDeclare = "let";
    final static String tokenAssign = "=";
    final static String tokenBind = "->";
    final static String assignReference = "=>";
    final static String tokenPrint = "print";
    final static String intType = ":int";
    final static String doubleType = ":double";
    final static String charType = ":char";
    final static String intPointerType = ":int*";
    final static String doublePointerType = ":double*";
    final static String charPointerType = ":char*";
    final static String end = ";";
    final static String tableToken = "Table";
    final static String deref = "*";
    final static String drop = "drop";
    final static String clean = "clean";

    
    static VarTable table;

    static void start(VarTable x){
        table = x;
    }
    static Type hasType(String line) { // trocar ordem, pois o int é substring do int*
        if (line.contains(intType)) {
            if (line.contains(intPointerType)) {
                return Type.INT_POINTER;
            }
            return Type.INT;
        } else if (line.contains(doubleType)) {
            if (line.contains(doublePointerType)) {
                return Type.DOUBLE_POINTER;
            }
            return Type.DOUBLE;
        } else if (line.contains(charType)) {
            if (line.contains(charPointerType)) {
                return Type.CHAR_POINTER;
            }
            return Type.CHAR;
        }
        return null;
    }

    public static void interpret(String code) {
        String[] lines = code.split(";");
        for (String line : lines) {
            if(line.contains(clean)){
                DataOUT.clean();
            }else if(line.contains("help")){
                DataOUT.Help();
            }
            else if (line.contains(tokenDeclare)) {
                declareVariable(line);
            }else if(line.contains(assignReference)){
                assignReference(line);
            } 
            else if (line.contains(tokenAssign)||line.contains(tokenBind)) {
                assignVariable(line);
            } else if (line.contains(tokenPrint)) {
                printVariable(line);
            }else if(line.contains(deref)){
                dereferenceVariable(line);
            }else if(line.contains(drop)){
                dropVariable(line);
            }
        }
    }

    public static void declareVariable(String line) {
        line = removeSpace(line);
        Type T = hasType(line);
        try{

        String type = T.toString().toLowerCase();
        line = line.replace(":","");
        if (type == null) {
            DataOUT.Alert("Invalid type declaration");
        }

        // Encontrar as posições das expressões "let" e o tipo
        int letIndex = line.indexOf(tokenDeclare);
        int typeIndex = line.indexOf(Variable.TypeToString(T).toLowerCase());

        // Extrair a substring entre "let" e o tipo
        String name = line.substring(letIndex + tokenDeclare.length(), typeIndex).trim();
        if (name.equals(tableToken)) {
            DataOUT.Alert("Invalid variable name | Table is a reserved token");
            return;
        }

        Variable var = new Variable(T, name, null);
        if(table.getVariable(name) != null){
            
            return;
        }
        table.addVariable(var);
    }catch(Exception e){
        DataOUT.Alert("Invalid declaration" + e.getMessage());
    }
    }

    static void assignVariable(String line) {
        /*
         * line expected like -> var = value;
         * or var -> var2
         */
        line = removeSpace(line);
        line = line.replace(";", "");
        if (line.contains(tokenAssign)) {
            String[] parts = line.split("=");
            String varName = parts[0];
            String value = parts[1];
            Variable var = table.getVariable(varName);
            if (var == null) {
                DataOUT.Alert("Variable not found");
                return;
            } else if (!var.canAssign()) {
                DataOUT.Alert("Invalid assign");
                return;
            }
            try {

                if (var.type == Type.INT) {
                    var.value = Integer.parseInt(value);
                } else if (var.type == Type.DOUBLE) {
                    var.value = Double.parseDouble(value);
                } else if (var.type == Type.CHAR) {
                    var.value = value.charAt(0);
                }
            } catch (Exception e) {
                DataOUT.Alert("Invalid assign" + e.getMessage());
                return;
            }

        } else if (line.contains(tokenBind)) {
            try {
                String[] parts = line.split("->");
                // y -> x
                String varName = parts[0];
                String varName2 = parts[1];
                Variable referenciator = table.getVariable(varName);
                Variable source = table.getVariable(varName2);
                if (referenciator == null || source == null) {
                    DataOUT.Alert("Variable not found to reference");
                    return;
                } else if (!referenciator.canReference()) {
                    DataOUT.Alert("Invalid assign");
                    return;
                }
                table.refereenceVar(referenciator, source);
            } catch (Exception e) {
                DataOUT.Alert("Invalid assign" + e.getMessage());
                return;
            }

        } else {
            DataOUT.Alert("Invalid assign");
            return;
        }
    }

   
    static void printVariable(String line) {
        line = removeSpace(line);
        line = line.replace(";", "");

        String varName = line.replace(tokenPrint, "");
        Variable var = table.getVariable(varName);
        if(varName.equals(tableToken)){
            DataOUT.printTable(table);
            return;
        }
        if (var == null) {
            DataOUT.Alert("Variable not found to print ->" + varName);
            return;
        }
        DataOUT.printVar(var);
    }

    static String removeSpace(String str) {
        return str.replaceAll("\\s", "");
    }

    static void dereferenceVariable(String line) {
        line = removeSpace(line);
        line = line.replace(";", "");
        String varName = line.replace(deref, "");
        Variable var = table.getVariable(varName);
        if (var == null) {
            DataOUT.Alert("Variable not found to dereference ->" + varName);
            return;
        }
        DataOUT.printReference(var, table.dereferenceVar(var));
    } 

    static void dropVariable(String line) {
        line = removeSpace(line);
        line = line.replace(";", "");
        String varName = line.replace(drop, "");
        Variable var = table.getVariable(varName);
        if (var == null) {
            DataOUT.Alert("Variable not found to drop ->" + varName);
            return;
        }
        table.dropVar(varName);
    }

    static void assignReference(String line) {
        line = removeSpace(line);
        line = line.replace(";", "");
        String[] parts = line.split("=>");
        String varName = parts[0];
        Variable referenciator = table.getVariable(varName);
        Object newValue = parts[1];
        if (referenciator == null ) {
            DataOUT.Alert("Variable not found to reference");
            return;
        } else if (!referenciator.canReference()) {
            DataOUT.Alert("Invalid assign");
            return;
        }

        table.assignReference(referenciator, newValue);
        
    }

    

}
