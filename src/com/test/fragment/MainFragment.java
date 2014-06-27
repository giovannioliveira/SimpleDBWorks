package com.test.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.test.db.CustomDatabaseConstants;
import com.test.db.CustomDatabaseOpenHelper;
import com.test.testproject.R;

public class MainFragment extends Fragment{
	EditText nome;
	EditText email;
	Button commit;
	ListView list;
	CustomDatabaseOpenHelper db;
	private SimpleCursorAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		nome = (EditText) rootView.findViewById(R.id.nome);
		email = (EditText) rootView.findViewById(R.id.email);
		commit = (Button) rootView.findViewById(R.id.commit);
		list = (ListView) rootView.findViewById(R.id.list);
		db = CustomDatabaseOpenHelper.getInstance(getActivity());
		
		setCommitListener();
		
		generateListView();
		
		return rootView;
	}
	
	public void setCommitListener(){
		commit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!insertRegister(nome.getText().toString(), email.getText().toString())){
					Toast.makeText(getActivity(), "Falha ao adicionar registro", Toast.LENGTH_LONG).show();
				}
				generateListView();
			}
		});
	}
	
	public void generateListView(){
		Cursor data = getAllRegisters();

		String[] from = new String[] {
				CustomDatabaseConstants.COLUNA_B,
			    CustomDatabaseConstants.COLUNA_C
		};
		
		int[] to = new int[] { 
			    R.id.tvName,
			    R.id.tvEmail
		};
		
		adapter = new SimpleCursorAdapter(getActivity(), 
										  R.layout.list_view_adapter, 
										  data, 
										  from, 
										  to, 
										  0);
		list.setAdapter(adapter);
	}
	
	public boolean insertRegister(String name, String email){
		ContentValues values = new ContentValues();
		values.put(CustomDatabaseConstants.COLUNA_B, name);
		values.put(CustomDatabaseConstants.COLUNA_C, email);
		long result = db.getWritableDatabase().insert(CustomDatabaseConstants.TABLE_NAME, null, values);
		if(result==-1){
			return false;
		}
		return true;
	}
	
	public Cursor getAllRegisters(){
		String select = "SELECT * FROM " + CustomDatabaseConstants.TABLE_NAME;
		return db.getReadableDatabase().rawQuery(select, null);
	}

}
