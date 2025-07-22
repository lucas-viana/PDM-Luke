package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class LoginActivity extends AppCompatActivity {
    
    private EditText editTextUsuario, editTextSenha;
    private Button buttonLogin, buttonCadastro;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private PreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        preferencesManager = new PreferencesManager(this);
        
        // Verificar se usuário já está logado
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Usuário já está logado, redirecionar para MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        initializeViews();
        setupEventListeners();
    }
    
    private void initializeViews() {
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCadastro = findViewById(R.id.buttonCadastro);
        
        // Verificar se os views foram encontrados
        if (editTextUsuario == null) {
            System.err.println("ERRO: editTextUsuario não encontrado!");
        }
        if (editTextSenha == null) {
            System.err.println("ERRO: editTextSenha não encontrado!");
        }
        if (buttonCadastro == null) {
            System.err.println("ERRO: buttonCadastro não encontrado!");
        }
        
        // Adicionar ProgressBar programaticamente se não existir
        progressBar = new ProgressBar(this);
        progressBar.setVisibility(View.GONE);
        
        // Limpar erros ao digitar
        editTextUsuario.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editTextUsuario.setError(null);
            }
        });
        
        editTextSenha.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editTextSenha.setError(null);
            }
        });
    }
    
    private void setupEventListeners() {
        buttonLogin.setOnClickListener(v -> realizarLogin());
        buttonCadastro.setOnClickListener(v -> {
            // Navegar para a tela de cadastro
            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(intent);
        });
    }
    
    private void realizarLogin() {
        String email = editTextUsuario.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();
        
        if (!validarCampos(email, senha)) {
            return;
        }
        
        mostrarCarregamento(true);
        
        firebaseAuth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mostrarCarregamento(false);
                    
                    if (task.isSuccessful()) {
                        // Login bem-sucedido
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // Salvar informações do usuário
                            String nomeUsuario = user.getDisplayName();
                            if (nomeUsuario == null || nomeUsuario.isEmpty()) {
                                nomeUsuario = user.getEmail().split("@")[0];
                            }
                            preferencesManager.setUserName(nomeUsuario);
                        }
                        
                        Toast.makeText(LoginActivity.this, 
                            "Login realizado com sucesso! Bem-vindo ao Luke Diário!", 
                            Toast.LENGTH_LONG).show();
                        
                        // Redirecionar para MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        
                    } else {
                        // Falha no login
                        String errorMessage = "Erro no login. Verifique suas credenciais.";
                        if (task.getException() != null) {
                            String error = task.getException().getMessage();
                            if (error != null) {
                                if (error.contains("password")) {
                                    errorMessage = "Senha incorreta. Tente novamente.";
                                } else if (error.contains("user") || error.contains("email")) {
                                    errorMessage = "Usuário não encontrado. Verifique o e-mail.";
                                } else if (error.contains("network")) {
                                    errorMessage = "Erro de conexão. Verifique sua internet.";
                                }
                            }
                        }
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
    
    private boolean validarCampos(String email, String senha) {
        if (email.isEmpty()) {
            editTextUsuario.setError("E-mail é obrigatório");
            editTextUsuario.requestFocus();
            return false;
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUsuario.setError("Digite um e-mail válido");
            editTextUsuario.requestFocus();
            return false;
        }
        
        if (senha.isEmpty()) {
            editTextSenha.setError("Senha é obrigatória");
            editTextSenha.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void mostrarCarregamento(boolean mostrar) {
        if (mostrar) {
            buttonLogin.setEnabled(false);
            buttonCadastro.setEnabled(false);
            buttonLogin.setText("Carregando...");
        } else {
            buttonLogin.setEnabled(true);
            buttonCadastro.setEnabled(true);
            buttonLogin.setText("Entrar");
        }
    }
}