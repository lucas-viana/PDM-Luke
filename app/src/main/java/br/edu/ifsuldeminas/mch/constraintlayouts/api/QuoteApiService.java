package br.edu.ifsuldeminas.mch.constraintlayouts.api;

import br.edu.ifsuldeminas.mch.constraintlayouts.model.DicaDoDia;
import retrofit2.Call;
import retrofit2.http.GET;

public interface QuoteApiService {
    // Interface mantida para futuras implementações quando APIs estiverem estáveis
    // Atualmente o app utiliza prioritariamente dicas offline para garantir confiabilidade
    
    @GET("random")
    Call<DicaDoDia> obterDicaDoDia();
}
