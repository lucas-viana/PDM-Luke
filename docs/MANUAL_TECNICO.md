# 📚 Manual Técnico - Luke Diário de Emoções

## 🎯 Visão Geral do Projeto

**Luke Diário de Emoções** é um aplicativo Android desenvolvido especificamente para educação assistiva voltada a pessoas com autismo. O app oferece um ambiente estruturado e visual para organizar atividades diárias, registrar emoções e promover autonomia pessoal.

### 🏗️ Arquitetura Geral
- **Framework**: Android SDK (Java)
- **Banco de Dados**: Firebase Firestore + SQLite (Room)
- **Autenticação**: Firebase Authentication
- **Comunicação HTTP**: Retrofit 2.9.0 + OkHttp 4.12.0
- **UI/UX**: Material Design Components
- **Notificações**: Local Notifications + Push Notifications

---

## 📱 Módulos da Aplicação

### 1. **🔐 Sistema de Autenticação**

#### **LoginActivity.java**
- **Função**: Gerencia login de usuários
- **Recursos**:
  - Autenticação via Firebase
  - Validação de campos em tempo real
  - Redirecionamento automático para usuários logados
  - Feedback visual de carregamento

#### **CadastroActivity.java**
- **Função**: Registro de novos usuários
- **Recursos**:
  - Criação de conta Firebase
  - Validação de email e senha
  - Campo para email do responsável (para notificações)
  - Verificação de confirmação de senha

**🔧 Configuração**:
```java
// Firebase Authentication já configurado
FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
```

---

### 2. **🏠 Tela Principal (MainActivity)**

#### **MainActivity.java**
- **Função**: Hub central de navegação
- **Recursos**:
  - Lista de atividades do Luke com categorias
  - Toolbar com menu de opções
  - FloatingActionButton para registro rápido de sentimentos
  - Sistema de notificações diárias

#### **AcaoPersonagemAdapter.java**
- **Função**: Adapter para lista de atividades
- **Recursos**:
  - Categorização visual por cores
  - Ícones personalizados para cada ação
  - Indicadores de importância
  - Clique para executar ações

**🎨 Atividades Disponíveis**:
- **Autocuidado**: Banho, escovação, alimentação
- **Rotina**: Dormir, estudar, organizar casa
- **Lazer**: Brincadeiras, exercícios, música
- **Social**: Interação, comunicação
- **Emoções**: Registro de sentimentos

---

### 3. **💭 Sistema de Registro Emocional**

#### **SentimentoDiarioActivity.java**
- **Função**: Interface principal para registro de emoções
- **Recursos**:
  - RadioButtons para seleção de humor
  - SeekBar para intensidade emocional
  - Persistência em banco local (Room)
  - Histórico de registros

#### **EmocaoEntity.java**
- **Função**: Entidade do banco de dados
- **Campos**:
  - `id`: Identificador único
  - `tipoEmocao`: Tipo do sentimento
  - `intensidade`: Nível de 1-5
  - `data`: Timestamp do registro
  - `observacoes`: Notas adicionais

**💾 Banco de Dados Room**:
```java
@Entity(tableName = "emocoes")
public class EmocaoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    // ... outros campos
}
```

---

### 4. **💡 Sistema de Dicas Motivacionais**

#### **DicaDoDiaActivity.java**
- **Função**: Exibe dicas motivacionais personalizadas
- **Recursos Atuais**:
  - ✅ **Sistema Offline**: 30 dicas estáticas pré-definidas
  - ✅ **Randomização**: Seleção aleatória de dicas e autores
  - ✅ **Feedback Visual**: Animações suaves e loading states
  - ✅ **100% Confiável**: Sem dependências externas

#### **Dicas Implementadas**:
```java
private final String[] DICAS_MOTIVACIONAIS = {
    "Cada pequeno passo é uma grande conquista! 🌟",
    "Você é único e especial do seu próprio jeito! 💫",
    "A rotina traz segurança e tranquilidade. 📅",
    // ... 27 dicas adicionais
};
```

**🔄 Funcionalidade**:
- Clique em "Nova Dica" → Gera dica aleatória instantaneamente
- Animações suaves para transição de conteúdo
- Autores variados da "Equipe Luke"

---

### 5. **📝 Sistema de Posts do GitHub**

