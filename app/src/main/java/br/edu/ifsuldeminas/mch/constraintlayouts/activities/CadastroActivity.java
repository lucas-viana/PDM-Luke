package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.utils.PreferencesManager;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;
    private EditText editTextEmailResponsavel;
    private Button buttonCadastrar;
    private TextView textViewJaTenhoConta;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        preferencesManager = new PreferencesManager(this);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        editTextConfirmarSenha = findViewById(R.id.editTextConfirmarSenha);
        editTextEmailResponsavel = findViewById(R.id.editTextEmailResponsavel);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        textViewJaTenhoConta = findViewById(R.id.textViewJaTenhoConta);
        progressBar = findViewById(R.id.progressBar);

        // Limpar erros ao focar nos campos
        editTextNome.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) editTextNome.setError(null);
        });

        editTextEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) editTextEmail.setError(null);
        });

        editTextSenha.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) editTextSenha.setError(null);
        });

        editTextConfirmarSenha.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) editTextConfirmarSenha.setError(null);
        });

        editTextEmailResponsavel.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) editTextEmailResponsavel.setError(null);
        });
    }

    private void setupClickListeners() {
        buttonCadastrar.setOnClickListener(v -> realizarCadastro());
        
        textViewJaTenhoConta.setOnClickListener(v -> {
            // Voltar para a tela de login
            Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void realizarCadastro() {
        // Limpar erros anteriores
        limparErros();

        // Obter valores dos campos
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();
        String confirmarSenha = editTextConfirmarSenha.getText().toString().trim();
        String emailResponsavel = editTextEmailResponsavel.getText().toString().trim();

        // Debug para acompanhar o processo
        System.out.println("=== DEBUG CADASTRO ===");
        System.out.println("Nome: '" + nome + "'");
        System.out.println("Email: '" + email + "'");
        System.out.println("Senha length: " + senha.length());
        System.out.println("Email Responsável: '" + emailResponsavel + "'");
        System.out.println("=====================");

        // Validar campos
        if (!validarCampos(nome, email, senha, confirmarSenha, emailResponsavel)) {
            return;
        }

        // Verificar conectividade apenas para debug - não bloquear o cadastro
        boolean networkAvailable = isNetworkAvailable();
        System.out.println("=== VERIFICAÇÃO DE REDE (Debug) ===");
        System.out.println("Rede disponível: " + networkAvailable);
        System.out.println("NOTA: Prosseguindo com cadastro independente da verificação local");
        System.out.println("===================================");

        // Mostrar carregamento
        mostrarCarregamento(true);

        // Tentar cadastrar - deixar o Firebase lidar com erros de conectividade
        tentarCadastro(email, senha, nome, emailResponsavel, 1);
    }

    private void tentarCadastro(String email, String senha, String nome, String emailResponsavel, int tentativa) {
        System.out.println("=== TENTATIVA " + tentativa + " ===");
        
        // Criar usuário no Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Cadastro bem-sucedido
                            mostrarCarregamento(false);
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                // Salvar dados do usuário
                                preferencesManager.setUserName(nome);
                                preferencesManager.salvarUsuario(user.getUid(), email, emailResponsavel);

                                Toast.makeText(CadastroActivity.this,
                                        "✅ Cadastro realizado com sucesso! Bem-vindo ao Luke Diário, " + nome + "! 🎉",
                                        Toast.LENGTH_LONG).show();

                                // Redirecionar para MainActivity
                                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // Falha no cadastro - verificar se deve tentar novamente
                            Exception exception = task.getException();
                            boolean shouldRetry = false;
                            
                            if (exception != null) {
                                String error = exception.getMessage();
                                if (error != null && (error.contains("network") || error.contains("timeout") || 
                                                    error.contains("interrupted connection") || 
                                                    error.contains("unreachable host") ||
                                                    error.contains("reCAPTCHA"))) {
                                    shouldRetry = true;
                                }
                            }
                            
                            if (shouldRetry && tentativa < 3) {
                                // Tentar novamente após delay
                                System.out.println("Tentando novamente em 2 segundos...");
                                buttonCadastrar.setText("Tentando novamente... (" + (tentativa + 1) + "/3)");
                                
                                buttonCadastrar.postDelayed(() -> {
                                    tentarCadastro(email, senha, nome, emailResponsavel, tentativa + 1);
                                }, 2000);
                            } else {
                                // Falha definitiva ou erro não relacionado à rede
                                mostrarCarregamento(false);
                                processarErroCadastro(exception);
                            }
                        }
                    }
                });
    }

    private void processarErroCadastro(Exception exception) {
        String errorMessage = "Erro no cadastro. Tente novamente.";
        
        if (exception != null) {
            String error = exception.getMessage();
            System.out.println("=== ERRO FIREBASE ===");
            System.out.println("Erro completo: " + error);
            System.out.println("Tipo de exceção: " + exception.getClass().getSimpleName());
            System.out.println("====================");
            
            if (error != null) {
                if (error.contains("email") && error.contains("use")) {
                    errorMessage = "❌ Este e-mail já está sendo usado por outra conta.";
                } else if (error.contains("weak-password")) {
                    errorMessage = "❌ A senha é muito fraca. Use pelo menos 6 caracteres.";
                } else if (error.contains("invalid-email")) {
                    errorMessage = "❌ O formato do e-mail é inválido.";
                } else if (error.contains("network") || error.contains("timeout") || 
                         error.contains("interrupted connection") || 
                         error.contains("unreachable host") ||
                         error.contains("reCAPTCHA")) {
                    errorMessage = "⚠️ Problema de conexão persistente.\n\n" +
                                 "• Verifique sua conexão com a internet\n" +
                                 "• Tente conectar via WiFi se estiver usando dados móveis\n" +
                                 "• Aguarde alguns minutos e tente novamente\n" +
                                 "• Se o problema persistir, tente mais tarde";
                    
                    // Habilitar botão de tentar novamente após 3 segundos
                    buttonCadastrar.postDelayed(() -> {
                        buttonCadastrar.setText("🔄 Tentar Novamente");
                        buttonCadastrar.setEnabled(true);
                    }, 3000);
                } else if (error.contains("too-many-requests")) {
                    errorMessage = "⏳ Muitas tentativas. Aguarde alguns minutos antes de tentar novamente.";
                }
            }
        }
        
        Toast.makeText(CadastroActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private boolean validarCampos(String nome, String email, String senha, String confirmarSenha, String emailResponsavel) {
        boolean isValid = true;

        // Validar nome
        if (nome.isEmpty()) {
            editTextNome.setError("Nome é obrigatório");
            editTextNome.requestFocus();
            isValid = false;
        }

        // Validar email
        if (email.isEmpty()) {
            editTextEmail.setError("E-mail é obrigatório");
            if (isValid) editTextEmail.requestFocus();
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Digite um e-mail válido");
            if (isValid) editTextEmail.requestFocus();
            isValid = false;
        }

        // Validar email do responsável (se preenchido)
        if (!emailResponsavel.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailResponsavel).matches()) {
            editTextEmailResponsavel.setError("Digite um e-mail válido para o responsável");
            if (isValid) editTextEmailResponsavel.requestFocus();
            isValid = false;
        }

        // Validar senha
        if (senha.isEmpty()) {
            editTextSenha.setError("Senha é obrigatória");
            if (isValid) editTextSenha.requestFocus();
            isValid = false;
        } else if (senha.length() < 6) {
            editTextSenha.setError("A senha deve ter pelo menos 6 caracteres");
            if (isValid) editTextSenha.requestFocus();
            isValid = false;
        }

        // Validar confirmação de senha
        if (confirmarSenha.isEmpty()) {
            editTextConfirmarSenha.setError("Confirmação de senha é obrigatória");
            if (isValid) editTextConfirmarSenha.requestFocus();
            isValid = false;
        } else if (!senha.equals(confirmarSenha)) {
            editTextConfirmarSenha.setError("As senhas não coincidem");
            if (isValid) editTextConfirmarSenha.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private void limparErros() {
        editTextNome.setError(null);
        editTextEmail.setError(null);
        editTextSenha.setError(null);
        editTextConfirmarSenha.setError(null);
        editTextEmailResponsavel.setError(null);
    }

    private void mostrarCarregamento(boolean mostrar) {
        if (mostrar) {
            progressBar.setVisibility(View.VISIBLE);
            buttonCadastrar.setEnabled(false);
            buttonCadastrar.setText("Cadastrando...");
        } else {
            progressBar.setVisibility(View.GONE);
            buttonCadastrar.setEnabled(true);
            buttonCadastrar.setText("Cadastrar");
        }
    }

    private boolean isNetworkAvailable() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                
                System.out.println("=== DEBUG CONECTIVIDADE ===");
                System.out.println("ConnectivityManager: " + (connectivityManager != null ? "OK" : "NULL"));
                System.out.println("ActiveNetworkInfo: " + (activeNetworkInfo != null ? "OK" : "NULL"));
                if (activeNetworkInfo != null) {
                    System.out.println("Tipo de rede: " + activeNetworkInfo.getTypeName());
                    System.out.println("Estado: " + activeNetworkInfo.getState());
                    System.out.println("Conectado: " + activeNetworkInfo.isConnected());
                }
                System.out.println("Resultado final: " + isConnected);
                System.out.println("===========================");
                
                return isConnected;
            }
            System.out.println("ConnectivityManager é null - assumindo conectado");
            return true; // Se não conseguir verificar, assume que está conectado
        } catch (Exception e) {
            System.out.println("Erro ao verificar conectividade: " + e.getMessage());
            return true; // Em caso de erro, assume que está conectado
        }
    }
}
