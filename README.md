# Gestão de Times de Futebol 🏆

Projeto Maven multi-módulo em Java desenvolvido para a disciplina de **Arquitetura de Sistemas**. Demonstra na prática a diferença entre código escrito sem boas práticas de engenharia e código refatorado aplicando os **princípios SOLID**, usando como domínio um sistema de gestão de times de futebol.

O projeto é composto por dois módulos que implementam as mesmas funcionalidades de formas opostas — permitindo uma comparação direta entre os dois estilos de desenvolvimento.

---

## 📦 Módulos

### 🔴 Caos

Código intencionalmente mal estruturado para ilustrar antipadrões comuns. Principais problemas encontrados:

| Violação | Descrição |
|---|---|
| **SRP** (Single Responsibility) | A classe `Main` gerencia o fluxo, conecta ao banco, executa lógica de negócio e trata erros — tudo em um único lugar |
| **OCP** (Open/Closed) | Lógica de bônus de pagamento implementada com `switch` fixo — adicionar um novo tipo exige modificar a classe existente |
| **ISP** (Interface Segregation) | A interface `MembroFutebol` obriga todas as classes a implementar métodos como `cobrarEscanteio()` e `contratar()`, mesmo quando não fazem sentido para a entidade |
| **DIP** (Dependency Inversion) | Dependência direta de `Scanner`, strings de conexão hardcoded e instâncias concretas de `DriverManager` espalhadas pelo código |
| **SQL Injection** | Queries montadas por concatenação de strings sem sanitização (`"INSERT INTO clube (nome) VALUES ('" + nome + "')"`) |
| **Credenciais expostas** | Usuário, senha e URL do banco de dados escritos diretamente no código-fonte |

**Tecnologias:** Java 22 · MySQL (mysql-connector-j 8.0.33)

---

### 🟢 Ordem

Código refatorado seguindo os **princípios SOLID** e organizado em camadas bem definidas (arquitetura em camadas / Layered Architecture):

```
Ordem/src/main/java/br/com/weg/
│
├── Main.java                          # Ponto de entrada
│
├── domain/                            # Camada de Domínio
│   ├── entity/
│   │   ├── Clube.java                 # Entidade: clube de futebol
│   │   ├── Partida.java               # Entidade: partida entre dois clubes
│   │   └── Usuario.java               # Entidade: jogador, comissão técnica ou presidente
│   ├── enums/
│   │   └── TipoUsuario.java           # JOGADOR | COMISSAOTECNICA | PRESIDENTE | ADMIN
│   └── repository/                    # Interfaces (abstrações) — DIP
│       ├── ClubeRepository.java
│       ├── PartidaRepository.java
│       └── UsuarioRepository.java
│
├── application/                       # Camada de Aplicação
│   ├── dto/                           # Data Transfer Objects (Request / Response)
│   │   ├── Clube/
│   │   ├── Partida/
│   │   └── Usuario/
│   ├── mapper/                        # Conversão entre Entity e DTO
│   │   ├── ClubeMapper.java
│   │   ├── PartidaMapper.java
│   │   └── UsuarioMapper.java
│   └── service/                       # Regras de negócio (SRP)
│       ├── ClubeService.java
│       ├── PartidaService.java
│       └── UsuarioService.java
│
├── infra/                             # Camada de Infraestrutura
│   ├── database/
│   │   ├── Conexao.java               # Gerenciamento centralizado da conexão PostgreSQL
│   │   └── TesteConexao.java
│   └── persistence/                   # Implementações concretas dos repositórios
│       ├── ClubeRepositoryImpl.java
│       ├── PartidaRepositoryImpl.java
│       └── UsuarioRepositoryImpl.java
│
└── presentation/                      # Camada de Apresentação (CLI)
    ├── controller/
    │   └── MainController.java        # Orquestra a navegação entre menus
    └── view/
        ├── AdminView.java
        ├── ClubeView.java
        ├── PartidaView.java
        ├── UsuarioView.java
        └── helpers/
            ├── InputHelper.java       # Leitura de entrada do usuário
            └── MessageHelper.java     # Exibição padronizada de mensagens
```

**Tecnologias:** Java 22 · PostgreSQL (postgresql 42.7.2)

---

