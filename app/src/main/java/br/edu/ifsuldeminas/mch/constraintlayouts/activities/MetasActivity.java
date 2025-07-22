package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.adapter.MetaAdapter;
import br.edu.ifsuldeminas.mch.constraintlayouts.dao.MetaDAO;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.Meta;

public class MetasActivity extends AppCompatActivity {

    private MetaDAO metaDAO;
    private MetaAdapter adapter;
    private RecyclerView recyclerViewMetas;
    private FloatingActionButton fabAdicionarMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

        metaDAO = new MetaDAO();
        recyclerViewMetas = findViewById(R.id.recyclerViewMetas);
        fabAdicionarMeta = findViewById(R.id.fabAdicionarMeta);

        setupRecyclerView();

        fabAdicionarMeta.setOnClickListener(v -> abrirDialogoMeta(null));
    }

    private void setupRecyclerView() {
        Query query = metaDAO.listarMetas();
        FirestoreRecyclerOptions<Meta> options = new FirestoreRecyclerOptions.Builder<Meta>()
                .setQuery(query, Meta.class)
                .build();

        adapter = new MetaAdapter(options, this::abrirDialogoMeta, this::deletarMeta);
        recyclerViewMetas.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMetas.setAdapter(adapter);
    }

    private void abrirDialogoMeta(Meta meta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_meta, null);
        builder.setView(dialogView);

        EditText editTextTitulo = dialogView.findViewById(R.id.editTextTituloMeta);
        EditText editTextDescricao = dialogView.findViewById(R.id.editTextDescricaoMeta);
        Button buttonSalvar = dialogView.findViewById(R.id.buttonSalvarMeta);

        if (meta != null) {
            editTextTitulo.setText(meta.getTitulo());
            editTextDescricao.setText(meta.getDescricao());
        }

        AlertDialog dialog = builder.create();

        buttonSalvar.setOnClickListener(v -> {
            String titulo = editTextTitulo.getText().toString().trim();
            String descricao = editTextDescricao.getText().toString().trim();

            if (titulo.isEmpty()) {
                Toast.makeText(this, "O título da meta é obrigatório", Toast.LENGTH_SHORT).show();
                return;
            }

            if (meta == null) { // Criar nova meta
                metaDAO.criarMeta(new Meta(titulo, descricao))
                        .addOnSuccessListener(aVoid -> Toast.makeText(this, "Meta criada!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Erro ao criar meta", Toast.LENGTH_SHORT).show());
            } else { // Atualizar meta existente
                meta.setTitulo(titulo);
                meta.setDescricao(descricao);
                metaDAO.atualizarMeta(meta.getId(), meta)
                        .addOnSuccessListener(aVoid -> Toast.makeText(this, "Meta atualizada!", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(this, "Erro ao atualizar meta", Toast.LENGTH_SHORT).show());
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deletarMeta(String metaId) {
        metaDAO.deletarMeta(metaId)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Meta deletada!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Erro ao deletar meta", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
