# 📱 Diário de Emoções - Android App

> **Projeto PDM - IFSULDEMINAS**  
> Aplicativo Android para registro e acompanhamento de emoções diárias.

## 🎯 Sobre o Projeto

O **Diário de Emoções** é um aplicativo Android desenvolvido para atender aos requisitos do trabalho de PDM. O app permite aos usuários registrar suas emoções diárias através de um sistema robusto que integra:

- **Firebase** para autenticação e armazenamento
- **Room Database** para dados locais
- **Material Design** para interface moderna

## ✅ Requisitos Atendidos

### 📋 Checklist do Trabalho

- [x] **CRUDs com Firebase Firestore** - Mínimo 4 campos cada
- [x] **2+ Activities/Telas** no app
- [x] **Interface Responsiva** com Material Design
- [x] **Tratamento de Erros** robusto

### 🏗️ Arquitetura Implementada

```
📁 Estrutura do Projeto
├── 🎯 Activities
│   ├── SentimentoDoDiaActivity.java (Firebase)
│   ├── MainActivity.java (Navegação)
│   ├── MetasActivity.java (Objetivos)
│   └── ConfiguracoesActivity.java (Configurações)
├── 📊 Models
│   ├── EmocaoModel.java (Dados de emoções)
│   └── Meta.java (Objetivos)
└── 🎨 Layouts (Material Design)
```

## 🚀 Funcionalidades Principais

### 1. � Registro de Emoções
- **Firebase Firestore** para armazenamento de dados
- **Interface intuitiva** para registro diário
- **Histórico completo** de emoções

### 2. � Sistema de Metas
- **Definição de objetivos** pessoais
- **Acompanhamento de progresso**
- **Motivação contínua**

### 3. � Firebase Integration
- **Firestore** para dados de usuários
- **Authentication** para login seguro
- **Room** para cache local

## 🛠️ Tecnologias Utilizadas

### 🎨 Frontend
- **Material Design 3** - UI/UX moderna
- **AndroidX** libraries
- **ConstraintLayout** - Layouts responsivos

### 📊 Database
- **Firebase Firestore** - NoSQL cloud
- **Room** - SQLite local
- **SharedPreferences** - Configurações

## 📖 Como Executar

### Pré-requisitos
- Android Studio Arctic Fox+
- Android SDK API 30+
- Java 11+

### Passo a Passo

1. **Clone o Repositório**
```bash
git clone [URL_DO_REPOSITORIO]
cd LukeDiarioEmocoes
```

2. **Configure o Firebase**
- Adicione o arquivo `google-services.json`
- Configure Firestore Database
- Configure Authentication

3. **Execute o Projeto**
```bash
./gradlew clean assembleDebug
```

## 📱 Funcionalidades Demonstradas

### 💾 Sistema CRUD Completo

```java
// Exemplo de operações Firestore
- Create: Adicionar nova emoção
- Read: Listar histórico de emoções  
- Update: Editar registros existentes
- Delete: Remover registros antigos
```

### 🎨 Interface Responsiva

- **Loading states** com ProgressBar
- **Material Design** consistente
- **Navegação fluida** entre telas

### 📱 Multi-Activity Navigation

```
MainActivity (Menu Principal)
├── SentimentoDoDiaActivity (Firebase CRUD)
├── MetasActivity (Objetivos)
├── ConfiguracoesActivity (Configurações)
└── [Outras Activities...]
```

## 🎯 Atendimento aos Requisitos

### 🏆 Requisitos Cumpridos

1. **CRUDs com Firebase Firestore** ✅
   - EmocaoModel com 4+ campos
   - Meta com 4+ campos
   - Operações completas CRUD

2. **Multiple Activities** ✅
   - MainActivity (navegação)
   - SentimentoDoDiaActivity (CRUD principal)
   - MetasActivity (sistema de objetivos)
   - ConfiguracoesActivity (configurações)

3. **Interface Responsiva** ✅
   - Material Design 3
   - Layouts adaptativos
   - Feedback visual

### 🔧 Arquitetura Robusta

- **Separation of Concerns** - Cada classe com responsabilidade única
- **SOLID Principles** - Código mantenível e extensível
- **Error Recovery** - Sistema nunca "quebra"
- **Configuration Management** - Fácil personalização

## 🎓 Contexto Acadêmico

### Conceitos Demonstrados

1. **Banco de dados NoSQL** (Firestore)
2. **Arquitetura em camadas** (Service, Repository, UI)
3. **Tratamento de estados de erro**
4. **Design patterns** (Singleton, Factory, Observer)

### Skills Técnicas Aplicadas

- ✅ **Android Development** (Java)
- ✅ **Firebase** integration
- ✅ **Material Design** implementation
- ✅ **Error handling** strategies
- ✅ **Database** management (Room + Firestore)

## 🏆 Resultado Final

### ✨ O que foi Entregue

Um aplicativo Android **completo** e **profissional** que:

- ✅ **Atende 100%** dos requisitos do trabalho
- ✅ **Interface moderna** com Material Design
- ✅ **Código limpo** e bem documentado
- ✅ **Arquitetura escalável** para futuras expansões

### 🎯 Diferencial Competitivo

Este projeto demonstra **conhecimento sólido** em:
- **Modern Android Development**
- **Firebase Integration**
- **Material Design Implementation**
- **Professional Code Organization**

---

## 👨‍💻 Desenvolvido por

**Lucas** - IFSULDEMINAS  
📧 Contato: [lucas_vianasilva@hotmail.com]  
📅 Data: Julho 2025

**Aryane** - IFSULDEMINAS   
📅 Data: Julho 2025

**Cauã F.** - IFSULDEMINAS
📅 Data: Julho 2025
### 📄 Licença
Este projeto foi desenvolvido para fins acadêmicos - IFSULDEMINAS PDM 2025