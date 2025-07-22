package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;

public class DicaDoDiaActivity extends AppCompatActivity {

    private static final String TAG = "DicaDoDiaActivity";
    
    private TextView textViewDica;
    private TextView textViewAutor;
    private MaterialButton buttonNovaDica;
    private ProgressBar progressBar;
    
    // Array de dicas motivacionais estáticas
    private final String[] DICAS_MOTIVACIONAIS = {
        "Cada pequeno passo é uma grande conquista! 🌟",
        "Você é único e especial do seu próprio jeito! 💫",
        "A rotina traz segurança e tranquilidade. 📅",
        "Está tudo bem sentir emoções diferentes. 💙",
        "Seus interesses especiais são tesouros valiosos! 💎",
        "Você tem o direito de ser compreendido e respeitado. 🤝",
        "Pequenas vitórias merecem grandes celebrações! 🎉",
        "Sua perspectiva única torna o mundo mais rico. 🌈",
        "É normal precisar de tempo para processar as coisas. ⏰",
        "Você merece paciência, carinho e compreensão. ❤️",
        "Sua sensibilidade é uma força, não uma fraqueza. 💪",
        "Cada dia é uma nova oportunidade de aprender. 📚",
        "Você tem o direito de expressar suas necessidades. 🗣️",
        "Suas habilidades especiais fazem diferença no mundo. ⭐",
        "É importante respeitar seus próprios limites. 🛡️",
        "Você é capaz de coisas incríveis! 🚀",
        "Sua criatividade não tem limites. 🎨",
        "Pequenos momentos de felicidade são preciosos. ☀️",
        "Você tem o direito de ser aceito como é. 🏠",
        "Sua determinação é inspiradora. 🏆",
        "É normal precisar de pausas e descanso. 😌",
        "Você traz alegria especial para quem te conhece. 😊",
        "Suas conquistas diárias são motivo de orgulho. 🌱",
        "Você tem um coração cheio de amor para dar. 💝",
        "Sua presença faz diferença na vida das pessoas. 🌺",
        "É importante celebrar quem você é! 🎊",
        "Você é forte, corajoso e resiliente. 🦋",
        "Sua curiosidade é um dom especial. 🔍",
        "Cada desafio superado te torna mais forte. 💪",
        "Você merece todo o amor e apoio do mundo. 🌍"
    };
    
    private final String[] AUTORES = {
        "Equipe Luke",
        "Luke - Seu Assistente",
        "Comunidade Inclusiva",
        "Amigos do Luke",
        "Família Luke",
        "Apoio Especializado",
        "Educação Inclusiva",
        "Rede de Apoio"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dica_do_dia);

        inicializarComponentes();
        configurarToolbar();
        carregarDicaDoDia();
    }

    private void inicializarComponentes() {
        textViewDica = findViewById(R.id.textViewDica);
        textViewAutor = findViewById(R.id.textViewAutor);
        buttonNovaDica = findViewById(R.id.buttonNovaDica);
        progressBar = findViewById(R.id.progressBar);

        buttonNovaDica.setOnClickListener(v -> {
            // Carregar nova dica aleatória
            carregarDicaDoDia();
        });
    }

    private void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void carregarDicaDoDia() {
        // Mostrar loading brevemente para feedback visual
        mostrarLoading(true);
        
        // Gerar dica aleatória
        Random random = new Random();
        int indiceDica = random.nextInt(DICAS_MOTIVACIONAIS.length);
        int indiceAutor = random.nextInt(AUTORES.length);
        
        String dicaSelecionada = DICAS_MOTIVACIONAIS[indiceDica];
        String autorSelecionado = AUTORES[indiceAutor];
        
        // Simular pequeno delay para feedback visual
        textViewDica.postDelayed(() -> {
            mostrarLoading(false);
            exibirDica(dicaSelecionada, autorSelecionado);
        }, 300); // 300ms para feedback visual suave
        
        Log.d(TAG, "Nova dica carregada: " + dicaSelecionada);
    }

    private void exibirDica(String texto, String autor) {
        textViewDica.setText(texto);
        textViewAutor.setText("- " + autor);
        
        // Animação sutil para feedback visual
        textViewDica.setAlpha(0f);
        textViewAutor.setAlpha(0f);
        
        textViewDica.animate()
                .alpha(1f)
                .setDuration(500)
                .start();
                
        textViewAutor.animate()
                .alpha(1f)
                .setDuration(500)
                .setStartDelay(200)
                .start();
    }

    private void mostrarLoading(boolean mostrar) {
        if (mostrar) {
            progressBar.setVisibility(View.VISIBLE);
            buttonNovaDica.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            buttonNovaDica.setEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
