package net.larntech.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserAdapter.UserClickListener{


    RecyclerView rvUsers;
    UserAdapter userAdapter;
    List<UserModel> userModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvUsers = findViewById(R.id.rvUsers);
        setData();
        prepareRecyclerView();
    }

    public void setData(){
        userModelList.add(new UserModel("Josephine", "Martha", " 0700000000"));
        userModelList.add(new UserModel("Richard", "Larn", " 0700123123"));
        userModelList.add(new UserModel("Maiko", "Willams", " 0711212121"));
        userModelList.add(new UserModel("Dennis", "Martin", " 072231232"));
    }


    public void prepareRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvUsers.setLayoutManager(linearLayoutManager);
        preAdapter();
    }

    public void preAdapter(){
        userAdapter = new UserAdapter(userModelList,this, this::selectedUser);
        rvUsers.setAdapter(userAdapter);
    }

    @Override
    public void selectedUser(UserModel userModel) {
//        Toast.makeText(this,"Selected user "+userModel.getFirstName(), Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,SelectedUserActivity.class).putExtra("data",userModel));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchView){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText;
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);
    }
}