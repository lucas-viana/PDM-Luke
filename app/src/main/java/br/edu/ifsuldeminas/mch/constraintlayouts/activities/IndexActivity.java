package br.edu.ifsuldeminas.mch.constraintlayouts.activities;

            import android.content.Intent;
            import android.os.Bundle;
            import android.widget.ImageView;

            import androidx.activity.EdgeToEdge;
            import androidx.appcompat.app.AppCompatActivity;
            import androidx.core.graphics.Insets;
            import androidx.core.view.GravityCompat;
            import androidx.core.view.ViewCompat;
            import androidx.core.view.WindowInsetsCompat;
            import androidx.drawerlayout.widget.DrawerLayout;

            import com.google.android.material.navigation.NavigationView;

            import br.edu.ifsuldeminas.mch.constraintlayouts.R;
            import br.edu.ifsuldeminas.mch.constraintlayouts.SentimentoDoDiaActivity;

public class IndexActivity extends AppCompatActivity {

                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    EdgeToEdge.enable(this);
                    setContentView(R.layout.activity_index);

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

                    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.indexLayout), (v, insets) -> {
                        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                        return insets;
                    });

                    findViewById(R.id.button_actions).setOnClickListener(v -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    });
                    findViewById(R.id.button_motivation).setOnClickListener(v -> {
                        Intent intent = new Intent(this, SentimentoDoDiaActivity.class);
                        startActivity(intent);
                    });
                }
            }