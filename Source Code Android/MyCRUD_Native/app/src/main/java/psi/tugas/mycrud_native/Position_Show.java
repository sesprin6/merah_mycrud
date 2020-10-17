package psi.tugas.mycrud_native;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mycrud.library.Cfg_Pegawai;
import com.mycrud.library.Cfg_Posisi;
import com.mycrud.library.Cfg_Posisi;
import com.mycrud.library.Cfg_Posisi;
import com.mycrud.library.GlobalUtils;
import com.mycrud.library.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Position_Show extends AppCompatActivity implements View.OnClickListener
{
    private EditText editText_Id;
    private EditText editText_Position;
    private EditText editText_Salary;

    private Button button_Update;
    private Button button_Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_show);

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        editText_Id = findViewById(R.id.editText_Id);
        editText_Position = findViewById(R.id.editText_Position);
        editText_Salary = findViewById(R.id.editText_Salary);

        button_Update = findViewById(R.id.button_Update);
        button_Delete = findViewById(R.id.button_Delete);

        button_Update.setOnClickListener(this);
        button_Delete.setOnClickListener(this);

        editText_Id.setText(getIntent().getStringExtra(Cfg_Posisi.TAG_ID));

        getPosition();
    }

    //region Methods
    @SuppressWarnings("deprecation")
    private void getPosition()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Position_Show.this, "Memuat data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                return RequestHandler.sendGetRequest(Cfg_Posisi.URL_GET + editText_Id.getText());
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

    private void showPosition(String json_data)
    {
        try
        {
            JSONArray result = new JSONObject(json_data).getJSONArray(Cfg_Posisi.TAG_JSON);
            JSONObject data = result.getJSONObject(0);

            editText_Position.setText(data.getString(Cfg_Posisi.POSITION));
            editText_Salary.setText(data.getString(Cfg_Posisi.SALARY));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void updatePosition()
    {
        final String id = editText_Id.getText().toString();
        final String position = editText_Position.getText().toString();
        final String salary = editText_Salary.getText().toString();

        if (TextUtils.isEmpty(position) || TextUtils.isEmpty(salary))
        {
            GlobalUtils.GToast.show(Position_Show.this, "Field tidak boleh kosong!");
            return;
        }

        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Position_Show.this, "Memperbarui data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                HashMap<String, String> map = new HashMap<>();
                map.put(Cfg_Posisi.ID, id);
                map.put(Cfg_Posisi.POSITION, position);
                map.put(Cfg_Posisi.SALARY, salary);

                return RequestHandler.sendPostRequest(Cfg_Posisi.URL_UPDATE, map);
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Position_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    @SuppressWarnings("deprecation")
    private void deletePosition()
    {
        class Task extends AsyncTask<Void, Void, String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                GlobalUtils.GProgressDialog.show(Position_Show.this, "Menghapus data", "Mohon tunggu...");
            }

            @Override
            protected String doInBackground(Void... voids)
            {
                return RequestHandler.sendGetRequest(Cfg_Posisi.URL_DELETE + editText_Id.getText());
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                GlobalUtils.GProgressDialog.dismiss();
                GlobalUtils.GToast.show(Position_Show.this, s);
                finish();
            }
        }

        new Task().execute();
    }

    private void confirmDeletePosition()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda yakin ingin menghapus posisi ini?");

        builder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        deletePosition();
                    }
                });
        builder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //endregion

    //region Events
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
    public void onClick(View view)
    {
        if (view == button_Update)
            updatePosition();
        else if (view == button_Delete)
            confirmDeletePosition();
    }
    //endregion
}