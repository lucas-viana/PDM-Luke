package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import br.edu.ifsuldeminas.mch.constraintlayouts.R;
import br.edu.ifsuldeminas.mch.constraintlayouts.adapter.PostAdapter;
import br.edu.ifsuldeminas.mch.constraintlayouts.api.PostApiService;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.Post;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostsActivity extends AppCompatActivity {
    
    private static final String TAG = "PostsActivity";
    
    // 🔗 CONFIGURADO: URL do GitHub raw do lucas-viana
    private static final String GITHUB_RAW_BASE_URL = "https://raw.githubusercontent.com/";
    
    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private ProgressBar progressBar;
    private TextView textViewStatus;
    private MaterialButton buttonRecarregar;
    private PostApiService postApiService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        
        configurarToolbar();
        inicializarViews();
        configurarRetrofit();
        configurarRecyclerView();
        configurarEventos();
        
        // Carregar posts automaticamente
        carregarPosts();
    }
    
    private void configurarToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("📝 Posts do GitHub");
        }
    }
    
    private void inicializarViews() {
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        progressBar = findViewById(R.id.progressBar);
        textViewStatus = findViewById(R.id.textViewStatus);
        buttonRecarregar = findViewById(R.id.buttonRecarregar);
    }
    
    private void configurarRetrofit() {
        // Cliente HTTP com timeout configurado
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        
        // Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GITHUB_RAW_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        
        postApiService = retrofit.create(PostApiService.class);
        Log.d(TAG, "Retrofit configurado para: " + GITHUB_RAW_BASE_URL);
    }
    
    private void configurarRecyclerView() {
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));
        
        // Inicializar adapter com lista vazia
        postAdapter = new PostAdapter(new ArrayList<>(), this);
        recyclerViewPosts.setAdapter(postAdapter);
        
        // Configurar clique nos posts
        postAdapter.setOnPostClickListener(post -> {
            String mensagem = "📝 " + post.getTitle() + "\n\n" + post.getBody();
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
        });
    }
    
    private void configurarEventos() {
        buttonRecarregar.setOnClickListener(v -> carregarPosts());
    }
    
    private void carregarPosts() {
        mostrarLoading(true);
        atualizarStatus("🔄 Conectando ao GitHub do lucas-viana...");
        
        Log.d(TAG, "Carregando posts de: https://raw.githubusercontent.com/lucas-viana/luke-posts/refs/heads/main/posts.json");
        
        Call<List<Post>> call = postApiService.obterPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                mostrarLoading(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> posts = response.body();
                    Log.d(TAG, "✅ Posts carregados com sucesso: " + posts.size() + " posts");
                    
                    if (posts.isEmpty()) {
                        atualizarStatus("📄 Arquivo JSON vazio ou sem posts");
                    } else {
                        atualizarStatus("✅ " + posts.size() + " posts carregados do GitHub!");
                        postAdapter.updatePosts(posts);
                    }
                } else {
                    Log.e(TAG, "❌ Erro na resposta: " + response.code() + " - " + response.message());
                    String erro = "Erro HTTP " + response.code() + ": " + response.message();
                    tratarErro(erro);
                }
            }
            
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG, "❌ Falha na requisição: " + t.getMessage(), t);
                mostrarLoading(false);
                String erro = "Falha na conexão: " + t.getMessage();
                if (t.getMessage() != null && t.getMessage().contains("timeout")) {
                    erro = "Timeout - GitHub pode estar lento";
                } else if (t.getMessage() != null && t.getMessage().contains("Unable to resolve host")) {
                    erro = "Sem conexão com a internet";
                }
                tratarErro(erro);
            }
        });
    }
    
    private void mostrarLoading(boolean mostrar) {
        progressBar.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        buttonRecarregar.setEnabled(!mostrar);
    }
    
    private void atualizarStatus(String mensagem) {
        textViewStatus.setText(mensagem);
        Log.d(TAG, "Status: " + mensagem);
    }
    
    private void tratarErro(String erro) {
        atualizarStatus("❌ " + erro);
        Toast.makeText(this, "Erro: " + erro, Toast.LENGTH_LONG).show();
        
        // Exibir posts offline como fallback
        List<Post> postsOffline = criarPostsOffline();
        postAdapter.updatePosts(postsOffline);
        atualizarStatus("📱 Exibindo posts offline de exemplo");
    }
    
    private List<Post> criarPostsOffline() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1, "Post Offline 1", "Este é um post de exemplo que funciona offline quando o GitHub está inacessível."));
        posts.add(new Post(2, "Configuração OK", "O serviço está configurado para: https://raw.githubusercontent.com/lucas-viana/luke-posts/refs/heads/main/posts.json"));
        posts.add(new Post(3, "Testando Conexão", "Verifique sua conexão com a internet e tente recarregar os posts."));
        return posts;
    }
    
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
