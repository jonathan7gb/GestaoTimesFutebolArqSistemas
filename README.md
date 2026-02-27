# Gestão de Times de Futebol 🏆

Projeto Maven multi-módulo em Java que demonstra a diferença entre código **sem boas práticas** e código **seguindo os princípios SOLID**, usando como contexto um sistema de gestão de times de futebol.

---

## 📦 Módulos

### 🔴 Caos
Código escrito sem boas práticas de arquitetura. Serve como exemplo do que **não** fazer: responsabilidades misturadas, alto acoplamento e baixa coesão.

### 🟢 Ordem
Código refatorado seguindo os **princípios SOLID**, com camadas bem definidas:

| Camada | Pacote | Responsabilidade |
|---|---|---|
| Domínio | `domain.entity` | Entidades do negócio (Clube, Partida, Usuário) |
| Serviço | `service` | Regras de negócio |
| Infraestrutura | `infra` | Acesso a banco de dados e conexões |

---

## 🧱 Princípios SOLID aplicados no módulo Ordem

- **S** — *Single Responsibility*: cada classe tem uma única responsabilidade
- **O** — *Open/Closed*: classes abertas para extensão, fechadas para modificação
- **L** — *Liskov Substitution*: subtipos podem substituir seus tipos base
- **I** — *Interface Segregation*: interfaces coesas e específicas
- **D** — *Dependency Inversion*: dependência de abstrações, não de implementações concretas

---

## 🚀 Como executar

**Pré-requisitos:** Java 22+ e Maven 3.x

```bash
# Clonar o repositório
git clone https://github.com/jonathan7gb/GestaoTimesFutebolArqSistemas.git
cd GestaoTimesFutebolArqSistemas

# Compilar todos os módulos
mvn compile

# Executar o módulo Ordem
cd Ordem
mvn exec:java -Dexec.mainClass="br.com.weg.Main"
```

---

## 🛠️ Tecnologias

- Java 22
- Maven (multi-módulo)
