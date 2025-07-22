package br.edu.ifsuldeminas.mch.constraintlayouts.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.activities.SentimentoDiarioActivity;
import br.edu.ifsuldeminas.mch.constraintlayouts.activities.GaleriaAcoesActivity;
import br.edu.ifsuldeminas.mch.constraintlayouts.modelLayout.AcaoPersonagem;
import br.edu.ifsuldeminas.mch.constraintlayouts.utils.PreferencesManager;

public class AcaoPersonagemAdapter extends RecyclerView.Adapter<AcaoPersonagemAdapter.ViewHolder> {
    private final List<AcaoPersonagem> lista;
    private final Context context;
    private final PreferencesManager preferencesManager;

    public AcaoPersonagemAdapter(List<AcaoPersonagem> lista, Context context) {
        this.lista = lista;
        this.context = context;
        this.preferencesManager = new PreferencesManager(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.activity_item_acao_personagem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AcaoPersonagem acao = lista.get(position);
        holder.imageView.setImageResource(acao.getImagemId());
        holder.textViewTitulo.setText(acao.getTitulo());
        holder.textViewDescricao.setText(acao.getDescricao());
        holder.textViewCategoria.setText(acao.getCategoria());
        
        // Aplicar estilo visual para ações importantes
        if (acao.isImportante()) {
            holder.iconImportancia.setVisibility(View.VISIBLE);
            holder.iconImportancia.setImageResource(R.drawable.ic_star_important);
        } else {
            holder.iconImportancia.setVisibility(View.GONE);
        }
        
        // Configurar cores da categoria
        int corCategoria = getCategoriaColor(acao.getCategoria());
        holder.textViewCategoria.setTextColor(ContextCompat.getColor(context, corCategoria));
        
        // Clique no item inteiro
        holder.itemView.setOnClickListener(v -> {
            AcaoPersonagem acaoClicada = lista.get(position);
            String responsibleEmail = preferencesManager.getResponsibleEmail();
            String userName = preferencesManager.getUserName();

            if (responsibleEmail != null && !responsibleEmail.isEmpty()) {
                // Salvar a ação no Firestore para acionar a Cloud Function
                salvarAcaoParaNotificar(acaoClicada, responsibleEmail, userName);
            } else {
                Toast.makeText(context, "Ação registrada. E-mail do responsável não configurado para notificação.", Toast.LENGTH_LONG).show();
            }
        });
        
        // Registra menu de contexto
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Opções para " + acao.getTitulo());
                menu.add(0, 1, 0, "✅ Marcar como Concluída");
                menu.add(0, 2, 0, "📝 Adicionar Nota");
                menu.add(0, 3, 0, "⭐ Marcar como Favorita");
                menu.add(0, 4, 0, "📅 Agendar Lembrete");
                menu.add(0, 5, 0, "📤 Compartilhar Progresso");
            }
        });
    }
    
    private void executeAction(AcaoPersonagem acao) {
        if (acao.getTitulo().contains("Registro de Emoções") || 
            acao.getTitulo().contains("Sentimento")) {
            context.startActivity(new Intent(context, SentimentoDiarioActivity.class));
        } else {
            // Para outras ações, mostrar feedback positivo específico para autismo
            String mensagem = "✅ " + acao.getTitulo() + " - " + acao.getDescricao();
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
        }
    }

    private int getCategoriaColor(String categoria) {
        switch (categoria) {
            case "Autocuidado":
                return R.color.design_default_color_primary;
            case "Emoções":
                return R.color.marrom_01;
            case "Exercício":
                return R.color.verde_folha;
            case "Educação":
                return R.color.azul_claro;
            case "Lazer":
                return R.color.design_default_color_secondary;
            case "Saúde":
                return R.color.design_default_color_error;
            case "Principal":
                return R.color.amarelo_01;
            default:
                return R.color.cinza_medio;
        }
    }

    private void salvarAcaoParaNotificar(AcaoPersonagem acao, String emailResponsavel, String nomeUsuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        
        // Usando um Map para mais flexibilidade com a Cloud Function
        Map<String, Object> registro = new HashMap<>();
        registro.put("tituloAcao", acao.getTitulo());
        registro.put("descricaoAcao", acao.getDescricao());
        registro.put("categoriaAcao", acao.getCategoria());
        registro.put("nomeUsuario", nomeUsuario);
        registro.put("emailResponsavel", emailResponsavel);
        registro.put("timestamp", new Date());

        db.collection("acoesRealizadas")
            .add(registro)
            .addOnSuccessListener(documentReference -> 
                Toast.makeText(context, "Ação registrada! O responsável será notificado.", Toast.LENGTH_SHORT).show())
            .addOnFailureListener(e -> 
                Toast.makeText(context, "Erro ao registrar ação. Tente novamente.", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView imageView;
        TextView textViewTitulo;
        TextView textViewDescricao;
        TextView textViewCategoria;
        ImageView iconImportancia;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAcao);
            textViewTitulo = itemView.findViewById(R.id.textViewAcao);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewCategoria = itemView.findViewById(R.id.textViewCategoria);
            iconImportancia = itemView.findViewById(R.id.imageViewIndicadorImportante);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // Implementado no onBindViewHolder
        }
    }
}