package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.utils.NotificationManager;
import br.edu.ifsuldeminas.mch.constraintlayouts.utils.PreferencesManager;

public class ConfiguracoesActivity extends AppCompatActivity {
    
    private PreferencesManager preferencesManager;
    private NotificationManager notificationManager;
    private SwitchMaterial switchNotificacoes;
    private SwitchMaterial switchAltoContraste;
    private MaterialCardView cardPerfil;
    private Button buttonSalvarConfig;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        
        preferencesManager = new PreferencesManager(this);
        notificationManager = new NotificationManager(this);
        
        initializeViews();
        setupEventListeners();
        loadUserPreferences();
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Configurações");
        }
    }
    
    private void initializeViews() {
        switchNotificacoes = findViewById(R.id.switchNotificacoes);
        switchAltoContraste = findViewById(R.id.switchAltoContraste);
        cardPerfil = findViewById(R.id.cardPerfil);
        buttonSalvarConfig = findViewById(R.id.buttonSalvarConfig);
    }
    
    private void setupEventListeners() {
        switchNotificacoes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, 
                isChecked ? "Notificações ativadas ✅" : "Notificações desativadas ❌", 
                Toast.LENGTH_SHORT).show();
        });
        
        switchAltoContraste.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, 
                isChecked ? "Alto contraste ativado ✅" : "Alto contraste desativado ❌", 
                Toast.LENGTH_SHORT).show();
        });
        
        cardPerfil.setOnClickListener(v -> {
            Toast.makeText(this, "🧑‍💼 Configurações de perfil - Em desenvolvimento", Toast.LENGTH_LONG).show();
        });
        
        buttonSalvarConfig.setOnClickListener(v -> {
            salvarConfiguracoes();
        });
    }
    
    private void loadUserPreferences() {
        // Carregar preferências salvas
        switchNotificacoes.setChecked(preferencesManager.getNotificationsEnabled());
        switchAltoContraste.setChecked(preferencesManager.getHighContrastEnabled());
    }
    
    private void salvarConfiguracoes() {
        // Salvar configurações
        preferencesManager.setNotificationsEnabled(switchNotificacoes.isChecked());
        preferencesManager.setHighContrastEnabled(switchAltoContraste.isChecked());
        
        Toast.makeText(this, "💾 Configurações salvas com sucesso!", Toast.LENGTH_SHORT).show();
        
        // Retornar para a tela principal
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