#### **PostsActivity.java**
- **Função**: Consome e exibe posts de um repositório GitHub
- **URL Configurada**: `https://raw.githubusercontent.com/lucas-viana/luke-posts/refs/heads/main/posts.json`

#### **PostApiService.java**
- **Função**: Interface Retrofit para API do GitHub
- **Endpoint**: 
```java
@GET("lucas-viana/luke-posts/refs/heads/main/posts.json")
Call<List<Post>> obterPosts();
```

#### **PostAdapter.java**
- **Função**: Adapter para RecyclerView de posts
- **Layout**: Material Cards com título, corpo e ID

**🌐 Recursos**:
- Carregamento automático de posts
- Tratamento de erros com fallback offline
- Interface responsiva com loading states
- Posts offline de exemplo quando GitHub inacessível

---

### 6. **⚙️ Sistema de Configurações**

#### **ConfiguracoesActivity.java**
- **Função**: Tela de configurações do usuário
- **Recursos**:
  - Configuração de nome do usuário
  - Gerenciamento de notificações
  - Informações do app
  - Opções de logout

#### **PreferencesManager.java**
- **Função**: Gerencia preferências locais
- **Dados Armazenados**:
  - Nome do usuário
  - Configurações de notificação
  - Flag de primeiro acesso

---

### 7. **🎯 Sistema de Metas**

#### **MetasActivity.java**
- **Função**: Gestão de objetivos pessoais
- **Recursos**:
  - Criação de metas personalizadas
  - Acompanhamento de progresso
  - Categorização por área de interesse

---

### 8. **🖼️ Galeria de Ações**

#### **GaleriaAcoesActivity.java**
- **Função**: Visualização de atividades realizadas
- **Recursos**:
  - Histórico de ações executadas
  - Filtros por categoria e data
  - Estatísticas de uso

---

### 9. **📧 Sistema de Notificações por Email**

#### **Cloud Functions (Firebase)**
- **Função**: Envio automático de emails para responsáveis
- **Trigger**: Quando uma ação é registrada no Firestore
- **Configuração**: Gmail SMTP configurado

#### **Fluxo de Funcionamento**:
1. Usuário executa uma ação no app
2. Dados salvos na coleção `acoesRealizadas` (Firestore)
3. Cloud Function detecta novo documento
4. Email automático enviado para responsável

**📨 Exemplo de Email**:
```
De: Luke - Diário de Emoções <notificacoes.luke.diario@gmail.com>
Para: responsavel@email.com
Assunto: Nova atividade registrada por João

Olá,

João acabou de registrar a seguinte atividade:

Ação: Escovar os dentes
Descrição: Escovei os dentes após o café da manhã

Atenciosamente,
Equipe Luke
```

---

## 🛠️ Configurações Técnicas

### **Firebase Setup**
```json
{
  "project_id": "luke-diario-emocoes",
  "authentication": "enabled",
  "firestore": "enabled",
  "functions": "enabled"
}
```

### **Dependências Principais (build.gradle)**
```gradle
dependencies {
    // Firebase
    implementation 'com.google.firebase:firebase-auth:22.3.1'
    implementation 'com.google.firebase:firebase-firestore:24.10.0'
    
    // Retrofit & HTTP
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.okhttp3:okhttp:4.12.0'
    
    // Room Database
    implementation 'androidx.room:room-runtime:2.6.1'
    annotationProcessor 'androidx.room:room-compiler:2.6.1'
    
    // Material Design
    implementation 'com.google.android.material:material:1.11.0'
}
```

### **Permissões (AndroidManifest.xml)**
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

---

## 📊 Estrutura de Dados

### **Firestore Collections**
```
/acoesRealizadas
  - emailResponsavel: string
  - nomeUsuario: string
  - tituloAcao: string
  - descricaoAcao: string
  - timestamp: timestamp

/usuarios
  - nome: string
  - email: string
  - emailResponsavel: string
  - configuracoes: object
```

### **SQLite Tables (Room)**
```sql
CREATE TABLE emocoes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    tipoEmocao TEXT NOT NULL,
    intensidade INTEGER NOT NULL,
    data TEXT NOT NULL,
    observacoes TEXT
);
```

---

## 🔄 Fluxos de Funcionamento

### **Fluxo de Login**
1. **LoginActivity** → Validação → Firebase Auth
2. Sucesso → **MainActivity**
3. Erro → Feedback visual

