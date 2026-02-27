# ⚽ Gestão de Times de Futebol — Sistema Ordem

> Projeto educacional desenvolvido para a disciplina de **Arquitetura de Sistemas**, demonstrando a diferença entre um sistema com arquitetura bem definida (**Ordem**) e um sistema sem organização estrutural (**Caos**).

---

## 📌 Índice

1. [Visão Geral](#visão-geral)
2. [Módulos do Projeto](#módulos-do-projeto)
3. [Sistema Ordem — Detalhamento](#sistema-ordem--detalhamento)
   - [Objetivo](#objetivo)
   - [Arquitetura em Camadas](#arquitetura-em-camadas)
   - [Estrutura de Pacotes](#estrutura-de-pacotes)
   - [Entidades de Domínio](#entidades-de-domínio)
   - [Camada de Infraestrutura](#camada-de-infraestrutura)
   - [Camada de Serviço](#camada-de-serviço)
   - [Ponto de Entrada](#ponto-de-entrada)
4. [Comparação: Ordem vs Caos](#comparação-ordem-vs-caos)
5. [Tecnologias Utilizadas](#tecnologias-utilizadas)
6. [Pré-requisitos](#pré-requisitos)
7. [Como Compilar e Executar](#como-compilar-e-executar)
8. [Estrutura de Diretórios](#estrutura-de-diretórios)

---

## Visão Geral

O sistema **Gestão de Times de Futebol** é uma aplicação Java que gerencia informações relacionadas ao mundo do futebol, incluindo **clubes**, **partidas** e **usuários**. O projeto é estruturado como um **multi-módulo Maven** e serve como estudo prático de arquitetura de software, comparando dois módulos distintos:

| Módulo   | Descrição                                                               |
|----------|-------------------------------------------------------------------------|
| `Ordem`  | Sistema com arquitetura em camadas bem definida (Domain, Service, Infra) |
| `Caos`   | Sistema sem separação de responsabilidades ou organização estrutural    |

---

## Módulos do Projeto

```
GestaoTimesFutebolArqSistemas/   ← Projeto raiz (POM agregador)
├── Ordem/                        ← Módulo com arquitetura organizada
└── Caos/                         ← Módulo sem arquitetura definida
```

**Grupo Maven:** `br.com.weg`  
**Versão:** `1.0-SNAPSHOT`  
**Java:** 22

---

## Sistema Ordem — Detalhamento

### Objetivo

O módulo **Ordem** demonstra como um sistema deve ser organizado seguindo os princípios de **Arquitetura em Camadas** (*Layered Architecture*). Cada camada possui uma responsabilidade única e bem definida, promovendo:

- ✅ **Separação de responsabilidades** (*Separation of Concerns*)
- ✅ **Alta coesão** dentro de cada camada
- ✅ **Baixo acoplamento** entre as camadas
- ✅ **Manutenibilidade** e **escalabilidade** do código
- ✅ **Testabilidade** isolada de cada componente

---

### Arquitetura em Camadas

O sistema **Ordem** é dividido em três camadas principais:

```
┌──────────────────────────────────────────────┐
│               Camada de Serviço              │  ← Regras de Negócio
│              (br.com.weg.service)            │
├──────────────────────────────────────────────┤
│            Camada de Domínio                 │  ← Entidades do negócio
│          (br.com.weg.domain.entity)          │
├──────────────────────────────────────────────┤
│          Camada de Infraestrutura            │  ← Conexão e Persistência
│             (br.com.weg.infra)               │
└──────────────────────────────────────────────┘
```

| Camada          | Pacote                      | Responsabilidade                                              |
|-----------------|-----------------------------|---------------------------------------------------------------|
| **Domain**      | `br.com.weg.domain.entity`  | Modelagem das entidades de negócio (Clube, Partida, Usuário)  |
| **Service**     | `br.com.weg.service`        | Lógica de negócio e orquestração das operações                |
| **Infra**       | `br.com.weg.infra`          | Conexão com banco de dados e repositórios de acesso a dados   |

---

### Estrutura de Pacotes

```
Ordem/
└── src/
    └── main/
        └── java/
            └── br/
                └── com/
                    └── weg/
                        ├── Main.java                          ← Ponto de entrada da aplicação
                        ├── domain/
                        │   └── entity/
                        │       ├── Clube.java                 ← Entidade: Clube de futebol
                        │       ├── Partida.java               ← Entidade: Partida entre clubes
                        │       └── Usuario.java               ← Entidade: Usuário do sistema
                        ├── infra/
                        │   ├── conexao/
                        │   │   └── Conexao.java               ← Gerenciamento de conexão com BD
                        │   └── repository/
                        │       └── ClubeRepository.java       ← Repositório de acesso a Clubes
                        └── service/
                            └── UsuarioService.java            ← Serviço de regras de negócio de Usuário
```

---

### Entidades de Domínio

As entidades representam os conceitos centrais do negócio e ficam isoladas na camada de domínio (`br.com.weg.domain.entity`). Elas não dependem de nenhuma outra camada.

#### 🏟️ `Clube`
Representa um **clube de futebol**. É a entidade central do sistema e agrega informações como nome, cidade, fundação e elenco de jogadores.

#### ⚽ `Partida`
Representa uma **partida de futebol** entre dois clubes. Armazena dados como clube mandante, clube visitante, placar, data e local de realização.

#### 👤 `Usuario`
Representa um **usuário** do sistema de gestão. Contém as informações de autenticação e perfil de acesso ao sistema.

---

### Camada de Infraestrutura

A camada de infraestrutura (`br.com.weg.infra`) é responsável por toda a comunicação com recursos externos, como banco de dados. Ela é dividida em dois subpacotes:

#### 🔌 `conexao/Conexao`
Gerencia a **conexão com o banco de dados**. Centraliza a configuração e o ciclo de vida das conexões, garantindo que nenhuma outra parte do sistema precise conhecer detalhes de como a conexão é estabelecida.

- Responsabilidade: abrir, fornecer e fechar conexões com o banco de dados
- Padrão aplicado: *Single Responsibility Principle* (SOLID)

#### 🗄️ `repository/ClubeRepository`
Implementa o padrão **Repository** para a entidade `Clube`. Centraliza todas as operações de persistência relacionadas a clubes:

- Busca de clubes por ID ou nome
- Inserção de novos clubes
- Atualização de dados de clubes existentes
- Remoção de clubes

Ao isolar o acesso a dados em um repositório, o restante do sistema permanece desacoplado dos detalhes de persistência.

---

### Camada de Serviço

A camada de serviço (`br.com.weg.service`) contém as **regras de negócio** da aplicação. Os serviços orquestram as entidades de domínio e os repositórios para executar casos de uso do sistema.

#### ⚙️ `UsuarioService`
Gerencia as operações de negócio relacionadas ao `Usuario`:

- Cadastro e validação de novos usuários
- Autenticação e controle de acesso
- Atualização de informações de perfil
- Desativação ou remoção de usuários

O serviço conhece o domínio e a infraestrutura, mas **nunca** expõe detalhes de implementação para camadas superiores.

---

### Ponto de Entrada

#### 🚀 `Main`
Classe principal que inicializa a aplicação. Neste estágio inicial do projeto, demonstra a execução básica do sistema. Em versões futuras, será responsável por iniciar o contexto da aplicação e expor os pontos de acesso (ex.: API REST, CLI, etc.).

---

## Comparação: Ordem vs Caos

| Critério                       | Módulo `Ordem`                              | Módulo `Caos`                          |
|--------------------------------|---------------------------------------------|----------------------------------------|
| **Organização**                | Pacotes por responsabilidade                | Sem organização estrutural             |
| **Separação de camadas**       | Domain / Service / Infra                    | Tudo junto em uma única classe/pacote  |
| **Manutenibilidade**           | Alta — fácil de modificar sem efeitos colaterais | Baixa — mudanças podem quebrar tudo |
| **Testabilidade**              | Alta — cada camada pode ser testada isolada | Baixa — dependências entrelaçadas      |
| **Escalabilidade**             | Alta — novas funcionalidades seguem o padrão | Baixa — adicionar código aumenta o caos |
| **Reusabilidade**              | Alta — serviços e repositórios reutilizáveis | Baixa — lógica duplicada e acoplada   |
| **Legibilidade**               | Alta — intenção clara pelo nome dos pacotes | Baixa — difícil entender o fluxo       |

> 💡 **Conclusão:** O módulo `Ordem` exemplifica como boas práticas de arquitetura tornam o sistema mais robusto, compreensível e fácil de evoluir, enquanto o módulo `Caos` mostra os problemas que surgem quando não há organização.

---

## Tecnologias Utilizadas

| Tecnologia       | Versão   | Finalidade                             |
|------------------|----------|----------------------------------------|
| Java             | 22       | Linguagem principal de desenvolvimento |
| Maven            | 3.x+     | Gerenciador de build e dependências    |
| IntelliJ IDEA    | —        | IDE recomendada para desenvolvimento   |

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **JDK 22** ou superior → [Download](https://www.oracle.com/java/technologies/downloads/)
- **Apache Maven 3.x+** → [Download](https://maven.apache.org/download.cgi)

Verifique as instalações:

```bash
java -version
mvn -version
```

---

## Como Compilar e Executar

### Clonar o repositório

```bash
git clone https://github.com/jonathan7gb/GestaoTimesFutebolArqSistemas.git
cd GestaoTimesFutebolArqSistemas
```

### Compilar todos os módulos

```bash
mvn clean compile
```

### Compilar apenas o módulo Ordem

```bash
cd Ordem
mvn clean compile
```

### Executar o módulo Ordem

```bash
mvn exec:java -Dexec.mainClass="br.com.weg.Main"
```

### Empacotar o projeto

```bash
mvn clean package
```

---

## Estrutura de Diretórios

```
GestaoTimesFutebolArqSistemas/
├── pom.xml                                          ← POM raiz (multi-módulo)
├── README.md                                        ← Este arquivo
├── Ordem/                                           ← Módulo com arquitetura organizada
│   ├── pom.xml
│   └── src/main/java/br/com/weg/
│       ├── Main.java
│       ├── domain/entity/
│       │   ├── Clube.java
│       │   ├── Partida.java
│       │   └── Usuario.java
│       ├── infra/
│       │   ├── conexao/Conexao.java
│       │   └── repository/ClubeRepository.java
│       └── service/
│           └── UsuarioService.java
└── Caos/                                            ← Módulo sem arquitetura definida
    ├── pom.xml
    └── src/main/java/br/com/weg/
        └── Main.java
```

---

> Projeto desenvolvido para fins educacionais — **Arquitetura de Sistemas** | `br.com.weg`
