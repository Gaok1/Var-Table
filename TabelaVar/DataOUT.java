public class DataOUT {
    // Definições de códigos de escape ANSI para cores
    private static final String ANSI_COLOR_RED = "\u001B[31m";
    private static final String ANSI_COLOR_GREEN = "\u001B[32m";
    private static final String ANSI_COLOR_YELLOW = "\u001B[33m";
    private static final String ANSI_COLOR_BLUE = "\u001B[34m";
    private static final String ANSI_COLOR_MAGENTA = "\u001B[35m";
    private static final String ANSI_COLOR_CYAN = "\u001B[36m";
    private static final String ANSI_COLOR_RESET = "\u001B[0m";

    private static final int NameFieldSize = 5;
    private static final int TypeFieldSize = 12;
    private static final int ValueFieldSize = 5;
    private static final int AddressFieldSize = 12;

  

    public static void clean() {
        // Limpa a tela
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    private static void printTableRow(VarTable table) {
        // Imprime o cabeçalho da tabela com cores
        clean();
        System.out.println(ANSI_COLOR_CYAN + "+------+--------------+--------------+--------------+");
        System.out.println("|" + ANSI_COLOR_YELLOW + " Name " + ANSI_COLOR_CYAN + "|" + ANSI_COLOR_RED
                + " Type         " + ANSI_COLOR_CYAN + "|" + ANSI_COLOR_BLUE + " Value " + ANSI_COLOR_CYAN + "|"
                + ANSI_COLOR_GREEN + " Address      " + ANSI_COLOR_CYAN + "|");
        System.out.println("+------+--------------+--------------+--------------+" + ANSI_COLOR_RESET);

        // Itera sobre cada variável na tabela
        for (Variable var : table.variables) {
            if (var == null) {
                System.out.println(ANSI_COLOR_RED + "Variable is NULL" + ANSI_COLOR_RESET);
                continue;
            }

            // Imprime os detalhes da variável de acordo com seu tipo, com cores diferentes
            String name = var.name;
            String type = var.type.toString();
            String value = (var.value != null) ? var.value.toString() : "null";
            String address = var.address;

            // Preenche com espaços adicionais para alinhar os campos
            name = padRight(name, NameFieldSize);
            type = padRight(type, TypeFieldSize);
            value = padRight(value, ValueFieldSize);
            address = padRight(address, AddressFieldSize);

            System.out.println("|" + ANSI_COLOR_YELLOW + " " + name + ANSI_COLOR_CYAN + "| " + ANSI_COLOR_RED + type
                    + ANSI_COLOR_CYAN + " | " + ANSI_COLOR_BLUE + value + ANSI_COLOR_CYAN + " | " + ANSI_COLOR_GREEN
                    + address + ANSI_COLOR_CYAN + " |");
        }

        // Adiciona a linha de finalização da tabela
        System.out
                .println(ANSI_COLOR_CYAN + "+------+--------------+--------------+--------------+" + ANSI_COLOR_RESET);
    }

    // Função para preencher com espaços à direita para alinhar os campos
    private static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static void printTable(VarTable table) {
        printTableRow(table);
    }

    public static void Start(VarTable table) {
        System.out.println(ANSI_COLOR_CYAN + "+--------------------------------------------------+");
        System.out.println("|             " + ANSI_COLOR_YELLOW + "VAR TABLE" + ANSI_COLOR_CYAN
                + "                    |");
        System.out.println("+------+--------------+--------------+--------------+\n\n");
        System.out.println(ANSI_COLOR_RESET);
    }

    public static void printComandSign() {
        System.out.print(ANSI_COLOR_CYAN + ">>> " + ANSI_COLOR_RESET);
    }

    public static void Alert(String message) {
        System.out.println(ANSI_COLOR_RED + message + ANSI_COLOR_RESET);
    }

    static void printVar(Variable var) {
        // Imprime os detalhes da variável com cores diferentes
        System.out.println(ANSI_COLOR_YELLOW + "Name: " + ANSI_COLOR_CYAN + var.name);
        System.out.println(ANSI_COLOR_RED + "Type: " + ANSI_COLOR_CYAN + var.type);
        System.out.println(
                ANSI_COLOR_BLUE + "Value: " + ANSI_COLOR_CYAN + ((var.value != null) ? var.value.toString() : "null"));
        System.out.println(ANSI_COLOR_GREEN + "Address: " + ANSI_COLOR_CYAN + var.address);
        // Reset das cores para evitar vazamentos
        System.out.print(ANSI_COLOR_RESET);
    }

    static void printReference(Variable var, Variable reference) {
        // Imprime os detalhes da variável com cores diferentes
        System.out.println(ANSI_COLOR_YELLOW + "Name: " + ANSI_COLOR_CYAN + var.name);
        System.out.println(ANSI_COLOR_RED + "Type: " + ANSI_COLOR_CYAN + var.type);
        System.out.println(ANSI_COLOR_BLUE + "Value: " + ANSI_COLOR_CYAN + var.value.toString());
        System.out.println(ANSI_COLOR_GREEN + "Address: " + ANSI_COLOR_CYAN + var.address);
        System.out.println("Referenced value -> " + ANSI_COLOR_BLUE + reference.value.toString() + ANSI_COLOR_RESET);
        // Reset das cores para evitar vazamentos
        System.out.print(ANSI_COLOR_RESET);
    }

    static void Help(){
        System.out.println(ANSI_COLOR_CYAN + "Comandos disponíveis:");
        System.out.println(ANSI_COLOR_YELLOW + "let <type> <name>;" + ANSI_COLOR_CYAN + " - Declaração de variável");
        System.out.println(ANSI_COLOR_YELLOW + "<name> = <value>;" + ANSI_COLOR_CYAN + " - Atribuição de valor");
        System.out.println(ANSI_COLOR_YELLOW + "<name> -> <name>;" + ANSI_COLOR_CYAN + " - Referência de variável");
        System.out.println(ANSI_COLOR_YELLOW + "print <name>;" + ANSI_COLOR_CYAN + " - Imprime o valor da variável");
        System.out.println(ANSI_COLOR_YELLOW + "drop <name>;" + ANSI_COLOR_CYAN + " - Remove a variável da tabela");
        System.out.println(ANSI_COLOR_YELLOW + "clean;" + ANSI_COLOR_CYAN + " - Limpa a tela");
        System.out.println(ANSI_COLOR_YELLOW + "Table nome de variavel para imprimir a tabela de variáveis -> print Table" + ANSI_COLOR_RESET);

    }

}