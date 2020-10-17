package psi.tugas.mycrud_native;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mycrud.library.Cfg_Pegawai;
import com.mycrud.library.Cfg_Posisi;
import com.mycrud.library.GlobalUtils;
import com.mycrud.library.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Position_Show_All extends AppCompatActivity implements ListView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener
{
    private ListView listView;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_show_all);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);
    }

    //region Methods
    private void showPosition(String json_data)
    {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try
        {
            JSONArray result = new JSONObject(json_data).getJSONArray(Cfg_Posisi.TAG_JSON);

            for (int i = 0; i < result.length(); i++)
            {
                JSONObject obj = result.getJSONObject(i);

                HashMap<String, String> position = new HashMap<>();
                position.put(Cfg_Posisi.ID, obj.getString(Cfg_Posisi.ID));
                position.put(Cfg_Posisi.POSITION, obj.getString(Cfg_Posisi.POSITION));

                list.add(position);
            }
        }
        catch (JSONException ignored) {}

        ListAdapter adapter = new SimpleAdapter(Position_Show_All.this, list, R.layout.list_item, new String[] {Cfg_Posisi.ID, Cfg_Posisi.POSITION}, new int[] {R.id.id, R.id.name});
        listView.setAdapter(adapter);
    }

    @SuppressWarnings("deprecation")
    private void getJSON()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Position_Show_All.this, "Mengambil data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                return RequestHandler.sendGetRequest(Cfg_Posisi.URL_GET_ALL);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                showPosition(s);
            }
        }

        new Task().execute();
    }
    //endregion

    //region Events
    @Override
    protected void onResume()
    {
        super.onResume();
        getJSON();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = new Intent(this, Position_Show.class);
        HashMap<String, String> map = (HashMap<String, String>) adapterView.getItemAtPosition(i);
        String id = map.get(Cfg_Posisi.ID);
        intent.putExtra(Cfg_Posisi.TAG_ID, id);
        startActivity(intent);
    }

    @Override
    public void onRefresh()
    {
        getJSON();
        swipeRefresh.setRefreshing(false);
    }
    //endregion
}