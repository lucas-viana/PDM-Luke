package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.content.Intent;
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
        
        // Criar ProgressBar programaticamente se não existir no layout
        progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.GONE);

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

        // Mostrar carregamento
        mostrarCarregamento(true);

        // Criar usuário no Firebase
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mostrarCarregamento(false);

                        if (task.isSuccessful()) {
                            // Cadastro bem-sucedido
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                // Salvar dados do usuário
                                preferencesManager.setUserName(nome);
                                preferencesManager.salvarUsuario(user.getUid(), email, emailResponsavel);

                                Toast.makeText(CadastroActivity.this,
                                        "Cadastro realizado com sucesso! Bem-vindo ao Luke Diário, " + nome + "!",
                                        Toast.LENGTH_LONG).show();

                                // Redirecionar para MainActivity
                                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            // Falha no cadastro
                            String errorMessage = "Erro no cadastro. Tente novamente.";
                            if (task.getException() != null) {
                                String error = task.getException().getMessage();
                                if (error != null) {
                                    if (error.contains("email") && error.contains("use")) {
                                        errorMessage = "Este e-mail já está sendo usado por outra conta.";
                                    } else if (error.contains("weak-password")) {
                                        errorMessage = "A senha é muito fraca. Use pelo menos 6 caracteres.";
                                    } else if (error.contains("invalid-email")) {
                                        errorMessage = "O formato do e-mail é inválido.";
                                    } else if (error.contains("network")) {
                                        errorMessage = "Erro de conexão. Verifique sua internet.";
                                    }
                                }
                            }
                            Toast.makeText(CadastroActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
}
