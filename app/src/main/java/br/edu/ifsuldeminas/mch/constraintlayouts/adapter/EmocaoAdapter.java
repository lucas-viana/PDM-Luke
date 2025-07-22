package br.edu.ifsuldeminas.mch.constraintlayouts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.EmocaoModel;

public class EmocaoAdapter extends RecyclerView.Adapter<EmocaoAdapter.ViewHolder> {
    private List<EmocaoModel> emocoes;
    private Context context;

    public EmocaoAdapter(List<EmocaoModel> emocoes, Context context) {
        this.emocoes = emocoes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_emocao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmocaoModel emocao = emocoes.get(position);
        
        holder.textViewEmocao.setText(emocao.getEmocao());
        holder.textViewData.setText(emocao.getData());
        holder.textViewDescricao.setText(emocao.getDescricao());
        holder.textViewIntensidade.setText("Intensidade: " + emocao.getNivelIntensidade() + "/5");
        
        // Define ícone baseado na emoção
        int iconResource = getEmocaoIcon(emocao.getEmocao());
        holder.imageViewEmocao.setImageResource(iconResource);
        
        // Define cor baseada na intensidade
        int color = getIntensidadeColor(emocao.getNivelIntensidade());
        holder.textViewIntensidade.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return emocoes.size();
    }

    private int getEmocaoIcon(String emocao) {
        switch (emocao) {
            case "Feliz": return R.drawable.ic_sentiment_happy;
            case "Triste": return R.drawable.ic_sentiment_sad;
            case "Raiva": return R.drawable.ic_sentiment_angry;
            case "Medo": return R.drawable.ic_sentiment_scared;
            case "Calmo": return R.drawable.ic_sentiment_calm;
            default: return R.drawable.ic_sentiment_neutral;
        }
    }
    
    private int getIntensidadeColor(int intensidade) {
        switch (intensidade) {
            case 1: return context.getColor(R.color.intensity_low);
            case 2: return context.getColor(R.color.intensity_medium_low);
            case 3: return context.getColor(R.color.intensity_medium);
            case 4: return context.getColor(R.color.intensity_medium_high);
            case 5: return context.getColor(R.color.intensity_high);
            default: return context.getColor(R.color.black);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewEmocao;
        TextView textViewEmocao;
        TextView textViewData;
        TextView textViewDescricao;
        TextView textViewIntensidade;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewEmocao = itemView.findViewById(R.id.imageViewEmocao);
            textViewEmocao = itemView.findViewById(R.id.textViewEmocao);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewIntensidade = itemView.findViewById(R.id.textViewIntensidade);
        }
    }
}
