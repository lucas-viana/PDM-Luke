package br.edu.ifsuldeminas.mch.constraintlayouts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import br.edu.ifsuldeminas.mch.constraintlayouts.activities.LoginActivity;

public class SentimentoDoDiaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sentimento_do_dia);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Abre o menu lateral ao clicar no ícone
        ImageView menuIcon = findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Ação do botão de logout
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sentimento_do_dia_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SeekBar sliderHappy = findViewById(R.id.slider_happy);
        SeekBar sliderNeutral = findViewById(R.id.slider_neutral);
        SeekBar sliderAngry = findViewById(R.id.slider_angry);
        Button buttonShare = findViewById(R.id.button_share);

        buttonShare.setOnClickListener(v -> {
            String resultado = "Meu sentimento do dia:\n"
                    + "Feliz: " + sliderHappy.getProgress() + "\n"
                    + "Neutro: " + sliderNeutral.getProgress() + "\n"
                    + "Irritado: " + sliderAngry.getProgress();

            Snackbar.make(v, "Pronto para compartilhar!", Snackbar.LENGTH_LONG)
                    .setAction("Compartilhar", view -> {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, resultado);
                        startActivity(Intent.createChooser(shareIntent, "Compartilhar via"));
                    }).show();
        });
    }
}