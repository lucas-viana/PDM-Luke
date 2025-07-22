package br.edu.ifsuldeminas.mch.constraintlayouts.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import br.edu.ifsuldeminas.mch.constraintlayouts.model.Meta;

public class MetaDAO {
    private static final String COLLECTION_NAME = "metas";
    private CollectionReference metasCollection;

    public MetaDAO() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        metasCollection = db.collection(COLLECTION_NAME);
    }

    public Task<DocumentReference> criarMeta(Meta meta) {
        return metasCollection.add(meta);
    }

    public Task<Void> atualizarMeta(String metaId, Meta meta) {
        return metasCollection.document(metaId).set(meta);
    }

    public Task<Void> deletarMeta(String metaId) {
        return metasCollection.document(metaId).delete();
    }

    public Query listarMetas() {
        return metasCollection.orderBy("dataCriacao", Query.Direction.DESCENDING);
    }
}
