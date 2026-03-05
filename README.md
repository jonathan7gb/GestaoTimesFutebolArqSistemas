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

### S — Single Responsibility Principle (Responsabilidade Única)

> *"Uma classe deve ter apenas um motivo para mudar."*

Cada classe possui exatamente uma responsabilidade bem definida:

| Classe | Responsabilidade |
|---|---|
| `ClubeService` | Apenas regras de negócio relacionadas a clube |
| `PartidaService` | Apenas regras de negócio relacionadas a partida |
| `UsuarioService` | Apenas regras de negócio relacionadas a usuário |
| `ClubeRepositoryImpl` | Apenas persistência de clube no banco de dados |
| `ClubeMapper` | Apenas conversão entre `Clube` (entidade) e `ClubeResponseDTO` |
| `MessageHelper` | Apenas exibição de mensagens no console |
| `InputHelper` | Apenas leitura e validação de entrada do usuário |
| `ConsoleNotificacao` | Apenas envio de notificações coloridas ao console |
| `MainController` | Apenas orquestração da navegação entre menus |

**Por quê?** — Quando uma responsabilidade muda (ex.: formato das mensagens), somente a classe correspondente (`MessageHelper`) precisa ser alterada; as demais ficam intocadas.

---

### O — Open/Closed Principle (Aberto/Fechado)

> *"Uma classe deve estar aberta para extensão e fechada para modificação."*

A interface `INotificacao` (em `domain/notification`) define o contrato de notificação. Novas estratégias de envio são adicionadas criando uma nova classe que implementa essa interface, **sem alterar nenhum código existente**:

```
INotificacao (interface)
├── ConsoleNotificacao  ← exibe mensagens coloridas no terminal
└── SilentNotificacao   ← descarta todas as mensagens (modo silencioso)
```

Da mesma forma, as interfaces de repositório (`ClubeRepository`, `PartidaRepository`, `UsuarioRepository`) permitem que novas implementações de persistência (ex.: em memória, em arquivo) sejam adicionadas sem modificar os serviços.

**Por quê?** — O `Main` seleciona a estratégia de notificação em tempo de execução; os serviços nunca precisam ser alterados para suportar um novo modo de notificação.

---

### L — Liskov Substitution Principle (Substituição de Liskov)

> *"Classes derivadas devem poder substituir suas classes base sem alterar o comportamento esperado."*

`ClubeRepositoryImpl`, `PartidaRepositoryImpl` e `UsuarioRepositoryImpl` implementam completamente os contratos de `ClubeRepository`, `PartidaRepository` e `UsuarioRepository`, respectivamente. O `MainController` recebe instâncias de `IClubeService`, `IPartidaService` e `IUsuarioService`; qualquer implementação concreta pode substituir a atual sem quebrar o sistema.

**Por quê?** — Garante que os testes e o código de produção possam usar implementações substitutas (ex.: stubs em memória) sem comportamento inesperado.

---

### I — Interface Segregation Principle (Segregação de Interfaces)

> *"Nenhum cliente deve ser forçado a depender de métodos que não utiliza."*

Em vez de uma única interface monolítica com todos os métodos possíveis (como `MembroFutebol` no módulo Caos), as interfaces são pequenas e coesas:

| Interface | Métodos |
|---|---|
| `ClubeRepository` | `criarClube`, `listarClubes`, `existeId` |
| `PartidaRepository` | `criarPartida`, `listarPartidas`, `buscarPartidaPorClube`, `listarNomeClubes` |
| `UsuarioRepository` | `criarUsuario`, `listarUsuarios`, `listarJogador`, `buscarJogadorPorId`, … |
| `IClubeService` | `criarClube`, `mostrarClubes` |
| `IPartidaService` | `criarPartida`, `listarPartidas`, `listarPartidasPorClube`, `retornarNomesClubesPartidas` |
| `IUsuarioService` | `criarUsuario`, `listarUsuarios`, `idsNomeClubeUsuario`, `listarUsuariosDeUmClube` |
| `INotificacao` | `sucesso`, `erro`, `info` |

**Por quê?** — Cada implementação assina apenas o contrato relevante para si, sem herdar métodos sem sentido para o seu contexto.

---

### D — Dependency Inversion Principle (Inversão de Dependências)

> *"Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações."*

O fluxo de dependência no módulo Ordem é:

```
presentation  →  application  →  domain (interfaces)  ←  infra
```

- `MainController` depende de `IClubeService`, `IPartidaService`, `IUsuarioService` (interfaces do domínio).
- `ClubeService`, `PartidaService`, `UsuarioService` dependem de `ClubeRepository`, `PartidaRepository`, `UsuarioRepository` e `INotificacao` (interfaces do domínio) — **nenhum import de classe concreta de infraestrutura existe nestas classes**.
- `ClubeRepositoryImpl`, `PartidaRepositoryImpl`, `UsuarioRepositoryImpl`, `ConsoleNotificacao` e `SilentNotificacao` implementam as interfaces do domínio.
- `Main.java` é a única classe que conhece as implementações concretas e as injeta via construtor.

**Por quê?** — Isola a lógica de negócio de detalhes técnicos (banco de dados, console). Trocar o banco de PostgreSQL para outro ou o modo de notificação requer apenas mudar `Main.java`, sem tocar nos serviços.

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
