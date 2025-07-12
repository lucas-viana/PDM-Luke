package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.adapter.AcaoPersonagemAdapter;
import br.edu.ifsuldeminas.mch.constraintlayouts.modelLayout.AcaoPersonagem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura a Toolbar personalizada
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewAcoes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<AcaoPersonagem> lista = Arrays.asList(
            new AcaoPersonagem(R.drawable.luke_banho, "Banho"),
            new AcaoPersonagem(R.drawable.luke_agua, "Água"),
            new AcaoPersonagem(R.drawable.luke_celular, "Celular")
        );

        recyclerView.setAdapter(new AcaoPersonagemAdapter(lista));
    }
}