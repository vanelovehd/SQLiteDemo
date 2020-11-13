package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sqlitedemo.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLHelper sqlHelper;
    ActivityMainBinding binding;
    List<Contact> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding  = DataBindingUtil.setContentView(this,R.layout.activity_main);

        sqlHelper= new SQLHelper(this);
        contactList = sqlHelper.getAllFood();

        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString().trim();
                String price = binding.etDonGia.getText().toString().trim();
                if(price !=  null || !price.isEmpty()){
                    Contact contact = new Contact(0,name,Integer.parseInt(price));
                    sqlHelper.insertFoods(contact);
                    Toast.makeText(getBaseContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                }

            }
        });
        binding.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etName.getText().toString().trim();
                String price =  binding.etDonGia.getText().toString().trim();


                if(price !=  null || !price.isEmpty()){
                    Contact contact = new Contact(Integer.parseInt(binding.etID.getText().toString()),name,Integer.parseInt(price));
                    sqlHelper.updateFoods(contact);
                    Toast.makeText(getBaseContext(),"Sửa thành công",Toast.LENGTH_LONG).show();
                }
            }

        });

        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(binding.etID.getText().toString().trim());
                sqlHelper.deleteFoods(id);
            }
        });

        binding.btnXoaALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int id = Integer.parseInt(binding.etID.getText().toString().trim());
                sqlHelper.deleteAllFoods();
            }
        });

    }
}