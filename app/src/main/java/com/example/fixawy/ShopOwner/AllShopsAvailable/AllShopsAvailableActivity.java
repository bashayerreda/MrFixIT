package com.example.fixawy.ShopOwner.AllShopsAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.fixawy.R;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;
import com.example.fixawy.ShopOwner.SignUp.ShopOwnerSignUPActivity;
import com.example.fixawy.Worker.SelectJobPage.SelectJobActivity;

public class AllShopsAvailableActivity extends AppCompatActivity {

    Button btnElectrican,btnPlumber,btnCarpenter,btnPainter,btnTiles,btnMason,btnSmith,btnParquet,btnGyp,btnGlass,btnAlumetal,btnWood,btnCurtains,btnSatellite,btnAppliances,btnMarble;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_shops_available);

        type=getIntent().getStringExtra("type");

        btnElectrican = findViewById(R.id.electricity_btn);
        btnPlumber = findViewById(R.id.pulmber_btn);
        btnCarpenter = findViewById(R.id.carpenter_btn);
        btnPainter = findViewById(R.id.painter_btn);
        btnTiles = findViewById(R.id.tilesHandyman_btn);
        btnMason = findViewById(R.id.mason_btn);
        btnSmith = findViewById(R.id.smith_btn);
        btnParquet = findViewById(R.id.parquet_btn);
        btnGyp = findViewById(R.id.gypsum_btn);
        btnMarble = findViewById(R.id.marble_btn);
        btnGlass = findViewById(R.id.glasses_btn);
        btnAlumetal = findViewById(R.id.alumetal_btn);
        btnWood = findViewById(R.id.woodPainter_btn);
        btnCurtains = findViewById(R.id.curtains_btn);
        btnSatellite = findViewById(R.id.satellite_btn);
        btnAppliances = findViewById(R.id.Appliances_btn);

        btnElectrican.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this, ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Electricity");
                startActivity(intent);
                finish();
            }
        });

        btnPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Plumber");
                startActivity(intent);
                finish();
            }
        });

        btnCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Carpenter");
                startActivity(intent);
                finish();
            }
        });

        btnPainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Painter");
                startActivity(intent);
                finish();
            }
        });

        btnTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","TilesHandyMan");
                startActivity(intent);
                finish();
            }
        });

        btnMason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Mason");
                startActivity(intent);
                finish();
            }
        });

        btnSmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Smith");
                startActivity(intent);
                finish();
            }
        });

        btnParquet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Parquet");
                startActivity(intent);
                finish();
            }
        });

        btnGyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Gypsum");
                startActivity(intent);
                finish();
            }
        });

        btnMarble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Marble");
                startActivity(intent);
                finish();
            }
        });

        btnAlumetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Alumetal");
                startActivity(intent);
                finish();
            }
        });

        btnGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Glasses");
                startActivity(intent);
                finish();
            }
        });

        btnWood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","WoodPainter");
                startActivity(intent);
                finish();
            }
        });

        btnCurtains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Curtains");
                startActivity(intent);
                finish();
            }
        });

        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Satellite");
                startActivity(intent);
                finish();
            }
        });

        btnAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllShopsAvailableActivity.this,ShopOwnerSignUPActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("shopType","Appliances");
                startActivity(intent);
                finish();
            }
        });
    }

}