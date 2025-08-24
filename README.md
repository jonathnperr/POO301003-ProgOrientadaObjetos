# **POO301003 - Programação Orientada a Objetos**  

<p align="center">
  <img width="667" height="375" alt="home 1" src="https://github.com/user-attachments/assets/a704b525-756c-4c98-9c03-7155d22b5ac4" />
</p>

## **ℹ️ Sobre o Repositório**  
Repositório contendo o **projeto final** da disciplina **POO301003 - Programação Orientada a Objetos**, desenvolvido como parte do Curso Técnico em Desenvolvimento de Sistemas do IFSC. O projeto consiste em um **sistema de gerenciamento para uma lavagem de veículos**, aplicando os principais conceitos de **Orientação a Objetos (OO)** e **arquitetura em camadas (MVC)**.  

### **🔧 Tecnologias e Ferramentas**  
- **Linguagem:** Java  
- **Interface Gráfica:** JavaFX  
- **Banco de Dados:** MySQL  
- **Padrão de Projeto:** MVC (Model-View-Controller)  

## **🎯 Objetivos do Projeto**  
O projeto visa consolidar os conhecimentos adquiridos na disciplina, aplicando:  
✅ **Abstração e Encapsulamento** (Classes, Atributos, Métodos)  
✅ **Herança e Polimorfismo** (Extensibilidade e Reutilização de Código)  
✅ **Persistência de Dados** (CRUD com JDBC e MySQL)  
✅ **Arquitetura em Camadas** (Separação entre Model, View e Controller)  
✅ **Desenvolvimento de Casos de Uso** (Cadastro de Veículos, Modelos, Marcas, Cores)  

## **📚 Conteúdo Aplicado**  

### **1️⃣ Fundamentos de OO**  
- **Classes e Objetos** (`Veiculo`, `Modelo`, `Marca`, `Cor`, `Motor`)  
- **Encapsulamento** (Getters/Setters, Visibilidade `private`)  
- **Associações entre Classes** (Agregação e Composição)  

### **2️⃣ Persistência de Dados**  
- **Padrão DAO (Data Access Object)**  
- **Conexão com MySQL** (`DatabaseFactory`, `DatabaseMySQL`)  
- **Operações CRUD** (Inserir, Alterar, Remover, Listar)  

### **3️⃣ Interface Gráfica (JavaFX)**  
- **FXML para Layout** (Tabelas, Formulários, Diálogos)  
- **Controller Classes** (`FXMLAnchorPaneCadastroVeiculoController`, etc.)  
- **Validação de Dados** (Tratamento de Exceções)  

### **4️⃣ Arquitetura em Camadas (MVC)**  
- **Model** (Domínio e DAO)  
- **View** (FXML + JavaFX)  
- **Controller** (Lógica de Negócio e Navegação)  

## **📂 Estrutura do Projeto**  

```
📦 projeto-lavacao-veiculos  
├── 📂 src  
│   ├── 📂 br.edu.ifsc.fln  
│   │   ├── 📂 controller   # Controladores (MVC)  
│   │   ├── 📂 model        # Classes de domínio e DAO  
│   │   ├── 📂 view         # Arquivos FXML  
│   │   └── 📂 mainapp      # Classe principal (MainApp.java)  
├── 📜 README.md            # Este arquivo  
└── 📜 pom.xml (opcional)   # Configuração Maven (se usado)  
```

## **📖 Referências Bibliográficas**  

### **Bibliografia Básica**  
1. **ASCENCIO, Ana Fernanda Gomes; CAMPOS, Edilene Aparecida Veneruchi de.**  
   *Fundamentos da programação de computadores: algoritmos, Pascal, C/C++ e Java.*  
   São Paulo: Pearson Prentice Hall, 2012.  

2. **SILVA FILHO, Antonio Mendes da.**  
   *Introdução à programação orientada a objetos com C++.*  
   Rio de Janeiro: Elsevier, 2010.  

### **Bibliografia Complementar**  
3. **HORSTMANN, Cay S.; CORNELL, Gary.**  
   *Core Java: fundamentos.*  
   Rio de Janeiro: Alta Books, 2004.  

4. **DEITEL, Harvey M.**  
   *Java: como programar.*  
   México: Bookman, 2003.  

5. **SANTOS, Rafael.**  
   *Introdução à programação orientada a objetos usando Java.*  
   Rio de Janeiro: Elsevier, 2013.  

## **👨‍🏫 Professor Responsável**  
**Professor:** Marcos André Phishing  
**Código da Disciplina:** POO301003   
**Semestre:** 3º  

### **🚀 Como Executar o Projeto?**  
1. **Pré-requisitos:**  
   - JDK 11+  
   - MySQL (Banco de dados configurado conforme `DatabaseMySQL.java`)  
   - JavaFX (Adicionar como biblioteca)  

2. **Clone o repositório:**  
   ```bash
   git clone https://github.com/seu-usuario/projeto-lavacao-veiculos.git
   ```

3. **Importe no IntelliJ/Eclipse** e execute `MainApp.java`.  

### **🔍 Observações**  
- O projeto foi desenvolvido para **fins educacionais**, aplicando os conceitos vistos em aula.  
- **Dúvidas ou contribuições?** Sinta-se à vontade para abrir uma *issue* ou *pull request*.  
