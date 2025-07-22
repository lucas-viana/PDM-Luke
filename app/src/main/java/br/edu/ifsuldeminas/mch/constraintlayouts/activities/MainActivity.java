package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.adapter.AcaoPersonagemAdapter;
import br.edu.ifsuldeminas.mch.constraintlayouts.modelLayout.AcaoPersonagem;
import br.edu.ifsuldeminas.mch.constraintlayouts.utils.PreferencesManager;
import br.edu.ifsuldeminas.mch.constraintlayouts.utils.NotificationManager;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_CODE = 1000;
    
    private PreferencesManager preferencesManager;
    private FirebaseAuth firebaseAuth;
    private TextView textViewBoasVindas;
    private NotificationManager notificationManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        preferencesManager = new PreferencesManager(this);
        
        // Inicializar o gerenciador de notificações
        notificationManager = new NotificationManager(this);
        
        // Verificar se usuário está logado
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            // Usuário não está logado, redirecionar para login
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        setupViews();
        setupRecyclerView();
        setupFab();
        solicitarPermissaoNotificacao();
        
        // Mostra Snackbar de boas-vindas para usuários autistas
        if (preferencesManager.isFirstTime()) {
            Snackbar.make(findViewById(R.id.main_layout), 
                "🌟 Bem-vindo! Vamos organizar seu dia juntos! 🌟", 
                Snackbar.LENGTH_LONG).show();
            preferencesManager.setFirstTime(false);
        }
    }
    
    private void setupViews() {
        // Configura a Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Configura texto de boas-vindas personalizado
        textViewBoasVindas = findViewById(R.id.textViewBoasVindas);
        
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String nomeUsuario = preferencesManager.getUserName();
        if (user != null && (nomeUsuario.equals("Usuário") || nomeUsuario.isEmpty())) {
            nomeUsuario = user.getDisplayName();
            if (nomeUsuario == null || nomeUsuario.isEmpty()) {
                nomeUsuario = user.getEmail().split("@")[0];
            }
            preferencesManager.setUserName(nomeUsuario);
        }
        
        textViewBoasVindas.setText("Olá, " + nomeUsuario + "! 😊\nVamos organizar seu dia?");
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Luke - Seu Assistente Diário");
        }
    }
    
    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAcoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lista completa de ações do Luke para educação assistiva
        List<AcaoPersonagem> lista = Arrays.asList(
            // Atividades de Autocuidado (Importantes)
            new AcaoPersonagem(R.drawable.luke_banho, "Tomar Banho", 
                "Hora de se limpar e se sentir bem", "Autocuidado", true),
            new AcaoPersonagem(R.drawable.luke_dente, "Escovar os Dentes", 
                "Cuidar da higiene dental", "Autocuidado", true),
            new AcaoPersonagem(R.drawable.luke_agua, "Beber Água", 
                "Manter-se hidratado é importante", "Autocuidado", true),
            new AcaoPersonagem(R.drawable.luke_comer, "Fazer Refeição", 
                "Hora de se alimentar bem", "Autocuidado", true),
            
            // Atividades de Rotina Diária
            new AcaoPersonagem(R.drawable.luke_dormir, "Dormir", 
                "Hora do descanso e sono reparador", "Rotina"),
            new AcaoPersonagem(R.drawable.luke_home, "Atividades em Casa", 
                "Organizar e cuidar do ambiente", "Rotina"),
            new AcaoPersonagem(R.drawable.luke_estudar, "Estudar", 
                "Aprender coisas novas", "Educação"),
            
            // Atividades de Lazer e Recreação
            new AcaoPersonagem(R.drawable.luke_brincando, "Brincadeiras", 
                "Atividades divertidas", "Lazer"),
            new AcaoPersonagem(R.drawable.luke_bola, "Jogar Bola", 
                "Atividade física com bola", "Exercício"),
            new AcaoPersonagem(R.drawable.luke_bicicleta, "Andar de Bicicleta", 
                "Exercitar-se pedalando", "Exercício"),
            
            // Tecnologia e Comunicação
            new AcaoPersonagem(R.drawable.luke_celular, "Usar Celular", 
                "Tempo controlado com tecnologia", "Tecnologia"),
            
            // Emoções e Sentimentos
            new AcaoPersonagem(R.drawable.luke_feliz, "Estar Feliz", 
                "Reconhecer momentos de alegria", "Emoções"),
            new AcaoPersonagem(R.drawable.luke_triste, "Estar Triste", 
                "É normal se sentir triste às vezes", "Emoções"),
            new AcaoPersonagem(R.drawable.luke_bravo, "Estar Bravo", 
                "Identificar e controlar a raiva", "Emoções"),
            
            // Situações Especiais
            new AcaoPersonagem(R.drawable.luke_doente, "Não se Sentir Bem", 
                "Quando não estamos bem de saúde", "Saúde"),
            new AcaoPersonagem(R.drawable.luke_barulho, "Lidar com Barulho", 
                "Estratégias para ambientes barulhentos", "Sensorial"),
            new AcaoPersonagem(R.drawable.luke_barriga, "Dor de Barriga", 
                "Como lidar com desconfortos", "Saúde"),
            
            // Interação Social
            new AcaoPersonagem(R.drawable.luke_maos, "Interação Social", 
                "Relacionar-se com outras pessoas", "Social"),
            new AcaoPersonagem(R.drawable.luke_boca, "Comunicação", 
                "Expressar-se e comunicar-se", "Comunicação"),
            
            // Funcionalidade Principal
            new AcaoPersonagem(R.drawable.ic_sentiment_happy, "Registro de Emoções", 
                "Anotar como me sinto hoje", "Principal", true)
        );

        AcaoPersonagemAdapter adapter = new AcaoPersonagemAdapter(lista, this);
        recyclerView.setAdapter(adapter);
    }
    
    private void setupFab() {
        FloatingActionButton fab = findViewById(R.id.fabSentimento);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, SentimentoDiarioActivity.class));
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ConfiguracoesActivity.class));
            return true;
        } else if (id == R.id.action_share) {
            shareApp();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "Luke Diário v1.0 - Educação Assistiva para Autismo", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_sentimento_diario) {
            startActivity(new Intent(this, SentimentoDiarioActivity.class));
            return true;
        } else if (id == R.id.action_galeria) {
            startActivity(new Intent(this, GaleriaAcoesActivity.class));
            return true;
        } else if (id == R.id.action_metas) {
            startActivity(new Intent(this, MetasActivity.class));
            return true;
        } else if (id == R.id.action_dica_do_dia) {
            startActivity(new Intent(this, DicaDoDiaActivity.class));
            return true;
        } else if (id == R.id.action_posts) {
            startActivity(new Intent(this, PostsActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            logout();
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Luke Diário de Emoções");
        shareIntent.putExtra(Intent.EXTRA_TEXT, 
            "Descobri este aplicativo incrível para educação assistiva! 🌟\n\n" +
            "Luke Diário de Emoções ajuda pessoas com autismo a organizar suas atividades diárias " +
            "e compreender suas emoções de forma visual e intuitiva.\n\n" +
            "Experimente você também! 😊");
        startActivity(Intent.createChooser(shareIntent, "Compartilhar Luke Diário"));
    }
    
    private void logout() {
        firebaseAuth.signOut();
        Toast.makeText(this, "Logout realizado com sucesso", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void solicitarPermissaoNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) 
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, 
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, 
                    NOTIFICATION_PERMISSION_CODE);
            } else {
                configurarNotificacoes();
            }
        } else {
            configurarNotificacoes();
        }
    }

    private void configurarNotificacoes() {
        notificationManager.scheduleDaily19hNotification();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                configurarNotificacoes();
                Toast.makeText(this, "Lembretes diários ativados!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissão negada. Lembretes não serão exibidos.", Toast.LENGTH_LONG).show();
            }
        }
    }
}