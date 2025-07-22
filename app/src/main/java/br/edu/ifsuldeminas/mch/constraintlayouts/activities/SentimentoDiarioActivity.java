package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.dao.EmocaoDAO;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.EmocaoModel;
import br.edu.ifsuldeminas.mch.constraintlayouts.adapter.EmocaoAdapter;

public class SentimentoDiarioActivity extends AppCompatActivity {
    private RadioGroup radioGroupEmocoes;
    private EditText editTextDescricao;
    private SeekBar seekBarIntensidade;
    private TextView textViewIntensidade;
    private Button buttonSalvar;
    private RecyclerView recyclerViewEmocoes;
    private EmocaoDAO emocaoDAO;
    private EmocaoAdapter emocaoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentimento_diario);
        
        // Configura toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_sentimento);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Sentimento do Dia");
        }
        
        inicializarComponentes();
        configurarEventos();
        emocaoDAO = new EmocaoDAO(this);
        carregarEmocoes();
    }
    
    private void inicializarComponentes() {
        radioGroupEmocoes = findViewById(R.id.radioGroupEmocoes);
        editTextDescricao = findViewById(R.id.editTextDescricao);
        seekBarIntensidade = findViewById(R.id.seekBarIntensidade);
        textViewIntensidade = findViewById(R.id.textViewIntensidade);
        buttonSalvar = findViewById(R.id.buttonSalvar);
        recyclerViewEmocoes = findViewById(R.id.recyclerViewEmocoes);
        
        // Configura RecyclerView
        recyclerViewEmocoes.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void configurarEventos() {
        seekBarIntensidade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String intensidade = "Intensidade: " + (progress + 1) + "/5";
                textViewIntensidade.setText(intensidade);
            }
            
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
        buttonSalvar.setOnClickListener(this::salvarEmocao);
    }
    
    private void salvarEmocao(View view) {
        int selectedEmocaoId = radioGroupEmocoes.getCheckedRadioButtonId();
        if (selectedEmocaoId == -1) {
            Toast.makeText(this, "Selecione uma emoção", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String emocao = getEmocaoSelecionada(selectedEmocaoId);
        String descricao = editTextDescricao.getText().toString().trim();
        int intensidade = seekBarIntensidade.getProgress() + 1;
        String dataAtual = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        
        EmocaoModel emocaoModel = new EmocaoModel(dataAtual, emocao, descricao, intensidade);
        long id = emocaoDAO.inserirEmocao(emocaoModel);
        
        if (id > 0) {
            Snackbar.make(view, "Emoção salva com sucesso!", Snackbar.LENGTH_LONG).show();
            limparFormulario();
            carregarEmocoes();
        } else {
            Toast.makeText(this, "Erro ao salvar emoção", Toast.LENGTH_SHORT).show();
        }
    }
    
    private String getEmocaoSelecionada(int selectedId) {
        if (selectedId == R.id.radioFeliz) return "Feliz";
        if (selectedId == R.id.radioTriste) return "Triste";
        if (selectedId == R.id.radioRaiva) return "Raiva";
        if (selectedId == R.id.radioMedo) return "Medo";
        if (selectedId == R.id.radioCalmo) return "Calmo";
        return "Neutro";
    }
    
    private void limparFormulario() {
        radioGroupEmocoes.clearCheck();
        editTextDescricao.setText("");
        seekBarIntensidade.setProgress(0);
        textViewIntensidade.setText("Intensidade: 1/5");
    }
    
    private void carregarEmocoes() {
        List<EmocaoModel> emocoes = emocaoDAO.listarEmocoes();
        emocaoAdapter = new EmocaoAdapter(emocoes, this);
        recyclerViewEmocoes.setAdapter(emocaoAdapter);
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