### **Fluxo de Registro de Emoção**
1. **MainActivity** → FAB → **SentimentoDiarioActivity**
2. Seleção de emoção + intensidade
3. Salvar → Room Database
4. Confirmação visual

### **Fluxo de Dica do Dia**
1. **MainActivity** → Menu → **DicaDoDiaActivity**
2. Carregamento automático de dica aleatória
3. "Nova Dica" → Gera nova dica instantaneamente
4. Animação suave de transição

### **Fluxo de Posts GitHub**
1. **MainActivity** → Menu → **PostsActivity**
2. Retrofit → GitHub Raw API
3. Sucesso → Exibe posts / Erro → Posts offline
4. Feedback visual contínuo

### **Fluxo de Notificação Email**
1. Usuário executa ação → **AcaoPersonagemAdapter**
2. Dados → Firestore (`acoesRealizadas`)
3. Cloud Function triggered
4. Email SMTP → Responsável

---

## 🎨 Design System

### **Cores Principais**
```xml
<color name="marrom_01">#8B4513</color>
<color name="amarelo_01">#FFD700</color>
<color name="vermelho_01">#DC143C</color>
<color name="azul_01">#4169E1</color>
```

### **Tipografia**
- **Font Family**: Nunito (acessibilidade otimizada)
- **Tamanhos**: 12sp, 14sp, 16sp, 18sp, 20sp

### **Componentes UI**
- **Material Cards**: Para itens de lista
- **FloatingActionButton**: Ações principais
- **Toolbar**: Navegação consistente
- **ProgressBar**: Feedback de carregamento

---

## 🔧 Manutenção e Troubleshooting

### **Logs Importantes**
```java
Log.d("DicaDoDiaActivity", "Nova dica carregada: " + dicaSelecionada);
Log.d("PostsActivity", "Posts carregados com sucesso: " + posts.size());
Log.e("MainActivity", "Erro na autenticação: " + error);
```

### **Problemas Comuns**

#### **1. Dicas não carregam**
- **Causa**: Arquivo de layout não encontrado
- **Solução**: Verificar `R.layout.activity_dica_do_dia`

#### **2. Posts GitHub falham**
- **Causa**: URL incorreta ou sem internet
- **Solução**: Verifica URL em `PostApiService.java` ou usar offline

#### **3. Emails não enviados**
- **Causa**: Campo emailResponsavel vazio
- **Solução**: Implementar coleta no cadastro

#### **4. Room database erro**
- **Causa**: Migração de schema
- **Solução**: Incrementar version em `@Database`

---

## 🚀 Deploy e Distribuição

### **Build Release**
```bash
./gradlew assembleRelease
```

### **APK Location**
```
app/build/outputs/apk/release/app-release.apk
```

### **Assinatura Digital**
- Configurar keystore em `app/build.gradle`
- Usar variáveis de ambiente para senhas

---

## 📈 Métricas e Analytics

### **Eventos Trackeados**
- Login de usuário
- Registro de emoção
- Execução de ação
- Visualização de dica
- Acesso a posts

### **Firebase Analytics**
```java
FirebaseAnalytics.getInstance(this)
    .logEvent("acao_executada", bundle);
```

---

## 🔮 Roadmap Futuro

### **Versão 1.1**
- [ ] Sistema de conquistas
- [ ] Modo escuro
- [ ] Backup na nuvem
- [ ] Widget para tela inicial

### **Versão 1.2**
- [ ] Integração com calendário
- [ ] Lembretes personalizados
- [ ] Relatórios para responsáveis
- [ ] Múltiplos idiomas

### **Versão 2.0**
- [ ] IA para recomendações
- [ ] Comunidade de usuários
- [ ] Terapias guiadas
- [ ] Integração com dispositivos

---

## 👥 Equipe de Desenvolvimento

**Desenvolvedor Principal**: Lucas Viana  
**Foco**: Educação assistiva para autismo  
**Tecnologias**: Android, Firebase, Java  

---

## 📞 Suporte Técnico

**Issues GitHub**: Para problemas técnicos  
**Email**: Para dúvidas de uso  
**Documentação**: Este manual para referência técnica  

---

*"Desenvolvido com ❤️ para promover inclusão e autonomia através da tecnologia"*
