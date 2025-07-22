package br.edu.ifsuldeminas.mch.constraintlayouts.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    
    private List<Post> posts;
    private Context context;
    private OnPostClickListener listener;

    // Interface para cliques nos posts
    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    public PostAdapter(List<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    public void setOnPostClickListener(OnPostClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        
        holder.textViewId.setText("Post #" + post.getId());
        holder.textViewTitle.setText(post.getTitle());
        holder.textViewBody.setText(post.getBody());
        
        // Clique no item
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPostClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts != null ? posts.size() : 0;
    }

    // Método para atualizar a lista
    public void updatePosts(List<Post> newPosts) {
        this.posts = newPosts;
        notifyDataSetChanged();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewId;
        TextView textViewTitle;
        TextView textViewBody;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.textViewPostId);
            textViewTitle = itemView.findViewById(R.id.textViewPostTitle);
            textViewBody = itemView.findViewById(R.id.textViewPostBody);
        }
    }
}
