// app/src/main/java/br/edu/ifsuldeminas/mch/constraintlayouts/Adapter/AcaoPersonagemAdapter.java
package br.edu.ifsuldeminas.mch.constraintlayouts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.modelLayout.AcaoPersonagem;

public class AcaoPersonagemAdapter extends RecyclerView.Adapter<AcaoPersonagemAdapter.ViewHolder> {
    private final List<AcaoPersonagem> lista;

    public AcaoPersonagemAdapter(List<AcaoPersonagem> lista) {
        this.lista = lista;
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
        holder.imageView.setImageResource(acao.imagemResId);
        holder.button.setText(acao.nomeAcao);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        Button button;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewAcao);
            button = itemView.findViewById(R.id.buttonAcao);
        }
    }
}