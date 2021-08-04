package com.example.fixawy.Client.AllTypesOfShops;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fixawy.Client.HomePageClient.HomePageClientActivity;
import com.example.fixawy.Client.ShowProductsOfShopType.ShowProductsOfShopTypeActivity;
import com.example.fixawy.Pojos.ShopOwnerUser;
import com.example.fixawy.R;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;
import com.example.fixawy.Worker.SelectJobPage.SelectJobActivity;
import static com.example.fixawy.Share.VerifyCode.VerificationCode.EXTR_USER_NAME;

import java.util.ArrayList;

public class AllTypesOfShopsActivity extends AppCompatActivity {

    Button btnElectricity, btnPlumber, btnCarpenter, btnPainter, btnTiles, btnMason, btnSmith, btnParquet, btnGyp, btnGlass, btnAlumetal, btnWood, btnCurtains, btnSatellite, btnAppliances, btnMarble;
    String phoneClient,clientName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_types_of_shops);

        phoneClient = getIntent().getStringExtra("phone");
        clientName = getIntent().getStringExtra(EXTR_USER_NAME);

        btnElectricity = findViewById(R.id.electricity);
        btnPlumber = findViewById(R.id.plumber);
        btnCarpenter = findViewById(R.id.carpenter);
        btnPainter = findViewById(R.id.painter);
        btnTiles = findViewById(R.id.tilesHandyman);
        btnMason = findViewById(R.id.mason);
        btnSmith = findViewById(R.id.smith);
        btnParquet = findViewById(R.id.parquet);
        btnGyp = findViewById(R.id.gypsum);
        btnGlass = findViewById(R.id.glasses);
        btnAlumetal = findViewById(R.id.alumetal);
        btnWood = findViewById(R.id.woodPainter);
        btnCurtains = findViewById(R.id.curtains);
        btnSatellite = findViewById(R.id.satellite);
        btnAppliances = findViewById(R.id.Appliances);
        btnMarble = findViewById(R.id.marble);


        btnElectricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Electricity");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Plumber");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Carpenter");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnPainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Painter");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "TilesHandyMan");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnMason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Mason");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnSmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Smith");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnParquet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Parquet");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnGyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Gypsum");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnMarble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Marble");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnAlumetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Alumetal");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Glasses");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnWood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "WoodPainter");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnCurtains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Curtains");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Satellite");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });

        btnAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTypesOfShopsActivity.this, ShowProductsOfShopTypeActivity.class);
                intent.putExtra("shopType", "Appliances");
                intent.putExtra("phoneClient",phoneClient);
                intent.putExtra(EXTR_USER_NAME,clientName);
                startActivity(intent);
                finish();
            }
        });
    }
}

