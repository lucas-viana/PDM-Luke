package br.edu.ifsuldeminas.mch.constraintlayouts.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.Meta;

public class MetaAdapter extends FirestoreRecyclerAdapter<Meta, MetaAdapter.MetaViewHolder> {

    private final OnItemClickListener listener;
    private final OnItemDeleteListener deleteListener;

    public interface OnItemClickListener {
        void onItemClick(Meta meta);
    }

    public interface OnItemDeleteListener {
        void onItemDelete(String metaId);
    }

    public MetaAdapter(@NonNull FirestoreRecyclerOptions<Meta> options, OnItemClickListener listener, OnItemDeleteListener deleteListener) {
        super(options);
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull MetaViewHolder holder, int position, @NonNull Meta model) {
        model.setId(getSnapshots().getSnapshot(position).getId());
        holder.bind(model, listener);
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta, parent, false);
        return new MetaViewHolder(view);
    }

    class MetaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewDescricao;
        CheckBox checkBoxConcluida;

        public MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTituloMeta);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricaoMeta);
            checkBoxConcluida = itemView.findViewById(R.id.checkBoxMetaConcluida);

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    deleteListener.onItemDelete(getItem(position).getId());
                }
                return true;
            });
        }

        public void bind(final Meta meta, final OnItemClickListener listener) {
            textViewTitulo.setText(meta.getTitulo());
            textViewDescricao.setText(meta.getDescricao());
            checkBoxConcluida.setChecked(meta.isConcluida());
            itemView.setOnClickListener(v -> listener.onItemClick(meta));
        }
    }
}
