# Interpreter Java

The **Interpreter Java** is a command-line application written in Java that provides a simplified interpreter for a basic programming language. It allows users to execute commands for variable declaration, assignment, referencing, printing, and more.

## Features

### Variable Management
- **Declaration**: Declare variables with types such as integer (`int`), double (`double`), character (`char`), and their respective pointer types.
- **Assignment**: Assign values to variables of compatible types.
- **Referencing**: Create references between variables.
- **Printing**: Print the values of variables.
- **Removal**: Remove variables from the interpreter's variable table.

### Additional Commands
- **Screen Clearing**: Clear the console screen for better visibility.
- **Help**: Get assistance and view available commands.
- **print Table**: print the current variables with Type,value and Adress

## Supported Types

The interpreter supports the following types:
- **Integer**: `:int`
- **Double**: `:double`
- **Character**: `:char`
- **Integer Pointer**: `:int*`
- **Double Pointer**: `:double*`
- **Character Pointer**: `:char*`

## Supported Commands

The interpreter supports the following commands:

- **print**: Print the value of a referenced variable in the table. When using the keyword `Table`, it prints all variables declared in the table.

- **Dereference**: Dereferences a pointer using the variable's address, indicated by the `*` operator.

- **Declare**: Add new variables to the table using the statement `let`. It must specify the variable type after the variable's name.

- **Typing**: When declaring a variable, specify its type after the variable's name.

- **Assigning Value**: Assign values to variables of non-pointer types using the equal sign `=`.

- **Referencing Variables**: Use `->` to capture the variable's address for referencing. Refer to the Command Syntax section for more details.

- **Assigning Values to Pointers**: Use `=>` to assign a value through a pointer to the variable whose address is stored in the pointer.



## Usage

1. **Compile and Run**: Compile the Java files and execute the main class.
2. **Enter Commands**: Input commands using the specified syntax for variable declaration, assignment, referencing, etc.
3. **Observe Output**: View the output generated by the interpreter as it executes the commands.

## Command Syntax

### Variable Declaration
```java
let <type> <name>;
```

### Value Assignment
```java
let <type> <name>;
```

### Value Referencing
```java
<name> -> <name>;
```
### Referencing Assignment
```java
<name> => value;
```
### Printing
```java
print <name>;
```

### Value Removal
```java
drop <name>;
```

### Screen Clearing
```java
clean;
```

## Example
Here's an example session demonstrating the usage of the interpreter:
#**Input**:
```
>>> let a:int; a = 20;
>>> let b :int*; b -> a;
>>> b => 20
>>> print a
```
#**Output**:
```
Name: a
Type: INT
Value: 20
Address: [Address Value]
```
#**Input**:
```
>>> *b
```
#**Output**:

```
Name: b
Type: INT_POINTER
Value: [Address Value]
Address: [Address Value]
Referenced value -> 20

```

#**Input**:
```
>>> print Table
```
#**Output**:

```
+------+--------------+--------------+--------------+
| Name | Type         | Value | Address      |
+------+--------------+--------------+--------------+
| a    | INT          | 20    | 7C0ECB48     |
| b    | INT_POINTER  | 7C0ECB48 | B3038CC1     |
+------+--------------+--------------+--------------+

```





