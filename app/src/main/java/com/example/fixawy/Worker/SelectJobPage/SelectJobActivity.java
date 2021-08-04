package com.example.fixawy.Worker.SelectJobPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fixawy.R;
import com.example.fixawy.Share.RegisterPage.RegisterActivity;

public class SelectJobActivity extends AppCompatActivity {

    Button btnElectrican,btnPlumber,btnCarpenter,btnPainter,btnTiles,btnMason,btnSmith,btnParquet,btnGyp,btnGlass,btnAlumetal,btnWood,btnCurtains,btnSatellite,btnAppliances,btnMarble;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_job);

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
               Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
               intent.putExtra("type",type);
               intent.putExtra("jobTitle","Electricity");
               startActivity(intent);
               finish();
            }
        });

        btnPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Plumber");
                startActivity(intent);
                finish();
            }
        });

        btnCarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Carpenter");
                startActivity(intent);
                finish();
            }
        });

        btnPainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Painter");
                startActivity(intent);
                finish();
            }
        });

        btnTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","TilesHandyMan");
                startActivity(intent);
                finish();
            }
        });

        btnMason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Mason");
                startActivity(intent);
                finish();
            }
        });

        btnSmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Smith");
                startActivity(intent);
                finish();
            }
        });

        btnParquet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Parquet");
                startActivity(intent);
                finish();
            }
        });

        btnGyp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Gypsum");
                startActivity(intent);
                finish();
            }
        });

        btnMarble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Marble");
                startActivity(intent);
                finish();
            }
        });

        btnAlumetal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Alumetal");
                startActivity(intent);
                finish();
            }
        });

        btnGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Glasses");
                startActivity(intent);
                finish();
            }
        });

        btnWood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","WoodPainter");
                startActivity(intent);
                finish();
            }
        });

        btnCurtains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Curtains");
                startActivity(intent);
                finish();
            }
        });

        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Satellite");
                startActivity(intent);
                finish();
            }
        });

        btnAppliances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this,RegisterActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("jobTitle","Appliances");
                startActivity(intent);
                finish();
            }
        });
    }
}