package br.edu.ifsuldeminas.mch.constraintlayouts.api;

import java.util.List;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostApiService {
    @GET("lucas-viana/luke-posts/refs/heads/main/posts.json")
    Call<List<Post>> obterPosts();
}