## 🧱 Princípios SOLID aplicados no módulo Ordem

### S — Single Responsibility (Responsabilidade Única)
Cada classe tem exatamente uma responsabilidade:
- `ClubeService` → apenas regras de negócio relacionadas a clube
- `ClubeRepositoryImpl` → apenas persistência de clube no banco de dados
- `ClubeMapper` → apenas conversão entre `Clube` (entidade) e `ClubeResponseDTO`
- `MessageHelper` → apenas exibição de mensagens de sucesso/erro no console

### O — Open/Closed (Aberto/Fechado)
Novas funcionalidades são adicionadas criando novas classes que implementam as interfaces existentes, sem modificar o código já testado.

### L — Liskov Substitution (Substituição de Liskov)
`ClubeRepositoryImpl`, `PartidaRepositoryImpl` e `UsuarioRepositoryImpl` implementam suas respectivas interfaces do domínio (`ClubeRepository`, `PartidaRepository`, `UsuarioRepository`) e podem ser substituídas sem quebrar o comportamento esperado.

### I — Interface Segregation (Segregação de Interfaces)
Em vez de uma única interface `MembroFutebol` com todos os métodos possíveis (como no módulo Caos), as interfaces são coesas e específicas por contexto (repositórios separados para cada entidade de domínio).

### D — Dependency Inversion (Inversão de Dependências)
Os serviços (`ClubeService`, `PartidaService`, `UsuarioService`) dependem das **abstrações** definidas em `domain/repository`, não das implementações concretas em `infra/persistence`. A camada de domínio não conhece a camada de infraestrutura.

---

## 👤 Perfis de usuário e permissões

O sistema possui quatro perfis de acesso com menus e permissões distintas:

| Perfil | Permissões |
|---|---|
| **JOGADOR** | Visualizar jogadores e partidas do próprio clube |
| **COMISSAOTECNICA** | Visualizar jogadores e partidas do próprio clube |
| **PRESIDENTE** | Cadastrar jogadores, agendar partidas, visualizar jogadores e partidas |
| **ADMIN** | Acesso completo: cadastrar clube, usuário, partida; listar todos os registros |

---

## 🗄️ Banco de Dados

O módulo **Ordem** utiliza **PostgreSQL**. A conexão é gerenciada centralmente na classe `Conexao.java` (camada de infraestrutura).

Configure as credenciais de acesso ao banco na classe:

```
Ordem/src/main/java/br/com/weg/infra/database/Conexao.java
```

Tabelas esperadas:

| Tabela | Campos principais |
|---|---|
| `clube` | `id`, `nome`, `ano_fundacao`, `pais` |
| `usuario` | `id`, `nome`, `peso`, `altura`, `tipo`, `id_clube` |
| `partida` | `id`, `id_clube_a`, `id_clube_b`, `data_hora_partida`, `localizacao` |

---

## 🚀 Como executar

**Pré-requisitos:** Java 22+ e Maven 3.x

```bash
# Clonar o repositório
git clone https://github.com/jonathan7gb/GestaoTimesFutebolArqSistemas.git
cd GestaoTimesFutebolArqSistemas

# Compilar todos os módulos
mvn compile

# Executar o módulo Ordem (código com boas práticas)
cd Ordem
mvn exec:java -Dexec.mainClass="br.com.weg.Main"

# Executar o módulo Caos (código sem boas práticas — apenas para fins didáticos)
cd Caos
mvn exec:java -Dexec.mainClass="br.com.weg.Main"
```

> ⚠️ O módulo **Caos** requer uma instância local do MySQL em `localhost:3306`. O módulo **Ordem** requer acesso ao banco PostgreSQL configurado em `Conexao.java`.

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 22 | Linguagem principal |
| Maven (multi-módulo) | Build e gerenciamento de dependências |
| PostgreSQL + JDBC | Banco de dados do módulo Ordem |
| MySQL + JDBC | Banco de dados do módulo Caos (didático) |

---

## 🎓 Contexto Acadêmico

Este projeto foi desenvolvido como trabalho prático da disciplina de **Arquitetura de Sistemas**, com o objetivo de evidenciar os problemas de um código sem estrutura e demonstrar como a aplicação dos **princípios SOLID** resulta em um sistema mais legível, manutenível e extensível.
