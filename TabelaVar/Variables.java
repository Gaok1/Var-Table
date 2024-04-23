import java.util.ArrayList;
import java.util.Random;

// Enumeração para os diferentes tipos de variáveis
enum Type {
    INT,
    DOUBLE,
    CHAR,
    INT_POINTER,
    DOUBLE_POINTER,
    CHAR_POINTER

}

// Classe para representar uma variável
class Variable {
    Type type;
    String name;
    Object value; // O valor pode ser de qualquer tipo
    String address;

    // Construtor
    Variable(Type type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.address = generateRandomAddress();
    }

    // Método para gerar um endereço aleatório
    private String generateRandomAddress() {
        Random rand = new Random();
        StringBuilder addressBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            addressBuilder.append(Integer.toHexString(rand.nextInt(16)));
        }
        return addressBuilder.toString().toUpperCase();
    }

    // Método para converter a variável em uma string formatada
    @Override
    public String toString() {
        String typeStr = type.toString();
        String valueStr = (value != null) ? value.toString() : "NULL";
        return "Name: " + name + ", Type: " + typeStr + ", Value: " + valueStr + ", Address: " + address;
    }

    public boolean isPointer() {
        return type == Type.INT_POINTER || type == Type.DOUBLE_POINTER || type == Type.CHAR_POINTER;
    }

    public int getSizeBytes(Variable var) {
        if (var.type == Type.INT || var.type == Type.INT_POINTER) {
            return 4;
        } else if (var.type == Type.DOUBLE || var.type == Type.DOUBLE_POINTER) {
            return 8;
        } else if (var.type == Type.CHAR || var.type == Type.CHAR_POINTER) {
            return 1;
        }
        return 0;
    }

    boolean canAssign() {
        return type == Type.INT || type == Type.DOUBLE || type == Type.CHAR;
    }

    boolean canReference() {
        return type == Type.INT_POINTER || type == Type.DOUBLE_POINTER || type == Type.CHAR_POINTER;
    }

    public static String TypeToString(Type type) {
        if (type == Type.INT) {
            return "INT";
        } else if (type == Type.DOUBLE) {
            return "DOUBLE";
        } else if (type == Type.CHAR) {
            return "CHAR";
        } else if (type == Type.INT_POINTER) {
            return "INT*";
        } else if (type == Type.DOUBLE_POINTER) {
            return "DOUBLE*";
        } else if (type == Type.CHAR_POINTER) {
            return "CHAR*";
        }
        return "UNKNOWN";
    }

}

// Classe para representar uma tabela de variáveis
class VarTable {
    ArrayList<Variable> variables;
    int sizeBytes;

    // Construtor
    VarTable() {
        this.variables = new ArrayList<Variable>();
        this.sizeBytes = 0;
    }

    // Método para adicionar uma variável à tabela
    void addVariable(Variable var) {
        variables.add(var);
        sizeBytes += var.getSizeBytes(var);
    }

    public void refereenceVar(Variable var, Variable Source) {
        if (var.type == Type.INT_POINTER && Source.type == Type.INT) {
        } else if (var.type == Type.DOUBLE_POINTER && Source.type == Type.DOUBLE) {
        } else if (var.type == Type.CHAR_POINTER && Source.type == Type.CHAR) {
        } else {
            DataOUT.Alert("Invalid assign Type");
            return;
        }

        if (var.isPointer()) {
            var.value = Source.address;
        }

    }

    public void assignReference(Variable var, Object newValue) {
        if (!var.isPointer()) {
            return;
        }
        Variable source = dereferenceVar(var);
        if (varCanRefereVar(var, source)) {
            switch (var.type) {
                case INT_POINTER:
                    if(newValue instanceof String) {
                        source.value = Integer.parseInt((String) newValue);
                    }
                    break;
                case DOUBLE_POINTER:
                    if(newValue instanceof String) {
                        source.value = Double.parseDouble((String) newValue);
                    }
                    break;
                case CHAR_POINTER:
                    if(newValue instanceof String && !((String) newValue).isEmpty()) {
                        source.value = ((String) newValue).charAt(0);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public Variable dereferenceVar(Variable var) {
        if (var.isPointer()) {
            for (Variable variable : variables) {

                if (variable.address == var.value) {
                    return variable;
                }
            }
        }
        return null;
    }

    public boolean varCanRefereVar(Variable var, Variable source) {
        try{
           if (var.type != source.type) {
            if (var.type == Type.INT_POINTER && source.type == Type.INT) {
                return true;
            } else if (var.type == Type.DOUBLE_POINTER && source.type == Type.DOUBLE) {
                return true;
            } else if (var.type == Type.CHAR_POINTER && source.type == Type.CHAR) {
                return true;
            } else {
                return false;
            }
        } 
        }catch(Exception e){
            if(e instanceof NullPointerException){
                DataOUT.Alert("Null Pointer Exception");
            }else{
                DataOUT.Alert("Internal Error");
            }
            return false;
        }
        return false;
    }

    public Variable getVariable(String varName) {
        for (Variable var : this.variables) {
            if (var.name.equals(varName)) {
                return var;
            }
        }
        return null;
    }

    public boolean varCanAssign(String varName) {
        Variable var = getVariable(varName);
        if (var == null) {
            return false;
        }
        return var.canAssign();
    }

    public boolean varCanReference(String varName) {
        Variable var = getVariable(varName);
        if (var == null) {
            return false;
        }
        return var.canReference();
    }

    public void dropVar(String varName) {
        for (Variable var : this.variables) {
            if (var.name.equals(varName)) {
                this.variables.remove(var);
                return;
            }
        }
    }

}
